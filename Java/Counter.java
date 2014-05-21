
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Find the longest contiguous mid-string sequence of 1s in a given input 
 * string made of 0s and 1s. The sequence has to be wrapped by a 0 on either side 
 * i.e, sequence of 1s at the start or end of the string don't qualify.
 * If more than one sequence of same length exists, the first one is listed.
 * 
 * @author Chocka
 * 
 */
public class Counter {
    public static void main(String args[]) throws IOException {
        Counter counter = new Counter();
        // first process inputs through command line args
        for (String input : args) {
            counter.count(input);
        }
        // then read input from stdin
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        while (true) {
            System.out.println("\nEnter the string (or 'EXIT' to terminate) : ");
            input = br.readLine();
            if ("exit".equalsIgnoreCase(input))
                break;
            counter.count(input);
        }
    }

    private void count(String input) {
        if (!input.matches("^[01]+$")) {
            System.out.println("Invalid input : " + input);
            return;
        }

        int count = -1, result = 0, start = 0, end = 0;
        for (int j = 0; j < input.length(); j++) {
            char c = input.charAt(j);
            switch (c) {
            case '0':
                if (count > result) {
                    result = count;
                    end = j - 1;
                    start = j - result;
                }
                count = 0;
                break;
            case '1':
                if (count >= 0) {
                    count++;
                }
                break;
            }
        }

        System.out.println("Input String : " + input);
        System.out.println("Length of longest mid-string sequence of 1s : " + result);
        System.out.println("Sequence Start : " + start + "     Sequence End : " + end);
        System.out.println("");
    }
}
