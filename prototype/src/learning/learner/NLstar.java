package learning.learner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import grammar.grammar.Element;
import grammar.grammar.Sequence;
import grammar.grammar.Terminal;
import learning.automata.Automata;
import learning.teacher.Teacher;

public class NLstar {

	List<Element> U = new ArrayList<Element>();
	List<Element> UA = new ArrayList<Element>();
	List<Element> V = new ArrayList<Element>();
	List<Element> A = new ArrayList<Element>();
	final Terminal LAMBDA;
	Teacher teacher;
	
	public NLstar(List<Element> alphabet, Teacher teacher, Terminal lambda) {
		
		this.A = alphabet;
		this.teacher = teacher;
		this.LAMBDA = lambda;
		constructInitialTUV();

	}
	
	private void constructInitialTUV() {
		
		expandU(LAMBDA);
		V.add(LAMBDA);
		
	}
	
	public Automata learn() throws Exception {		
		
		//System.out.println("Start lstar round 1 ... ");
		//System.out.println("Making table closed and consistent ... ");
		this.makeTableClosedAndConsistent();
		
		//System.out.println("Creating Acceptor ... ");
		Automata M = createNFA();
		//System.out.println(M.toString());
				
		System.out.println("Finding counterexample ... ");
		Sequence counterexample = teacher.conjecture(M);
		
		
		while(counterexample != null) {
			counterexample.remove(LAMBDA);
			
			System.out.println("Adding counterexample ... (\"" + counterexample + "\")");
			boolean noErr = this.addCounterexample(counterexample);
			if (!noErr) { return null; }
						
			//System.out.println("Making table closed and consistent ... ");
			this.makeTableClosedAndConsistent();
			
			//System.out.println("Creating Acceptor ... ");
			M = createNFA();
			
			System.out.println("Finding counterexample ... ");
			counterexample = teacher.conjecture(M);

		}
		return M;
	}
	
	private void makeTableClosedAndConsistent() throws Exception {
		while(42 == Integer.parseInt(("42"))) {
					
			//System.out.println(this);

			//System.out.println("Checking closeness ... ");
			
			Element t = this.isRFSAclosed();
			if (t != null) { this.expandU(t); continue;}
			
			//System.out.println("is closed");
			
			//System.out.println("Checking consistency ... ");
			
			List<Element> candidates = this.isRFSAconsistent();
			if (candidates != null) { this.fixInconsistency(candidates); continue; }
			
			//System.out.println("is consistent");

			break;			
		}
	}
	
	private void expandU(Element t) {
		
		Sequence seq = new Sequence();
		for (Element e : t.getContent()) { if (!e.equals(LAMBDA)) { seq.add(e); }}
		
		if (U.contains(seq)) { return; }
		
		this.U.add(seq);
		this.UA.remove(seq);
		
		for (Element a : A) {
			
			Sequence ta = new Sequence();
			ta.add(seq);
			ta.add(a);
			
			if (!UA.contains(ta)) { this.UA.add(ta); }
		}	
	}
	
	/* RFSA closeness is defined as:
	 * 
	 * For each r element of UA: r == Join{r' element of Primes_upp(T)| r' is covered by r}
	 * 
	 * returns null if the table is closed, otherwise a candidate ua which should be added to U
	 */
	private Element isRFSAclosed() {
		
		for (Element e : UA) {
			
			Row r = new Row(e);

			//if (!r.isPrime()) { continue; }
			
			List<Row> upperPrimeCoveringRows = new ArrayList<Row>();
			
			for (Element el : U) {
				
				Row r_c = new Row(el);
				
				if (r_c.isPrime() && r.covers(r_c)) {
					upperPrimeCoveringRows.add(r_c);
				}
			}
			
			Row joinedRow = new Row();
			for (Row r_c : upperPrimeCoveringRows) { joinedRow = joinedRow.join(r_c); }
				
			if (!joinedRow.equals(r)) { return e; }
		}
		return null;		
	}
		
	private boolean addCounterexample(Element counterexample) {
		
		// Check if all non-terminals in the counterexample are indeed in the current alphabet - if not abort learning
		if (!this.A.containsAll(counterexample.getNonTerminals())) { return false; }
		
		
		if (!this.V.contains(counterexample)) { this.V.add(counterexample); }
		
		List<Element> ce = counterexample.getContent();
		
		for (int i = 1; i < ce.size(); i++) {
			Sequence seq = new Sequence();
			for (int j = i; j < ce.size(); j++) {
				seq.add(ce.get(j));
			}
			if (!this.V.contains(seq)) { this.V.add(seq); }
		}
		return true;
	}
	
	/*
	 * RFSA consistency is defined as:
	 * 
	 * for all u, u' element of U and a element of A
	 * if row(u) covers row(u') implies that row(ua) covers (u'a)
	 * 
	 * returns null if table is consistent, otherwise a triple (u, u', a) of candidates which should be fixed
	 */
	private List<Element> isRFSAconsistent() {
		
		for (Element u : U) {
			for (Element u_c: U) {
				
				if (!u.equals(u_c) && (new Row(u)).covers(new Row(u_c))) {
					
					// Now for every ua and u'a the same must be the case
					for (Element a : A) {
						
						Sequence ua = new Sequence();
						ua.add(u);
						ua.add(a);
						
						Sequence u_ca = new Sequence();
						u_ca.add(u_c);
						u_ca.add(a);
						
						if (!(new Row(ua)).covers(new Row(u_ca))) {	
							List<Element> candidates = new ArrayList<Element>();
							candidates.add(u);
							candidates.add(u_c);
							candidates.add(a);
							return candidates;
						}
					}			
				}
			}
		}	
		return null;
	}
	
	private void fixInconsistency(List<Element> candidates) throws Exception {
		
		Element a = candidates.get(2);
		
		Sequence ua = new Sequence();
		ua.add(candidates.get(0));
		ua.add(a);
		
		Sequence u_ca = new Sequence();
		u_ca.add(candidates.get(1));
		u_ca.add(a);
		
		// Try to find a v element of V so that T(uav) = false and T(u_cav) = true, then add av to V
		Sequence av = null;
		for (Element v : this.V) {
			
			Sequence uav = (Sequence) ua.clone();
			uav.add(v);
			
			Sequence u_cav = (Sequence) u_ca.clone();
			u_cav.add(v);
			
			if(!this.teacher.membershipQuery(uav) && this.teacher.membershipQuery(u_cav)) {
				av = new Sequence();
				av.add(a);
				av.add(v);
			}
		}
		if (av != null) {
			this.V.add(av);
		} else {		
			throw new Exception("Unable to fix inconsistency ...");
		}
		
	}
	
	/*
	 * Creates an NFA (Q, Q_0, F, t) where
	 * 
	 * Q = Primes_upp(T)
	 * Q_0 = {r element of Q | row(lambda) covers r}
	 * F = {r element of Q | r(lambda) = true}
	 * t(row(u), a) = {r element of Q | row(ua) covers r} for u element of U, row(u) element of Q, a element of A
	 */
	private Automata createNFA() {
		
		Automata M = new Automata();
		M.setAlphabet(new HashSet<Element>(this.A));
		
		// Find states Q
		List<Row> Q = new ArrayList<Row>();
		for (Element u : this.U) {
			Row r = new Row(u);
			if (r.isPrime()) { Q.add(r); }
		}
		
		// Map states to q_x naming
		Map<Row, String> translation = new HashMap<Row, String>();
		int i = 1;
		for (Row state : Q) {
			translation.put(state, ("q"+i++));
			M.addState(translation.get(state));
		}
		
		// Find initial state
		Row lambda_row = new Row(this.LAMBDA);
		for (Row r : Q) {
			if (r.covers(lambda_row)) { 
				if (r.equals(lambda_row)) {
					M.setInitialState(translation.get(r));
				} else {
					M.addState("q0");
					translation.put(lambda_row, "q0");
					M.setInitialState("q0");
					// Add transition to r
					for (Element e : U) {
						if (new Row(e).equals(r)) {
							M.addTransition(M.createTransition("q0", e, translation.get(r)));
						}
					}
				}
			}
		}
		
		// Find final states F
		List<Row> F = new ArrayList<Row>();
		for (Element u : this.U) {
			if (this.teacher.membershipQuery(u) && Q.contains(new Row(u))) { M.addFinalState(translation.get(new Row(u))); }
		}
		
		// Find transitions
		for (Element u : U) {
			if (!Q.contains(new Row(u))) { continue; }
			for (Element a : A) {
				for (Row r : Q) {
					Sequence ua = new Sequence();
					ua.add(u);
					ua.add(a);
					
					if (new Row(ua).covers(r)) { 
						M.addTransition(M.createTransition(translation.get(new Row(u)), a, translation.get(r)));
					}
				}
			}
		}
		
		return M;
	}
	
	
	@Override
	public String toString() {
		
		String table = "";
		
		for (int i = 0; i < V.size()+1; i++) { table += "--------"; }
		
		table += "-\n| T\t| ";
		
		for (Element e : V) {
			table += e.toString() + "\t|";
		}
		table += "\n";
		for (int i = 0; i < V.size()+1; i++) { table += "--------"; }
		table += "-";
		
		for (Element e : U) {
			
			if ((new Row(e)).isPrime()) {
				table += "\n|* ";
			} else { table += "\n|  "; }
			
			
			table += e.toString() + "\t| ";
			
			for (Element el : V) {
				
				Sequence s = new Sequence();
				s.add(e);
				s.add(el);
				
				table += teacher.membershipQuery(s) + "\t| ";
			}
		}
		
		table += "\n";
		for (int i = 0; i < V.size()+1; i++) { table += "--------"; }
		table += "-";
		
		for (Element e : UA) {
			
			if ((new Row(e)).isPrime()) {
				table += "\n|* ";
			} else { table += "\n|  "; }
			
			table += e.toString() + "\t| ";
			
			for (Element el : V) {
				
				Sequence s = new Sequence();
				s.add(e);
				s.add(el);
				
				table += teacher.membershipQuery(s) + "\t| ";
			}
		}
		
		table += "\n";
		for (int i = 0; i < V.size()+1; i++) { table += "--------"; }
		table += "-";
		
		return table;
	}
	
	
	private class Row {
		
		List<Boolean> values;
		
		Row() {
			
			this.values = new ArrayList<Boolean>();
			for (int i = 0; i < V.size(); i++) {this.values.add(false); }
			
		}
				
		Row(List<Boolean> values) {
			this.values = values;
		}
		
		Row(Element element) {
			
			values = new ArrayList<Boolean>();
			
			for (Element e : V) {
				Sequence s = new Sequence();
				s.add(element);
				s.add(e);
				values.add(teacher.membershipQuery(s));
			}
		}
		
		Row join(Row r2) {
			
			List<Boolean> values = new ArrayList<Boolean>();
			
			for (int i = 0; i < this.values.size(); i++) {
				values.add(this.values.get(i) || r2.values.get(i));
			}
			
			return new Row(values);
		}
		
		boolean isPrime() {
			
			List<Row> candidates = new ArrayList<Row>();
						
			for (Element e: U) { 
				Row row = new Row(e);
				if (row.equals(this)) { continue; }
				int i;
				for (i = 0; i < this.values.size(); i++) {
					if (!this.values.get(i) && row.values.get(i)) { i = -1; break; }
				}
				if (i != -1) { candidates.add(row); }
			}
			
			for (Element e: UA) { 
				Row row = new Row(e);
				if (row.equals(this)) { continue; }
				int i;
				for (i = 0; i < this.values.size(); i++) {
					if (!this.values.get(i) && row.values.get(i)) { i = -1; break; }
				}
				if (i != -1) { candidates.add(row); }
			}
			
			Row composedRow = new Row();			
			for (Row row: candidates) { 
				composedRow = composedRow.join(row);
			}
			
			if (!this.equals(composedRow)) { return true; }
			
			return false;
		}
				
		boolean covers(Row r2) {
			
			for (int i = 0; i < this.values.size(); i++) {
				
				if (r2.values.get(i)) {
					if (!this.values.get(i)) { return false; }
				}
			}
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((values == null) ? 0 : values.hashCode());
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
			Row other = (Row) obj;
			if (values == null) {
				if (other.values != null)
					return false;
			} else if (!values.equals(other.values))
				return false;
			return true;
		}
		
	}
}