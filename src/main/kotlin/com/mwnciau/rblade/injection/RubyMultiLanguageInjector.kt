package com.mwnciau.rblade.injection

import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost
import com.mwnciau.rblade.psi.RBladeRubyTemplate
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage

class RubyMultiLanguageInjector : MultiHostInjector {
  override fun getLanguagesToInject(registrar: MultiHostRegistrar, context: PsiElement) {
    registrar.startInjecting(RubyLanguage.INSTANCE)
      .addPlace(null, null, context as PsiLanguageInjectionHost, context.textRange)
      .doneInjecting()
  }

  override fun elementsToInjectIn(): MutableList<out Class<out PsiElement>> {
    return mutableListOf(RBladeRubyTemplate::class.java)
  }
}