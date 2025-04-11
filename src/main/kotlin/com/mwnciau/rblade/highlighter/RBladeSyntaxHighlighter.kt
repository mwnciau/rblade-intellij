package com.mwnciau.rblade.highlighter

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.mwnciau.rblade.RBladeMergingLexer
import com.mwnciau.rblade.psi.RBladeTypes

class RBladeSyntaxHighlighter : SyntaxHighlighterBase(), SyntaxHighlighter {
    companion object {
        private val COMMENT: TextAttributesKey =
            createTextAttributesKey("RBLADE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        private val BAD_CHARACTER: TextAttributesKey =
            createTextAttributesKey("RBLADE_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
        private val STATEMENT: TextAttributesKey =
            createTextAttributesKey("RBLADE_STATEMENT", DefaultLanguageHighlighterColors.CONSTANT)
        private val SEPARATOR: TextAttributesKey =
            createTextAttributesKey("RBLADE_SEPARATOR", DefaultLanguageHighlighterColors.COMMA)
        private val KEYWORD: TextAttributesKey =
            createTextAttributesKey("RBLADE_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)

        private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
        private val COMMENT_KEYS = arrayOf(COMMENT)
        private val STATEMENT_KEYS = arrayOf(STATEMENT)
        private val SEPARATOR_KEYS = arrayOf(SEPARATOR)
        private val KEYWORD_KEYS = arrayOf(KEYWORD)
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }

    override fun getHighlightingLexer(): Lexer {
        return RBladeMergingLexer()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when(tokenType) {
            TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
            RBladeTypes.COMMENT -> COMMENT_KEYS
            RBladeTypes.RBLADE_STATEMENT -> STATEMENT_KEYS
            RBladeTypes.RBLADE_STATEMENT_COMMA -> SEPARATOR_KEYS
            RBladeTypes.RBLADE_STATEMENT_EACH_IN -> KEYWORD_KEYS
            RBladeTypes.RBLADE_STATEMENT_PROPS_NAME -> KEYWORD_KEYS
            else -> EMPTY_KEYS
        }
    }
}

