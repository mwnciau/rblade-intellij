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
import com.mwnciau.rblade.psi.RBladePropName
import com.mwnciau.rblade.psi.impl.RBladeRubyFileImpl
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
    rbladeVars.map { name -> decs.put(name, createPsiElement(parent, name)) }

    val rbladeFile = viewProvider.getRBladeFile()

    val properties = rbladeFile.descendantsOfType<RBladePropName>()
    properties.forEach {
      prop -> decs.put(prop.text, createPsiElement(prop, prop.text))
    }

    return decs
  }

  fun createPsiElement(parent: PsiElement, name: String) : PsiElement {
    return RBladeFakePSIVariable(parent, name, 0, parent.containingFile.viewProvider.getPsi(RubyLanguage.INSTANCE) as RFile)
  }

  private class RBladeFakePSIVariable(private val parent: PsiElement, private val name: String, val offsetInParent: Int, val containingFile: RFile) : FakePsiElement(), RubySearchableFakePsiElement, RPsiElement, PsiMetaOwner, PsiPresentableMetaData {
    override fun getParent() : PsiElement {
      return parent
    }

    override fun getUseScope() : SearchScope {
      return LocalSearchScope(containingFile)
    }

    override fun getStartOffsetInParent() : Int {
      return offsetInParent
    }

    override fun getTextOffset() : Int {
      return parent.textOffset + offsetInParent
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
      return parent !is RBladeRubyFileImpl
    }

    override fun getLanguage() : Language {
      return RubyLanguage.INSTANCE
    }

    override fun equals(other: Any?): Boolean {
      if (this == other) {
          return true;
      } else if (other != null && other is RBladeFakePSIVariable) {
          return parent.equals(other.parent) && name.equals(other.name) && offsetInParent == other.offsetInParent;
      } else {
          return false;
      }
    }

    override fun hashCode(): Int {
      return Objects.hash(parent, name, offsetInParent)
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
