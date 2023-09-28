package prototype.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;

import grammar.addons.TreeNode;
import grammar.addons.UniversialParser;
import grammar.coco.GrammarParser;
import grammar.generator.TreeGenerator;
import grammar.grammar.Grammar;
import grammar.grammar.Terminal;
import compiler.coco.CompilerGenerator;
import compilers.antlr.advexpr.ANTLRAdvExprParser;
import compilers.antlr.expr.ANTLRExprParser;
import compilers.antlr.hello.ANTLRHelloParser;
import compilers.antlr.json.ANTLRJsonParser;
import compilers.antlr.mail.ANTLRMailParser;
import compilers.antlr.url.ANTLRUrlParser;
import compilers.coco.advexpr.AdvExprParser;
import compilers.coco.expr.ExprParser;
import compilers.coco.hello.HelloParser;
import compilers.coco.json.JsonParser;
import compilers.coco.mail.MailParser;
import compilers.coco.url.UrlParser;
import at.scch.codeanalytics.frontend.java.JavaFrontend;
import fuzzer.FuzzingBasedGrammarLearner;
import fuzzer.ITarget;
import fuzz.generator.IResultValidation;
import at.scch.codeanalytics.gastm.CompilationUnit;

public class FuzzingBasedGrammarLearnerTest {
	
	
	
	@Test
	public void genCompiler() throws IOException {
		
		try {

		CompilerGenerator.genCompiler(new File("/compile/generator/compilers/url/url.atg"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Test
	public void testWithExpryParser() throws Exception {
		
		String JAVA_CLASS_NAME = "Parser";
		String JAVA_METHOD_NAME = "parse";
		String PATH = "src/compilers/coco/expr/";
		
		Grammar targetGrammar = GrammarParser.parse(new File("src/compilers/coco/expr/ExprGen.atg"));
		
		Set<Terminal> alphabet = targetGrammar.getTerminals();
		
		final IResultValidation v = new IResultValidation() {

			@Override
			public boolean validate(TreeNode result) {
				if (result == null) { return false; }
				return true;
			}
		};

		final ITarget t = new ITarget() {

			@Override
			public TreeNode execute(String input) {
				return ExprParser.parse(input);
			}
		};
		Grammar seed = GrammarParser.parse(new File("src/compilers/coco/expr/ExprGenSeed.atg"));
		FuzzingBasedGrammarLearner fuzzer = new FuzzingBasedGrammarLearner(alphabet, seed, t, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH, v, 1000, new Random(42));
		
		try {		
			
			long startTime = System.nanoTime();
			Grammar learnedGrammar = fuzzer.learn();		
			System.out.println("Time elapsed: " + ((System.nanoTime()-startTime))/1000000000 + "s");
			
			
			testGrammars(learnedGrammar, targetGrammar, t, v);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWithMailParser() throws Exception {
		
		try {
		
		String JAVA_CLASS_NAME = "Parser";
		String JAVA_METHOD_NAME = "Parse";
		
		String PATH = "src/compilers/coco/mail/";
		Grammar targetGrammar = GrammarParser.parse(new File("src/compilers/coco/mail/Mail.atg"));

		
		Set<Terminal> alphabet = targetGrammar.getTerminals();

		
		System.out.println("Initializing analysis framework and parsing alphabet ...");
		
		File targetFile = new File(PATH + JAVA_CLASS_NAME + ".java");
		CompilationUnit unit = JavaFrontend.parse(targetFile);
		
		final IResultValidation v = new IResultValidation() {

			@Override
			public boolean validate(TreeNode result) {
				if (result == null) { return false; }
				return true;
			}
		};

		final ITarget t = new ITarget() {

			@Override
			public TreeNode execute(String input) {
				return MailParser.parse(input);
			}
		};
		
		System.out.println("Initializing fuzzer and learning components ...");
		Grammar seed = GrammarParser.parse(new File("src/compilers/coco/mail/MailSeed.atg"));
		FuzzingBasedGrammarLearner fuzzer = new FuzzingBasedGrammarLearner(alphabet, seed, t, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH, v, 1000, new Random(42));
		
		System.out.println("Everything set up - starting to learn ...");
		long startTime = System.nanoTime();
		Grammar learnedGrammar = fuzzer.learn();		
		System.out.println("Time elapsed: " + ((System.nanoTime()-startTime))/1000000000 + "s");
		testGrammars(learnedGrammar, targetGrammar, t, v);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWithHelloParser() throws Exception {
		
		try {
		
		String JAVA_CLASS_NAME = "Parser";
		String JAVA_METHOD_NAME = "parse";
		String PATH = "src/compilers/coco/hello/";
		
		Grammar targetGrammar = GrammarParser.parse(new File("src/compilers/coco/hello/Hello.atg"));
		
		Set<Terminal> alphabet = targetGrammar.getTerminals();
		
		final IResultValidation v = new IResultValidation() {

			@Override
			public boolean validate(TreeNode result) {
				if (result == null) { return false; }
				return true;
			}
		};

		final ITarget t = new ITarget() {

			@Override
			public TreeNode execute(String input) {
				return HelloParser.parse(input);
			}
		};
		Grammar seed = GrammarParser.parse(new File("src/compilers/coco/hello/HelloSeed.atg"));
		FuzzingBasedGrammarLearner fuzzer = new FuzzingBasedGrammarLearner(alphabet, seed, t, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH, v, 1000, new Random(42));		
		
		long startTime = System.nanoTime();
		Grammar learnedGrammar = fuzzer.learn();		
		System.out.println("Time elapsed: " + ((System.nanoTime()-startTime))/1000000000 + "s");
		testGrammars(learnedGrammar, targetGrammar, t, v);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWithAdvExprParser() throws Exception {		
 		
		try {
			
			String JAVA_CLASS_NAME = "Parser";
			String JAVA_METHOD_NAME = "Parse";
			
			String PATH = "src/compilers/coco/advexpr/";
			Grammar targetGrammar = GrammarParser.parse(new File("src/compilers/coco/advexpr/advexpr.atg"));

			
			Set<Terminal> alphabet = targetGrammar.getTerminals();

			
			System.out.println("Initializing analysis framework and parsing alphabet ...");
			
			File targetFile = new File(PATH + JAVA_CLASS_NAME + ".java");
			CompilationUnit unit = JavaFrontend.parse(targetFile);
			
			final IResultValidation v = new IResultValidation() {

				@Override
				public boolean validate(TreeNode result) {
					if (result == null) { return false; }
					return true;
				}
			};

			final ITarget t = new ITarget() {

				@Override
				public TreeNode execute(String input) {
					return AdvExprParser.parse(input);
				}
			};
			
			System.out.println("Initializing fuzzer and learning components ...");
			Grammar seed = GrammarParser.parse(new File("src/compilers/coco/advexpr/advexprSeed.atg"));
			FuzzingBasedGrammarLearner fuzzer = new FuzzingBasedGrammarLearner(alphabet, seed, t, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH, v, 1000, new Random(42));
			
			System.out.println("Everything set up - starting to learn ...");
			long startTime = System.nanoTime();
			Grammar learnedGrammar = fuzzer.learn();		
			System.out.println("Time elapsed: " + ((System.nanoTime()-startTime))/1000000000 + "s");
			testGrammars(learnedGrammar, targetGrammar, t, v);

			} catch (Exception e) {
				e.printStackTrace();
			}
		

		
	}
	
	@Test
	public void testWithJsonParser() throws Exception {		
 		
		try {
			
			String JAVA_CLASS_NAME = "Parser";
			String JAVA_METHOD_NAME = "Parse";
			
			String PATH = "src/compilers/coco/json/";
			Grammar targetGrammar = GrammarParser.parse(new File("src/compilers/coco/json/json.atg"));

			
			Set<Terminal> alphabet = targetGrammar.getTerminals();

			
			System.out.println("Initializing analysis framework and parsing alphabet ...");
			
			File targetFile = new File(PATH + JAVA_CLASS_NAME + ".java");
			CompilationUnit unit = JavaFrontend.parse(targetFile);
			
			final IResultValidation v = new IResultValidation() {

				@Override
				public boolean validate(TreeNode result) {
					if (result == null) { return false; }
					return true;
				}
			};

			final ITarget t = new ITarget() {

				@Override
				public TreeNode execute(String input) {
					return JsonParser.parse(input);
				}
			};
			
			System.out.println("Initializing fuzzer and learning components ...");
			Grammar seed = GrammarParser.parse(new File("src/compilers/coco/json/JsonSeed.atg"));
			FuzzingBasedGrammarLearner fuzzer = new FuzzingBasedGrammarLearner(alphabet, seed, t, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH, v, 10000, new Random(42));
			
			System.out.println("Everything set up - starting to learn ...");
			long startTime = System.nanoTime();
			Grammar learnedGrammar = fuzzer.learn();		
			System.out.println("Time elapsed: " + ((System.nanoTime()-startTime))/1000000000 + "s");
			
			
			testGrammars(learnedGrammar, targetGrammar, t, v);

			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	@Test
	public void testWithURLParser() throws Exception {
		
		try {
			
			String JAVA_CLASS_NAME = "Parser";
			String JAVA_METHOD_NAME = "Parse";
			
			String PATH = "src/compilers/coco/url/";
			Grammar targetGrammar = GrammarParser.parse(new File("src/compilers/coco/url/url.atg"));

			
			Set<Terminal> alphabet = targetGrammar.getTerminals();

			
			System.out.println("Initializing analysis framework and parsing alphabet ...");
			
			File targetFile = new File(PATH + JAVA_CLASS_NAME + ".java");
			CompilationUnit unit = JavaFrontend.parse(targetFile);
			
			final IResultValidation v = new IResultValidation() {

				@Override
				public boolean validate(TreeNode result) {
					if (result == null) { return false; }
					return true;
				}
			};

			final ITarget t = new ITarget() {

				@Override
				public TreeNode execute(String input) {
					return UrlParser.parse(input);
				}
			};
			
			System.out.println("Initializing fuzzer and learning components ...");
			Grammar seed = GrammarParser.parse(new File("src/compilers/coco/url/urlseed.atg"));
			FuzzingBasedGrammarLearner fuzzer = new FuzzingBasedGrammarLearner(alphabet, seed, t, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH, v, 10000, new Random(42));
			
			System.out.println("Everything set up - starting to learn ...");
			long startTime = System.nanoTime();
			Grammar learnedGrammar = fuzzer.learn();		
			System.out.println("Time elapsed: " + ((System.nanoTime()-startTime))/1000000000 + "s");
			testGrammars(learnedGrammar, targetGrammar, t, v);

			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	@Test
	public void testWithANTLRExprParser() throws Exception {
		String JAVA_CLASS_NAME = "ExprParser";
		String JAVA_METHOD_NAME = "parse";
		String PATH = "src/compilers/antlr/expr/";
		
		Grammar targetGrammar = GrammarParser.parse(new File("src/compilers/antlr/expr/ExprGen.atg"));
		
		Set<Terminal> alphabet = targetGrammar.getTerminals();
		
		final IResultValidation v = new IResultValidation() {

			@Override
			public boolean validate(TreeNode result) {
				if (result == null) { return false; }
				return true;
			}
		};

		final ITarget t = new ITarget() {

			@Override
			public TreeNode execute(String input) {
				return ANTLRExprParser.parse(input);
			}
		};
		Grammar seed = GrammarParser.parse(new File("src/compilers/antlr/expr/ExprGenSeed.atg"));
		FuzzingBasedGrammarLearner fuzzer = new FuzzingBasedGrammarLearner(alphabet, seed, t, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH, v, 1000, new Random(21));
		
		try {		
			
			long startTime = System.nanoTime();
			Grammar learnedGrammar = fuzzer.learn();		
			System.out.println("Time elapsed: " + ((System.nanoTime()-startTime))/1000000000 + "s");
			
			
			testGrammars(learnedGrammar, targetGrammar, t, v);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testWithANTLRAdvExprParser() throws Exception {
		String JAVA_CLASS_NAME = "AdvExprParser";
		String JAVA_METHOD_NAME = "parse";
		String PATH = "src/compilers/antlr/advexpr/";
		
		Grammar targetGrammar = GrammarParser.parse(new File("src/compilers/antlr/advexpr/AdvExpr.atg"));
		
		Set<Terminal> alphabet = targetGrammar.getTerminals();
		
		final IResultValidation v = new IResultValidation() {

			@Override
			public boolean validate(TreeNode result) {
				if (result == null) { return false; }
				return true;
			}
		};

		final ITarget t = new ITarget() {

			@Override
			public TreeNode execute(String input) {
				return ANTLRAdvExprParser.parse(input);
			}
		};
		Grammar seed = GrammarParser.parse(new File("src/compilers/antlr/advexpr/AdvExprSeed.atg"));
		FuzzingBasedGrammarLearner fuzzer = new FuzzingBasedGrammarLearner(alphabet, seed, t, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH, v, 1000, new Random(42));
		
		try {		
			
			long startTime = System.nanoTime();
			Grammar learnedGrammar = fuzzer.learn();		
			System.out.println("Time elapsed: " + ((System.nanoTime()-startTime))/1000000000 + "s");
			
			
			testGrammars(learnedGrammar, targetGrammar, t, v);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWithANTLRHelloParser() throws Exception {
		String JAVA_CLASS_NAME = "HelloParser";
		String JAVA_METHOD_NAME = "parse";
		String PATH = "src/compilers/antlr/hello/";
		
		Grammar targetGrammar = GrammarParser.parse(new File("src/compilers/antlr/hello/Hello.atg"));
		
		Set<Terminal> alphabet = targetGrammar.getTerminals();
		
		final IResultValidation v = new IResultValidation() {

			@Override
			public boolean validate(TreeNode result) {
				if (result == null) { return false; }
				return true;
			}
		};

		final ITarget t = new ITarget() {

			@Override
			public TreeNode execute(String input) {
				return ANTLRHelloParser.parse(input);
			}
		};
		Grammar seed = GrammarParser.parse(new File("src/compilers/antlr/hello/HelloSeed.atg"));
		FuzzingBasedGrammarLearner fuzzer = new FuzzingBasedGrammarLearner(alphabet, seed, t, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH, v, 1000, new Random(42));
		
		try {		
			
			long startTime = System.nanoTime();
			Grammar learnedGrammar = fuzzer.learn();		
			System.out.println("Time elapsed: " + ((System.nanoTime()-startTime))/1000000000 + "s");
			
			
			testGrammars(learnedGrammar, targetGrammar, t, v);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWithANTLRMailParser() throws Exception {
		String JAVA_CLASS_NAME = "MailParser";
		String JAVA_METHOD_NAME = "parse";
		String PATH = "src/compilers/antlr/mail/";
		
		Grammar targetGrammar = GrammarParser.parse(new File("src/compilers/antlr/mail/Mail.atg"));
		
		Set<Terminal> alphabet = targetGrammar.getTerminals();
		
		final IResultValidation v = new IResultValidation() {

			@Override
			public boolean validate(TreeNode result) {
				if (result == null) { return false; }
				return true;
			}
		};

		final ITarget t = new ITarget() {

			@Override
			public TreeNode execute(String input) {
				return ANTLRMailParser.parse(input);
			}
		};
		Grammar seed = GrammarParser.parse(new File("src/compilers/antlr/mail/MailSeed.atg"));
		FuzzingBasedGrammarLearner fuzzer = new FuzzingBasedGrammarLearner(alphabet, seed, t, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH, v, 1000, new Random(42));
		
		try {		
			
			long startTime = System.nanoTime();
			Grammar learnedGrammar = fuzzer.learn();		
			System.out.println("Time elapsed: " + ((System.nanoTime()-startTime))/1000000000 + "s");
			
			
			testGrammars(learnedGrammar, targetGrammar, t, v);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWithANTLRJsonParser() throws Exception {
		String JAVA_CLASS_NAME = "JsonParser";
		String JAVA_METHOD_NAME = "parse";
		String PATH = "src/compilers/antlr/json/";
		
		Grammar targetGrammar = GrammarParser.parse(new File("src/compilers/antlr/json/Json.atg"));
		
		Set<Terminal> alphabet = targetGrammar.getTerminals();
		
		final IResultValidation v = new IResultValidation() {

			@Override
			public boolean validate(TreeNode result) {
				if (result == null) { return false; }
				return true;
			}
		};

		final ITarget t = new ITarget() {

			@Override
			public TreeNode execute(String input) {
				return ANTLRJsonParser.parse(input);
			}
		};
		Grammar seed = GrammarParser.parse(new File("src/compilers/antlr/json/JsonSeed.atg"));
		FuzzingBasedGrammarLearner fuzzer = new FuzzingBasedGrammarLearner(alphabet, seed, t, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH, v, 10000, new Random(42));
		
		try {		
			
			long startTime = System.nanoTime();
			Grammar learnedGrammar = fuzzer.learn();		
			System.out.println("Time elapsed: " + ((System.nanoTime()-startTime))/1000000000 + "s");
			
			
			testGrammars(learnedGrammar, targetGrammar, t, v);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWithANTLRUrlParser() throws Exception {
		String JAVA_CLASS_NAME = "UrlParser";
		String JAVA_METHOD_NAME = "parse";
		String PATH = "src/compilers/antlr/url/";
		
		Grammar targetGrammar = GrammarParser.parse(new File("src/compilers/antlr/url/Url.atg"));
		
		Set<Terminal> alphabet = targetGrammar.getTerminals();
		
		final IResultValidation v = new IResultValidation() {

			@Override
			public boolean validate(TreeNode result) {
				if (result == null) { return false; }
				return true;
			}
		};

		final ITarget t = new ITarget() {

			@Override
			public TreeNode execute(String input) {
				return ANTLRUrlParser.parse(input);
			}
		};
		Grammar seed = GrammarParser.parse(new File("src/compilers/antlr/url/UrlSeed.atg"));
		FuzzingBasedGrammarLearner fuzzer = new FuzzingBasedGrammarLearner(alphabet, seed, t, JAVA_CLASS_NAME, JAVA_METHOD_NAME, PATH, v, 10000, new Random(42));
		
		try {		
			
			long startTime = System.nanoTime();
			Grammar learnedGrammar = fuzzer.learn();		
			System.out.println("Time elapsed: " + ((System.nanoTime()-startTime))/1000000000 + "s");
			
			
			testGrammars(learnedGrammar, targetGrammar, t, v);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void testGrammars(Grammar learned, Grammar target, ITarget target_parser, IResultValidation val) throws MalformedURLException, IOException {
				
		UniversialParser learned_parser = new UniversialParser(learned);
		TreeGenerator learned_gen = new TreeGenerator(learned, new Random(42), 100);
		TreeGenerator target_gen = new TreeGenerator(target, new Random(42), 100);

		int amount = 1000;
		
		int tp = 0;
		int fp = 0;
		
		for (int i = 0; i < amount; i++) {
			
			TreeNode input = learned_gen.yield();
			TreeNode result = target_parser.execute(input.toString());
			
			if (val.validate(result)) { tp++; } else {fp++; System.out.println("FP: \"" + input.toString() + "\"");}
			
		}
		
		System.out.println("Precision: " + ((double)tp/amount));
		
		
		tp = 0;
		int fn = 0;
		
		for (int i = 0; i < amount; i++) {
			
			TreeNode input = target_gen.yield();			
			if (learned_parser.parse(input.toString().replace(" ", ""))) { 
				tp++; //System.out.println("TRUE: " + input.toString().replace(" ", ""));
			} else {
				fn++; //System.out.println("FN: \"" + input.toString().replace(" ", "") + "\"");
			}
			
		}
		
		System.out.println("Recall: " + ((double)tp/amount));

	}
}
