package grammar.addons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

import grammar.grammar.Element;
import grammar.grammar.Grammar;
import grammar.grammar.NonTerminal;
import grammar.grammar.Rule;
import grammar.grammar.Sequence;
import grammar.grammar.Terminal;

public class TreeNode {

	public TreeNode parent;
	public Element node;
	public List<TreeNode> children;
	public Grammar g;
	
	public TreeNode(Element node, TreeNode parent, Grammar g) {	
		this(node, parent, new ArrayList<TreeNode>(), g);		
	}
	
	public TreeNode(Element node, TreeNode parent, List<TreeNode> children, Grammar g) {
		this.parent = parent;
		this.node = node;
		this.children = children;
		this.g = g;
	}
	
	public Element getNode() {
		return node;
	}
	
	public void addChildren(TreeNode children) {
		this.children.add(children);
	}
	
	public void printTree() {
		this.printTree(0, new HashSet<Integer>());
	}
	
	public void replaceElement(Element key, Element body) {
		if (this.node.equals(key)) { this.node = body; }
		for (TreeNode child : this.children) { child.replaceElement(key, body); }
	}
	
	public TreeNode getSubNode(Element key) {
		
		if (this.node.equals(key)) {
			return this;
		}
		
		if (this.children.size() < 1) { return null; }
		
		for (TreeNode n : this.children) {
			TreeNode node = n.getSubNode(key);
			if (node != null) { return node; }
		}
		
		return null;
	}
	
	private void printTree(int rec, Set<Integer> linepos) {	
		
		for (int i = 0; i < rec-1; i++) {
			
			if (linepos.contains(i)) {
				System.out.print("|\t");
			} else {			
				System.out.print("\t");
			}
		}
		
		if (rec != 0) {System.out.print((char)9492 + "----> ");}
		System.out.print(node.toString() + "(" + node.getClass().getSimpleName() + ")\n");
		for (int i = 0; i < this.children.size(); i++) {
			
			if (i < this.children.size()-1) {
				linepos.add(rec);
			}
			
			this.children.get(i).printTree(rec+1, linepos);
			
			if (i == this.children.size()-2) {
				linepos.remove(rec);
			}
		}
	}
	
	public void remove(Element e, boolean keepChildren) {
		
		for (int i = 0; i < this.children.size(); i++) {
			TreeNode child = this.children.get(i);
			child.remove(e, keepChildren);
			
			if (child.node.equals(e)) {
				
				this.children.remove(i);
				
				if (keepChildren) {
					this.children.addAll(i, child.children);
				}		
				i--;
			} 
		}
	}
	
	private Sequence buildSequence(Sequence s) {
		
		if (this.children.isEmpty()) {
			s.add(this.node);
		} else {
			for (TreeNode child : this.children) {
				s = child.buildSequence(s);
			}
		}
		
		return s;	
	}
	
	public String buildString(String s) {
		if (this.node.getClass().equals(Terminal.class)) {
			
			if (this.node.toString().equals("\\n")) { 		// TODO EXPERIMENTAL
				s += "\n";
			} else { 		
				s += this.node.toString();
			}
		} else if (this.children.isEmpty()) {
			s += this.node.toString();
		} else {
			for (TreeNode child : this.children) {			
				if (g != null && !g.getRuleTable().isEmpty() &&
						g.getRuleTable().contains(new Rule((NonTerminal)this.node, new Terminal("DUMMY"))) &&
						g.getRuleTable().get(g.getRuleTable().indexOf(new Rule((NonTerminal)this.node, new Terminal("DUMMY")))).isSep()) {
					
					if (s.length() > 0 && s.charAt(s.length()-1) != ' ') { s += " "; }
					s = child.buildString(s);
					if (s.length() > 0 && s.charAt(s.length()-1) != ' ') { s += " "; }
				} else {
					s = child.buildString(s);
				}
			}
		}
		return s;
	}
	
	public Sequence toSequence() {
		return this.buildSequence(new Sequence());
	}
	
	public String toString() {
		return this.buildString("").trim();
	}
	
	public TreeNode selectRandomLeaf(Random rand) {
		
		if (this.children.isEmpty()) {
			return this;
		} else {
			return this.children.get(rand.nextInt(this.children.size())).selectRandomLeaf(rand);
		}
	}
	
	public TreeNode clone() {
		
		TreeNode clone = new TreeNode(this.node, this.parent, this.g);
		for (TreeNode child : this.children) {
			clone.addChildren(child.clone());
		}
		
		return clone;
	}
	
	public void setTreeGrammar(Grammar g) {
		this.g = g;
		for (TreeNode child : this.children) { child.setTreeGrammar(g); }
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((node == null) ? 0 : node.toString().toLowerCase().hashCode());
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
		TreeNode other = (TreeNode) obj;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.toString().toLowerCase().equals(other.node.toString().toLowerCase()))
			return false;
		return true;
	}
	
	
}
