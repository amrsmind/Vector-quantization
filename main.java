package vectorQuantization;

import java.util.ArrayList;
import java.util.Arrays;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Encode e = new Encode("C:\\Users\\amr\\eclipse-workspace\\vectorQuantization\\messi.jpg",5,5,32);
		e.quantize("C:\\Users\\amr\\eclipse-workspace\\vectorQuantization\\tigercompressed1.jpg");
	   
	    /* ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
	      a.add(new ArrayList<Integer>(Arrays.asList(1,2,7,9,4,11)));
	      a.add(new ArrayList<Integer>(Arrays.asList(3,4,6,6,12,12)));
	      a.add(new ArrayList<Integer>(Arrays.asList(4,9,15,14,9,9)));
	      a.add(new ArrayList<Integer>(Arrays.asList(10,10,20,18,8,8)));
	      a.add(new ArrayList<Integer>(Arrays.asList(4,3,17,16,1,4)));
	      a.add(new ArrayList<Integer>(Arrays.asList(4,5,18,18,5,6)));
         Encode e = new Encode(a,2,2,4);
         e.quantize("C:\\Users\\amr\\eclipse-workspace\\vectorQuantization\\tigercompressed.jpg");
	*/
	
	}
 
}
