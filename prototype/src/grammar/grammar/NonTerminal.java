package grammar.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Hannes Sochor
 * 
 *         Implements an Elements which represents a NonTerminal Variable of a
 *         Grammar
 *
 */
public class NonTerminal extends Element {

	private String identifier;

	/**
	 * Creates a NonTerminal Object which is identified by the given string.
	 * @param identifier the string which identifies the NonTerminal
	 */
	public NonTerminal(String identifier) {
		this.identifier = identifier;
	}
	
	public void empty() {}

	public void add(Element e) {
	}

	public void remove(Element e) {
	}
	
	public void simpleRemove(Element e) {
	}
	
	public boolean purge(Set<NonTerminal> nts) {
		
		if (nts.contains(this)) { return true; }
		else { return false; }
		
	}

	public boolean contains(Element e) {
		return this.equals(e);
	}

	public void replace(Element key, Element body) {
		
	}

	public List<Element> getContent() {
		return new ArrayList<Element>(Arrays.asList(this));
	}

	public boolean isEmpty() {
		return false;
	}

	public Element clone() {
		return new NonTerminal(this.identifier);
	}

	public boolean isTerminating() {
		return false;
	}

	public Set<Terminal> getTerminals() {
		return new HashSet<Terminal>();
	}
	
	public Set<NonTerminal> getNonTerminals() {
		return new HashSet<NonTerminal>(Arrays.asList(this));
	}

	@Override
	public String toString() {
		return this.identifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
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
		NonTerminal other = (NonTerminal) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}

	@Override
	public void accept(GrammarVisitor v) {
		v.visit(this);
	}
}