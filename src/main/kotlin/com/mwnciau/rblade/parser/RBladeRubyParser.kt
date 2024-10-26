package com.mwnciau.rblade.parser

import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import org.jetbrains.plugins.ruby.ruby.lang.parser.RubyParser
import org.jetbrains.plugins.ruby.ruby.lang.parser.parsingUtils.RBuilderImpl
import org.jetbrains.plugins.ruby.ruby.sdk.LanguageLevel

class RBladeRubyParser(private val languageLevel: LanguageLevel) : RubyParser(languageLevel), PsiParser {
  override fun createBuilder(builder: PsiBuilder): RBuilderImpl {
    return RBladeRubyBuilder(builder, languageLevel)
  }
}