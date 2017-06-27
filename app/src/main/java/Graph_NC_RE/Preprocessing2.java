package Graph_NC_RE;

import net.sf.javaml.utils.ArrayUtils;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Preprocessing2 {
	private HashMap<String, ArrayList<Double>> featureSet;
	private String[] featureNames;
//	private UndirectedGraph graph;
	private double theta;
	private ArrayList<Integer> clusters;
	private double[][] wMatrix;
	private double[][] xMatrix;
	private double[][] graphWeights;
	private int no_groups;
	private ArrayList<Integer> groups;

	public Preprocessing2(HashMap<String, ArrayList<Double>> featureSet, String[] featureNames, double theta) {
		super();
		this.featureSet = featureSet;
		this.featureNames = featureNames;
		this.theta = theta;
		this.clusters = new ArrayList<Integer>();		
		this.xMatrix = new double[featureSet.size()][featureSet.size()];
		this.graphWeights = new double[featureSet.size()][featureSet.size()];
	}

	/**
	 * Initializes an UndirectedGraph containing all features as nodes and their 
	 * Pearson's Correlation Coefficient as edges
	 * 
	 * @return
	 */
	public void initializeGraph(){
		/*
		 * create initial graph with nodes - the features
		 * and edges - their l2 similarity
		 */
		for (int i=0; i<featureNames.length; i++){
			for (int j=i+1; j<featureNames.length; j++){
				// get features i and j
				ArrayList<Double> feat_i = this.featureSet.get(featureNames[i]);
				ArrayList<Double> feat_j = this.featureSet.get(featureNames[j]);

				if (feat_i.size() != feat_j.size()) {
					int min_sz = Math.min(feat_i.size(), feat_j.size());
					feat_i = Utilities.subList(feat_i, min_sz);
					feat_j = Utilities.subList(feat_j, min_sz);
				}

				// compute l2 similarity between two feature vectors
				double corr = Metrics.l2similarity(feat_i, feat_j);
				if (i==j) corr = 0;
//				// // // System.out.println(corr);

				graphWeights[i][j] = corr;
				graphWeights[j][i] = corr;
			}
		}
		/*
		 *  loop to update weights to range [0 1] using softmax scaling
		 */
		for (int i=0; i<graphWeights.length; i++) {
			double[] weights_from_i = graphWeights[i];

			double[] new_weights = updateWeights(i, weights_from_i);
			graphWeights[i] = new_weights;
		}

//		Utilities.print2dArray(graphWeights);
//		// // System.out.println("end weights");
	}

	/**
	 * Given node i and its corresponding weights,
	 * scales each weight value int the range [0,1] using softmax scaling
	 * @param i node
	 * @param weightsFrom_i_arr corresponding weights
	 */
	public double[] updateWeights(int i, double[] weightsFrom_i_arr) {
		// init Arraylist for updated weights
		double[] weightsFrom_i_new = new double[weightsFrom_i_arr.length];

		// traverse through weights corresponding to node i and perform softmax scaling foreach value
		for (int k=0; k<weightsFrom_i_arr.length; k++) {
			double softmax = 0;
			// softmax scaling for each value
			if (i!=k)
				softmax = Utilities.softmaxScaling(weightsFrom_i_arr[k], weightsFrom_i_arr);
			// add scaled value to new weights Arraylist
			weightsFrom_i_new[k] = softmax;
		}
		// update weights for node
		return weightsFrom_i_new;
	}

	public ArrayList<Integer> iterativeDominantSetsExtraction() {
		int N = this.graphWeights.length;
		int no_groups = 0;
		double[] zn = new double[N];
		ArrayList<Integer> rem_list = new ArrayList<Integer>(N);
		ArrayList<Integer> groups = new ArrayList<Integer>(N);

		for (int i=0; i<N; i++) {
			zn[i] = 1;
			rem_list.add(i,i);
			groups.add(i,0);
		}

		double sum_zn = Utilities.sumArray(zn);
		for (int i=0; i<N; i++) zn[i] = (double)zn[i]/sum_zn;

/*		RealMatrix A = MatrixUtils.createRealMatrix(this.graphWeights);
		RealMatrix X = MatrixUtils.createRowRealMatrix(zn);
		RealMatrix X_T = MatrixUtils.createColumnRealMatrix(zn);
	    RealMatrix mult = X.multiply(A).multiply(X_T);
*/
//	    // // // System.out.println("mult:"+mult.getEntry(0, 0));

	    double[] multipl = Matrix.multiply(zn, graphWeights);
	    double product = Matrix.multiply(multipl, zn);
	    // // // System.out.println(product);

//	    for (int i=0; i<mult.length; i++)
//	    	// // // System.out.print(mult[i] + " ");

	    double f_ini = product; //mult contains 1 element
	    double f_curr = f_ini + 0.5 * f_ini;

//		// // // System.out.println(f_ini+" "+f_curr);

		ArrayList<Double> cost_function = new ArrayList<Double>();

		int c=1;
		while ( ( (f_curr - f_ini) * (1 - Utilities.isEmptyArrayList(rem_list)) ) > 0 ) {
			 // System.out.println("while");
//			RealMatrix subA = A.getSubMatrix(Utilities.listToArrayInt(rem_list), Utilities.listToArrayInt(rem_list));
			double[][] sub = Matrix.getSubMatrix(graphWeights, Utilities.listToArrayInt(rem_list), Utilities.listToArrayInt(rem_list));

//			ArrayList<Integer> sel_list = dominantSetsExtraction(subA);

			ArrayList<Integer> sel_list2 = dominantSetsExtraction2(sub);
			ArrayList<Integer> new_group = new ArrayList<Integer>();
			// // System.out.println("sel_list2 "+sel_list2);

			double[] z = new double[N];
			for (int i=0; i<z.length; i++) {
				z[i] = 0;
			}

			for (int i=0; i<sel_list2.size(); i++) {
				int ind = sel_list2.get(i);
				new_group.add(rem_list.get(ind));
				groups.set(ind, c);
				z[ind] = 1;
			}
			 // // System.out.println("new_group:"+new_group);

			double sum_z = Utilities.sumArray(z);
			for (int i=0; i<z.length; i++) {
				z[i] = z[i]/sum_z;
			}

//			RealMatrix Z = MatrixUtils.createRowRealMatrix(z);
//			RealMatrix Z_T = MatrixUtils.createColumnRealMatrix(z);
//			RealMatrix multZ = Z.multiply(A).multiply(Z_T);

			multipl = Matrix.multiply(z, graphWeights);
			product = Matrix.multiply(multipl, z);

			f_curr = product;
			cost_function.add(c-1, f_curr);

			 // // System.out.println("f_curr: "+f_curr + " f_ini: "+f_ini);

			for (int i=0; i<new_group.size(); i++) {
				int element = new_group.get(i);
//				// // // System.out.println("el"+element);
				rem_list.remove(rem_list.indexOf(element));
			}
			 // // System.out.println("rem_list "+rem_list);

			c++;
		}
		no_groups = c-1;

		  // System.out.println(groups);
//		   System.out.println("no_groups: "+no_groups);

		this.no_groups = no_groups;
		this.groups = groups;

		return groups;
	}


	/**
	 * method to extract dominant sets for a given similarity matrix A
	 * @param A weight matrix with feature similarities
	 * @return selection list
	 */
	public ArrayList<Integer> dominantSetsExtraction2(double[][] A) {
		ArrayList<Integer> sel_list = new ArrayList<Integer>();
		ArrayList<Integer> rest_list = new ArrayList<Integer>();

		int N = A.length;
		double[] x = new double[N];
		for (int i=0; i<N; i++) {
			x[i] = (double) 1/N;
		}

//		RealMatrix X = MatrixUtils.createRowRealMatrix(x);
//		RealMatrix X_T = MatrixUtils.createColumnRealMatrix(x);
//		RealMatrix mult = X.multiply(A).multiply(X_T);

		double[] multipl = Matrix.multiply(x, A);
	    double product = Matrix.multiply(multipl, x);

		double f_0 = product; //mult contains 1 element
		double f_1 = 0;
		double f_2 = f_0;

//		RealMatrix X2 = MatrixUtils.createRowRealMatrix(x); //X
//		RealMatrix X2_T = MatrixUtils.createColumnRealMatrix(x);

		double[] x2 = x;

		while ( (double)(f_2-f_1)/f_0 > 0.00000001 ) {
			// // System.out.println("dominantSetsExtraction2 while -->" + (double)(f_2-f_1)/f_0);
			f_1 = f_2;

//			RealMatrix mult1 = X2.multiply(A); // elements: [1 x nFeatures]
//			RealMatrix mult2 = X2.multiply(A).multiply(X2_T); // elements: [1x1]

			double[] mult1 = Matrix.multiply(x2, A);
			double mult2 = Matrix.multiply(mult1, x2);

//			// // // System.out.println(mult1.toString());
			 // // System.out.println(mult2);

			double mult2_val = mult2;

			double[] div = Matrix.scalarMultiply(mult1, (double)1/mult2_val); //mult1.scalarMultiply((double)1/mult2_val);
			// // // System.out.println(div.toString());

//			RealMatrix multiplication = MatrixUtils.createRealMatrix(X2.getData());
//			// // // System.out.println(X2.toString());
			for (int i=0; i<x2.length; i++) {
				double elem1 = x2[i];
				double elem2 = div[i];

				double m = elem1 * elem2;

				x2[i] = m;
			}

			// // // System.out.println(X2.toString());



//			X2.multiply(A).multiply(X2_T);
//			X2_T = X2.transpose();

			double[] mult = Matrix.multiply(x2, A);
			double res = Matrix.multiply(mult, x2);

//			RealMatrix ff = X2.multiply(A).multiply(X2_T);
//			f_2 = ff.getEntry(0, 0);
			double[] ff = Matrix.multiply(A, x2);
			f_2 = Matrix.multiply(ff, x2);

		}

//		x = x2;
		x = x2.clone();
		double[] X_data = x.clone();
		double max_X = ArrayUtils.max(X_data);
		for (int i=0; i<X_data.length; i++) {
			if (X_data[i] > (0.05 * max_X)) {
				int sel_ind = i;
				sel_list.add(sel_ind);
			}
			else {
				int rest_ind = i;
				rest_list.add(rest_ind);
			}
		}

		 // // System.out.println("sel_list: "+sel_list);
		 // // System.out.println("rest_list: "+rest_list);

		return sel_list;
	}

	/**
	 * method to extract dominant sets for a given similarity matrix A
	 * @param A weight matrix with feature similarities
	 * @return selection list
	 */
	public ArrayList<Integer> dominantSetsExtraction(RealMatrix A) {
		ArrayList<Integer> sel_list = new ArrayList<Integer>();
		ArrayList<Integer> rest_list = new ArrayList<Integer>();

		int N = A.getRowDimension();
		double[] x = new double[N];
		for (int i=0; i<N; i++) {
			x[i] = (double) 1/N;
		}

		RealMatrix X = MatrixUtils.createRowRealMatrix(x);
		RealMatrix X_T = MatrixUtils.createColumnRealMatrix(x);
		RealMatrix mult = X.multiply(A).multiply(X_T);

		double f_0 = mult.getEntry(0, 0); //mult contains 1 element
		double f_1 = 0;
		double f_2 = f_0;

		RealMatrix X2 = MatrixUtils.createRowRealMatrix(x); //X
		RealMatrix X2_T = MatrixUtils.createColumnRealMatrix(x);

		while ( (double)(f_2-f_1)/f_0 > 0.00000001 ) {
			f_1 = f_2;

			RealMatrix mult1 = X2.multiply(A); // elements: [1 x nFeatures]
			RealMatrix mult2 = X2.multiply(A).multiply(X2_T); // elements: [1x1]
//			// // // System.out.println(mult1.toString());
//			// // // System.out.println(mult2.toString());

			double mult2_val = mult2.getEntry(0, 0);

			RealMatrix div = mult1.scalarMultiply((double)1/mult2_val);
//			// // // System.out.println(div.toString());

//			RealMatrix multiplication = MatrixUtils.createRealMatrix(X2.getData());
//			// // // System.out.println(X2.toString());
			for (int i=0; i<X2.getRowDimension(); i++) {
				for (int j=0; j<X2.getColumnDimension(); j++) {
					double elem1 = X2.getEntry(i, j);
					double elem2 = div.getEntry(i, j);

					double m = elem1 * elem2;

					X2.setEntry(i, j, m);
				}
			}

//			// // // System.out.println(X2.toString());

			X2.multiply(A).multiply(X2_T);
			X2_T = X2.transpose();

			RealMatrix ff = X2.multiply(A).multiply(X2_T);
			f_2 = ff.getEntry(0, 0);
		}

		X = X2;
		double[] X_data = X.getData()[0];
		double max_X = ArrayUtils.max(X_data);
		for (int i=0; i<X_data.length; i++) {
			if (X_data[i] > (0.05 * max_X)) {
				int sel_ind = i;
				sel_list.add(sel_ind);
			}
			else {
				int rest_ind = i;
				rest_list.add(rest_ind);
			}
		}

		// // // System.out.println("sel_list: "+sel_list);
		// // // System.out.println("rest_list: "+rest_list);
		
		return sel_list;
	}

	/**
	 * method that writes graph to file in order to use it as an input in ModularityOptimizer
	 * @param graph
	 * @param fileName
	 */
	public void writeGraphToFile(double[][] graph, String fileName) {
		try {
			Utilities.graphToFile(graph, fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public double[][] getGraphWeights() {
		return graphWeights;
	}

	public void setGraphWeights(double[][] graphWeights) {
		this.graphWeights = graphWeights;
	}

	public int getNo_groups() {
		return no_groups;
	}

	public void setNo_groups(int no_groups) {
		this.no_groups = no_groups;
	}

	public ArrayList<Integer> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Integer> groups) {
		this.groups = groups;
	}
}
