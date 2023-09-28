package compilers.antlr.hello;

// Generated from ./Hello.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HelloParser}.
 */
public interface HelloListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HelloParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(HelloParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(HelloParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#hello}.
	 * @param ctx the parse tree
	 */
	void enterHello(HelloParser.HelloContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#hello}.
	 * @param ctx the parse tree
	 */
	void exitHello(HelloParser.HelloContext ctx);
}