package tests;
import utils.RegExStringGenerator;

public class TestRegExGenerator {
    public static void main(String arg[]) {
        String res = RegExStringGenerator.generateRegEx(20);
        assert (res.length() <= 20);
        System.out.println(res);
    }
}
