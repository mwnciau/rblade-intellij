package com.mwnciau.rblade.ruby

import com.intellij.lang.Language
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage

class RBladeRubyLanguage : Language(RubyLanguage.INSTANCE, "RBladeRuby") {
  companion object {
    val INSTANCE = RBladeRubyLanguage()
  }
}