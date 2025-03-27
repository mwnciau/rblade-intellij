package com.mwnciau.rblade

import org.junit.Test

class RBladeInjectionTest : RBladeTest() {
  @Test
  fun testRBladeRuby() {
    assertLexesTo("@ruby foo @endRuby", "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: foo ", "RBLADE_STATEMENT:@endRuby")
    assertLexesTo("@ruby\nfoo\n@endRuby", "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION:\nfoo\n", "RBLADE_STATEMENT:@endRuby")

    assertLexesTo("@ruby 'foo' @endRuby", "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: 'foo' ", "RBLADE_STATEMENT:@endRuby")
    assertLexesTo("@ruby 1 @endRuby", "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: 1 ", "RBLADE_STATEMENT:@endRuby")
    assertLexesTo("@ruby 'hello dear reader' @endRuby", "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: 'hello dear reader' ", "RBLADE_STATEMENT:@endRuby")

    assertLexesTo("@ruby foo + bar << 'BAZ' @endRuby", "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: foo + bar << 'BAZ' ", "RBLADE_STATEMENT:@endRuby")
    assertLexesTo("@ruby \"foo\" + 'bar' @endRuby", "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: \"foo\" + 'bar' ", "RBLADE_STATEMENT:@endRuby")
    assertLexesTo("@ruby \"#{foo}\" @endRuby", "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: \"#{foo}\" ", "RBLADE_STATEMENT:@endRuby")
    assertLexesTo("@ruby 'a' * 3 @endRuby", "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: 'a' * 3 ", "RBLADE_STATEMENT:@endRuby")

    assertLexesTo("@ruby foo @endRuby @ruby bar @endRuby",
      "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: foo ", "RBLADE_STATEMENT:@endRuby",
      "HTML_TEMPLATE: ",
      "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: bar ", "RBLADE_STATEMENT:@endRuby",
    )

    assertLexesTo("1 @ruby 2 @endRuby 3 @ruby 4 @endRuby 5",
      "HTML_TEMPLATE:1 ",
      "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: 2 ", "RBLADE_STATEMENT:@endRuby",
      "HTML_TEMPLATE: 3 ",
      "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: 4 ", "RBLADE_STATEMENT:@endRuby",
      "HTML_TEMPLATE: 5",
    )

    // RBlade ruby statements should be case-insensitive and allow an underscore in the end tag
    assertLexesTo("@ruby foo @endruby", "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: foo ", "RBLADE_STATEMENT:@endruby")
    assertLexesTo("@RUBY foo @endRUBY", "RBLADE_STATEMENT:@RUBY", "RUBY_EXPRESSION: foo ", "RBLADE_STATEMENT:@endRUBY")
    assertLexesTo("@ruby foo @end_ruby", "RBLADE_STATEMENT:@ruby", "RUBY_EXPRESSION: foo ", "RBLADE_STATEMENT:@end_ruby")
    assertLexesTo("@rUbY fOo @EnD_rUbY", "RBLADE_STATEMENT:@rUbY", "RUBY_EXPRESSION: fOo ", "RBLADE_STATEMENT:@EnD_rUbY")
  }

  @Test
  fun testRBladePrint() {
    assertLexesTo("{{foo}}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION:foo", "RBLADE_STATEMENT:}}")
    assertLexesTo("{{ foo }}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION: foo ", "RBLADE_STATEMENT:}}")
    assertLexesTo("{{\nfoo\n}}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION:\nfoo\n", "RBLADE_STATEMENT:}}")

    assertLexesTo("{{ 'foo' }}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION: 'foo' ", "RBLADE_STATEMENT:}}")
    assertLexesTo("{{ 1 }}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION: 1 ", "RBLADE_STATEMENT:}}")
    assertLexesTo("{{ 'hello dear reader' }}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION: 'hello dear reader' ", "RBLADE_STATEMENT:}}")

    assertLexesTo("{{ foo + bar << 'BAZ' }}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION: foo + bar << 'BAZ' ", "RBLADE_STATEMENT:}}")
    assertLexesTo("{{ \"foo\" + 'bar' }}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION: \"foo\" + 'bar' ", "RBLADE_STATEMENT:}}")
    assertLexesTo("{{ \"#{foo}\" }}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION: \"#{foo}\" ", "RBLADE_STATEMENT:}}")
    assertLexesTo("{{ 'a' * 3 }}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION: 'a' * 3 ", "RBLADE_STATEMENT:}}")

    assertLexesTo("{{foo}} {{bar}}",
      "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION:foo", "RBLADE_STATEMENT:}}",
      "HTML_TEMPLATE: ",
      "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION:bar", "RBLADE_STATEMENT:}}",
    )

    assertLexesTo("1{{2}}3{{4}}5",
      "HTML_TEMPLATE:1",
      "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION:2", "RBLADE_STATEMENT:}}",
      "HTML_TEMPLATE:3",
      "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION:4", "RBLADE_STATEMENT:}}",
      "HTML_TEMPLATE:5",
    )
  }

  @Test
  fun testRBladeUnsafePrint() {
    assertLexesTo("{!!foo!!}", "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION:foo", "RBLADE_STATEMENT:!!}")
    assertLexesTo("{!! foo !!}", "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION: foo ", "RBLADE_STATEMENT:!!}")
    assertLexesTo("{!!\nfoo\n!!}", "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION:\nfoo\n", "RBLADE_STATEMENT:!!}")

    assertLexesTo("{!! 'foo' !!}", "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION: 'foo' ", "RBLADE_STATEMENT:!!}")
    assertLexesTo("{!! 1 !!}", "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION: 1 ", "RBLADE_STATEMENT:!!}")
    assertLexesTo("{!! 'hello dear reader' !!}", "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION: 'hello dear reader' ", "RBLADE_STATEMENT:!!}")

    assertLexesTo("{!! foo + bar << 'BAZ' !!}", "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION: foo + bar << 'BAZ' ", "RBLADE_STATEMENT:!!}")
    assertLexesTo("{!! \"foo\" + 'bar' !!}", "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION: \"foo\" + 'bar' ", "RBLADE_STATEMENT:!!}")
    assertLexesTo("{!! \"#{foo}\" !!}", "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION: \"#{foo}\" ", "RBLADE_STATEMENT:!!}")
    assertLexesTo("{!! 'a' * 3 !!}", "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION: 'a' * 3 ", "RBLADE_STATEMENT:!!}")

    assertLexesTo("{!!foo!!} {!!bar!!}",
      "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION:foo", "RBLADE_STATEMENT:!!}",
      "HTML_TEMPLATE: ",
      "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION:bar", "RBLADE_STATEMENT:!!}",
    )

    assertLexesTo("1{!!2!!}3{!!4!!}5",
      "HTML_TEMPLATE:1",
      "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION:2", "RBLADE_STATEMENT:!!}",
      "HTML_TEMPLATE:3",
      "RBLADE_STATEMENT:{!!", "RUBY_EXPRESSION:4", "RBLADE_STATEMENT:!!}",
      "HTML_TEMPLATE:5",
    )
  }

  @Test
  fun testERBRuby() {
    assertLexesTo("<%foo%>", "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION:foo", "RBLADE_STATEMENT:%>")
    assertLexesTo("<% foo %>", "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION: foo ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%\nfoo\n%>", "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION:\nfoo\n", "RBLADE_STATEMENT:%>")

    assertLexesTo("<% 'foo' %>", "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION: 'foo' ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<% 1 %>", "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION: 1 ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<% 'hello dear reader' %>", "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION: 'hello dear reader' ", "RBLADE_STATEMENT:%>")

    assertLexesTo("<% foo + bar << 'BAZ' %>", "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION: foo + bar << 'BAZ' ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<% \"foo\" + 'bar' %>", "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION: \"foo\" + 'bar' ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<% \"#{foo}\" %>", "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION: \"#{foo}\" ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<% 'a' * 3 %>", "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION: 'a' * 3 ", "RBLADE_STATEMENT:%>")

    assertLexesTo("<%foo%> <%bar%>",
      "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION:foo", "RBLADE_STATEMENT:%>",
      "HTML_TEMPLATE: ",
      "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION:bar", "RBLADE_STATEMENT:%>",
    )

    assertLexesTo("1<%2%>3<%4%>5",
      "HTML_TEMPLATE:1",
      "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION:2", "RBLADE_STATEMENT:%>",
      "HTML_TEMPLATE:3",
      "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION:4", "RBLADE_STATEMENT:%>",
      "HTML_TEMPLATE:5",
    )
  }

  @Test
  fun testERBPrint() {
    assertLexesTo("<%=foo%>", "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION:foo", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%= foo %>", "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION: foo ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%=\nfoo\n%>", "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION:\nfoo\n", "RBLADE_STATEMENT:%>")

    assertLexesTo("<%= 'foo' %>", "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION: 'foo' ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%= 1 %>", "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION: 1 ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%= 'hello dear reader' %>", "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION: 'hello dear reader' ", "RBLADE_STATEMENT:%>")

    assertLexesTo("<%= foo + bar << 'BAZ' %>", "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION: foo + bar << 'BAZ' ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%= \"foo\" + 'bar' %>", "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION: \"foo\" + 'bar' ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%= \"#{foo}\" %>", "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION: \"#{foo}\" ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%= 'a' * 3 %>", "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION: 'a' * 3 ", "RBLADE_STATEMENT:%>")

    assertLexesTo("<%=foo%> <%=bar%>",
      "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION:foo", "RBLADE_STATEMENT:%>",
      "HTML_TEMPLATE: ",
      "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION:bar", "RBLADE_STATEMENT:%>",
    )

    assertLexesTo("1<%=2%>3<%=4%>5",
      "HTML_TEMPLATE:1",
      "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION:2", "RBLADE_STATEMENT:%>",
      "HTML_TEMPLATE:3",
      "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION:4", "RBLADE_STATEMENT:%>",
      "HTML_TEMPLATE:5",
    )
  }

  @Test
  fun testERBUnsafePrint() {
    assertLexesTo("<%==foo%>", "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION:foo", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%== foo %>", "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION: foo ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%==\nfoo\n%>", "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION:\nfoo\n", "RBLADE_STATEMENT:%>")

    assertLexesTo("<%== 'foo' %>", "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION: 'foo' ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%== 1 %>", "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION: 1 ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%== 'hello dear reader' %>", "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION: 'hello dear reader' ", "RBLADE_STATEMENT:%>")

    assertLexesTo("<%== foo + bar << 'BAZ' %>", "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION: foo + bar << 'BAZ' ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%== \"foo\" + 'bar' %>", "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION: \"foo\" + 'bar' ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%== \"#{foo}\" %>", "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION: \"#{foo}\" ", "RBLADE_STATEMENT:%>")
    assertLexesTo("<%== 'a' * 3 %>", "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION: 'a' * 3 ", "RBLADE_STATEMENT:%>")

    assertLexesTo("<%==foo%> <%==bar%>",
      "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION:foo", "RBLADE_STATEMENT:%>",
      "HTML_TEMPLATE: ",
      "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION:bar", "RBLADE_STATEMENT:%>",
    )

    assertLexesTo("1<%==2%>3<%==4%>5",
      "HTML_TEMPLATE:1",
      "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION:2", "RBLADE_STATEMENT:%>",
      "HTML_TEMPLATE:3",
      "RBLADE_STATEMENT:<%==", "RUBY_EXPRESSION:4", "RBLADE_STATEMENT:%>",
      "HTML_TEMPLATE:5",
    )
  }

  @Test
  fun testEscapes() {
    assertLexesTo("@{{ 'foo' }}", "HTML_TEMPLATE:@{{ 'foo' }}")
    assertLexesTo("@{!! 'foo' !!}", "HTML_TEMPLATE:@{!! 'foo' !!}")
    assertLexesTo("<%% 'foo' %>", "HTML_TEMPLATE:<%% 'foo' %>")
    assertLexesTo("<%%= 'foo' %>", "HTML_TEMPLATE:<%%= 'foo' %>")
    assertLexesTo("<%%== 'foo' %>", "HTML_TEMPLATE:<%%== 'foo' %>")
  }

  @Test
  fun testLimitations() {
    // The end tag cannot appear within the print
    assertLexesTo("{{ 'foo}}' }}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION: 'foo", "RBLADE_STATEMENT:}}", "HTML_TEMPLATE:' }}")
    assertLexesTo("<%= 'foo%>' %>", "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION: 'foo", "RBLADE_STATEMENT:%>", "HTML_TEMPLATE:' %>")

    // A workaround to this is using the alternative syntax
    assertLexesTo("<%= 'foo}}' %>", "RBLADE_STATEMENT:<%=", "RUBY_EXPRESSION: 'foo}}' ", "RBLADE_STATEMENT:%>")
    assertLexesTo("{{ 'foo%>' }}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION: 'foo%>' ", "RBLADE_STATEMENT:}}")

    // Start tags should be unaffected
    assertLexesTo("{{ '{{' }}", "RBLADE_STATEMENT:{{", "RUBY_EXPRESSION: '{{' ", "RBLADE_STATEMENT:}}")
    assertLexesTo("<% '<%' %>", "RBLADE_STATEMENT:<%", "RUBY_EXPRESSION: '<%' ", "RBLADE_STATEMENT:%>")

    // While problematic, RBlade will not parse the opening tag without the end tag
    assertLexesTo("{{ @if", "RBLADE_STATEMENT:{{", "HTML_TEMPLATE: ", "RBLADE_STATEMENT:@if")
    assertLexesTo("<% @if", "RBLADE_STATEMENT:<%", "HTML_TEMPLATE: ", "RBLADE_STATEMENT:@if")

    // RBlade Ruby statements have stricter boundaries
    assertLexesTo("@ruby@endRuby", "HTML_TEMPLATE:@ruby@endRuby")
    assertLexesTo("@rubyfoo@endRuby", "HTML_TEMPLATE:@rubyfoo@endRuby")
    assertLexesTo("foo@ruby bar @endRuby", "HTML_TEMPLATE:foo@ruby bar @endRuby")
    assertLexesTo("@ruby foo@endRuby", "HTML_TEMPLATE:@ruby foo@endRuby")
    assertLexesTo("@ruby foo @endRubybar", "HTML_TEMPLATE:@ruby foo @endRubybar")
  }
}