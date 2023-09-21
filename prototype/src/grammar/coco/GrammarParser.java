package grammar.coco;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import grammar.grammar.Grammar;

public class GrammarParser {
	
	public static Grammar parse(File pascalSourceFile) {
		return parse(pascalSourceFile, "");
	}
	
	
	public static Grammar parse(File pascalSourceFile, String any) {
		// 1. Parsing
		String srcName = pascalSourceFile.getAbsolutePath();
		Scanner scanner = new Scanner(srcName);
		Parser parser = new Parser(scanner);
		
		if (!any.equals("")) {
			parser.any = new ArrayList<String>(Arrays.asList(any.split("")));
		}
		
		parser.Parse();

		// 2. Set attributes.
		return parser.getGrammar();
	}

}
