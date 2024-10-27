// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.mwnciau.rblade;

import com.intellij.psi.tree.IElementType;
import com.mwnciau.rblade.psi.RBladeTypes;
import com.intellij.psi.TokenType;
import java.util.ArrayDeque;

%%

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
    private ArrayDeque<Character> stringDelimiterStack = new ArrayDeque<Character>();
    private boolean stringIsInterpolated;
    private String rubyBlockEndDelimiter;

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

STATEMENT_CHARACTER=[a-zA-Z_]

BEGIN_RBLADE_STATEMENT=\@{STATEMENT_CHARACTER}+[ \t]*\(
RBLADE_STATEMENT=\@{STATEMENT_CHARACTER}+

RBLADE_COMMENT=\{\{--[^]*?--\}\}|<%#[^]*?%>

RUBY_BLOCK_END=\}\}|%>|\!\!\}|\@end_?ruby|\)

BLADE_START_CHARACTER=<|@|\{
NON_RBLADE_CHARACTER=[^@<{]
NON_RBLADE_STRING={NON_RBLADE_CHARACTER}+

%state STATE_RUBY_BLOCK
%state STATE_RUBY_BLOCK_END
%state STATE_STRING_LITERAL
%state STATE_STRING_LITERAL_INTERPOLATION

%%

<YYINITIAL> {
    {RBLADE_COMMENT}                { return RBladeTypes.COMMENT; }

    \{\{                            {
                                            stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = "}}";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
                                        }
    \{\!\!                          {
                                            stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = "!!}";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
                                        }
    \<%=?=?                         {
                                            stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = "%>";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
                                        }
    @ruby                           {
                                            stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = "@endruby";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
                                        }

    {BEGIN_RBLADE_STATEMENT}        {
                                            stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = ")";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
                                        }
    {RBLADE_STATEMENT}              { return RBladeTypes.RBLADE_STATEMENT; }

    {BLADE_START_CHARACTER}         { return RBladeTypes.HTML_TEMPLATE; }
    {NON_RBLADE_STRING}             { return RBladeTypes.HTML_TEMPLATE; }
    [^]                             { return RBladeTypes.HTML_TEMPLATE; }
}

<STATE_RUBY_BLOCK> {
    {RUBY_BLOCK_END}                {
                                            if (
                                              stateStack.getFirst() == STATE_RUBY_BLOCK_END
                                              && rubyBlockEndDelimiter.equals(yytext().toString().replace("_", "").toLowerCase())
                                            ) {
                                                yypushback(yylength());
                                                yybegin(stateStack.removeFirst());
                                            } else {
                                              if (yycharat(0) == ')') {
                                                  yybegin(stateStack.removeFirst());
                                              }

                                              return RBladeTypes.RUBY_EXPRESSION;
                                            }
                                        }
    ,                               { return stateStack.getFirst() == STATE_RUBY_BLOCK_END ? RBladeTypes.RBLADE_STATEMENT : RBladeTypes.RUBY_EXPRESSION; }
    \(                              { stateStack.addFirst(STATE_RUBY_BLOCK); return RBladeTypes.RUBY_EXPRESSION; }
    \"|\'|%[qQwWiIrsx]?.            {
                                            stateStack.addFirst(STATE_RUBY_BLOCK);
                                            stringDelimiterStack.addFirst(flipBracket(yycharat(yylength() - 1)));
                                            stringIsInterpolated = yycharat(0) == '"' || (yylength() == 3 && yytext().toString().substring(0, 2) == "%Q");
                                            yybegin(STATE_STRING_LITERAL);
                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
    [^(\"\'%)}@,]+                  { return RBladeTypes.RUBY_EXPRESSION; }
    [^]                             { return RBladeTypes.RUBY_EXPRESSION; }
}
<STATE_RUBY_BLOCK_END> {
  {RUBY_BLOCK_END}                  {
                                        yybegin(YYINITIAL);
                                        return RBladeTypes.RBLADE_STATEMENT;
                                      }
  [^]                               { return TokenType.BAD_CHARACTER; }
}

<STATE_STRING_LITERAL> {
    #\{                             {
                                            if (stringIsInterpolated) {
                                              stateStack.addFirst(STATE_STRING_LITERAL);
                                              yybegin(STATE_STRING_LITERAL_INTERPOLATION);
                                            }
                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
    \\[^]                           { return RBladeTypes.RUBY_EXPRESSION; }
    [^]                             {
                                            if (yycharat(0) == stringDelimiterStack.getFirst()) {
                                                stringDelimiterStack.removeFirst();
                                                yybegin(stateStack.removeFirst());
                                            }
                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
}

<STATE_STRING_LITERAL_INTERPOLATION> {
    \}                              { yybegin(stateStack.removeFirst()); return RBladeTypes.RUBY_EXPRESSION; }
    \{                              { stateStack.addFirst(STATE_STRING_LITERAL_INTERPOLATION); return RBladeTypes.RUBY_EXPRESSION; }
    \"|\'|%[qQwWiIrsx]?.            {
                                          stateStack.addFirst(STATE_STRING_LITERAL_INTERPOLATION);
                                          stringDelimiterStack.addFirst(flipBracket(yycharat(yylength() - 1)));
                                          yybegin(STATE_STRING_LITERAL);
                                          return RBladeTypes.RUBY_EXPRESSION;
                                        }
    [^\}\{\"\'%]+                   { return RBladeTypes.RUBY_EXPRESSION; }
    [^]                             { return RBladeTypes.RUBY_EXPRESSION; }
}

[^]                                 { return TokenType.BAD_CHARACTER; }
