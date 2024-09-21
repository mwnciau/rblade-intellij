package com.mwnciau.rblade.highlighter

import com.intellij.lang.html.HTMLLanguage
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage
import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.editor.ex.util.LayerDescriptor
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.mwnciau.rblade.psi.RBladeTypes

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
    }
}