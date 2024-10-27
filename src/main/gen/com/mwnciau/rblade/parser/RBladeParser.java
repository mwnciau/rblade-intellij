// This is a generated file. Not intended for manual editing.
package com.mwnciau.rblade.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.mwnciau.rblade.psi.RBladeTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class RBladeParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return rbladeFile(b, l + 1);
  }

  /* ********************************************************** */
  // statement|HTML_TEMPLATE|COMMENT
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = statement(b, l + 1);
    if (!r) r = consumeToken(b, HTML_TEMPLATE);
    if (!r) r = consumeToken(b, COMMENT);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean rbladeFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rbladeFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rbladeFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // RBLADE_STATEMENT (RUBY_EXPRESSION+ RBLADE_STATEMENT)*
  public static boolean statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement")) return false;
    if (!nextTokenIs(b, RBLADE_STATEMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RBLADE_STATEMENT);
    r = r && statement_1(b, l + 1);
    exit_section_(b, m, STATEMENT, r);
    return r;
  }

  // (RUBY_EXPRESSION+ RBLADE_STATEMENT)*
  private static boolean statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!statement_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "statement_1", c)) break;
    }
    return true;
  }

  // RUBY_EXPRESSION+ RBLADE_STATEMENT
  private static boolean statement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = statement_1_0_0(b, l + 1);
    r = r && consumeToken(b, RBLADE_STATEMENT);
    exit_section_(b, m, null, r);
    return r;
  }

  // RUBY_EXPRESSION+
  private static boolean statement_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, RUBY_EXPRESSION);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, RUBY_EXPRESSION)) break;
      if (!empty_element_parsed_guard_(b, "statement_1_0_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

}
