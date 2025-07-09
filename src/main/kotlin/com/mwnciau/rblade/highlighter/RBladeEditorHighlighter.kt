package com.mwnciau.rblade.highlighter

import com.intellij.injected.editor.VirtualFileWindow
import com.intellij.lang.Language
import com.intellij.lang.html.HTMLLanguage
import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.editor.ex.util.LayerDescriptor
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Comparing
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider
import com.mwnciau.rblade.RBladeLanguage
import com.mwnciau.rblade.psi.RBladeTypes
import org.jetbrains.plugins.ruby.ruby.lang.RubyLanguage

class RBladeEditorHighlighter(
    private val project: Project?,
    private val virtualFile: VirtualFile?,
    colors: EditorColorsScheme
) : LayeredLexerEditorHighlighter(SyntaxHighlighterFactory.getSyntaxHighlighter(RBladeLanguage.INSTANCE, project, virtualFile), colors) {
    private var currentTemplateLanguage : Language? = null

    init {
        val rubyHighlighter =
            SyntaxHighlighterFactory.getSyntaxHighlighter(RubyLanguage.INSTANCE, project, virtualFile)
        val rubyLayer = LayerDescriptor(rubyHighlighter, "\n")
        this.registerLayer(RBladeTypes.RUBY_EXPRESSION, rubyLayer)
    }

    override fun updateLayers(): Boolean {
        val templateLanguage = getCurrentTemplateLanguageAndPrefixes()
        if (!Comparing.equal(currentTemplateLanguage, templateLanguage)) {
            unregisterLayer(RBladeTypes.HTML_TEMPLATE)
            currentTemplateLanguage = templateLanguage
            val templateLanguageHighlighter = SyntaxHighlighterFactory.getSyntaxHighlighter(currentTemplateLanguage as Language, project, virtualFile)
            this.registerLayer(RBladeTypes.HTML_TEMPLATE, LayerDescriptor(templateLanguageHighlighter, "", null))

            return true
        } else {
            return false
        }
    }


    private fun getCurrentTemplateLanguageAndPrefixes(): Language {
        if (virtualFile !is VirtualFileWindow && virtualFile != null && project != null) {
            val viewProvider = PsiManager.getInstance(project).findViewProvider(virtualFile)
            return if (viewProvider is TemplateLanguageFileViewProvider) {
                viewProvider.templateDataLanguage
            } else {
                HTMLLanguage.INSTANCE
            }
        } else {
            return HTMLLanguage.INSTANCE
        }
    }
}
