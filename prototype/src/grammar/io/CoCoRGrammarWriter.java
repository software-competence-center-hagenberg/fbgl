package grammar.io;

import grammar.grammar.Alternative;
import grammar.grammar.Element;
import grammar.grammar.Grammar;
import grammar.grammar.GrammarVisitor;
import grammar.grammar.NonTerminal;
import grammar.grammar.Rule;
import grammar.grammar.Sequence;
import grammar.grammar.Terminal;

/**
 * Prints Coco/R representations of {@code Grammar} objects to a string.
 * 
 * http://www.ssw.uni-linz.ac.at/Research/Projects/Coco/
 * 
 * @author Josef Pichler
 *
 */
public class CoCoRGrammarWriter extends GrammarVisitor {

	private final StringBuilder out;

	public CoCoRGrammarWriter() {
		out = new StringBuilder();
	}

	@Override
	public String toString() {
		return out.toString();
	}

	@Override
	public void visit(Grammar grammar) {
		print("COMPILER ");
		grammar.getStartSymbol().accept(this);
		println();
		println("PRODUCTIONS");
		for (Rule e : grammar.getRuleTable()) {
			e.accept(this);
		}
		print("END ");
		grammar.getStartSymbol().accept(this);
		println(".");
	}

	@Override
	public void visit(Rule rule) {
		rule.getKey().accept(this);
		print(" = ");
		rule.getBody().accept(this);
		println(".");
	}

	@Override
	public void visit(Terminal terminal) {
		print("\"");
		print(terminal.toString());
		print("\"");
	}

	@Override
	public void visit(NonTerminal nonTerminal) {
		String str = nonTerminal.toString();
		if (str.length() > 1) {
			print(str.substring(0, 1).toUpperCase());
			print(str.substring(1));
		} else {
			print(str.toUpperCase());
		}
	}

	@Override
	public void visit(Sequence sequence) {
		for (Element e : sequence.getContent()) {
			e.accept(this);
			print(" ");
		}
	}

	@Override
	public void visit(Alternative alternative) {
		boolean useSeparator = false;
		print("(");
		for (Element e : alternative.getContent()) {
			if (useSeparator) {
				print(" | ");
			} else {
				useSeparator = true;
			}
			e.accept(this);
		}
		print(")");
	}

	private void print(String... str) {
		for (String e : str) {
			out.append(e);
		}
	}

	private void println(String... str) {
		print(str);
		out.append("\r\n");
	}

}
