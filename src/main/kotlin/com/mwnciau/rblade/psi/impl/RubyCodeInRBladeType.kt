package com.mwnciau.rblade.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lexer.Lexer
import com.intellij.psi.PsiFile
import com.mwnciau.rblade.lexer.RBladeRubyLexer
import com.mwnciau.rblade.parser.RBladeRubyBuilder
import com.mwnciau.rblade.parser.RBladeRubyParser
import com.mwnciau.rblade.psi.RBladeOuterElementType
import org.jetbrains.plugins.ruby.ruby.lang.parser.RubyParser
import org.jetbrains.plugins.ruby.ruby.lang.parser.parsingUtils.RBuilder
import org.jetbrains.plugins.ruby.ruby.lang.parser.parsingUtils.RubyPsiBuilder
import org.jetbrains.plugins.ruby.ruby.lang.psi.impl.RubyInTemplateElementType
import org.jetbrains.plugins.ruby.ruby.sdk.LanguageLevel

class RubyCodeInRBladeType(debugName: String) : RubyInTemplateElementType(debugName) {
  companion object {
    val IN_SCRIPLET_STATE_PREFIXES = arrayOf(
      "<%", "<%==", "<%=", "<%#", "{{", "@ruby"
    )
  }

  override fun createLexer(lazyParsableElement: ASTNode?): Lexer {
    var state: Int = 0
    if (lazyParsableElement != null) {
      val textBefore = findOuterElementTextBefore(lazyParsableElement)
      if (textBefore != null) {
        for (prefix in IN_SCRIPLET_STATE_PREFIXES) {
          if (textBefore.endsWith(prefix)) {
            // ??
            //     public static final int IN_ERB_SCRIPTLET_STATE = 4;
            //     public static final int STATE_RUBY_BLOCK = 2;
            state = 2
          }
        }
      }
    }

    return RBladeRubyLexer(false, state);
  }

  private fun findOuterElementTextBefore(element: ASTNode): String? {
    var currentElement = element
    while (currentElement != null) {
      if (currentElement is RBladeOuterLanguageElement) {
        return currentElement.getText()
      }

      currentElement = if (currentElement.treePrev != null) {
        currentElement.treePrev
      } else {
        currentElement.treeParent
      }
    }

    return null
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