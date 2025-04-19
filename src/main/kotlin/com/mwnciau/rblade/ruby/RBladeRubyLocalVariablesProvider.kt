package com.mwnciau.rblade.ruby

import com.intellij.lang.Language
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.FakePsiElement
import com.intellij.psi.meta.PsiMetaData
import com.intellij.psi.meta.PsiMetaOwner
import com.intellij.psi.meta.PsiPresentableMetaData
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.LocalSearchScope
import com.intellij.psi.search.SearchScope
import com.intellij.psi.util.descendantsOfType
import com.mwnciau.rblade.RBladeFileViewProvider
import com.mwnciau.rblade.RBladeIcons
import com.mwnciau.rblade.psi.RBladeElementFactory
import com.mwnciau.rblade.psi.RBladePropName
import com.mwnciau.rblade.stubs.RBladePropNameStub
import org.jetbrains.annotations.NonNls
import org.jetbrains.plugins.ruby.ruby.codeInsight.RubyLocalVariablesProvider
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage
import org.jetbrains.plugins.ruby.ruby.lang.findUsages.RubySearchableFakePsiElement
import org.jetbrains.plugins.ruby.ruby.lang.psi.RFile
import org.jetbrains.plugins.ruby.ruby.lang.psi.RPsiElement
import org.jetbrains.plugins.ruby.ruby.lang.psi.holders.RContainer
import org.jetbrains.plugins.ruby.ruby.sdk.LanguageLevel
import java.util.Objects
import javax.swing.Icon

class RBladeRubyLocalVariablesProvider : RubyLocalVariablesProvider {
  val rbladeVars = listOf(
    "attributes",
    "slot",
  )

  override fun collectDeclarations(parent: PsiElement): Map<String?, PsiElement?> {
    val viewProvider = parent.containingFile.viewProvider as? RBladeFileViewProvider ?: return mapOf()

    val decs = mutableMapOf<String?, PsiElement?>()
    rbladeVars.map { name -> decs.put(name, createPsiElement(parent.containingFile as RFile, name)) }

    val properties = viewProvider.getRBladeFile().stub?.childrenStubs

    properties?.forEach {
      prop ->
      if (prop is RBladePropNameStub) {
        decs.put(prop.key, createPsiElement(parent.containingFile as RFile, prop.key, prop.textOffset))
      }
    }

    return decs
  }

  fun createPsiElement(rubyFile: RFile, name: String, textOffset: Int = 0) : PsiElement {
    return RBladeFakePSIVariable(rubyFile, name, textOffset)
  }

  private class RBladeFakePSIVariable(private val containingFile: RFile, private val name: String, private val textOffset: Int) : FakePsiElement(), RubySearchableFakePsiElement, RPsiElement, PsiMetaOwner, PsiPresentableMetaData {
    override fun getParent() : PsiElement {
      return containingFile
    }

    override fun getUseScope() : SearchScope {
      return LocalSearchScope(containingFile)
    }

    override fun getStartOffsetInParent() : Int {
      return 0
    }

    override fun getTextOffset() : Int {
      return textOffset
    }

    override fun getTextRange() : TextRange {
      return TextRange.create(textOffset, textOffset + textLength);
    }

    override fun getText() : String {
      return name
    }

    override fun getDeclaration() : PsiElement {
      return this
    }

    override fun getName(context : PsiElement?) : String {
      return name
    }

    override fun getName() : String {
      return name
    }

    override fun init(element : PsiElement) {
    }

    override fun getTextLength() : Int {
      return name.length
    }

    override fun isReadWriteAccessible() : Boolean {
      // This will always be 0 for slot/attributes and > 0 for props
      return textOffset > 0
    }

    override fun getLanguage() : Language {
      return RubyLanguage.INSTANCE
    }

    override fun setName(newName: @NonNls String): PsiElement? {
      val viewProvider = parent.containingFile.viewProvider as RBladeFileViewProvider
      val rbladeFile = viewProvider.getRBladeFile()

      val dummyFile = RBladeElementFactory.createRBladeFile(project, "@props($newName: 0)")
      val newPsiElement = dummyFile?.descendantsOfType<RBladePropName>()?.first()

      if (newPsiElement != null) {
        val originalPsiElement = rbladeFile.descendantsOfType<RBladePropName>().first { psiElement ->
          psiElement.text == name
        }

        originalPsiElement.parent.addBefore(newPsiElement, originalPsiElement)
        originalPsiElement.delete()
      }

      return this
    }

    override fun equals(other: Any?): Boolean {
      if (this == other) {
          return true;
      } else if (other != null && other is RBladeFakePSIVariable) {
          return parent.equals(other.parent) && name.equals(other.name) && textOffset == other.textOffset
      } else {
          return false;
      }
    }

    override fun hashCode(): Int {
      return Objects.hash(parent, name, 0)
    }

    override fun getParentContainer(): RContainer? {
      return containingFile
    }

    override fun getResolveScope(): GlobalSearchScope {
      return GlobalSearchScope.fileScope(containingFile)
    }

    override fun getLanguageLevel(): LanguageLevel? {
      return containingFile.languageLevel
    }

    override fun getMetaData(): PsiMetaData? {
      return this
    }

    override fun getTypeName(): String? {
      return "rblade.prop"
    }

    override fun getIcon(): Icon? {
      return RBladeIcons.RBlade
    }
  }
}
