package grammar.grammar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Hannes Sochor
 * 
 *         Implements an Element which consists of a set of Elements which are
 *         logically connected via or Statements
 *
 */
public class Alternative extends Element {

	public Set<Element> alternatives;

	/**
	 * Creates an empty Alternative
	 */
	public Alternative() {
		this.alternatives = new HashSet<Element>();
	}

	/**
	 * Creates an Alternative with the given Elements
	 * @param alternatives A Set of Elements
	 */
	public Alternative(Set<Element> alternatives) {
		this.alternatives = new HashSet<Element>();

		for (Element e : alternatives) {

			if ((e.getClass().equals(Alternative.class) || e.getClass().equals(Sequence.class))
					&& e.getContent().size() == 1) {
				e = e.getContent().get(0);
			}

			if (e.isEmpty()) {
				continue;
			}

			if (e.getClass().equals(Alternative.class)) {
				this.alternatives.addAll(e.getContent());
			} else {
				this.alternatives.add(e);
			}
		}
	}
	
	public void empty() {
		this.alternatives = new HashSet<Element>();
	}

	public void add(Element e) {

		if ((e.getClass().equals(Alternative.class) || e.getClass().equals(Sequence.class))
				&& e.getContent().size() == 1) {
			e = e.getContent().get(0);
		}

		if (e.isEmpty()) {
			return;
		}

		if (e.getClass().equals(Alternative.class)) {
			this.alternatives.addAll(e.getContent());
		} else {
			this.alternatives.add(e);
		}
	}

	public void setAlternatives(Set<Element> alt) {
		this.alternatives = alt;
	}

	public void remove(Element e) {
		this.alternatives.remove(e);
		for (Element alt : this.alternatives) {
			alt.remove(e);
		}
	}
	
	public void simpleRemove(Element e) {
		this.alternatives.remove(e);
	}
	
	public boolean purge(Set<NonTerminal> nts) {
		
		List<Element> alternatives = new ArrayList<Element>(this.alternatives);
		
		for (int i = 0; i < alternatives.size(); i++) {
			if (alternatives.get(i).purge(nts)) {
				//System.out.println("purging " + alternatives.get(i));
				this.alternatives.remove(alternatives.get(i));
				//i--;
			}
		}
		
		if (this.alternatives.size() < 1) {
			return true;
		} else { return false; }
	}

	public boolean contains(Element e) {
		for (Element alternative : this.alternatives) {
			if (alternative.contains(e)) {
				return true;
			}
		}
		return false;
	}

	public void replace(Element key, Element body) {

		for (Element e : this.alternatives) {
			e.replace(key, body);
		}

		if (this.alternatives.contains(key)) {
			this.alternatives.remove(key);
			this.add(body);		
		}
		
	}

	public List<Element> getContent() {
		List<Element> content = new ArrayList<Element>();
		for (Element e : this.alternatives) {
			content.add(e);
		}
		
		Collections.sort(content, new Comparator<Element>() {
			public int compare(Element e1, Element e2) {
	            return e2.toString().compareTo(e1.toString());
	        }
		});
		
		return content;
	}
	
	public int size() {
		return alternatives.size();
	}

	public boolean isEmpty() {
		return this.alternatives.isEmpty();
	}

	public Element clone() {
		Element result = new Alternative();
		for (Element e : this.alternatives) {
			result.add(e.clone());
		}
		return result;
	}

	public boolean isTerminating() {
		for (Element e : this.alternatives) {
			if (e.isTerminating()) {
				return true;
			}
		}
		return false;
	}

	public Set<Terminal> getTerminals() {

		Set<Terminal> terminals = new HashSet<Terminal>();

		for (Element e : this.alternatives) {
			terminals.addAll(e.getTerminals());
		}

		return terminals;
	}
	
	public Set<NonTerminal> getNonTerminals() {

		Set<NonTerminal> terminals = new HashSet<NonTerminal>();

		for (Element e : this.alternatives) {
			terminals.addAll(e.getNonTerminals());
		}

		return terminals;
	}

	@Override
	public String toString() {

		if (this.alternatives.size() == 1) {
			return ((Element) this.alternatives.toArray()[0]).toString();
		}

		List<String> list = new ArrayList<String>();
		for (Element e : this.getContent()) {
			list.add(e.toString());
		}
		Collections.sort(list);

		String result = "(";

		for (String s : list) {
			result += s;
			if (list.indexOf(s) != list.size() - 1) {
				result += " | ";
			}
		}
		return result + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alternatives == null) ? 0 : alternatives.hashCode());
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
		Alternative other = (Alternative) obj;
		if (alternatives == null) {
			if (other.alternatives != null)
				return false;
		} else if (!alternatives.equals(other.alternatives))
			return false;
		return true;
	}

	@Override
	public void accept(GrammarVisitor v) {
		v.visit(this);
	}
}
