package com.mwnciau.rblade

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.mwnciau.rblade.parser.RBladeParser
import com.mwnciau.rblade.psi.RBladeFile
import com.mwnciau.rblade.psi.RBladeTokenSets
import com.mwnciau.rblade.psi.RBladeTypes
import com.mwnciau.rblade.stubs.RBladeStubFileElementType

class RBladeParserDefinition : ParserDefinition {
    companion object {
        val FILE = RBladeStubFileElementType()
    }
    override fun createLexer(project: Project): Lexer {
        return RBladeMergingLexer()
    }

    override fun getCommentTokens(): TokenSet {
        return RBladeTokenSets.COMMENTS
    }

    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createParser(project: Project): PsiParser {
        return RBladeParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return RBladeFile(viewProvider)
    }

    override fun createElement(node: ASTNode): PsiElement {
        return RBladeTypes.Factory.createElement(node)
    }
}
