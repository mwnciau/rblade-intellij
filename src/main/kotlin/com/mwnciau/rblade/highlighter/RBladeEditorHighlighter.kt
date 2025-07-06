package com.mwnciau.rblade.highlighter

import com.intellij.lang.Language
import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.editor.ex.util.LayerDescriptor
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.mwnciau.rblade.RBladeLanguage
import com.mwnciau.rblade.psi.RBladeTypes
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage

class RBladeEditorHighlighter(
    private val project: Project?,
    private val virtualFile: VirtualFile?,
    colors: EditorColorsScheme
) : LayeredLexerEditorHighlighter(SyntaxHighlighterFactory.getSyntaxHighlighter(RBladeLanguage.INSTANCE, project, virtualFile), colors) {
    init {
        val rubyHighlighter =
            SyntaxHighlighterFactory.getSyntaxHighlighter(RubyLanguage.INSTANCE, project, virtualFile)
        val rubyLayer = LayerDescriptor(rubyHighlighter, "\n")
        this.registerLayer(RBladeTypes.RUBY_EXPRESSION, rubyLayer)
    }

    override fun updateLayers(): Boolean {
        return true;
    }
}
