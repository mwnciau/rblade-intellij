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
    private boolean lastCharacterWasWord = false;
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

    private void checkLastChar(){
      char chr = yycharat(yylength() - 1);

      lastCharacterWasWord = (chr >= 'a' && chr <= 'z') || (chr >= 'A' && chr <= 'Z') || (chr >= '0' && chr <= '9') || chr == '_';
    }
%}

RBLADE_END_STATEMENT=end[_A-Za-z]*[\?!]?
RBLADE_STATEMENT_LITERAL={RBLADE_END_STATEMENT}|blank\?|break|case|checked|class|defined\?|delete|disabled|else|elsif|each|each_?else|each_?with_?index|each_?with_?index_?else|empty|empty\?|env|for|for_?else|if|method|next|nil\?|old|once|patch|prepend|prepend_?if|prepend_?once|present\?|production|props|push|push_?if|push_?once|put|read_?only|required|selected|should_?render|stack|style|unless|until|when|while

BEGIN_RBLADE_STATEMENT=\@(ruby|{RBLADE_STATEMENT_LITERAL})[ \t]*\(
RBLADE_STATEMENT=\@{RBLADE_STATEMENT_LITERAL}

ESCAPED_RBLADE=\<%%|\@\{\{|\@\{\!\!|\@{RBLADE_STATEMENT}|\@\@ruby

RBLADE_COMMENT=\{\{--~(--\}\})|<%#~(%>)

RUBY_BLOCK_END=\}\}|%>|\!\!\}|\@end_?ruby|\)

RBLADE_START_CHARACTER=[<@{]
NON_RBLADE_START_CHARACTER=[^{RBLADE_START_CHARACTER}]

START_BLOCK=[{\[(|]
END_BLOCK=[}\])]

PERCENT_STRING_DELIMITER=[\x00-\x7F&&[^a-zA-Z0-9]]

// Verbatim to prevent other rules affecting the contents of verbatim blocks
%xstate STATE_VERBATIM
%states RBLADE_RUBY RBLADE_PRINT RBLADE_UNSAFE_PRINT ERB_STATEMENT
%state STATE_RUBY_BLOCK
%state STATE_RUBY_BLOCK_END
%state STATE_STRING_LITERAL
%state STATE_STRING_LITERAL_INTERPOLATION

%%

// RBlade verbatims and comments should take the highest priority so extend the matching text to the end of the file
\@verbatim/~(\@end_?verbatim)[^]* {
  if (lastCharacterWasWord) {
      return RBladeTypes.HTML_TEMPLATE;
  }
  yybegin(STATE_VERBATIM);
  return RBladeTypes.RBLADE_STATEMENT;
}

{RBLADE_COMMENT}/[^]* {
    return RBladeTypes.COMMENT;
}

<YYINITIAL> {
    {ESCAPED_RBLADE}/[^]* {
      checkLastChar();
      return RBladeTypes.HTML_TEMPLATE;
    }
    \{\{/~(\}\}) {
      lastCharacterWasWord = false;
      yybegin(RBLADE_PRINT);
      return RBladeTypes.RBLADE_STATEMENT;
    }
    \{\!\!/~(\!\!\}) {
      lastCharacterWasWord = false;
      yybegin(RBLADE_UNSAFE_PRINT);
      return RBladeTypes.RBLADE_STATEMENT;
    }
    \@ruby/\s~([^a-zA-Z0-9_]\@end_?ruby) {
      if (lastCharacterWasWord) {
        return RBladeTypes.HTML_TEMPLATE;
      }
      lastCharacterWasWord = true;
      yybegin(RBLADE_RUBY);
      return RBladeTypes.RBLADE_STATEMENT;
    }
    \<%=?=?/~(%\>) {
      lastCharacterWasWord = false;
      yybegin(ERB_STATEMENT);
      return RBladeTypes.RBLADE_STATEMENT;
    }

    {BEGIN_RBLADE_STATEMENT} {
      if (lastCharacterWasWord) {
        lastCharacterWasWord = false;
        return RBladeTypes.HTML_TEMPLATE;
      }
      lastCharacterWasWord = false;

      stateStack.addFirst(STATE_RUBY_BLOCK_END);
      rubyBlockEndDelimiter = ")";
      yybegin(STATE_RUBY_BLOCK);
      currentStatement = yytext().toString().replaceAll("[^a-zA-Z]", "").toLowerCase();

      return RBladeTypes.RBLADE_STATEMENT;
    }
    {RBLADE_STATEMENT} {
      if (lastCharacterWasWord) {
          return RBladeTypes.HTML_TEMPLATE;
      }
      checkLastChar();

      return RBladeTypes.RBLADE_STATEMENT;
    }
    {RBLADE_STATEMENT}[a-zA-Z0-9_\?\!] {
      checkLastChar();
      return RBladeTypes.HTML_TEMPLATE;
    }
    {NON_RBLADE_START_CHARACTER}+ {
      checkLastChar();
      return RBladeTypes.HTML_TEMPLATE;
    }
    {RBLADE_START_CHARACTER} {
      lastCharacterWasWord = false;
      return RBladeTypes.HTML_TEMPLATE;
    }
}

<RBLADE_RUBY> {
  \@end_?ruby {
    lastCharacterWasWord = true;
    yybegin(YYINITIAL);
    return RBladeTypes.RBLADE_STATEMENT;
  }
  [^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
}
<RBLADE_PRINT> {
  \}\} {
    lastCharacterWasWord = false;
    yybegin(YYINITIAL);
    return RBladeTypes.RBLADE_STATEMENT;
  }
  [^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
}
<RBLADE_UNSAFE_PRINT> {
  \!\!\} {
    lastCharacterWasWord = false;
    yybegin(YYINITIAL);
    return RBladeTypes.RBLADE_STATEMENT;
  }
  [^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
}
<ERB_STATEMENT> {
  %> {
    lastCharacterWasWord = false;
    yybegin(YYINITIAL);
    return RBladeTypes.RBLADE_STATEMENT;
  }
  [^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
}

<STATE_VERBATIM> {
  [^\@]+ {
    return RBladeTypes.HTML_TEMPLATE;
  }
  \@end_?verbatim {
    lastCharacterWasWord = true;
    yybegin(YYINITIAL);
    return RBladeTypes.RBLADE_STATEMENT;
  }
  \@ {
    return RBladeTypes.HTML_TEMPLATE;
  }
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
    \"|\'|%[qQwWiIrsx]?{PERCENT_STRING_DELIMITER} {
                                            stateStack.addFirst(STATE_RUBY_BLOCK);
                                            blockStack.addFirst(flipBracket(yycharat(yylength() - 1)));
                                            stringIsInterpolated = yycharat(0) == '"' || yylength() == 2 || (yylength() == 3 && (yycharat(1) == 'Q' || yycharat(1) == 'W' || yycharat(1) == 'I' || yycharat(1) == 'r' || yycharat(1) == 'x'));
                                            yybegin(STATE_STRING_LITERAL);
                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
    \?[^]                               { return RBladeTypes.RUBY_EXPRESSION; }
    [^\"\'%\[\](){}|@,\s\:/?]+          { return RBladeTypes.RUBY_EXPRESSION; }
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
    \"|\'|%[qQwWiIrsx]?{PERCENT_STRING_DELIMITER} {
                                          stateStack.addFirst(STATE_STRING_LITERAL_INTERPOLATION);
                                          blockStack.addFirst(flipBracket(yycharat(yylength() - 1)));
                                          yybegin(STATE_STRING_LITERAL);

                                          return RBladeTypes.RUBY_EXPRESSION;
                                        }
    [^\}\{\"\'%/]+                      { return RBladeTypes.RUBY_EXPRESSION; }
    [^]                                 { return RBladeTypes.RUBY_EXPRESSION; }
}

[^]                                     { return TokenType.BAD_CHARACTER; }
