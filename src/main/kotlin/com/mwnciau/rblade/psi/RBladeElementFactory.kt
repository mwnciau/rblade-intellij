package com.mwnciau.rblade.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.impl.PsiFileFactoryImpl
import com.intellij.testFramework.LightVirtualFile
import com.mwnciau.rblade.RBladeLanguage

class RBladeElementFactory {
  companion object {
    fun createRBladeFile(project: Project, text: String): PsiFile? {
      val virtualFile = LightVirtualFile("dummy.rblade", RBladeLanguage.INSTANCE, text)
      return (PsiFileFactory.getInstance(project) as PsiFileFactoryImpl).trySetupPsiForFile(virtualFile, RBladeLanguage.INSTANCE, false, true)
    }
  }
}