package vectorQuantization;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

public class Encode {
	ArrayList<ArrayList<Integer>> imgmat;	
	int vectorsizewidth;
	int vectorsizeheight;
	int vectornumbers;
	ArrayList<ArrayList<ArrayList<Integer>>> averages;
	Block b;
	ArrayList<ArrayList<Integer>> compressedimgmat;
	Encode(){
		//
	}
	Encode(ArrayList<ArrayList<Integer>> imgmat,int vectorsizewidth,int vectorsizeheight,int vectornumbers){
		this.imgmat = imgmat;
		this.vectorsizeheight = vectorsizeheight;
		this.vectorsizewidth = vectorsizewidth; 
		this.vectornumbers = vectornumbers;
		averages = new ArrayList<ArrayList<ArrayList<Integer>>>();
		b = new Block(imgmat,vectorsizewidth,vectorsizeheight);
	}
	Encode(String filepath,int vectorsizewidth,int vectorsizeheight,int vectornumbers){
		imgmat = readImage(filepath);
		this.vectorsizeheight = vectorsizeheight;
		this.vectorsizewidth = vectorsizewidth; 
		this.vectornumbers = vectornumbers;
		averages = new ArrayList<ArrayList<ArrayList<Integer>>>();
		b = new Block(imgmat,vectorsizewidth,vectorsizeheight);
	}
	public ArrayList<ArrayList<Integer>> readImage(String filePath){
		
		File f = new File(filePath); //image file path
		
		//int[][] imageMAtrix=null;
		
		try {
			BufferedImage img= ImageIO.read(f);
			int width = img.getWidth(); 
	        int height = img.getHeight(); 
	        //imgmat = new ArrayList<ArrayList<Integer>>(Collections.nCopies(height, new ArrayList<Integer>(Collections.nCopies(width, 0))));
	        imgmat = new ArrayList<ArrayList<Integer>>(height);
	        for(int i=0;i<height;i++) {
	        	imgmat.add(new ArrayList<Integer>(Collections.nCopies(width, 0)));
	        }
	        for (int y = 0; y < height; y++) 
	        { 
	            for (int x = 0; x < width; x++) 
	            { 
	                int p = img.getRGB(x,y); 
	                int a = (p>>24)&0xff; 
	                int r = (p>>16)&0xff; 
	                int g = (p>>8)&0xff; 
	                int b = p&0xff; 
	  
	                //because in gray image r=g=b  we will select r  
	               
	                imgmat.get(y).set(x, r);
	                
	                //set new RGB value 
	                p = (a<<24) | (r<<16) | (g<<8) | b; 
	                img.setRGB(x, y, p); 
	            } 
	        } 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return imgmat;
	}
	public void writeImage(ArrayList<ArrayList<Integer>> imageMatrix,String imageoutPath){

		int height=imageMatrix.size();
		int width=imageMatrix.get(0).size();
		BufferedImage img=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);

		 for (int y = 0; y < height; y++)
	        {
	            for (int x = 0; x < width; x++){

	            	int a=255;
	            	int pix=	imageMatrix.get(y).get(x);
	            	int p=  (a<<24)	| (pix<<16) | (pix<<8) | pix;

	            	img.setRGB(x, y, p);

	            }
	        }

		 File f = new File(imageoutPath);

		try {
			ImageIO.write(img, "jpg", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public ArrayList<ArrayList<Integer>> getaverage(ArrayList<ArrayList<Integer>> mat) {
		int counterwidth = mat.get(0).size() / vectorsizewidth ;
		int counterheight = mat.size() / vectorsizeheight;
		int blocknumbers = counterwidth * counterheight;
		int sum = 0;
		int width = mat.get(0).size();
		int height = mat.size();
		ArrayList<ArrayList<Integer>> avgsmat = new ArrayList<ArrayList<Integer>>(vectorsizeheight);
		for(int krow = 0;krow<vectorsizewidth;krow++) {
			for(int kcol = 0;kcol<vectorsizeheight;kcol++){
		for(int i=0;i<height;i+=vectorsizeheight) {
			for(int j=0;j<width;j+=vectorsizewidth) {
				j+=kcol;
				i+=krow;
				sum += mat.get(i).get(j);
			}
		}
		avgsmat.get(krow).set(kcol, sum/blocknumbers);
		sum=0;
		}
		}
		return avgsmat;
	}*/
	/*public void quantize(){
		quantize1(b.output,0);
	}
	
	public void quantize1(ArrayList<ArrayList<ArrayList<Integer>>> a,int counter){
		ArrayList<ArrayList<Integer>> average = Block.getaverage(a);
		if(counter==(Math.log(vectornumbers)/Math.log(2))) {
			averages.add(average);
		}
		ArrayList<ArrayList<Integer>> left = Block.getleft(average);
		ArrayList<ArrayList<Integer>> right = Block.getright(average);
		
		ArrayList<ArrayList<ArrayList<Integer>>> nearestleft = b.getnearestvectorleft(left, right);
		ArrayList<ArrayList<ArrayList<Integer>>> nearestright = b.getnearestvectorright(left, right);
		
		quantize1(nearestleft,counter+1);
		quantize1(nearestright,counter+1);
	}
	public void quantize2(){
		
	}*/
	
	public void quantize(String outputpath){
		quantize1();
		quantize2();
		writeImage(compressedimgmat,outputpath);
	}
	public void quantize1(){
		ArrayList<ArrayList<ArrayList<Integer>>> templist = new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<ArrayList<ArrayList<Integer>>> tempfortemplist = new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<ArrayList<Integer>> average = Block.getaverage(b.output);
        templist.add(Block.getleft(average));
        templist.add(Block.getright(average));
        while(true) {
        	   for(int i = 0;i<templist.size();i++) {
        		   //Block.printmatlist(templist);System.out.println("()()()()()()");
        		   ArrayList<ArrayList<ArrayList<Integer>>> nearvectors = b.associate(templist, i);
        		   //Block.printmatlist(nearvectors);System.out.println("()()()()()()");
        		   //System.out.println(nearvectors.size());
        		   average = Block.getaverage(nearvectors);
        		   tempfortemplist.add(Block.getleft(average));
        		   tempfortemplist.add(Block.getright(average));                   

        	   }
   	        if(tempfortemplist.size()==vectornumbers){
   	         templist = tempfortemplist;
	        	break;
	        }
        	   templist = new ArrayList<ArrayList<ArrayList<Integer>>>(tempfortemplist);
        	   tempfortemplist.clear();
        }
        	int counter = 0;   
       		ArrayList<ArrayList<ArrayList<Integer>>> newtemplist = new ArrayList<ArrayList<ArrayList<Integer>>>();
       		for(int i = 0;i<templist.size();i++) {
       		  ArrayList<ArrayList<ArrayList<Integer>>> nearvectors = b.associate(templist, i);
        	  average = Block.getaverage(nearvectors);
        	  newtemplist.add(average);
        	  if(i==templist.size()-1){
        		  if(Block.compare(templist,newtemplist)) {
        			  break;
        		  }
        		  else {
        			  System.out.println(counter);
                     counter++;
        			  if(counter++>100) {
        				  templist = newtemplist;
        				  break;
        			  }
        			  i=-1;
        			  templist = new ArrayList<ArrayList<ArrayList<Integer>>>(newtemplist);
        			  newtemplist.clear();
        		  }
        	  }
     	   }
     	   averages = templist;
	}
	public void quantize2(){
		ArrayList<Integer> compressedimglist = b.finalassociate(averages);
		ArrayList<ArrayList<ArrayList<Integer>>> originalimglist = Block.returnoriginal(averages, compressedimglist);
        compressedimgmat = Block.tomat(originalimglist, imgmat.get(0).size()/vectorsizewidth);
	}

}
