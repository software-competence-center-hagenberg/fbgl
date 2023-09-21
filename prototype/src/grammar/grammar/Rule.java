package grammar.grammar;

import java.io.Serializable;
import java.util.Set;

/**
 * 
 * @author Hannes Sochor
 * 
 *         Represents a Rule of a Grammar. A Rule consists of a left part
 *         (NonTerminal) which can be substituted with the right part (a random
 *         Element)
 *
 */
public class Rule implements Serializable {

	private static final long serialVersionUID = 1L;
	private final NonTerminal key;
	private Element body;
	private boolean sep = false;

	/**
	 * Creates a new Rule by with the given Elements.
	 * 
	 * @param key
	 *            A NonTerminal on the left side
	 * @param body
	 *            A random Element on the right side
	 */
	public Rule(NonTerminal key, Element body) {
		this.key = key;

		if ((body.getClass().equals(Alternative.class) || body.getClass().equals(Sequence.class))
				&& body.getContent().size() == 1) {
			body = body.getContent().get(0);
		}

		this.body = body;
	}
	
	public boolean isSep() {
		return this.sep;
	}
	
	public void setSep(boolean sep) {
		this.sep = sep;
	}
	
	public NonTerminal getKey() {
		return this.key;
	}

	public Element getBody() {
		return this.body;
	}

	public void setBody(Element body) {
		this.body = body;
	}

	public void remove(Element e) {
		this.body.remove(e);
	}

	public void replace(Element key, Element body) {
		
		if (this.body.equals(key)) {
			this.body = body;
		} else {
			this.body.replace(key, body);
		}
	}

	public boolean isEmpty() {
		return this.body.isEmpty();
	}

	public Rule clone() {
		Rule r = new Rule((NonTerminal) this.key.clone(), this.body.clone());
		r.setSep(this.sep);
		return r;
	}

	public String toString() {
		return this.key.toString() + " => " + this.body.toString();
	}

	public boolean merge(Rule rule) {
		if (this.body.getClass().equals(new Alternative().getClass())) {
			this.body.add(rule.body);
		} else {
			Element newBody = new Alternative();
			newBody.add(this.body);
			newBody.add(rule.body);
			this.body = newBody;
		}
		return true;
	}

	/**
	 * Checks if the body of the rule contains the key.
	 * 
	 * @return True if the key is also found in the body
	 */
	public boolean isRecursive() {
		return this.body.contains(this.key);
	}

	/**
	 * Checks if this rule has a path which leads to a Terminal or a Sequence of
	 * Terminals
	 * 
	 * @return True if there is a path to a Terminal or a Sequence of Terminals
	 */
	public boolean isTerminating() {
		return this.body.isTerminating();
	}

	public Set<Terminal> getTerminals() {
		return this.body.getTerminals();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		Rule other = (Rule) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	public void accept(GrammarVisitor v) {
		v.visit(this);
	}
}