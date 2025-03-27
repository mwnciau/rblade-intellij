package com.mwnciau.rblade

import junit.framework.TestCase.assertEquals
import org.junit.Test

class RBladeMergingLexerTest {
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
    assertEquals(listOf("RBLADE_STATEMENT"), lex("@PROPS"))

    assertEquals(listOf("RBLADE_STATEMENT"), lex("@prependif"))
    assertEquals(listOf("RBLADE_STATEMENT"), lex("@prepend_if"))
    assertEquals(listOf("RBLADE_STATEMENT"), lex("@prependIf"))

    assertEquals(listOf("RBLADE_STATEMENT"), lex("@eachwithIndexElse"))
    assertEquals(listOf("RBLADE_STATEMENT"), lex("@each_with_index_else"))

    assertEquals(listOf("RBLADE_STATEMENT"), lex("@endIf"))
    assertEquals(listOf("RBLADE_STATEMENT"), lex("@end_if"))
    assertEquals(listOf("RBLADE_STATEMENT"), lex("@end"))

    assertEquals(listOf("HTML_TEMPLATE"), lex("@notAStatement"))
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
  fun testRegex() {
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@if(string.match /\"/)"),
    )

    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("{{ \"#{ /}\"/ }} }\" }}"),
    )
  }

  @Test
  fun testRubyStrings() {
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("{{ \"}}\" }}"),
    )

    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("{{ \"#{\" }} \"}\" }}"),
    )
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("{{ %Q[#{] }} ] }}"),
    )
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("{{ %[#{] }} ] }}"),
    )

    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("{{ %[}}] }}"),
    )
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("{{ %|}}| }}"),
    )
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("{{ %b}}b }}"),
    )

    // These aren't interpolated so the closing character will be registered
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT", "HTML_TEMPLATE"),
      lex("{{ '#{' }} '}' }}"),
    )
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT", "HTML_TEMPLATE"),
      lex("{{ %q[#{] }} }] }}"),
    )
  }

  @Test
  fun testProps() {
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT_PROPS_COLON", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@props(title: required)"),
    )

    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT_PROPS_COLON", "RUBY_EXPRESSION", "RBLADE_STATEMENT_COMMA", "RUBY_EXPRESSION", "RBLADE_STATEMENT_PROPS_COLON", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@props(a: 'a', b: 'b')"),
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
      """.trimIndent()),
    )
  }

  @Test
  fun testVerbatimStatement() {
    assertEquals(
      listOf("RBLADE_STATEMENT", "HTML_TEMPLATE", "RBLADE_STATEMENT"),
      lex("""
        @verbatim
          <x-form.error
            :errors="@model.errors"
            :except="[:name, :email]"
          />
          {{ not ruby }}
          {{-- not a comment --}}
        @endVerbatim
      """.trimIndent()),
    )
  }

  @Test
  fun testCommentGreediness() {
    assertEquals(
      listOf("COMMENT", "HTML_TEMPLATE", "COMMENT"),
      lex("{{-- comment 1 --}} {{-- comment 2 --}}"),
    )
    assertEquals(
      listOf("HTML_TEMPLATE", "COMMENT", "HTML_TEMPLATE", "RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT", "HTML_TEMPLATE"),
      lex(
        """
          <nav>
            <%# commnet %>
            <% statement %>
          </nav>
      """.trimIndent()
      ),
    )
  }

  @Test
  fun testCommaSeparation() {
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("<% {}.each do |k, v| %>"),
    )

    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT_COMMA", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@pushIf(123, 456)"),
    )
  }

  @Test
  fun testLoops() {
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT_EACH_IN", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@each (user in users)"),
    )

    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT_COMMA", "RUBY_EXPRESSION", "RBLADE_STATEMENT_EACH_IN", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@eachWithIndex (user, index in users)"),
    )

    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT_COMMA", "RUBY_EXPRESSION", "RBLADE_STATEMENT_COMMA", "RUBY_EXPRESSION", "RBLADE_STATEMENT_EACH_IN", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@eachWithIndex (key, value, index  in {a: 'A'})"),
    )

    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT_COMMA", "RUBY_EXPRESSION", "RBLADE_STATEMENT_COMMA", "RUBY_EXPRESSION", "RBLADE_STATEMENT_EACH_IN", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@eachWithIndexElse(key, value, index  in {a: 'A'})"),
    )

    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@for (i in 0...10)"),
    )
  }


  @Test
  fun testBracketMatching() {
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("{{ attributes.merge({\"class\": \"button\"}) }}")
    )

    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@if( ()(())(()()) {([])} |()| )")
    )

    // Ignore brackets that are out of order
    assertEquals(
      listOf("RBLADE_STATEMENT", "RUBY_EXPRESSION", "RBLADE_STATEMENT"),
      lex("@if( {)} )")
    )
  }
}