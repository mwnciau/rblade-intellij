package com.mwnciau.rblade.stubs

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.mwnciau.rblade.RBladeLanguage
import com.mwnciau.rblade.psi.impl.RBladePropNameImpl

class RBladePropNameStubElementType : IStubElementType<RBladePropNameStub, RBladePropNameImpl>("RBLADE_PROP_NAME_STUB",
  RBladeLanguage.INSTANCE
) {
  override fun createPsi(stub: RBladePropNameStub): RBladePropNameImpl? {
    return RBladePropNameImpl(stub, this)
  }

  override fun createStub(
    psi: RBladePropNameImpl,
    parentStub: StubElement<out PsiElement?>
  ): RBladePropNameStub {

    return RBladePropNameStub(parentStub, psi.text, psi.textOffset)
  }

  override fun getExternalId(): String {
    return "rblade.RBLADE_PROP_NAME"
  }

  override fun serialize(stub: RBladePropNameStub, dataStream: StubOutputStream) {
    dataStream.writeName(stub.key)
    dataStream.writeInt(stub.textOffset)
  }

  override fun deserialize(
    dataStream: StubInputStream,
    parentStub: StubElement<*>
  ): RBladePropNameStub {
    return RBladePropNameStub(parentStub, dataStream.readNameString()!!, dataStream.readInt())
  }

  override fun indexStub(p0: RBladePropNameStub, p1: IndexSink) {
  }

  override fun isAlwaysLeaf(root: StubBase<*>): Boolean {
    return true
  }
}