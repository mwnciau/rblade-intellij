package com.mwnciau.rblade.highlighter

import com.intellij.lang.html.HTMLLanguage
import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.editor.ex.util.LayerDescriptor
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.mwnciau.rblade.psi.RBladeTypes
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage

class RBladeEditorHighlighter(
    project: Project?,
    virtualFile: VirtualFile?,
    colors: EditorColorsScheme
) : LayeredLexerEditorHighlighter(RBladeSyntaxHighlighter(), colors) {
    init {
        val htmlHighlighter =
            SyntaxHighlighterFactory.getSyntaxHighlighter(HTMLLanguage.INSTANCE, project, virtualFile)
        val htmlLayer = LayerDescriptor(htmlHighlighter, "")
        this.registerLayer(RBladeTypes.HTML_TEMPLATE, htmlLayer)
        val rubyHighlighter =
            SyntaxHighlighterFactory.getSyntaxHighlighter(RubyLanguage.INSTANCE, project, virtualFile)
        val rubyLayer = LayerDescriptor(rubyHighlighter, "")
        this.registerLayer(RBladeTypes.RUBY_EXPRESSION, rubyLayer)
    }
}
