package fuzzer;

import java.io.IOException;
import java.net.MalformedURLException;

import grammar.addons.TreeNode;

public interface ITarget {
	
	public TreeNode execute(String input) throws MalformedURLException, IOException;
	
}
