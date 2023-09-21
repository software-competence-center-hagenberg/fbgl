package learning.teacher;

import grammar.grammar.Element;
import grammar.grammar.Grammar;
import grammar.grammar.Sequence;
import grammar.grammar.Terminal;
import learning.automata.Automata;

public abstract class Teacher {

	private Terminal lambda;

	public abstract void setQueryCount(int mq, int eq);
	public abstract int[] getQueryCount();
	public abstract boolean membershipQuery(Element e);
	public abstract Sequence conjecture(Automata M) throws Exception;
	public abstract Grammar buildGrammar(Automata M) throws Exception;
}
