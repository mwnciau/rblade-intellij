package com.mwnciau.rblade.html

import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlAttributeValue
import com.intellij.psi.xml.XmlTokenType
import com.mwnciau.rblade.RBladeFileViewProvider
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage

class RBladeMultiHostInjector : MultiHostInjector {
  override fun getLanguagesToInject(
    registrar: MultiHostRegistrar,
    context: PsiElement
  ) {
    if (context.containingFile.viewProvider !is RBladeFileViewProvider) return

    if (context is XmlAttributeValue) {
      val attribute = context.parent as? XmlAttribute ?: return
      val tag = attribute.parent ?: return
      if (tag.name.startsWith("x-") && attribute.name.startsWith(":") && !attribute.name.startsWith("::")) {
        val valueToken = context.node?.findChildByType(XmlTokenType.XML_ATTRIBUTE_VALUE_TOKEN) ?: return

        registrar.startInjecting(RubyLanguage.INSTANCE)
          .addPlace(null, null, context as PsiLanguageInjectionHost, valueToken.textRange.shiftLeft(context.textRange.startOffset))
          .doneInjecting()
      }
    }
  }

  override fun elementsToInjectIn() = listOf(XmlAttributeValue::class.java)
}
