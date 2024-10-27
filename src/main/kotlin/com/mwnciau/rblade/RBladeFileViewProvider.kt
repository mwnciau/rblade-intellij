package com.mwnciau.rblade

import RBladeElementTypes
import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.html.HTMLLanguage
import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.fileTypes.PlainTextLanguage
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.LanguageSubstitutors
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.source.PsiFileImpl
import com.intellij.psi.templateLanguages.TemplateLanguage
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.ILightStubFileElementType
import com.mwnciau.rblade.psi.impl.RBladeRubyFileImpl
import com.mwnciau.rblade.ruby.RBladeRubyLanguage
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage


class RBladeFileViewProvider(
  manager: PsiManager,
  virtualFile: VirtualFile,
  eventSystemEnabled: Boolean
) : MultiplePsiFilesPerDocumentFileViewProvider(manager, virtualFile, eventSystemEnabled),
  TemplateLanguageFileViewProvider {
  companion object {
    private val LOG = Logger.getInstance(RBladeFileViewProvider::class.java)
  }

  private val templateDataLanguage: Language = computeTemplateDataLanguage()

  override fun getBaseLanguage(): Language {
    return RBladeLanguage.INSTANCE
  }

  override fun getTemplateDataLanguage(): Language {
    return templateDataLanguage
  }

  override fun getLanguages(): MutableSet<Language> {
    return mutableSetOf(RBladeLanguage.INSTANCE, RubyLanguage.INSTANCE, templateDataLanguage)
  }

  override fun getContentElementType(language: Language): IElementType? {
    return when (language) {
      RubyLanguage.INSTANCE -> {
        RBladeElementTypes.RUBY_CODE_IN_RBLADE_ROOT
      }

      templateDataLanguage -> {
        RBladeElementTypes.TEMPLATE_DATA
      }

      else -> {
        null
      }
    }
  }

  override fun cloneInner(fileCopy: VirtualFile): MultiplePsiFilesPerDocumentFileViewProvider {
    return RBladeFileViewProvider(manager, fileCopy, false);
  }

  override fun createFile(lang: Language): PsiFile? {
    return when (lang) {
      RubyLanguage.INSTANCE -> {
        RBladeRubyFileImpl(this)
      }
      templateDataLanguage -> {
        val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(lang)
        val file = parserDefinition.createFile(this) as PsiFileImpl
        if (file.contentElementType !is ILightStubFileElementType<*>) {
          file.contentElementType = RBladeElementTypes.TEMPLATE_DATA
        }

        file
      }
      RBladeLanguage.INSTANCE -> {
        val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(lang)

        parserDefinition.createFile(this)
      }
      else -> {
        null
      }
    }
  }

  override fun getStubBindingRoot(): PsiFile {
    return this.getPsi(RubyLanguage.INSTANCE) as PsiFile
  }

  private fun computeTemplateDataLanguage(): Language {
    var name = virtualFile.name
    if (name.endsWith(".rblade")) {
      name = name.substring(0, name.length - 7)
    }

    val dotIndex = name.lastIndexOf(".")
    if (dotIndex < 0) {
      return HTMLLanguage.INSTANCE
    }

    val templateLanguage = this.getLanguageByExtension(name.substring(dotIndex + 1))
    if (templateLanguage !is TemplateLanguage) {
      if (LanguageParserDefinitions.INSTANCE.forLanguage(templateLanguage) != null) {
        return templateLanguage
      }

      LOG.warn("No parser definition found for [$templateLanguage], in file $name falling back to plain text");

      return PlainTextLanguage.INSTANCE
    }

    return templateLanguage
  }

  private fun getLanguageByExtension(ext: String): Language {
    val fileType = FileTypeManager.getInstance().getFileTypeByExtension(ext)
    if (fileType !is LanguageFileType) {
      return HTMLLanguage.INSTANCE;
    }

    val language = fileType.language

    return if (language is RubyLanguage) {
      RBladeRubyLanguage.INSTANCE
    } else if (ext.equals("plist", true)) {
      XMLLanguage.INSTANCE
    } else {
      val substitutedLanguage = LanguageSubstitutors
        .getInstance()
        .substituteLanguage(language, this.virtualFile, this.manager.project)

      if (substitutedLanguage != RBladeLanguage.INSTANCE) {
        substitutedLanguage
      } else if (language != RBladeLanguage.INSTANCE) {
        language
      } else {
        HTMLLanguage.INSTANCE
      }
    }
  }
}