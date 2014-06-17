import java.util.*;

/**
 * A round-about way of finding duplicates in a given list of integers.
 * Clearly not the best way, esp when you can harness the Set data structure's 
 * uniqueness to achieve this. Just a trial though.
 */
public class FindDuplicates {
    public static Collection<Integer> findDuplicates(Collection<Integer> input) {
        List<Integer> list = new ArrayList<Integer>(input);
        Collections.sort(list);
        Collection<Integer> output = new ArrayList<Integer>();
        Iterator<Integer> iter = list.iterator();
        Integer i = iter.next();
        while (iter.hasNext()) {
            Integer next = iter.next();
            if (i==next) {
                if (!output.contains(i)) {
                    output.add(i);
                }
            } else {
                i = next;
            }
        }
        return output;
    }
    
    public static void main (String args[]) {
        FindDuplicates obj = new FindDuplicates();
        Collection<Integer> input = new ArrayList<Integer>();
        input.add(3);
        input.add(2);
        input.add(3);
        input.add(7);
        input.add(2);
        input.add(8);
        input.add(1);
        input.add(8);
        input.add(2);
        input.add(6);
        
        Collection<Integer> output = obj.findDuplicates(input);
        
        System.out.println("Input Numbers : ");
        for (Integer i : input) {
            System.out.println (i);
        }
        System.out.println("Repeated Numbers : ");
        for (Integer i : output) {
            System.out.println (i);
        }
        
    }
}
