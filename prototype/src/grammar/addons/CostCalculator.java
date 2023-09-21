package grammar.addons;

import java.util.Hashtable;
import java.util.Map;

import grammar.grammar.Alternative;
import grammar.grammar.Element;
import grammar.grammar.Grammar;
import grammar.grammar.NonTerminal;
import grammar.grammar.Rule;
import grammar.grammar.Sequence;
import grammar.grammar.Terminal;

public class CostCalculator {

	Grammar g;
	Map<Element, Integer> costTable;
	Rule currentRule = null;
	boolean fillGaps = false;
	
	public Map<Element, Integer> calculateCosts(Grammar g) {
		
		this.g = g;
		costTable = new Hashtable<Element, Integer>();
		
		boolean found = true;
		while(found) {
			found = false;
			
			for (Rule r : this.g.getRuleTable()) {
				
				if (costTable.containsKey(r.getKey())) { continue; }
				
				int cost = calcElementCost(r.getBody());
				if (cost != 0) { 
					cost++;
					costTable.put(r.getKey(), cost);
					found = true; 
				}
			}
		}
		
		// Fill rest of the table
		for (Rule r : this.g.getRuleTable()) {
			fillGaps = true;
			calcElementCost(r.getBody());
		}
		
		return costTable;
		
	}
	
	private int calcElementCost(Element e) {
		
		if (!fillGaps && costTable.containsKey(e)) { return costTable.get(e); }
				
		if (e.getClass().equals(Terminal.class)) {
			this.costTable.put(e, 1);
			return 1;
		}
		else if (e.getClass().equals(NonTerminal.class)) {
			if (costTable.containsKey(e)) { return costTable.get(e); }
			else if (this.g.getRule((NonTerminal)e) == null) { this.costTable.put(e, 1); return 1; }
			else { return 0; }
 		} 
		else if (e.getClass().equals(Sequence.class)) {
			
			int cost = calcSequenceCost((Sequence) e);
			
			if (cost != 0 && !this.costTable.containsKey(e)) {
				this.costTable.put(e, cost);
			}
			return cost;
		}
		else if (e.getClass().equals(Alternative.class)) {
			
			int cost = calcAlternativeCost((Alternative) e);
			
			if (cost != 0 && !this.costTable.containsKey(e)) {
				this.costTable.put(e, cost);
			}
			
			return cost;
		}
		
		else {
 			return 0;
 		}
	}
	
	private int calcSequenceCost(Sequence s) {
		
		int cost = 0;
		for (Element e : s.getContent()) {
			int c = calcElementCost(e);
			if (c == 0) { return 0; }
			cost += c;
		}
		
		cost++;
		return cost;
	}
	
	private int calcAlternativeCost(Alternative a) {
		int minCost = Integer.MAX_VALUE;
		
		for (Element e : a.getContent()) {
			int cost = calcElementCost(e);
			
			//if (cost != 0 && !this.costTable.containsKey(e)) { this.costTable.put(e,  cost); }
			
			if (cost == 0) {continue;}			
			if (cost < minCost) { minCost = cost; }
		}
		
		if (minCost == Integer.MAX_VALUE) { return 0; }
		minCost++;
		return minCost;
	}
	
	
	/*
	public Map<Element, Integer> calculateCosts(Grammar g) {
		this.g = g;
		costTable = new Hashtable<Element, Integer>();
		
		calcRuleCost(g.getRuleTable().get(g.getRuleTable().indexOf(new Rule(g.getStartSymbol(), new Terminal("DUMMY")))));
				
		return this.costTable;
	}
	
	
	private int calcRuleCost(Rule r) {
		
		if (r.equals(currentRule)) {
			return Integer.MAX_VALUE;
		}
		
		currentRule = r;
		
		int cost = calcElementCost(r.getBody());
		
		if (cost != Integer.MAX_VALUE) {cost++;}
		return cost;
	}
	
	private int calcElementCost(Element e) {
		
		try {
		
			if (e.getClass().equals(Terminal.class)) {
				if (!costTable.containsKey(e)) { this.costTable.put(e, 0); }
				return 0;
			}
			else if (e.getClass().equals(NonTerminal.class)) {
				if (costTable.containsKey(e)) { return costTable.get(e); }
				else {
					int cost = calcRuleCost(g.getRuleTable().get(g.getRuleTable().indexOf(new Rule((NonTerminal)e, new Terminal("DUMMY")))));
					if (cost != Integer.MAX_VALUE) {cost++;}
					this.costTable.put(e, cost);
					return cost;
				}
	 		} 
			else if (e.getClass().equals(Sequence.class)) {
				return calcSequenceCost((Sequence) e);
			}
			else if (e.getClass().equals(Alternative.class)) {
				return calcAlternativeCost((Alternative) e);
			}
			
			else {
	 			return Integer.MAX_VALUE;
	 		}
		} catch (StackOverflowError error) {
			return Integer.MAX_VALUE;
		}
	}
	
	private int calcSequenceCost(Sequence s) {
		int cost = 0;
		for (Element e : s.getContent()) {
			int c = calcElementCost(e);
			if (c == Integer.MAX_VALUE) { cost = Integer.MAX_VALUE; break; }
			cost += c;
		}
		
		if (cost != Integer.MAX_VALUE) {cost++;}
		this.costTable.put(s, cost);
		return cost;
	}
	
	private int calcAlternativeCost(Alternative a) {
		int minCost = Integer.MAX_VALUE;
		
		for (Element e : a.getContent()) {
			int cost = calcElementCost(e);
			if (cost < minCost) { minCost = cost; }
		}
		
		if (minCost != Integer.MAX_VALUE) {minCost++;}
		this.costTable.put(a, minCost);
		return minCost;
	}
	*/
}
