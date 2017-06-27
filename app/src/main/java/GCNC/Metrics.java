package GCNC;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.ArrayList;

public class Metrics {
	
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
	 * Given an Arraylist, this method computes its term variance
	 * @param A The Arraylist
	 * @return The TV value
	 */
	public static double TV(ArrayList<Double> A) {
		double tv = 0;
		double[] A_arr = Utilities.listToArray(A);
		int nPatterns = A.size();
		
		double sum_diff = 0;
		for (int i=0; i<nPatterns; i++) {
			double a = A.get(i);
			// mean of feature vector
			double mean_A = mean(A_arr);
			
			// diff
			double diff = Math.pow((a - mean_A), 2);

			// compute the sum
			sum_diff += diff;
		}
		
		// compute term variance value
		tv = sum_diff / nPatterns;
		
		return tv;
	}
	
	/**
	 * method that computes Laplacian Centrality at a given graph
	 * @param weightMatrix the adjacency (weight) matrix of the graph
	 * @return an array indicating the LC value for each graph node
	 */
	public static double[] LC(double[][] weightMatrix) {
		double[] LC = new double[weightMatrix.length];
		
		double LE = LaplacianEnergy(weightMatrix);

		for (int i=0; i<weightMatrix.length; i++) {
			double[][] tmpW = Utilities.excludeElement(i, weightMatrix);
//			System.out.println("tmpW");
//			Utilities.print2dArray(tmpW);
			double LEi = LaplacianEnergy(tmpW);
			
			LC[i] = (LE - LEi)/LE;
//			System.out.println(LC[i]);
		}
		
		return LC;
	}
	
	
    /**
     * method that computes the LaplacianEnergy of a graph object
     * @return
     */
    public static double LaplacianEnergy(double[][] weightMatrix) {
    	double LE = 0;
    	
    	// create sum_weight matrix (X)
    	double[][] sumWeightMatrix = new double[weightMatrix.length][weightMatrix.length];
		for (int i=0; i<weightMatrix.length; i++) {
			double sum = 0;
			for (int j=0; j<weightMatrix[i].length; j++) {
				sum += weightMatrix[i][j];
			}
			sumWeightMatrix[i][i] = sum;
		}
    	
    	// compute Laplacian Energy
    	double sumX = 0;
    	for (int i=0; i<sumWeightMatrix.length; i++) {
    		sumX += sumWeightMatrix[i][i];
    	}
    	
    	double sumW = 0;
    	for (int i=0; i<weightMatrix.length; i++) {
    		for (int j=0; i<weightMatrix.length; i++) { //was j=i+1
    			double w = weightMatrix[i][j];
    			sumW =+ Math.pow(w, 2);
    		}
    	}
//    	sumW = 2 * sumW;
    	LE = sumX + sumW;
    	
    	return LE;
    }
}
