package com.mwnciau.rblade

import com.intellij.codeInsight.template.TemplateActionContext
import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.mwnciau.rblade.highlighter.RBladeSyntaxHighlighter
import com.mwnciau.rblade.psi.RBladeFile

class RBladeLiveTemplateContextType : TemplateContextType("RBlade") {
  override fun isInContext(templateActionContext: TemplateActionContext): Boolean {
    return templateActionContext.getFile() is RBladeFile
  }

  override fun createHighlighter(): SyntaxHighlighter? {
    return RBladeSyntaxHighlighter()
  }
}