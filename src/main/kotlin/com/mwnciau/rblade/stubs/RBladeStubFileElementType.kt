package com.mwnciau.rblade.stubs

import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.tree.IStubFileElementType
import com.mwnciau.rblade.RBladeLanguage
import com.mwnciau.rblade.psi.RBladeFile

class RBladeStubFileElementType : IStubFileElementType<PsiFileStub<RBladeFile>>("rblade.FILE", RBladeLanguage.INSTANCE) {
  override fun getExternalId(): String {
    return "rblade"
  }
}
