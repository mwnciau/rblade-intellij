package com.mwnciau.rblade

import org.junit.Test

class RBladeStatementTest : RBladeTest() {
  @Test
  fun testStatement() {
    assertLexesTo("@pushIf", "RBLADE:@pushIf")
    assertLexesTo("@pushIf @if", "RBLADE:@pushIf", " ", "RBLADE:@if")
    assertLexesTo("@pushIf()", "RBLADE:@pushIf(", "RBLADE:)")
    assertLexesTo("@pushIf(1, 2, 3)", "RBLADE:@pushIf(", "RB:1", "COMMA:,", "RB: 2", "COMMA:,", "RB: 3", "RBLADE:)")
    assertLexesTo("@pushIf   (1, 2, 3)", "RBLADE:@pushIf   (", "RB:1", "COMMA:,", "RB: 2", "COMMA:,", "RB: 3", "RBLADE:)")
    assertLexesTo("@pushIf   (1,\n2,\n3)", "RBLADE:@pushIf   (", "RB:1", "COMMA:,", "RB:\n2", "COMMA:,", "RB:\n3", "RBLADE:)")
  }

  @Test
  fun testVariants() {
    assertLexesTo("@eachwithindex", "RBLADE:@eachwithindex")
    assertLexesTo("@each_with_index", "RBLADE:@each_with_index")
    assertLexesTo("@EachWithIndex", "RBLADE:@EachWithIndex")
    assertLexesTo("@eAcHwItHiNdEx", "RBLADE:@eAcHwItHiNdEx")

    assertLexesTo("@end", "RBLADE:@end")
    assertLexesTo("@END", "RBLADE:@END")
    assertLexesTo("@endTheTest?", "RBLADE:@endTheTest?")
    assertLexesTo("@endTheTest!", "RBLADE:@endTheTest!")
    assertLexesTo("@End_The_Test", "RBLADE:@End_The_Test")
  }

  @Test
  fun testLoops() {
    assertLexesTo("@each (user in users)", "RBLADE:@each (", "RB:user", " in ", "RB:users", "RBLADE:)")
    assertLexesTo("@eachWithIndex (user, index in users)", "RBLADE:@eachWithIndex (", "RB:user", "COMMA:,", "RB: index", " in ", "RB:users", "RBLADE:)")
    assertLexesTo("@eachWithIndex (k, v, i in {a: 'A'})", "RBLADE:@eachWithIndex (", "RB:k", "COMMA:,", "RB: v", "COMMA:,", "RB: i", " in ", "RB:{a: 'A'}", "RBLADE:)")
    assertLexesTo("@eachWithIndexElse (k, v, i in {a: 'A'})", "RBLADE:@eachWithIndexElse (", "RB:k", "COMMA:,", "RB: v", "COMMA:,", "RB: i", " in ", "RB:{a: 'A'}", "RBLADE:)")

    assertLexesTo("@for (i in 0...10)", "RBLADE:@for (", "RB:i in 0...10", "RBLADE:)")
  }

  @Test
  fun testNestedStatement() {
    assertLexesTo("@pushIf(@if)", "RBLADE:@pushIf(", "RB:@if", "RBLADE:)")
    assertLexesTo("@pushIf( @if )", "RBLADE:@pushIf(", "RB: @if ", "RBLADE:)")
    assertLexesTo("@pushIf( (@if) )", "RBLADE:@pushIf(", "RB: (@if) ", "RBLADE:)")
    assertLexesTo("@pushIf( @if() )", "RBLADE:@pushIf(", "RB: @if() ", "RBLADE:)")
  }

  @Test
  fun testEscapedStatement() {
    assertLexesTo("@@props", "@@props")
    assertLexesTo("@@props(1, 2, 3)", "@@props(1, 2, 3)")
    assertLexesTo("@@props(@if(1))", "@@props(", "RBLADE:@if(", "RB:1", "RBLADE:)", ")")
  }

  @Test
  fun testBracketMatching() {
    assertLexesTo("@if()", "RBLADE:@if(", "RBLADE:)")
    assertLexesTo("@if( )", "RBLADE:@if(", "RB: ", "RBLADE:)")

    assertLexesTo("@if((1), ((2)), (3))", "RBLADE:@if(", "RB:(1), ((2)), (3)", "RBLADE:)")
    assertLexesTo("@if((1 + 2), 3)", "RBLADE:@if(", "RB:(1 + 2), 3", "RBLADE:)")
    assertLexesTo("@if((((((((((()))))))))))", "RBLADE:@if(", "RB:(((((((((())))))))))", "RBLADE:)")
    assertLexesTo("@if(()()(())()((()))(()()))", "RBLADE:@if(", "RB:()()(())()((()))(()())", "RBLADE:)")
    assertLexesTo("@if(((())),((())))", "RBLADE:@if(", "RB:((())),((()))", "RBLADE:)")
    assertLexesTo("@if({{ }})", "RBLADE:@if(", "RB:{{ }}", "RBLADE:)")

    assertLexesTo("@if(1,\n2)", "RBLADE:@if(", "RB:1,\n2", "RBLADE:)")
    assertLexesTo("@if(1)\n,2)", "RBLADE:@if(", "RB:1", "RBLADE:)", "\n,2)")
    assertLexesTo("@if(1,)\n(2 + (3)),\n4) @if()", "RBLADE:@if(", "RB:1,", "RBLADE:)", "\n(2 + (3)),\n4) ", "RBLADE:@if(", "RBLADE:)")

    assertLexesTo("( @if())", "( ", "RBLADE:@if(", "RBLADE:)", ")")
    assertLexesTo("@if)", "RBLADE:@if", ")")

    // RBlade is able to detect that these parentheses don't match, but due to limitations in JFlex, we assume they do
    assertLexesTo("@if(", "RBLADE:@if(")
    assertLexesTo("@if(()(()", "RBLADE:@if(", "RB:()(()")
  }

  @Test
  fun testRubyStrings() {
    // Non-interpolated strings
    assertLexesTo("@if(')', 2)", "RBLADE:@if(", "RB:')', 2", "RBLADE:)")
    assertLexesTo("@if('(', 2)", "RBLADE:@if(", "RB:'(', 2", "RBLADE:)")
    assertLexesTo("@if(%q[)], 2)", "RBLADE:@if(", "RB:%q[)], 2", "RBLADE:)")
    assertLexesTo("@if('#{')'}')", "RBLADE:@if(", "RB:'#{'", "RBLADE:)", "'}')")
    assertLexesTo("@if(%q(\\)), 2)", "RBLADE:@if(", "RB:%q(\\)), 2", "RBLADE:)")
    assertLexesTo("@if(%q)\\)), 2)", "RBLADE:@if(", "RB:%q)\\)), 2", "RBLADE:)")

    // Interpolated strings
    assertLexesTo("@if(\")\", 2)", "RBLADE:@if(", "RB:\")\", 2", "RBLADE:)")
    assertLexesTo("@if(\"(\", 2)", "RBLADE:@if(", "RB:\"(\", 2", "RBLADE:)")
    assertLexesTo("@if(%Q[)], 2)", "RBLADE:@if(", "RB:%Q[)], 2", "RBLADE:)")
    assertLexesTo("@if(%[)], 2)", "RBLADE:@if(", "RB:%[)], 2", "RBLADE:)")
    assertLexesTo("@if(\"#{\")\"}\")", "RBLADE:@if(", "RB:\"#{\")\"}\"", "RBLADE:)")

    assertLexesTo("@if(?), 2)", "RBLADE:@if(", "RB:?), 2", "RBLADE:)")

    // "a" is not a valid percent-string delimiter
    assertLexesTo("@if(%qa)a, 2)", "RBLADE:@if(", "RB:%qa", "RBLADE:)", "a, 2)")
  }

  @Test
  fun testCommasInBrackets() {
    assertLexesTo("@pushIf([1, 2, 3])", "RBLADE:@pushIf(", "RB:[1, 2, 3]", "RBLADE:)")
    assertLexesTo("@pushIf([1, 2, 3], a)", "RBLADE:@pushIf(", "RB:[1, 2, 3]", "COMMA:,", "RB: a", "RBLADE:)")
    assertLexesTo("@pushIf([1, {2, 3}])", "RBLADE:@pushIf(", "RB:[1, {2, 3}]", "RBLADE:)")
    assertLexesTo("@pushIf([1], ([{2, 3}]))", "RBLADE:@pushIf(", "RB:[1]", "COMMA:,", "RB: ([{2, 3}])", "RBLADE:)")
  }

  @Test
  fun testBoundaries() {
    assertLexesTo("@ifhi", "@ifhi")
    assertLexesTo("hi@if", "hi@if")
    assertLexesTo(" @if", " ", "RBLADE:@if")
    assertLexesTo(">@if", ">", "RBLADE:@if")
    assertLexesTo(".@if", ".", "RBLADE:@if")
    assertLexesTo(")@if", ")", "RBLADE:@if")
    assertLexesTo("{{ }}@if", "RBLADE:{{", "RB: ", "RBLADE:}}", "RBLADE:@if")

    assertLexesTo("@end@end", "RBLADE:@end", "@end")

    assertLexesTo(">@if Hi @endIf", ">", "RBLADE:@if", " Hi ", "RBLADE:@endIf")
    assertLexesTo("'@if Hi @endIf", "'", "RBLADE:@if", " Hi ", "RBLADE:@endIf")
    assertLexesTo(".@if Hi @endIf", ".", "RBLADE:@if", " Hi ", "RBLADE:@endIf")
  }
}