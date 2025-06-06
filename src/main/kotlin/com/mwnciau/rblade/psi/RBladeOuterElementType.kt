package com.mwnciau.rblade.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.tree.OuterLanguageElementType
import com.mwnciau.rblade.RBladeLanguage
import org.jetbrains.plugins.ruby.ruby.lang.psi.PresentableElementType

open class RBladeOuterElementType @JvmOverloads constructor(debugName: String, private val presentableName: String = debugName) : OuterLanguageElementType(debugName, RBladeLanguage.INSTANCE), PresentableElementType {
  override fun getPresentableName(): String {
    return presentableName
  }

  override fun createLeafNode(charSequence: CharSequence): ASTNode {
    return RBladeOuterLanguageElement(this, charSequence)
  }
}