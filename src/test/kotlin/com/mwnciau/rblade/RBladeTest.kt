package com.mwnciau.rblade

import com.intellij.psi.tree.IElementType
import kotlin.test.assertEquals

open class RBladeTest {
  private fun lex(string: String): MutableList<String> {
    val lexer = RBladeMergingLexer()
    lexer.start(string)

    var tokens = mutableListOf<String>()
    var tokenType = lexer.tokenType

    while (tokenType != null) {
      tokens.add(tokenTypeToPrefix(tokenType) + string.substring(lexer.tokenStart, lexer.tokenEnd))

      lexer.advance()
      tokenType = lexer.tokenType
    }

    return tokens
  }

  private fun tokenTypeToPrefix(tokenType: IElementType): String{
    return when (tokenType.toString()) {
      "RUBY_EXPRESSION" -> "RB:"
      "RBLADE_STATEMENT" -> "RBLADE:"
      "COMMENT" -> "COMMENT:"
      "RBLADE_STATEMENT_COMMA" -> "COMMA:"
      else -> ""
    }
  }

  protected fun assertLexesTo(rbladeString: String, vararg expected: String) {
    var actual = lex(rbladeString)

    assertEquals(expected.toList(), actual)
  }
}