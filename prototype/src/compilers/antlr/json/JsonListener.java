package compilers.antlr.json;

// Generated from ./Json.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JsonParser}.
 */
public interface JsonListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JsonParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(JsonParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(JsonParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#json}.
	 * @param ctx the parse tree
	 */
	void enterJson(JsonParser.JsonContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#json}.
	 * @param ctx the parse tree
	 */
	void exitJson(JsonParser.JsonContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(JsonParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(JsonParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(JsonParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(JsonParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#members}.
	 * @param ctx the parse tree
	 */
	void enterMembers(JsonParser.MembersContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#members}.
	 * @param ctx the parse tree
	 */
	void exitMembers(JsonParser.MembersContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#member}.
	 * @param ctx the parse tree
	 */
	void enterMember(JsonParser.MemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#member}.
	 * @param ctx the parse tree
	 */
	void exitMember(JsonParser.MemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(JsonParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(JsonParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#elements}.
	 * @param ctx the parse tree
	 */
	void enterElements(JsonParser.ElementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#elements}.
	 * @param ctx the parse tree
	 */
	void exitElements(JsonParser.ElementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(JsonParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(JsonParser.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(JsonParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(JsonParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#characters}.
	 * @param ctx the parse tree
	 */
	void enterCharacters(JsonParser.CharactersContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#characters}.
	 * @param ctx the parse tree
	 */
	void exitCharacters(JsonParser.CharactersContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#character}.
	 * @param ctx the parse tree
	 */
	void enterCharacter(JsonParser.CharacterContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#character}.
	 * @param ctx the parse tree
	 */
	void exitCharacter(JsonParser.CharacterContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(JsonParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(JsonParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(JsonParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(JsonParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#digits}.
	 * @param ctx the parse tree
	 */
	void enterDigits(JsonParser.DigitsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#digits}.
	 * @param ctx the parse tree
	 */
	void exitDigits(JsonParser.DigitsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#digit}.
	 * @param ctx the parse tree
	 */
	void enterDigit(JsonParser.DigitContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#digit}.
	 * @param ctx the parse tree
	 */
	void exitDigit(JsonParser.DigitContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#onenine}.
	 * @param ctx the parse tree
	 */
	void enterOnenine(JsonParser.OnenineContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#onenine}.
	 * @param ctx the parse tree
	 */
	void exitOnenine(JsonParser.OnenineContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#fraction}.
	 * @param ctx the parse tree
	 */
	void enterFraction(JsonParser.FractionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#fraction}.
	 * @param ctx the parse tree
	 */
	void exitFraction(JsonParser.FractionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#exponent}.
	 * @param ctx the parse tree
	 */
	void enterExponent(JsonParser.ExponentContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#exponent}.
	 * @param ctx the parse tree
	 */
	void exitExponent(JsonParser.ExponentContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#sign}.
	 * @param ctx the parse tree
	 */
	void enterSign(JsonParser.SignContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#sign}.
	 * @param ctx the parse tree
	 */
	void exitSign(JsonParser.SignContext ctx);
	/**
	 * Enter a parse tree produced by {@link JsonParser#ws}.
	 * @param ctx the parse tree
	 */
	void enterWs(JsonParser.WsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JsonParser#ws}.
	 * @param ctx the parse tree
	 */
	void exitWs(JsonParser.WsContext ctx);
}