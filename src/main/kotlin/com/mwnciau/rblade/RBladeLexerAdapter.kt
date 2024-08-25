package com.mwnciau.rblade

import com.intellij.lexer.FlexAdapter
import java.io.Reader

class RBladeLexerAdapter : FlexAdapter(RBladeLexer(null as Reader?)) {
}