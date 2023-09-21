package grammar.addons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import grammar.grammar.Alternative;
import grammar.grammar.Element;
import grammar.grammar.Grammar;
import grammar.grammar.GrammarVisitor;
import grammar.grammar.Rule;
import grammar.grammar.Sequence;

public class GrammarDismantler extends GrammarVisitor {
	
	
	public GrammarDismantler() {
	}
	
	public Grammar dismantle(Grammar g) {
		
		g = g.clone();
		
		for (Rule rule : g.getRuleTable()) {
			dismantle(rule);			
		}
		
		g = new TerminalSeperator().sperate(g);
		
		return g;
	}
	
	private void dismantle(Rule r) {
		
		Element newElement = dismantle(r.getBody());
		r.setBody(newElement);
		
	}
	
	private Element dismantle(Element e) {
				
		if (e.getClass().equals(Alternative.class)) {
			return dismantle((Alternative) e);
		} else if (e.getClass().equals(Sequence.class)) {
			return dismantle((Sequence) e);
		} else {
			return e;
		}
	}
	
	private Element dismantle(Alternative a) {
		
		Alternative newAlt = new Alternative();
		
		for (Element e : a.getContent()) {
			newAlt.add(dismantle(e));
		}
		
		return newAlt;
	}
	
	private Element dismantle(Sequence s) {
		
		Sequence newSeq = new Sequence();
		
		for (Element e : s.getContent()) {
			newSeq.add(dismantle(e));
		}
		
		s = newSeq;
		
		
		Alternative a = new Alternative();
	
		List<Sequence> possibilities = new ArrayList<Sequence>();
	
		for (Element e : s.getContent().get(0).getContent()) {
			possibilities.add(new Sequence(Arrays.asList(e)));
		}
		
		for (int i = 1; i < s.getContent().size(); i++) {
			possibilities = this.merge(possibilities, s.getContent().get(i));
		}
		
		for (Sequence seq : possibilities) {
			a.add(seq);
		}
		
		return a;
	}
	
	private List<Sequence> merge(List<Sequence> seq, Element e) {
				
		List<Sequence> possibilities = new ArrayList<Sequence>();
		
		for (Sequence s : seq) {
			for (Element el : e.getContent()) {
								
				Sequence newSeq = (Sequence) s.clone();
				newSeq.add(el);
				possibilities.add(newSeq);
				
			}
		}
		return possibilities;
	}
}
