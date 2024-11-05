// Generated by JFlex 1.9.1 http://jflex.de/  (tweaked for IntelliJ platform)
// source: RBlade.flex

// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.mwnciau.rblade;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.mwnciau.rblade.psi.RBladeTypes;
import com.intellij.psi.TokenType;
import java.util.ArrayDeque;
import java.util.Set;

public class RBladeLexer implements FlexLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int STATE_RUBY_BLOCK = 2;
  public static final int STATE_RUBY_BLOCK_END = 4;
  public static final int STATE_STRING_LITERAL = 6;
  public static final int STATE_STRING_LITERAL_INTERPOLATION = 8;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2,  2,  3,  3,  4, 4
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\1\u0100\24\u0200\1\u0300\11\u0200\1\u0400\1\u0500\16\u0200"+
    "\1\u0600\247\u0200\10\u0700\u1020\u0200";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\4\2\22\0\1\1\1\3\1\4\1\5"+
    "\1\0\1\6\1\0\1\4\1\7\1\10\2\0\1\11"+
    "\1\12\14\0\1\13\1\0\1\14\1\15\1\16\1\17"+
    "\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\1\30\1\31\1\27\1\32\1\33\1\34\1\35\1\36"+
    "\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46"+
    "\1\47\1\50\1\27\1\51\1\52\1\53\1\0\1\54"+
    "\1\0\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\1\30\1\31\1\27\1\32\1\33\1\34\1\35\1\36"+
    "\1\37\1\40\1\55\1\56\1\43\1\44\1\45\1\46"+
    "\1\57\1\50\1\27\1\60\1\51\1\61\7\0\1\2"+
    "\32\0\1\62\217\0\2\63\115\0\1\64\u0200\0\1\62"+
    "\177\0\13\62\35\0\2\2\5\0\1\62\57\0\1\62"+
    "\312\0\1\65\325\0\1\62\377\0\u0100\66";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[2048];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\5\0\4\1\3\2\1\3\1\2\1\4\1\5\1\6"+
    "\1\7\1\2\2\10\3\11\1\12\2\11\3\13\1\2"+
    "\1\14\1\2\1\15\1\16\1\17\20\0\1\20\2\0"+
    "\1\2\1\3\3\0\1\21\1\2\1\14\1\0\1\17"+
    "\14\0\1\22\20\0\1\23\5\0\1\17\14\0\1\22"+
    "\1\0\1\24\23\0\1\25\2\0\1\26\7\0\1\22"+
    "\1\0\1\22\7\0\1\22\2\0\1\27\16\0\1\22"+
    "\52\0";

  private static int [] zzUnpackAction() {
    int [] result = new int[217];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\67\0\156\0\245\0\334\0\u0113\0\u014a\0\u0181"+
    "\0\u01b8\0\u01ef\0\u0226\0\u025d\0\u0294\0\u02cb\0\u0294\0\u0294"+
    "\0\u0294\0\u0294\0\u0302\0\u0294\0\u0339\0\u0294\0\u0370\0\u03a7"+
    "\0\u0294\0\u03de\0\u0415\0\u0294\0\u044c\0\u0483\0\u04ba\0\u0294"+
    "\0\u04f1\0\u0294\0\u0294\0\u0528\0\u055f\0\u0596\0\u05cd\0\u0604"+
    "\0\u063b\0\u0672\0\u06a9\0\u06e0\0\u0717\0\u074e\0\u0785\0\u07bc"+
    "\0\u07f3\0\u082a\0\u0861\0\u0898\0\u08cf\0\u0226\0\u0906\0\u093d"+
    "\0\u0974\0\u09ab\0\u0415\0\u09e2\0\u0294\0\u0294\0\u0a19\0\u0a50"+
    "\0\u0a87\0\u0abe\0\u0af5\0\u0b2c\0\u0b63\0\u0b9a\0\u0bd1\0\u0c08"+
    "\0\u0c3f\0\u0c76\0\u0cad\0\u0ce4\0\u0d1b\0\u0d52\0\u0d89\0\u0dc0"+
    "\0\u0df7\0\u0e2e\0\u0e65\0\u0e9c\0\u0ed3\0\u0f0a\0\u0f41\0\u0f78"+
    "\0\u0faf\0\u0fe6\0\u101d\0\u1054\0\u108b\0\u10c2\0\u0294\0\u10f9"+
    "\0\u1130\0\u1167\0\u119e\0\u11d5\0\u0294\0\u120c\0\u1243\0\u127a"+
    "\0\u12b1\0\u12e8\0\u131f\0\u1356\0\u138d\0\u13c4\0\u13fb\0\u1432"+
    "\0\u1469\0\u14a0\0\u0d52\0\u0294\0\u14d7\0\u150e\0\u1545\0\u157c"+
    "\0\u15b3\0\u15ea\0\u1621\0\u1658\0\u168f\0\u16c6\0\u16fd\0\u1734"+
    "\0\u176b\0\u17a2\0\u17d9\0\u1810\0\u1847\0\u187e\0\u18b5\0\u1130"+
    "\0\u18ec\0\u1923\0\u0294\0\u195a\0\u1991\0\u19c8\0\u19ff\0\u1a36"+
    "\0\u1a6d\0\u1aa4\0\u1adb\0\u1b12\0\u1b49\0\u1b80\0\u1bb7\0\u1bee"+
    "\0\u1c25\0\u1c5c\0\u1c93\0\u1cca\0\u1d01\0\u1d38\0\u1d6f\0\u0d52"+
    "\0\u1da6\0\u1ddd\0\u1e14\0\u1e4b\0\u1e82\0\u1eb9\0\u1ef0\0\u1f27"+
    "\0\u1f5e\0\u1f95\0\u1fcc\0\u2003\0\u203a\0\u2071\0\u20a8\0\u20df"+
    "\0\u2116\0\u214d\0\u2184\0\u21bb\0\u21f2\0\u2229\0\u2260\0\u2297"+
    "\0\u22ce\0\u2305\0\u233c\0\u2373\0\u23aa\0\u23e1\0\u2418\0\u244f"+
    "\0\u2486\0\u24bd\0\u24f4\0\u252b\0\u2562\0\u2599\0\u25d0\0\u2607"+
    "\0\u263e\0\u2675\0\u26ac\0\u26e3\0\u271a\0\u2751\0\u2788\0\u27bf"+
    "\0\u27f6\0\u282d\0\u2864\0\u289b\0\u28d2\0\u2909\0\u2940\0\u2977"+
    "\0\u29ae";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[217];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length() - 1;
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpacktrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\14\6\1\7\3\6\1\10\37\6\1\11\6\6\1\12"+
    "\2\13\1\14\1\15\1\12\1\16\1\17\1\20\1\21"+
    "\1\12\1\22\4\12\1\23\30\12\1\17\1\12\1\24"+
    "\4\12\1\17\1\25\1\13\4\12\3\26\1\27\2\26"+
    "\1\30\1\26\1\31\7\26\1\32\40\26\1\33\5\26"+
    "\5\34\1\35\44\34\1\36\14\34\4\37\1\40\1\37"+
    "\1\41\51\37\1\42\1\43\5\37\14\6\1\0\3\6"+
    "\1\0\37\6\1\0\6\6\6\0\1\44\102\0\1\45"+
    "\1\46\1\47\1\50\1\51\2\0\1\52\2\0\1\53"+
    "\1\54\1\55\1\56\1\0\1\57\1\60\1\0\1\61"+
    "\1\62\1\63\6\0\1\57\1\60\4\0\1\52\1\60"+
    "\5\0\1\64\54\0\1\65\6\0\1\12\2\0\1\12"+
    "\1\0\1\12\4\0\1\12\1\0\4\12\1\0\30\12"+
    "\1\0\1\12\1\0\4\12\3\0\4\12\1\0\2\66"+
    "\26\0\1\67\30\0\1\66\1\67\3\0\1\12\2\0"+
    "\1\70\1\0\1\12\4\0\1\12\1\0\4\12\1\0"+
    "\30\12\1\0\1\12\1\0\4\12\3\0\4\12\67\0"+
    "\2\15\1\0\13\15\1\20\12\15\1\71\6\15\1\71"+
    "\5\15\1\71\6\15\3\71\6\15\26\0\1\72\122\0"+
    "\1\20\10\0\1\73\101\0\1\31\75\0\1\74\122\0"+
    "\1\31\65\0\1\75\6\0\67\76\4\37\1\0\1\37"+
    "\1\0\51\37\2\0\5\37\2\40\1\0\26\40\1\77"+
    "\6\40\1\77\5\40\1\77\6\40\3\77\6\40\6\0"+
    "\1\100\7\0\1\101\104\0\1\102\5\0\1\103\13\0"+
    "\1\103\32\0\1\104\6\0\1\105\2\0\1\106\60\0"+
    "\1\107\3\0\1\110\31\0\1\110\24\0\1\111\11\0"+
    "\1\112\1\113\1\114\67\0\1\115\56\0\1\116\65\0"+
    "\1\117\66\0\1\120\3\0\1\121\31\0\1\121\36\0"+
    "\1\122\1\0\1\123\52\0\1\124\17\0\1\125\2\0"+
    "\1\126\10\0\1\125\36\0\1\127\16\0\1\130\47\0"+
    "\1\131\2\0\1\132\12\0\1\133\60\0\1\134\56\0"+
    "\1\135\71\0\1\136\41\0\1\137\75\0\1\140\111\0"+
    "\1\141\31\0\1\12\2\0\1\12\1\0\1\12\4\0"+
    "\1\12\1\0\4\12\1\0\30\12\1\0\1\12\1\0"+
    "\4\12\1\0\1\20\1\0\4\12\2\15\1\0\63\15"+
    "\36\0\1\142\66\0\1\143\31\0\2\40\1\0\63\40"+
    "\1\0\6\100\1\144\60\100\15\0\1\145\72\0\1\146"+
    "\72\0\1\147\103\0\1\150\13\0\1\150\5\0\1\150"+
    "\27\0\1\151\62\0\1\152\73\0\1\153\4\0\1\154"+
    "\75\0\1\155\13\0\1\155\5\0\1\155\25\0\1\156"+
    "\105\0\1\157\13\0\1\157\5\0\1\157\41\0\1\160"+
    "\53\0\1\161\20\0\1\116\62\0\1\162\13\0\1\162"+
    "\12\0\1\163\5\0\1\164\122\0\1\165\72\0\1\166"+
    "\7\0\1\166\42\0\1\167\57\0\1\116\65\0\1\150"+
    "\106\0\1\170\50\0\1\171\10\0\1\172\72\0\1\173"+
    "\1\116\12\0\1\173\5\0\1\173\23\0\1\174\16\0"+
    "\1\175\50\0\1\176\77\0\1\177\71\0\1\200\51\0"+
    "\1\201\26\0\1\202\51\0\1\203\7\0\1\204\64\0"+
    "\1\205\13\0\1\205\36\0\1\206\3\0\1\202\31\0"+
    "\1\202\15\0\1\207\55\0\2\210\57\0\1\210\30\0"+
    "\1\211\66\0\1\212\42\0\6\100\1\144\7\100\1\213"+
    "\50\100\35\0\1\214\52\0\1\215\72\0\1\116\64\0"+
    "\1\216\105\0\1\217\13\0\1\217\5\0\1\217\33\0"+
    "\1\220\31\0\1\220\30\0\1\221\62\0\1\222\75\0"+
    "\1\223\63\0\1\116\3\0\1\52\31\0\1\52\46\0"+
    "\1\224\42\0\1\225\1\0\30\225\3\0\4\225\10\0"+
    "\1\163\5\0\1\164\15\0\1\226\26\0\1\227\42\0"+
    "\1\230\101\0\1\116\42\0\1\116\72\0\1\231\102\0"+
    "\1\232\2\0\1\233\13\0\1\233\5\0\1\233\26\0"+
    "\1\234\12\0\1\217\57\0\1\235\62\0\1\236\106\0"+
    "\1\237\72\0\1\240\43\0\1\241\105\0\1\242\45\0"+
    "\1\215\76\0\1\150\60\0\1\152\72\0\1\243\31\0"+
    "\1\243\25\0\1\244\101\0\1\116\31\0\12\207\1\245"+
    "\54\207\41\0\1\246\12\0\1\247\1\246\52\0\1\250"+
    "\12\0\1\251\1\250\43\0\1\167\32\0\1\167\33\0"+
    "\1\116\32\0\1\116\33\0\1\252\32\0\1\252\43\0"+
    "\1\116\13\0\1\116\5\0\1\116\37\0\1\253\74\0"+
    "\1\150\45\0\1\254\45\0\1\163\5\0\1\164\15\0"+
    "\1\226\20\0\1\255\5\0\1\256\62\0\1\257\17\0"+
    "\1\163\5\0\1\164\7\0\1\225\1\0\30\225\3\0"+
    "\4\225\42\0\1\104\60\0\1\226\77\0\1\122\60\0"+
    "\1\116\63\0\1\260\66\0\1\261\105\0\1\262\23\0"+
    "\1\163\5\0\1\164\21\0\1\52\4\0\1\263\15\0"+
    "\1\264\6\0\1\52\41\0\1\265\15\0\1\266\43\0"+
    "\1\267\31\0\1\267\26\0\1\270\76\0\1\271\66\0"+
    "\1\116\54\0\1\272\45\0\12\207\1\273\54\207\44\0"+
    "\1\274\63\0\1\246\13\0\1\246\55\0\1\275\63\0"+
    "\1\250\13\0\1\250\36\0\1\122\66\0\1\276\74\0"+
    "\1\252\64\0\1\277\31\0\1\277\30\0\1\226\20\0"+
    "\1\255\21\0\1\163\5\0\1\164\7\0\1\116\104\0"+
    "\1\300\66\0\1\301\54\0\1\302\100\0\1\123\62\0"+
    "\1\52\4\0\1\263\24\0\1\52\40\0\1\303\67\0"+
    "\1\265\71\0\1\252\13\0\1\252\54\0\1\252\47\0"+
    "\1\304\105\0\1\305\23\0\12\207\1\273\46\207\1\306"+
    "\5\207\22\0\1\307\66\0\1\310\70\0\1\167\105\0"+
    "\1\311\47\0\1\235\105\0\1\167\66\0\1\312\56\0"+
    "\1\313\74\0\1\314\12\0\1\315\1\314\42\0\1\316"+
    "\31\0\1\316\3\0\12\207\1\245\46\207\1\213\5\207"+
    "\50\0\1\20\66\0\1\31\46\0\1\317\67\0\1\320"+
    "\31\0\1\320\53\0\1\116\43\0\1\321\102\0\1\314"+
    "\13\0\1\314\45\0\1\116\63\0\1\322\22\0\1\323"+
    "\6\0\1\322\41\0\1\206\65\0\1\324\66\0\1\325"+
    "\62\0\1\322\31\0\1\322\27\0\1\326\66\0\1\327"+
    "\67\0\1\330\66\0\1\331\102\0\1\116\13\0\1\116"+
    "\60\0\1\162\7\0\1\162\7\0";

  private static int [] zzUnpacktrans() {
    int [] result = new int[10725];
    int offset = 0;
    offset = zzUnpacktrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpacktrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String[] ZZ_ERROR_MSG = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\5\0\7\1\1\11\1\1\4\11\1\1\1\11\1\1"+
    "\1\11\2\1\1\11\2\1\1\11\3\1\1\11\1\1"+
    "\2\11\1\1\20\0\1\1\2\0\2\1\3\0\2\11"+
    "\1\1\1\0\1\1\14\0\1\1\20\0\1\11\5\0"+
    "\1\11\14\0\1\1\1\0\1\11\23\0\1\1\2\0"+
    "\1\11\7\0\1\1\1\0\1\1\7\0\1\1\2\0"+
    "\1\1\16\0\1\1\52\0";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[217];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** Number of newlines encountered up to the start of the matched text. */
  @SuppressWarnings("unused")
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  @SuppressWarnings("unused")
  protected int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  private boolean zzEOFDone;

  /* user code: */
    private int parentheses;
    private ArrayDeque<Integer> stateStack = new ArrayDeque<Integer>();
    private ArrayDeque<Character> blockStack = new ArrayDeque<Character>();
    private boolean stringIsInterpolated;
    private String rubyBlockEndDelimiter;
    private String currentStatement = "";
    private Set<String> naryStatements = Set.of("pushif", "prependif", "each", "eachelse", "eachwithindex", "eachwithindexelse", "props");

    private char flipBracket(char bracket){
        switch (bracket) {
            case '{': return '}';
            case '[': return ']';
            case '(': return ')';
            case '<': return '>';
            default: return bracket;
        }
    }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public RBladeLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** Returns the maximum size of the scanner buffer, which limits the size of tokens. */
  private int zzMaxBufferLen() {
    return Integer.MAX_VALUE;
  }

  /**  Whether the scanner buffer can grow to accommodate a larger token. */
  private boolean zzCanGrow() {
    return true;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  public final int getTokenStart() {
    return zzStartRead;
  }

  public final int getTokenEnd() {
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end, int initialState) {
    zzBuffer = buffer;
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      {@code false}, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position {@code pos} from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() {
    if (!zzEOFDone) {
      zzEOFDone = true;
    
      return;
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException
  {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { return RBladeTypes.HTML_TEMPLATE;
            }
          // fall through
          case 24: break;
          case 2:
            { return RBladeTypes.RUBY_EXPRESSION;
            }
          // fall through
          case 25: break;
          case 3:
            { stateStack.addFirst(STATE_RUBY_BLOCK);
                                            blockStack.addFirst(flipBracket(yycharat(yylength() - 1)));
                                            stringIsInterpolated = yycharat(0) == '"' || (yylength() == 3 && yytext().toString().substring(0, 2).equals("%Q"));
                                            yybegin(STATE_STRING_LITERAL);
                                            return RBladeTypes.RUBY_EXPRESSION;
            }
          // fall through
          case 26: break;
          case 4:
            { if (!blockStack.isEmpty() && yycharat(0) == '|' && blockStack.peekFirst() == '|') {
                                                blockStack.removeFirst();
                                            } else {
                                                blockStack.addFirst(flipBracket(yycharat(0)));
                                            }

                                            return RBladeTypes.RUBY_EXPRESSION;
            }
          // fall through
          case 27: break;
          case 5:
            { if (blockStack.isEmpty() && rubyBlockEndDelimiter.equals(yytext().toString().replace("_", "").toLowerCase())) {
                                                yypushback(yylength());
                                                yybegin(stateStack.removeFirst());
                                            } else {
                                                if (!blockStack.isEmpty() && blockStack.peekFirst() == yycharat(0)) {
                                                    blockStack.removeFirst();
                                                }

                                                return RBladeTypes.RUBY_EXPRESSION;
                                            }
            }
          // fall through
          case 28: break;
          case 6:
            { if (blockStack.isEmpty() && (naryStatements.contains(currentStatement))) {
                                            return RBladeTypes.RBLADE_STATEMENT_COMMA;
                                          } else {
                                            return RBladeTypes.RUBY_EXPRESSION;
                                          }
            }
          // fall through
          case 29: break;
          case 7:
            { if (blockStack.isEmpty() && currentStatement.equals("props")) {
                                                return RBladeTypes.RBLADE_STATEMENT_PROPS_COLON;
                                            }

                                            return RBladeTypes.RUBY_EXPRESSION;
            }
          // fall through
          case 30: break;
          case 8:
            { if (!blockStack.isEmpty() && blockStack.peekFirst() == yycharat(0)) {
                                                blockStack.removeFirst();
                                            }

                                            return RBladeTypes.RUBY_EXPRESSION;
            }
          // fall through
          case 31: break;
          case 9:
            { return TokenType.BAD_CHARACTER;
            }
          // fall through
          case 32: break;
          case 10:
            { yybegin(YYINITIAL);
                                          currentStatement = "";
                                          return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 33: break;
          case 11:
            { if (yycharat(0) == blockStack.getFirst()) {
                                                blockStack.removeFirst();
                                                yybegin(stateStack.removeFirst());
                                            }
                                            return RBladeTypes.RUBY_EXPRESSION;
            }
          // fall through
          case 34: break;
          case 12:
            { stateStack.addFirst(STATE_STRING_LITERAL_INTERPOLATION);
                                          blockStack.addFirst(flipBracket(yycharat(yylength() - 1)));
                                          yybegin(STATE_STRING_LITERAL);
                                          return RBladeTypes.RUBY_EXPRESSION;
            }
          // fall through
          case 35: break;
          case 13:
            { stateStack.addFirst(STATE_STRING_LITERAL_INTERPOLATION); return RBladeTypes.RUBY_EXPRESSION;
            }
          // fall through
          case 36: break;
          case 14:
            { yybegin(stateStack.removeFirst()); return RBladeTypes.RUBY_EXPRESSION;
            }
          // fall through
          case 37: break;
          case 15:
            { stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = "%>";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 38: break;
          case 16:
            { stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = "}}";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 39: break;
          case 17:
            { if (stringIsInterpolated) {
                                              stateStack.addFirst(STATE_STRING_LITERAL);
                                              yybegin(STATE_STRING_LITERAL_INTERPOLATION);
                                            }
                                            return RBladeTypes.RUBY_EXPRESSION;
            }
          // fall through
          case 40: break;
          case 18:
            { return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 41: break;
          case 19:
            { stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = "!!}";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 42: break;
          case 20:
            { stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = ")";
                                            yybegin(STATE_RUBY_BLOCK);
                                            currentStatement = yytext().toString().replaceAll("[^a-zA-Z]", "").toLowerCase();

                                            return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 43: break;
          case 21:
            { if (blockStack.isEmpty() && currentStatement.startsWith("each")) {
                                                return RBladeTypes.RBLADE_STATEMENT_EACH_IN;
                                            }

                                            return RBladeTypes.RUBY_EXPRESSION;
            }
          // fall through
          case 44: break;
          case 22:
            { return RBladeTypes.COMMENT;
            }
          // fall through
          case 45: break;
          case 23:
            { stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                            rubyBlockEndDelimiter = "@endruby";
                                            currentStatement = "ruby";
                                            yybegin(STATE_RUBY_BLOCK);
                                            return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 46: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
