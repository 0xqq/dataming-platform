package pers.yhf.platform.basicalgorithm.classification;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pers.yhf.platform.utils.AlgorithmOperation;
import pers.yhf.platform.utils.ReadOperation;
import pers.yhf.platform.utils.ShowOperations;

/**
 * 朴素贝叶斯算法
 * @author Yin
 *
 */
public class NaiveBayes {

	public static void main(String[] args) throws IOException {  

		String path= "C:\\Users\\Yin\\Desktop\\balance-scale2.csv";
		//System.out.println(getProbabilityFromLabel(path,"B"));
		//String[] str = getAllLabels(path);
		//ShowOperations.printArr(str); 
		 
		//double[][] trainSet = AlgorithmOperation.getDatas(path);
		 //getProbabilityOnlabel(path,"R"); 
		
		//标签转化为数值
		//transferToNumFromLabel(path);
		
		String path2= "C:\\Users\\Yin\\Desktop\\balance-scale3.csv"; 
		//String[][] trainSet2 = ReadOperation.read_csvFile2(path2);
		 
		predict(path,path2);
		
	}
	
	
	
	//------------------------------------算法区域--------------------------------------//
	
	
	public static void predict(String path,String testPath) throws IOException{
		String[][] testSet = ReadOperation.read_csvFile2(testPath);
		String[][] trainSet =  ReadOperation.read_csvFile2(path);
		int row = testSet.length;
		int col = testSet[0].length;
		String predictPro = null;
		int count = 0;
		for(int i=0;i<row;i++){
			predictPro = predictEachTestSample(path,testSet[i]);
			System.out.println("实际标签："+testSet[i][col-1]); 
			System.out.println(); 
			if(predictPro.equals(testSet[i][col-1])){
				count++;
			}
			predictPro = null;
		}
		System.out.println("count="+count); 
		System.out.println("测试集预测准确率： "+count/(double)row); 
	}
	
	/**
	 * 对于给定测试样本(带真实标签) 预测标签
	 * @param path
	 * @param testArr
	 * @return
	 * @throws IOException 
	 */
	    public static String predictEachTestSample(String path,String[] testArr) throws IOException{
	    	String[][] dataSet =  ReadOperation.read_csvFile2(path); 
		    int row = dataSet.length;
		    int col = dataSet[0].length;
		    String[] labels = AlgorithmOperation.getLabels2(path); 
		    Map<String,Double> map = new HashMap<String,Double>();
		    Set<String> labelSet = new HashSet<String>(); 
	    	 for(String lab:labels){
	    		 labelSet.add(lab);
	    	 }
	    	 for(String lab:labelSet){ 
	    		 map.put(lab, getProbabilityOnlabel(path,lab,testArr));
	    	 }
	    	 System.out.println(map); 
		    
	    	 String lab = AlgorithmOperation.getMaxValue(map);
	    	 System.out.println("该测试样本预测标签：  "+lab);  
	    	 return lab;
	    }
	
	
	    /**
	     * 获取标签列表(非重复)
	     * @param path
	     * @return
	     * @throws IOException
	     */
	    public static String[] getAllLabels(String path) throws IOException{
	    	String[] labels = AlgorithmOperation.getLabels2(path);
	    	Set<String> labelSet = new HashSet<String>(); 
	    	 for(String lab:labels){
	    		 labelSet.add(lab);
	    	 }
	    	 int len = labelSet.size();
	    	 String[] labelStr = labelSet.toArray(new String[len]);
	    	 return labelStr;
	    }
	
	    
	    
	    /**
	     * 计算某个标签的概率
	     * @param label
	     * @return
	     * @throws IOException 
	     */
	    public static double getProbabilityFromLabel(String path,String label) throws IOException{
	    	String[] labels = getAllLabels(path);
	    	int len = labels.length;
	        for(String lab:labels){
	    		if(lab.equals(label))return 1/(double)len;
	    	}
	    	return 0.0; 
	    }
	    
	     
	    /**
	     * 计算某个标签的出现的次数
	     * @param label
	     * @return
	     * @throws IOException 
	     */
	    public static int getNumFromLabel(String path,String label) throws IOException{
	    	String[] labels = AlgorithmOperation.getLabels2(path); 
	    	int count=0;
	        for(String lab:labels){
	    		if(label.equals(lab)) count++;
	    	}
	    	return count; 
	    }
	    
	    
	    
	    
	    
	    
	    
	    /**
	     * 获取数据集中分类为给定标签的所有样本的数量
	     * @param path
	     * @param label
	     * @return
	     * @throws IOException 
	     */
	    public static int getTrainNumFromLabel(String path,String label) throws IOException{ 
	    	String[] labels = AlgorithmOperation.getLabels2(path);   
	    	int count = 0;
	    	for(String lab:labels){
	    		if(label.equals(lab)) count++;
	    	}
	    	return count;
	    } 
	    
	    
	    /**
	     * 对于给定测试样本以及给定标签y, 计算 P(ai|y)的连续乘积
	     * @param path    
	     * @param label    给定标签
	     * @param testArr  测试样本
	     * @return
	     * @throws IOException 
	     */
	   public static double getProbabilityOnlabel(String path,String label,String[] testArr) throws IOException{
		    int len = testArr.length;
		    String[][] dataSet =  ReadOperation.read_csvFile2(path);
		    int row = dataSet.length;
		    int col = dataSet[0].length;
		    double result = 1.0;
		    int labelNum = getTrainNumFromLabel(path,label);
		    // System.out.println("labelNum="+labelNum); 
		    int count = 0;  
		   if(len==col){
			  for(int j=0;j<col-1;j++){
			      for(int i=0;i<row;i++){ 
					if(testArr[j].equals(dataSet[i][j]) && label.equals(dataSet[i][col-1])){
						  count++;
					  }
			    	 }
			      if( (count/(double)labelNum)==0){
			    	 //拉普拉斯修正
			    	 result *= laplace(path,label,j,count);
			      }
			      else{
			    	  result *= count/(double)labelNum;
			      }
			   count=0;
			    } 
			  return result;
		   }//if
		    return 0.0;
		 }
	   
	   
	   /**
	    * 拉普拉斯校正
	    * 训练集上，很多样本的取值可能并不在其中，但是这不并代表这种情况发生的概率为0，因为未被观测到，并不代表出现的概率为0
	    * @param path
	    * @param index   第index列
	    * @param num 标签label下  第index个特征值出现次数
	    * @return
	 * @throws IOException 
	    */
	   public static double laplace(String path,String label,int index,int num) throws IOException{
		   String[][] dataSet =  ReadOperation.read_csvFile2(path); 
		    //标签label出现的次数
		   int labelNum = getNumFromLabel(path,label);  
		   int row = dataSet.length;  
	       String[] traitArr = AlgorithmOperation.getColArray(dataSet, index);
	       Set<String> traitSet = new HashSet<String>();
	       for(String trait:traitArr){
	    	   traitSet.add(trait);
	       }
	       //获取第index列的特征值的可能取值数量
	       int traitValueNum = traitSet.size();
	       double label_probability = (labelNum+1)/(double)(row+traitValueNum);
	        //System.out.println("laplace: "+label_probability); 
	       double result = (num+1)/(double)(labelNum+traitValueNum);
	       return result;
	   }
	    
	   
//----------------------------------------------------------------------------------------//
	   
	   /**
	     * 将String类型标签转化为Integer值   其中   B:0     R:1      L:2
	     * @param path
	     * @param label
	     * @throws IOException 
	     */
	    public static void transferToNumFromLabel(String path) throws IOException{
	    	String[][] dataSet =  ReadOperation.read_csvFile2(path);
	    	int row = dataSet.length;
	    	  System.out.println("row="+row); 
	    	int col = dataSet[0].length;
	    	double[][] arr = new double[row][col];
	    	for(int i=0;i<row;i++){
	    		for(int j=0;j<col-1;j++){
	    			arr[i][j] = Double.valueOf(dataSet[i][j]);
	    			if("B".equals(dataSet[i][col-1])){
	    				arr[i][col-1]= 0;
	    			}
	    			else if("R".equals(dataSet[i][col-1])){
	    				arr[i][col-1]= 1;
	    			}
	    			else if("L".equals(dataSet[i][col-1])){
	    				arr[i][col-1]= 2;
	    			}
	    		}
	    	}
	    }
	    
	    

}
