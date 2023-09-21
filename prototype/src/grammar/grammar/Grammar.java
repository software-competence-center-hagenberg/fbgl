package grammar.grammar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import grammar.addons.CostCalculator;
import grammar.addons.GrammarSummarizer;
import grammar.io.CoCoRGrammarWriter;

/**
 * 
 * @author Sochor Hannes
 *
 *         An implementation of a formal grammar which is defined by a start
 *         symbol and a set of various construction rules.
 */
public class Grammar implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Rule> ruleTable;
	private NonTerminal startSymbol;

	/**
	 * Creates an empty Grammar object
	 */
	public Grammar() {
		this.ruleTable = new ArrayList<Rule>();
		this.startSymbol = null;
	}

	/**
	 * Returns all Terminal Symbols which are in the rules of this grammar.
	 * 
	 * @return a set of Terminals
	 */
	public Set<Terminal> getTerminals() {

		Set<Terminal> terminals = new HashSet<Terminal>();

		for (Rule rule : this.ruleTable) {
			terminals.addAll(rule.getTerminals());
		}

		return terminals;
	}

	/**
	 * Returns all NonTerminal Variables which are defined as keys of the rules in
	 * this grammar.
	 * 
	 * @return a set of NonTerminals
	 */
	public Set<NonTerminal> getNonTerminals() {

		Set<NonTerminal> nonTerminals = new HashSet<NonTerminal>();

		for (Rule rule : this.ruleTable) {
			nonTerminals.add(rule.getKey());
			nonTerminals.addAll(rule.getBody().getNonTerminals());
		}

		return nonTerminals;
	}

	/**
	 * Sets the start symbol of the grammar to the given NonTerminal.
	 * 
	 * @param startSymbol
	 *            the NonTerminal which should be the start symbol.
	 */
	public void setStartSymbol(NonTerminal startSymbol) {
		this.startSymbol = startSymbol;
	}

	/**
	 * Returns the start symbol of the grammar.
	 * 
	 * @return a NonTerminal
	 */
	public NonTerminal getStartSymbol() {
		return this.startSymbol;
	}

	/**
	 * Adds the given Rule to the ruleset of the grammar. If the given key already
	 * exists, the body of the new rule is added as an alternative to the current
	 * body. If not a new rule is created.
	 * 
	 * @param rule
	 *            A Rule object which should be added.
	 * @return True if the operation was successful.
	 */
	public boolean addRule(Rule rule) {
		
		if (this.ruleTable.contains(rule)) {
			return this.ruleTable.get(this.ruleTable.indexOf(rule)).merge(rule);
		} else {
			return this.ruleTable.add(rule);
		}
	}

	/**
	 * Removes a rule from the ruleset. The rule which should be removed is
	 * identified only by the key of the given Rule. The body of the given Rule does
	 * not affect the operation.
	 * 
	 * @param rule
	 *            The Rule which should be deleted.
	 * @return True if the operation was successful.
	 */
	public boolean removeRule(Rule rule) {
		return this.ruleTable.remove(rule);
	}

	/**
	 * Returns the ruletable of this grammar.
	 * 
	 * @return a List of Rules
	 */
	public List<Rule> getRuleTable() {
		return this.ruleTable;
	}

	
	public Rule getRule(NonTerminal key) {
		
		if (this.getRuleTable().contains(new Rule(key, key))) {	
			return this.getRuleTable().get(this.getRuleTable().indexOf(new Rule(key, key))); 
		} else {
			return null;
		}
	}
	
	public String toString() {
		
		CoCoRGrammarWriter w = new CoCoRGrammarWriter();
		this.accept(w);
		
		return w.toString();
		
	}

	/**
	 * Returns True if both the start symbol is not set and the ruletable is empty.
	 */
	public boolean isEmpty() {
		return this.startSymbol == null && this.ruleTable.isEmpty();
	}

	/**
	 * Merges this Grammar with the given Grammar if, and only if the start symbols
	 * are identical. If yes, all Rules of the given Grammar are added.
	 * 
	 * @param g
	 *            the Grammar which should be merged.
	 * @return True if the operation was successful.
	 */
	public boolean merge(Grammar g) {

		if (this.startSymbol.equals(g.startSymbol)) {

			for (Rule rule : g.ruleTable) {
				this.addRule(rule);
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes all Rules an its NonTerminal key if the body of the Rule is empty. If
	 * the body of the Rule is a single NonTerminal Variable, all occurrences of the
	 * Rules key are substituted to this NonTerminal. All Rules are factorized and
	 * some small improvements to readability are made.
	 */
	public void simplify() {
		// TODO implement new
		/*
		this.removeEmptyRules();
		this.removeRulesWithSingleAssignments();

		GrammarSimplifier simplifier = new GrammarSimplifier();
		Grammar simplifiedGrammar = simplifier.simplify(this);
		
		

		this.startSymbol = simplifiedGrammar.startSymbol;
		this.ruleTable = simplifiedGrammar.ruleTable;
		*/
	}
	
	/** Tries to eliminate as much rules as possible and summarize them to one rule to rule them all */
	public void summarize() {

		GrammarSummarizer summarizer = new GrammarSummarizer();
		Grammar summarizedGrammar = summarizer.summarize(this);

		this.startSymbol = summarizedGrammar.startSymbol;
		this.ruleTable = summarizedGrammar.ruleTable;
	}
	
	// TODO Test if this works correctly
	/**
	 * Checks if every path in the grammar is terminating (in a sense that from every path a sequence of terminals can be derived) through calculating 
	 * the cost (see CostCalculator) of every NonTerminal. If it is not possible to calculate such a cost value the corresponding rule of the NonTerminal, 
	 * as well as every other occurrence of the NonTerminal in other rules are removed. If an alternative contains such a nonTerminal, it is removed from the alternatives 
	 * set. If a sequence contains such a NonTerminal, the whole sequence will be removed.
	 */
	public void removeNonTerminatingPaths() {
		
		CostCalculator calculator = new CostCalculator();
		Map<Element, Integer> costs = calculator.calculateCosts(this);
		
		Set<NonTerminal> nonTerminating = new HashSet<NonTerminal>();
		
		for (NonTerminal nt : this.getNonTerminals()) {
			if (!costs.containsKey(nt) || costs.get(nt) == 1) { 
				nonTerminating.add(nt);
			}
		}
		
		this.purge(nonTerminating);
		
		
		// Test if any new terminals are now unreachable
		costs = calculator.calculateCosts(this);
		for (NonTerminal nt : this.getNonTerminals()) {
			if (!costs.containsKey(nt) || costs.get(nt) == 1) {
				this.removeNonTerminatingPaths();
				break;
			};
		}	
	}
	
	
	/**
	 * Removes every occurrence of the given NonTerminals as well as every Rule, Sequence and Alternative which contains one of the given NonTerminals
	 * @param undesireables
	 */
	private void purge(Set<NonTerminal> undesireables) {
				
		// First remove nonTerminating Rules
		for (int i = 0; i < this.ruleTable.size(); i++) {
				
			if (undesireables.contains(this.ruleTable.get(i).getKey())) { 
				this.ruleTable.remove(i);
				i--;
			}
		}
		
		// Then purge the rest of the rules
		for (int i = 0; i < this.ruleTable.size(); i++) {
			
			if (this.ruleTable.get(i).getBody().purge(undesireables)) { 
				this.ruleTable.remove(i);
				i--;
			}
		}
		
	}
		

	/**
	 * Removes all Rules an its NonTerminal key if the body of the Rule is empty.
	 */
	public void removeEmptyRules() {

		List<Rule> obsolete = new ArrayList<Rule>();

		for (Rule rule : this.ruleTable) {
			if (rule.isEmpty()) {
				obsolete.add(rule);
			}
		}
		this.ruleTable.removeAll(obsolete);

		for (Rule obs : obsolete) {
			for (Rule rule : this.ruleTable) {
				rule.remove(obs.getKey());
			}
		}
	}

	/**
	 * If the body of the Rule is a single NonTerminal Variable, all occurrences of
	 * the Rules key are substituted to this NonTerminal.
	 */
	public void removeRulesWithSingleAssignments() {
		List<Rule> obsolete = new ArrayList<Rule>();

		for (Rule rule : this.ruleTable) {
			if (rule.getBody().getContent().size() == 1
					&& (rule.getBody().getContent().get(0).getClass().equals(NonTerminal.class)
							|| rule.getBody().getContent().get(0).getClass().equals(Terminal.class))) {

				if (rule.getBody().getClass().equals(NonTerminal.class)) {
					obsolete.add(rule);
				}
			}
		}
		this.ruleTable.removeAll(obsolete);

		for (Rule obs : obsolete) {

			if (obs.getKey().equals(this.startSymbol)) {
				this.startSymbol = (NonTerminal) obs.getBody();
			}

			for (Rule rule : this.ruleTable) {
				rule.replace(obs.getKey(), obs.getBody());
			}
		}
	}

	/**
	 * Checks if it is possible to derive a Sequence of Terminals starting with the start symbol using the rules of this grammar.
	 * @return True if the Grammar is complete.
	 */
	public boolean isComplete() {

		Grammar g = reduce();
		Terminal end = new Terminal("END");


		if (g.ruleTable.get(g.ruleTable.indexOf(new Rule(g.startSymbol, end))).getBody().equals(end)) {
			return true;
		}
		return false;
	}
	
	public Grammar reduce() {
		Grammar g = new Grammar();
		g.setStartSymbol(new NonTerminal(this.startSymbol.toString()));
		g.ruleTable = new ArrayList<Rule>();
		for (Rule rule : this.ruleTable) {
			g.addRule(new Rule((NonTerminal) rule.getKey().clone(), rule.getBody().clone()));
		}
		Terminal end = new Terminal("END");

		boolean foundNew = true;

		while (foundNew) {
			foundNew = false;
			for (Rule rule : g.ruleTable) {
				if (rule.isTerminating() && !rule.getBody().equals(end)) {
					rule.setBody(end);
					foundNew = true;
				}
			}
			g.eliminateRules();
		}
		return g;
	}
	
	public boolean eliminateRule(Rule rule) {
		
		if (rule.isRecursive()) { return false; }
		
		this.removeRule(rule);
		
		for (Rule r : this.ruleTable) {
			r.replace(rule.getKey(), rule.getBody());
		}
		
		return true;
	}

	private void eliminateRules() {

		Rule candidate = this.findNonRecursiveRule();
		while (candidate != null) {

			this.removeRule(candidate);
			for (Rule rule : this.ruleTable) {
				rule.replace(candidate.getKey(), candidate.getBody());
			}

			candidate = this.findNonRecursiveRule();
		}
	}

	private Rule findNonRecursiveRule() {
		for (Rule rule : this.ruleTable) {
			if (!rule.isRecursive() && !rule.getKey().equals(this.startSymbol)) {
				return rule;
			}
		}
		return null;
	}
	
	/**
	 * Returns a exact copy of this Grammar.
	 */
	public Grammar clone() {
		Grammar g = new Grammar();
		g.setStartSymbol((NonTerminal) this.startSymbol.clone());
		for (Rule rule : this.ruleTable) {
			g.addRule(rule.clone());
		}
		return g;
	}

	public void accept(GrammarVisitor v) {
		v.visit(this);
	}
}
