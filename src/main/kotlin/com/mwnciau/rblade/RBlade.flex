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

RBLADE_START_CHARACTER=[<@{]
NON_RBLADE_START_CHARACTER=[^{RBLADE_START_CHARACTER}]

PERCENT_STRING_DELIMITER=[\x00-\x7F&&[^a-zA-Z0-9]]
STRING_START_CHARACTERS=[\"'%?]

// Verbatim to prevent other rules affecting the contents of verbatim blocks
%xstate STATE_VERBATIM
%states RBLADE_RUBY RBLADE_PRINT RBLADE_UNSAFE_PRINT ERB_STATEMENT
%state STATE_RUBY_BLOCK
%states STATE_STRING_LITERAL STATE_STRING_INTERPOLATED_LITERAL STATE_STRING_INTERPOLATION
%states MATCHED_PARENTHESES MATCHED_BRACKETS MATCHED_BRACES

%%

// RBlade verbatims and comments should take the highest priority so extend the matching text to the end of the file
\@verbatim/[^a-zA-Z0-9_]~([^a-zA-Z0-9_]\@end_?verbatim) {
  if (lastCharacterWasWord) {
      return RBladeTypes.HTML_TEMPLATE;
  }
  lastCharacterWasWord = true;
  stateStack.addFirst(yystate());
  yybegin(STATE_VERBATIM);
  return RBladeTypes.RBLADE_STATEMENT;
}

{RBLADE_COMMENT} {
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
    // If we can match a longer string with an alphanum character, this isn't a valid statement
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
    checkLastChar();
    return stateStack.getFirst() == YYINITIAL ? RBladeTypes.HTML_TEMPLATE : RBladeTypes.RUBY_EXPRESSION;
  }
  \@end_?verbatim {
    if (lastCharacterWasWord) {
      return stateStack.getFirst() == YYINITIAL ? RBladeTypes.HTML_TEMPLATE : RBladeTypes.RUBY_EXPRESSION;
    }
    lastCharacterWasWord = true;
    yybegin(stateStack.removeFirst());
    return RBladeTypes.RBLADE_STATEMENT;
  }
  \@end_?verbatim[a-zA-Z0-9_] {
    lastCharacterWasWord = true;
    return stateStack.getFirst() == YYINITIAL ? RBladeTypes.HTML_TEMPLATE : RBladeTypes.RUBY_EXPRESSION;
  }
  \@ {
    lastCharacterWasWord = false;
    return stateStack.getFirst() == YYINITIAL ? RBladeTypes.HTML_TEMPLATE : RBladeTypes.RUBY_EXPRESSION;
  }
}

<STATE_RUBY_BLOCK, MATCHED_BRACES, MATCHED_BRACKETS, MATCHED_PARENTHESES, STATE_STRING_INTERPOLATION> {
  \"|%[QWIrx]?{PERCENT_STRING_DELIMITER} {
    stateStack.addFirst(yystate());
    blockStack.addFirst(flipBracket(yycharat(yylength() - 1)));
    yybegin(STATE_STRING_INTERPOLATED_LITERAL);
    return RBladeTypes.RUBY_EXPRESSION;
  }
  \'|%[qwis]{PERCENT_STRING_DELIMITER} {
    stateStack.addFirst(yystate());
    blockStack.addFirst(flipBracket(yycharat(yylength() - 1)));
    yybegin(STATE_STRING_LITERAL);
    return RBladeTypes.RUBY_EXPRESSION;
  }
  \?[^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
}

<STATE_RUBY_BLOCK> {
  \) {
    yybegin(YYINITIAL);
    currentStatement = "";
    return RBladeTypes.RBLADE_STATEMENT;
  }
  \( {
    yybegin(MATCHED_PARENTHESES);
    stateStack.addFirst(STATE_RUBY_BLOCK);
    return RBladeTypes.RUBY_EXPRESSION;
  }
  \{ {
    yybegin(MATCHED_BRACES);
    stateStack.addFirst(STATE_RUBY_BLOCK);
    return RBladeTypes.RUBY_EXPRESSION;
  }
  \[ {
    yybegin(MATCHED_BRACKETS);
    stateStack.addFirst(STATE_RUBY_BLOCK);
    return RBladeTypes.RUBY_EXPRESSION;
  }
  , {
    if (naryStatements.contains(currentStatement)) {
      return RBladeTypes.RBLADE_STATEMENT_COMMA;
    } else {
      return RBladeTypes.RUBY_EXPRESSION;
    }
  }
  \: {
    if (blockStack.isEmpty() && currentStatement.equals("props")) {
      return RBladeTypes.RBLADE_STATEMENT_PROPS_COLON;
    }

    return RBladeTypes.RUBY_EXPRESSION;
  }
  \s+in\s+ {
    if (currentStatement.startsWith("each")) {
      return RBladeTypes.RBLADE_STATEMENT_EACH_IN;
    }

    return RBladeTypes.RUBY_EXPRESSION;
  }
  \?[^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
  [^@{STRING_START_CHARACTERS}\[(){@,\s\:]+ {
    return RBladeTypes.RUBY_EXPRESSION;
  }
  [^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
}

<MATCHED_PARENTHESES> {
  \) {
    yybegin(stateStack.removeFirst());
    return RBladeTypes.RUBY_EXPRESSION;
  }
  \( {
    stateStack.addFirst(MATCHED_PARENTHESES);
    return RBladeTypes.RUBY_EXPRESSION;
  }
  [^@{STRING_START_CHARACTERS}()]+ {
    return RBladeTypes.RUBY_EXPRESSION;
  }
  [^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
}
<MATCHED_BRACES> {
  \} {
    yybegin(stateStack.removeFirst());
    return RBladeTypes.RUBY_EXPRESSION;
  }
  \{ {
    stateStack.addFirst(MATCHED_BRACES);
    return RBladeTypes.RUBY_EXPRESSION;
  }
  [^@{STRING_START_CHARACTERS}{}]+ {
    return RBladeTypes.RUBY_EXPRESSION;
  }
  [^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
}
<MATCHED_BRACKETS> {
  \] {
    yybegin(stateStack.removeFirst());
    return RBladeTypes.RUBY_EXPRESSION;
  }
  \[ {
    stateStack.addFirst(MATCHED_BRACKETS);
    return RBladeTypes.RUBY_EXPRESSION;
  }
  [^@{STRING_START_CHARACTERS}\[\]]+ {
    return RBladeTypes.RUBY_EXPRESSION;
  }
  [^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
}

<STATE_STRING_LITERAL> {
  [^{PERCENT_STRING_DELIMITER}]+ {
    return RBladeTypes.RUBY_EXPRESSION;
  }
  \\[^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
  [^] {
    if (yycharat(0) == blockStack.getFirst()) {
      blockStack.removeFirst();
      yybegin(stateStack.removeFirst());
    }

    return RBladeTypes.RUBY_EXPRESSION;
  }
}

<STATE_STRING_INTERPOLATED_LITERAL> {
  [^{PERCENT_STRING_DELIMITER}]+ {
    return RBladeTypes.RUBY_EXPRESSION;
  }
  #\{ {
    stateStack.addFirst(STATE_STRING_INTERPOLATED_LITERAL);
    yybegin(STATE_STRING_INTERPOLATION);
    return RBladeTypes.RUBY_EXPRESSION;
  }
  \\[^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
  [^] {
    if (yycharat(0) == blockStack.getFirst()) {
      blockStack.removeFirst();
      yybegin(stateStack.removeFirst());
    }

    return RBladeTypes.RUBY_EXPRESSION;
  }
}

<STATE_STRING_INTERPOLATION> {
  \} {
    yybegin(stateStack.removeFirst());
    return RBladeTypes.RUBY_EXPRESSION;
  }
  \{ {
    stateStack.addFirst(STATE_STRING_INTERPOLATION);
    return RBladeTypes.RUBY_EXPRESSION;
  }
  [^\{\}@{STRING_START_CHARACTERS}]+ {
    return RBladeTypes.RUBY_EXPRESSION;
  }
  [^] {
    return RBladeTypes.RUBY_EXPRESSION;
  }
}

[^] {
  return TokenType.BAD_CHARACTER;
}
