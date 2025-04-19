package com.mwnciau.rblade.psi

import com.intellij.psi.templateLanguages.OuterLanguageElementImpl
import com.intellij.psi.tree.IElementType

class RBladeOuterLanguageElement(type: IElementType, text: CharSequence) : OuterLanguageElementImpl(type, text) {
  override fun toString(): String {
    return "Outer: $elementType, characters in RBlade or Ruby"
  }
}