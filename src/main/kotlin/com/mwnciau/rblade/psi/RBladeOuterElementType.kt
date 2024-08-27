package com.mwnciau.rblade.psi

import com.intellij.lang.ASTNode
import com.intellij.psi.templateLanguages.OuterLanguageElementImpl
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.ILeafElementType
import com.mwnciau.rblade.RBladeLanguage

class RBladeOuterElementType(debugName: String) : IElementType(debugName, RBladeLanguage.INSTANCE), ILeafElementType {
  override fun createLeafNode(charSequence: CharSequence): ASTNode {
    return OuterLanguageElementImpl(this, charSequence);
  }
}