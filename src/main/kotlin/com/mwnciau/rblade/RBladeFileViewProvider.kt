package com.mwnciau.rblade

import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.ParserDefinition
import com.intellij.lang.html.HTMLLanguage
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.source.PsiFileImpl
import com.intellij.psi.templateLanguages.TemplateDataElementType
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider
import com.mwnciau.rblade.psi.RBladeFile
import com.mwnciau.rblade.psi.RBladeOuterElementType
import com.mwnciau.rblade.psi.RBladeTypes
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage


class RBladeFileViewProvider(
  manager: PsiManager,
  virtualFile: VirtualFile,
  eventSystemEnabled: Boolean
) : MultiplePsiFilesPerDocumentFileViewProvider(manager, virtualFile, eventSystemEnabled), TemplateLanguageFileViewProvider {
  companion object {
    val OUTER_RBLADE = RBladeOuterElementType("Outer RBlade");
  }

  override fun getBaseLanguage(): Language {
    return RBladeLanguage.INSTANCE
  }

  override fun getLanguages(): MutableSet<Language> {
    return mutableSetOf(RBladeLanguage.INSTANCE, HTMLLanguage.INSTANCE/*, RubyLanguage.INSTANCE*/)
  }

  override fun getTemplateDataLanguage(): Language {
    return HTMLLanguage.INSTANCE
  }

  override fun cloneInner(fileCopy: VirtualFile): MultiplePsiFilesPerDocumentFileViewProvider {
    return RBladeFileViewProvider(manager, fileCopy, false);
  }

  override fun createFile(lang: Language): PsiFile? {
    if (lang === RBladeLanguage.INSTANCE) {
      return RBladeFile(this)
    }

    val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(lang) ?: return null

    val psiFile = parserDefinition.createFile(this)
    if (lang === HTMLLanguage.INSTANCE && psiFile is PsiFileImpl) {
      psiFile.contentElementType = TemplateDataElementType("HTML embedded in RBlade", RBladeLanguage.INSTANCE, RBladeTypes.HTML_TEMPLATE, OUTER_RBLADE)
    }/* else if (lang === RubyLanguage.INSTANCE && psiFile is PsiFileImpl) {
      psiFile.contentElementType = TemplateDataElementType("Ruby embedded in RBlade", RBladeLanguage.INSTANCE, RBladeTypes.RUBY_TEMPLATE, OUTER_RBLADE)
      //return LanguageParserDefinitions.INSTANCE.forLanguage(RubyLanguage.INSTANCE).createFile(this) as PsiFileImpl;
    }*/

    return psiFile
  }
}