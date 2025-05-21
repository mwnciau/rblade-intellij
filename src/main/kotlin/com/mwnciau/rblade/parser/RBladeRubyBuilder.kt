package com.mwnciau.rblade.parser

import com.intellij.lang.PsiBuilder
import com.intellij.psi.tree.IElementType
import com.mwnciau.rblade.psi.RBladeOuterElementType
import com.mwnciau.rblade.psi.RBladeTokenType
import org.jetbrains.plugins.ruby.ruby.lang.parser.parsingUtils.RBuilderImpl
import org.jetbrains.plugins.ruby.ruby.sdk.LanguageLevel

class RBladeRubyBuilder(psiBuilder: PsiBuilder, languageLevel: LanguageLevel) : RBuilderImpl(psiBuilder, languageLevel) {
  override fun error(error: String) {
    super.error("[RBLADE RUBY]" + error)
  }

  override fun isAcceptableErrorToken(tokenType: IElementType?): Boolean {
    return tokenType is RBladeTokenType
      || tokenType is RBladeOuterElementType
      || super.isAcceptableErrorToken(tokenType)
  }
}
