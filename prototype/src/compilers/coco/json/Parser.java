package compilers.coco.json;

import grammar.addons.TreeNode;
import grammar.grammar.NonTerminal;

public class Parser {
	public static final int _EOF = 0;
	public static final int maxT = 25;

	static final boolean _T = true;
	static final boolean _x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;
	
	public Scanner scanner;
	public Errors errors;
	private TreeNode node;

	

	public Parser(Scanner scanner, TreeNode root) {
		this.scanner = scanner;
		this.node = root;
		errors = new Errors();
	}
	
	public int getErrorCount() {
		return this.errors.count;
	}

	void SynErr (int n) {
		if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n);
		errDist = 0;
	}

	public void SemErr (String msg) {
		if (errDist >= minErrDist) errors.SemErr(t.line, t.col, msg);
		errDist = 0;
	}
	
	void Get () {
		for (;;) {
			t = la;
			la = scanner.Scan();
			if (la.kind <= maxT) {
				++errDist;
				break;
			}

			la = t;
		}
	}
	
	void Expect (int n) {
		if (la.kind==n) Get(); else { SynErr(n); }
	}
	
	boolean StartOf (int s) {
		return set[s][la.kind];
	}
	
	void ExpectWeak (int n, int follow) {
		if (la.kind == n) Get();
		else {
			SynErr(n);
			while (!StartOf(follow)) Get();
		}
	}
	
	boolean WeakSeparator (int n, int syFol, int repFol) {
		int kind = la.kind;
		if (kind == n) { Get(); return true; }
		else if (StartOf(repFol)) return false;
		else {
			SynErr(n);
			while (!(set[syFol][kind] || set[repFol][kind] || set[0][kind])) {
				Get();
				kind = la.kind;
			}
			return StartOf(syFol);
		}
	}
	
	void Json() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Element();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Element() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Ws();
		Value();
		Ws();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Value() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		switch (la.kind) {
		case 4: {
			Object();
			break;
		}
		case 8: {
			Array();
			break;
		}
		case 10: {
			String();
			break;
		}
		case 15: case 16: case 17: case 18: {
			Number();
			break;
		}
		case 1: {
			Get();
			break;
		}
		case 2: {
			Get();
			break;
		}
		case 3: {
			Get();
			break;
		}
		default: SynErr(26); break;
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Object() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Expect(4);
		Ws();
		if (la.kind == 10) {
			String();
			Ws();
			Expect(5);
			Element();
			if (la.kind == 6) {
				Get();
				Members();
			}
		}
		Expect(7);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Array() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Expect(8);
		Ws();
		if (StartOf(1)) {
			Value();
			Ws();
			if (la.kind == 6) {
				Get();
				Elements();
			}
		}
		Expect(9);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void String() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Expect(10);
		Characters();
		Expect(10);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Number() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Integer();
		Fraction();
		Exponent();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Ws() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Expect(24);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Members() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Member();
		if (la.kind == 6) {
			Get();
			Members();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Member() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Ws();
		String();
		Ws();
		Expect(5);
		Element();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Elements() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Element();
		if (la.kind == 6) {
			Get();
			Elements();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Characters() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (la.kind == 11) {
			Get();
		} else if (la.kind == 12 || la.kind == 13 || la.kind == 14) {
			Character();
			Characters();
		} else SynErr(27);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Character() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (la.kind == 12) {
			Get();
		} else if (la.kind == 13) {
			Get();
		} else if (la.kind == 14) {
			Get();
		} else SynErr(28);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Integer() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (la.kind == 15) {
			Get();
		} else if (la.kind == 16 || la.kind == 17 || la.kind == 18) {
			Onenine();
			if (StartOf(2)) {
				Digits();
			}
		} else SynErr(29);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Fraction() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (la.kind == 11) {
			Get();
		} else if (la.kind == 19) {
			Get();
			Digits();
		} else SynErr(30);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Exponent() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (la.kind == 11) {
			Get();
		} else if (la.kind == 20) {
			Get();
			Sign();
			Digits();
		} else if (la.kind == 21) {
			Get();
			Sign();
			Digits();
		} else SynErr(31);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Onenine() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (la.kind == 16) {
			Get();
		} else if (la.kind == 17) {
			Get();
		} else if (la.kind == 18) {
			Get();
		} else SynErr(32);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Digits() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Digit();
		if (StartOf(2)) {
			Digits();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Digit() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (la.kind == 15) {
			Get();
		} else if (la.kind == 16 || la.kind == 17 || la.kind == 18) {
			Onenine();
		} else SynErr(33);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Sign() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (la.kind == 11) {
			Get();
		} else if (la.kind == 22) {
			Get();
		} else if (la.kind == 23) {
			Get();
		} else SynErr(34);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		Json();
		Expect(0);

	}

	private static final boolean[][] set = {
		{_T,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x},
		{_x,_T,_T,_T, _T,_x,_x,_x, _T,_x,_T,_x, _x,_x,_x,_T, _T,_T,_T,_x, _x,_x,_x,_x, _x,_x,_x},
		{_x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_T, _T,_T,_T,_x, _x,_x,_x,_x, _x,_x,_x}

	};
} // end Parser


class Errors {
	public int count = 0;                                    // number of errors detected
	public java.io.PrintStream errorStream = System.out;     // error messages go to this stream
	public String errMsgFormat = "-- line {0} col {1}: {2}"; // 0=line, 1=column, 2=text
	
	protected void printMsg(int line, int column, String msg) {
		StringBuffer b = new StringBuffer(errMsgFormat);
		int pos = b.indexOf("{0}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, line); }
		pos = b.indexOf("{1}");
		if (pos >= 0) { b.delete(pos, pos+3); b.insert(pos, column); }
		pos = b.indexOf("{2}");
		if (pos >= 0) b.replace(pos, pos+3, msg);
		//errorStream.println(b.toString());
	}
	
	public void SynErr (int line, int col, int n) {
		String s;
		switch (n) {
			case 0: s = "EOF expected"; break;
			case 1: s = "\"t\" expected"; break;
			case 2: s = "\"f\" expected"; break;
			case 3: s = "\"n\" expected"; break;
			case 4: s = "\"{\" expected"; break;
			case 5: s = "\":\" expected"; break;
			case 6: s = "\",\" expected"; break;
			case 7: s = "\"}\" expected"; break;
			case 8: s = "\"[\" expected"; break;
			case 9: s = "\"]\" expected"; break;
			case 10: s = "\"!\" expected"; break;
			case 11: s = "\"^\" expected"; break;
			case 12: s = "\"a\" expected"; break;
			case 13: s = "\"b\" expected"; break;
			case 14: s = "\"c\" expected"; break;
			case 15: s = "\"0\" expected"; break;
			case 16: s = "\"1\" expected"; break;
			case 17: s = "\"2\" expected"; break;
			case 18: s = "\"3\" expected"; break;
			case 19: s = "\".\" expected"; break;
			case 20: s = "\"E\" expected"; break;
			case 21: s = "\"e\" expected"; break;
			case 22: s = "\"+\" expected"; break;
			case 23: s = "\"-\" expected"; break;
			case 24: s = "\"_\" expected"; break;
			case 25: s = "??? expected"; break;
			case 26: s = "invalid Value"; break;
			case 27: s = "invalid Characters"; break;
			case 28: s = "invalid Character"; break;
			case 29: s = "invalid Integer"; break;
			case 30: s = "invalid Fraction"; break;
			case 31: s = "invalid Exponent"; break;
			case 32: s = "invalid Onenine"; break;
			case 33: s = "invalid Digit"; break;
			case 34: s = "invalid Sign"; break;
			default: s = "error " + n; break;
		}
		printMsg(line, col, s);
		count++;
	}

	public void SemErr (int line, int col, String s) {	
		printMsg(line, col, s);
		count++;
	}
	
	public void SemErr (String s) {
		errorStream.println(s);
		count++;
	}
	
	public void Warning (int line, int col, String s) {	
		printMsg(line, col, s);
	}
	
	public void Warning (String s) {
		errorStream.println(s);
	}
} // Errors


class FatalError extends RuntimeException {
	public static final long serialVersionUID = 1L;
	public FatalError(String s) { super(s); }
}
