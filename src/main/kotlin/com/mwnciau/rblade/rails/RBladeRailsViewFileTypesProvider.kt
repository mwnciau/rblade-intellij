package com.mwnciau.rblade.rails

import com.intellij.lang.Language
import com.mwnciau.rblade.RBladeLanguage
import org.jetbrains.plugins.ruby.rails.RailsViewFileTypesProvider

class RBladeRailsViewFileTypesProvider : RailsViewFileTypesProvider {
  override fun getViewLanguages(): MutableSet<Language> {
    return mutableSetOf(RBladeLanguage.INSTANCE)
  }
}