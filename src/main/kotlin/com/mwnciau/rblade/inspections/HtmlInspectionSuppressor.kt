package com.mwnciau.rblade.inspections

import com.intellij.codeInspection.InspectionSuppressor
import com.intellij.codeInspection.SuppressQuickFix
import com.intellij.codeInspection.htmlInspections.HtmlUnknownTagInspection
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlTag
import com.intellij.xml.util.CheckEmptyTagInspection
import com.mwnciau.rblade.RBladeLanguage

class HtmlInspectionSuppressor: InspectionSuppressor {
  companion object {
    val SUPPRESSED_INSPECTIONS = listOf((CheckEmptyTagInspection()).getID(), (HtmlUnknownTagInspection()).getID());
  }

  override fun isSuppressedFor(element: PsiElement, toolId: String): Boolean {
    if (!SUPPRESSED_INSPECTIONS.contains(toolId)) {
      return false
    }

    val file = element.containingFile
    if (file !== null && file.viewProvider.hasLanguage(RBladeLanguage.INSTANCE)) {
      val xmlTag = PsiTreeUtil.getParentOfType(element, XmlTag::class.java, false)
      return xmlTag !== null && xmlTag.name.startsWith("x-")
    }

    return false
  }

  override fun getSuppressActions(p0: PsiElement?, p1: String): Array<SuppressQuickFix> {
    return SuppressQuickFix.EMPTY_ARRAY
  }
}