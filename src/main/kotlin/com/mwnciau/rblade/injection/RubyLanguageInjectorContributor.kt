package com.mwnciau.rblade.injection

import com.intellij.lang.injection.general.Injection
import com.intellij.lang.injection.general.LanguageInjectionContributor
import com.intellij.lang.injection.general.SimpleInjection
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.mwnciau.rblade.psi.RBladeRubyTemplate
import com.mwnciau.rblade.psi.RBladeTypes
import io.ktor.util.reflect.*
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage

class RubyLanguageInjectorContributor : LanguageInjectionContributor {
  override fun getInjection(context: PsiElement): Injection? {
    return SimpleInjection(RubyLanguage.INSTANCE, "", "", null)
    /*if (context is RBladeRubyTemplate || context.toString() == "RBladeTokenType.RUBY_EXPRESSION") {
      return SimpleInjection(RubyLanguage.INSTANCE, "", "", null)
    }

    return null*/
  }
}