package com.mwnciau.rblade

import junit.framework.TestCase.assertEquals
import org.junit.Test

class RBladeMergingLexerTest {
  private fun lexer(): RBladeMergingLexer {
    return RBladeMergingLexer()
  }

  private fun lex(string: String): MutableList<String> {
    val lexer = RBladeMergingLexer()
    lexer.start(string)

    var tokens = mutableListOf<String>()
    var token = lexer.tokenType

    while (token != null) {
      tokens.add(token.toString())

      lexer.advance()
      token = lexer.tokenType
    }

    return tokens
  }

  @Test
  fun testStatement() {
    assertEquals(listOf("RBLADE_STATEMENT"), lex("@props"))
  }

  @Test
  fun testStatementWithParameters() {
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@props(12345)"),
    )
  }

  @Test
  fun testStatementWithTemplate() {
    assertEquals(
      listOf("HTML_TEMPLATE", "RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT", "HTML_TEMPLATE"),
      lex("<p>{{ test }}</p>"),
    )
  }

  @Test
  fun testStatementWithRegex() {
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("{!! /\"/ !!}"),
    )
  }

  @Test
  fun testPropsWithBraces() {
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@props({title: required})"),
    )
  }

  @Test
  fun testRubyStatement() {
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("""
        @ruby
          colourData = {
            red: [{value: 123}, {value: 456}]
          }
        @endruby
      """.trimIndent())
    )
  }
}