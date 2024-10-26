package com.mwnciau.rblade

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.MergingLexerAdapter
import com.intellij.psi.tree.TokenSet
import com.mwnciau.rblade.psi.RBladeTypes
import java.io.Reader

class RBladeMergingLexer : MergingLexerAdapter(FlexAdapter(RBladeLexer(null as Reader?)), TOKENS_TO_MERGE) {
  companion object {
    val TOKENS_TO_MERGE = TokenSet.create(RBladeTypes.HTML_TEMPLATE)
  }
}