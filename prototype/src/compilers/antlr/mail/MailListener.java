package compilers.antlr.mail;

// Generated from ./Mail.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MailParser}.
 */
public interface MailListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MailParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(MailParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MailParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(MailParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MailParser#mail}.
	 * @param ctx the parse tree
	 */
	void enterMail(MailParser.MailContext ctx);
	/**
	 * Exit a parse tree produced by {@link MailParser#mail}.
	 * @param ctx the parse tree
	 */
	void exitMail(MailParser.MailContext ctx);
	/**
	 * Enter a parse tree produced by {@link MailParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(MailParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link MailParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(MailParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link MailParser#tag}.
	 * @param ctx the parse tree
	 */
	void enterTag(MailParser.TagContext ctx);
	/**
	 * Exit a parse tree produced by {@link MailParser#tag}.
	 * @param ctx the parse tree
	 */
	void exitTag(MailParser.TagContext ctx);
	/**
	 * Enter a parse tree produced by {@link MailParser#character}.
	 * @param ctx the parse tree
	 */
	void enterCharacter(MailParser.CharacterContext ctx);
	/**
	 * Exit a parse tree produced by {@link MailParser#character}.
	 * @param ctx the parse tree
	 */
	void exitCharacter(MailParser.CharacterContext ctx);
}