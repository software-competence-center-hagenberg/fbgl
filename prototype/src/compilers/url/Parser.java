package compilers.url;

import grammar.addons.TreeNode;
import grammar.grammar.NonTerminal;

public class Parser {
	public static final int _EOF = 0;
	public static final int maxT = 37;

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
	
	void Url() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (la.kind == 1) {
			Httpaddress();
		} else if (la.kind == 7) {
			Ftpaddress();
		} else if (la.kind == 2) {
			Telnetaddress();
		} else if (la.kind == 11) {
			Gopheraddress();
		} else SynErr(38);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Httpaddress() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Expect(1);
		Expect(2);
		Expect(2);
		Expect(3);
		Expect(4);
		Expect(5);
		Expect(5);
		Hostport();
		if (la.kind == 5) {
			Get();
			Path();
		}
		if (la.kind == 6) {
			Get();
			Search();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Ftpaddress() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Expect(7);
		Expect(2);
		Expect(3);
		Expect(4);
		Expect(5);
		Expect(5);
		Login();
		Expect(5);
		Path();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Telnetaddress() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Expect(2);
		Expect(8);
		Expect(9);
		Expect(10);
		Expect(8);
		Expect(2);
		Expect(4);
		Expect(5);
		Expect(5);
		Login();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Gopheraddress() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Expect(11);
		Expect(12);
		Expect(3);
		Expect(1);
		Expect(8);
		Expect(13);
		Expect(4);
		Expect(5);
		Expect(5);
		Hostport();
		if (la.kind == 5) {
			Get();
			Gtype();
			if (StartOf(1)) {
				Selector();
			}
		}
		if (la.kind == 6) {
			Get();
			Search();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Hostport() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Host();
		if (la.kind == 4) {
			Get();
			Port();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Path() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Xalphas();
		if (la.kind == 5) {
			Get();
			Path();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Search() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Xalphas();
		if (la.kind == 16) {
			Get();
			Search();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Login() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		User();
		if (la.kind == 4) {
			Get();
			Password();
		}
		Expect(14);
		Hostport();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Gtype() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Xalpha();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Selector() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Path();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void User() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Xalphas();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Password() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Xalphas();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Host() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (StartOf(2)) {
			Hostname();
		} else if (StartOf(3)) {
			Hostnumber();
		} else SynErr(39);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Port() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Digits();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Hostname() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Ialpha();
		if (la.kind == 15) {
			Get();
			Hostname();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Hostnumber() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Digits();
		Expect(15);
		Digits();
		Expect(15);
		Digits();
		Expect(15);
		Digits();
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Ialpha() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Alpha();
		if (StartOf(1)) {
			Xalphas();
		}
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
		if (StartOf(3)) {
			Digits();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Xalphas() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		Xalpha();
		if (StartOf(1)) {
			Xalphas();
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Xalpha() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (StartOf(2)) {
			Alpha();
		} else if (StartOf(3)) {
			Digit();
		} else if (StartOf(4)) {
			Safe();
		} else if (StartOf(5)) {
			Extra();
		} else SynErr(40);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Alpha() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		switch (la.kind) {
		case 17: {
			Get();
			break;
		}
		case 18: {
			Get();
			break;
		}
		case 19: {
			Get();
			break;
		}
		case 20: {
			Get();
			break;
		}
		case 21: {
			Get();
			break;
		}
		case 22: {
			Get();
			break;
		}
		default: SynErr(41); break;
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
		if (la.kind == 23) {
			Get();
		} else if (la.kind == 24) {
			Get();
		} else if (la.kind == 25) {
			Get();
		} else if (la.kind == 26) {
			Get();
		} else SynErr(42);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Safe() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		if (la.kind == 27) {
			Get();
		} else if (la.kind == 28) {
			Get();
		} else if (la.kind == 29) {
			Get();
		} else if (la.kind == 30) {
			Get();
		} else SynErr(43);
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}

	void Extra() {
		// START Entry instrumentation
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		TreeNode current = new TreeNode(new NonTerminal(methodName), this.node, null);
		this.node.addChildren(current);
		this.node = current;
		// END
		switch (la.kind) {
		case 31: {
			Get();
			break;
		}
		case 32: {
			Get();
			break;
		}
		case 33: {
			Get();
			break;
		}
		case 34: {
			Get();
			break;
		}
		case 35: {
			Get();
			break;
		}
		case 36: {
			Get();
			break;
		}
		default: SynErr(44); break;
		}
		// START Exit instrumentation
		this.node = this.node.parent;
		// END
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		Url();
		Expect(0);

	}

	private static final boolean[][] set = {
		{_T,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x},
		{_x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_T,_T,_T, _T,_T,_T,_T, _T,_T,_T,_T, _T,_T,_T,_T, _T,_T,_T,_T, _T,_x,_x},
		{_x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_T,_T,_T, _T,_T,_T,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x},
		{_x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_T, _T,_T,_T,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x},
		{_x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_T, _T,_T,_T,_x, _x,_x,_x,_x, _x,_x,_x},
		{_x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_x, _x,_x,_x,_T, _T,_T,_T,_T, _T,_x,_x}

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
			case 1: s = "\"h\" expected"; break;
			case 2: s = "\"t\" expected"; break;
			case 3: s = "\"p\" expected"; break;
			case 4: s = "\":\" expected"; break;
			case 5: s = "\"/\" expected"; break;
			case 6: s = "\"?\" expected"; break;
			case 7: s = "\"f\" expected"; break;
			case 8: s = "\"e\" expected"; break;
			case 9: s = "\"l\" expected"; break;
			case 10: s = "\"n\" expected"; break;
			case 11: s = "\"g\" expected"; break;
			case 12: s = "\"o\" expected"; break;
			case 13: s = "\"r\" expected"; break;
			case 14: s = "\"@\" expected"; break;
			case 15: s = "\".\" expected"; break;
			case 16: s = "\"+\" expected"; break;
			case 17: s = "\"a\" expected"; break;
			case 18: s = "\"b\" expected"; break;
			case 19: s = "\"c\" expected"; break;
			case 20: s = "\"A\" expected"; break;
			case 21: s = "\"B\" expected"; break;
			case 22: s = "\"C\" expected"; break;
			case 23: s = "\"0\" expected"; break;
			case 24: s = "\"1\" expected"; break;
			case 25: s = "\"2\" expected"; break;
			case 26: s = "\"3\" expected"; break;
			case 27: s = "\"$\" expected"; break;
			case 28: s = "\"-\" expected"; break;
			case 29: s = "\"_\" expected"; break;
			case 30: s = "\"&\" expected"; break;
			case 31: s = "\"!\" expected"; break;
			case 32: s = "\"*\" expected"; break;
			case 33: s = "\"(\" expected"; break;
			case 34: s = "\")\" expected"; break;
			case 35: s = "\";\" expected"; break;
			case 36: s = "\",\" expected"; break;
			case 37: s = "??? expected"; break;
			case 38: s = "invalid Url"; break;
			case 39: s = "invalid Host"; break;
			case 40: s = "invalid Xalpha"; break;
			case 41: s = "invalid Alpha"; break;
			case 42: s = "invalid Digit"; break;
			case 43: s = "invalid Safe"; break;
			case 44: s = "invalid Extra"; break;
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
