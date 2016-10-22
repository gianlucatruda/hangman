/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.io.*;
import java.util.*;

public class HangmanLexicon {

	private ArrayList <String> wordList = new ArrayList <String> ();	

	public HangmanLexicon() {
	//adds the individual words in the file to the array list
		try {
			BufferedReader hangmanWords = new BufferedReader(new FileReader("data/HangmanLexicon.txt"));
			while(true) {
				String line = hangmanWords.readLine();
				if(line == null) break;
				wordList.add(line);
			}
			hangmanWords.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}


/** Returns the word at the specified index.
     * @param index
     * @return  */
	public String getWord(int index) {
		return wordList.get(index);
	}

	/** Returns the number of words in the lexicon.
     * @return  */
	public int getWordCount() {
		return wordList.size();
	}
        
        public String[] getAllWords(){
            String[] out = new String[getWordCount()];
            for (int i=0; i<getWordCount();i++){
                out[i] = wordList.get(i);
            }
            
            return out;
        }
}
