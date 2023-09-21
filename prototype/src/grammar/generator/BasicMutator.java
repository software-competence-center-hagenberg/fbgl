package grammar.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import grammar.addons.TreeNode;
import grammar.grammar.Element;
import grammar.grammar.Terminal;

public class BasicMutator {

	Random rand;
	List<Element> alphabet;
	Terminal lambda;
	
	public BasicMutator(Random rand, Terminal lambda, Set<Element> alphabet) {
		this.rand = rand;
		this.lambda = lambda;
		this.alphabet = new ArrayList<Element>(alphabet);
	}
	
	public TreeNode mutate(TreeNode input) {
				
		int strategy = rand.nextInt(3);
		
		//System.out.println("New Mutation (" + strategy + ")");
		
		switch(strategy) {
		case 0:
			input = exchangeElement(input);
			break;
		case 1:
			input = removeElement(input);
			break;
		case 2: 
			input = addElement(input);
			break;
		}
		return input;
	}
	
	private TreeNode exchangeElement(TreeNode input) {
		TreeNode leaf = input.selectRandomLeaf(rand);
				
		Element element = this.alphabet.get(rand.nextInt(this.alphabet.size()));
		
		//System.out.println("Exchange: \"" + leaf.node + "\" --> \"" + element + "\"");
		
		leaf.node = element;		
		return input;
	}
	
	private TreeNode removeElement(TreeNode input) {
		TreeNode leaf = input.selectRandomLeaf(rand);
		leaf.node = this.lambda;
		leaf.children.clear();
		
		//System.out.println("Remove: \"" + leaf.node + "\"");
		
		return input;
	}
	
	private TreeNode addElement(TreeNode input) {
		TreeNode leaf = input.selectRandomLeaf(rand);
		
		Element element = this.alphabet.get(rand.nextInt(this.alphabet.size()));

				
		leaf.parent.addChildren(new TreeNode(element, leaf.parent, leaf.g));
		
		//System.out.println("Add: \"" + element + "\"");
		
		return input;
	}
}
