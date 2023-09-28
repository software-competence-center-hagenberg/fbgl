package compilers.antlr.url;

// Generated from ./Url.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class UrlLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36;
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
			"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
			"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "T__32", 
			"T__33", "T__34", "T__35"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'h'", "'t'", "'p'", "':'", "'/'", "'?'", "'f'", "'e'", "'l'", 
			"'n'", "'g'", "'o'", "'r'", "'@'", "'.'", "'+'", "'a'", "'b'", "'c'", 
			"'A'", "'B'", "'C'", "'0'", "'1'", "'2'", "'3'", "'$'", "'-'", "'_'", 
			"'&'", "'!'", "'*'", "'('", "')'", "';'", "','"
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


	public UrlLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Url.g4"; }

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
		"\u0004\u0000$\u0091\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015"+
		"\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018"+
		"\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001b"+
		"\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001e"+
		"\u0001\u001e\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001!\u0001!\u0001"+
		"\"\u0001\"\u0001#\u0001#\u0000\u0000$\u0001\u0001\u0003\u0002\u0005\u0003"+
		"\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015"+
		"\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012"+
		"%\u0013\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b7\u001c"+
		"9\u001d;\u001e=\u001f? A!C\"E#G$\u0001\u0000\u0000\u0090\u0000\u0001\u0001"+
		"\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001"+
		"\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000"+
		"\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000"+
		"\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000"+
		"\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000"+
		"\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000"+
		"\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000"+
		"\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000"+
		"\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'"+
		"\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000"+
		"\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000"+
		"\u00001\u0001\u0000\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005"+
		"\u0001\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000"+
		"\u0000\u0000\u0000;\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000"+
		"\u0000?\u0001\u0000\u0000\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C"+
		"\u0001\u0000\u0000\u0000\u0000E\u0001\u0000\u0000\u0000\u0000G\u0001\u0000"+
		"\u0000\u0000\u0001I\u0001\u0000\u0000\u0000\u0003K\u0001\u0000\u0000\u0000"+
		"\u0005M\u0001\u0000\u0000\u0000\u0007O\u0001\u0000\u0000\u0000\tQ\u0001"+
		"\u0000\u0000\u0000\u000bS\u0001\u0000\u0000\u0000\rU\u0001\u0000\u0000"+
		"\u0000\u000fW\u0001\u0000\u0000\u0000\u0011Y\u0001\u0000\u0000\u0000\u0013"+
		"[\u0001\u0000\u0000\u0000\u0015]\u0001\u0000\u0000\u0000\u0017_\u0001"+
		"\u0000\u0000\u0000\u0019a\u0001\u0000\u0000\u0000\u001bc\u0001\u0000\u0000"+
		"\u0000\u001de\u0001\u0000\u0000\u0000\u001fg\u0001\u0000\u0000\u0000!"+
		"i\u0001\u0000\u0000\u0000#k\u0001\u0000\u0000\u0000%m\u0001\u0000\u0000"+
		"\u0000\'o\u0001\u0000\u0000\u0000)q\u0001\u0000\u0000\u0000+s\u0001\u0000"+
		"\u0000\u0000-u\u0001\u0000\u0000\u0000/w\u0001\u0000\u0000\u00001y\u0001"+
		"\u0000\u0000\u00003{\u0001\u0000\u0000\u00005}\u0001\u0000\u0000\u0000"+
		"7\u007f\u0001\u0000\u0000\u00009\u0081\u0001\u0000\u0000\u0000;\u0083"+
		"\u0001\u0000\u0000\u0000=\u0085\u0001\u0000\u0000\u0000?\u0087\u0001\u0000"+
		"\u0000\u0000A\u0089\u0001\u0000\u0000\u0000C\u008b\u0001\u0000\u0000\u0000"+
		"E\u008d\u0001\u0000\u0000\u0000G\u008f\u0001\u0000\u0000\u0000IJ\u0005"+
		"h\u0000\u0000J\u0002\u0001\u0000\u0000\u0000KL\u0005t\u0000\u0000L\u0004"+
		"\u0001\u0000\u0000\u0000MN\u0005p\u0000\u0000N\u0006\u0001\u0000\u0000"+
		"\u0000OP\u0005:\u0000\u0000P\b\u0001\u0000\u0000\u0000QR\u0005/\u0000"+
		"\u0000R\n\u0001\u0000\u0000\u0000ST\u0005?\u0000\u0000T\f\u0001\u0000"+
		"\u0000\u0000UV\u0005f\u0000\u0000V\u000e\u0001\u0000\u0000\u0000WX\u0005"+
		"e\u0000\u0000X\u0010\u0001\u0000\u0000\u0000YZ\u0005l\u0000\u0000Z\u0012"+
		"\u0001\u0000\u0000\u0000[\\\u0005n\u0000\u0000\\\u0014\u0001\u0000\u0000"+
		"\u0000]^\u0005g\u0000\u0000^\u0016\u0001\u0000\u0000\u0000_`\u0005o\u0000"+
		"\u0000`\u0018\u0001\u0000\u0000\u0000ab\u0005r\u0000\u0000b\u001a\u0001"+
		"\u0000\u0000\u0000cd\u0005@\u0000\u0000d\u001c\u0001\u0000\u0000\u0000"+
		"ef\u0005.\u0000\u0000f\u001e\u0001\u0000\u0000\u0000gh\u0005+\u0000\u0000"+
		"h \u0001\u0000\u0000\u0000ij\u0005a\u0000\u0000j\"\u0001\u0000\u0000\u0000"+
		"kl\u0005b\u0000\u0000l$\u0001\u0000\u0000\u0000mn\u0005c\u0000\u0000n"+
		"&\u0001\u0000\u0000\u0000op\u0005A\u0000\u0000p(\u0001\u0000\u0000\u0000"+
		"qr\u0005B\u0000\u0000r*\u0001\u0000\u0000\u0000st\u0005C\u0000\u0000t"+
		",\u0001\u0000\u0000\u0000uv\u00050\u0000\u0000v.\u0001\u0000\u0000\u0000"+
		"wx\u00051\u0000\u0000x0\u0001\u0000\u0000\u0000yz\u00052\u0000\u0000z"+
		"2\u0001\u0000\u0000\u0000{|\u00053\u0000\u0000|4\u0001\u0000\u0000\u0000"+
		"}~\u0005$\u0000\u0000~6\u0001\u0000\u0000\u0000\u007f\u0080\u0005-\u0000"+
		"\u0000\u00808\u0001\u0000\u0000\u0000\u0081\u0082\u0005_\u0000\u0000\u0082"+
		":\u0001\u0000\u0000\u0000\u0083\u0084\u0005&\u0000\u0000\u0084<\u0001"+
		"\u0000\u0000\u0000\u0085\u0086\u0005!\u0000\u0000\u0086>\u0001\u0000\u0000"+
		"\u0000\u0087\u0088\u0005*\u0000\u0000\u0088@\u0001\u0000\u0000\u0000\u0089"+
		"\u008a\u0005(\u0000\u0000\u008aB\u0001\u0000\u0000\u0000\u008b\u008c\u0005"+
		")\u0000\u0000\u008cD\u0001\u0000\u0000\u0000\u008d\u008e\u0005;\u0000"+
		"\u0000\u008eF\u0001\u0000\u0000\u0000\u008f\u0090\u0005,\u0000\u0000\u0090"+
		"H\u0001\u0000\u0000\u0000\u0001\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}