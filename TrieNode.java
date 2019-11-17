import java.util.*;

public class TrieNode
{
	public TrieNode[] children;
	public boolean isEnd;

	public TrieNode() {
		children = new TrieNode[27];
		isEnd = false;
	}
	
}