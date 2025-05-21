package com.mwnciau.rblade.parser

import com.intellij.psi.tree.IElementType
import com.mwnciau.rblade.psi.RBladeTypes
import org.jetbrains.plugins.ruby.ruby.lang.lexer.RubyTokenTypes
import org.jetbrains.plugins.ruby.templates.TemplateElementsProvider

class RBladeRubyElementsProvider : TemplateElementsProvider {
  override fun getOuterElements(): Collection<IElementType> {
    return listOf(RBladeTypes.RBLADE_INJECTION_IN_RUBY, RBladeTypes.RBLADE_INJECTION_IN_HTML)
  }

  override fun getContinuationElements(): Collection<IElementType> {
    return listOf(RubyTokenTypes.tWHITE_SPACE, RubyTokenTypes.tWHITE_SPACE_WITH_NEWLINE, RubyTokenTypes.tLINE_CONTINUATION)
  }

  override fun getEndElements(): Collection<IElementType> {
    return listOf(RubyTokenTypes.kEND)
  }
}