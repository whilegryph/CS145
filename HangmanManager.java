// William Greiner, Donnelly Miller
// CS 145 Ryan Parsons
// 02/18/2022
// Lab 4: Evil Hangman

// This program keeps track of the state of the evil hangman game.

import java.util.*;

public class HangmanManager {

   private Set<String> newDictionary; /*dictionary that only contains words that 
                                                            fit the desired length*/
   private SortedSet<Character> guesses; /*list of chars guessed by user*/
   private String pattern;
   private int guessAmount; /*guesses left*/

// takes any word out of the given list that isn't equal in length to the given length, 
// as well as any duplicate words.
   public HangmanManager(List<String> dictionary, int length, int max) {
   
      String temp = "";
   
      for(int i = 0; i < length; i++) {
         temp += "-";
      }
   
      pattern = temp;
      guessAmount = max;
      newDictionary = new TreeSet<String>();
      guesses = new TreeSet<Character>();
   
      for(String word : dictionary) {
         if(length == word.length()) {
            newDictionary.add(word);
         }
      }
   }
   
// contains the new dictionary.
   public Set<String> words() {
      return newDictionary;
   }
   
// gives the amount of guesses the player has left.
   public int guessesLeft() {
      return guessAmount; 
   }
   
// contains a sorted set of characters guessed by the player.
   public SortedSet<Character> guesses() {
      return guesses;
   }
   
// the pattern of characters guessed to characters not yet guessed, 
// the latter of which is displayed as dashes
   public String pattern() {
      return pattern;
   }
// records the next guess made by the user, and uses it to determine which path
// or family of words to use. It also updates guesses left, 
// and updates the pattern to a new pattern.
   public int record(char guess) {
      guessAmount--;
      guesses.add(guess);

//    creates a map with keys for patterns and elements for lists of words that match
      Map<String, Set<String>> m = new HashMap<String, Set<String>>();
   
//    iterates through dictionary
      for(String word : newDictionary) {
      
         Set<String> match = new HashSet<String>();
         String temp = patternMaker(word, guess);
         
//       compares each element pattern with every other element pattern. Makes map of these
//       patterns and their matching values.
         for(String word2 : newDictionary) {
            if(temp.equals(patternMaker(word2, guess))) {
//             adds value to list
               match.add(word2);
            }
         }

//       adds pattern and its matching values to map.
         m.put(temp, match);
      
      }
      
      System.out.println(m);
      
      int count = bestPattern(m, guess);
      newDictionary = m.get(patternMaker(pattern, guess));
      
      System.out.println("pattern: " + pattern + "\n" + m);
      
      return count;
   
   }
   
// make additional private helper methods(?)
   
// calculates most devious pattern
   private int bestPattern(Map<String, Set<String>> m, char guess) {
   
      int best = 0;
      String wordTemp = "";
      String last = "";
      
//    goes through maps list of keys
      for(String word : m.keySet()) {
//       checks the size of the key's assosciated value, and updates the largest key variable,
//       "best" if its size is smaller than the key's assosciated value. Updates pattern.
         if(m.get(word).size() == best) {
            wordTemp = comparePattern(word, last, guess);
         } else if(m.get(word).size() > best) {
            best = m.get(word).size();
            wordTemp = word;
         }
//       stores current word to be compared to next word.
         last = word;
      }
      
      patternUpdate(wordTemp);
      int count = 0;
      
//    counts amount of times guess shows up in pattern.
      for(int i = 0; i < pattern.length(); i++) {
         if(pattern.charAt(i) == guess) {
            count++;
         }
      }
      
      return count;
      
   }
   
// makes pattern for word and returns that pattern
   private String patternMaker(String word, char guess) {
   
      String pat = "";
   
      for(int i = 0; i < word.length(); i++) {
         if(word.charAt(i) == guess) {
            pat += guess;
         } else {
            pat += "-";
         }
      }
      
      return pat;
   
   }
   
// compares two patterns, takes the current word in cycle, and last word in cycle
// and then picks the one with more instances of guess.
   private String comparePattern(String current, String last, char guess) {
      int count = 0;
      for(int i = 0; i < current.length(); i++) {
         if(current.charAt(i) == guess) {
            count++;
         }
      }
      int count2 = 0;
      for(int i = 0; i < last.length(); i++) {
         if(last.charAt(i) == guess) {
            count2++;
         }
      }
      if(count <= count2) {
         return current;
      } else {
         return last;
      }
   }
   
// updates pattern
   private void patternUpdate(String word) {
      String temp = "";
      for(int i = 0; i < pattern.length(); i++) {
         if(word.charAt(i) != '-') {
            temp += word.charAt(i);
         } else {
            temp += pattern.charAt(i);
         }
      }
      
      pattern = temp;
   }

}