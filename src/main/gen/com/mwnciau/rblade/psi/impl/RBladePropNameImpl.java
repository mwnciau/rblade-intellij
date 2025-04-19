// This is a generated file. Not intended for manual editing.
package com.mwnciau.rblade.psi.impl;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;
import com.mwnciau.rblade.psi.RBladePropName;
import com.mwnciau.rblade.psi.RBladeVisitor;
import com.mwnciau.rblade.stubs.RBladePropNameStub;
import org.jetbrains.annotations.NotNull;

public class RBladePropNameImpl extends StubBasedPsiElementBase<RBladePropNameStub> implements RBladePropName {
  public RBladePropNameImpl(@NotNull RBladePropNameStub stub, @NotNull IStubElementType<?, ?> type) {
    super(stub, type);
  }

  public RBladePropNameImpl(@NotNull ASTNode node) {
    super(node);
  }

  public RBladePropNameImpl(RBladePropNameStub stub, IElementType type, ASTNode node) {
    super(stub, type, node);
  }

  public void accept(@NotNull RBladeVisitor visitor) {
    visitor.visitPropName(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof RBladeVisitor) accept((RBladeVisitor)visitor);
    else super.accept(visitor);
  }

}
