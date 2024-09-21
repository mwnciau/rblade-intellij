package com.mwnciau.rblade

import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.html.HTMLLanguage
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.source.PsiFileImpl
import com.intellij.psi.templateLanguages.ConfigurableTemplateLanguageFileViewProvider
import com.intellij.psi.tree.IElementType
import com.mwnciau.rblade.psi.*
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage
import java.util.concurrent.ConcurrentHashMap


class RBladeFileViewProvider(
  manager: PsiManager,
  virtualFile: VirtualFile,
  eventSystemEnabled: Boolean
) : MultiplePsiFilesPerDocumentFileViewProvider(manager, virtualFile, eventSystemEnabled),
  ConfigurableTemplateLanguageFileViewProvider {
  companion object {
    val OUTER_RBLADE = RBladeOuterElementType("Outer RBlade")
    val ELEMENT_TYPE_BY_LANGUAGE_ID = ConcurrentHashMap<String, IElementType>();
  }

  override fun getBaseLanguage(): Language {
    return RBladeLanguage.INSTANCE
  }

  override fun getLanguages(): MutableSet<Language> {
    return mutableSetOf(RBladeLanguage.INSTANCE, HTMLLanguage.INSTANCE, RubyLanguage.INSTANCE)
  }

  override fun getTemplateDataLanguage(): Language {
    return HTMLLanguage.INSTANCE
  }

  override fun cloneInner(fileCopy: VirtualFile): MultiplePsiFilesPerDocumentFileViewProvider {
    return RBladeFileViewProvider(manager, fileCopy, false);
  }

  override fun createFile(lang: Language): PsiFile? {
    val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(lang) ?: return null

    if (lang.isKindOf(RBladeLanguage.INSTANCE)) {
      return parserDefinition.createFile(this)
    }

    val psiFileImpl = parserDefinition.createFile(this) as PsiFileImpl
    psiFileImpl.contentElementType = elementType(lang)

    return psiFileImpl
  }

  fun elementType(language : Language) : IElementType {
    return ELEMENT_TYPE_BY_LANGUAGE_ID.computeIfAbsent(
      language.getID(),
      {
        languageID : String ->
          val elementType : IElementType

          if (language == RubyLanguage.INSTANCE) {
              elementType = EmbeddedRubyElementType()
          } else {
              elementType = HtmlElementType();
          }

          elementType
      }
    )
  }

  override fun supportsIncrementalReparse(rootLanguage: Language): Boolean {
    return false
  }
}