package compilers.antlr.mail;

// Generated from ./Mail.g4 by ANTLR 4.13.1
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
public class MailParser extends Parser {
	TreeNode node;
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, WS=29;
	public static final int
		RULE_parse = 0, RULE_mail = 1, RULE_string = 2, RULE_tag = 3, RULE_character = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "mail", "string", "tag", "character"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'@'", "'.'", "'a'", "'b'", "'c'", "'d'", "'e'", "'f'", "'g'", 
			"'h'", "'i'", "'j'", "'k'", "'l'", "'m'", "'n'", "'o'", "'p'", "'q'", 
			"'r'", "'s'", "'t'", "'u'", "'v'", "'w'", "'x'", "'y'", "'z'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, "WS"
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
	public String getGrammarFileName() { return "Mail.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MailParser(TokenStream input, TreeNode node) {
		super(input);
		this.node = node;
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParseContext extends ParserRuleContext {
		public MailContext mail() {
			return getRuleContext(MailContext.class,0);
		}
		public TerminalNode EOF() { return getToken(MailParser.EOF, 0); }
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MailListener ) ((MailListener)listener).enterParse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MailListener ) ((MailListener)listener).exitParse(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			mail();
			setState(11);
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
	public static class MailContext extends ParserRuleContext {
		public List<StringContext> string() {
			return getRuleContexts(StringContext.class);
		}
		public StringContext string(int i) {
			return getRuleContext(StringContext.class,i);
		}
		public TagContext tag() {
			return getRuleContext(TagContext.class,0);
		}
		public MailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mail; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MailListener ) ((MailListener)listener).enterMail(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MailListener ) ((MailListener)listener).exitMail(this);
		}
	}

	public final MailContext mail() throws RecognitionException {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		MailContext _localctx = new MailContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_mail);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			string();
			setState(14);
			match(T__0);
			setState(15);
			string();
			setState(16);
			match(T__1);
			setState(17);
			tag();
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
	public static class StringContext extends ParserRuleContext {
		public CharacterContext character() {
			return getRuleContext(CharacterContext.class,0);
		}
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MailListener ) ((MailListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MailListener ) ((MailListener)listener).exitString(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_string);
		try {
			setState(23);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(19);
				character();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(20);
				character();
				setState(21);
				string();
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
	public static class TagContext extends ParserRuleContext {
		public List<CharacterContext> character() {
			return getRuleContexts(CharacterContext.class);
		}
		public CharacterContext character(int i) {
			return getRuleContext(CharacterContext.class,i);
		}
		public TagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MailListener ) ((MailListener)listener).enterTag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MailListener ) ((MailListener)listener).exitTag(this);
		}
	}

	public final TagContext tag() throws RecognitionException {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		TagContext _localctx = new TagContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_tag);
		try {
			setState(32);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(25);
				character();
				setState(26);
				character();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(28);
				character();
				setState(29);
				character();
				setState(30);
				character();
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
	public static class CharacterContext extends ParserRuleContext {
		public CharacterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_character; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MailListener ) ((MailListener)listener).enterCharacter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MailListener ) ((MailListener)listener).exitCharacter(this);
		}
	}

	public final CharacterContext character() throws RecognitionException {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		CharacterContext _localctx = new CharacterContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_character);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 536870904L) != 0)) ) {
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
		"\u0004\u0001\u001d%\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0003\u0002\u0018\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003!\b\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0000\u0000\u0005\u0000\u0002\u0004\u0006"+
		"\b\u0000\u0001\u0001\u0000\u0003\u001c!\u0000\n\u0001\u0000\u0000\u0000"+
		"\u0002\r\u0001\u0000\u0000\u0000\u0004\u0017\u0001\u0000\u0000\u0000\u0006"+
		" \u0001\u0000\u0000\u0000\b\"\u0001\u0000\u0000\u0000\n\u000b\u0003\u0002"+
		"\u0001\u0000\u000b\f\u0005\u0000\u0000\u0001\f\u0001\u0001\u0000\u0000"+
		"\u0000\r\u000e\u0003\u0004\u0002\u0000\u000e\u000f\u0005\u0001\u0000\u0000"+
		"\u000f\u0010\u0003\u0004\u0002\u0000\u0010\u0011\u0005\u0002\u0000\u0000"+
		"\u0011\u0012\u0003\u0006\u0003\u0000\u0012\u0003\u0001\u0000\u0000\u0000"+
		"\u0013\u0018\u0003\b\u0004\u0000\u0014\u0015\u0003\b\u0004\u0000\u0015"+
		"\u0016\u0003\u0004\u0002\u0000\u0016\u0018\u0001\u0000\u0000\u0000\u0017"+
		"\u0013\u0001\u0000\u0000\u0000\u0017\u0014\u0001\u0000\u0000\u0000\u0018"+
		"\u0005\u0001\u0000\u0000\u0000\u0019\u001a\u0003\b\u0004\u0000\u001a\u001b"+
		"\u0003\b\u0004\u0000\u001b!\u0001\u0000\u0000\u0000\u001c\u001d\u0003"+
		"\b\u0004\u0000\u001d\u001e\u0003\b\u0004\u0000\u001e\u001f\u0003\b\u0004"+
		"\u0000\u001f!\u0001\u0000\u0000\u0000 \u0019\u0001\u0000\u0000\u0000 "+
		"\u001c\u0001\u0000\u0000\u0000!\u0007\u0001\u0000\u0000\u0000\"#\u0007"+
		"\u0000\u0000\u0000#\t\u0001\u0000\u0000\u0000\u0002\u0017 ";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}