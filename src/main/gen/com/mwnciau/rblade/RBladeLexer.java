// Generated by JFlex 1.9.1 http://jflex.de/  (tweaked for IntelliJ platform)
// source: RBlade.flex

// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.mwnciau.rblade;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.mwnciau.rblade.psi.RBladeTypes;
import com.intellij.psi.TokenType;import kotlinx.html.RUBY;
import java.util.ArrayDeque;


class RBladeLexer implements FlexLexer {

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
    "\1\0\37\u0100\1\u0200\267\u0100\10\u0300\u1020\u0100";

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
    "\1\0\1\6\1\0\1\4\1\7\1\10\3\0\1\11"+
    "\16\0\1\12\1\13\1\14\1\0\1\15\1\16\1\17"+
    "\1\16\1\20\1\21\3\16\1\22\4\16\1\23\2\16"+
    "\1\22\1\24\2\16\1\25\1\16\1\22\1\16\1\26"+
    "\1\16\1\0\1\27\2\0\1\30\1\0\1\16\1\17"+
    "\1\16\1\20\1\21\3\16\1\22\4\16\1\23\2\16"+
    "\1\22\1\31\1\22\1\16\1\25\1\16\2\22\1\26"+
    "\1\16\1\32\1\0\1\33\7\0\1\2\u01a2\0\2\2"+
    "\326\0\u0100\2";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[1024];
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
    "\5\0\4\1\2\2\1\3\1\2\1\4\1\5\2\2"+
    "\3\6\1\7\2\6\3\10\1\2\1\11\1\2\1\12"+
    "\1\13\1\14\2\15\1\0\1\16\1\2\1\3\3\0"+
    "\1\17\1\2\1\11\1\0\1\14\1\0\1\20\1\15"+
    "\1\21\4\0\1\14\1\15\3\0\1\22\1\23\13\0"+
    "\1\22";

  private static int [] zzUnpackAction() {
    int [] result = new int[73];
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
    "\0\0\0\34\0\70\0\124\0\160\0\214\0\250\0\304"+
    "\0\340\0\374\0\u0118\0\u0134\0\u0150\0\u0134\0\u0134\0\u016c"+
    "\0\u0188\0\u0134\0\u01a4\0\u01c0\0\u0134\0\u01dc\0\u01f8\0\u0134"+
    "\0\u0214\0\u0230\0\u024c\0\u0134\0\u0268\0\u0134\0\u0134\0\u0284"+
    "\0\u02a0\0\u02bc\0\u02d8\0\u02f4\0\u0310\0\u032c\0\u0348\0\u01f8"+
    "\0\u0364\0\u0134\0\u0134\0\u0380\0\u039c\0\u03b8\0\u03d4\0\u0134"+
    "\0\u03f0\0\u0134\0\u040c\0\u0428\0\u0444\0\u0460\0\u0134\0\u047c"+
    "\0\u0498\0\u04b4\0\u04d0\0\u039c\0\u02a0\0\u04ec\0\u0508\0\u0524"+
    "\0\u0540\0\u055c\0\u0578\0\u0594\0\u05b0\0\u05cc\0\u05e8\0\u0604"+
    "\0\u0498";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[73];
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
    "\12\6\1\7\2\6\1\10\14\6\1\11\1\6\3\12"+
    "\1\13\1\14\1\12\1\15\1\16\1\17\4\12\1\20"+
    "\15\12\1\21\3\22\1\23\2\22\1\24\1\22\1\25"+
    "\4\22\1\26\15\22\1\27\2\30\1\22\2\30\1\31"+
    "\21\30\1\32\4\30\4\33\1\34\1\33\1\35\23\33"+
    "\1\36\1\37\12\6\1\0\2\6\1\0\14\6\1\0"+
    "\1\6\6\0\1\40\43\0\6\41\1\42\2\41\1\0"+
    "\1\41\1\42\5\0\1\43\26\0\1\44\1\0\4\12"+
    "\1\0\1\12\3\0\4\12\1\0\15\12\1\0\3\12"+
    "\1\45\1\0\1\12\3\0\4\12\1\0\15\12\35\0"+
    "\2\14\1\0\11\14\1\17\5\14\1\46\6\14\1\46"+
    "\2\14\21\0\1\47\45\0\1\17\3\0\1\50\44\0"+
    "\1\25\40\0\1\51\45\0\1\25\32\0\1\52\1\0"+
    "\2\53\1\0\31\53\4\33\1\0\1\33\1\0\23\33"+
    "\2\0\2\34\1\0\17\34\1\54\6\34\1\54\2\34"+
    "\5\0\1\55\5\0\1\56\21\0\1\57\5\0\1\60"+
    "\6\0\11\41\1\0\2\41\3\0\1\57\5\0\1\60"+
    "\6\0\7\41\1\61\1\41\1\0\2\41\5\0\1\62"+
    "\41\0\1\63\22\0\4\12\1\0\1\12\3\0\4\12"+
    "\1\0\15\12\1\17\2\14\1\0\31\14\23\0\1\64"+
    "\33\0\1\65\10\0\2\34\1\0\31\34\6\55\1\66"+
    "\25\55\13\0\1\67\21\0\1\57\5\0\1\60\25\0"+
    "\1\57\5\0\1\60\6\0\1\41\1\70\7\41\1\0"+
    "\2\41\13\0\1\71\42\0\1\72\33\0\1\73\13\0"+
    "\6\55\1\66\5\55\1\74\17\55\1\0\1\57\5\0"+
    "\1\60\6\0\10\41\1\75\1\0\2\41\2\0\11\71"+
    "\1\76\22\71\24\0\1\77\3\0\1\100\1\77\26\0"+
    "\1\101\3\0\1\102\1\101\2\0\11\71\1\103\22\71"+
    "\25\0\1\104\32\0\1\77\4\0\1\77\27\0\1\105"+
    "\32\0\1\101\4\0\1\101\2\0\11\71\1\103\21\71"+
    "\1\106\17\0\1\107\33\0\1\110\14\0\11\71\1\76"+
    "\21\71\1\111\26\0\1\17\33\0\1\25\5\0";

  private static int [] zzUnpacktrans() {
    int [] result = new int[1568];
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
    "\5\0\6\1\1\11\1\1\2\11\2\1\1\11\2\1"+
    "\1\11\2\1\1\11\3\1\1\11\1\1\2\11\3\1"+
    "\1\0\3\1\3\0\2\11\1\1\1\0\1\1\1\0"+
    "\1\11\1\1\1\11\4\0\1\11\1\1\3\0\2\1"+
    "\13\0\1\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[73];
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
    private ArrayDeque<Character> stringDelimiterStack = new ArrayDeque<Character>();
    private boolean stringIsInterpolated;
    private String rubyBlockEndDelimiter;

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
  RBladeLexer(java.io.Reader in) {
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
          case 20: break;
          case 2:
            { 
            }
          // fall through
          case 21: break;
          case 3:
            { stateStack.addFirst(STATE_RUBY_BLOCK);
                                        stringDelimiterStack.addFirst(flipBracket(yycharat(yylength() - 1)));
                                        stringIsInterpolated = yycharat(0) == '"' || (yylength() == 3 && yytext().toString().substring(0, 2) == "%Q");
                                        yybegin(STATE_STRING_LITERAL);
            }
          // fall through
          case 22: break;
          case 4:
            { stateStack.addFirst(STATE_RUBY_BLOCK);
            }
          // fall through
          case 23: break;
          case 5:
            { if (
                                          stateStack.getFirst() == STATE_RUBY_BLOCK_END
                                          && rubyBlockEndDelimiter.equals(yytext().toString().replace("_", "").toLowerCase())
                                        ) {
                                            yypushback(yylength());
                                            yybegin(stateStack.removeFirst());
                                            return RBladeTypes.RUBY_EXPRESSION;
                                        }
                                        if (yycharat(0) == ')') {
                                            yybegin(stateStack.removeFirst());
                                        }
            }
          // fall through
          case 24: break;
          case 6:
            { return TokenType.BAD_CHARACTER;
            }
          // fall through
          case 25: break;
          case 7:
            { yybegin(YYINITIAL);
                                      return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 26: break;
          case 8:
            { if (yycharat(0) == stringDelimiterStack.getFirst()) {
                                            stringDelimiterStack.removeFirst();
                                            yybegin(stateStack.removeFirst());
                                        }
            }
          // fall through
          case 27: break;
          case 9:
            { stateStack.addFirst(STATE_STRING_LITERAL_INTERPOLATION);
                                      stringDelimiterStack.addFirst(flipBracket(yycharat(yylength() - 1)));
                                      yybegin(STATE_STRING_LITERAL);
            }
          // fall through
          case 28: break;
          case 10:
            { stateStack.addFirst(STATE_STRING_LITERAL_INTERPOLATION);
            }
          // fall through
          case 29: break;
          case 11:
            { yybegin(stateStack.removeFirst());
            }
          // fall through
          case 30: break;
          case 12:
            { stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                        rubyBlockEndDelimiter = "%>";
                                        yybegin(STATE_RUBY_BLOCK);
                                        return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 31: break;
          case 13:
            { return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 32: break;
          case 14:
            { stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                        rubyBlockEndDelimiter = "}}";
                                        yybegin(STATE_RUBY_BLOCK);
                                        return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 33: break;
          case 15:
            { if (stringIsInterpolated) {
                                          stateStack.addFirst(STATE_STRING_LITERAL);
                                          yybegin(STATE_STRING_LITERAL_INTERPOLATION);
                                        }
            }
          // fall through
          case 34: break;
          case 16:
            { stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                        rubyBlockEndDelimiter = ")";
                                        yybegin(STATE_RUBY_BLOCK);
                                        return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 35: break;
          case 17:
            { stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                        rubyBlockEndDelimiter = "!!}";
                                        yybegin(STATE_RUBY_BLOCK);
                                        return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 36: break;
          case 18:
            { return RBladeTypes.COMMENT;
            }
          // fall through
          case 37: break;
          case 19:
            { stateStack.addFirst(STATE_RUBY_BLOCK_END);
                                        rubyBlockEndDelimiter = "@endruby";
                                        yybegin(STATE_RUBY_BLOCK);
                                        return RBladeTypes.RBLADE_STATEMENT;
            }
          // fall through
          case 38: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
