package grammar.coco;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

import grammar.grammar.*;

@SuppressWarnings("all")
class Parser {
	public static final int _EOF = 0;
	public static final int _ident = 1;
	public static final int _number = 2;
	public static final int _string = 3;
	public static final int _badString = 4;
	public static final int _char = 5;
	public static final int maxT = 44;
	public static final int _ddtSym = 45;
	public static final int _optionSym = 46;

	static final boolean T = true;
	static final boolean x = false;
	static final int minErrDist = 2;

	public Token t;    // last recognized token
	public Token la;   // lookahead token
	int errDist = minErrDist;
	
	public Scanner scanner;
	public Errors errors;
	private final Grammar grammar = new Grammar();
	
	private static String reocIdent = "REOC_";
	private static int reocCTR = 1;

	
public Grammar getGrammar() { return grammar; }
public List<String> any = new ArrayList<String>(Arrays.asList("abcdefghijklmnopqrstuvwxyz0123456789".split("")));
/*-------------------------------------------------------------------------*/



	public Parser(Scanner scanner) {
		this.scanner = scanner;
		errors = new Errors();
	}

	void SynErr (int n) {
		if (errDist >= minErrDist) errors.SynErr(la.line, la.col, n);
		errDist = 0;
	}

	public void SemErr (String msg) {
		if (errDist >= minErrDist) errors.SemErr(t.line, t.col, msg);
		errDist = 0;
	}
		
	public int errorCount(){
	   return errors.count;
	}
	
	void Get () {
		for (;;) {
			t = la;
			la = scanner.Scan();
			if (la.kind <= maxT) {
				++errDist;
				break;
			}

			if (la.kind == 45) {
			}
			if (la.kind == 46) {
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
	
	void Coco() {
		if (StartOf(1)) {
			Get();
			while (StartOf(1)) {
				Get();
			}
		}
		Expect(6);
		Expect(1);
		NonTerminal startSy = new NonTerminal(t.val); 
		grammar.setStartSymbol(startSy); 
		while (StartOf(2)) {
			Get();
		}
		if (la.kind == 7) {
			Get();
		}
		if (la.kind == 8) {
			Get();
			while (la.kind == 1) {
				Rule r = SetDecl();
				grammar.addRule(r); 
			}
		}
		if (la.kind == 9) {
			Get();
			while (la.kind == 1 || la.kind == 3 || la.kind == 5) {
				TokenDecl();
			}
		}
		if (la.kind == 10) {
			Get();
			while (la.kind == 1 || la.kind == 3 || la.kind == 5) {
				TokenDecl();
			}
		}
		while (la.kind == 11) {
			Get();
			Expect(12);
			Element e1 = TokenExpr();
			Expect(13);
			Element e2 = TokenExpr();
			if (la.kind == 14) {
				Get();
			}
		}
		while (la.kind == 15) {
			Get();
			Element e = Set();
		}
		while (!(la.kind == 0 || la.kind == 16)) {SynErr(45); Get();}
		Expect(16);
		while (la.kind == 1) {
			Get();
			NonTerminal lhs = new NonTerminal(t.val);        
			if (la.kind == 24 || la.kind == 29) {
				AttrDecl();
			}
			if (la.kind == 42) {
				SemText();
			}
			ExpectWeak(17, 3);
			Element rhs = Expression();
			Rule r = new Rule(lhs, rhs); grammar.addRule(r); 
			r.setSep(true); 
			ExpectWeak(18, 4);
		}
		Expect(19);
		Expect(1);
		Expect(18);
	}

	Rule  SetDecl() {
		Rule  r;
		Expect(1);
		NonTerminal lhs = new NonTerminal(t.val);        
		Expect(17);
		Element rhs = Set();
		r = new Rule(lhs, rhs); 
		Expect(18);
		return r;
	}

	void TokenDecl() {
		Element e = Sym();
		NonTerminal lhs = (NonTerminal) e; 
		while (!(StartOf(5))) {SynErr(46); Get();}
		if (la.kind == 17) {
			Get();
			Element rhs = TokenExpr();
			Expect(18);
			Rule r = new Rule(lhs, rhs); 
			grammar.addRule(r); 
		} else if (StartOf(6)) {
		} else SynErr(47);
		if (la.kind == 42) {
			SemText();
		}
	}

	Element  TokenExpr() {
		Element  e;
		Alternative a = new Alternative(); 
		Element e1 = TokenTerm();
		if (e1 != null) a.add(e1); 
		while (WeakSeparator(33,7,8) ) {
			Element e2 = TokenTerm();
			if (e2 != null) a.add(e2); 
		}
		e = a.size() == 1 ? e1 : a; 
		return e;
	}

	Element  Set() {
		Element  e;
		e = new Alternative(); 
		Element ss1 = SimSet();
		e.add(ss1); 
		while (la.kind == 20 || la.kind == 21) {
			if (la.kind == 20) {
				Get();
				Element ss2 = SimSet();
				e.add(ss2); 
			} else {
				Get();
				Element ss2 = SimSet();
				for (Element el : ss2.getContent()) { e.remove(el); } 
			}
		}
		return e;
	}

	void AttrDecl() {
		if (la.kind == 24) {
			Get();
			if (la.kind == 25 || la.kind == 26) {
				if (la.kind == 25) {
					Get();
				} else {
					Get();
				}
				TypeName();
				Expect(1);
				if (la.kind == 27) {
					Get();
				} else if (la.kind == 28) {
					Get();
					while (StartOf(9)) {
						Get();
					}
					Expect(27);
				} else SynErr(48);
			} else if (StartOf(10)) {
				if (StartOf(11)) {
					Get();
					while (StartOf(9)) {
						Get();
					}
				}
				Expect(27);
			} else SynErr(49);
		} else if (la.kind == 29) {
			Get();
			if (la.kind == 25 || la.kind == 26) {
				if (la.kind == 25) {
					Get();
				} else {
					Get();
				}
				TypeName();
				Expect(1);
				if (la.kind == 30) {
					Get();
				} else if (la.kind == 28) {
					Get();
					while (StartOf(12)) {
						Get();
					}
					Expect(30);
				} else SynErr(50);
			} else if (StartOf(10)) {
				if (StartOf(13)) {
					Get();
					while (StartOf(12)) {
						Get();
					}
				}
				Expect(30);
			} else SynErr(51);
		} else SynErr(52);
	}

	void SemText() {
		Expect(42);
		while (StartOf(14)) {
			if (StartOf(15)) {
				Get();
			} else if (la.kind == 4) {
				Get();
			} else {
				Get();
			}
		}
		Expect(43);
	}

	Element  Expression() {
		Element  e;
		e = new Alternative(); 
		Element t1 = Term();
		if (t1 != null) e.add(t1); 
		while (WeakSeparator(33,16,17) ) {
			Element t2 = Term();
			if (t2 != null) e.add(t2); 
		}
		return e;
	}

	Element  SimSet() {
		Element  e;
		e = new Alternative(); 
		if (la.kind == 1) {
			Get();
			e = new NonTerminal(t.val); 
		} else if (la.kind == 3) {
			Get();
			for (char c : t.val.substring(1, t.val.length()-1).toCharArray()) { e.add(new Terminal(Character.toString(c))); }; 
		} else if (la.kind == 5) {
			String s1 = Char();
			e = new Terminal(s1); 
			if (la.kind == 22) {
				Get();
				String s2 = Char();
				if (s2 != null) {  
				e = new Alternative();
				for (int i = (int)(s1.charAt(0)); i <= s2.charAt(0); i++) {
				e.add(new Terminal(Character.toString((char) i)));
				}}; 
			}
		} else if (la.kind == 23) {
			Get();
			for (String s : any) { e.add(new Terminal(s)); } 
		} else SynErr(53);
		return e;
	}

	String  Char() {
		String  s;
		Expect(5);
		s = t.val.substring(1, t.val.length()-1); 
		if (s.equals("\\'")) { s = "'"; } 
		return s;
	}

	Element  Sym() {
		Element  e;
		e = null; 
		if (la.kind == 1) {
			Get();
			e = new NonTerminal(t.val); 
		} else if (la.kind == 3 || la.kind == 5) {
			if (la.kind == 3) {
				Get();
				e = new Terminal(t.val.substring(1, t.val.length()-1)); 
			} else {
				Get();
				String c = t.val.substring(1, t.val.length()-1); 
				if (c.equals("\\'")) { c = "'"; } 
				e = new Terminal(c); 
			}
		} else SynErr(54);
		return e;
	}

	void TypeName() {
		Expect(1);
		while (la.kind == 18 || la.kind == 24 || la.kind == 31) {
			if (la.kind == 18) {
				Get();
				Expect(1);
			} else if (la.kind == 31) {
				Get();
				Expect(32);
			} else {
				Get();
				TypeName();
				while (la.kind == 28) {
					Get();
					TypeName();
				}
				Expect(27);
			}
		}
	}

	Element  Term() {
		Element  e;
		e = new Sequence(); 
		if (StartOf(18)) {
			if (la.kind == 40) {
				Resolver();
			}
			Element e1 = Factor();
			if (e1 != null) e.add(e1); 
			while (StartOf(19)) {
				Element e2 = Factor();
				if (e2 != null) e.add(e2);  
			}
		} else if (StartOf(20)) {
		} else SynErr(55);
		return e;
	}

	void Resolver() {
		Expect(40);
		Expect(35);
		Condition();
	}

	Element  Factor() {
		Element  e;
		e = null; 
		switch (la.kind) {
		case 1: case 3: case 5: case 34: {
			if (la.kind == 34) {
				Get();
			}
			Element e1 = Sym();
			e = e1; 
			if (la.kind == 24 || la.kind == 29) {
				Attribs();
			}
			break;
		}
		case 35: {
			Get();
			Element e1 = Expression();
			Expect(36);
			e = new Sequence(Arrays.asList(e1)); 
			break;
		}
		case 31: {
			Get();
			Element e1 = Expression();
			Expect(32);
			if (e1 != null) {
				e = new Alternative();
				e.add(e1);
				e.add(new Terminal(""));
				}
			break;
		}
		case 37: {
			Get();
			Element e1 = Expression();
			Expect(38);
			e = new NonTerminal(this.reocIdent+this.reocCTR);
			this.reocCTR++;
			Sequence s = new Sequence();
			s.add(e1);
			s.add(e);
			Alternative a = new Alternative();
			a.add(new Terminal(""));
			a.add(s);
			this.grammar.addRule(new Rule((NonTerminal)e, a));
			break;
		}
		case 42: {
			SemText();
			break;
		}
		case 23: {
			Get();
			e = new Alternative(); 
			for (String s : any) { e.add(new Terminal(s)); } 
			break;
		}
		case 39: {
			Get();
			break;
		}
		default: SynErr(56); break;
		}
		return e;
	}

	void Attribs() {
		if (la.kind == 24) {
			Get();
			if (la.kind == 25 || la.kind == 26) {
				if (la.kind == 25) {
					Get();
				} else {
					Get();
				}
				while (StartOf(21)) {
					if (StartOf(22)) {
						Get();
					} else if (la.kind == 31 || la.kind == 35) {
						Bracketed();
					} else {
						Get();
					}
				}
				if (la.kind == 27) {
					Get();
				} else if (la.kind == 28) {
					Get();
					while (StartOf(9)) {
						if (StartOf(23)) {
							Get();
						} else {
							Get();
						}
					}
					Expect(27);
				} else SynErr(57);
			} else if (StartOf(10)) {
				if (StartOf(11)) {
					if (StartOf(24)) {
						Get();
					} else {
						Get();
					}
					while (StartOf(9)) {
						if (StartOf(23)) {
							Get();
						} else {
							Get();
						}
					}
				}
				Expect(27);
			} else SynErr(58);
		} else if (la.kind == 29) {
			Get();
			if (la.kind == 25 || la.kind == 26) {
				if (la.kind == 25) {
					Get();
				} else {
					Get();
				}
				while (StartOf(25)) {
					if (StartOf(26)) {
						Get();
					} else if (la.kind == 31 || la.kind == 35) {
						Bracketed();
					} else {
						Get();
					}
				}
				if (la.kind == 30) {
					Get();
				} else if (la.kind == 28) {
					Get();
					while (StartOf(12)) {
						if (StartOf(27)) {
							Get();
						} else {
							Get();
						}
					}
					Expect(30);
				} else SynErr(59);
			} else if (StartOf(10)) {
				if (StartOf(13)) {
					if (StartOf(28)) {
						Get();
					} else {
						Get();
					}
					while (StartOf(12)) {
						if (StartOf(27)) {
							Get();
						} else {
							Get();
						}
					}
				}
				Expect(30);
			} else SynErr(60);
		} else SynErr(61);
	}

	void Condition() {
		while (StartOf(29)) {
			if (la.kind == 35) {
				Get();
				Condition();
			} else {
				Get();
			}
		}
		Expect(36);
	}

	Element  TokenTerm() {
		Element  e;
		e = new Sequence(); 
		Element e1 = TokenFactor();
		if (e1 != null) e.add(e1); 
		while (StartOf(7)) {
			Element e2 = TokenFactor();
			if (e2 != null) e.add(e2);  
		}
		if (la.kind == 41) {
			Get();
			Expect(35);
			Element e3 = TokenExpr();
			Expect(36);
		}
		return e;
	}

	Element  TokenFactor() {
		Element  e;
		e = null; 
		if (la.kind == 1 || la.kind == 3 || la.kind == 5) {
			Element e1 = Sym();
			e = e1; 
		} else if (la.kind == 35) {
			Get();
			Element e1 = TokenExpr();
			Expect(36);
			e = new Sequence(Arrays.asList(e1)); 
		} else if (la.kind == 31) {
			Get();
			Element e1 = TokenExpr();
			Expect(32);
			if (e1 != null) { 
				e = new Alternative();
				e.add(e1);
				e.add(new Terminal(""));
			} 
		} else if (la.kind == 37) {
			Get();
			Element e1 = TokenExpr();
			Expect(38);
			e = new NonTerminal(this.reocIdent+this.reocCTR);
			this.reocCTR++;
			Sequence s = new Sequence();
			s.add(e1);
			s.add(e);
			Alternative a = new Alternative();
			a.add(new Terminal(""));
			a.add(s);
			this.grammar.addRule(new Rule((NonTerminal)e, a));
		} else SynErr(62);
		return e;
	}

	void Bracketed() {
		if (la.kind == 35) {
			Get();
			while (StartOf(29)) {
				if (la.kind == 31 || la.kind == 35) {
					Bracketed();
				} else {
					Get();
				}
			}
			Expect(36);
		} else if (la.kind == 31) {
			Get();
			while (StartOf(30)) {
				if (la.kind == 31 || la.kind == 35) {
					Bracketed();
				} else {
					Get();
				}
			}
			Expect(32);
		} else SynErr(63);
	}



	public void Parse() {
		la = new Token();
		la.val = "";		
		Get();
		Coco();
		Expect(0);

	}

	private static final boolean[][] set = {
		{T,T,x,T, x,T,x,x, x,x,T,T, x,x,x,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x},
		{x,T,T,T, T,T,x,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, T,T,T,x, x,x,x,x, T,T,T,x, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{T,T,x,T, x,T,x,x, x,x,T,T, x,x,x,T, T,T,T,x, x,x,x,T, x,x,x,x, x,x,x,T, x,T,T,T, x,T,x,T, T,x,T,x, x,x},
		{T,T,x,T, x,T,x,x, x,x,T,T, x,x,x,T, T,T,x,T, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x},
		{T,T,x,T, x,T,x,x, x,x,T,T, x,x,x,T, T,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x},
		{x,T,x,T, x,T,x,x, x,x,T,T, x,x,x,T, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x},
		{x,T,x,T, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,T, x,T,x,x, x,x,x,x, x,x},
		{x,x,x,x, x,x,x,x, x,x,x,T, x,T,T,T, T,x,T,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x, T,x,T,x, x,x,x,x, x,x},
		{x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,x, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x,x,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x,x,x, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,x,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x,x,T, T,T,x,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,x, T,x},
		{x,T,T,T, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,x,x, T,x},
		{x,T,x,T, x,T,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x,x,T, x,x,x,x, x,x,x,T, T,T,T,T, T,T,T,T, T,x,T,x, x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x, T,x,T,x, x,x,x,x, x,x},
		{x,T,x,T, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,x,x,T, x,x,T,T, x,T,x,T, T,x,T,x, x,x},
		{x,T,x,T, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, x,x,x,x, x,x,x,T, x,x,T,T, x,T,x,T, x,x,T,x, x,x},
		{x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,x,x, T,x,T,x, x,x,x,x, x,x},
		{x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,x, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,x, x,T,T,x, T,T,T,x, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,x, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x,x,x, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, x,T,x,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, x,T,x,x, T,T,T,x, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,x,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x,x,T, T,T,x,T, T,T,T,T, T,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, x,T,T,T, T,T,T,T, T,x},
		{x,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, T,T,T,T, x,T,T,T, T,T,T,T, T,T,T,T, T,x}

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
		errorStream.println(b.toString());
	}
	
	public void SynErr (int line, int col, int n) {
		String s;
		switch (n) {
			case 0: s = "EOF expected"; break;
			case 1: s = "ident expected"; break;
			case 2: s = "number expected"; break;
			case 3: s = "string expected"; break;
			case 4: s = "badString expected"; break;
			case 5: s = "char expected"; break;
			case 6: s = "\"COMPILER\" expected"; break;
			case 7: s = "\"IGNORECASE\" expected"; break;
			case 8: s = "\"CHARACTERS\" expected"; break;
			case 9: s = "\"TOKENS\" expected"; break;
			case 10: s = "\"PRAGMAS\" expected"; break;
			case 11: s = "\"COMMENTS\" expected"; break;
			case 12: s = "\"FROM\" expected"; break;
			case 13: s = "\"TO\" expected"; break;
			case 14: s = "\"NESTED\" expected"; break;
			case 15: s = "\"IGNORE\" expected"; break;
			case 16: s = "\"PRODUCTIONS\" expected"; break;
			case 17: s = "\"=\" expected"; break;
			case 18: s = "\".\" expected"; break;
			case 19: s = "\"END\" expected"; break;
			case 20: s = "\"+\" expected"; break;
			case 21: s = "\"-\" expected"; break;
			case 22: s = "\"..\" expected"; break;
			case 23: s = "\"ANY\" expected"; break;
			case 24: s = "\"<\" expected"; break;
			case 25: s = "\"^\" expected"; break;
			case 26: s = "\"out\" expected"; break;
			case 27: s = "\">\" expected"; break;
			case 28: s = "\",\" expected"; break;
			case 29: s = "\"<.\" expected"; break;
			case 30: s = "\".>\" expected"; break;
			case 31: s = "\"[\" expected"; break;
			case 32: s = "\"]\" expected"; break;
			case 33: s = "\"|\" expected"; break;
			case 34: s = "\"WEAK\" expected"; break;
			case 35: s = "\"(\" expected"; break;
			case 36: s = "\")\" expected"; break;
			case 37: s = "\"{\" expected"; break;
			case 38: s = "\"}\" expected"; break;
			case 39: s = "\"SYNC\" expected"; break;
			case 40: s = "\"IF\" expected"; break;
			case 41: s = "\"CONTEXT\" expected"; break;
			case 42: s = "\"(.\" expected"; break;
			case 43: s = "\".)\" expected"; break;
			case 44: s = "??? expected"; break;
			case 45: s = "this symbol not expected in Coco"; break;
			case 46: s = "this symbol not expected in TokenDecl"; break;
			case 47: s = "invalid TokenDecl"; break;
			case 48: s = "invalid AttrDecl"; break;
			case 49: s = "invalid AttrDecl"; break;
			case 50: s = "invalid AttrDecl"; break;
			case 51: s = "invalid AttrDecl"; break;
			case 52: s = "invalid AttrDecl"; break;
			case 53: s = "invalid SimSet"; break;
			case 54: s = "invalid Sym"; break;
			case 55: s = "invalid Term"; break;
			case 56: s = "invalid Factor"; break;
			case 57: s = "invalid Attribs"; break;
			case 58: s = "invalid Attribs"; break;
			case 59: s = "invalid Attribs"; break;
			case 60: s = "invalid Attribs"; break;
			case 61: s = "invalid Attribs"; break;
			case 62: s = "invalid TokenFactor"; break;
			case 63: s = "invalid Bracketed"; break;
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
