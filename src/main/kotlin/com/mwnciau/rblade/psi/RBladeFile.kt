package com.mwnciau.rblade.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiFile
import com.intellij.psi.impl.source.PsiFileWithStubSupport
import com.intellij.psi.stubs.PsiFileStubImpl
import com.intellij.psi.stubs.StubTree
import com.intellij.psi.stubs.StubTreeLoader
import com.intellij.util.concurrency.ThreadingAssertions
import com.mwnciau.rblade.RBladeFileType
import com.mwnciau.rblade.RBladeLanguage
import java.lang.ref.SoftReference

class RBladeFile(viewProvider: FileViewProvider) :
  PsiFileBase(viewProvider, RBladeLanguage.INSTANCE), PsiFileWithStubSupport
{
    companion object {
      val LOGGER = Logger.getInstance(RBladeFile::class.java)
    }

  @Volatile
  var stubRef: SoftReference<StubTree>? = null
  var stubLock = Any()

  override fun toString(): String {
    return "RBladeFile:${this.name}"
  }

  override fun getFileType(): FileType {
    return RBladeFileType.INSTANCE
  }

  override fun getStubTree(): StubTree? {
    if (virtualFile == null || !virtualFile.isValid) {
      return null
    }

    ThreadingAssertions.assertReadAccess()

    stubRef?.get()?.let { return it }

    val newStubTree = StubTreeLoader.getInstance().readOrBuild(project, virtualFile, this) as StubTree?

    if (newStubTree == null) {
      if (LOGGER.isDebugEnabled) {
        LOGGER.debug("No stub for class file in index: " + virtualFile.presentableUrl)
      }

      return null
    }

    synchronized(stubLock) {
      stubRef?.get()?.let { return it }

      val fileStub = newStubTree.root as PsiFileStubImpl<PsiFile>
      fileStub.psi = this

      stubRef = SoftReference(newStubTree)

      return newStubTree
    }
  }
}
