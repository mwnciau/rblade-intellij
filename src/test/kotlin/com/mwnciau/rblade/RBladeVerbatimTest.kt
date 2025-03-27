package com.mwnciau.rblade

import org.junit.Test

class RBladeVerbatimTest : RBladeTest() {
  @Test
  fun testVerbatim() {
    assertLexesTo("@verbatim Hi @endVerbatim", "RBLADE_STATEMENT:@verbatim", "HTML_TEMPLATE: Hi ", "RBLADE_STATEMENT:@endVerbatim")
    assertLexesTo("@Verbatim Hi @EndVerbatim", "RBLADE_STATEMENT:@Verbatim", "HTML_TEMPLATE: Hi ", "RBLADE_STATEMENT:@EndVerbatim")
    assertLexesTo("@verbatim Hi @end_verbatim", "RBLADE_STATEMENT:@verbatim", "HTML_TEMPLATE: Hi ", "RBLADE_STATEMENT:@end_verbatim")
  }

  @Test
  fun testStatements() {
    assertLexesTo("@verbatim @endIf @endVerbatim", "RBLADE_STATEMENT:@verbatim", "HTML_TEMPLATE: @endIf ", "RBLADE_STATEMENT:@endVerbatim")
    assertLexesTo("@verbatim @props() @endVerbatim", "RBLADE_STATEMENT:@verbatim", "HTML_TEMPLATE: @props() ", "RBLADE_STATEMENT:@endVerbatim")
  }

  @Test
  fun testInjections() {
    assertLexesTo("@verbatim {{ abc }} @endVerbatim", "RBLADE_STATEMENT:@verbatim", "HTML_TEMPLATE: {{ abc }} ", "RBLADE_STATEMENT:@endVerbatim")
    assertLexesTo("@verbatim <%= abc %> @endVerbatim", "RBLADE_STATEMENT:@verbatim", "HTML_TEMPLATE: <%= abc %> ", "RBLADE_STATEMENT:@endVerbatim")
    assertLexesTo("@verbatim @ruby abc @endRuby @endVerbatim", "RBLADE_STATEMENT:@verbatim", "HTML_TEMPLATE: @ruby abc @endRuby ", "RBLADE_STATEMENT:@endVerbatim")
  }

  @Test
  fun testBoundaries() {
    assertLexesTo("@verbatimhi@endVerbatim", "HTML_TEMPLATE:@verbatimhi@endVerbatim")
    assertLexesTo("@verbatim hi@endVerbatim", "HTML_TEMPLATE:@verbatim hi@endVerbatim")
    assertLexesTo("@verbatimhi @endVerbatim", "HTML_TEMPLATE:@verbatimhi @endVerbatim")
    assertLexesTo("hi@verbatim @endVerbatim", "HTML_TEMPLATE:hi@verbatim @endVerbatim")

    assertLexesTo(">@verbatim Hi @endVerbatim", "HTML_TEMPLATE:>", "RBLADE_STATEMENT:@verbatim", "HTML_TEMPLATE: Hi ", "RBLADE_STATEMENT:@endVerbatim")
    assertLexesTo("'@verbatim Hi @endVerbatim", "HTML_TEMPLATE:'", "RBLADE_STATEMENT:@verbatim", "HTML_TEMPLATE: Hi ", "RBLADE_STATEMENT:@endVerbatim")
    assertLexesTo(".@verbatim Hi @endVerbatim", "HTML_TEMPLATE:.", "RBLADE_STATEMENT:@verbatim", "HTML_TEMPLATE: Hi ", "RBLADE_STATEMENT:@endVerbatim")
  }
}