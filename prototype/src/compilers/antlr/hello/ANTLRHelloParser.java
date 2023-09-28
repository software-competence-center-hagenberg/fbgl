package compilers.antlr.hello;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import grammar.addons.TreeNode;
import grammar.grammar.NonTerminal;

public class ANTLRHelloParser  {
	
	public static void main(String[] args) {
		
		String s = args[0];
		ANTLRHelloParser.parse(s);		
	}
	
	
	public static TreeNode parse(String s) {
		
		TreeNode root = new TreeNode(new NonTerminal("parse"), null, null);
		
		HelloLexer lexer = new HelloLexer(CharStreams.fromString(s));
		
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		HelloParser parser = new HelloParser(tokens, root);
		parser.removeErrorListeners();
		
		parser.parse();	
		
		if (parser.getNumberOfSyntaxErrors() > 0) { root = null; }
		
		return root;
		
	}
}