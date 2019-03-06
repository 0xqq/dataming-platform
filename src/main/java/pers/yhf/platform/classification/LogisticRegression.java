package pers.yhf.platform.classification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pers.yhf.platform.utils.AlgorithmOperation;
import pers.yhf.platform.utils.ReadOperation;
 

 
/**
 * 逻辑回归算法
 * https://www.cnblogs.com/pinard/p/5970503.html
 * 
 * @author Yin
 *
 */
public class LogisticRegression {
	 
	public static void main(String[] args) throws IOException { 
		 
		String path = "C:\\Users\\Yin\\Desktop\\TRAFFICDATA_TRAIN.csv";
		String testDataPath = "C:\\Users\\Yin\\Desktop\\TRAFFICDATA_TEST.csv";
		
		double learningRate = 0.002;
		int maxIterator = 300;
	 
		int[] labels = AlgorithmOperation.getLabels(path);
		double[][] dataSet = AlgorithmOperation.getDatas(path);
		
		predict(testDataPath,dataSet,labels,learningRate, maxIterator, 2); 
	}
	
	
	/**
	 * 预测
	 * @param testDataPath
	 * @throws IOException 
	 */
	public static void predict(String testDataPath,double[][] dataSet,int[] labels,double learningRate, int maxIterator, int algorithm) throws IOException{
		List<String> list = ReadOperation.read_csvFile(testDataPath);
		int len = list.size();
		int dim = list.get(0).split(",").length-1;
		int[] haveLabels = AlgorithmOperation.getLabels(testDataPath);
		List<Double> paramList = train(dataSet, labels,learningRate, maxIterator, algorithm);
		 int correct = 0;
		//读取测试数据
		double[][] testDataSet = AlgorithmOperation.getDatas(testDataPath);
		int len2 = testDataSet.length;
		int tmp = 0;
		double result = 0;
		for(int i=0;i<len2;i++){
		   for(int j=0;j<dim;j++){
			  tmp += testDataSet[i][j]*paramList.get(j);	
		    }
		   result = 0;
		   result = sigmoid(tmp);
		   if((result>0.5 && haveLabels[i]==1)||(result<=0.5 && haveLabels[i]==0)){
			   correct++;  //result>0.5时  结果分类为1   否则结果分类为0
			 } 
		   System.out.print("预测标签值："+result+"  "); 
		 }
		System.out.println();
		System.out.println("分类准确率："+ (double)correct/len);
	}
	
	
	
	 public static List<Double> train(double[][] dataSet,int[] labels,double learningRate, int maxIt, int algorithm) {
		int size = dataSet.length;
		int dim = dataSet[0].length;
		List<Double> paramList = null;
		double[] w = new double[dim];
		
		for(int i = 0; i < dim; i++) {
			w[i] = 1;
		}
		
		switch(algorithm){
		//批量梯度下降
		case 1: 
			paramList = null;
			paramList = new ArrayList<Double>();
			for(int i = 0; i < maxIt; i++) {
				//求输出
				double[] out = new double[size];
				for(int s = 0; s < size; s++) {
					double lire = innerProduct(w, dataSet[s]);
					out[s] = sigmoid(lire);
				}
				for(int d = 0; d < dim; d++) {
					double sum = 0;
					for(int s = 0; s < size; s++) {
						sum  += (labels[s] - out[s]) * dataSet[s][d];
					}
					w[d] = w[d] + learningRate * sum;
				}
				System.out.println("w迭代:  "+Arrays.toString(w));
			} //更新迭代结束
			
			for(int i=0;i<w.length;i++){
				paramList.add(w[i]); 
			}
			
			break;
		//随机梯度下降
		case 2: 
			paramList = null;
			paramList = new ArrayList<Double>();
			for(int i = 0; i < maxIt; i++) {
				for(int s = 0; s < size; s++) {
					double lire = innerProduct(w, dataSet[s]);
					double out = sigmoid(lire);
					double error = labels[s] - out;
					for(int d = 0; d < dim; d++) {
						w[d] += learningRate * error * dataSet[s][d];
					}
				}
				System.out.println("w迭代:  "+Arrays.toString(w));
			} //更新迭代结束
			
			for(int i=0;i<w.length;i++){
				paramList.add(w[i]); 
			}
			
			break;
		}
		return paramList;
	} 
	
	private static double innerProduct(double[] w, double[] x) {
		
		double sum = 0;
		for(int i = 0; i < w.length; i++) {
			sum += w[i] * x[i];
		}
		
		return sum;
	}
	
	private static double sigmoid(double src) {
		return  (1.0 / (1 + Math.exp(-src)));
	}
 



//------------------------------------------------------------------------//

  public static void printArr(int[] str){
	  int len = str.length;
	  for(int i=0;i<len;i++){
		  System.out.print(str[i]+" ");
	  }
	  System.out.println();
   }
  
  public static void printArr(double[] str){
	  int len = str.length;
	  for(int i=0;i<len;i++){
		  System.out.print(str[i]+" ");
	  }
	  System.out.println();
   }
  
  
  public static void printArr2(double[][] str){
	  int len = str.length;
	  for(int i=0;i<len;i++){
		  for(int j=0;j<str[i].length;j++){
			  System.out.print(str[i][j]+" ");
		  }
		 System.out.println(); 
	  }
   }
  
  
  
  }
