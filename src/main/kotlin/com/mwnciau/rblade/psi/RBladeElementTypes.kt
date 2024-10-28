package com.mwnciau.rblade.psi

import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.templateLanguages.TemplateDataElementType
import com.intellij.psi.tree.IStubFileElementType
import com.mwnciau.rblade.RBladeLanguage
import com.mwnciau.rblade.psi.impl.RubyCodeInRBladeType
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage

class RBladeElementTypes {
  companion object {
    val TEMPLATE_DATA = TemplateDataElementType("RBLADE_TEMPLATE_DATA", RubyLanguage.INSTANCE, RBladeTypes.HTML_TEMPLATE, RBladeTypes.RBLADE_INJECTION_IN_HTML)
    val RBLADE_FILE = IStubFileElementType<PsiFileStub<RBladeFile>>("RBLADE_FILE", RBladeLanguage.INSTANCE)
    val RUBY_CODE_IN_RBLADE_ROOT = RubyCodeInRBladeType("RUBY_CODE_IN_RBLADE_ROOT")
  }
}