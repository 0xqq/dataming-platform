package pers.yhf.platform.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据挖掘常用操作
 * @author Yin
 *
 */
public class AlgorithmOperation {

	public static void main(String[] args) {

        

	}
	
	
	
	
	
	/**
	 * 获取数据集中去标签后的数据集
	 * @param path
	 * @param dim
	 * @return
	 * @throws IOException
	 */
	public static double[][] getDatas(String path) throws IOException{
		List<String> list = ReadOperation.read_csvFile(path); 
		int len = list.size();
		int dim = list.get(0).split(",").length-1;
		double[][] dataSet = new double[len][dim];
		 for(int i=0;i<len;i++){
		    for(int j=0;j<dim;j++){
			    dataSet[i][j] = Double.valueOf(list.get(i).split(",")[j]);
			 }
	     }
	     return dataSet;
	}
	
	 
	/**
	 * 获取数据集中的所有标签(整型)
	 * @param path 数据集路径
	 * @param dim 维特征向量
	 * @return
	 * @throws IOException
	 */
	public static int[] getLabels(String path) throws IOException{
		List<String> list = ReadOperation.read_csvFile(path);
		int len = list.size();
		int[] arr = new int[len];
		int dim = list.get(0).split(",").length-1;
		for(int i=0;i<len;i++){
			arr[i] = Integer.valueOf(list.get(i).split(",")[dim]);
		}
		return arr;
	}
	
	
	/**
	 * 获取数据集中的所有标签(字符串类型)
	 * @param path 数据集路径
	 * @param dim 维特征向量
	 * @return
	 * @throws IOException
	 */
	public static String[] getLabels2(String path) throws IOException{
		List<String> list = ReadOperation.read_csvFile(path);
		int len = list.size();
		String[] arr = new String[len];
		int dim = list.get(0).split(",").length-1;
		for(int i=0;i<len;i++){
			arr[i] = list.get(i).split(",")[dim];
		}
		return arr;
	}
	
	
	/**
	 * 取二维数组的第index列，形成一个row个元素的一维数组
	 * @param dataSet
	 * @return
	 */
	public static String[] getColArray(String[][] dataSet,int index){
		int row = dataSet.length; 
		String[] arr = new String[row];
		for(int i=0;i<row;i++){
			arr[i]=dataSet[i][index];
		}
		return arr;
	}
	
	
	public static String getMaxValue(Map<String,Double> map){
		Set<String> set = map.keySet();
		int len = set.size();
		String[] arr = new String[len];
		int index=0;
		for(String s:set){
			arr[index]=s;
			index++;
		}
		double[] value = new double[len];
		for(int i=0;i<len;i++){
			value[i]=map.get(arr[i]);
		}
		 
		double max = value[0];
	       int maxIndex=0;
	       for(int i=0;i<len;i++){
	    	   if(max<value[i]){
	    		   max=value[i];
	    		   maxIndex=i;
	    	   }
	       }
		 //System.out.println("最大为:"+max); 
		return arr[maxIndex];
	}

}
