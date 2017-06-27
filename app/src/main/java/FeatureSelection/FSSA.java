package FeatureSelection;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FSSA {

	private HashMap<String, ArrayList<Double>> features;
	private String[] featureNames;
	private ArrayList<ArrayList<Double>> FSSA_input;
	
	public FSSA(HashMap<String, ArrayList<Double>> features, String[] featureNames){
		this.features = features;
		this.featureNames = featureNames;
		
		this.FSSA_input = createFSSAInput(this.features, this.featureNames);
	}
	
	/**
	 * FSSA
//	 * @param features
//	 * @param k_param
	 * @return
	 */
	public HashMap<String, Double> FSSA(int k){
//		System.out.println("FSSA start");
		HashMap<String, Double> reducedSet = new HashMap<String, Double>();
		int featureSize = features.size();
		int k_param = k;
		double drift = 1.0;
		
		ArrayList<Integer> k_list = new ArrayList<Integer>();
		ArrayList<Double> lower_list = new ArrayList<Double>();
		
		ArrayList<Integer> tag_list = new ArrayList<Integer>();
		for (int i=0; i<featureSize; i++)
			tag_list.add(1);
		
		//Form the inter-feature distance matrix
		double[][] dm = l2Matrix(FSSA_input);
		RealMatrix dm_matr = MatrixUtils.createRealMatrix(dm);
		
		//Form a vector containing the distance of k-NN for each feature.
		ArrayList<Double> rks0 = knn_distance(featureSize, dm_matr, k_param);
		
		//Condense the feature set
		int index=0;
		double lower = 9999, prev_lower = 9999;
		while ( (featureSize - dm_matr.getTrace()) > 0 ){
//			System.out.println("while: "+(featureSize - dm_matr.getTrace()));
			double diff = (featureSize - dm_matr.getTrace()) -1;
			if (k_param > (diff)){
				k_param = (int) diff;
			}
			if (k_param<=0)
				break;
			
			prev_lower = lower;
			Pair<Integer, Double> p = lowError(featureSize, dm_matr,  k_param);
			index = p.getKey();
			lower = (Double) p.getValue();
			
			//adjust k
			while (lower > (drift*prev_lower) ){
//				System.out.println("adjust k "+k_param);
				k_param = k_param-1;
				if (k_param==0)
					break;
				p = lowError(featureSize, dm_matr, k_param);
				index = (Integer) p.getKey();
				lower = (Double) p.getValue();
			}

			if (k_param<=0)
				break;
			
			//update dm
			double[][] new_dm = updateDm(featureSize, dm_matr, index, k_param);
//			System.out.println("new dm");
//			for (int i=0; i<new_dm.length; i++) {
//				for (int j=0; j<new_dm[i].length; j++) {
//					System.out.print(new_dm[i][j]+" ");
//				}
//				System.out.println();
//			}
			
			k_list.add(k_param);
			lower_list.add(lower);
			tag_list.set(index, 0);
			
			for (int i=0; i<featureSize; i++){
				if (new_dm[i][i] == 1){
					tag_list.set(i, 0);
				}
			}
			
			for (int i=0; i<featureSize; i++){
				if (new_dm[i][i] == 0){
					reducedSet.put(featureNames[i], rks0.get(i));
				}
			}	
		}
//		System.out.println("FSSA end");
		return reducedSet;
	}
	
	private double[][] l2Matrix(ArrayList<ArrayList<Double>> features){
//		System.out.println("l2Matrix start");
		ArrayList<ArrayList<Double>> l2matrix = new ArrayList<ArrayList<Double>>();
		double[][] l2mat = new double[features.size()][features.size()];

		float sxy=0, sx=0, sy=0, mnx1=0, mnx2=0, theta=0;
		//pairwise feature similarity measure
		double[][] dm = new double[features.size()][features.size()];
		for (int i=0; i<features.size(); i++){
			for (int j=0; j<features.size(); j++){
				ArrayList<Double> x = features.get(i);
				ArrayList<Double> y = features.get(j);

				if (i!=j){
//					System.out.println(i+"--"+j);
					dm[i][j] = similarityMeasure(x,y);
				}
				else {
					dm[i][j] = 0;
				}
			}
		}		
//		System.out.println("l2Matrix end");
		return dm;
	}
	
	private double similarityMeasure(ArrayList<Double> x, ArrayList<Double> y){
//		System.out.println("similarityMeasure start");
		double theta = 0;
		double[] x_arr = Utilities.CollectionUtilities.listToArray(x);
		double[] y_arr = Utilities.CollectionUtilities.listToArray(y);
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
		
//		System.out.println("cov");
//		for (int i=0; i<data.length; i++){
//			for (int j=0; j<data[i].length; j++){
//				System.out.print(data[i][j]+" ");
//			}
//			System.out.println();
//		}

		RealMatrix cov_xy = new Covariance(mx).getCovarianceMatrix();
		double[][] cov_xy_arr = cov_xy.getData();
		
//		System.out.println("cov");
//		for (int i=0; i<cov_xy_arr.length; i++){
//			for (int j=0; j<cov_xy_arr[i].length; j++){
//				System.out.print(cov_xy_arr[i][j]+" ");
//			}
//			System.out.println();
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
//		System.out.println("similarityMeasure end");
		return dist;
	}
	
	private ArrayList<Double> knn_distance(int featureSize, RealMatrix dm_matr, int k){
//		System.out.println("knn_distance start");
		ArrayList<Double> rk = new ArrayList<Double>();
		for (int i=0; i<featureSize; i++){
			RealMatrix dd_matr = null;
			ArrayList<Double> dd_list = new ArrayList<Double>();
			
			if (i == 0){
				//dd=dm(1,2:no_feature);
				dd_matr = dm_matr.getSubMatrix(0, 0, 1, featureSize-1);
				double[][] dd_matrData = dd_matr.getData();
				
				for (int ii=0; ii<dd_matrData.length; ii++){
					for (int jj=0; jj<dd_matrData[ii].length; jj++){
						dd_list.add(dd_matrData[ii][jj]);
					}
				}
			}
			else if (i == featureSize-1){
				//dd=dm(1:no_feature-1,no_feature)';
				dd_matr = dm_matr.getSubMatrix(0, featureSize-2, featureSize-1, featureSize-1);
				double[][] dd_matrData = dd_matr.getData();
				
				for (int ii=0; ii<dd_matrData.length; ii++){
					for (int jj=0; jj<dd_matrData[ii].length; jj++){
						dd_list.add(dd_matrData[ii][jj]);
					}
				}
			}
			else{
				//dd=[dm(i,i+1:no_feature),dm(1:i-1,i)'];
				RealMatrix tmp1, tmp2;
				tmp1 = dm_matr.getSubMatrix(i, i, i+1, featureSize-1);
				tmp2 = dm_matr.getSubMatrix(0, i-1, i, i).transpose(); // was i-1 
				
				double[][] tmp1Data = tmp1.getData();
				double[][] tmp2Data = tmp2.getData();
				
				for (int ii = 0; ii < tmp1Data.length; ii++) {
					for (int jj = 0; jj < tmp1Data[ii].length; jj++) {
						dd_list.add(tmp1Data[ii][jj]);
					}
				}
				for (int ii = 0; ii < tmp2Data.length; ii++) {
					for (int jj = 0; jj < tmp2Data[ii].length; jj++) {
						dd_list.add(tmp2Data[ii][jj]);
					}
				}
			}
			Collections.sort(dd_list);
			
			rk.add(dd_list.get(k-1));
		}
//		System.out.println("knn_distance end");
		return rk;
	}
	
	private Pair<Integer, Double> lowError(int featureSize, RealMatrix dm, int k) {
//		System.out.println("lowError start");
		double high = 9999;
		ArrayList<Double> kd = new ArrayList<Double>();
		Pair<Integer, Double> p;

		for (int i = 0; i < featureSize; i++) {
			RealMatrix dd_matr = null;
			ArrayList<Double> dd_list = new ArrayList<Double>();
			if (dm.getEntry(i, i) == 1) {
				kd.add(i, high);
			} 
			else {
				if (i == 0) {
					// dd=[HIGH,dm(1,2:no_feature)];
					dd_matr = dm.getSubMatrix(0, 0, 1, featureSize - 1);
					double[][] dd_matrData = dd_matr.getData();

					dd_list.add(0, high);
					for (int ii = 0; ii < dd_matrData.length; ii++) {
						for (int jj = 0; jj < dd_matrData[ii].length; jj++) {
							dd_list.add(dd_matrData[ii][jj]);
						}
					}
				} 
				else if (i == featureSize - 1) {
					// dd=[dm(1:no_feature-1,no_feature)',HIGH];
					dd_matr = dm.getSubMatrix(0, featureSize - 2,
							featureSize - 1, featureSize - 1);
					double[][] dd_matrData = dd_matr.getData();
					
					for (int ii = 0; ii < dd_matrData.length; ii++) {
						for (int jj = 0; jj < dd_matrData[ii].length; jj++) {
							dd_list.add(dd_matrData[ii][jj]);
						}
					}
					dd_list.add(high);
				} 
				else {
					// dd=[dm(1:i-1,i)',HIGH,dm(i,i+1:no_feature)];
					RealMatrix tmp1, tmp2;
					tmp1 = dm.getSubMatrix(i, i, i + 1, featureSize - 1);
					tmp2 = dm.getSubMatrix(0, i - 1, i, i); // was i-1
					
					double[][] tmp1Data = tmp1.getData();
					double[][] tmp2Data = tmp2.getData();
					
					for (int ii = 0; ii < tmp2Data.length; ii++) {
						for (int jj = 0; jj < tmp2Data[ii].length; jj++) {
							dd_list.add(tmp2Data[ii][jj]);
						}
					}
					dd_list.add(high);
					for (int ii = 0; ii < tmp1Data.length; ii++) {
						for (int jj = 0; jj < tmp1Data[ii].length; jj++) {
							dd_list.add(tmp1Data[ii][jj]);
						}
					}
				}

				for (int j = 0; j < featureSize; j++) {
					if (dm.getEntry(j, j) == 1) {
						dd_list.set(j, high);
					}
				}

				Collections.sort(dd_list);
				kd.add(i, dd_list.get(k-1));
			}
		}
		double min = Collections.min(kd);
		int ind = kd.indexOf(min);
		
		p = Pair.create(ind, min);
//		System.out.println("lowError end");
		return p;
	}
	
	private double[][] updateDm(int featureSize, RealMatrix dm, int index, int k){
//		System.out.println("updateDm start");
		double[][] dm_arr = new double[dm.getRowDimension()][dm.getColumnDimension()];
		double high = 9999;
		int i = index;
		RealMatrix dd_matr = null, new_dm = null;;
		ArrayList<Double> dd_list = new ArrayList<Double>();
		
		dm_arr = dm.getData();
		
		if (i == 0) {
			// dd=[HIGH,dm(1,2:no_feature)];
			dd_matr = dm.getSubMatrix(0, 0, 1, featureSize - 1);
			double[][] dd_matrData = dd_matr.getData();
			
			dd_list.add(high);
			for (int ii = 0; ii < dd_matrData.length; ii++) {
				for (int jj = 0; jj < dd_matrData[ii].length; jj++) {
					dd_list.add(dd_matrData[ii][jj]);
				}
			}
		}
		else if (i == (featureSize-1)) {
			// dd=[dm(1:no_feature-1,no_feature)',HIGH];
			dd_matr = dm.getSubMatrix(0, featureSize - 2,
					featureSize - 1, featureSize - 1).transpose();
			double[][] dd_matrData = dd_matr.getData();
			
			for (int ii = 0; ii < dd_matrData.length; ii++) {
				for (int jj = 0; jj < dd_matrData[ii].length; jj++) {
					dd_list.add(dd_matrData[ii][jj]);
				}
			}
			dd_list.add(high);
		}
		else {
			// dd=[dm(1:i-1,i)',HIGH,dm(i,i+1:no_feature)];
			RealMatrix tmp1, tmp2;
			tmp1 = dm.getSubMatrix(i, i, i + 1, featureSize - 1);
			tmp2 = dm.getSubMatrix(0, i - 1, i, i).transpose(); // was
																// i-1
			double[][] tmp1Data = tmp1.getData();
			double[][] tmp2Data = tmp2.getData();

			for (int ii = 0; ii < tmp2Data.length; ii++) {
				for (int jj = 0; jj < tmp2Data[ii].length; jj++) {
					dd_list.add(tmp2Data[ii][jj]);
				}
			}
			dd_list.add(high);
			for (int ii = 0; ii < tmp1Data.length; ii++) {
				for (int jj = 0; jj < tmp1Data[ii].length; jj++) {
					dd_list.add(tmp1Data[ii][jj]);
				}
			}
		}
		
		for (int j=0; j<featureSize; j++){
			if (dm.getEntry(j, j) == 1)
				dd_list.set(j, high);
		}
		
		ArrayList<Double> dd_list_store = new ArrayList<Double>(dd_list);
		Collections.sort(dd_list);
		int[] indexes = new int[dd_list.size()];
		for (int n = 0; n < dd_list.size(); n++){
		    indexes[n] = dd_list_store.indexOf(dd_list.get(n));
		}
		
		new_dm = dm;
		
		for (int j=0; j<k; j++){
			int ind = indexes[j];
			new_dm.setEntry(ind, ind, 1);
		}
		dm_arr = new_dm.getData();
		
//		System.out.println("updateDm end");
//		for (int ii=0; ii<dm_arr.length; ii++) {
//			for (int j=0; j<dm_arr[i].length; j++) {
//				System.out.println(dm_arr[ii][j]+" ");
//			}
//			System.out.println();
//		}
		return dm_arr;
	}
	
	public ArrayList<ArrayList<Double>> createFSSAInput(HashMap<String, ArrayList<Double>> features, String[] featureNames_Arr) {
		ArrayList<ArrayList<Double>> FSSA_input = new ArrayList<ArrayList<Double>>();
		for (int i=0; i<featureNames_Arr.length; i++){
			ArrayList<Double> val = new ArrayList<Double>();
			val.addAll(features.get(featureNames_Arr[i]));
			FSSA_input.add(val);
		}
		return FSSA_input;
	}

	public ArrayList<ArrayList<Double>> getFSSA_input() {
		return FSSA_input;
	}

	public void setFSSA_input(ArrayList<ArrayList<Double>> fSSA_input) {
		FSSA_input = fSSA_input;
	}
}
