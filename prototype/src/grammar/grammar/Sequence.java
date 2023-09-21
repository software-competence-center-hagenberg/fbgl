package grammar.grammar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Hannes Sochor
 * 
 * An Element which represents a Sequence of Elements in a Grammar.
 *
 */
public class Sequence extends Element {
	
	private List<Element> sequence;
	
	/**
	 * Creates an empty Sequence
	 */
	public Sequence() {
		this.sequence = new ArrayList<Element>();
	}
	
	/**
	 * Creates a Sequence with the given List of Elements
	 * @param seq The List of Elements which should define the Sequence
	 */ 
	public Sequence(List<Element> seq) {	
		
		this.sequence = new ArrayList<Element>(); 
		for (Element e : seq) {
			
			if ((e.getClass().equals(Alternative.class) || e.getClass().equals(Sequence.class)) && e.getContent().size() == 1) {
				e = e.getContent().get(0);
			}
			
			if (e.isEmpty()) {
				continue;
			}
			
			if (e.getClass().equals(Sequence.class)) {
				this.sequence.addAll(e.getContent());
			} else {
				this.sequence.add(e);
			}
		}		
	}
	
	public void empty() {
		this.sequence = new ArrayList<Element>();
	}
	
	public void add(Element e) {
		
		if ((e.getClass().equals(Alternative.class) || e.getClass().equals(Sequence.class)) && e.getContent().size() == 1) {
			e = e.getContent().get(0);
		}
		
		if (e.isEmpty()) {
			return;
		}
		
		if (e.getClass().equals(Terminal.class) && e.equals((new Terminal("")))) { return; }
		
		if (e.getClass().equals(Sequence.class)) {
			this.sequence.addAll(e.getContent());
		} else {
			this.sequence.add(e);
		}
	}
	
	public void remove(Element e) {
		
		while(this.sequence.contains(e)) { this.sequence.remove(e); }
		for (Element seq : this.sequence) {
			seq.remove(e);
		}
	}
	
	public void simpleRemove(Element e) {
		while(this.sequence.contains(e)) { this.sequence.remove(e); }
	}
	
	public boolean purge(Set<NonTerminal> nts) {
		for (int i = 0; i < this.size(); i++) {
			if (this.sequence.get(i).purge(nts)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(Element e) {
		for (Element part : this.sequence) {
			if (part.contains(e)) {
				return true;
			}
		}
		return false;
	}
	
	public void replace(Element key, Element body) {
				
		for (Element e : this.sequence) {
			e.replace(key, body);
		}
		
		List<Element> newSeq = new ArrayList<Element>();
		
		for (Element e : this.sequence) {
			if (e.equals(key)) {
				
				if ((e.getClass().equals(Alternative.class) || e.getClass().equals(Sequence.class)) && e.getContent().size() == 1) {
					e = body.getContent().get(0);
				}		
				
				if (e.getClass().equals(Sequence.class)) {
					newSeq.addAll(body.getContent());
				} else {
					newSeq.add(body);
				}
				
			} else {
				newSeq.add(e);
			}
		}
		
		this.sequence = newSeq;
	}
	
	public List<Element> getContent() {
		return this.sequence;
	}
	
	public int size() {
		return sequence.size();
	}
	
	public boolean isEmpty() {
		return this.sequence.isEmpty();
	}
	
	public Element clone() {
		
		if (this.sequence == null) { return null; }
		
		Sequence result = new Sequence();
		for (Element e : this.sequence) {
			result.add(e.clone());
		}
		return result;
	}
		
	public boolean isTerminating() {
		for (Element e : this.sequence) {
			if (!e.isTerminating()) {
				return false;
			}
		}
		return true;
	}
	
	public Set<Terminal> getTerminals() {
		
		Set<Terminal> terminals = new HashSet<Terminal>();
		
		for (Element e : this.sequence) {
			terminals.addAll(e.getTerminals());
		}
		
		return terminals;	
	}
	
	public Set<NonTerminal> getNonTerminals() {
		
		Set<NonTerminal> terminals = new HashSet<NonTerminal>();
		
		for (Element e : this.sequence) {
			terminals.addAll(e.getNonTerminals());
		}
		
		return terminals;	
	}

	@Override
	public String toString() {
		
		if (this.sequence.size() == 1) {
			return this.sequence.get(0).toString();
		}
		
		String result = "(";
		
		for (Element e : this.sequence) {
			result += e.toString();
			if (this.sequence.indexOf(e) != this.sequence.size()-1) {
				result += " ";
			}
		}
		return result + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
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
		Sequence other = (Sequence) obj;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		return true;
	}

	@Override
	public void accept(GrammarVisitor v) {
		v.visit(this);
	}
}
