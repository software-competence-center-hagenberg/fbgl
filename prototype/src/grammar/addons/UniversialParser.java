package grammar.addons;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import grammar.grammar.Alternative;
import grammar.grammar.Element;
import grammar.grammar.Grammar;
import grammar.grammar.NonTerminal;
import grammar.grammar.Rule;
import grammar.grammar.Sequence;
import grammar.grammar.Terminal;

/**
 * Implementation of a simple Early Parser modified to handle epsilon rules. 
 * Complexity: O(R^2 N^3) where R is number of Rules and N is the string length.
 * 
 * @author Hannes Sochor
 *
 */

public class UniversialParser {
	
	Grammar grammar;
	List<List<Item>> tables;
	Set<NonTerminal> nullables;
	int overflowcounter = 0;
	final int _MAX_RECURSIVE_CALLS_ = 100;
	
	public UniversialParser(Grammar g) {
		this.grammar = new GrammarDismantler().dismantle(g);
		
		/*
		int size = 0;
		for (NonTerminal nt : this.grammar.getNonTerminals()) {
			size += this.getItemsForNonTerminalFromGrammar(nt, 0).size();
		}
		System.out.println(size);
		*/
		
		this.manageStartSymbol();
		this.manageEmptyTerminals();
		
		nullables = this.findNullables();		
	}
	
	public Grammar getGrammar() {
		return this.grammar;
	}
	
	public TreeNode getSyntaxTree(String input) {
		
		this.parse(input);
		
		Set<Item> parseTable = new HashSet<Item>();
		
		for (List<Item> table : this.tables) {
			for (Item item : table) {
				if (item.isFinal()) {
					parseTable.add(item);
					//System.out.println(item);
				}
			}
		}
		
		TreeNode root = new TreeNode(this.grammar.getStartSymbol(), null, this.grammar);
		
		// Validate parseTable (for multible construction paths for an input)
		List<List<Item>> doubleEntries = new ArrayList<List<Item>>();
		List<Item> parseList = List.copyOf(parseTable);
		for (int i = 0; i < parseList.size(); i++) {
			List<Item> val = new ArrayList<Item>();
			val.add(parseList.get(i));
			for (int j = i+1; j < parseList.size(); j++) {
				if (parseList.get(i).start == parseList.get(j).start && parseList.get(i).rhs.equals(parseList.get(j).rhs)) {
					val.add(parseList.get(j));
				}
			}
			if (val.size() > 1) { doubleEntries.add(val); }
		}
		
		/*
		for (List<Item> entries : doubleEntries) {
			System.out.println("----------------");
			for (Item item : entries) { System.out.println(item); }
		}
		*/
		
		if (!doubleEntries.isEmpty()) {
			
			int paths = 1;
			for (List<Item> entries : doubleEntries) { paths *= entries.size(); }
			
			System.out.println("WARNING: Multiple possibilities for input syntax tree found. This will lead to an exponential runtime. First valid path will be taken. May lead to a stack overflow depending on the problem size. Possible paths: " + paths + "\nEach dot equals 1/1000 of the search space done.");
			
			int[] selection = new int[doubleEntries.size()+1];
						
			int i = 0;
			while (true) {
				
				if (i++%(paths/1000) == 0) {System.out.print(".");}
				//System.out.println("Testing path " + i);
				
				TreeNode candidate = testSelection(selection, doubleEntries, parseTable, root);
				if (candidate != null && candidate.toString().equals(input)) { 
					return candidate; 
				}
				
				selection[0]++;
				
				for (int pos = 0; pos < doubleEntries.size(); pos++) {
					if (selection[pos] >= doubleEntries.get(pos).size()) {
						selection[pos] = 0;
						selection[pos+1] += 1;
					}
				}
				if (selection[doubleEntries.size()] == 1) {
					return null;
				}
			}
			
			
		} else {
			try {
				this.expandNode(root, parseTable, 0);
			} catch (OverflowDangerException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		//root.printTree();
		//System.out.println(root);
		return root;
	}
	
	private TreeNode testSelection(int[] selection, List<List<Item>> doubleEntries, Set<Item> parseTable, TreeNode root) {
		
		//System.out.println(globalcounter++);
		//System.out.println(Arrays.toString(selection));
		//return null;
		
		
		Set<Item> table = new HashSet<Item>();
		for (Item item : parseTable) { table.add(item); }
		
		for (int i = 0; i < doubleEntries.size(); i++) {
			for (int j = 0; j < doubleEntries.get(i).size(); j++) {
				if (j != selection[i]) { table.remove(doubleEntries.get(i).get(j)); }
			}
		}
		
		try {
			this.overflowcounter = 0;
			this.expandNode(root, table, 0);
			return root;
		} catch (OverflowDangerException e) {
			return null;
		}
	}
	
	private int expandNode(TreeNode node, Set<Item> parseTable, int pos) throws OverflowDangerException {
		
		this.overflowcounter++;
		if (this.overflowcounter > this._MAX_RECURSIVE_CALLS_) { throw new OverflowDangerException(); }
		
		for (Item item : parseTable) {
			
			if (item.start == pos && item.rhs.equals(node.node) ) {
				for (Element e : item.lhs) {

					if (e.getClass().equals(Terminal.class)) {
						pos++;
					}
					
					TreeNode child = new TreeNode(e, node, this.grammar);
					node.addChildren(child);
					
					if (child.node.equals(new NonTerminal("NULL"))) {
						child.addChildren(new TreeNode(new Terminal(""), child, this.grammar));
					}
					
					if (!e.getClass().equals(Terminal.class)) {
						//System.out.println(globalcounter++);
						pos = this.expandNode(child, parseTable, pos);
					}
				}
				/* ### TODO ###
				 * There is no handling of two suitable rules implemented - currently both are added after one another.
				 */
				//break;
			}
		}
		
		for (int i = 0; i < node.children.size() && node.children.size() > 1;) {
			
			if (node.children.get(i).node.equals(new NonTerminal("NULL"))) {
				node.children.remove(i);
				continue;
			}
			i++;			
		}	
		
		this.overflowcounter--;
		return pos;
	}
		
	public boolean parse(String input) {
		
		tables = new ArrayList<List<Item>>();
		
		String inputs[] = input.split("");
 		
		List<Item> startItems = getItemsForNonTerminalFromGrammar(this.grammar.getStartSymbol(), 0);
		if (startItems.size() > 1) {
			System.out.println("Invalid starting rule syntax");
			return false;
		} 
		Item start = startItems.get(0);
		
		List<Item> table = new ArrayList<Item>();
		table.add(start);
		
		this.predict(table, 0);
		this.tables.add(table);
		
		for (int i = 0; i < inputs.length; i++) {
			
			//System.out.println("Building table #" + (i+1));

			
			table = this.scan(table, new Terminal(inputs[i]));	
			this.tables.add(table);		
			
			int l;
			int j = 1;
			
			do {
				//System.out.println("Round " + j++);
				l = table.size();
				//System.out.println("Predicting");
				predict(table, (i+1));	
				//for (Item item : table) {
				//	System.out.println(item.toString());
				//}
				//System.out.println("Completing");
				this.complete(table);
				//for (Item item : table) {
				//	System.out.println(item.toString());
				//}
				
			} while (table.size() > l);
			//for (Item item : table) {
			//	System.out.println(item.toString());
			//}
		}
		
	
		//printTables();
		return isValid(table);

	}
	
	private void manageStartSymbol() {
		NonTerminal start = new NonTerminal("EARLYSTART");
		this.grammar.addRule(new Rule(start, this.grammar.getStartSymbol()));
		this.grammar.setStartSymbol(start);
	}
	
	private void manageEmptyTerminals() {
		
		NonTerminal lambda = new NonTerminal("NULL");
				
		for (Rule rule : this.grammar.getRuleTable()) {
			rule.replace(new Terminal(""), lambda);
		}
		
		this.grammar.addRule(new Rule(lambda, new Terminal("")));
	}
	
	private void printTables() {
		
		for (int i = 0; i < this.tables.size(); i++) {
			System.out.println("\nQ_" + i + "\n------------------");
			for (Item item : this.tables.get(i)) { System.out.println(item.toString()); }
		}
	}
	
	private boolean isValid(List<Item> finalTable) {
		
		for (Item item : finalTable) {
			
			if (item.rhs.equals(this.grammar.getStartSymbol()) && item.isFinal() && item.start == 0) {
				return true;
			}
		}
		return false;
	}
	
	private void complete(List<Item> table) {
		
		List<Item> temporalTable = new ArrayList<Item>();
				
		for (Item item : table) {
			
			if (item.isFinal()) {
			
				for (NonTerminal nt : this.grammar.getNonTerminals()) {
					
					for(Item it : this.getItemsFromTableWithNonTerminal(nt, item.start)) {
						
						if (it.next() != null && it.next().equals(item.rhs)) {
							
							temporalTable.add(it.progress());
							//this.complete(temporalTable);
						}
					}		
				}			
			}		
		}	
		
		for (Item item : temporalTable) {
			if (!table.contains(item)) {
				table.add(item);
			}
		}
	}
	
	private List<Item> scan(List<Item> table, Terminal t) {
		
		List<Item> nextTable = new ArrayList<Item>();
		
		for (Item item : table) {
			if (item.next() != null && item.next().equals(t)) {
				nextTable.add(item.progress());
			}
		}
		
		return nextTable;
	}
	
	private void predict(List<Item> table, int start) {
		
		for (int i = 0; i < table.size(); i++) {
			
			Item item = table.get(i);
			
			List<Item> candidates = new ArrayList<Item>();
			
			if (item.next() != null && (this.nullables.contains(item.next()) || item.next().equals(new Terminal("")))) {
				candidates.add(item.progress());
			}
			
			if (item.next() != null && item.next().getClass().equals(NonTerminal.class)) {		
				
				candidates.addAll(this.getItemsForNonTerminalFromGrammar((NonTerminal) item.next(), start));
				
				for (Item it : candidates) {
					
					if (!table.contains(it)) {
						table.add(it);
					}
				}	
			}
		}
	}
	
	private List<Item> getItemsFromTableWithNonTerminal(NonTerminal nt, int tableIndex) {
		
		List<Item> items = new ArrayList<Item>();
		List<Item> table = this.tables.get(tableIndex);
		
		for (Item item : table) {
			
			if (!item.isFinal() && item.next().equals(nt)) {
				items.add(item);
			}	
		}
		return items;
	}
	
	private List<Item> getItemsForNonTerminalFromGrammar(NonTerminal nt, int start) {
		
		List<Item> items = new ArrayList<Item>();
		
		Rule rule = this.grammar.getRule(nt);
		
		if (rule.getBody().getClass().equals(Alternative.class)) {
			for (Element e : rule.getBody().getContent()) {
				Item item = new Item(rule.getKey(), e, 0, start);
				items.add(item);
			}
		} else {
			Item item = new Item(rule.getKey(), rule.getBody(), 0, start);
			items.add(item);
		}
		return items;
	}
	
	private Set<NonTerminal> findNullables() {
		
		Set<NonTerminal> nullables = new HashSet<NonTerminal>();
		
		Grammar ng = this.grammar.clone();
		
		boolean foundNullable = true;
		
		while(foundNullable) {
			foundNullable = false;
			
			for (Rule rule : ng.getRuleTable()) {
				
				if (nullables.contains(rule.getKey())) { continue; }
				
				if (isNullable(rule.getBody(), nullables)) {
					nullables.add(rule.getKey());
					foundNullable = true;
				}
			}
		}
		return nullables;
	}
	
	private boolean isNullable(Element e, Set<NonTerminal> nullables) {
		
		if (e.equals(new Terminal("")) || nullables.contains(e)) {
			return true;
		} else if (e.getClass().equals(Alternative.class)) {
			
			for (Element el : e.getContent()) {
				if (isNullable(el, nullables)) {
					return true;
				}
			}
		} else if (e.getClass().equals(Sequence.class)) {
			
			for (Element el : e.getContent()) {
				if (!isNullable(el, nullables)) {
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	
	class Item {
		
		NonTerminal rhs;
		List<Element> lhs;
		int pos = 0;
		int start;
		static final char dot = (char) 9899;
		
		public Item(NonTerminal rhs, Element lhs, int pos, int start) {
			this(rhs, lhs.getContent(), pos, start);
		}
		
		public Item(NonTerminal rhs, List<Element> lhs, int pos, int start) {
			this.rhs = rhs;
			this.pos = pos;
			this.lhs = lhs;
			this.start = start;
			
			/*
			if (this.next() != null && this.next().equals(new Terminal(""))) {
				this.pos += 1;
			}
			*/
		}
		
		public Element next() {
			if (pos >= lhs.size()) {
				return null;
			} else {
				return lhs.get(pos);
			}
		}
		
		public boolean isFinal() {
			if (this.pos == lhs.size()) {
				return true;
			}
			return false;
		}
		
		public Item progress() {	
			return new Item(this.rhs, this.lhs, this.pos+1, this.start);
		}
		
		public Item clone() {
			return new Item(this.rhs, this.lhs, this.pos, this.start);
		}
		
		public String toString() {
			
			String result = start + " :: " + rhs.toString() + " --> ";
			
			for (int i = 0; i < lhs.size(); i++) {
				if (i == pos) { result += dot + " "; }
				
				String var = "";
				if(lhs.get(i).getClass().equals(Terminal.class)) {
					var += "\"" + lhs.get(i).toString() + "\"";
				} else {
					var = lhs.get(i).toString();
				}
				result += var + " ";
			}
			
			if (pos == lhs.size()) { result += dot + " "; }
			
			return result;
		}

		private UniversialParser getOuterType() {
			return UniversialParser.this;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((lhs == null) ? 0 : lhs.hashCode());
			result = prime * result + pos;
			result = prime * result + ((rhs == null) ? 0 : rhs.hashCode());
			result = prime * result + start;
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
			Item other = (Item) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (lhs == null) {
				if (other.lhs != null)
					return false;
			} else if (!lhs.equals(other.lhs))
				return false;
			if (pos != other.pos)
				return false;
			if (rhs == null) {
				if (other.rhs != null)
					return false;
			} else if (!rhs.equals(other.rhs))
				return false;
			if (start != other.start)
				return false;
			return true;
		}
	}
	
	class OverflowDangerException extends Exception {
		public OverflowDangerException() {	}
		public OverflowDangerException(String message) { super(message); }
	}
}
