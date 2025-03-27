// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.mwnciau.rblade;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.mwnciau.rblade.psi.RBladeTypes;
import com.intellij.psi.TokenType;
import java.util.ArrayDeque;
import java.util.Set;
%%

%public
%class RBladeLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%ignorecase
%eof{
      return;
%eof}

%{
    private int parentheses;
    private ArrayDeque<Integer> stateStack = new ArrayDeque<Integer>();
    private ArrayDeque<Character> blockStack = new ArrayDeque<Character>();
    private boolean stringIsInterpolated;
    private String rubyBlockEndDelimiter;
    private String currentStatement = "";
    private Set<String> naryStatements = Set.of("pushif", "prependif", "each", "eachelse", "eachwithindex", "eachwithindexelse", "props");

    private char flipBracket(char bracket){
        switch (bracket) {
            case '{': return '}';
            case '[': return ']';
            case '(': return ')';
            case '<': return '>';
            default: return bracket;
        }
    }
%}

RBLADE_END_STATEMENT=end[_A-Za-z\?]*
RBLADE_STATEMENT_LITERAL={RBLADE_END_STATEMENT}|blank\?|break|case|checked|class|defined\?|delete|disabled|else|elsif|each|each_?else|each_?with_?index|each_?with_?index_?else|empty|empty\?|env|for|for_?else|if|method|next|nil\?|old|once|patch|prepend|prepend_?if|prepend_?once|present\?|production|props|push|push_?if|push_?once|put|read_?only|required|ruby|selected|should_?render|stack|style|unless|until|when|while

BEGIN_RBLADE_STATEMENT=\@{RBLADE_STATEMENT_LITERAL}[ \t]*\(

RBLADE_STATEMENT=\@{RBLADE_STATEMENT_LITERAL}

ESCAPE_SEQUENCES=\<%%|\@\{\{|\@{RBLADE_STATEMENT}

RBLADE_COMMENT=\{\{--~(--\}\})|<%#~(%>)

RUBY_BLOCK_END=\}\}|%>|\!\!\}|\@end_?ruby|\)

BLADE_START_CHARACTER=<|@|\{
NON_RBLADE_CHARACTER=[^@<{]
NON_RBLADE_STRING={NON_RBLADE_CHARACTER}+

START_BLOCK=[{\[(|]
END_BLOCK=[}\])]

%state STATE_VERBATIM
%state STATE_RUBY_BLOCK
%state STATE_RUBY_BLOCK_END
%state STATE_STRING_LITERAL
%state STATE_STRING_LITERAL_INTERPOLATION
%state STATE_REGEX_LITERAL

%%

<YYINITIAL> {
    {RBLADE_COMMENT}                    { return RBladeTypes.COMMENT; }
    {ESCAPE_SEQUENCES}                  { return RBladeTypes.HTML_TEMPLATE; }
    \@verbatim                          {
                                          yybegin(STATE_VERBATIM);
                                          return RBladeTypes.RBLADE_STATEMENT;
                                        }
    \{\{                                {
                                            stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = "}}";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
                                        }
    \{\!\!                              {
                                            stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = "!!}";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
                                        }
    \<%=?=?                             {
                                            stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = "%>";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
                                        }
    @ruby                               {
                                            stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = "@endruby";
                                            currentStatement = "ruby";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
                                        }

    {BEGIN_RBLADE_STATEMENT}            {
                                            stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = ")";
                                            yybegin(STATE_RUBY_BLOCK);
                                            currentStatement = yytext().toString().replaceAll("[^a-zA-Z]", "").toLowerCase();

                                            return RBladeTypes.RBLADE_STATEMENT;
                                            }
    {RBLADE_STATEMENT}                  { return RBladeTypes.RBLADE_STATEMENT; }

    {BLADE_START_CHARACTER}             { return RBladeTypes.HTML_TEMPLATE; }
    {NON_RBLADE_STRING}                 { return RBladeTypes.HTML_TEMPLATE; }
    [^]                                 { return RBladeTypes.HTML_TEMPLATE; }
}

<STATE_VERBATIM> {
  [^\@]+                                 {return RBladeTypes.HTML_TEMPLATE;}
  \@end_?verbatim                        {return RBladeTypes.RBLADE_STATEMENT;}
  \@                                     {return RBladeTypes.HTML_TEMPLATE;}
  [^]                                   {return TokenType.BAD_CHARACTER;}
}

<STATE_RUBY_BLOCK> {
    {RUBY_BLOCK_END}                    {
                                            if (blockStack.isEmpty() && rubyBlockEndDelimiter.equals(yytext().toString().replace("_", "").toLowerCase())) {
                                                yypushback(yylength());
                                                yybegin(stateStack.removeFirst());
                                            } else {
                                                if (!blockStack.isEmpty() && blockStack.peekFirst() == yycharat(0)) {
                                                    blockStack.removeFirst();
                                                }

                                                return RBladeTypes.RUBY_EXPRESSION;
                                            }
                                        }
    {START_BLOCK}                       {
                                            if (!blockStack.isEmpty() && yycharat(0) == '|' && blockStack.peekFirst() == '|') {
                                                blockStack.removeFirst();
                                            } else {
                                                blockStack.addFirst(flipBracket(yycharat(0)));
                                            }

                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
    {END_BLOCK}                         {
                                            if (!blockStack.isEmpty() && blockStack.peekFirst() == yycharat(0)) {
                                                blockStack.removeFirst();
                                            }

                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
    ,                                   {
                                          if (blockStack.isEmpty() && (naryStatements.contains(currentStatement))) {
                                            return RBladeTypes.RBLADE_STATEMENT_COMMA;
                                          } else {
                                            return RBladeTypes.RUBY_EXPRESSION;
                                          }
                                        }
    \:                                  {
                                            if (blockStack.isEmpty() && currentStatement.equals("props")) {
                                                return RBladeTypes.RBLADE_STATEMENT_PROPS_COLON;
                                            }

                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
    \s+in\s+                            {
                                            if (blockStack.isEmpty() && currentStatement.startsWith("each")) {
                                                return RBladeTypes.RBLADE_STATEMENT_EACH_IN;
                                            }

                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
    \/[^/]                              {
                                            stateStack.addFirst(STATE_RUBY_BLOCK);
                                            yypushback(1);
                                            yybegin(STATE_REGEX_LITERAL);

                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
    \"|\'|%[qQwWiIrsx]?.                {
                                            stateStack.addFirst(STATE_RUBY_BLOCK);
                                            blockStack.addFirst(flipBracket(yycharat(yylength() - 1)));
                                            stringIsInterpolated = yycharat(0) == '"' || yylength() == 2 || (yylength() == 3 && yytext().toString().substring(0, 2).equals("%Q"));
                                            yybegin(STATE_STRING_LITERAL);
                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
    [^\"\'%\[\](){}|@,\s\:/]+           { return RBladeTypes.RUBY_EXPRESSION; }
    [^]                                 { return RBladeTypes.RUBY_EXPRESSION; }
}
<STATE_RUBY_BLOCK_END> {
  {RUBY_BLOCK_END}                      {
                                          yybegin(YYINITIAL);
                                          currentStatement = "";
                                          return RBladeTypes.RBLADE_STATEMENT;
                                        }
  [^]                                   { return TokenType.BAD_CHARACTER; }
}

<STATE_STRING_LITERAL> {
    #\{                                 {
                                            if (stringIsInterpolated) {
                                              stateStack.addFirst(STATE_STRING_LITERAL);
                                              yybegin(STATE_STRING_LITERAL_INTERPOLATION);
                                            }
                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
    \\[^]                               { return RBladeTypes.RUBY_EXPRESSION; }
    [^]                                 {
                                            if (yycharat(0) == blockStack.getFirst()) {
                                                blockStack.removeFirst();
                                                yybegin(stateStack.removeFirst());
                                            }

                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
}

<STATE_STRING_LITERAL_INTERPOLATION> {
    \}                                  { yybegin(stateStack.removeFirst()); return RBladeTypes.RUBY_EXPRESSION; }
    \{                                  { stateStack.addFirst(STATE_STRING_LITERAL_INTERPOLATION); return RBladeTypes.RUBY_EXPRESSION; }
    \"|\'|%[qQwWiIrsx]?.                {
                                          stateStack.addFirst(STATE_STRING_LITERAL_INTERPOLATION);
                                          blockStack.addFirst(flipBracket(yycharat(yylength() - 1)));
                                          yybegin(STATE_STRING_LITERAL);

                                          return RBladeTypes.RUBY_EXPRESSION;
                                        }
    \/[^/]                              {
                                            yypushback(1);
                                            stateStack.addFirst(STATE_STRING_LITERAL_INTERPOLATION);
                                            yybegin(STATE_REGEX_LITERAL);

                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
    [^\}\{\"\'%/]+                      { return RBladeTypes.RUBY_EXPRESSION; }
    [^]                                 { return RBladeTypes.RUBY_EXPRESSION; }
}

<STATE_REGEX_LITERAL> {
    \\[^]                               { return RBladeTypes.RUBY_EXPRESSION; }
    \/                                  {
                                            yybegin(stateStack.removeFirst());

                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
    [^]                                 { return RBladeTypes.RUBY_EXPRESSION; }
}

[^]                                     { return TokenType.BAD_CHARACTER; }
