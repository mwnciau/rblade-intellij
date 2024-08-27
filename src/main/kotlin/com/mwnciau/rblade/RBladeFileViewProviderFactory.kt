package com.mwnciau.rblade

import com.intellij.lang.Language
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.FileViewProvider
import com.intellij.psi.FileViewProviderFactory
import com.intellij.psi.PsiManager

class RBladeFileViewProviderFactory : FileViewProviderFactory {
  override fun createFileViewProvider(file: VirtualFile, language: Language?, manager: PsiManager, eventSystemEnabled: Boolean): FileViewProvider {
    return RBladeFileViewProvider(manager, file, eventSystemEnabled)
  }
}