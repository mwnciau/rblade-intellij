package com.mwnciau.rblade.parser

import com.intellij.lang.PsiBuilder
import com.intellij.psi.tree.IElementType
import com.mwnciau.rblade.psi.RBladeElementType
import com.mwnciau.rblade.psi.RBladeOuterElementType
import org.jetbrains.plugins.ruby.RBundle
import org.jetbrains.plugins.ruby.ruby.lang.parser.parsingUtils.RBuilderImpl
import org.jetbrains.plugins.ruby.ruby.sdk.LanguageLevel

class RBladeRubyBuilder(psiBuilder: PsiBuilder, languageLevel: LanguageLevel) : RBuilderImpl(psiBuilder, languageLevel) {
  override fun error(error: String) {
    super.error(RBundle.message("parsing.error.ruby", error))
  }

  override fun isAcceptableErrorToken(tokenType: IElementType?): Boolean {
    return tokenType is RBladeElementType
      || tokenType is RBladeOuterElementType
      || super.isAcceptableErrorToken(tokenType)
  }
}