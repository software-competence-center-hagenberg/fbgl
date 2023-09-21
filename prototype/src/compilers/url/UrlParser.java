package compilers.url;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import grammar.addons.TreeNode;
import grammar.grammar.NonTerminal;

public class UrlParser {
	
	public static TreeNode parse(String s) {
		InputStream i = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
		
		Scanner scanner = new Scanner(i);
		
		TreeNode root = new TreeNode(new NonTerminal("parse"), null, null);
		
		Parser parser = new Parser(scanner, root);
		parser.Parse();

		if (parser.getErrorCount() == 0) {
			return root;
		} else {
			return null;
		}
	}

	public static void main(String[] args) throws IOException {

		String s = args[0];
		UrlParser.parse(s);	
	}
}
