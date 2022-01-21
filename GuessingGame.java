// Dwight Davis, William Griener, Donnelly Miller
// CS 145
// ??

// The program plays a simple guessing game with the user, in which it continuously prompts 
// the user to guess a pseudorandomly generated number until they have guessed right.

import java.util.*;

public class GuessingGame {

   public static final int MAX_NUM = 100;

   public static void main(String[] args) {
   
      Scanner console = new Scanner(System.in);
      
      introduction();
      
      int best = 0;
      int gameCount = 0;
      int guessCount = 0;
      boolean a = true;
   
      while(a) {
         Random rand = new Random();
         guessCount += playGame(rand, console);
         System.out.print("Do you want to play again? ");
         String playAgain = console.next();
         
         if(best > guessCount || best == 0) {
            best = guessCount;
         }
         gameCount++;
         if(!playAgain.substring(0, 1).equalsIgnoreCase("y")) {
            a = false;
         }
      }
      
      results(gameCount, guessCount, best);
   
   }
   // Describes the rules of the game and how the game will be played to the user.
   public static void introduction() {
      System.out.println(
         "This program allows you to play a guessing game.\n" +
         "I will think of a number between 1 and\n" +
         MAX_NUM + "and will allow you to guess until\n" +
         "you get it. For each guess, I will tell you\n" +
         "whether the right answer is higher or lower\n" +
         "than your guess.");
   }
   // Plays the game with the user.
   public static int playGame(Random rand, Scanner console) {
      System.out.println("\nI'm thinking of a number between 1 and " + MAX_NUM + "...");
      
      int num = rand.nextInt(MAX_NUM) + 1;
      
      int guessCount = 0;
      int randNum = rand.nextInt(MAX_NUM) + 1;
      int guess = 0;
       
      while (guess != randNum) {
         System.out.print("Your guess? ");
         guess = console.nextInt();
         if (guess > randNum) {
            System.out.println("It's lower.");
            guessCount++;
         } else if (guess < randNum) {
            System.out.println("It's higher.");
            guessCount++;
         } else {
            if(guessCount == 0) {
               System.out.println("You got it right in 1 guess.");
            } else {
               System.out.println("You got it right in " + guessCount + " guesses.");
            }
         }
      }
      return guessCount;
   }
   // Provides the stats of every game played to the user.
   public static void results(int gameCount, int guessCount, int best) {
      double average = (double) guessCount/gameCount;
      System.out.println("\nOverall results:");
      System.out.println("    total games\t= " + gameCount);
      System.out.println("    total guesses = " + guessCount);
      System.out.printf(("    guesses/game  = %.1f\n"), average);
      System.out.println("    best game\t\t= " + best);
   }
}