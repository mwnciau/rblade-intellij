// This is a generated file. Not intended for manual editing.
package com.mwnciau.rblade.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.mwnciau.rblade.psi.impl.*;

public interface RBladeTypes {

  IElementType STATEMENT = RBladeTypesFactory.createElement("STATEMENT");
  IElementType STATEMENT_PARAMETERS = RBladeTypesFactory.createElement("STATEMENT_PARAMETERS");

  IElementType COMMENT = RBladeTypesFactory.createToken("COMMENT");
  IElementType HTML_TEMPLATE = RBladeTypesFactory.createToken("HTML_TEMPLATE");
  IElementType RBLADE_INJECTION_IN_HTML = RBladeTypesFactory.createToken("RBLADE_INJECTION_IN_HTML");
  IElementType RBLADE_INJECTION_IN_RUBY = RBladeTypesFactory.createToken("RBLADE_INJECTION_IN_RUBY");
  IElementType RBLADE_STATEMENT = RBladeTypesFactory.createToken("RBLADE_STATEMENT");
  IElementType RBLADE_STATEMENT_COMMA = RBladeTypesFactory.createToken("RBLADE_STATEMENT_COMMA");
  IElementType RBLADE_STATEMENT_EACH_IN = RBladeTypesFactory.createToken("RBLADE_STATEMENT_EACH_IN");
  IElementType RBLADE_STATEMENT_PROPS_COLON = RBladeTypesFactory.createToken("RBLADE_STATEMENT_PROPS_COLON");
  IElementType RUBY_EXPRESSION = RBladeTypesFactory.createToken("RUBY_EXPRESSION");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == STATEMENT) {
        return new RBladeStatementImpl(node);
      }
      else if (type == STATEMENT_PARAMETERS) {
        return new RBladeStatementParametersImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
