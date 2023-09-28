package compilers.antlr.json;

// Generated from ./Json.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class JsonLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'t'", "'f'", "'n'", "'{'", "':'", "','", "'}'", "'['", "']'", 
			"'!'", "'^'", "'a'", "'b'", "'c'", "'0'", "'1'", "'2'", "'3'", "'.'", 
			"'E'", "'e'", "'+'", "'-'", "'_'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
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


	public JsonLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Json.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u0018a\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013"+
		"\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016"+
		"\u0001\u0017\u0001\u0017\u0000\u0000\u0018\u0001\u0001\u0003\u0002\u0005"+
		"\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n"+
		"\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011"+
		"#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017/\u0018\u0001\u0000\u0000`"+
		"\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000"+
		"\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000"+
		"\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000"+
		"\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001"+
		"\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000"+
		"\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000"+
		"\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/"+
		"\u0001\u0000\u0000\u0000\u00011\u0001\u0000\u0000\u0000\u00033\u0001\u0000"+
		"\u0000\u0000\u00055\u0001\u0000\u0000\u0000\u00077\u0001\u0000\u0000\u0000"+
		"\t9\u0001\u0000\u0000\u0000\u000b;\u0001\u0000\u0000\u0000\r=\u0001\u0000"+
		"\u0000\u0000\u000f?\u0001\u0000\u0000\u0000\u0011A\u0001\u0000\u0000\u0000"+
		"\u0013C\u0001\u0000\u0000\u0000\u0015E\u0001\u0000\u0000\u0000\u0017G"+
		"\u0001\u0000\u0000\u0000\u0019I\u0001\u0000\u0000\u0000\u001bK\u0001\u0000"+
		"\u0000\u0000\u001dM\u0001\u0000\u0000\u0000\u001fO\u0001\u0000\u0000\u0000"+
		"!Q\u0001\u0000\u0000\u0000#S\u0001\u0000\u0000\u0000%U\u0001\u0000\u0000"+
		"\u0000\'W\u0001\u0000\u0000\u0000)Y\u0001\u0000\u0000\u0000+[\u0001\u0000"+
		"\u0000\u0000-]\u0001\u0000\u0000\u0000/_\u0001\u0000\u0000\u000012\u0005"+
		"t\u0000\u00002\u0002\u0001\u0000\u0000\u000034\u0005f\u0000\u00004\u0004"+
		"\u0001\u0000\u0000\u000056\u0005n\u0000\u00006\u0006\u0001\u0000\u0000"+
		"\u000078\u0005{\u0000\u00008\b\u0001\u0000\u0000\u00009:\u0005:\u0000"+
		"\u0000:\n\u0001\u0000\u0000\u0000;<\u0005,\u0000\u0000<\f\u0001\u0000"+
		"\u0000\u0000=>\u0005}\u0000\u0000>\u000e\u0001\u0000\u0000\u0000?@\u0005"+
		"[\u0000\u0000@\u0010\u0001\u0000\u0000\u0000AB\u0005]\u0000\u0000B\u0012"+
		"\u0001\u0000\u0000\u0000CD\u0005!\u0000\u0000D\u0014\u0001\u0000\u0000"+
		"\u0000EF\u0005^\u0000\u0000F\u0016\u0001\u0000\u0000\u0000GH\u0005a\u0000"+
		"\u0000H\u0018\u0001\u0000\u0000\u0000IJ\u0005b\u0000\u0000J\u001a\u0001"+
		"\u0000\u0000\u0000KL\u0005c\u0000\u0000L\u001c\u0001\u0000\u0000\u0000"+
		"MN\u00050\u0000\u0000N\u001e\u0001\u0000\u0000\u0000OP\u00051\u0000\u0000"+
		"P \u0001\u0000\u0000\u0000QR\u00052\u0000\u0000R\"\u0001\u0000\u0000\u0000"+
		"ST\u00053\u0000\u0000T$\u0001\u0000\u0000\u0000UV\u0005.\u0000\u0000V"+
		"&\u0001\u0000\u0000\u0000WX\u0005E\u0000\u0000X(\u0001\u0000\u0000\u0000"+
		"YZ\u0005e\u0000\u0000Z*\u0001\u0000\u0000\u0000[\\\u0005+\u0000\u0000"+
		"\\,\u0001\u0000\u0000\u0000]^\u0005-\u0000\u0000^.\u0001\u0000\u0000\u0000"+
		"_`\u0005_\u0000\u0000`0\u0001\u0000\u0000\u0000\u0001\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}