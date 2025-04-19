package com.mwnciau.rblade

import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.mwnciau.rblade.ruby.RBladeRubyLanguage
import org.jetbrains.plugins.ruby.ruby.lang.parser.RubyParserDefinition
import org.jetbrains.plugins.ruby.ruby.lang.psi.impl.RFileImpl

class RBladeRubyParserDefinition : RubyParserDefinition() {
    companion object {
        val FILE = RBladeRubyFileElementType()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return RFileImpl(viewProvider, RBladeRubyLanguage.INSTANCE)
    }
}
