package compilers.antlr.mail;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import grammar.addons.TreeNode;
import grammar.grammar.NonTerminal;

public class ANTLRMailParser  {
	
	public static void main(String[] args) {
		
		String s = args[0];
		ANTLRMailParser.parse(s);		
	}
	
	
	public static TreeNode parse(String s) {
		
		TreeNode root = new TreeNode(new NonTerminal("parse"), null, null);
		
		MailLexer lexer = new MailLexer(CharStreams.fromString(s));
		
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		MailParser parser = new MailParser(tokens, root);
		parser.removeErrorListeners();
		
		parser.parse();	
		
		if (parser.getNumberOfSyntaxErrors() > 0) { root = null; }
		
		return root;
		
	}
}