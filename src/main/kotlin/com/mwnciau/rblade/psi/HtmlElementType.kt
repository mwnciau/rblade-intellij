package com.mwnciau.rblade.psi

import com.intellij.lang.html.HTMLLanguage
import com.intellij.psi.templateLanguages.TemplateDataElementType
import com.mwnciau.rblade.RBladeFileViewProvider

class HtmlElementType : TemplateDataElementType(
  "HTML_TEMPLATE",
  HTMLLanguage.INSTANCE,
  RBladeTypes.HTML_TEMPLATE,
  RBladeFileViewProvider.OUTER_RBLADE,
) {
}