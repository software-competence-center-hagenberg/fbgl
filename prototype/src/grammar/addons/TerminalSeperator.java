package grammar.addons;

import java.util.ArrayList;
import java.util.List;

import grammar.grammar.Alternative;
import grammar.grammar.Element;
import grammar.grammar.Grammar;
import grammar.grammar.GrammarVisitor;
import grammar.grammar.NonTerminal;
import grammar.grammar.Rule;
import grammar.grammar.Sequence;
import grammar.grammar.Terminal;

public class TerminalSeperator extends GrammarVisitor {
	
	List<Rule> newRules;
	
	public TerminalSeperator() {
		newRules = new ArrayList<Rule>();
	}
	
	public Grammar sperate(Grammar g) {
		
		g = g.clone();
		g.accept(this);
		
		for (Rule rule : this.newRules) {
			g.addRule(rule);
		}
			
		return g;
	}
	
	public void visit(Grammar g) {
		for (Rule r : g.getRuleTable()) {
			r.accept(this);
		}
	}
	
	public void visit(Rule r) {
		
		Element e = r.getBody();
		
		if (e.getClass().equals(Terminal.class)) {
			
			if (e.toString().length() > 1) {
				
				NonTerminal nt = new NonTerminal(e.toString());
				Sequence seq = new Sequence();
				for (String str : e.toString().split("")) { seq.add(new Terminal(str)); }
				
				r.setBody(nt);
				this.newRules.add(new Rule(nt, seq));
			}
		} else {
			e.accept(this);
		}
	}
	
	public void visit(Alternative a) {
		
		for (Element e : a.getContent()) {
			
			if (e.getClass().equals(Terminal.class)) {
				
				if (e.toString().length() > 1) {
					
					NonTerminal nt = new NonTerminal(e.toString());
					Sequence seq = new Sequence();
					for (String str : e.toString().split("")) { seq.add(new Terminal(str)); }
					
					a.replace(e, nt);
					this.newRules.add(new Rule(nt, seq));
				}
			} else {
				e.accept(this);
			}
		}
	}
	
	public void visit(Sequence s) {
		
		for (Element e : s.getContent()) {
			
			if (e.getClass().equals(Terminal.class)) {
				
				if (e.toString().length() > 1) {
					
					NonTerminal nt = new NonTerminal(e.toString());
					Sequence seq = new Sequence();
					for (String str : e.toString().split("")) { seq.add(new Terminal(str)); }
					
					s.replace(e, nt);
					this.newRules.add(new Rule(nt, seq));
				}
			} else {
				e.accept(this);
			}
		}
	}
}
