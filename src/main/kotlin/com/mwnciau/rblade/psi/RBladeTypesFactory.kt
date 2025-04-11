package com.mwnciau.rblade.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.templateLanguages.OuterLanguageElementImpl
import com.intellij.psi.tree.IElementType

interface RBladeTypesFactory {
  companion object {
    @JvmStatic
    fun createElement(elementName: String): RBladeElementType
    {
      return when (elementName) {
        "PROPS" -> RBladeElementType("PROPS")
        "PROP_NAME" -> RBladeElementType("PROP_NAME")
        "STATEMENT" -> RBladeElementType("STATEMENT")
        "STATEMENT_PARAMETERS" -> RBladeElementType("STATEMENT_PARAMETERS")
        else -> throw AssertionError ("Unknown element type: $elementName")
      }
    }

    @JvmStatic
    fun createToken(tokenName: String): IElementType {
      return when (tokenName) {
        "COMMENT" -> RBladeTokenType("COMMENT")
        "HTML_TEMPLATE" -> RBladeOuterElementType("HTML_TEMPLATE")
        "RBLADE_STATEMENT" -> RBladeTokenType("RBLADE_STATEMENT")
        "RBLADE_STATEMENT_COMMA" -> RBladeTokenType("RBLADE_STATEMENT_COMMA")
        "RBLADE_STATEMENT_EACH_IN" -> RBladeTokenType("RBLADE_STATEMENT_EACH_IN")
        "RBLADE_STATEMENT_PROPS_NAME" -> RBladeTokenType("RBLADE_STATEMENT_PROP_NAME")
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
