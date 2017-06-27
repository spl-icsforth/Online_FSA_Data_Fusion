package Graph_NC_RE;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;


public class Metrics {

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
//			// System.out.println("tmpW");
//			Utilities.print2dArray(tmpW);
			double LEi = LaplacianEnergy(tmpW);
			
			LC[i] = (LE - LEi)/LE;
			// System.out.println(LC[i]);
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
    
    /**
     * method that calculates dHm for a specific graph object
     * dHm = abs(HA\Am - HA)
     * @param data 
     * @return array indicating the RE value for each graph node
     */
    public static double[] RE(double[][] data) {
    	double[] RE = new double[data.length];


		// create RealMatrix representation of data
		RealMatrix mx = MatrixUtils.createRealMatrix(data);
		// calculate covariance matrix
		RealMatrix covMat;
		if (data.length > 1) covMat = new Covariance(mx).getCovarianceMatrix();
		else {
			covMat = MatrixUtils.createRealMatrix(1, 1);
			covMat.setEntry(0, 0, 0);
		}
    	// representation entropy of whole data matrix
    	double Hr = RepresentationEntropy(covMat);

		ArrayList<Integer> featureIndexes = Utilities.rangeArrayList(0, data.length);
    	// representation entropy of data when removing element i
    	for (int i=0; i<data.length; i++) {
//			double[][] tmpData = excludeElement(data, i);

//			RE[i] = Math.abs( RepresentationEntropy(tmpData) - Hr );
			ArrayList<Integer> remove_i = new ArrayList<Integer>(featureIndexes);
			remove_i.remove(i);
//			System.out.println("featureIndexes i: "+i +remove_i);
			int[] excludeFeat_i = Utilities.listToArrayInt(remove_i);

			/**
			 * calculate RepresentationEntropy for dataset when removing feature i
			 */
			RealMatrix x = covMat.getSubMatrix(excludeFeat_i, excludeFeat_i);
			double hi = Metrics.RepresentationEntropy(x);
			RE[i] = Math.abs( hi - Hr );
		}
    	
    	return RE;
    }

	public static double[][] excludeElement(double[][] matrix, int elem) {
		double[][] outMatrix = new double[matrix.length][matrix[0].length];
		for (int i=0; i<matrix.length; i++) {

			for (int j=0; j<matrix[i].length; j++) {
				if (j == elem) {
					if (elem == 0)
						matrix[i][j] = matrix[i][j+1];
					else if (elem == matrix[0].length)
						continue;
					else
						matrix[i][j-1] = matrix[i][j];
				}
				matrix[i][j] = matrix[i][j]; //?
			}
		}
//    	System.out.println(outMatrix.length + "::: "+outMatrix[0].length);
		return matrix;
	}
    
	/**
	 * Given a matrix, calculates its RepresentationEntropy
	 *
	 * @return the normalized RepresentationEntropy value
	 */
	public static double RepresentationEntropy(RealMatrix covMat) {

		// calculate eigenvalues of covariance matrix
		EigenDecomposition eig = new EigenDecomposition(covMat);
		double[] eigenV = eig.getRealEigenvalues();
		for (int i=0; i<eigenV.length; i++) ; // System.out.println("eig:"+eigenV[i]);
		// normalize eigenValues array wrt to its sum
		double[] normEigenV = Utilities.normArray(eigenV);

		// hr calculation
		double hr = 0;
		double hrNorm = 0;
		
		double[] HR = new double[normEigenV.length];
		for (int i=0; i<normEigenV.length; i++) {
			double log = Math.log10(normEigenV[i]);
			hr = hr - (normEigenV[i] * log);
		}
		hrNorm = hr / Math.log10(covMat.getColumnDimension());
		// System.out.println("hr norm:"+hrNorm);
		return hrNorm;
	}
	
	public static double l2similarity(ArrayList<Double> x, ArrayList<Double> y){
		double theta = 0;
		double[] x_arr = Utilities.listToArray(x);
		double[] y_arr = Utilities.listToArray(y);
		DescriptiveStatistics stats_x = new DescriptiveStatistics(x_arr);
		DescriptiveStatistics stats_y = new DescriptiveStatistics(y_arr);
		
		//calculate mean x y
		double mean_x = stats_x.getMean();
		double mean_y = stats_y.getMean();
		
		//calculate var x y using the population variance formula : sum((x_i - mean)^2) / n
		double var_x = stats_x.getPopulationVariance();
		double var_y = stats_y.getPopulationVariance();
		
		//calculate sxy
		double sxy = 0;
		for (int i=0; i<x_arr.length; i++){
			sxy += (x_arr[i] * y_arr[i]); 
		}
		sxy = (sxy/x_arr.length) - (mean_x * mean_y);
		
		
		//calculate theta
		if (var_x == var_y) {
			theta = (float) (0.5 * Math.PI / 2);
		} else {
			theta = (0.5 * Math.atan(2 * sxy / (var_x - var_y)));
		}
		
		//calculate covariance matrix
		//tmp = cov(x1-mean(x1),x2-mean(x2),1);
		ArrayRealVector xx = new ArrayRealVector(x_arr);
		xx.mapSubtract(mean_x);
		
		ArrayRealVector yy = new ArrayRealVector(y_arr);
		yy.mapSubtract(mean_y);
		
		
		//TODO 13x2
		RealMatrix mx = MatrixUtils.createRealMatrix(new double[][]{
				  xx.toArray(),
				  yy.toArray() });
		mx = mx.transpose();
		double[][] data = mx.getData();
		
//		// System.out.println("cov");
//		for (int i=0; i<data.length; i++){
//			for (int j=0; j<data[i].length; j++){
//				// System.out.print(data[i][j]+" ");
//			}
//			// System.out.println();
//		}

		RealMatrix cov_xy = new Covariance(mx).getCovarianceMatrix();
		double[][] cov_xy_arr = cov_xy.getData();
		
//		// System.out.println("cov");
//		for (int i=0; i<cov_xy_arr.length; i++){
//			for (int j=0; j<cov_xy_arr[i].length; j++){
//				// System.out.print(cov_xy_arr[i][j]+" ");
//			}
//			// System.out.println();
//		}

		//calculate eigenvalues
		RealMatrix ll = null, llv2;
//		try{
			//TODO - output not the same as matlab
			EigenDecomposition eig = new EigenDecomposition(cov_xy); //exception
			//V,D correspond to matlab output for the eig method --> [V,D] = eig(A)
			ll = eig.getV();
			double[][] lldata = ll.getData();
			llv2 = eig.getD();
			double[][] ll2data = llv2.getData();
//		}
//		catch (Exception e) {
//			System.err.println("><><<<<<<<<<>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<EXCEPTION>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<>>>>>>>>");
//			System.err.println(e);
//		}
		
		double b_hat = -(1.0 / Math.tan(theta));		
		/* 	if sx<sy
        		a4 = ll(2,2)/ll(1,2);
    		else
        		a4 = ll(2,1)/ll(1,1);
     		end 
     	*/
		
		//calculate a_hat
		double a_hat = 0;
		if (var_x > var_y){ //was <
			if (ll!=null){
				//2nd column
//				a_hat = ll.getEntry(1, 1) / ll.getEntry(0, 1);
				a_hat =  (ll.getEntry(1, 1) / ll.getEntry(0, 1)); // me - ?
			}
		}
		else {
			if (ll!=null){
				//1st column
				a_hat = (ll.getEntry(1, 0) / ll.getEntry(0, 0));
			}
		}
		
		//b=-mean(x1)*a4+mean(x2);
		double b = -mean_x * a_hat + mean_y;

		/*	dist=0.0;
   			for i=1:no_x1,
      			dist=dist+ ((x2(i)-a4*x1(i)-b)/sqrt(a4^2+1))^2;
   			end
   			dist=dist/no_x1;  
   		*/
		double dist = 0;
		for (int i=0; i<x_arr.length; i++){
			dist += Math.pow( (y_arr[i] - a_hat * x_arr[i] - b) / (Math.sqrt(Math.pow(a_hat, 2)+1)), 2 );
		}
		dist =dist / x_arr.length;
		return dist;
	}
}
