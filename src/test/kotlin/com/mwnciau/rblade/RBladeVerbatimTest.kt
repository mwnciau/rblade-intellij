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
    assertLexesTo("@verbatim hi@endVerbatim", "RBLADE:@verbatim", " hi@endVerbatim")
    assertLexesTo("@verbatimhi @endVerbatim", "@verbatimhi ", "RBLADE:@endVerbatim")
    assertLexesTo("hi@verbatim @endVerbatim", "hi@verbatim ", "RBLADE:@endVerbatim")

    assertLexesTo(">@verbatim Hi @endVerbatim", ">", "RBLADE:@verbatim", " Hi ", "RBLADE:@endVerbatim")
    assertLexesTo("'@verbatim Hi @endVerbatim", "'", "RBLADE:@verbatim", " Hi ", "RBLADE:@endVerbatim")
    assertLexesTo(".@verbatim Hi @endVerbatim", ".", "RBLADE:@verbatim", " Hi ", "RBLADE:@endVerbatim")
  }

  @Test
  fun testVerbatimInWeirdPlaces() {
    assertLexesTo("@if(@verbatim {{ blah }} @endVerbatim)", "RBLADE:@if(", "RBLADE:@verbatim", "RB: {{ blah }} ", "RBLADE:@endVerbatim", "RBLADE:)")
    assertLexesTo("@if(\"#{ @verbatim blah @endVerbatim }\")", "RBLADE:@if(", "RB:\"#{ ", "RBLADE:@verbatim", "RB: blah ", "RBLADE:@endVerbatim", "RB: }\"", "RBLADE:)")
    assertLexesTo("@if({ @verbatim blah @endVerbatim })", "RBLADE:@if(", "RB:{ ", "RBLADE:@verbatim", "RB: blah ", "RBLADE:@endVerbatim", "RB: }", "RBLADE:)")
    assertLexesTo("@if(( @verbatim blah @endVerbatim ))", "RBLADE:@if(", "RB:( ", "RBLADE:@verbatim", "RB: blah ", "RBLADE:@endVerbatim", "RB: )", "RBLADE:)")
    assertLexesTo("@if([ @verbatim blah @endVerbatim ])", "RBLADE:@if(", "RB:[ ", "RBLADE:@verbatim", "RB: blah ", "RBLADE:@endVerbatim", "RB: ]", "RBLADE:)")
    assertLexesTo("@if(' @verbatim blah @endVerbatim ')", "RBLADE:@if(", "RB:' ", "RBLADE:@verbatim", "RB: blah ", "RBLADE:@endVerbatim", "RB: '", "RBLADE:)")
    assertLexesTo("@if(\" @verbatim blah @endVerbatim \")", "RBLADE:@if(", "RB:\" ", "RBLADE:@verbatim", "RB: blah ", "RBLADE:@endVerbatim", "RB: \"", "RBLADE:)")
  }
}