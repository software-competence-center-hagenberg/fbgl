package compiler.coco;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class CompilerGenerator {

	public static void genCompiler(File f) throws IOException {
				
		String[] args = new String[] {f.getPath(), "-package", "at.scch.codeanalytics.compile.generator", "-o", "src/main/java/at/scch/codeanalytics/compile/generator/", "-frames", "src/main/resources/coco/"};		
		Coco.main(args);		
		
		// Recompile Scanner and Parser
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();			
		compiler.run(null, null, null, "src/main/java/at/scch/codeanalytics/compile/generator/Scanner.java");
		compiler.run(null, null, null, "src/main/java/at/scch/codeanalytics/compile/generator/Parser.java");
		
		// Move .class files to target folder
		File folder = new File("src/main/java/at/scch/codeanalytics/compile/generator/");
		for (File file : folder.listFiles()) {
			if (file.isFile() && file.getName().contains(".class")) {
				
				String targetPath = "target/classes/at/scch/codeanalytics/compile/generator/";
				Files.deleteIfExists(new File(targetPath + file.getName()).toPath());
				Files.move(file.toPath(), new File(targetPath + file.getName()).toPath());
				
			}
		}
	}
}
