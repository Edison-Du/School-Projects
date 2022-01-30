/**
 * [letterMutation.java]
 * This program allows users to find the shortest path between
 * two words in an inputted dictionary.
 * 
 * @author Edison Du
 * @version 1.0 Dec 17, 2021
 */

import java.util.Scanner;
import java.util.LinkedList;
import java.io.File;

public class LetterMutation {

    public static void main(String[] args) {

        WordList wordList;
        LinkedList<String> mutationPath;

        Scanner keyboard = new Scanner(System.in);
        File wordFile;
        String firstWord;
        String secondWord;
        Character userOption;
        
        // Get user input for file to read words from (repeatedly ask if the file does not exist)
        do {
            System.out.println("Please enter the name of the file: ");
            System.out.print("> ");

            wordFile = new File(keyboard.next());
            if (!wordFile.exists()) {
                System.out.println("\nFile does not exist.");
            }
            System.out.println();
        } while (!wordFile.exists());

        wordList = new WordList(wordFile);

        // Repeatedly allow user the query the shortest path between two words
        do {
            System.out.println("Enter the first word: ");
            System.out.print("> ");
            firstWord = keyboard.next().toLowerCase();

            System.out.println("\nEnter the second word: ");
            System.out.print("> ");
            secondWord = keyboard.next().toLowerCase();

            mutationPath = wordList.getMutationPath(firstWord, secondWord);

            // Input words are invalid
            if ( !wordList.wordExists(firstWord) || !wordList.wordExists(secondWord)
                || firstWord.length() != secondWord.length() ) {
                System.out.println("\nYou did not enter valid words, please check that the words are in the word list and that they are the same length.");

            // No path exists
            } else if (mutationPath == null) {
                System.out.printf("\nNo mutation path exists between '%s' and '%s'.\n", firstWord, secondWord);

            // Print path
            } else {
                System.out.printf(
                    "\nMutation path of length %d found between '%s' and '%s': \n", 
                    mutationPath.size()-1,
                    firstWord,
                    secondWord
                );

                for (String word : mutationPath) {
                    System.out.print(" -> " + word);
                }
                System.out.println();
            }

            // Give user option to quit or continue
            System.out.println("\nDo you wish to quit the program? (Y/N) ");
            System.out.print("> ");
            userOption = Character.toLowerCase(keyboard.next().charAt(0));
            System.out.println();

        } while (userOption == 'n');

        keyboard.close();
    }
}