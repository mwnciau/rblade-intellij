package com.mwnciau.rblade.psi.impl

import RBladeElementTypes
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.impl.source.PsiFileImpl
import com.mwnciau.rblade.RBladeFileType
import com.mwnciau.rblade.RBladeFileViewProvider
import com.mwnciau.rblade.psi.RBladeFile
import com.mwnciau.rblade.psi.RBladeRubyFile
import org.jetbrains.plugins.ruby.ruby.codeInsight.resolve.scope.RControlFlow
import org.jetbrains.plugins.ruby.ruby.codeInsight.resolve.scope.Scope
import org.jetbrains.plugins.ruby.ruby.codeInsight.symbols.Type
import org.jetbrains.plugins.ruby.ruby.codeInsight.symbols.fqn.FQN
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage
import org.jetbrains.plugins.ruby.ruby.lang.psi.RFile
import org.jetbrains.plugins.ruby.ruby.lang.psi.RPsiElement
import org.jetbrains.plugins.ruby.ruby.lang.psi.RPsiStructureElement
import org.jetbrains.plugins.ruby.ruby.lang.psi.controlStructures.blocks.RCompoundStatement
import org.jetbrains.plugins.ruby.ruby.lang.psi.holders.RContainer
import org.jetbrains.plugins.ruby.ruby.lang.psi.holders.RequireInfo
import org.jetbrains.plugins.ruby.ruby.lang.psi.impl.RPsiFileBase
import org.jetbrains.plugins.ruby.ruby.lang.psi.variables.RConstant
import org.jetbrains.plugins.ruby.ruby.lang.psi.variables.RGlobalVariable
import org.jetbrains.plugins.ruby.ruby.lang.psi.variables.fields.RField
import org.jetbrains.plugins.ruby.ruby.lang.psi.visitors.RubyElementVisitor
import org.jetbrains.plugins.ruby.ruby.sdk.LanguageLevel
import org.jetbrains.plugins.ruby.utils.VirtualFileUtil

class RBladeFileImpl(viewProvider: FileViewProvider) :
  PsiFileImpl(RBladeElementTypes.RBLADE_FILE, RBladeElementTypes.RBLADE_FILE, viewProvider),
  RBladeFile
{
  override fun toString(): String {
    return "RBladeFile:${this.name}"
  }

  override fun accept(visitor: PsiElementVisitor) {
    if (visitor is RubyElementVisitor) {
      visitor.visitRFile(this)
    } else {
      visitor.visitFile(this)
    }
  }

  override fun getFileType(): FileType {
    return RBladeFileType.INSTANCE
  }

  override fun getInnerRubyFile(): RFile {
    return viewProvider.getPsi(RubyLanguage.INSTANCE) as RFile
  }

  override fun getCompoundStatement(): RCompoundStatement {
    return getInnerRubyFile().compoundStatement
  }

  override fun getStatements(): MutableList<RPsiElement> {
    return getInnerRubyFile().statements
  }

  override fun getParentContainer(): RContainer? {
    return getInnerRubyFile().parentContainer
  }

  override fun getPresentableLocation(): String? {
    return getInnerRubyFile().presentableLocation
  }

  override fun getStructureElements(): MutableList<RPsiStructureElement> {
    return getInnerRubyFile().structureElements
  }

  override fun getRequires(): MutableList<RequireInfo> {
    return getInnerRubyFile().requires
  }

  override fun checkHashes(): Boolean {
    return true
  }

  override fun getLoadPath(): MutableCollection<VirtualFile> {
    return getInnerRubyFile().loadPath
  }

  override fun getPragmas(): MutableMap<String, String> {
    return mutableMapOf()
  }

  override fun getLanguageLevel(): LanguageLevel? {
    return getInnerRubyFile().languageLevel
  }

  override fun getFieldsDeclarations(): MutableList<RField> {
    return getInnerRubyFile().fieldsDeclarations
  }

  override fun getConstantDeclarations(): MutableList<RConstant> {
    return getInnerRubyFile().constantDeclarations
  }

  override fun getGlobalVarDefinitions(): MutableList<RGlobalVariable> {
    return getInnerRubyFile().globalVarDefinitions
  }

  override fun getScope(): Scope {
    return getInnerRubyFile().scope
  }

  override fun getControlFlow(): RControlFlow {
    return getInnerRubyFile().controlFlow
  }

  override fun getFQN(): FQN {
    return getInnerRubyFile().fqn
  }

  override fun getFQNWithNesting(): FQN {
    return getInnerRubyFile().fqnWithNesting
  }

  override fun getSymbolType(): Type {
    return getInnerRubyFile().symbolType
  }
}
