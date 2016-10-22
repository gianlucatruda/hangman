/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.program.*;
import acm.util.*;
import java.awt.Color;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Hangman extends ConsoleProgram {

	private HangmanLexicon hangmanWords;
	
	private HangmanCanvas canvas;
	
	private final RandomGenerator rgen = RandomGenerator.getInstance();
	
	/** Tracks the number of guesses the player has */
	private int guessCounter = 8;
    
  //This is the word being guessed
	private String hiddenWord;
    
  //This is the secret word
	private String word = pickWord();
	
	//This is the latest character entered by the user
	private char ch;
	
	//This string keeps track of all the incorrect guessed letters
	private String incorrectLetters = "";
    
        
        /**
         * Returns the number of guesses remaining in the round.
        * @return guessCounter
         */
        public int getGuesses(){
            return guessCounter;
        }
        
        /**
         * Returns the length of the mystery word.
         */
        public int getWordLength(){
            return hiddenWord.length();
        }
        
        @Override
    public void run() {
        
        //Option Menu to choose AI or Human player.
        
        JFrame frame = new JFrame();
        Object[] options = {"AI vs AI",
                    "AI vs Human",
                    "Human vs AI"};
        int n = JOptionPane.showOptionDialog(frame,
            "Select your game type.",
            "HANGMAN",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[2]);
        
            switch (n) {
                case 0:
                    setUpGame();
                    SimpleAI ai = new SimpleAI();
                    ai.setMysteryWordLength(word.length());
                    try {
                        playGame(ai);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
                    }       break;
                case 1:
                    setUpGame();
                    playGame();
                    break;
                case 2:
                    setUpUserGame();
                    SimpleAI ai2 = new SimpleAI();
                    ai2.setMysteryWordLength(word.length());                    
                    try {
                        playGame(ai2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
                    }       
                    break;
                default:
                    exit();
                    break;
            }
        
    }
    
    
    /*Set up the game by displaying the welcome message, 
     *how many letters there are in the word, 
     *and how many guesses the user has
     */
    private void setUpGame() {
    	canvas.reset();
    	hiddenWord = showNumberOfLetters();
    	canvas.displayWord(hiddenWord);
    	println("Welcome to Hangman!\n");
        println("Your word is " + hiddenWord.length() + " letters long.");
    	println("The word now looks like this: " + hiddenWord);
    	println("You have " + guessCounter + " guesses left.");
    }
    
    private void setUpUserGame(){
            JFrame frame = new JFrame();
            Object[] possibilities = hangmanWords.getAllWords();
            String s = (String)JOptionPane.showInputDialog(
                    frame,
                    "Enter a word for the AI to guess:\n",
                    "Human vs AI",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "AARDVARK");
        word = s.toUpperCase();
        setUpGame();
    }
	
    //Generates a random word selected from the HangmanLexicon
    private String pickWord() {
    	hangmanWords = new HangmanLexicon();
    	int randomWord = rgen.nextInt(0, (hangmanWords.getWordCount() - 1)); 
    	String pickedWord = hangmanWords.getWord(randomWord);
    	return pickedWord;
    }
	
    //Shows how many letters there are in the word as part of game setup
	private String showNumberOfLetters() {
		String result = "";
		for(int i = 0; i< word.length(); i++) {
			result = result + "-";
		}
		return result;
		}
	
	private void playGame() {
		while(guessCounter > 0) {
			String getChar = readLine("Your guess: ");
			while (true) {
				if(getChar.length() > 1) {
					getChar = readLine("You can only guess one letter at a time. Try again: ");
				}
				if(getChar.length() == 1) break;
			}
			ch = getChar.charAt(0);
			if(Character.isLowerCase(ch)) {
				ch = Character.toUpperCase(ch);
			}
			letterCheck();
			if (hiddenWord.equals(word)) {
				println("You guessed the word: " + word);
				println("You win");
                                canvas.setBackground(Color.green);
				break;
			}
			println("The word now looks like this: " + hiddenWord);
			println("You have " + guessCounter + " guesses left.");
			
		}
		if (guessCounter == 0) {
			println("You're completely hung.");
			println("The word was:" + word);
			println("You lose.");
                        canvas.setBackground(Color.red);
		}		
	}
        
        private void playGame(SimpleAI ai) throws InterruptedException {
            		while(guessCounter > 0) {
			println("Your guess: ");
                        String getChar = ai.makeGuess();
                        println(getChar);
			while (true) {
				if(getChar.length() > 1) {
					println("You can only guess one letter at a time. Try again: ");
                                        getChar = ai.makeGuess();
                                        println(getChar);
				}
				if(getChar.length() == 1) break;
			}
			ch = getChar.charAt(0);
			if(Character.isLowerCase(ch)) {
				ch = Character.toUpperCase(ch);
			}
			letterCheck();
			if (hiddenWord.equals(word)) {
				println("You guessed the word: " + word);
				println("You win");
                                canvas.setBackground(Color.green);
				break;
			}
			println("The word now looks like this: " + hiddenWord);
			println("You have " + guessCounter + " guesses left.");
			
		}
		if (guessCounter == 0) {
			println("You're completely hung.");
			println("The word was:" + word);
			println("You lose.");
                        canvas.setBackground(Color.red);
		}
        }
	
	//updates the hiddenWord if the character entered is correct 
	private void letterCheck() {
		//checks to see if the guessed letter is in the word
		if(word.indexOf(ch) == -1) {
			println("There are no " + ch + "'s in the word");
			guessCounter--;
			incorrectLetters = incorrectLetters + ch;
			canvas.noteIncorrectGuess(incorrectLetters);
		}
		if(word.indexOf(ch) != -1) {
			println("The guess is correct.");
		}
		//goes through each of the letters in the word and checks if it matches the guessed letter, 
		//if it's a match, updates the hidden word to reveal the position of the guessed letter
		for(int i = 0; i < word.length(); i++) {
			if (ch == word.charAt(i)) {
				if (i > 0) {
					hiddenWord = hiddenWord.substring(0, i) + ch + hiddenWord.substring(i + 1);
				}
				if(i == 0) {
					hiddenWord = ch + hiddenWord.substring(1);
				}
				canvas.displayWord(hiddenWord);
			}
		}
        }
	
        @Override
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
		}
        

        
}
