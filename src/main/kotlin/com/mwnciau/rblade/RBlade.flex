// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.mwnciau.rblade;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.mwnciau.rblade.psi.RBladeTypes;
import com.intellij.psi.TokenType;import kotlinx.html.RUBY;

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
    private char stringEndDelimiter;
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

//FIRST_VALUE_CHARACTER=[^ \n\f\\] | "\\"{CRLF} | "\\".
//VALUE_CHARACTER=[^\n\f\\] | "\\"{CRLF} | "\\".
//END_OF_LINE_COMMENT=("#"|"!")[^\r\n]*
//SEPARATOR=[:=]
//KEY_CHARACTER=[^:=\ \n\t\f\\] | "\\ "
STATEMENT_CHARACTER=[a-zA-Z_]

BEGIN_RBLADE_STATEMENT=\@{STATEMENT_CHARACTER}+[ \t]*\(
RBLADE_STATEMENT=\@{STATEMENT_CHARACTER}+

RBLADE_COMMENT=\{\{--[^]*?--\}\}|<%#[^]*?%>

RUBY_BLOCK_END=\}\}|%>|\!\!\}|\@end_?ruby

BLADE_START_CHARACTER=<|@|\{
NON_RBLADE_CHARACTER=[^@<{]
NON_RBLADE_STRING={NON_RBLADE_CHARACTER}+

%state RBLADE_PARAMETERS
%state RBLADE_PARAMETER_STRING_LITERAL
%state RUBY_BLOCK

%%

<YYINITIAL> {
    {RBLADE_COMMENT}                { return RBladeTypes.COMMENT; }

    \{\{                            { yybegin(RUBY_BLOCK); rubyBlockEndDelimiter = "}}"; return RBladeTypes.RBLADE_STATEMENT; }
    \{\!\!                          { yybegin(RUBY_BLOCK); rubyBlockEndDelimiter = "!!}"; return RBladeTypes.RBLADE_STATEMENT; }
    \<%=?=?                         { yybegin(RUBY_BLOCK); rubyBlockEndDelimiter = "%>"; return RBladeTypes.RBLADE_STATEMENT; }
    @ruby                           { yybegin(RUBY_BLOCK); rubyBlockEndDelimiter = "@endruby"; return RBladeTypes.RBLADE_STATEMENT; }

    {BEGIN_RBLADE_STATEMENT}        {
                                        yybegin(RBLADE_PARAMETERS);
                                        parentheses = 1;
                                        return RBladeTypes.RBLADE_STATEMENT;
                                    }
    {RBLADE_STATEMENT}              { return RBladeTypes.RBLADE_STATEMENT; }

    {BLADE_START_CHARACTER}         { return RBladeTypes.HTML_TEMPLATE; }
    {NON_RBLADE_STRING}             { return RBladeTypes.HTML_TEMPLATE; }
}

<RBLADE_PARAMETERS> {
    \(                              { parentheses += 1; return RBladeTypes.RUBY_TEMPLATE; }
    \)                              {
                                        parentheses -= 1;
                                        if (parentheses == 0) {
                                            yybegin(YYINITIAL);
                                            return RBladeTypes.RBLADE_STATEMENT;
                                        } else {
                                            return RBladeTypes.RUBY_TEMPLATE;
                                        }
                                    }
    \"|'|%[qQwWiIrsx]?.             {
                                        yybegin(RBLADE_PARAMETER_STRING_LITERAL);
                                        stringEndDelimiter = flipBracket(yycharat(yylength() - 1));
                                    }
    [^(\"'%)]+                      { return RBladeTypes.RUBY_TEMPLATE; }
}

<RBLADE_PARAMETER_STRING_LITERAL> {
    // This is a shortcut, but it only causes problems in weird edge cases
    #\{[^}]+\}                      { }
    \\.                             { }
    .                               {
                                        if (yycharat(0) == stringEndDelimiter) {
                                            yybegin(RBLADE_PARAMETERS);
                                            return RBladeTypes.RUBY_TEMPLATE;
                                        }
                                    }
}

<RUBY_BLOCK> {
    {RUBY_BLOCK_END}                {
                                        if (rubyBlockEndDelimiter.equals(yytext().toString().replace("_", "").toLowerCase())) {
                                            yybegin(YYINITIAL);
                                            return RBladeTypes.RBLADE_STATEMENT;
                                        } else {
                                            return RBladeTypes.RUBY_TEMPLATE;
                                        }
                                    }
    [^}%!@]+                        { return RBladeTypes.RUBY_TEMPLATE; }
    [^]                             { return RBladeTypes.RUBY_TEMPLATE; }
}


[^]                                 { return TokenType.BAD_CHARACTER; }
