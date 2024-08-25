package com.mwnciau.rblade.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.mwnciau.rblade.RBladeFileType
import com.mwnciau.rblade.RBladeLanguage

class RBladeFile(viewProvider: FileViewProvider): PsiFileBase(viewProvider, RBladeLanguage.INSTANCE) {
    override fun getFileType(): FileType {
        return RBladeFileType.INSTANCE
    }

    override fun toString(): String {
        return "RBlade File"
    }
}