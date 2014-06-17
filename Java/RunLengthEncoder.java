/**
 * Encode the given string by replacing repeated characters with a single instance 
 * of that character followed by the number of repetitions.
 * eg: ddddffffvvvg should give d4f4v3g1
 */
public class RunLengthEncoder {
    public String encode (String input) {
    	if (input == null) {
            return null;
        }
        if (input.length() == 0) { 
        	return ""; 
        }
        StringBuffer outBuf = new StringBuffer();
        char c = input.charAt(0);
        int count = 1;
        for (int i=1; i<input.length(); i++) {
            char cur = input.charAt(i);
            if (c == cur) {
                count++;
            } else {
                outBuf.append(c);
                outBuf.append(count);
                c = cur;
                count = 1;
            }
        }
        outBuf.append(c);
        outBuf.append(count);
        return outBuf.toString();
    }
    
    public static void main(String args[]) {
        RunLengthEncoder encoder = new RunLengthEncoder();

        String test = "ddddffffvvvg";
        String output = encoder.encode(test);
        System.out.println("Input String : " + test);
        System.out.println("Encoded String : " + output);

        test = "dddd";
        output = encoder.encode(test);
        System.out.println("Input String : " + test);
        System.out.println("Encoded String : " + output);

        test = "d";
        output = encoder.encode(test);
        System.out.println("Input String : " + test);
        System.out.println("Encoded String : " + output);

        test = "";
        output = encoder.encode(test);
        System.out.println("Input String : " + test);
        System.out.println("Encoded String : " + output);

        test = null;
        output = encoder.encode(test);
        System.out.println("Input String : " + test);
        System.out.println("Encoded String : " + output);
    }
}
