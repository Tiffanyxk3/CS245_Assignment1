import java.util.*;

public class Trie
{
	public TrieNode root;

	public Trie() {
		root = new TrieNode();
	}

	public void insert(String elem) {
		TrieNode t = root;
		for (int i=0; i<elem.length(); i++) {
			if (elem.charAt(i) == '\'') {
			// characte at i is a '
				if (t.children[26] == null) {
					t.children[26] = new TrieNode();
				}
				t = t.children[26];
			}
			else {
			// character at i is a letter
				if (t.children[(int)(elem.charAt(i)-'a')] == null) {
					t.children[(int)(elem.charAt(i)-'a')] = new TrieNode();
				}
				t = t.children[(int)(elem.charAt(i)-'a')];
			}
		}
		t.isEnd = true;
		// search until the last word in elem and make isEnd to true
	}

	public boolean search(String elem) {
		TrieNode t = root;
		for (int i=0; i<elem.length(); i++) {
			if (elem.charAt(i) == '\'') {
				if (t.children[26] == null) {
				// there's no '
					return false;
				}
				t = t.children[26];
			}
			else {
				if (t.children[(int)(elem.charAt(i)-'a')] == null) {
				// there's no the specific character
					return false;
				}
				t = t.children[(int)(elem.charAt(i)-'a')];
			}
		}
		return t.isEnd;
	}

}