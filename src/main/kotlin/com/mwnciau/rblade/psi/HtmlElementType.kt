package com.mwnciau.rblade.psi

import com.intellij.lang.Language
import com.intellij.lang.html.HTMLLanguage
import com.intellij.psi.templateLanguages.TemplateDataElementType
import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls

class HtmlElementType : TemplateDataElementType(
  "HTML_TEMPLATE",
  HTMLLanguage.INSTANCE,
  RBladeTypes.HTML_TEMPLATE
  outerElementType,
) {
}