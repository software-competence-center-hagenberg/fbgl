package grammar.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import grammar.addons.CostCalculator;
import grammar.addons.TreeNode;
import grammar.grammar.Alternative;
import grammar.grammar.Element;
import grammar.grammar.Grammar;
import grammar.grammar.NonTerminal;
import grammar.grammar.Rule;
import grammar.grammar.Sequence;
import grammar.grammar.Terminal;

public class TreeGenerator extends Generator{

	Grammar g;
	Random rand;
	int MAX_NODES = 100;
	int currentNodes = 0;
	public Map<Element, Integer> costs;
	int optimizationLevel = 0;
	
	public TreeGenerator(Grammar g, Random rand, int maxNodes) {
		this(g, rand, maxNodes, 0);
	}
	
	public TreeGenerator(Grammar g, Random rand, int maxNodes, int optimizationLevel) {
		
		this.g = g;
		this.rand = rand;
		this.MAX_NODES = maxNodes;
		costs = new CostCalculator().calculateCosts(g);
		this.optimizationLevel = optimizationLevel;
		
	}
	
	public TreeNode yield() {
		TreeNode root = new TreeNode(g.getStartSymbol(), null, this.g);
		currentNodes = 0;
		this.expandRandomThenRecursive(root);
		
		return root;
	}
	
	private void expandRandomThenRecursive(TreeNode root) {
		
		//long startTime = System.nanoTime();		
		
		List<TreeNode> expandables = new ArrayList<TreeNode>();
		expandables.add(root);
				
		while (!expandables.isEmpty() && expandables.size() <= MAX_NODES) {

			int random = rand.nextInt(expandables.size());
			TreeNode candidate = expandables.get(random);
			expandables.remove(random);
			expandables.addAll(expandSingleNode(candidate));
		} 
		
		
		//long stopTime = System.nanoTime();
		//System.out.println("Random time: " + (double)(stopTime-startTime)/1000000000);
		//System.out.println(expandables.size() + "Nodes left");
		//startTime = System.nanoTime();	
					
		for (TreeNode t : expandables) {
			fullExpandNode(t);
		}
		
		//stopTime = System.nanoTime();
		//System.out.println("Expand time: " + (double)(stopTime-startTime)/1000000000);
		
		
	}
	
	public List<TreeNode> expandSingleNode(TreeNode parent) {
		
		//System.out.println("------------\nExpanding: " + parent.node);
		
		//long startTime = System.nanoTime();		
		
		currentNodes++;
		
		if (parent.getNode() == null) {return new ArrayList<TreeNode>();}
		if (parent.getNode().getClass().equals(Terminal.class)) { return new ArrayList<TreeNode>(); }
		
		NonTerminal nt = (NonTerminal) parent.getNode();
		
		/*
		long stopTime = System.nanoTime();
		System.out.println("Step 1: " + (double)(stopTime-startTime)/1000000000);
		startTime = System.nanoTime();		
		*/
		Element sub = null;		
		if (g.getRuleTable().contains(new Rule(nt, new Terminal("DUMMY")))) {
			sub = g.getRuleTable().get(g.getRuleTable().indexOf(new Rule(nt, new Terminal("DUMMY")))).getBody();
		} else {
			return new ArrayList<TreeNode>();
		}
		/*
		stopTime = System.nanoTime();
		System.out.println("Step 2: " + (double)(stopTime-startTime)/1000000000);
		startTime = System.nanoTime();		
		
		long startTime2 = System.nanoTime();	
		*/
		List<Element> children = resolve(sub);
		/*
		long stopTime2 = System.nanoTime();
		System.out.println("\tResolve: " + (double)(stopTime2-startTime2)/1000000000);
		startTime2 = System.nanoTime();	
		*/
		for (Element child : children) {
			TreeNode node = new TreeNode(child, parent, this.g);
			parent.addChildren(node);
		}
		/*
		stopTime2 = System.nanoTime();
		System.out.println("\tAdding: " + (double)(stopTime2-startTime2)/1000000000);
		
		stopTime = System.nanoTime();
		System.out.println("Step 3: " + (double)(stopTime-startTime)/1000000000);
		*/
		return parent.children;
	}
	
	public void fullExpandNode(TreeNode parent) {
		
		currentNodes++;
		
		if (parent.getNode() == null) {return;}
		if (parent.getNode().getClass().equals(Terminal.class)) {return;}
		
		NonTerminal nt = (NonTerminal) parent.getNode();
		
		if (g.getRuleTable().contains(new Rule(nt, new Terminal("DUMMY")))) {
			Element sub = g.getRuleTable().get(g.getRuleTable().indexOf(new Rule(nt, new Terminal("DUMMY")))).getBody();
		
			List<Element> children = resolve(sub);
			for (Element child : children) {
				TreeNode node = new TreeNode(child, parent, this.g);
				parent.addChildren(node);
				fullExpandNode(node);
			}
		}
	}
	
	public TreeNode getUnexpandedNode(TreeNode parent) {
				
		if (parent.getNode() == null) {return null;}
		if (parent.getNode().getClass().equals(Terminal.class)) {return null;}
		
		if (parent.children.isEmpty()) { return parent; }
		else {
			for (TreeNode child : parent.children) {
				TreeNode unexpanded = getUnexpandedNode(child);
				if (unexpanded != null) { return unexpanded; }
			}
		}		
		return null;
	}
	
	
	private List<Element> resolve(Element e) {
		
		List<Element> candidates = new ArrayList<Element>();
		
		if (e.getClass().equals(NonTerminal.class) || e.getClass().equals(Terminal.class)) {
			candidates.add(e);
			return candidates;
		}
		else if (e.getClass().equals(Sequence.class)) {
			return this.resolveSequence(e);
		}
		else if (e.getClass().equals(Alternative.class)) {
			switch(this.optimizationLevel) {
			case 0:
				return this.resolveAlternative(e);
			case 1:
				return this.optimizedResolveAlternative2(e);
			case 2:
				return this.optimizedResolveAlternative(e);
			case 3:
				return this.fastResolveAlternative(e);
			}
			return this.resolveAlternative(e);
		}
		else {
			return candidates;
		}
	}
	
	private List<Element> resolveSequence(Element e) {
		List<Element> candidates = new ArrayList<Element>();
		for (Element el : e.getContent()) {
			candidates.addAll(resolve(el));
		}
		return candidates;
	}
	
	private List<Element> fastResolveAlternative(Element e) {
		
		List<Element> candidates = new ArrayList<Element>();
		
		
		Element el = null;
		if (currentNodes < MAX_NODES) {
			
			el = e.getContent().get(rand.nextInt(e.getContent().size()));
			
		} else {
			
			List<Element> sameCost = new ArrayList<Element>();
			
			int minCost = Integer.MAX_VALUE-1;
			for (Element ele : e.getContent()) {
				if (this.costs.containsKey(ele) && this.costs.get(ele) == minCost) {
					sameCost.add(ele);
				} else if (this.costs.containsKey(ele) && this.costs.get(ele) < minCost) {
					sameCost.clear();
					sameCost.add(ele);
					minCost = this.costs.get(ele);
				}
			}
			if (sameCost.size() < 1) { el = e.getContent().get(rand.nextInt(e.getContent().size())); }
			else { el = sameCost.get(rand.nextInt(sameCost.size())); }
		}
		
		candidates.addAll(resolve(el));
		
		return candidates;
		
	}
	
	private List<Element> optimizedResolveAlternative2(Element e) {
		

		Map<Integer, List<Element>> groupedCosts = new HashMap<Integer, List<Element>>();
				
		for (Element el : e.getContent()) {
			
			int cost = costs.get(el);
			
			if (groupedCosts.containsKey(cost)) {
				groupedCosts.get(cost).add(el);
			} else {
				List<Element> group = new ArrayList<Element>();
				group.add(el);
				groupedCosts.put(cost, group);
			}
		}
		
		
		int costsum = 0;
		for (int i : groupedCosts.keySet()) {
			costsum += i; 
		}
		
		Map<Integer, List<Element>> groupMapping = new HashMap<Integer, List<Element>>();
		
		double factor = 1;
		// Calculate adjusted values
		List<Double> adjustedValues = new ArrayList<Double>();
		double avg = costsum/groupedCosts.size();
		int i = 0;
		for (int key : groupedCosts.keySet()) {
			
			groupMapping.put(i, groupedCosts.get(key));
			i++;
			
			double cost = key;
			double diff = cost-avg;	
			if (diff < 0) {					
				adjustedValues.add(cost/(((diff-1)*(-1))*factor));
			} else {
				adjustedValues.add(cost*(((diff+1))*factor));
			}	
		}
		
		// Calculate probabilities and their sum
		// This is awfully slow -->
		List<Double> probs = new ArrayList<Double>();
					
		double x = (((double)currentNodes)/MAX_NODES);
		double cx = costsum * x;
		double x1 = 1-x;
		
		double probSum = 0;
		for (i = 0; i < groupedCosts.size(); i++) {
			
			double val = adjustedValues.get(i);
			
			// Original Calculation: double prob = val+((double)costsum/val-val)*(((double)currentNodes)/MAX_NODES);
			double prob = val*x1 + cx/val;
			probs.add(prob);
			probSum += prob;
		}
		// --> end slow part
		
		// normalize probabilities
		for (i = 0; i < probs.size(); i++) {
			probs.set(i, probs.get(i)/probSum);
		}
		
		// Select element
		double diceroll = rand.nextDouble();
		
		List<Element> selectedGroup = null;
		i = 0;
		for (i = 0; i < probs.size(); i++) {
			if (diceroll <= probs.get(i)) {
				selectedGroup = groupMapping.get(i);
				break;
			} else {
				diceroll -= probs.get(i);
			}
		}
		
		Element selectedElement = selectedGroup.get(rand.nextInt(selectedGroup.size()));
		List<Element> candidates = new ArrayList<Element>();
		candidates.addAll(resolve(selectedElement));
		return candidates;
	}
	
	private List<Element> optimizedResolveAlternative(Element e) {
		
		List<List<Element>> groups = new ArrayList<List<Element>>();
		
		for (int i = 0; i < 5; i++) {
			groups.add(new ArrayList<Element>());
		}

		for (Element el : e.getContent()) {
			
			int costs = Integer.MAX_VALUE;
			
			if (this.costs.containsKey(el)) {
				costs = this.costs.get(el);
			} else {
				System.out.println("Unable to expand \"" + e + "\" - No cost found for \"" + el + "\"");
			}
			
			if (costs < 2) {
				groups.get(0).add(el);
			} else if (costs <= 5) {
				groups.get(1).add(el);
			} else if (costs <= 10) {
				groups.get(2).add(el);
			} else if (costs == Integer.MAX_VALUE) {
				groups.get(4).add(el);
			} else {
				groups.get(3).add(el);
			}
		}
		
		List<Double> groupProbs = new ArrayList<Double>();
		
		double factor = (double)currentNodes+1/MAX_NODES*10;
		factor = factor*factor;
		
		double probsum = 0;
		double val = 0.0;
		for (int i = 0; i < 5; i++) {
			val += 0.2;
			double prob = 1/(factor*val+1/(factor*val));
			groupProbs.add(i, prob);
			probsum += prob;
		}
		
		for (int i = 0; i < groups.size(); i++) {
			 if (groups.get(i).isEmpty()) {
				 groups.remove(i);
				 probsum -= groupProbs.get(i);
				 groupProbs.remove(i);
				 i--;
			 }
		}
		
		// Normalize group probs
		for (int i = 0; i < groupProbs.size(); i++) {
			groupProbs.set(i, groupProbs.get(i)/probsum);
		}
		
		// Select Group and Element
		List<Element> selectedGroup = selectGroup(groups, groupProbs);
		Element selectedElement = selectedGroup.get(rand.nextInt(selectedGroup.size()));
		
		List<Element> candidates = new ArrayList<Element>();
		candidates.addAll(resolve(selectedElement));
		return candidates;
	}
	
	private List<Element> selectGroup(List<List<Element>> groups, List<Double> probs) {
		
		double diceroll = rand.nextDouble();
		
		int i = 0;
		for (i = 0; i < probs.size(); i++) {
			if (diceroll <= probs.get(i)) {
				break;
			} else {
				diceroll -= probs.get(i);
			}
		}
		
		return groups.get(i);
	}
	
	private List<Element> resolveAlternative(Element e) {
		
		List<Element> candidates = new ArrayList<Element>();
		
		double factor = 1; //TODO adjust / find optimum
		
		Element el = null;
		if (currentNodes < MAX_NODES) {
			
			// Direct generated total length to follow MAX_Nodes size
			
			// Calculate the sum of all costs
			int costsum = 0;
			for (Element ele : e.getContent()) {
				try {
					costsum += this.costs.get(ele); 
				} catch(NullPointerException exception) {
					costsum += 1;
				}
			}
			
			// Calculate adjusted values
			List<Double> adjustedValues = new ArrayList<Double>();
			double avg = costsum/e.getContent().size();
			for (Element ele : e.getContent()) {
				double cost = (double) this.costs.get(ele);
				double diff = cost-avg;	
				if (diff < 0) {					
					adjustedValues.add(cost/(((diff-1)*(-1))*factor));
				} else {
					adjustedValues.add(cost*(((diff+1))*factor));
				}	
			}
			
			// Calculate probabilities and their sum
			// This is awfully slow -->
			List<Double> probs = new ArrayList<Double>();
						
			double x = (((double)currentNodes)/MAX_NODES);
			double cx = costsum * x;
			double x1 = 1-x;
			
			double probSum = 0;
			for (int i = 0; i < e.getContent().size(); i++) {
				
				double val = adjustedValues.get(i);
				
				// Original Calculation: double prob = val+((double)costsum/val-val)*(((double)currentNodes)/MAX_NODES);
				double prob = val*x1 + cx/val;
				probs.add(prob);
				probSum += prob;
			}
			// --> end slow part
			
			// normalize probabilities
			for (int i = 0; i < probs.size(); i++) {
				probs.set(i, probs.get(i)/probSum);
			}
			
			// Select element
			double diceroll = rand.nextDouble();
			
			int i = 0;
			for (i = 0; i < probs.size(); i++) {
				if (diceroll <= probs.get(i)) {
					el = e.getContent().get(i);
					break;
				} else {
					diceroll -= probs.get(i);
				}
			}
			
		} else {
			
			List<Element> sameCost = new ArrayList<Element>();
			
			int minCost = Integer.MAX_VALUE-1;
			for (Element ele : e.getContent()) {
				if (this.costs.containsKey(ele) && this.costs.get(ele) == minCost) {
					sameCost.add(ele);
				} else if (this.costs.containsKey(ele) && this.costs.get(ele) < minCost) {
					sameCost.clear();
					sameCost.add(ele);
					minCost = this.costs.get(ele);
				}
			}
			if (sameCost.size() < 1) { el = e.getContent().get(rand.nextInt(e.getContent().size())); }
			else { el = sameCost.get(rand.nextInt(sameCost.size())); }
		}
		
		candidates.addAll(resolve(el));
		
		return candidates;
	}
}
