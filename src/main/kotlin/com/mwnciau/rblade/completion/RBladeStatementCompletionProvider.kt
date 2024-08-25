package com.mwnciau.rblade.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext

class RBladeStatementCompletionProvider : CompletionProvider<CompletionParameters>() {
  companion object {
    val STATEMENTS = arrayOf(
      "blank?",
      "break",
      "case",
      "checked",
      "class",
      "defined?",
      "delete",
      "disabled",
      "else",
      "elsif",
      "each",
      "eachElse",
      "eachWithIndex",
      "eachWithIndexElse",
      "empty",
      "empty?",
      "end",
      "endBlank?",
      "endCase",
      "endDefined?",
      "endEach",
      "endEachElse",
      "endEachWithIndex",
      "endEachWithIndexElse",
      "endEmpty?",
      "endEnv",
      "endFor",
      "endForElse",
      "endIf",
      "endNil?",
      "endOnce",
      "endPrepend",
      "endPrependIf",
      "endPrependOnce",
      "endPresent?",
      "endProduction",
      "endPush",
      "endPushIf",
      "endPushOnce",
      "endRuby",
      "endUnless",
      "endUntil",
      "endWhile",
      "env",
      "for",
      "forElse",
      "if",
      "method",
      "next",
      "nil?",
      "old",
      "once",
      "patch",
      "prepend",
      "prependIf",
      "prependOnce",
      "present?",
      "production",
      "props",
      "push",
      "pushIf",
      "pushOnce",
      "put",
      "readonly",
      "required",
      "ruby",
      "selected",
      "shouldRender",
      "stack",
      "style",
      "unless",
      "until",
      "when",
      "while",
    )
  }

  override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
    resultSet.addAllElements(STATEMENTS.map { statement -> LookupElementBuilder.create(statement) })
  }
}