package com.mwnciau.rblade.psi

import com.intellij.psi.tree.TokenSet

interface RBladeTokenSets {
    companion object {
        val COMMENTS: TokenSet = TokenSet.create(RBladeTypes.COMMENT)
    }
}