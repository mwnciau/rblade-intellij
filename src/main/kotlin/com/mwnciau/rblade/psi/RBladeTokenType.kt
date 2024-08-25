package com.mwnciau.rblade.psi

import com.intellij.psi.tree.IElementType
import com.mwnciau.rblade.RBladeLanguage

class RBladeTokenType(debugName: String): IElementType(debugName, RBladeLanguage.INSTANCE) {
    override fun toString(): String {
        return "RBladeTokenType.${super.toString()}"
    }
}