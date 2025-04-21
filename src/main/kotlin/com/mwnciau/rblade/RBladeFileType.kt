package com.mwnciau.rblade

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

class RBladeFileType private constructor() : LanguageFileType(RBladeLanguage.INSTANCE) {
    companion object {
        val INSTANCE = RBladeFileType()
    }

    override fun getName(): String {
        return "RBlade"
    }

    override fun getDescription(): String {
        return "RBlade Template File"
    }

    override fun getDefaultExtension(): String {
        return "rblade"
    }

    override fun getIcon(): Icon {
        return RBladeIcons.RBlade
    }
}

