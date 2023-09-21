package grammar.grammar;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Hannes Sochor
 * 
 *         An abstract element.
 *
 */
public abstract class Element implements Serializable {

	private static final long serialVersionUID = 1L;
	public boolean isSep = false;

	/**
	 * Adds the given element to this element only if this element is not a Terminal
	 * or a NonTerminal
	 * 
	 * @param e
	 *            the element which should be added
	 */
	public abstract void add(Element e);

	/**
	 * Recursively removes the given element from this element only if this element
	 * is not a Terminal or a NonTerminal.
	 * 
	 * @param e
	 *            the element which should be removed.
	 */
	public abstract void remove(Element e);
	
	public abstract void simpleRemove(Element e);
	
	public abstract boolean purge(Set<NonTerminal> nts);

	/**
	 * Checks if this element is or contains the given element.
	 * 
	 * @param e
	 *            the element which should be checked.
	 * @return True if this element is or contains the given element.
	 */
	public abstract boolean contains(Element e);

	/**
	 * Recursively replaces the given key with the given body in this element.
	 * 
	 * @param key
	 *            the element which should be replaced.
	 * @param value
	 *            the element which should be in place of the key
	 */
	public abstract void replace(Element key, Element value);

	/**
	 * Returns a List of Elements which are contained in this element
	 * 
	 * @return A List of Elements
	 */
	public abstract List<Element> getContent();

	/**
	 * Returns if the element is empty
	 * 
	 * @return True if the element is empty
	 */
	public abstract boolean isEmpty();

	/**
	 * Returns an exact copy of the element
	 */
	public abstract Element clone();

	/**
	 * Checks if there is a path to a Terminal or a Sequence of Terminals in this
	 * element
	 * 
	 * @return True if there is a Terminal or a Sequence of Terminals
	 */
	public abstract boolean isTerminating();

	/**
	 * Returns all Terminals which are contained in this element
	 * 
	 * @return a Set of Elements
	 */
	public abstract Set<Terminal> getTerminals();
	public abstract Set<NonTerminal> getNonTerminals();
	
	public abstract void empty();

	public abstract void accept(GrammarVisitor v);
}
