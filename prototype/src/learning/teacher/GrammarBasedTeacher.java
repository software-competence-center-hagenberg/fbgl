package learning.teacher;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import grammar.addons.CallTreeExtractor;
import grammar.addons.TreeNode;
import grammar.addons.UniversialParser;
import grammar.generator.TreeGenerator;
import grammar.grammar.Element;
import grammar.grammar.Grammar;
import grammar.grammar.NonTerminal;
import grammar.grammar.Rule;
import grammar.grammar.Sequence;
import grammar.grammar.Terminal;
import learning.automata.Automata;
import fuzzer.ITarget;
import fuzz.generator.IResultValidation;

import grammar.coco.GrammarParser;
import grammar.generator.BasicMutator;

public class GrammarBasedTeacher extends Teacher {
	
	Terminal LAMBDA;
	Map<Sequence, Boolean> T = new HashMap<Sequence, Boolean>();
	Grammar parentGrammar;
	Rule parentRule;
	private ITarget target;
	private IResultValidation validationFunction;
	Set<Element> alphabet;
	BasicMutator mutator;
	TreeGenerator globalGenerator;
	public int mq = 0;
	public int eq = 0;
	
	private Grammar learnedGrammar;
	private int batchsize;
	private Random rand;
	
	public GrammarBasedTeacher(Terminal lambda, Set<Element> alphabet, Grammar parentGrammar, Rule parentRule, ITarget t, IResultValidation v, Grammar learnedGrammar, int batchsize, Random rand) {
		this.LAMBDA = lambda;
		this.alphabet = alphabet;
		this.parentGrammar = parentGrammar;
		this.parentRule = parentRule;
		
		this.target = t;
		this.validationFunction = v;
		
		this.mutator = new BasicMutator(rand, this.LAMBDA, this.alphabet);
		globalGenerator = new TreeGenerator(parentGrammar, rand, 100);
				
		this.learnedGrammar = learnedGrammar;
		this.batchsize = batchsize;
		this.rand = rand;
	}
	
	public int[] getQueryCount() {
		int[] queries = {this.mq, this.eq};
		return queries;
	}
	
	public void setQueryCount(int mq, int eq) {
		this.mq = mq;
		this.eq = eq;	
	}

	/**
	 * Performs a query to check if a given rule A --> B is valid.
	 * 
	 * 1) Check already observed elements B
	 * 2) A valid input i is derived from the global grammar.
	 * 3) Check if a rule A --> X is present in the input syntax tree, if not go to 1.
	 * 4) Substitute X with B so that the rule A --> B is embedded in the syntax tree of input i
	 * 5) Resolve open non-terminals in input i using the global grammar
	 * 6) Execute input i
	 * 7) if the input is valid, check if the input syntax is in line with the call tree of the execution
	 * 
	 * returns true if the input is accepted by the parser and the syntax is correct
	 */
	public boolean membershipQuery(Element e) {
				
		// 1
		Sequence s = new Sequence();
		if (e.getContent().size() > 1) {
			for (Element t : e.getContent()) {
				if (!t.equals(LAMBDA)) {s.add(t);}
			}
		} else {
			s.add(e);
		}
		
		if (s.isEmpty()) {s.add(new Terminal(""));}
				
		if (this.T.containsKey(s)) { return this.T.get(s); }
		
		/*
		if (s.toString().contains("Member , Member , Member")) {
			System.out.println("here");
		}
		*/
		
		//System.out.print(".");
		//System.out.println(s);
		
		if (s.isEmpty()) {
			TreeNode res = this.execute("");
			boolean acc = this.validationFunction.validate(res);
			this.T.put(s, acc);
			return acc;
		}
		
		this.mq++;
		
		TreeNode inputNode = null;
		TreeNode ruleNode = null;
		
		// 2 & 3
		do {
			inputNode = globalGenerator.yield();
			ruleNode = inputNode.getSubNode(this.parentRule.getKey());
		} while (ruleNode == null);
		
		// 4
		ruleNode.children = new ArrayList<TreeNode>();
		
		Grammar grammar = parentGrammar.clone();
		grammar.removeRule(parentRule);
		grammar.addRule(new Rule(parentRule.getKey(), s));
				
		TreeGenerator generator = new TreeGenerator(grammar, this.rand, 100);
		List<TreeNode> unexpanded = generator.expandSingleNode(ruleNode);
		
		// 5
		for (TreeNode child : unexpanded) { globalGenerator.fullExpandNode(child); }
			
		// 6
		//inputNode.printTree();
		String input = inputNode.toString();
		
		//System.out.println(input);
		
		TreeNode result = this.execute(input);
		

		boolean accepted = this.validationFunction.validate(result);
		

		// 7
		if (accepted) {
			//TreeNode calltree = extractor.getCallTree(input);
			//result.printTree();
			//inputNode.printTree();
			accepted = CallTreeExtractor.compareSyntax(inputNode, result);
		}
		
		if (accepted) {
			System.out.print("");
		} else {
			System.out.print("");
		}
				
		this.T.put(s, accepted);
		
		return accepted;
	}
	
	private boolean hasAlreadySeen(Element e) {
		
		Sequence s = new Sequence();
		if (e.getContent().size() > 1) {
			for (Element t : e.getContent()) {
				if (!t.equals(LAMBDA)) {s.add(t);}
			}
		} else {
			s.add(e);
		}
				
		return this.T.containsKey(s);
	}
	
	public Sequence conjecture(Automata M) throws Exception {
		
		//this.printUpdate(M);
		
		this.eq++;
		
		Grammar ruleGrammar = buildGrammar(M);
		//System.out.println(ruleGrammar);
		
		// If the learned automata does not have final states, we cannot produce valid inputs. 
		// To provide a counterexample we have to simply find one valid input.
		// We can take this valid input from the one provided by the parent Grammar.
		if (ruleGrammar == null) { 
			TreeNode node = new TreeNode(this.parentRule.getKey(), null, parentGrammar);
			globalGenerator.expandSingleNode(node);
			return node.toSequence();
		}
		
		//System.out.println(ruleGrammar.toString());
		
		
		// It is important that rules of the form A -> A B | B are not able to self expand themselves if they are in the original alphabet as this 
		// confuses parseTree equivalence queries. So we temporarily replace them
		// This also applies to rules like 
		Grammar mutationGrammar = ruleGrammar.clone();
		for (Rule r : ruleGrammar.getRuleTable()) {
			for (Element e : this.alphabet) {
				if (e.getClass().equals(NonTerminal.class)) {
					r.replace(e, new NonTerminal(e.toString() + "_temporary_replacement"));					
				}	
			}
		}
		
		
		TreeGenerator generator = new TreeGenerator(ruleGrammar, this.rand, 100);
		
		// Test some random walks through the rule
		for (int i = 0; i < this.batchsize; i++) {
			
			//System.out.print(".");
			//if (i%100==0) {System.out.print("\n");}
			
			TreeNode ruleNode = null;
			ruleNode = generator.yield();
			
			// Change replacements back in node.
				for (Element e : this.alphabet) {
					if (e.getClass().equals(NonTerminal.class)) {
						ruleNode.replaceElement(new NonTerminal(e.toString() + "_temporary_replacement"), e);					
					}	
				}
				//ruleNode.replaceElement(new NonTerminal(r.getKey().toString() + "_temporary_replacement"), r.getKey());
						
			Sequence ruleBody = ruleNode.toSequence();
			//if (this.hasAlreadySeen(ruleBody)) { continue; }
			
			if (!this.membershipQuery(ruleBody)) {
				//if (ruleBody.size() <= 4) {
					return ruleBody;
				//}
			}
			
			// Proceed to test some mutations
			for (int j = 0; j < 10; j++) {
				ruleNode = mutator.mutate(ruleNode);
				
				// Test if mutation can be produced by the original rule, if yes discard mutation
				// TODO Current version is EXPERIMENTAL!
				// no idea if this grammar extension works for all cases
				Grammar parserGrammar = mutationGrammar.clone();
				for (NonTerminal nt : mutationGrammar.getNonTerminals()) {
					mutationGrammar.addRule(new Rule(nt, new Terminal(nt.toString())));
				}
				
				UniversialParser parser = new UniversialParser(mutationGrammar);
				
				String line = ruleNode.toString();
				
				if (parser.parse(line)) { continue; }
				
				ruleBody = ruleNode.toSequence();
				
				//if (this.hasAlreadySeen(ruleBody)) { continue; }
				
				if (this.membershipQuery(ruleBody)) {
					//if (ruleBody.size() <= 4) {
						return ruleBody;
					//}
				}
			}
		}
		System.out.print("\n");
		return null;
	}
	
	public Grammar buildGrammar(Automata M) throws Exception {
				
		if (M.finalStates.isEmpty()) { return null; }
		
		Grammar partialGrammar = M.toGrammar();	
				
		partialGrammar.removeRulesWithSingleAssignments();
		for (Rule rule : partialGrammar.getRuleTable()) { 
			rule.replace(partialGrammar.getStartSymbol(), parentRule.getKey()); 
		}	
		Element body = partialGrammar.getRule(partialGrammar.getStartSymbol()).getBody();
		partialGrammar.removeRule(partialGrammar.getRule(partialGrammar.getStartSymbol()));
		partialGrammar.addRule(new Rule(parentRule.getKey(), body));
		partialGrammar.setStartSymbol(parentRule.getKey());
		
		/*
		for (Element nt : this.alphabet) {
			if (nt.getClass().equals(NonTerminal.class)) {
				for (Rule rule : partialGrammar.getRuleTable()) { rule.replace(nt, new Terminal(nt.toString())); }
			}
		}
		
		partialGrammar.removeNonTerminatingPaths();
		*/
		//System.out.println(partialGrammar);
		
		return partialGrammar;
	}
	
	private TreeNode execute(String input) {
		
		//System.out.println("\"" + input + "\"");
		
		TreeNode result = null;
		try {
			result = target.execute(input);
		} catch( Exception e ) {
			System.out.println("Target crashed on input " + input.toString());
		}
		
		return result;	
	}	
	
	public void printUpdate(Automata M) throws Exception {
		
		Grammar seed = this.parentGrammar.clone();
		Grammar currentRule = this.buildGrammar(M);
		Grammar learned = this.learnedGrammar.clone();
		
		for (Rule r : seed.getRuleTable()) {
			if (learned.getRule(r.getKey()) == null) { learned.addRule(r); }
		}
		if (currentRule != null) {
			learned.removeRule(new Rule(currentRule.getStartSymbol(), new Terminal("DUMMY")));
			for (Rule r : currentRule.getRuleTable()) {
				learned.addRule(r);
			}
		}
		
		System.out.println("######### Status Update: #############");
		System.out.println(learned);
		System.out.println("MQ: " + this.mq);
		System.out.println("EQ: " + this.eq);	
		
		testGrammars(learned);
		System.out.println("#########   END  Update: #############");

	}
	
	private void testGrammars(Grammar learned) throws MalformedURLException, IOException {
		
		//ICompiler compiler = CompilerGenerator.genCompiler(null)
		
		Grammar target = GrammarParser.parse(new File("src/compilers/url/url.atg"));
		
		UniversialParser learned_parser = new UniversialParser(learned);
		TreeGenerator learned_gen = new TreeGenerator(learned, new Random(42), 100);
		TreeGenerator target_gen = new TreeGenerator(target, new Random(42), 100);

		int amount = 1000;
		
		int tp = 0;
		int fp = 0;
		
		for (int i = 0; i < amount; i++) {
			
			TreeNode input = learned_gen.yield();
			TreeNode result = this.target.execute(input.toString());
			
			if (this.validationFunction.validate(result)) { tp++; } else {fp++;}
			
		}
		
		System.out.println("Precision: " + ((double)tp/amount));
		
		
		tp = 0;
		int fn = 0;
		
		for (int i = 0; i < amount; i++) {
			
			TreeNode input = target_gen.yield();			
			if (learned_parser.parse(input.toString().replace(" ", ""))) { 
				tp++; //System.out.println("TRUE: " + input.toString().replace(" ", ""));
			} else {
				fn++; //System.out.println("FALSE: " + input.toString().replace(" ", ""));
			}
			
		}
		
		System.out.println("Recall: " + ((double)tp/amount));

	}
 }
