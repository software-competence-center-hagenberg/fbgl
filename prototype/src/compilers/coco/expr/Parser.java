package compilers.coco.expr;

import grammar.addons.TreeNode;
import grammar.grammar.NonTerminal;

public class Parser {
	public static final int _EOF = 0;
	public static final int maxT = 10;

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
	
	void Expr() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Term();
		if (la.kind == 1 || la.kind == 2) {
			if (la.kind == 1) {
				Get();
			} else {
				Get();
			}
			Term();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END	
	}

	void Term() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Factor();
		if (la.kind == 3 || la.kind == 4) {
			if (la.kind == 3) {
				Get();
			} else {
				Get();
			}
			Factor();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END	
	}

	void Factor() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (la.kind == 5) {
			Get();
		} else if (la.kind == 6) {
			Get();
		} else if (la.kind == 7) {
			Get();
		} else if (la.kind == 8) {
			Get();
			Expr();
			Expect(9);
		} else SynErr(11);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END	
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		Expr();
		Expect(0);

	}

	private static final boolean[][] set = {
		{_T,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x}

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
			case 1: s = "\"+\" expected"; break;
			case 2: s = "\"-\" expected"; break;
			case 3: s = "\"*\" expected"; break;
			case 4: s = "\"/\" expected"; break;
			case 5: s = "\"1\" expected"; break;
			case 6: s = "\"2\" expected"; break;
			case 7: s = "\"3\" expected"; break;
			case 8: s = "\"(\" expected"; break;
			case 9: s = "\")\" expected"; break;
			case 10: s = "??? expected"; break;
			case 11: s = "invalid Factor"; break;
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
