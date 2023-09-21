package grammar.addons;

import java.util.List;

import grammar.grammar.Alternative;
import grammar.grammar.Element;
import grammar.grammar.Grammar;
import grammar.grammar.Rule;

public class GrammarSummarizer {
		
	public Grammar summarize(Grammar g) {
		
		Grammar grammar = g.clone();
		boolean found = true;
		
		
		for (Rule r : grammar.getRuleTable()) {
			
			if (r.getBody().getClass().equals(Alternative.class)) {
				
				List<Element> alternatives = r.getBody().getContent();
				
				for (Element e : alternatives) { 
					if (e.equals(r.getKey())) {
						((Alternative)r.getBody()).alternatives.remove(e);
					}
				}
			}
		}
		
		
		
		while(found) {
			found = false;
			for (int i = 0; i < grammar.getRuleTable().size(); i++) {
				found |= removeRule(grammar, grammar.getRuleTable().get(i));
			}			
		}
	
		return grammar;
		
	}
	
	private boolean removeRule(Grammar grammar, Rule rule) {
		
		if (rule.getKey().equals(grammar.getStartSymbol())) { return false; }
		if (rule.getBody().contains(rule.getKey())) { return false; }
		
		grammar.removeRule(rule);

		for (Rule r : grammar.getRuleTable()) {
			r.replace(rule.getKey(), rule.getBody());
		}
		
		return true;
	}
}
