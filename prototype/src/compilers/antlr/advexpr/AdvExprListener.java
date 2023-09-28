package compilers.antlr.advexpr;

// Generated from ./AdvExpr.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AdvExprParser}.
 */
public interface AdvExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AdvExprParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(AdvExprParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link AdvExprParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(AdvExprParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link AdvExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(AdvExprParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link AdvExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(AdvExprParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link AdvExprParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(AdvExprParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link AdvExprParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(AdvExprParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link AdvExprParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(AdvExprParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AdvExprParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(AdvExprParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link AdvExprParser#num}.
	 * @param ctx the parse tree
	 */
	void enterNum(AdvExprParser.NumContext ctx);
	/**
	 * Exit a parse tree produced by {@link AdvExprParser#num}.
	 * @param ctx the parse tree
	 */
	void exitNum(AdvExprParser.NumContext ctx);
	/**
	 * Enter a parse tree produced by {@link AdvExprParser#digit}.
	 * @param ctx the parse tree
	 */
	void enterDigit(AdvExprParser.DigitContext ctx);
	/**
	 * Exit a parse tree produced by {@link AdvExprParser#digit}.
	 * @param ctx the parse tree
	 */
	void exitDigit(AdvExprParser.DigitContext ctx);
}