package com.mwnciau.rblade.lexer

import com.intellij.lexer.Lexer
import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType
import com.mwnciau.rblade.RBladeLexerAdapter
import com.mwnciau.rblade.psi.RBladeTypes
import com.mwnciau.rblade.psi.RBladeTypes.RUBY_EXPRESSION
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage
import org.jetbrains.plugins.ruby.ruby.lang.lexer.RubyLexer
import org.jetbrains.plugins.ruby.ruby.lang.lexer.RubyTokenTypes

class EmbeddedRuby : LexerBase() {
  private var rbladeLexer = RBladeLexerAdapter()
  private var rubyLexer = RubyLexer()

  private fun lexer() : Lexer {
    if (rbladeLexer.tokenType == RUBY_EXPRESSION) {
      return rubyLexer
    }

    return rbladeLexer
  }

  override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
    rbladeLexer.start(buffer, startOffset, endOffset, initialState)
  }

  override fun getState(): Int {
    return lexer().state
  }

  override fun getTokenType(): IElementType? {
    val tokenType = lexer().tokenType;

    if (tokenType != null) {
      if (tokenType == RBladeTypes.COMMENT) {
        return RubyTokenTypes.TLINE_COMMENT
      }
      if (tokenType.language != RubyLanguage.INSTANCE) {
          return RubyTokenTypes.tWHITE_SPACE_WITH_NEWLINE
      }
    }

    return tokenType
  }

  override fun getTokenStart(): Int {
    return lexer().tokenStart
  }

  override fun getTokenEnd(): Int {
    return lexer().tokenEnd
  }

  override fun advance() {
    if (rbladeLexer.tokenType == RUBY_EXPRESSION) {
      rubyLexer.advance()

      if (rubyLexer.tokenType == null){
        rbladeLexer.advance()
      }
    } else {
      rbladeLexer.advance()

      if (rbladeLexer.tokenType == RUBY_EXPRESSION){
        rubyLexer.start(bufferSequence, rbladeLexer.tokenStart, rbladeLexer.tokenEnd)
      }
    }
  }

  override fun getBufferSequence(): CharSequence {
    return rbladeLexer.bufferSequence
  }

  override fun getBufferEnd(): Int {
    return rbladeLexer.bufferEnd
  }
}