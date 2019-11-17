There are two functions involved if a trie is used: trie.insert() and trie.search(). The worst case running time of each of these functions is O(k) where k is the length of the longest word in dictionary. insert() runs dicionary.size() times and search() runs input.size() times. So the total running time: n*O(k)

There are three functions involved if a tree is used: tree.insert(), tree.search(), and tree.build(). The worst case running time of insert() is O(n) where n is dictionary.size(), search() is O(logn), build() is O(n). So the total running time: O(n)

So trie take less time.

I did all extra credits except for the first one. Extra credit 2 is included in the file CS245A1.java. And there are two files for extra credit 3: ActiveCheckerType.java and ActiveChecker.java. ActiveCheckerType.java receives a string from the command line and print 1 to 3 suggestions for it. ActiveCheckerType.java receives a string from the common line and gives suggestions for the word as each letter is typed (simulation of when we are typing in a word file).