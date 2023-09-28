package fuzzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import at.scch.codeanalytics.frontend.java.JavaFrontend;
import at.scch.codeanalytics.gastm.CompilationUnit;
import at.scch.codeanalytics.gastm.FunctionCallExpression;
import at.scch.codeanalytics.gastm.FunctionDefinition;
import at.scch.codeanalytics.gastm.impl.IdentifierReferenceImpl;
import at.scch.codeanalytics.gastm.impl.QualifiedOverPointerImpl;
import at.scch.codeanalytics.util.GastmSearch;

import fuzz.generator.IResultValidation;
import grammar.addons.CallTreeExtractor;
import grammar.addons.TreeNode;
import grammar.generator.TreeGenerator;
import grammar.grammar.Element;
import grammar.grammar.Grammar;
import grammar.grammar.NonTerminal;
import grammar.grammar.Rule;
import grammar.grammar.Terminal;
import learning.automata.Automata;
import learning.learner.NLstar;
import learning.teacher.GrammarBasedTeacher;
import learning.teacher.Teacher;

public class FuzzingBasedGrammarLearner {

	private IResultValidation validationFunction;
	private ITarget target;
	private String JAVA_CLASS_NAME;
	private String JAVA_METHOD_NAME;
	private String PATH;
	private Set<Terminal> alphabet;
	private Grammar grammar;
	private Terminal LAMBDA = new Terminal("");
	
	private int MQ = 0;
	private int EQ = 0;
	
	private CallTreeExtractor extractor;
	private int batchsize;
	private Random rand;
	
	public FuzzingBasedGrammarLearner(Set<Terminal> alphabet, Grammar seedGrammar, ITarget target, String JAVA_CLASS_NAME, String JAVA_METHOD_NAME, String PATH, IResultValidation validationFunction, int batchsize, Random rand) throws IOException {
		
		this.JAVA_CLASS_NAME = JAVA_CLASS_NAME;
		this.JAVA_METHOD_NAME = JAVA_METHOD_NAME;
		this.PATH = PATH;
		
		this.alphabet = alphabet;
		this.grammar = seedGrammar;
		
		this.target = target;
		this.validationFunction = validationFunction;
		
		this.batchsize = batchsize;
		this.rand = rand;
	}
	
	/*
	public void initialize(String sample) throws IOException {
		
		// TODO Fix input generator
		//RandomInputGenerator parser = new RandomInputGenerator(this.validationFunction, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH);
		//parser.setSampleAmount(1);
		//List<String> inputs = parser.generateInput();
		//while(inputs.size() > 1) {inputs.remove(1); }
		List<String> inputs = new ArrayList<String>();
		inputs.add(sample);
		
		//GrammarMiner miner = new GrammarMiner(this.JAVA_CLASS_NAME, this.JAVA_METHOD_NAME, this.PATH);
		//this.grammar = miner.mineGrammarFromInputs(inputs);
		
		//System.out.println(this.grammar);
		
	}
	*/
	
	public void setGrammar(Grammar g) {
		this.grammar = g;
	}
	
	public Grammar learn() throws Exception {
		
		Grammar learnedGrammar = new Grammar();
		learnedGrammar.setStartSymbol(this.grammar.getStartSymbol());
		
		// Get function definitions and alphabet (string, char, function call) from each
		File targetFile = new File(this.PATH + this.JAVA_CLASS_NAME + ".java");
		CompilationUnit unit = JavaFrontend.parse(targetFile);
		List<FunctionDefinition> functionDefinitions = GastmSearch.findAll(unit, FunctionDefinition.class);
		
		for (FunctionDefinition fd : functionDefinitions) {
			
			String functionName = fd.getIdentifierName().getNameString();
			
			Set<Element> alphabet = new HashSet<Element>();
			//alphabet.addAll(this.getTerminals(fd));
			alphabet.addAll(this.getNonTerminals(fd));	
			alphabet.addAll(this.alphabet);
			
			
			// Check if function name is in grammar and learn this rule if existent
			Rule rule = grammar.getRule(new NonTerminal(functionName));

			if (rule != null) {
				
				System.out.println("####################################\nLearning new Rule: " + functionName + "\n####################################");		
								
				Teacher teacher = new GrammarBasedTeacher(LAMBDA, alphabet, grammar, rule, target, validationFunction, learnedGrammar, batchsize, this.rand);
				teacher.setQueryCount(MQ, EQ);
				NLstar learner = new NLstar(new ArrayList<Element>(alphabet), teacher, LAMBDA);								// TODO change used learning algorithm here
				
				Automata M = learner.learn();
				if (M == null) { continue; }
				Grammar result = teacher.buildGrammar(M);
				
				addLearnedRule(learnedGrammar, result, alphabet, functionName);
				
				System.out.println("MQ: " + teacher.getQueryCount()[0]);
				System.out.println("EQ: " + teacher.getQueryCount()[1]);
				
				this.MQ = teacher.getQueryCount()[0];
				this.EQ = teacher.getQueryCount()[1];
					
				System.out.println("Final Grammar learned:\n----------------------\n " + learnedGrammar);		
			}
		}
		
		//System.out.println("MQ_sum: " + this.MQ);
		//System.out.println("EQ_sum: " + this.EQ);
			
		return learnedGrammar;
	}
	
	private void addLearnedRule(Grammar learnedGrammar, Grammar result, Set<Element> alphabet, String functionName) throws Exception {
		
		
		if (result.getRuleTable().size() > 1) { 
			List<NonTerminal> candidates = new ArrayList<NonTerminal>();
			for (Rule rule : result.getRuleTable()) {
				if (!rule.getKey().toString().equals(functionName)) {
					candidates.add(rule.getKey());
				}
			}
			for (NonTerminal nt : candidates) {
				NonTerminal newNT = new NonTerminal(functionName + "_" + (nt.toString()));
				for (Rule rule : result.getRuleTable()) { 
					rule.replace(nt, newNT);
				}
				result.addRule(new Rule(newNT, result.getRule(nt).getBody()));
				result.removeRule(result.getRule(nt));
			}
		}
		
		Rule learnedRule = new Rule(new NonTerminal(functionName), result.getRule(result.getStartSymbol()).getBody());
		result.removeRule(result.getRule(result.getStartSymbol()));
		
		// Change placeholder terminals back to NonTerminals
		replaceTerminals(learnedRule, alphabet);
		learnedGrammar.addRule(learnedRule);
		
		for (Rule rule : result.getRuleTable()) { replaceTerminals(rule, alphabet); learnedGrammar.addRule(rule); }
	}
	
	private void replaceTerminals(Rule rule, Set<Element> alphabet) {
		for (Element nt : alphabet) {
			if (nt.getClass().equals(NonTerminal.class)) {
				Terminal t = new Terminal(nt.toString());				
				rule.getBody().replace(t, nt);
			}
		}
	}
	
	public Set<NonTerminal> getNonTerminals(FunctionDefinition unit) {
		
		Set<NonTerminal> alphabet = new HashSet<NonTerminal>();

		List<FunctionCallExpression> fl = GastmSearch.findAll(unit, FunctionCallExpression.class);
		for (FunctionCallExpression f : fl) { 
			if (f.getCalledFunction().getClass().equals(IdentifierReferenceImpl.class)) {
				String name = ((IdentifierReferenceImpl)f.getCalledFunction()).getName().getNameString();
				
				// Check if this NonTerminal is existent in parent Grammar
				NonTerminal functionCall = new NonTerminal(name);
				if (grammar.getNonTerminals().contains(functionCall)) { alphabet.add(functionCall); }
			} else if (f.getCalledFunction().getClass().equals(QualifiedOverPointerImpl.class)) {
				
				String name = ((QualifiedOverPointerImpl)f.getCalledFunction()).getMember().getName().getNameString();
				
				// Check if this NonTerminal is existent in parent Grammar
				NonTerminal functionCall = new NonTerminal(name);
				if (grammar.getNonTerminals().contains(functionCall)) { alphabet.add(functionCall); }
			}
		}
		
		return alphabet;
	}
	
	public double testGrammar(Grammar grammar) {		
		
		TreeGenerator generator = new TreeGenerator(grammar, this.rand, 100);
		int hits = 0;
		int amount = 1000;
		for (int i = 0; i < amount; i++) { 
			String input = generator.yield().toString(); 
			
			TreeNode mutatedResult = this.execute(input);
			if (this.validationFunction.validate(mutatedResult)) {
				hits++;
			} else {
				System.out.println("Incorrectly generated input: " + input);
			}
		}
		
		//System.out.println("Tested the following grammar:\n" + grammar);		
		//GrammarCleaner cleaner = new GrammarCleaner();
		//cleaner.clean(grammar);
		//System.out.println("Tested the following grammar:\n" + grammar);
		
		double percentage = ((((double)hits)/amount));
		System.out.println("Out of " + amount + " generated inputs " + hits + " have been correct (" + percentage*100 + "%)");
		
		return percentage;
	}
	
	private TreeNode execute(String input) {
		
		TreeNode result = null;
		try {
			result = target.execute(input);
		} catch( Exception e ) {
			System.out.println("Target crashed on input " + input.toString());
		}
		
		return result;	
		
	}	
	
}
