package com.mwnciau.rblade

import org.junit.Test

class RBladeVerbatimTest : RBladeTest() {
  @Test
  fun testVerbatim() {
    assertLexesTo("@verbatim Hi @endVerbatim", "RBLADE:@verbatim", " Hi ", "RBLADE:@endVerbatim")
    assertLexesTo("@Verbatim Hi @EndVerbatim", "RBLADE:@Verbatim", " Hi ", "RBLADE:@EndVerbatim")
    assertLexesTo("@verbatim Hi @end_verbatim", "RBLADE:@verbatim", " Hi ", "RBLADE:@end_verbatim")
  }

  @Test
  fun testStatements() {
    assertLexesTo("@verbatim @endIf @endVerbatim", "RBLADE:@verbatim", " @endIf ", "RBLADE:@endVerbatim")
    assertLexesTo("@verbatim @props() @endVerbatim", "RBLADE:@verbatim", " @props() ", "RBLADE:@endVerbatim")
  }

  @Test
  fun testInjections() {
    assertLexesTo("@verbatim {{ abc }} @endVerbatim", "RBLADE:@verbatim", " {{ abc }} ", "RBLADE:@endVerbatim")
    assertLexesTo("@verbatim <%= abc %> @endVerbatim", "RBLADE:@verbatim", " <%= abc %> ", "RBLADE:@endVerbatim")
    assertLexesTo("@verbatim @ruby abc @endRuby @endVerbatim", "RBLADE:@verbatim", " @ruby abc @endRuby ", "RBLADE:@endVerbatim")
  }

  @Test
  fun testBoundaries() {
    assertLexesTo("@verbatimhi@endVerbatim", "@verbatimhi@endVerbatim")
    assertLexesTo("@verbatim hi@endVerbatim", "@verbatim hi@endVerbatim")
    assertLexesTo("@verbatimhi @endVerbatim", "@verbatimhi @endVerbatim")
    assertLexesTo("hi@verbatim @endVerbatim", "hi@verbatim @endVerbatim")

    assertLexesTo(">@verbatim Hi @endVerbatim", ">", "RBLADE:@verbatim", " Hi ", "RBLADE:@endVerbatim")
    assertLexesTo("'@verbatim Hi @endVerbatim", "'", "RBLADE:@verbatim", " Hi ", "RBLADE:@endVerbatim")
    assertLexesTo(".@verbatim Hi @endVerbatim", ".", "RBLADE:@verbatim", " Hi ", "RBLADE:@endVerbatim")
  }
}