package tests;
import utils.RegExStringGenerator;

import java.io.File;

public class TestRegExGenerator {
    public static void main(String arg[]) throws Exception {//text from which we are searching , length of regex
        if (arg.length < 2)
            throw new Exception("please give 2 arguments (File, length of regular expression)");
        File f = new File(arg[0]);
        if(!f.exists() || f.isDirectory())
            throw new Exception("first argument must be an existing file");


        try {
            int lengthRegEx = Integer.parseInt(arg[1]);
            String res = RegExStringGenerator.generateRegEx(lengthRegEx);
            System.out.println(res);
            long start_search = System.currentTimeMillis();

            long end_search = System.currentTimeMillis();
            // Building automata determinisation optimization search

        } catch (NumberFormatException nfe) {
            throw new Exception("second argument must be an integer (the length of regex generated)");
        }
    }
}
