public class SequenceFinder {
   public void find (int[][] matrix, int low, int high) {
       int size = matrix[0].length;
       int curr = low;
       int start = low;
       int maxLength = 0;
       int i=0,j=0;

       do {
    	   boolean found = false;
    	   for (i=0; i<size; i++) {
               for (j=0; j<size; j++) {
                   if (matrix[i][j] == curr) {found = true; break;}
               }
               if (found) break;
           }
    	   if (!found) { // error - missing number in the sequence
    		   System.out.println("Invalid input matrix (Out of sequence). No " + curr + " cannot be found.");
    		   return;
    	   }

    	   // (i,j) (i-1,j) (i+1,j) (i,j-1) (i,j+1)
           int length = explore(matrix, i, j, curr);
           if (length > maxLength) {
        	   start = curr; maxLength = length;
           }
           
           if (curr + length <= high) {
        	   curr = curr + length;
           } else {
        	   break;
           }
       } while(true);
       
       // start - beginning, maxLength - length of sequence
       for (i=0; i<maxLength; i++) {
    	   System.out.print(start+i + " ");
       }
   }

   public int explore(int [][] matrix, int i, int j, int val) {
       int length = 1;
       if (i>0 && matrix[i-1][j]==val+1)
           length += explore (matrix, i-1, j, val+1);
       if (i<matrix[0].length-1 && matrix[i+1][j]==val+1)
           length += explore (matrix, i+1, j, val+1);
       if (j>0 && matrix[i][j-1]==val+1)
           length += explore (matrix, i, j-1, val+1);
       if (j<matrix[0].length-1 && matrix[i][j+1]==val+1)
           length += explore (matrix, i, j+1, val+1);
       return length;
   }

   public static void main (String args[]) {
       SequenceFinder finder = new SequenceFinder();
       int [][] input = {{1,2,9}, {5,3,8}, {4,6,7}};
       finder.find(input, 1, 9);
   }
}
