package com.mwnciau.rblade.psi;

import com.intellij.lang.ASTNode
import com.intellij.psi.templateLanguages.OuterLanguageElementImpl
import com.intellij.psi.tree.IElementType

interface RBladeTypesFactory {
  companion object {
    @JvmStatic
    fun createElement(elementName: String): RBladeElementType
    {
      if (elementName == "STATEMENT") {
        return RBladeElementType("STATEMENT")
      }

      throw AssertionError ("Unknown element type: $elementName");
    }

    @JvmStatic
    fun createToken(tokenName: String): IElementType {
      return when (tokenName) {
        "COMMENT" -> RBladeTokenType("COMMENT")
        "HTML_TEMPLATE" -> RBladeOuterElementType("HTML_TEMPLATE")
        "RBLADE_STATEMENT" -> RBladeTokenType("RBLADE_STATEMENT")
        "RUBY_EXPRESSION" -> RBladeOuterElementType("RUBY_EXPRESSION")
        "RBLADE_INJECTION_IN_HTML" -> object : RBladeOuterElementType("RBLADE_INJECTION_IN_HTML") {
          override fun createLeafNode(charSequence: CharSequence): ASTNode {
            return OuterLanguageElementImpl (this, charSequence)
          }
        }
        "RBLADE_INJECTION_IN_RUBY" -> RBladeOuterElementType("RBLADE_INJECTION_IN_RUBY")
        else -> throw Error("Unknown token type: $tokenName")
      }
    }
  }
}
