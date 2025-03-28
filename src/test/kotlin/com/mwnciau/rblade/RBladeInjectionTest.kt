package com.mwnciau.rblade

import org.junit.Test

class RBladeInjectionTest : RBladeTest() {
  @Test
  fun testRBladeRuby() {
    assertLexesTo("@ruby foo @endRuby", "RBLADE:@ruby", "RB: foo ", "RBLADE:@endRuby")
    assertLexesTo("@ruby\nfoo\n@endRuby", "RBLADE:@ruby", "RB:\nfoo\n", "RBLADE:@endRuby")

    assertLexesTo("@ruby 'foo' @endRuby", "RBLADE:@ruby", "RB: 'foo' ", "RBLADE:@endRuby")
    assertLexesTo("@ruby 1 @endRuby", "RBLADE:@ruby", "RB: 1 ", "RBLADE:@endRuby")
    assertLexesTo("@ruby 'hello dear reader' @endRuby", "RBLADE:@ruby", "RB: 'hello dear reader' ", "RBLADE:@endRuby")

    assertLexesTo("@ruby foo + bar << 'BAZ' @endRuby", "RBLADE:@ruby", "RB: foo + bar << 'BAZ' ", "RBLADE:@endRuby")
    assertLexesTo("@ruby \"foo\" + 'bar' @endRuby", "RBLADE:@ruby", "RB: \"foo\" + 'bar' ", "RBLADE:@endRuby")
    assertLexesTo("@ruby \"#{foo}\" @endRuby", "RBLADE:@ruby", "RB: \"#{foo}\" ", "RBLADE:@endRuby")
    assertLexesTo("@ruby 'a' * 3 @endRuby", "RBLADE:@ruby", "RB: 'a' * 3 ", "RBLADE:@endRuby")

    assertLexesTo("@ruby foo @endRuby @ruby bar @endRuby",
      "RBLADE:@ruby", "RB: foo ", "RBLADE:@endRuby",
      " ",
      "RBLADE:@ruby", "RB: bar ", "RBLADE:@endRuby",
    )

    assertLexesTo("1 @ruby 2 @endRuby 3 @ruby 4 @endRuby 5",
      "1 ",
      "RBLADE:@ruby", "RB: 2 ", "RBLADE:@endRuby",
      " 3 ",
      "RBLADE:@ruby", "RB: 4 ", "RBLADE:@endRuby",
      " 5",
    )

    // RBlade ruby statements should be case-insensitive and allow an underscore in the end tag
    assertLexesTo("@ruby foo @endruby", "RBLADE:@ruby", "RB: foo ", "RBLADE:@endruby")
    assertLexesTo("@RUBY foo @endRUBY", "RBLADE:@RUBY", "RB: foo ", "RBLADE:@endRUBY")
    assertLexesTo("@ruby foo @end_ruby", "RBLADE:@ruby", "RB: foo ", "RBLADE:@end_ruby")
    assertLexesTo("@rUbY fOo @EnD_rUbY", "RBLADE:@rUbY", "RB: fOo ", "RBLADE:@EnD_rUbY")
  }

  @Test
  fun testRBladePrint() {
    assertLexesTo("{{foo}}", "RBLADE:{{", "RB:foo", "RBLADE:}}")
    assertLexesTo("{{ foo }}", "RBLADE:{{", "RB: foo ", "RBLADE:}}")
    assertLexesTo("{{\nfoo\n}}", "RBLADE:{{", "RB:\nfoo\n", "RBLADE:}}")

    assertLexesTo("{{ 'foo' }}", "RBLADE:{{", "RB: 'foo' ", "RBLADE:}}")
    assertLexesTo("{{ 1 }}", "RBLADE:{{", "RB: 1 ", "RBLADE:}}")
    assertLexesTo("{{ 'hello dear reader' }}", "RBLADE:{{", "RB: 'hello dear reader' ", "RBLADE:}}")

    assertLexesTo("{{ foo + bar << 'BAZ' }}", "RBLADE:{{", "RB: foo + bar << 'BAZ' ", "RBLADE:}}")
    assertLexesTo("{{ \"foo\" + 'bar' }}", "RBLADE:{{", "RB: \"foo\" + 'bar' ", "RBLADE:}}")
    assertLexesTo("{{ \"#{foo}\" }}", "RBLADE:{{", "RB: \"#{foo}\" ", "RBLADE:}}")
    assertLexesTo("{{ 'a' * 3 }}", "RBLADE:{{", "RB: 'a' * 3 ", "RBLADE:}}")

    assertLexesTo("{{foo}} {{bar}}",
      "RBLADE:{{", "RB:foo", "RBLADE:}}",
      " ",
      "RBLADE:{{", "RB:bar", "RBLADE:}}",
    )

    assertLexesTo("1{{2}}3{{4}}5",
      "1",
      "RBLADE:{{", "RB:2", "RBLADE:}}",
      "3",
      "RBLADE:{{", "RB:4", "RBLADE:}}",
      "5",
    )
  }

  @Test
  fun testRBladeUnsafePrint() {
    assertLexesTo("{!!foo!!}", "RBLADE:{!!", "RB:foo", "RBLADE:!!}")
    assertLexesTo("{!! foo !!}", "RBLADE:{!!", "RB: foo ", "RBLADE:!!}")
    assertLexesTo("{!!\nfoo\n!!}", "RBLADE:{!!", "RB:\nfoo\n", "RBLADE:!!}")

    assertLexesTo("{!! 'foo' !!}", "RBLADE:{!!", "RB: 'foo' ", "RBLADE:!!}")
    assertLexesTo("{!! 1 !!}", "RBLADE:{!!", "RB: 1 ", "RBLADE:!!}")
    assertLexesTo("{!! 'hello dear reader' !!}", "RBLADE:{!!", "RB: 'hello dear reader' ", "RBLADE:!!}")

    assertLexesTo("{!! foo + bar << 'BAZ' !!}", "RBLADE:{!!", "RB: foo + bar << 'BAZ' ", "RBLADE:!!}")
    assertLexesTo("{!! \"foo\" + 'bar' !!}", "RBLADE:{!!", "RB: \"foo\" + 'bar' ", "RBLADE:!!}")
    assertLexesTo("{!! \"#{foo}\" !!}", "RBLADE:{!!", "RB: \"#{foo}\" ", "RBLADE:!!}")
    assertLexesTo("{!! 'a' * 3 !!}", "RBLADE:{!!", "RB: 'a' * 3 ", "RBLADE:!!}")

    assertLexesTo("{!!foo!!} {!!bar!!}",
      "RBLADE:{!!", "RB:foo", "RBLADE:!!}",
      " ",
      "RBLADE:{!!", "RB:bar", "RBLADE:!!}",
    )

    assertLexesTo("1{!!2!!}3{!!4!!}5",
      "1",
      "RBLADE:{!!", "RB:2", "RBLADE:!!}",
      "3",
      "RBLADE:{!!", "RB:4", "RBLADE:!!}",
      "5",
    )
  }

  @Test
  fun testERBRuby() {
    assertLexesTo("<%foo%>", "RBLADE:<%", "RB:foo", "RBLADE:%>")
    assertLexesTo("<% foo %>", "RBLADE:<%", "RB: foo ", "RBLADE:%>")
    assertLexesTo("<%\nfoo\n%>", "RBLADE:<%", "RB:\nfoo\n", "RBLADE:%>")

    assertLexesTo("<% 'foo' %>", "RBLADE:<%", "RB: 'foo' ", "RBLADE:%>")
    assertLexesTo("<% 1 %>", "RBLADE:<%", "RB: 1 ", "RBLADE:%>")
    assertLexesTo("<% 'hello dear reader' %>", "RBLADE:<%", "RB: 'hello dear reader' ", "RBLADE:%>")

    assertLexesTo("<% foo + bar << 'BAZ' %>", "RBLADE:<%", "RB: foo + bar << 'BAZ' ", "RBLADE:%>")
    assertLexesTo("<% \"foo\" + 'bar' %>", "RBLADE:<%", "RB: \"foo\" + 'bar' ", "RBLADE:%>")
    assertLexesTo("<% \"#{foo}\" %>", "RBLADE:<%", "RB: \"#{foo}\" ", "RBLADE:%>")
    assertLexesTo("<% 'a' * 3 %>", "RBLADE:<%", "RB: 'a' * 3 ", "RBLADE:%>")

    assertLexesTo("<%foo%> <%bar%>",
      "RBLADE:<%", "RB:foo", "RBLADE:%>",
      " ",
      "RBLADE:<%", "RB:bar", "RBLADE:%>",
    )

    assertLexesTo("1<%2%>3<%4%>5",
      "1",
      "RBLADE:<%", "RB:2", "RBLADE:%>",
      "3",
      "RBLADE:<%", "RB:4", "RBLADE:%>",
      "5",
    )
  }

  @Test
  fun testERBPrint() {
    assertLexesTo("<%=foo%>", "RBLADE:<%=", "RB:foo", "RBLADE:%>")
    assertLexesTo("<%= foo %>", "RBLADE:<%=", "RB: foo ", "RBLADE:%>")
    assertLexesTo("<%=\nfoo\n%>", "RBLADE:<%=", "RB:\nfoo\n", "RBLADE:%>")

    assertLexesTo("<%= 'foo' %>", "RBLADE:<%=", "RB: 'foo' ", "RBLADE:%>")
    assertLexesTo("<%= 1 %>", "RBLADE:<%=", "RB: 1 ", "RBLADE:%>")
    assertLexesTo("<%= 'hello dear reader' %>", "RBLADE:<%=", "RB: 'hello dear reader' ", "RBLADE:%>")

    assertLexesTo("<%= foo + bar << 'BAZ' %>", "RBLADE:<%=", "RB: foo + bar << 'BAZ' ", "RBLADE:%>")
    assertLexesTo("<%= \"foo\" + 'bar' %>", "RBLADE:<%=", "RB: \"foo\" + 'bar' ", "RBLADE:%>")
    assertLexesTo("<%= \"#{foo}\" %>", "RBLADE:<%=", "RB: \"#{foo}\" ", "RBLADE:%>")
    assertLexesTo("<%= 'a' * 3 %>", "RBLADE:<%=", "RB: 'a' * 3 ", "RBLADE:%>")

    assertLexesTo("<%=foo%> <%=bar%>",
      "RBLADE:<%=", "RB:foo", "RBLADE:%>",
      " ",
      "RBLADE:<%=", "RB:bar", "RBLADE:%>",
    )

    assertLexesTo("1<%=2%>3<%=4%>5",
      "1",
      "RBLADE:<%=", "RB:2", "RBLADE:%>",
      "3",
      "RBLADE:<%=", "RB:4", "RBLADE:%>",
      "5",
    )
  }

  @Test
  fun testERBUnsafePrint() {
    assertLexesTo("<%==foo%>", "RBLADE:<%==", "RB:foo", "RBLADE:%>")
    assertLexesTo("<%== foo %>", "RBLADE:<%==", "RB: foo ", "RBLADE:%>")
    assertLexesTo("<%==\nfoo\n%>", "RBLADE:<%==", "RB:\nfoo\n", "RBLADE:%>")

    assertLexesTo("<%== 'foo' %>", "RBLADE:<%==", "RB: 'foo' ", "RBLADE:%>")
    assertLexesTo("<%== 1 %>", "RBLADE:<%==", "RB: 1 ", "RBLADE:%>")
    assertLexesTo("<%== 'hello dear reader' %>", "RBLADE:<%==", "RB: 'hello dear reader' ", "RBLADE:%>")

    assertLexesTo("<%== foo + bar << 'BAZ' %>", "RBLADE:<%==", "RB: foo + bar << 'BAZ' ", "RBLADE:%>")
    assertLexesTo("<%== \"foo\" + 'bar' %>", "RBLADE:<%==", "RB: \"foo\" + 'bar' ", "RBLADE:%>")
    assertLexesTo("<%== \"#{foo}\" %>", "RBLADE:<%==", "RB: \"#{foo}\" ", "RBLADE:%>")
    assertLexesTo("<%== 'a' * 3 %>", "RBLADE:<%==", "RB: 'a' * 3 ", "RBLADE:%>")

    assertLexesTo("<%==foo%> <%==bar%>",
      "RBLADE:<%==", "RB:foo", "RBLADE:%>",
      " ",
      "RBLADE:<%==", "RB:bar", "RBLADE:%>",
    )

    assertLexesTo("1<%==2%>3<%==4%>5",
      "1",
      "RBLADE:<%==", "RB:2", "RBLADE:%>",
      "3",
      "RBLADE:<%==", "RB:4", "RBLADE:%>",
      "5",
    )
  }

  @Test
  fun testEscapes() {
    assertLexesTo("@{{ 'foo' }}", "@{{ 'foo' }}")
    assertLexesTo("@{!! 'foo' !!}", "@{!! 'foo' !!}")
    assertLexesTo("<%% 'foo' %>", "<%% 'foo' %>")
    assertLexesTo("<%%= 'foo' %>", "<%%= 'foo' %>")
    assertLexesTo("<%%== 'foo' %>", "<%%== 'foo' %>")
  }

  @Test
  fun testLimitations() {
    // The end tag cannot appear within the print
    assertLexesTo("{{ 'foo}}' }}", "RBLADE:{{", "RB: 'foo", "RBLADE:}}", "' }}")
    assertLexesTo("<%= 'foo%>' %>", "RBLADE:<%=", "RB: 'foo", "RBLADE:%>", "' %>")

    // A workaround to this is using the alternative syntax
    assertLexesTo("<%= 'foo}}' %>", "RBLADE:<%=", "RB: 'foo}}' ", "RBLADE:%>")
    assertLexesTo("{{ 'foo%>' }}", "RBLADE:{{", "RB: 'foo%>' ", "RBLADE:}}")

    // Start tags should be unaffected
    assertLexesTo("{{ '{{' }}", "RBLADE:{{", "RB: '{{' ", "RBLADE:}}")
    assertLexesTo("<% '<%' %>", "RBLADE:<%", "RB: '<%' ", "RBLADE:%>")

    // While problematic, RBlade will not parse the opening tag without the end tag
    assertLexesTo("{{ @if", "{{ ", "RBLADE:@if")
    assertLexesTo("{!! @if", "{!! ", "RBLADE:@if")
    assertLexesTo("@ruby @if", "@ruby ", "RBLADE:@if")
    assertLexesTo("<% @if", "<% ", "RBLADE:@if")
    assertLexesTo("<%= @if", "<%= ", "RBLADE:@if")
    assertLexesTo("<%== @if", "<%== ", "RBLADE:@if")

    // RBlade Ruby statements have stricter boundaries
    assertLexesTo("@ruby@endRuby", "@ruby@endRuby")
    assertLexesTo("@rubyfoo@endRuby", "@rubyfoo@endRuby")
    assertLexesTo("foo@ruby bar @endRuby", "foo@ruby bar @endRuby")
    assertLexesTo("@ruby foo@endRuby", "@ruby foo@endRuby")
    assertLexesTo("@ruby foo @endRubybar", "@ruby foo @endRubybar")
  }
}