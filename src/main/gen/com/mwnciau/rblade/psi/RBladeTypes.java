// This is a generated file. Not intended for manual editing.
package com.mwnciau.rblade.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.mwnciau.rblade.psi.impl.*;

public interface RBladeTypes {

  IElementType RUBY_TEMPLATE = new RBladeElementType("RUBY_TEMPLATE");
  IElementType STATEMENT = new RBladeElementType("STATEMENT");

  IElementType COMMENT = new RBladeTokenType("COMMENT");
  IElementType HTML_TEMPLATE = new RBladeTokenType("HTML_TEMPLATE");
  IElementType RBLADE_STATEMENT = new RBladeTokenType("RBLADE_STATEMENT");
  IElementType RUBY_EXPRESSION = new RBladeTokenType("RUBY_EXPRESSION");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == RUBY_TEMPLATE) {
        return new RBladeRubyTemplateImpl(node);
      }
      else if (type == STATEMENT) {
        return new RBladeStatementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
