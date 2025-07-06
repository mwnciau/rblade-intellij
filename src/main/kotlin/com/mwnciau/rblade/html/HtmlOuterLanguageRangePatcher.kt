package com.mwnciau.rblade.html

import com.intellij.psi.templateLanguages.TemplateDataElementType

class HtmlOuterLanguageRangePatcher : TemplateDataElementType.OuterLanguageRangePatcher {
  override fun getTextForOuterLanguageInsertionRange(
    templateDataElementType: TemplateDataElementType,
    charSequence: CharSequence
  ): String? {
    return "Injection"
  }
}