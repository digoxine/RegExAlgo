package utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class RegExStringGenerator {

    public static final int FIRST_CHARACTER_ASCII = 97; // the a
    public static final int FINAL_CHARACTER_ASCII = 122;

    /**
     *
     * @param sizeRegEx          size of regEx the user wants to generate
     * @return                   a regex of length in range [sizeMin, sizeMax]
     */
    public static String generateRegEx(int sizeRegEx) {
        int availableNbCharacters = sizeRegEx;
        StringBuffer ret = new StringBuffer();
        while(availableNbCharacters > 0)
        {
            if( availableNbCharacters == 1)
            {
                ret.append((char)ThreadLocalRandom.current().nextInt(FIRST_CHARACTER_ASCII, FINAL_CHARACTER_ASCII));
                availableNbCharacters -= 1;
            }
            else
            {
                if (availableNbCharacters > 3)
                { // It is possible to use a parenthesis. Min parenthesis reg ex is '(' 'CHAR' ')' '+|*'
                    ret.append((char)'(');
                    availableNbCharacters -= 3;

                    System.out.println("available nb characters before add of parenthesis: " + availableNbCharacters);
                    String insideParenthesis = generateRegExInsideParenthesis(ThreadLocalRandom.current().nextInt(1, availableNbCharacters+1));
                    availableNbCharacters -= insideParenthesis.length();
                    ret.append(insideParenthesis);
                    ret.append((char)')');
                    ret.append((char)'*');
                    System.out.println("available nb characters after : " + availableNbCharacters);
                }
                else {
                    System.out.println("inside else");
                    for (int i=0;i<availableNbCharacters; i++) {
                        ret.append((char) ThreadLocalRandom.current().nextInt(FIRST_CHARACTER_ASCII, FINAL_CHARACTER_ASCII));
                        availableNbCharacters -- ;
                    }
                }
            }
        }
        return ret.toString();
    }

    /**
     *
     * @param availableLength
     * @return                  the available size for the regex parameter inside the parenthesis
     */
    public static String generateRegExInsideParenthesis(int availableLength) {
         //generate an int between 1 and size available

        int ascii = 0;
        int sizeUsed = 0;
        StringBuffer ret = new StringBuffer();
        if (availableLength == 1) {
            ascii = ThreadLocalRandom.current().nextInt(FIRST_CHARACTER_ASCII, FINAL_CHARACTER_ASCII);
            ret.append((char) ascii);
            return ret.toString();
        }
        int randInt = ThreadLocalRandom.current().nextInt(1,availableLength+1);

        System.out.println("my length inside parenthesis"+randInt);


        if (randInt == 1) {
            // Generating only one character
            // Finding an ascii character
            ascii = ThreadLocalRandom.current().nextInt(FIRST_CHARACTER_ASCII, FINAL_CHARACTER_ASCII);
            ret.append((char) ascii);
            return ret.toString();
         }
        ret.append((char) ThreadLocalRandom.current().nextInt(FIRST_CHARACTER_ASCII, FINAL_CHARACTER_ASCII));
        while (sizeUsed < randInt)
        {
            ascii = ThreadLocalRandom.current().nextInt(FIRST_CHARACTER_ASCII, FINAL_CHARACTER_ASCII);
            sizeUsed ++;
            ret.append((char) '|');
            ret.append((char) ascii);
        }

        return ret.toString();
    }

}
