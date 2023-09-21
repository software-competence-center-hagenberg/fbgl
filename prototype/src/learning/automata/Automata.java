package learning.automata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import grammar.grammar.Element;
import grammar.grammar.Grammar;
import grammar.grammar.NonTerminal;
import grammar.grammar.Rule;
import grammar.grammar.Sequence;
import grammar.grammar.Terminal;

public class Automata {
	
	Set<Element> alphabet;
	String initialState;
	public List<String> states = new ArrayList<String>();
	public Set<String> finalStates = new HashSet<String>();
	List<Transition> transitions = new ArrayList<Transition>();
	
	
	public Automata() {
		
	}
	
	public Automata(Set<Element> alphabet, String initialState, List<String> states, Set<String> finalStates, List<Transition> transitions) {
		
		this.alphabet = alphabet;
		this.initialState = initialState;
		this.states = states;
		this.finalStates = finalStates;
		this.transitions = transitions;
	}
	
	public void setAlphabet(Set<Element> alphabet) {
		this.alphabet = alphabet;
	}
	
	public void setInitialState(String state) {
		this.initialState = state;
	}
	
	public void addState(String state) {
		this.states.add(state);
	}
	
	public void addFinalState(String state) {
		this.finalStates.add(state);
	}
	
	public void addTransition(Transition transition) {
		if (!this.transitions.contains(transition)) { this.transitions.add(transition); }
	}
	
	public Transition createTransition(String source_state, Element a, String end_state) {
		return new Transition(source_state, a, end_state);
	}
	
	public Grammar toGrammar() throws Exception {
		
		Grammar grammar = new Grammar();
		grammar.setStartSymbol(new NonTerminal("S"));
		grammar.addRule(new Rule(grammar.getStartSymbol(), new NonTerminal(this.initialState)));
		
		
		if (this.finalStates.isEmpty()) {
			grammar.addRule(new Rule(new NonTerminal(this.initialState), new Terminal("")));
			return grammar;
		}
		
		/*
		List<String> trapStates = new ArrayList<String>();
		for (String state : states) {
			boolean trap = true;
			
			for (Element a : this.alphabet) {
				Pair p = new Pair(state, a);
				if (!this.transitions.get(p).equals(state)) { trap = false; }
			}
			
			if (trap) { trapStates.add(state); }
		}	
		*/		
		
		for (Transition transition : transitions) {	
			//if (!trapStates.contains(key.state) && !trapStates.contains(key.a.toString()) && !trapStates.contains(transitions.get(key))) {
			Sequence s = new Sequence();
			s.add(transition.a);
			s.add(new NonTerminal(transition.end_state));
			
			grammar.addRule(new Rule(new NonTerminal(transition.source_state), s));
			//}
		}
		
		for (String finalState : finalStates) {
			grammar.addRule(new Rule(new NonTerminal(finalState), new Terminal("")));
		}
		
		grammar.summarize();	
		return grammar;
	}
	
	@Override		
	public String toString() {
		
		String result = "";
		result += "States: ";
		for (String state: states) { result += state + ", "; }
		result += "\nInitial State: " + this.initialState; 
		result += "\nFinalStates: ";
		for (String state: this.finalStates) { result += state + ", "; }
		result += "\nTransitions:";
		for (Transition transition : this.transitions) { result += "\n" + transition.source_state + " + " + transition.a + " --> " + transition.end_state; }
					
		return result;
	}
	
	public class Transition {
		
		String source_state;
		Element a;
		String end_state;
		
		
		public Transition(String source, Element a, String end) {
			this.source_state = source;
			this.a = a;
			this.end_state = end;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((a == null) ? 0 : a.hashCode());
			result = prime * result + ((end_state == null) ? 0 : end_state.hashCode());
			result = prime * result + ((source_state == null) ? 0 : source_state.hashCode());
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Transition other = (Transition) obj;
			if (a == null) {
				if (other.a != null)
					return false;
			} else if (!a.equals(other.a))
				return false;
			if (end_state == null) {
				if (other.end_state != null)
					return false;
			} else if (!end_state.equals(other.end_state))
				return false;
			if (source_state == null) {
				if (other.source_state != null)
					return false;
			} else if (!source_state.equals(other.source_state))
				return false;
			return true;
		}
	}
}
