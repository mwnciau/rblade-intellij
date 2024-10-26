package com.mwnciau.rblade.lexer

import com.intellij.lexer.MergingLexerAdapter
import com.intellij.psi.tree.TokenSet
import com.mwnciau.rblade.psi.RBladeTokenType
import com.mwnciau.rblade.psi.RBladeTypes

class RBladeRubyLexer(highlightingMode: Boolean, private val initialState: Int = 0) : MergingLexerAdapter(_RBladeRubyLexer(highlightingMode), TOKENS_TO_MERGE) {
  companion object {
    private val TOKENS_TO_MERGE = TokenSet.create(RBladeTypes.RBLADE_INJECTION_IN_RUBY)
  }

  override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
    val state = if (initialState == 0) {
      this.initialState
    } else {
      initialState
    }

    super.start(buffer, startOffset, endOffset, state)
  }
}