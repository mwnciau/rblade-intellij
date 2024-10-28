package com.mwnciau.rblade.psi.impl

import com.mwnciau.rblade.psi.RBladeElementTypes
import com.intellij.psi.FileViewProvider
import com.mwnciau.rblade.RBladeFileViewProvider
import com.mwnciau.rblade.psi.RBladeRubyFile
import org.jetbrains.plugins.ruby.ruby.lang.psi.impl.RPsiFileBase
import org.jetbrains.plugins.ruby.utils.VirtualFileUtil

class RBladeRubyFileImpl(viewProvider: FileViewProvider) :
  RPsiFileBase(RBladeElementTypes.RUBY_CODE_IN_RBLADE_ROOT, RBladeElementTypes.RUBY_CODE_IN_RBLADE_ROOT, viewProvider),
  RBladeRubyFile
{
  override fun getViewProvider(): RBladeFileViewProvider {
    return super.getViewProvider() as RBladeFileViewProvider
  }

  override fun toString(): String {
    return "RBladeRubyFile:${this.name}"
  }

  override fun getPragmas(): MutableMap<String, String> {
    return mutableMapOf()
  }

  override fun getPresentableLocation(): String? {
    return VirtualFileUtil.getRelativePath(virtualFile, project);
  }
}
