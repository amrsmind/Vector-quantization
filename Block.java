package vectorQuantization;

import java.util.ArrayList;
import java.util.Collections;

public class Block {
	ArrayList<ArrayList<Integer>> input = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<ArrayList<Integer>>> output = new ArrayList<ArrayList<ArrayList<Integer>>>();
	int width;
	int height;
	Block(){
		//
	}
	Block(ArrayList<ArrayList<Integer>> input,int width,int height){
		this.input = input;
		this.width = width;
		this.height = height;
		int inputwidth = input.get(0).size();
		int inputheight = input.size();
		int blocknumbers = (inputwidth / width) * (inputheight / height);
		//ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>(Collections.nCopies(height, new ArrayList<Integer>(Collections.nCopies(width, 0))));
		ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
		for(int i=0;i<height;i++) {
			temp.add(new ArrayList<Integer>(Collections.nCopies(width, 0)));
		}
		for(int i=0;i<inputheight;i+=height) {
			for(int j=0;j<inputwidth;j+=width){
				for(int k=0;k<height;k++) {
					for(int m=0;m<width;m++){
						//k+=i;
						//m+=j;
						temp.get(k).set(m, input.get(k+i).get(m+j));
						//System.out.print(temp.get(k).get(m) + "    ");
					}
				}
				ArrayList<ArrayList<Integer>> newtemp = new ArrayList<ArrayList<Integer>>(temp);
				output.add(newtemp);
				temp = new ArrayList<ArrayList<Integer>>(height);
				for(int n=0;n<height;n++) {
					temp.add(new ArrayList<Integer>(Collections.nCopies(width, 0)));
				}
			}
		}
		
	}
	public static ArrayList<ArrayList<Integer>> getaverage(ArrayList<ArrayList<ArrayList<Integer>>> a) {
		//System.out.println(a.size() + "   "+a.get(0).get(0).size());
		ArrayList<ArrayList<Integer>> averages = new ArrayList<ArrayList<Integer>>(a.get(0).size());
		for(int i=0;i<a.get(0).size();i++) {
			averages.add(new ArrayList<Integer>(Collections.nCopies(a.get(0).get(0).size(), 0)));
		}
		
		for(int i=0;i<a.size();i++) {
			for(int j=0;j<a.get(0).size();j++) {
				for(int k=0;k<a.get(0).get(0).size();k++){
					averages.get(j).set(k, averages.get(j).get(k)+a.get(i).get(j).get(k));
				}
			}
		}
		for(int i=0;i<averages.size();i++) {
			for(int j=0;j<averages.get(0).size();j++) {
				averages.get(i).set(j, averages.get(i).get(j)/a.size());
			}
		}
	return averages;	
	}
	public static ArrayList<ArrayList<Integer>> getleft(ArrayList<ArrayList<Integer>> a){
		//ArrayList<ArrayList<Integer>> leftmat = new ArrayList<ArrayList<Integer>>(Collections.nCopies(a.size(),new ArrayList<Integer>(Collections.nCopies(a.get(0).size(), 0)))); 
		ArrayList<ArrayList<Integer>> leftmat = new ArrayList<ArrayList<Integer>>(a.size());
		for(int i=0;i<a.size();i++) {
			leftmat.add(new ArrayList<Integer>(Collections.nCopies(a.get(0).size(), 0)));
		}
		for(int i=0;i<a.size();i++) {
			for(int j=0;j<a.get(0).size();j++){
				leftmat.get(i).set(j, a.get(i).get(j)-1);
			}
		}
		return leftmat;
	}
	public static ArrayList<ArrayList<Integer>> getright(ArrayList<ArrayList<Integer>> a){
		//ArrayList<ArrayList<Integer>> rightmat = new ArrayList<ArrayList<Integer>>(Collections.nCopies(a.size(),new ArrayList<Integer>(Collections.nCopies(a.get(0).size(), 0)))); 
		ArrayList<ArrayList<Integer>> rightmat = new ArrayList<ArrayList<Integer>>(a.size());
		for(int i=0;i<a.size();i++) {
			rightmat.add(new ArrayList<Integer>(Collections.nCopies(a.get(0).size(), 0)));
		}
			
		
		for(int i=0;i<a.size();i++) {
			for(int j=0;j<a.get(0).size();j++){
				rightmat.get(i).set(j, a.get(i).get(j)+1);
			}
		}
		return rightmat;
	}
	public ArrayList<ArrayList<ArrayList<Integer>>> getnearestvectorleft(ArrayList<ArrayList<Integer>> left,ArrayList<ArrayList<Integer>> right){
		ArrayList<ArrayList<ArrayList<Integer>>> nearestvectors = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(ArrayList<ArrayList<Integer>> everymat:output){
			if(getdistance(left,everymat) < getdistance(right,everymat)){
				nearestvectors.add(everymat);
			}
		}
		return nearestvectors;
	}
	public ArrayList<ArrayList<ArrayList<Integer>>> getnearestvectorright(ArrayList<ArrayList<Integer>> left,ArrayList<ArrayList<Integer>> right){
		ArrayList<ArrayList<ArrayList<Integer>>> nearestvectors = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(ArrayList<ArrayList<Integer>> everymat:output){
			if(getdistance(left,everymat) >= getdistance(right,everymat)){
				nearestvectors.add(everymat);
			}
		}
		return nearestvectors;
	}
	public int getdistance(ArrayList<ArrayList<Integer>> a1,ArrayList<ArrayList<Integer>> a2){
		int sum = 0;
		for(int i=0;i<a1.size();i++) {
			for(int j=0;j<a1.get(0).size();j++){
				sum += Math.abs(a1.get(i).get(j) - a2.get(i).get(j));
			}
		}
		return sum;
	}
	public ArrayList<ArrayList<ArrayList<Integer>>> associate(ArrayList<ArrayList<ArrayList<Integer>>> avgs, int i){
		int minindex = 0;
		ArrayList<ArrayList<ArrayList<Integer>>> nearvectors = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(ArrayList<ArrayList<Integer>> everymat:output){
			int min = getdistance(avgs.get(0),everymat);
			for(int j=0;j<avgs.size();j++){
				if(getdistance(avgs.get(j),everymat)<min){
					minindex = j;
					min = getdistance(avgs.get(j),everymat);
				}
			}
			if(minindex == i){
				nearvectors.add(everymat);
			}
		}
		if(nearvectors.isEmpty()) {
			ArrayList<ArrayList<ArrayList<Integer>>> temp = new ArrayList<ArrayList<ArrayList<Integer>>>();
			temp.add(avgs.get(i));
			return temp;
		}
		return nearvectors;
	}
	public ArrayList<Integer> finalassociate(ArrayList<ArrayList<ArrayList<Integer>>> avgs){
		int minindex = 0;
		ArrayList<Integer> compressedimg = new ArrayList<Integer>();
		for(ArrayList<ArrayList<Integer>> everymat:output){
			int min = getdistance(avgs.get(0),everymat);
			for(int j=0;j<avgs.size();j++){
				if(getdistance(avgs.get(j),everymat)<min){
					minindex = j;
					min = getdistance(avgs.get(j),everymat);
				}
			}
			compressedimg.add(minindex);
			}
		
		return compressedimg;
	}

	
	public static boolean compare(ArrayList<ArrayList<ArrayList<Integer>>> c1,ArrayList<ArrayList<ArrayList<Integer>>> c2) {
		for(int i=0;i<c1.size();i++){
			if(!(compare2d(c1.get(i),c2.get(i)))) {
				return false;
			}
		}
		return true;
	}
	public static boolean compare2d(ArrayList<ArrayList<Integer>> c1,ArrayList<ArrayList<Integer>> c2) {
		for(int i=0;i<c1.size();i++) {
			for(int j=0;j<c1.get(0).size();j++) {
				if(c1.get(i).get(j) != c2.get(i).get(j)) {
					return false;
				}
			}
		}
		return true;
	}
	public static ArrayList<ArrayList<Integer>> listto2d(ArrayList<Integer> a,int width){
		ArrayList<ArrayList<Integer>> array2d = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> templist = new ArrayList<Integer>(width);
	    for(int i=0;i<a.size();i+=width) {
            for(int j=0;j<width;j++) {
            	templist.add(a.get(i+j));
            }
            array2d.add(templist);
            templist = new ArrayList<Integer>(width); 
	    }
	    return array2d;
	}
	public static ArrayList<ArrayList<ArrayList<Integer>>> returnoriginal(ArrayList<ArrayList<ArrayList<Integer>>> codebook,ArrayList<Integer> compressedimglist){
		ArrayList<ArrayList<ArrayList<Integer>>> originalimglist = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for(int i=0;i<compressedimglist.size();i++) {
			ArrayList<ArrayList<Integer>> temp2d= codebook.get(compressedimglist.get(i));
			originalimglist.add(temp2d);
		}
		return originalimglist;
	}
	public static ArrayList<ArrayList<Integer>> tomat(ArrayList<ArrayList<ArrayList<Integer>>> list,int blocknumsinrow){
		/*for(int i=0;i<list.size();i+=width) {
			for(int j=0;j<width;j++) {
				ArrayList<ArrayList<Integer>> temp = 
			}
		}*/
		int blockwidth = list.get(0).get(0).size();
		int blockheight = list.get(0).size();
		int blocknumsincol = list.size()/blocknumsinrow;
		ArrayList<ArrayList<Integer>> mat = new ArrayList<ArrayList<Integer>>(blockheight*blocknumsincol);
		for(int i=0;i<blockheight*blocknumsincol;i++) {
			mat.add(new ArrayList<Integer>(Collections.nCopies(blockwidth*blocknumsinrow, 0)));
		}
		int counter = 0;
		for(int h=0;h<blocknumsincol;h++) {
		for(int i=0;i<blocknumsinrow;i++){
			for(int j=h*blockheight;j<blockheight*(h+1);j++) {
				for(int k=i*blockwidth;k<blockwidth*(i+1);k++){
					mat.get(j).set(k, list.get(counter).get(j-(h*blockheight)).get(k-(i*blockwidth)));
				}
		       }
			counter++;
		      }
		     }
		
		return mat;
	}
	
	public static void printmat(ArrayList<ArrayList<Integer>> a){
           for(ArrayList<Integer> list:a) {
        	   for(int x:list) {
        		   System.out.print(x + "  ");
        	   }
        	   System.out.println();
           }
	}
	public static void printmatlist(ArrayList<ArrayList<ArrayList<Integer>>> a) {
		for(ArrayList<ArrayList<Integer>> everymat:a) {
			Block.printmat(everymat);
			System.out.println("-------------------------------");
		}
	}
	
		
		

}
