package compilers.antlr.advexpr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import grammar.addons.TreeNode;
import grammar.grammar.NonTerminal;

public class ANTLRAdvExprParser  {
	
	public static void main(String[] args) {
		
		String s = args[0];
		ANTLRAdvExprParser.parse(s);		
	}
	
	
	public static TreeNode parse(String s) {
		
		TreeNode root = new TreeNode(new NonTerminal("parse"), null, null);
		
		AdvExprLexer lexer = new AdvExprLexer(CharStreams.fromString(s));
		
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		AdvExprParser parser = new AdvExprParser(tokens, root);
		parser.removeErrorListeners();
		
		parser.parse();	
		
		if (parser.getNumberOfSyntaxErrors() > 0) { root = null; }
		
		return root;
		
	}
}