package com.mwnciau.rblade.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.PsiBuilderFactory
import com.intellij.psi.tree.IFileElementType
import com.mwnciau.rblade.lexer.EmbeddedRuby
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage

class EmbeddedRubyElementType : IFileElementType(RubyLanguage.INSTANCE) {
  override fun parseContents(chameleon: ASTNode): ASTNode? {
    val psi = chameleon.getPsi();
    assert(psi != null) { -> "Bad chameleon: $chameleon" }

    val project = psi.getProject()
    val languageForParser = this.getLanguageForParser(psi)
    val builder = PsiBuilderFactory.getInstance().createBuilder(
          project,
          chameleon,
          EmbeddedRuby(),
          languageForParser,
          chameleon.getChars()
    );
    val parser = LanguageParserDefinitions.INSTANCE.forLanguage(languageForParser).createParser(project);
    val node = parser.parse(this, builder);
    return node.getFirstChildNode();
  }
}