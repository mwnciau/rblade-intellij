package com.mwnciau.rblade.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.xml.XmlTokenType
import com.mwnciau.rblade.RBladeFileType

class RBladeCompletionContributor : CompletionContributor() {
  init {
    this.extend(
      CompletionType.BASIC,
      PlatformPatterns.psiElement(XmlTokenType.XML_DATA_CHARACTERS).inVirtualFile(PlatformPatterns.virtualFile().ofType(RBladeFileType.INSTANCE)),
      RBladeStatementCompletionProvider()
    )
  }
}
