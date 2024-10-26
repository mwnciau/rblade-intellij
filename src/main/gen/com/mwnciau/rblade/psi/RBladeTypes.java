// This is a generated file. Not intended for manual editing.
package com.mwnciau.rblade.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.mwnciau.rblade.psi.impl.RBladeStatementImpl;

public interface RBladeTypes {

  IElementType STATEMENT = new RBladeElementType("STATEMENT");

  IElementType COMMENT = new RBladeTokenType("COMMENT");
  IElementType HTML_TEMPLATE = new RBladeOuterElementType("HTML_TEMPLATE");
  IElementType RBLADE_STATEMENT = new RBladeTokenType("RBLADE_STATEMENT");
  IElementType RUBY_EXPRESSION = new RBladeOuterElementType("RUBY_EXPRESSION");

  IElementType RBLADE_INJECTION_IN_HTML = new RBladeOuterElementType("RBLADE_INJECTION_IN_HTML");
  IElementType RBLADE_INJECTION_IN_RUBY = new RBladeOuterElementType("RBLADE_INJECTION_IN_RUBY");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == STATEMENT) {
        return new RBladeStatementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
