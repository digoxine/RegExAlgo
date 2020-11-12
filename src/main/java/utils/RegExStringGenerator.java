package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.min;


public class RegExStringGenerator {

    public static final int FIRST_CHARACTER_ASCII = 97; // the a
    public static final int FINAL_CHARACTER_ASCII = 122;
    public static final int LENGTH_INSIDE_PARENTHESIS_MAX = 3;
    public static char[] options = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't','u','v','w','x','y','z'};
    //prise en compte des fréquences d'apparitions des lettres dans la langue anglaise
    public static double[] weights = new double[]{
            0.082, 0.015, 0.028, 0.043, 0.13, 0.022, 0.02, 0.061, 0.07, 0.0015, 0.0077, 0.04, 0.024, 0.067,
            0.075, 0.019, 0.0095, 0.06, 0.063, 0.091, 0.028, 0.0098, 0.024, 0.00015, 0.02, 0.00074
    };
    public static Random rand = new Random();
    public static NavigableMap<Double, Character> map = new TreeMap<Double, Character>();
    public static double totalWeight=0d;

    /**
     *
     * @param sizeRegEx          size of regEx the user wants to generate
     * @return                   a regex of length in range [sizeMin, sizeMax]
     */
    public static String generateRegEx(int sizeRegEx) {



        for (int i=0; i<weights.length; i++) {
            totalWeight += weights[i];
            map.put(totalWeight, options[i]);
        }


        int availableNbCharacters = sizeRegEx;
        StringBuffer ret = new StringBuffer();
        while(availableNbCharacters > 0)
        {
            if( availableNbCharacters == 1)
            {
                double rnd = rand.nextDouble() * totalWeight;
                ret.append(map.ceilingEntry(rnd).getValue());
                //ret.append((char)ThreadLocalRandom.current().nextInt(FIRST_CHARACTER_ASCII, FINAL_CHARACTER_ASCII));
                availableNbCharacters -= 1;
            }
            else
            {
                if (availableNbCharacters > 3)
                { // It is possible to use a parenthesis. Min parenthesis reg ex is '(' 'CHAR' ')' '+|*'
                    ret.append((char)'(');
                    availableNbCharacters -= 3;

                    //System.out.println("available nb characters before add of parenthesis: " + availableNbCharacters);
                    String insideParenthesis = generateRegExInsideParenthesis(ThreadLocalRandom.current().nextInt(1, min(LENGTH_INSIDE_PARENTHESIS_MAX,availableNbCharacters+1)));
                    availableNbCharacters -= insideParenthesis.length();
                    ret.append(insideParenthesis);
                    ret.append((char)')');
                    ret.append((char)'*');
                    //System.out.println("available nb characters after : " + availableNbCharacters);
                }
                else {
                    //System.out.println("inside else");
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
        Set<Character> lettersUsed = new HashSet();
        double rnd;
        char letter ;
        int ascii = 0;
        int sizeUsed = 0;
        StringBuffer ret = new StringBuffer();
        if (availableLength == 1) {
            ascii = ThreadLocalRandom.current().nextInt(FIRST_CHARACTER_ASCII, FINAL_CHARACTER_ASCII);
            ret.append((char) ascii);
            return ret.toString();
        }
        int randInt = ThreadLocalRandom.current().nextInt(1,availableLength+1);

        //System.out.println("my length inside parenthesis"+randInt);


        if (randInt == 1) {
            // Generating only one character
            // Finding an ascii character
            //ascii = ThreadLocalRandom.current().nextInt(FIRST_CHARACTER_ASCII, FINAL_CHARACTER_ASCII);
            //ret.append((char) ascii);
             rnd = rand.nextDouble() * totalWeight;
            ret.append(map.ceilingEntry(rnd).getValue());
            return ret.toString();
         }
        ret.append((char) ThreadLocalRandom.current().nextInt(FIRST_CHARACTER_ASCII, FINAL_CHARACTER_ASCII));
        while (sizeUsed < randInt)
        {
            //ascii = ThreadLocalRandom.current().nextInt(FIRST_CHARACTER_ASCII, FINAL_CHARACTER_ASCII);
            sizeUsed ++;
            ret.append((char) '|');
            rnd = 0;
            do {
                 rnd = rand.nextDouble() * totalWeight;
                 letter = map.ceilingEntry(rnd).getValue();
            } while(lettersUsed.contains(letter));
            ret.append(map.ceilingEntry(rnd).getValue());

            //ret.append((char) ascii);
        }

        return ret.toString();
    }

    public static void generateFileText(int lengthText, String fileName)
    {
        double rnd;
        PrintWriter out = null;
        try {
            out = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(fileName, true)));
            for(int i=0; i<lengthText; i++)
            {
                rnd = rand.nextDouble() ;
                out.print(map.ceilingEntry(rnd).getValue());
            }
        } catch (Exception e)
        {
            System.err.println("Error during generating file");
        }
        finally {
            out.close();
        }
    }
}
