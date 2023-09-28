package compilers.antlr.url;

// Generated from ./Url.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link UrlParser}.
 */
public interface UrlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link UrlParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(UrlParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(UrlParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#url}.
	 * @param ctx the parse tree
	 */
	void enterUrl(UrlParser.UrlContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#url}.
	 * @param ctx the parse tree
	 */
	void exitUrl(UrlParser.UrlContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#httpaddress}.
	 * @param ctx the parse tree
	 */
	void enterHttpaddress(UrlParser.HttpaddressContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#httpaddress}.
	 * @param ctx the parse tree
	 */
	void exitHttpaddress(UrlParser.HttpaddressContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#ftpaddress}.
	 * @param ctx the parse tree
	 */
	void enterFtpaddress(UrlParser.FtpaddressContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#ftpaddress}.
	 * @param ctx the parse tree
	 */
	void exitFtpaddress(UrlParser.FtpaddressContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#telnetaddress}.
	 * @param ctx the parse tree
	 */
	void enterTelnetaddress(UrlParser.TelnetaddressContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#telnetaddress}.
	 * @param ctx the parse tree
	 */
	void exitTelnetaddress(UrlParser.TelnetaddressContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#gopheraddress}.
	 * @param ctx the parse tree
	 */
	void enterGopheraddress(UrlParser.GopheraddressContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#gopheraddress}.
	 * @param ctx the parse tree
	 */
	void exitGopheraddress(UrlParser.GopheraddressContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#login}.
	 * @param ctx the parse tree
	 */
	void enterLogin(UrlParser.LoginContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#login}.
	 * @param ctx the parse tree
	 */
	void exitLogin(UrlParser.LoginContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#hostport}.
	 * @param ctx the parse tree
	 */
	void enterHostport(UrlParser.HostportContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#hostport}.
	 * @param ctx the parse tree
	 */
	void exitHostport(UrlParser.HostportContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#host}.
	 * @param ctx the parse tree
	 */
	void enterHost(UrlParser.HostContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#host}.
	 * @param ctx the parse tree
	 */
	void exitHost(UrlParser.HostContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#hostname}.
	 * @param ctx the parse tree
	 */
	void enterHostname(UrlParser.HostnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#hostname}.
	 * @param ctx the parse tree
	 */
	void exitHostname(UrlParser.HostnameContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#hostnumber}.
	 * @param ctx the parse tree
	 */
	void enterHostnumber(UrlParser.HostnumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#hostnumber}.
	 * @param ctx the parse tree
	 */
	void exitHostnumber(UrlParser.HostnumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#port}.
	 * @param ctx the parse tree
	 */
	void enterPort(UrlParser.PortContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#port}.
	 * @param ctx the parse tree
	 */
	void exitPort(UrlParser.PortContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(UrlParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(UrlParser.PathContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#search}.
	 * @param ctx the parse tree
	 */
	void enterSearch(UrlParser.SearchContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#search}.
	 * @param ctx the parse tree
	 */
	void exitSearch(UrlParser.SearchContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(UrlParser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(UrlParser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#user}.
	 * @param ctx the parse tree
	 */
	void enterUser(UrlParser.UserContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#user}.
	 * @param ctx the parse tree
	 */
	void exitUser(UrlParser.UserContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#password}.
	 * @param ctx the parse tree
	 */
	void enterPassword(UrlParser.PasswordContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#password}.
	 * @param ctx the parse tree
	 */
	void exitPassword(UrlParser.PasswordContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#gtype}.
	 * @param ctx the parse tree
	 */
	void enterGtype(UrlParser.GtypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#gtype}.
	 * @param ctx the parse tree
	 */
	void exitGtype(UrlParser.GtypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#xalpha}.
	 * @param ctx the parse tree
	 */
	void enterXalpha(UrlParser.XalphaContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#xalpha}.
	 * @param ctx the parse tree
	 */
	void exitXalpha(UrlParser.XalphaContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#xalphas}.
	 * @param ctx the parse tree
	 */
	void enterXalphas(UrlParser.XalphasContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#xalphas}.
	 * @param ctx the parse tree
	 */
	void exitXalphas(UrlParser.XalphasContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#ialpha}.
	 * @param ctx the parse tree
	 */
	void enterIalpha(UrlParser.IalphaContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#ialpha}.
	 * @param ctx the parse tree
	 */
	void exitIalpha(UrlParser.IalphaContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#alpha}.
	 * @param ctx the parse tree
	 */
	void enterAlpha(UrlParser.AlphaContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#alpha}.
	 * @param ctx the parse tree
	 */
	void exitAlpha(UrlParser.AlphaContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#digit}.
	 * @param ctx the parse tree
	 */
	void enterDigit(UrlParser.DigitContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#digit}.
	 * @param ctx the parse tree
	 */
	void exitDigit(UrlParser.DigitContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#safe}.
	 * @param ctx the parse tree
	 */
	void enterSafe(UrlParser.SafeContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#safe}.
	 * @param ctx the parse tree
	 */
	void exitSafe(UrlParser.SafeContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#extra}.
	 * @param ctx the parse tree
	 */
	void enterExtra(UrlParser.ExtraContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#extra}.
	 * @param ctx the parse tree
	 */
	void exitExtra(UrlParser.ExtraContext ctx);
	/**
	 * Enter a parse tree produced by {@link UrlParser#digits}.
	 * @param ctx the parse tree
	 */
	void enterDigits(UrlParser.DigitsContext ctx);
	/**
	 * Exit a parse tree produced by {@link UrlParser#digits}.
	 * @param ctx the parse tree
	 */
	void exitDigits(UrlParser.DigitsContext ctx);
}