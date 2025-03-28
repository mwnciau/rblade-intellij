package com.mwnciau.rblade

import org.junit.Test

class RBladeCommentsTest : RBladeTest() {
  @Test
  fun testRBladeComments() {
    assertLexesTo("{{--comment--}}", "COMMENT:{{--comment--}}")
    assertLexesTo("{{-- comment --}}", "COMMENT:{{-- comment --}}")
    assertLexesTo("{{--\ncomment\n--}}", "COMMENT:{{--\ncomment\n--}}")
    assertLexesTo("{{-- @endStatement --}}", "COMMENT:{{-- @endStatement --}}")
    assertLexesTo("{{-- {{ print }} --}}", "COMMENT:{{-- {{ print }} --}}")
  }

  @Test
  fun testERBComments() {
    assertLexesTo("<%#comment%>", "COMMENT:<%#comment%>")
    assertLexesTo("<%# comment %>", "COMMENT:<%# comment %>")
    assertLexesTo("<%#\ncomment\n%>", "COMMENT:<%#\ncomment\n%>")
    assertLexesTo("<%# @endStatement %>", "COMMENT:<%# @endStatement %>")
    assertLexesTo("<%# {{ print }} %>", "COMMENT:<%# {{ print }} %>")
  }

  @Test
  fun testMultiple() {
    assertLexesTo("{{-- 1 --}}{{-- 2 --}}", "COMMENT:{{-- 1 --}}", "COMMENT:{{-- 2 --}}")
    assertLexesTo("1{{-- 2 --}}3{{-- 4 --}}5", "1", "COMMENT:{{-- 2 --}}", "3", "COMMENT:{{-- 4 --}}", "5")

    assertLexesTo("<%#comment%><%#comment 2%>", "COMMENT:<%#comment%>", "COMMENT:<%#comment 2%>")
    assertLexesTo("1<%# 2 %>3<%# 4 %>5", "1", "COMMENT:<%# 2 %>", "3", "COMMENT:<%# 4 %>", "5")
  }

  @Test
  fun testCommentInWeirdPlaces() {
    assertLexesTo("@if({{--comment--}})", "RBLADE:@if(", "COMMENT:{{--comment--}}", "RBLADE:)")
    assertLexesTo("@if(<%#comment--%>)", "RBLADE:@if(", "COMMENT:<%#comment--%>", "RBLADE:)")
  }
}