/**
 * [PoemPredictor.java]
 * This program accepts a poem as input and predicts what type it is
 * by counting the number of lines and syllables per line.
 * @author Edison Du
 * @version 1.0 Mar 10, 2021
 */

import java.util.Scanner;

class PoemPredictor {
  
  public static void main(String[] args) {
   
    /* ================================ Variables ================================ */
    
    Scanner input = new Scanner(System.in);
    
    String vowels = "aeiou";
    String line = "";
    String syllablesPerLine = "";
    String poemType = "";
    
    int syllables = 0; 
    int currentWordSyllables = 0;
    int totalLines = 0;
    int totalSyllables = 0;
    
    /* ================================ Main Code ================================ */
   
    // Take input and determine syllable count per line
    line = input.nextLine().toLowerCase() + " "; 
    while (!line.equals("quit ")) {
      
      syllables = 0;
      currentWordSyllables = 0;
      
      // Remove punctuation
      for (int i = 0; i < 6; i++) {
        
        char currentSign = ".,!?'-".charAt(i);
        
        while (line.indexOf(currentSign) >= 0) {
          line = line.substring(0,line.indexOf(currentSign)) + line.substring(line.indexOf(currentSign)+1);
        }
      }

      // Count syllables 
      for (int i = 0; i < line.length(); i++) {
        
        // Add 1 for every vowel in the sentence, unless they are part of a consecutive vowel pair
        if (vowels.indexOf(line.charAt(i)) != -1) {
          syllables++;
          currentWordSyllables++;
          
          if (vowels.indexOf(line.charAt(i+1)) != -1) {
            syllables--;
            currentWordSyllables--;
          }
          
          if (line.charAt(i) == 'e') {
            if ( (i > 0) && (vowels.indexOf(line.charAt(i-1)) == -1) ) {
              
              // Subtract 1 for cases where letter "e" is silent at the end of a word (e.g. cake)
              if (line.charAt(i+1) == ' ') {
                if ( ! ( (i-2 >= 0) && (line.charAt(i-1) == 'l') && (vowels.indexOf(line.charAt(i-2)) == -1) && (line.charAt(i-2) != 'l') ) ) {
                  syllables--;
                  currentWordSyllables--;
                }
               
              // Subtract 1 for cases where letter "e" is silent in "es" or "ed" endings of a word (e.g. sales, jumped)
              } else if ( (line.charAt(i+2) == ' ') && (i-2 >= 0) ) {
                
                if ( (line.charAt(i-1) == 'l') && ("sd".indexOf(line.charAt(i+1)) != -1) ) {
                  if ( (vowels.indexOf(line.charAt(i-2)) != -1) || ("lrw".indexOf(line.charAt(i-2)) != -1) ) {
                    syllables--;
                    currentWordSyllables--;
                  }
                  
                } else if ( (line.charAt(i+1) == 'd') && ("dt".indexOf(line.charAt(i-1)) == -1) ) {
                  syllables--;
                  currentWordSyllables--;
                  
                } else if ( (line.charAt(i+1) == 's') && ("gscxz".indexOf(line.charAt(i-1)) == -1) ){
                  if ( ! ( (i-2 >= 0) && (line.charAt(i-1) == 'h') && ("cs".indexOf(line.charAt(i-2)) != -1) ) ) {
                    syllables--;
                    currentWordSyllables--;
                  }
                }
                
              }
              
            }
          } else if (line.charAt(i) == 'i') {
            
            // Add 1 for cases where the vowel pair "ia" is two syllables (e.g. piano)
            if (line.charAt(i+1) == 'a') {
              syllables++;
              currentWordSyllables++;
              
              /* Make sure we don't count cases where "ia" is one syllable, such as "tian" or "cial"
               * E.g. Christian, social
               */
              if (i-1 >= 0) {
                if ( ("tc".indexOf(line.charAt(i-1)) != -1) && ("nl".indexOf(line.charAt(i+2)) != -1) ) {
                  syllables--;
                  currentWordSyllables--;
                }
              }
            
            // Add 1 for cases where the vowel pair "io" is two syllables (e.g. lion)
            } else if (line.charAt(i+1) == 'o') {
              syllables++;
              currentWordSyllables++;
              
              /* Make sure we don't count cases where "io" is one syllable, such as "tion", "nior" or "cious" 
               * E.g. Vacation, senior, delicious
               */
              if (line.charAt(i+2) == 'n') {
                if (i-2 >= 0) {
                  if ( ("cgst".indexOf(line.charAt(i-1)) != -1) || ( (line.charAt(i-2) == 's') && (line.charAt(i-1) == 'h') ) ) {
                    syllables--;
                    currentWordSyllables--;
                  }
                }
                
              } else if (line.charAt(i+2) == 'r') {
                if ( (i-1 >= 0) && ("nvl".indexOf(line.charAt(i-1)) != -1) ) {
                  syllables--;
                  currentWordSyllables--;
                }
                
              } else if ( (i+3 < line.length()) && (line.charAt(i+2) == 'u') && (line.charAt(i+3) == 's') ) {
                if ( (i > 0) && ("cgtx".indexOf(line.charAt(i-1)) != -1) ) {
                  syllables--;
                  currentWordSyllables--;
                }
              }
            }
          } 
          
        // Add 1 for cases where letter "y" sounds like a vowel (e.g. lazy)
        } else if (line.charAt(i) == 'y') {
          if ( (i > 0) && (vowels.indexOf(line.charAt(i-1)) == -1) && (vowels.indexOf(line.charAt(i+1)) == -1) ) {
            syllables++;
            currentWordSyllables++;
          }
        
        // Cases relating to the end of a word
        } else if ( (i > 0) && (line.charAt(i) == ' ') && (line.charAt(i-1) != ' ') ) {
          
          // Subtract 1 for words with the suffix "que", "gue" or "ely" (e.g. unique, vague, lonely)
          if (i-4 >= 0) {
            if ( (line.substring(i-3, i).equals("que")) || (line.substring(i-3, i).equals("gue")) ) { 
              syllables--;
              currentWordSyllables--;
            }
          }
          if ( (i-5 >= 0) && (line.substring(i-3, i).equals("ely")) && (vowels.indexOf(line.charAt(i-4)) == -1) ) {
            syllables--;
            currentWordSyllables--;
          }
          
          // Prevent cases where a word is measured to have 0 syllables
          if (currentWordSyllables == 0) {
            syllables++;
          } else {
            currentWordSyllables = 0;
          }
        }
      }
      
      // Update counters, then go to next line
      totalLines++;
      totalSyllables += syllables;
      syllablesPerLine += syllables + " ";
      
      line = input.nextLine().toLowerCase() + " ";
    }
    
    input.close();
    
    // Determine poem type
    if (syllablesPerLine.equals("5 7 5 ")) {
      poemType = "Haiku";
    } else if (syllablesPerLine.equals("5 7 5 7 7 ")) {
      poemType = "Tanka";
    } else if (syllablesPerLine.equals("2 4 6 8 2 ")) {
      poemType = "Cinquain";
    } else if (syllablesPerLine.equals("9 8 7 6 5 4 3 2 1 ")) {
      poemType = "Nonet";
    } else if (totalLines == 3){
      
      /* We determine if the poem is a Sijo by splitting syllablesPerLine into substrings seperated by the spaces
       * Each substring is the syllables per individual line, which we check if it is between 14 and 16 
       */
      int currentSpaceIndex = 0;
      int nextSpaceIndex = 0;
      int currentLineSyllables = 0;
      poemType = "Sijo";

      for (int i = 0; i < 3; i++) {
        if (syllablesPerLine.charAt(currentSpaceIndex) == ' ') {
          currentSpaceIndex++;
        }
        
        nextSpaceIndex = syllablesPerLine.substring(currentSpaceIndex).indexOf(" ") + currentSpaceIndex;
        currentLineSyllables = Integer.parseInt(syllablesPerLine.substring(currentSpaceIndex, nextSpaceIndex));
        
        if ( (currentLineSyllables < 14) || (currentLineSyllables > 16) ) {
          poemType = "";
        }
        
        currentSpaceIndex = nextSpaceIndex;
      }
      
    }
    
    /* ================================ Output to User ================================ */
    
    System.out.println("RESULTS");
    
    if (totalLines == 1) {
      System.out.println("There is 1 line in this poem");
    } else {
      System.out.println("There are " + totalLines + " lines in this poem");
    }
    
    System.out.println("There is a total of " + totalSyllables + " syllable(s)");
    System.out.println("The pattern is " + syllablesPerLine);
    System.out.println("\nPrediction:");
    
    if (poemType.equals("")) {
      System.out.println("Sorry, I cannot predict this poetry form");
    } else {
      System.out.println("This is a " + poemType + " Poem");
    }
    
  }
  
}
