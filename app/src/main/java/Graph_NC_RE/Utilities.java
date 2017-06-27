package Graph_NC_RE;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Utilities {

	/**
	 * Given two feature vectors, computes the Pearson's Correlation Coefficient
	 * 
	 * @param x feature vector x
	 * @param y feature vectr y
	 * @return the Pearson's Correlation Coefficient
	 */
	public static double pearsonsCoeff(double[] x, double[] y) {
		PearsonsCorrelation p = new PearsonsCorrelation();
		double corr = p.correlation(x, y);
		
		return corr;
	}
	
	public static double mean(double[] m) {
	    double sum = 0;
	    for (int i = 0; i < m.length; i++) {
	        sum += m[i];
	    }
	    return sum / m.length;
	}
	
	/**
	 * Given a double value and an array, normalized the value in range [0, 1]
	 * using the softmax scaling methos [Theodoridis and Koutroumbas, 2008]
	 * @param w a double value
	 * @param weights the array
	 * @return
	 */
	public static double softmaxScaling(double w, double[] weights) {
		double softmax = 0;
		
		// mean of weights
		Mean m = new Mean();
		double mean = m.evaluate(weights);
		
		//variance of weights
		Variance v = new Variance();
		double var = v.evaluate(weights);
		
		// diff
		double diff = (w - mean) / var;
		
		// normalized value
		softmax = 1 / (1 + Math.exp(-diff));	
		
		return softmax;
	}
	
	public static double zScoreNorm(double a, double[] A) {
		double z = 0;
		
		// mean of A
//		Mean m = new Mean();
		double m = mean(A);// m.evaluate(A);
		
//		//variance of weights
		StandardDeviation s = new StandardDeviation();
		double std = s.evaluate(A);
//		
		z = (a - m) / std;
		
//		System.out.println("z: " + z);
		
		return m;
	}
	
	/**
	 * Given an Arraylist, converts in to an array
	 * @param list the Arraylist
	 * @return the resulting array
	 */
	public static double[] listToArray(ArrayList<Double> list) {
		double[] valuesToArray = new double[list.size()];
		for (int i = 0; i < list.size(); i++) {
			valuesToArray[i] = list.get(i).doubleValue();
		}
		return valuesToArray;
	}
	
	public static int[] listToArrayInt(ArrayList<Integer> list) {
		int[] valuesToArray = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			valuesToArray[i] = list.get(i).intValue();
		}
		return valuesToArray;
	}
	

	public static double[][] sumMatrix(double[][] a) {
		double[][] sum_a = new double[a.length][a.length];
		for (int i=0; i<sum_a.length; i++) {
    		for (int j=0; j<sum_a[i].length; j++) {
    			sum_a[i][j] = 0;
    		}
    	}
			
		for (int i=0; i<a.length; i++) {
			int sum = 0;
			for (int j=0; j<a[i].length; j++) {
//				sum_a[i][j] = 0;
				
				sum += a[i][j];
			}
			sum_a[i][i] = sum;
			System.out.println(sum);
		}
		
		return sum_a;
	}
	
	public static double sumArray(double[] a) {
		double sum = 0;
		for (int i=0; i<a.length; i++) {
			sum += a[i];
		}
		return sum;
	}
	
	public static void print2dArray(double[][] array) {
		for (int i=0; i<array.length; i++) {
			System.out.print(i+" -> ");
			for (int j=0; j<array[i].length; j++) {
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public static int isEmptyArray(int[] a) {
		if (a.length == 0)
			return 1;
		else
			return 0;
	}
	
	public static int isEmptyArrayList(ArrayList<Integer> a) {
		if (a.size() == 0)
			return 1;
		else
			return 0;
	}
	
	public static int[] removeElement(int[] original, int element){
	    int[] n = new int[original.length - 1];
	    System.arraycopy(original, 0, n, 0, element );
	    System.arraycopy(original, element+1, n, element, original.length - element-1);
	    return n;
	}
	
	public static double[][] excludeElement(int ind, double[][] a) {
		int rows = a.length;
		int columns = a.length;
		double destinationarr[][] = new double[rows-1][columns-1];

        int REMOVE_ROW = ind;
        int REMOVE_COLUMN = ind;
        int p = 0;
        for( int i = 0; i < rows; ++i) {
            if ( i == REMOVE_ROW)
                continue;

            int q = 0;
            for( int j = 0; j < columns; ++j) {
                if ( j == REMOVE_COLUMN)
                    continue;

                destinationarr[p][q] = a[i][j];
                q++;
            }

            p++;
        }
        
        return destinationarr;
	}
	
	public static double[] normArray(double[] a) {
		double sumA = sumArray(a);
		double[] normA = new double[a.length];
		
		for (int i=0; i<a.length; i++) {
			if (Math.abs(a[i]) < 0.0001)
				normA[i] = 1;
			else
				normA[i] = a[i] / sumA; 
		}
		return normA;
	}
	
	public static ArrayList<Integer> rangeArrayList(int start, int end) {
		ArrayList<Integer> a = new ArrayList<Integer>(end-start);
		
		for (int i=start; i<end; i++) {
			a.add(i);
		}

		return a;
	}

	public static ArrayList<Double> subList(ArrayList<Double> list, int sz) {
		ArrayList<Double> values = new ArrayList<Double>(sz);
		for (int i = 0; i < sz; i++) {
			values.add(list.get(i));
		}
		return values;
	}
	
	/**
	 * Given a HashMap, converts in to an array
	 * @param featureSet
	 * @param featureNames
	 */
	public static double[][] mapToArray(HashMap<Integer, ArrayList<Double>> map){
		double[][] mapArray = new double[map.size()][map.size()];
		/**
		 * iterate through features
		 */
		for (int i=0; i<map.size(); i++) {
			ArrayList<Double> values = map.get(i);
			double[] values_arr = listToArray(values);
			mapArray[i] = values_arr;
		}
//		mapArray = trasposeMatrix(mapArray);
//		print2dArray(mapArray);
		return mapArray;
	}
	
	public static double[][] matrixMultiplication(double[][] firstarray, double[][] secondarray) {
		/* Create another 2d array to store the result using the original arrays' lengths on row and column respectively. */
		double [][] result = new double[firstarray.length][secondarray[0].length];

		/* Loop through each and get product, then sum up and store the value */
		for (int i = 0; i < firstarray.length; i++) { 
		    for (int j = 0; j < secondarray[0].length; j++) { 
		        for (int k = 0; k < firstarray[0].length; k++) { 
		            result[i][j] += firstarray[i][k] * secondarray[k][j];
		        }
		    }
		}
		
		return result;
	}

	/**
	 * method to write a graph to a file
	 * @param graph The undirected graph
	 * @param fileName The filename
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void graphToFile(double[][] graph, String fileName) throws FileNotFoundException, IOException {
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");

		for (int i=0; i<graph.length; i++) {
			int node = i;
			double[] edgesFrom_i = graph[i];
			ArrayList<Integer> edges = new ArrayList<Integer>();
			for (int j=0; j<edgesFrom_i.length; j++) {
				if (edgesFrom_i[j] != 0) {
//					edges.add(j);
					String line = node + "	" + j + "	" + graph[i][j];
					writer.println(line);
				}
			}
		}

		writer.close();
	}
}
