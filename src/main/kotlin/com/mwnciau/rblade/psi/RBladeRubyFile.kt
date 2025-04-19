package com.mwnciau.rblade.psi

import com.intellij.psi.FileViewProvider
import com.mwnciau.rblade.RBladeFileViewProvider
import org.jetbrains.plugins.ruby.ruby.lang.psi.RFile
import org.jetbrains.plugins.ruby.ruby.lang.psi.impl.RPsiFileBase
import org.jetbrains.plugins.ruby.utils.VirtualFileUtil

class RBladeRubyFile(viewProvider: FileViewProvider) :
  RPsiFileBase(RBladeElementTypes.RUBY_CODE_IN_RBLADE_ROOT, RBladeElementTypes.RUBY_CODE_IN_RBLADE_ROOT, viewProvider),
  RFile
{
  override fun getViewProvider(): RBladeFileViewProvider {
    return super.viewProvider as RBladeFileViewProvider
  }

  override fun toString(): String {
    return "RBladeRubyFile:${this.name}"
  }

  override fun getPragmas(): MutableMap<String, String> {
    return mutableMapOf()
  }

  override fun getPresentableLocation(): String? {
    return VirtualFileUtil.getRelativePath(virtualFile, project)
  }
}

