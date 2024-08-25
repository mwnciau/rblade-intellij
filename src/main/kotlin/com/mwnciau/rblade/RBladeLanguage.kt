package com.mwnciau.rblade

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.psi.templateLanguages.TemplateLanguage

class RBladeLanguage private constructor() : Language("RBlade"), TemplateLanguage {
    companion object {
        val INSTANCE = RBladeLanguage()
    }

    override fun getAssociatedFileType(): LanguageFileType {
        return RBladeFileType.INSTANCE
    }

    override fun getDisplayName(): String {
        return "RBlade"
    }

    override fun isCaseSensitive(): Boolean {
        return true
    }
}
