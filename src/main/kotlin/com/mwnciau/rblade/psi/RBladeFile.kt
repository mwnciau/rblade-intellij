package com.mwnciau.rblade.psi

import org.jetbrains.plugins.ruby.ruby.lang.psi.RFile

interface RBladeFile : RFile {
    fun getInnerRubyFile(): RFile
}