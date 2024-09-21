package com.mwnciau.rblade

import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.psi.PsiFile
import com.mwnciau.rblade.highlighter.RBladeSyntaxHighlighter
import com.mwnciau.rblade.psi.RBladeFile

class RBladeLiveTemplateContextType : TemplateContextType("RBlade") {
  override fun isInContext(file : PsiFile, offset : Int): Boolean {
    return file is RBladeFile
  }

  override fun createHighlighter(): SyntaxHighlighter? {
    return RBladeSyntaxHighlighter()
  }
}