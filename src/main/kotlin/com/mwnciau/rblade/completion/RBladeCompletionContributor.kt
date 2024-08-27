package com.mwnciau.rblade.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns
import com.mwnciau.rblade.psi.RBladeTypes
import org.jetbrains.plugins.ruby.ruby.codeInsight.completion.RubyCompletionProvider

class RBladeCompletionContributor : CompletionContributor() {
  init {
    this.extend(
      CompletionType.BASIC,
      PlatformPatterns.psiElement(RBladeTypes.RBLADE_STATEMENT),
      RBladeStatementCompletionProvider()
    )
    this.extend(
      CompletionType.BASIC,
      PlatformPatterns.psiElement(RBladeTypes.RUBY_TEMPLATE),
      RBladeRubyCompletionProvider()
    )
  }
}