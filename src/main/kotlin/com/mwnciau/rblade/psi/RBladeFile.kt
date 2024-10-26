package com.mwnciau.rblade.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.mwnciau.rblade.RBladeFileType
import com.mwnciau.rblade.RBladeLanguage
import org.jetbrains.plugins.ruby.ruby.lang.psi.RFile

interface RBladeFile : RFile {
    fun getInnerRubyFile(): RFile
}