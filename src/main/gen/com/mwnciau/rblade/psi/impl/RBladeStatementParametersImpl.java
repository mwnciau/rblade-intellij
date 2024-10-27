// This is a generated file. Not intended for manual editing.
package com.mwnciau.rblade.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.mwnciau.rblade.psi.RBladeTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.mwnciau.rblade.psi.*;

public class RBladeStatementParametersImpl extends ASTWrapperPsiElement implements RBladeStatementParameters {

  public RBladeStatementParametersImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull RBladeVisitor visitor) {
    visitor.visitStatementParameters(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RBladeVisitor) accept((RBladeVisitor)visitor);
    else super.accept(visitor);
  }

}
