package compilers.antlr.url;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import grammar.addons.TreeNode;
import grammar.grammar.NonTerminal;

public class ANTLRUrlParser  {
	
	public static void main(String[] args) {
		
		String s = args[0];
		ANTLRUrlParser.parse(s);		
	}
	
	
	public static TreeNode parse(String s) {
		
		TreeNode root = new TreeNode(new NonTerminal("parse"), null, null);
		
		UrlLexer lexer = new UrlLexer(CharStreams.fromString(s));
		
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		UrlParser parser = new UrlParser(tokens, root);
		parser.removeErrorListeners();
		lexer.removeErrorListeners();
		
		parser.parse();	
		
		if (parser.getNumberOfSyntaxErrors() > 0) { root = null; }
		
		return root;
		
	}
}