package com.mwnciau.rblade.completion

import com.intellij.codeInsight.completion.CompletionConfidence
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.util.ThreeState
import com.mwnciau.rblade.psi.RBladeFile

class RBladeCompletionConfidence : CompletionConfidence() {
  override fun shouldSkipAutopopup(contextElement: PsiElement, psiFile: PsiFile, offset: Int): ThreeState {
    val currentOffset = offset - contextElement.node.startOffset - 2

    if (currentOffset < 0) {
      return ThreeState.UNSURE
    } else if (psiFile is RBladeFile && contextElement.text[currentOffset] == '@') {
      return ThreeState.NO
    } else {
      return ThreeState.YES
    }
  }
}