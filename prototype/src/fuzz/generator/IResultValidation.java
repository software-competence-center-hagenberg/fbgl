package fuzz.generator;

import grammar.addons.TreeNode;

public interface IResultValidation {

	public boolean validate(TreeNode result);
}
