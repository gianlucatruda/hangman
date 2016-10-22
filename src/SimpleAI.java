
/**
 *
 * @author 
 */

import java.util.concurrent.TimeUnit;


public class SimpleAI {
    
    private final String smallList  = "AEOITSUPRNDBGMYLHWFCKXVJZG";
    private final String medList    = "ESIARNTOLDUCGPMHBYFKWVZXJQ";
    private final String bigList    = "IETNSOARLCPUMDHGYBVFZXWKQJ";
    private String listy;
    int count;
    int wordLength;
    
    HangmanLexicon lexicon = new HangmanLexicon();
    
    public SimpleAI(){
        count = 0;
    }
    
    public void setMysteryWordLength(int x){
        wordLength = x;
    }
    
    public String makeGuess() throws InterruptedException{
        
        if(wordLength < 5)
        {
            listy = smallList;
        }
        else if ((wordLength >= 5) && (wordLength <10))
        {
            listy = medList;
        }
        else
        {
            listy = bigList;
        }
        
        TimeUnit.SECONDS.sleep(1);
        String out = listy.substring(count, count+1);
        count++;
        return out;
    }
    
}
