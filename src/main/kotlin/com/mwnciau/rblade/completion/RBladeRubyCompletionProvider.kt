package com.mwnciau.rblade.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext

class RBladeRubyCompletionProvider : CompletionProvider<CompletionParameters>() {
  companion object {
    val EXCERPTS = arrayOf(
      "attributes",
      "attributes.merge()",
      "attributes.merge(class: \"\")",
      "attributes.merge(style: \"\")",
      "attributes.class()",
    )
  }
  override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
    resultSet.addAllElements(EXCERPTS.map { excerpt -> LookupElementBuilder.create(excerpt) })
  }
}