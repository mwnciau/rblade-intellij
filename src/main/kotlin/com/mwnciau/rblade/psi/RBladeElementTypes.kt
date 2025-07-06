package com.mwnciau.rblade.psi

import com.intellij.psi.templateLanguages.TemplateDataElementType
import com.intellij.psi.tree.IElementType
import com.mwnciau.rblade.RBladeLanguage

class RBladeElementTypes {
  companion object {
    val TEMPLATE_DATA = object : TemplateDataElementType("RBLADE_TEMPLATE_DATA", RBladeLanguage.INSTANCE, RBladeTypes.HTML_TEMPLATE, RBladeTypes.RBLADE_INJECTION_IN_HTML) {
      override fun isInsertionToken(
        tokenType: IElementType?,
        tokenSequence: CharSequence
      ): Boolean {
        return true
      }
    }

    val RUBY_CODE_IN_RBLADE_ROOT = RubyCodeInRBladeType("RUBY_CODE_IN_RBLADE_ROOT")
  }
}