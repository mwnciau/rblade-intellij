package com.mwnciau.rblade.parser

import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType
import org.jetbrains.plugins.ruby.ruby.lang.parser.RubyParser
import org.jetbrains.plugins.ruby.ruby.lang.parser.parsingUtils.RBuilder
import org.jetbrains.plugins.ruby.ruby.sdk.LanguageLevel

class RBladeRubyParser(private val languageLevel: LanguageLevel) : RubyParser(languageLevel), PsiParser {
  override fun createBuilder(builder: PsiBuilder, elementType: IElementType?): RBuilder? {
    return RBladeRubyBuilder(builder, languageLevel)
  }
}