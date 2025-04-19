package com.mwnciau.rblade

import com.mwnciau.rblade.ruby.RBladeRubyLanguage
import org.jetbrains.plugins.ruby.ruby.lang.parser.RubyFileElementType

class RBladeRubyFileElementType : RubyFileElementType("rblade.ruby", RBladeRubyLanguage.INSTANCE) {
  override fun getExternalId(): String {
    return "rblade.ruby.FILE"
  }
}
