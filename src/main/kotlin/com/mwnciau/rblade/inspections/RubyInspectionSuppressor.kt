package com.mwnciau.rblade.inspections

import com.intellij.codeInsight.daemon.impl.analysis.XmlUnboundNsPrefixInspection
import com.intellij.codeInspection.InspectionSuppressor
import com.intellij.codeInspection.SuppressQuickFix
import com.intellij.codeInspection.htmlInspections.HtmlUnknownTagInspection
import com.intellij.codeInspection.htmlInspections.RequiredAttributesInspection
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlTag
import com.intellij.xml.util.CheckEmptyTagInspection
import com.mwnciau.rblade.RBladeLanguage
import org.jetbrains.plugins.ruby.ruby.inspections.classvariables.RubyClassVariableUsageInspection
import org.jetbrains.plugins.ruby.ruby.inspections.resolve.RubyResolveInspection

class RubyInspectionSuppressor: InspectionSuppressor {
  companion object {
    val SUPPRESSED_INSPECTIONS = listOf(RubyResolveInspection().id)
  }

  override fun isSuppressedFor(element: PsiElement, toolId: String): Boolean {
    if (!SUPPRESSED_INSPECTIONS.contains(toolId)) {
      return false
    }

    return element.containingFile !== null && element.containingFile.viewProvider.hasLanguage(RBladeLanguage.INSTANCE)
  }

  override fun getSuppressActions(p0: PsiElement?, p1: String): Array<SuppressQuickFix> {
    return SuppressQuickFix.EMPTY_ARRAY
  }
}