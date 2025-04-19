package com.mwnciau.rblade.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lexer.Lexer
import com.intellij.psi.PsiFile
import com.mwnciau.rblade.lexer.RBladeRubyLexer
import com.mwnciau.rblade.parser.RBladeRubyBuilder
import com.mwnciau.rblade.parser.RBladeRubyParser
import org.jetbrains.plugins.ruby.ruby.lang.parser.RubyParser
import org.jetbrains.plugins.ruby.ruby.lang.parser.parsingUtils.RBuilder
import org.jetbrains.plugins.ruby.ruby.lang.parser.parsingUtils.RubyPsiBuilder
import org.jetbrains.plugins.ruby.ruby.lang.psi.impl.RubyInTemplateElementType
import org.jetbrains.plugins.ruby.ruby.sdk.LanguageLevel

class RubyCodeInRBladeType(debugName: String) : RubyInTemplateElementType(debugName) {
  override fun createLexer(lazyParsableElement: ASTNode?): Lexer {
    return RBladeRubyLexer(false, 0)
  }

  override fun createParser(languageLevel: LanguageLevel): RubyParser {
    return RBladeRubyParser(languageLevel)
  }

  override fun createBuilder(psiFile: PsiFile, chameleon: ASTNode, languageForParser: Language, languageLevel: LanguageLevel): RBuilder {
    val project = psiFile.project
    val lexer = createLexer(chameleon)
    val parserDefintion = LanguageParserDefinitions.INSTANCE.forLanguage(languageForParser)
    val builder = RubyPsiBuilder.create(chameleon, project, parserDefintion, lexer)

    return RBladeRubyBuilder(builder, languageLevel)
  }
}