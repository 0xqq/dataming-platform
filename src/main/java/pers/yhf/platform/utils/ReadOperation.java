package pers.yhf.platform.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader; 
import java.util.ArrayList;
import java.util.List;

public class ReadOperation {

	public static void main(String[] args) throws IOException{ 
		/*String path = "file/data.csv";
		System.out.println(read_csvFile(path));
		
		String str = "-0.017612,14.053064,0"; 
		String str2 = str.split(",")[2];
		System.out.println(str2); */
		
		String path= "C:\\Users\\Yin\\Desktop\\balance-scale2.csv";
		String[][] arr = read_csvFile2(path);
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[0].length;j++){
				System.out.print(arr[i][j]+"  ");
			}
			System.out.println();
		}
		
	}
	
	public static final String getLabel(String line){
		String[] arr = line.split(",");
		int len = arr.length;
		return arr[len-1];
	}
	
	public static final List<String> read_csvFile(String filePath) throws IOException {    
		List<String> list = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(
        new FileInputStream(filePath)));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
        	list.add(line);
             //System.out.println(line+"   标记： "+getLabel(line));  
         }
        br.close();
        return list;
    }
	
	
	public static final String[][] read_csvFile2(String filePath) throws IOException{
		 List<String> list = ReadOperation.read_csvFile(filePath); 
		 int row = list.size();
		 int col = list.get(0).split(",").length;
		  //System.out.println(col); 
		 String[][] trainSet = new String[row][col];
		 for(int i=0;i<row;i++){
			 for(int j=0;j<col;j++){
				 trainSet[i][j] = list.get(i).split(",")[j];
			 }
		 }
		 return trainSet;
	}
	
	
	

}
