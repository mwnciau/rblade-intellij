package com.mwnciau.rblade.rails

import com.intellij.lang.Language
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import org.jetbrains.plugins.ruby.ruby.lang.psi.controlStructures.blocks.RCompoundStatement
import org.jetbrains.plugins.ruby.ruby.lang.psi.expressions.RExpression
import org.jetbrains.plugins.ruby.templates.TemplateIntegration
import org.jetbrains.plugins.ruby.templates.TemplateIntegrationUtils

class RubyRBladeIntegrationImpl : TemplateIntegration {
  override fun getOuterElements(): MutableCollection<out IElementType> {
    TODO("Not yet implemented")
  }

  override fun getContinuationElements(): MutableCollection<out IElementType> {
    TODO("Not yet implemented")
  }

  override fun getEndElements(): MutableCollection<out IElementType> {
    TODO("Not yet implemented")
  }

  override fun isTemplateFile(p0: PsiFile?): Boolean {
    TODO("Not yet implemented")
  }

  override fun canModifyPsiInsert(p0: FileViewProvider): Boolean {
    TODO("Not yet implemented")
  }

  override fun addRubyStatement(
    p0: FileViewProvider,
    p1: RCompoundStatement,
    p2: PsiElement,
    p3: PsiElement?,
    p4: Boolean
  ): PsiElement? {
    TODO("Not yet implemented")
  }

  override fun getFileType(p0: TemplateIntegrationUtils.TemplateLang): FileType? {
    TODO("Not yet implemented")
  }

  override fun getFileType(): FileType {
    TODO("Not yet implemented")
  }

  override fun getTemplateLanguage(): TemplateIntegrationUtils.TemplateLang {
    TODO("Not yet implemented")
  }

  override fun getLanguage(): Language {
    TODO("Not yet implemented")
  }

  override fun getTextForI18n(p0: Editor, p1: PsiFile): String? {
    TODO("Not yet implemented")
  }

  override fun replaceTextWithI18n(p0: Editor, p1: PsiFile, p2: RExpression): Boolean {
    TODO("Not yet implemented")
  }

  override fun mustCloseRubyBlock(): Boolean {
    TODO("Not yet implemented")
  }
}