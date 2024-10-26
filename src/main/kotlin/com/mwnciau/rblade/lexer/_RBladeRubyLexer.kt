package com.mwnciau.rblade.lexer

import com.mwnciau.rblade.RBladeMergingLexer
import com.mwnciau.rblade.psi.RBladeTypes
import org.jetbrains.plugins.ruby.erb.lexer.AbstractErbRubyLexer
import org.jetbrains.plugins.ruby.ruby.lang.lexer.RubyLexer

class _RBladeRubyLexer(highlightingMode: Boolean)
  : AbstractErbRubyLexer(highlightingMode, RBladeMergingLexer(), RBladeTypes.RUBY_EXPRESSION, RBladeTypes.RBLADE_INJECTION_IN_RUBY)
{
  override fun resetRubyLexer(rubyLexer: RubyLexer, buffer: CharSequence, start: Int, end: Int) {
    rubyLexer.start(";", 0, 1, 0)

    while (rubyLexer.tokenType != null) {
      rubyLexer.advance()
    }

    rubyLexer.start(buffer, start, end, 0)
  }
}