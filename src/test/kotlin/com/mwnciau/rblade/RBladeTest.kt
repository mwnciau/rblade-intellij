package com.mwnciau.rblade

import kotlin.test.assertEquals

open class RBladeTest {
  private fun lex(string: String): MutableList<String> {
    val lexer = RBladeMergingLexer()
    lexer.start(string)

    var tokens = mutableListOf<String>()
    var token = lexer.tokenType

    while (token != null) {
      tokens.add(token.toString() + ":" + string.substring(lexer.tokenStart, lexer.tokenEnd))

      lexer.advance()
      token = lexer.tokenType
    }

    return tokens
  }

  protected fun assertLexesTo(rbladeString: String, vararg expected: String) {
    var actual = lex(rbladeString)

    assertEquals(expected.toList(), actual)
  }
}