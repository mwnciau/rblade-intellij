package com.mwnciau.rblade.stubs

import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement
import com.mwnciau.rblade.psi.RBladePropName

class RBladePropNameStub(parent: StubElement<*>, val key: String, val textOffset: Int)
  : StubBase<RBladePropName>(parent, RBladeStubElementTypes.RBLADE_PROP_NAME)
{
}
