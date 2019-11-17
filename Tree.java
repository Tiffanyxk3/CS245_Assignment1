import java.util.*;

public class Tree
{
	TreeNode root;

	public Tree() {
	}

	public void insert(String elem) {
		root = insert(root, elem);
	}

	public TreeNode insert(TreeNode t, String elem) {
		if (t == null) {
			return new TreeNode(elem);
		}
		if (elem.compareTo(t.data) > 0) {
			t.right = insert(t.right, elem);
		}
		else {
			t.left = insert(t.left, elem);
		}
		return t;
	}

	public boolean search(String elem) {
		return search(root, elem);
	}

	public boolean search(TreeNode t, String elem) {
		if (t == null){
			return false;
		}
		if (t.data.equals(elem)) {
			return true;
		}
		if (elem.compareTo(t.data) < 0){
			return search(t.left, elem);
		}
		else {
			return search(t.right, elem);
		}
	}

	public TreeNode build(TreeNode t) {
	// make a balanced tree
		Vector<TreeNode> nodes = new Vector<TreeNode>();
		storeNodes(t, nodes);
		return buildRecursion(nodes, 0, nodes.size()-1);
	}

	public TreeNode buildRecursion(Vector<TreeNode> n, int start, int end) {
		if (start > end) {
			return null;
		}
		int mid = (start+end)/2; 
		TreeNode node = n.get(mid);
		node.left = buildRecursion(n, start, mid-1);
		node.right = buildRecursion(n, mid+1, end);
		return node;
	}

	public void storeNodes(TreeNode t, Vector<TreeNode> n) {
		if (t == null) {
			return;
		}
		storeNodes(t.left, n);
		n.add(t);
		storeNodes(t.right, n);
	}
}