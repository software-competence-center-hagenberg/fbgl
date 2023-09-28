package compilers.antlr.advexpr;

// Generated from ./AdvExpr.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;

import grammar.addons.TreeNode;
import grammar.grammar.NonTerminal;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class AdvExprParser extends Parser {
	TreeNode node;
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		WS=10;
	public static final int
		RULE_parse = 0, RULE_expr = 1, RULE_term = 2, RULE_factor = 3, RULE_num = 4, 
		RULE_digit = 5;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "expr", "term", "factor", "num", "digit"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'('", "')'", "'1'", "'2'", "'3'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "AdvExpr.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AdvExprParser(TokenStream input, TreeNode node) {
		super(input);
		this.node = node;
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParseContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EOF() { return getToken(AdvExprParser.EOF, 0); }
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AdvExprListener ) ((AdvExprListener)listener).enterParse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AdvExprListener ) ((AdvExprListener)listener).exitParse(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12);
			expr();
			setState(13);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AdvExprListener ) ((AdvExprListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AdvExprListener ) ((AdvExprListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_expr);
		int _la;
		try {
			setState(20);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(15);
				term();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(16);
				term();
				setState(17);
				_la = _input.LA(1);
				if ( !(_la==T__0 || _la==T__1) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(18);
				expr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END	
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public FactorContext factor() {
			return getRuleContext(FactorContext.class,0);
		}
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AdvExprListener ) ((AdvExprListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AdvExprListener ) ((AdvExprListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_term);
		int _la;
		try {
			setState(27);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(22);
				factor();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(23);
				factor();
				setState(24);
				_la = _input.LA(1);
				if ( !(_la==T__2 || _la==T__3) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(25);
				term();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END	
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FactorContext extends ParserRuleContext {
		public NumContext num() {
			return getRuleContext(NumContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AdvExprListener ) ((AdvExprListener)listener).enterFactor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AdvExprListener ) ((AdvExprListener)listener).exitFactor(this);
		}
	}

	public final FactorContext factor() throws RecognitionException {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_factor);
		try {
			setState(34);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__7:
			case T__8:
				enterOuterAlt(_localctx, 1);
				{
				setState(29);
				num();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 2);
				{
				setState(30);
				match(T__4);
				setState(31);
				expr();
				setState(32);
				match(T__5);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END	
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NumContext extends ParserRuleContext {
		public DigitContext digit() {
			return getRuleContext(DigitContext.class,0);
		}
		public NumContext num() {
			return getRuleContext(NumContext.class,0);
		}
		public NumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_num; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AdvExprListener ) ((AdvExprListener)listener).enterNum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AdvExprListener ) ((AdvExprListener)listener).exitNum(this);
		}
	}

	public final NumContext num() throws RecognitionException {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		NumContext _localctx = new NumContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_num);
		try {
			setState(40);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(36);
				digit();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(37);
				digit();
				setState(38);
				num();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END	
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DigitContext extends ParserRuleContext {
		public DigitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_digit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AdvExprListener ) ((AdvExprListener)listener).enterDigit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AdvExprListener ) ((AdvExprListener)listener).exitDigit(this);
		}
	}

	public final DigitContext digit() throws RecognitionException {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		DigitContext _localctx = new DigitContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_digit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 896L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END	
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\n-\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u0015\b\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002\u001c"+
		"\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003"+
		"\u0003#\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003"+
		"\u0004)\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0000\u0000\u0006"+
		"\u0000\u0002\u0004\u0006\b\n\u0000\u0003\u0001\u0000\u0001\u0002\u0001"+
		"\u0000\u0003\u0004\u0001\u0000\u0007\t*\u0000\f\u0001\u0000\u0000\u0000"+
		"\u0002\u0014\u0001\u0000\u0000\u0000\u0004\u001b\u0001\u0000\u0000\u0000"+
		"\u0006\"\u0001\u0000\u0000\u0000\b(\u0001\u0000\u0000\u0000\n*\u0001\u0000"+
		"\u0000\u0000\f\r\u0003\u0002\u0001\u0000\r\u000e\u0005\u0000\u0000\u0001"+
		"\u000e\u0001\u0001\u0000\u0000\u0000\u000f\u0015\u0003\u0004\u0002\u0000"+
		"\u0010\u0011\u0003\u0004\u0002\u0000\u0011\u0012\u0007\u0000\u0000\u0000"+
		"\u0012\u0013\u0003\u0002\u0001\u0000\u0013\u0015\u0001\u0000\u0000\u0000"+
		"\u0014\u000f\u0001\u0000\u0000\u0000\u0014\u0010\u0001\u0000\u0000\u0000"+
		"\u0015\u0003\u0001\u0000\u0000\u0000\u0016\u001c\u0003\u0006\u0003\u0000"+
		"\u0017\u0018\u0003\u0006\u0003\u0000\u0018\u0019\u0007\u0001\u0000\u0000"+
		"\u0019\u001a\u0003\u0004\u0002\u0000\u001a\u001c\u0001\u0000\u0000\u0000"+
		"\u001b\u0016\u0001\u0000\u0000\u0000\u001b\u0017\u0001\u0000\u0000\u0000"+
		"\u001c\u0005\u0001\u0000\u0000\u0000\u001d#\u0003\b\u0004\u0000\u001e"+
		"\u001f\u0005\u0005\u0000\u0000\u001f \u0003\u0002\u0001\u0000 !\u0005"+
		"\u0006\u0000\u0000!#\u0001\u0000\u0000\u0000\"\u001d\u0001\u0000\u0000"+
		"\u0000\"\u001e\u0001\u0000\u0000\u0000#\u0007\u0001\u0000\u0000\u0000"+
		"$)\u0003\n\u0005\u0000%&\u0003\n\u0005\u0000&\'\u0003\b\u0004\u0000\'"+
		")\u0001\u0000\u0000\u0000($\u0001\u0000\u0000\u0000(%\u0001\u0000\u0000"+
		"\u0000)\t\u0001\u0000\u0000\u0000*+\u0007\u0002\u0000\u0000+\u000b\u0001"+
		"\u0000\u0000\u0000\u0004\u0014\u001b\"(";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}