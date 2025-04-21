package com.mwnciau.rblade.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns
import com.mwnciau.rblade.RBladeFileType

class RBladeCompletionContributor : CompletionContributor() {
  init {
    this.extend(
      CompletionType.BASIC,
      PlatformPatterns.psiElement().inVirtualFile(PlatformPatterns.virtualFile().ofType(RBladeFileType.INSTANCE)),
      RBladeStatementCompletionProvider()
    )
  }
}