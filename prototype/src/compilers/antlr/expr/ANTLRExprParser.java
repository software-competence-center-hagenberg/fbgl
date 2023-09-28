package compilers.antlr.expr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import grammar.addons.TreeNode;
import grammar.grammar.NonTerminal;

public class ANTLRExprParser  {
	
	public static void main(String[] args) {
		
		String s = args[0];
		ANTLRExprParser.parse(s);		
	}
	
	
	public static TreeNode parse(String s) {
		
		TreeNode root = new TreeNode(new NonTerminal("parse"), null, null);
		
		ExprLexer lexer = new ExprLexer(CharStreams.fromString(s));
		
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ExprParser parser = new ExprParser(tokens, root);
		parser.removeErrorListeners();
		
		parser.parse();	
		
		if (parser.getNumberOfSyntaxErrors() > 0) { root = null; }
		
		return root;
		
	}
}