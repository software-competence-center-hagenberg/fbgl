package grammar.addons;

import java.util.ArrayList;
import java.util.List;

import grammar.grammar.Grammar;
import grammar.grammar.NonTerminal;

public class CallTreeExtractor {
	
	String JAVA_CLASS_NAME;
	String JAVA_METHOD_NAME;
	String PATH;
	Grammar g;
	
	public CallTreeExtractor(String JAVA_CLASS_NAME, String JAVA_METHOD_NAME, String PATH, Grammar g) {
		
		this.JAVA_CLASS_NAME = JAVA_CLASS_NAME;
		this.JAVA_METHOD_NAME = JAVA_METHOD_NAME;
		this.PATH = PATH;
		this.g = g;
	}
	
	public TreeNode getCallTree(String input) {
		
		return null;
	}	
	
	
	public static boolean compareSyntax(TreeNode node1, TreeNode node2) {
		
		node1 = node1.clone();
		node2 = node2.clone();
		
		removeEpsilonPaths(node1);
		removeEpsilonPaths(node2);
				
		node1.children = removeTerminals(node1.children);
		node2.children = removeTerminals(node2.children);
		
		//node1.printTree();
		//node2.printTree();
		
		if (!node1.children.equals(node2.children)) { return false; }
		
		for (int i = 0; i < node1.children.size(); i++) {
			if (!compareSyntax(node1.children.get(i), node2.children.get(i))) { return false; }
		}
		
		return true;
	}
	
	private static void removeEpsilonPaths(TreeNode node) {
		
		List<TreeNode> epsilonPaths = new ArrayList<TreeNode>();
		
		for (TreeNode child : node.children) {
			if (child.toString().equals("")) { epsilonPaths.add(child); }
		}
		
		node.children.removeAll(epsilonPaths);
	}
	
	private static List<TreeNode> removeTerminals(List<TreeNode> children) {
		
		for (int i = 0; i < children.size(); i++) {
			if (!children.get(i).node.getClass().equals(NonTerminal.class)) {
				children.remove(i);
				i--;
				continue;
			}
		}
		return children;
	}
}
