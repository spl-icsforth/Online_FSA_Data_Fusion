package GCNC;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Utilities {
	
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
	
	public static ArrayList<Integer> rangeArrayList(int start, int end) {
		ArrayList<Integer> a = new ArrayList<Integer>(end-start);
		
		for (int i=start; i<end; i++) {
			a.add(i);
		}

		return a;
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
	
	public static void print2dArray(double[][] array) {
		for (int i=0; i<array.length; i++) {
			System.out.print(i+" -> " + array[i].length + " --->");
			for (int j=0; j<array[i].length; j++) {
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}

	public static ArrayList<Double> subList(ArrayList<Double> list, int sz) {
		ArrayList<Double> values = new ArrayList<Double>(sz);
		for (int i = 0; i < sz; i++) {
			values.add(list.get(i));
		}
		return values;
	}
}
