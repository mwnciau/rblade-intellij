package com.mwnciau.rblade

import com.intellij.lang.Language
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.mwnciau.rblade.psi.RBladeFile
import com.mwnciau.rblade.psi.RBladeTypes
import org.jetbrains.plugins.ruby.ruby.lang.lexer.RubyTokenTypes
import org.jetbrains.plugins.ruby.ruby.lang.psi.controlStructures.blocks.RCompoundStatement
import org.jetbrains.plugins.ruby.ruby.lang.psi.expressions.RExpression
import org.jetbrains.plugins.ruby.templates.TemplateIntegration
import org.jetbrains.plugins.ruby.templates.TemplateIntegrationUtils

class RubyRBladeTemplateIntegration : TemplateIntegration {
  override fun getOuterElements(): MutableCollection<out IElementType> {
    return mutableListOf(RBladeTypes.RBLADE_INJECTION_IN_RUBY)
  }

  override fun getContinuationElements(): MutableCollection<out IElementType> {
    return mutableListOf()
  }

  override fun getEndElements(): MutableCollection<out IElementType> {
    return mutableListOf(RubyTokenTypes.kEND)
  }

  override fun isTemplateFile(file: PsiFile?): Boolean {
    return file is RBladeFile
  }

  override fun canModifyPsiInsert(viewProvider: FileViewProvider): Boolean {
    return viewProvider.getPsi(RBladeLanguage.INSTANCE) != null
  }

  override fun addRubyStatement(
    viewProvider: FileViewProvider,
    parent: RCompoundStatement,
    elementToAdd: PsiElement,
    anchor: PsiElement?,
    before: Boolean
  ): PsiElement? {
    return null
  }

  override fun getFileType(lang: TemplateIntegrationUtils.TemplateLang): FileType? {
    return if (lang == this.templateLanguage) {
      this.fileType
    } else {
      null
    }
  }

  override fun getFileType(): FileType {
    return RBladeFileType.INSTANCE
  }

  override fun getTemplateLanguage(): TemplateIntegrationUtils.TemplateLang {
    return TemplateIntegrationUtils.TemplateLang.HAML
  }

  override fun getLanguage(): Language {
    return RBladeLanguage.INSTANCE
  }

  override fun getTextForI18n(editor: Editor, file: PsiFile): String? {
    return null
  }

  override fun replaceTextWithI18n(p0: Editor, p1: PsiFile, p2: RExpression): Boolean {
    return false
  }

  override fun mustCloseRubyBlock(): Boolean {
    return false
  }
}