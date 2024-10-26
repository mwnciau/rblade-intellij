package com.mwnciau.rblade.psi.impl

import com.intellij.psi.templateLanguages.OuterLanguageElementImpl
import com.intellij.psi.tree.IElementType

class RBladeOuterLanguageElementImpl(type: IElementType, text: CharSequence) : OuterLanguageElementImpl(type, text), RBladeOuterLanguageElement {
  override fun toString(): String {
    return "Outer: $elementType, characters in RBlade or Ruby"
  }
}