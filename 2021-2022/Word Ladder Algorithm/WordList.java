/**
 * [WordList.java]
 * This class reads and manages a list of words, and allows users to
 * get the shortest mutation sequence between two words in the list.
 * 
 * @author Edison Du
 * @version 1.0 Dec 17, 2021
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;

public class WordList {
    
    /**
     * This is a hashmap containing all the words in the word list,
     * and associates a word with the previous word used to reach it
     * in the mutation path (null if no previous word).
     */
    private HashMap<String,String> words;

    /**
     * WordList
     * Constructor that reads words from a file.
     * @param file the file to read from
     */
    public WordList(File file) {
        words = new HashMap<>();
        readFile(file);
    }

    /**
     * readFile
     * Reads words from a file and stores it inside a HashMap.
     * @param file the file to read from
     * @return true if the file exists, false otherwise
     */
    private boolean readFile(File file) {
        try {
            Scanner fileInput = new Scanner(file);
            while(fileInput.hasNext()) {
                words.put(fileInput.next().toLowerCase(), null);
            }
            fileInput.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * wordExists
     * Checks if a word is in the word list
     * @param word the word to check
     * @return true if the word exists, false otherwise
     */
    public boolean wordExists(String word) {
        return words.containsKey(word);
    }

    /**
     * getPrevious
     * Gets the previous word used to reach a word in the list
     * @param word the word to get the previous of
     * @return the previous word, or null if there is no previous word or the word does not exist
     */
    private String getPrevious(String word) {
        return words.get(word);
    }

    /**
     * setPrevious
     * Sets the previous word of a word
     * @param word the word to set the previous of
     * @param previousWord the previous word to set to
     * @return true if the word exists, false otherwise
     */
    private boolean setPrevious(String word, String previousWord) {
        if (wordExists(word)) {
            words.put(word, previousWord);
            return true;
        }
        return false;
    }

    /**
     * resetPaths
     * Changes the previous word of all words in the list to null
     */
    private void resetPaths() {
        for (Map.Entry<String, String> entry : words.entrySet()) {
            entry.setValue(null);
        }
    }

    /**
     * getMutationPath
     * Gets the shortest path between two words by changing one letter
     * @param startingWord the word to start from
     * @param endingWord the word to reach
     * @return A linked list containing all words in the path, or null if there is no path
     */
    public LinkedList<String> getMutationPath(String startingWord, String endingWord) {
        
        // Make sure the words are valid
        if (wordExists(startingWord) && wordExists(endingWord)
            && (startingWord.length() == endingWord.length()) ) {

            LinkedList<String> path = new LinkedList<>();
            Queue<String> queue = new LinkedList<>();

            queue.add(startingWord);
            setPrevious(startingWord, "");

            // Breadth first search until there are no more words or the ending word is reached
            while (!queue.isEmpty() && getPrevious(endingWord) == null) {

                // Get the first word in the queue and add its mutations to the queue 
                String currentWord = queue.poll();
                for (String mutation : getAllMutations(currentWord)) {
                    if (getPrevious(mutation) == null) {
                        queue.add(mutation);
                        setPrevious(mutation, currentWord);
                    }
                }
            }

            if (getPrevious(endingWord) == null) {
                return null;
            }

            // Backtrack through the previous words to find the path
            while (!endingWord.equals("")) {
                path.addFirst(endingWord);
                endingWord = getPrevious(endingWord);
            }

            resetPaths();
            return path;
        }
        return null;
    }

    /**
     * getAllMutations
     * Gets all the words that differs by one letter (mutations) from a given word
     * @param word the word to find mutations of
     * @return A linked list containing the mutations
     */
    public LinkedList<String> getAllMutations(String word) {
        LinkedList<String> mutations = new LinkedList<>();

        // Swap each letter in the word with all other possible letters
        for (int i = 0; i < word.length(); i++) {
            for (char j = Consts.FIRST_LETTER; j <= Consts.LAST_LETTER; j++) {
                String newWord = word.substring(0,i) + j + word.substring(i+1);

                // If the word exists, add it to the list
                if (j != word.charAt(i) && wordExists(newWord)) {
                    mutations.add(newWord);
                }
            }
        }
        
        return mutations;
    }
}