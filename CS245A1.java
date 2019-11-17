import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class CS245A1
{
	private TrieNode trieN;
	private TreeNode treeN;

	public Trie trie;
	public Tree tree;
	public String inputFileName, outputFileName;
	public String dataStructure;
	public ArrayList<String> dictionary, input;
	public BufferedWriter output;

	public CS245A1() {
		trie = new Trie();
		tree = new Tree();
		dataStructure = "trie";
		dictionary = new ArrayList<String>();
		input = new ArrayList<String>();
	}

	public void readDictionary() {
		try {
			Scanner scan = new Scanner(new File("english.0"));
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				if (!line.isEmpty()) {
					dictionary.add(line);
				}
			}// scan the dictionary and store them in array
		}
		catch (FileNotFoundException e) {
			System.out.println("english.0 file not found.");
			return;
		}

		if (dataStructure.equals("trie")) {
			trieDictionary();
		}
		else {
			treeDictionary(0, dictionary.size()-1);
		}
	}

	public void trieDictionary() {
	// store elements in dictionary array into a trie
		for (int i=0; i<dictionary.size(); i++) {
			trie.insert(dictionary.get(i).toLowerCase());
		}
	}

	public void treeDictionary(int start, int end) {
	// store elements in dictionary array into a tree
		for (int i=0; i<dictionary.size(); i++) {
			tree.insert(dictionary.get(i).toLowerCase());
		}
		tree.root = tree.build(tree.root);
	}

	public void readInput() {
	// store element in input file into an array
		try {
			Scanner scan = new Scanner(new File(inputFileName));
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				if (!line.isEmpty()) {
					input.add(line);
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Input file not found.");
			return;
		}

		if (dataStructure.equals("trie")) {
			trieInput();
		}
		else {
			treeInput();
		}

	}

	public void trieInput() {
	// search the inputs using trie
		try {
			File newFile = new File(outputFileName);
			output = new BufferedWriter(new FileWriter(newFile));

			for (int i=0; i<input.size(); i++) {
				HashMap<String, Integer> distanceMap = new HashMap<String, Integer>();
				// distanceMap is a map from Strings in dictionary and their levenshtein distance with the input word
				String inputWord = input.get(i);
				if (trie.search(inputWord.toLowerCase())) {
					output.write(inputWord + "\n");
					// if the input word is in the dictionay then write it into the output file directly
				}
				else {
					for (int j=0; j<dictionary.size(); j++) {
						String word = dictionary.get(j);
						distanceMap.put(word, levenshtein(inputWord, word, inputWord.length(), word.length()));
						// store all Strings in dictionary with their levenshtein distance with the input word
					}
					distanceMap = distanceMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
					// sort distanceMap by value
					Iterator<String> iterator = distanceMap.keySet().iterator();
					for (int j=0; j<3; j++) {
						output.write(iterator.next() + " ");
					}
					// write the first three keys in distanceMap (with the three least levenshtein distance)
					output.write("\n");
				}
			}
			output.close();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void treeInput() {
	// search the inputs using tree, same thing as trieInput()
		try {
			output = new BufferedWriter(new FileWriter(outputFileName));
			for (int i=0; i<input.size(); i++) {
				HashMap<String, Integer> distanceMap = new HashMap<String, Integer>();
				String inputWord = input.get(i);
				if (tree.search(inputWord.toLowerCase())) {
					output.write(inputWord + "\n");
				}
				else {
					for (int j=0; j<dictionary.size(); j++) {
						String word = dictionary.get(j);
						distanceMap.put(word, levenshtein(inputWord, word, inputWord.length(), word.length()));
					
					}
					distanceMap = distanceMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
					Iterator<String> iterator = distanceMap.keySet().iterator();
					for (int j=0; j<3; j++) {
						output.write(iterator.next() + " ");
					}
					output.write("\n");
				}
			}
			output.close();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}	
	}

	public LinkedHashMap<String,Integer> sortMapByValue(HashMap<String, Integer> map) {
		List<String> keys = new ArrayList<>(map.keySet());
		List<Integer> values = new ArrayList<>(map.values());
		Collections.sort(values);
		Collections.sort(keys);

		LinkedHashMap<String,Integer> newMap = new LinkedHashMap<String,Integer>();
		Iterator<Integer> valueIterator = values.iterator();
		while (valueIterator.hasNext()) {
			Integer value = valueIterator.next();
			Iterator<String> keyIterator = keys.iterator();
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				Integer v1 = map.get(key);
				Integer v2 = value;
				if (v1.equals(v2)) {
					keyIterator.remove();
					newMap.put(key, value);
					break;
				}
			}
		}
		return newMap;
	}

	public int levenshtein(String str1, String str2, int lth1, int lth2) {
		int[][] length = new int[lth1+1][lth2+1];
		for (int i=0; i<=lth1; i++) {
			length[i][0] = i;
		}
		for (int i=0; i<=lth2; i++) {
			length[0][i] = i;
		}
		for (int i=1; i<=lth1; i++) {
			for (int j=1; j<=lth2; j++) {
				if (str1.charAt(i-1) == str2.charAt(j-1)) {
					length[i][j] = length[i-1][j-1];
				}
				else {
					length[i][j] = Math.min(length[i-1][j]+1, Math.min(length[i][j-1]+1, length[i-1][j-1]+1));
				}
			}
		}
		return length[lth1][lth2];
	}

	public void configuration() {
	// read properties file and store data structure type
		try {
			Scanner scan = new Scanner(new File("a1properties.txt"));
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				if (line.toLowerCase().contains("storage=")) {
					if (line.toLowerCase().contains("=tree")) {
						dataStructure = "tree";
					}
					else if (line.toLowerCase().contains("=trie")) {
						dataStructure = "trie";
					}
					else {
						dataStructure = "trie";
					}
				}
			}
		}
		catch (FileNotFoundException e) {
			dataStructure = "trie";
		}
	}


	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Invalid arguments.");
			return;
		}
		CS245A1 assignment1 = new CS245A1();
		assignment1.inputFileName = args[0];
		assignment1.outputFileName = args[1];
		assignment1.configuration();
		assignment1.readDictionary();
		assignment1.readInput();
	}

}