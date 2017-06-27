package GCNC;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import Louvain.ModularityOptimizer;

public class Preprocessing {
	private HashMap<String, ArrayList<Double>> featureSet;
	private String[] featureNames;
	private double theta;
	private ArrayList<Integer> clusters;
	private double[][] graphWeights;
	private int no_groups;
	private ArrayList<Integer> groups;
	private HashMap<Integer, Set<Integer>> graphClusters;

	public Preprocessing(HashMap<String, ArrayList<Double>> featureSet, String[] featureNames, double theta) {
		super();
		this.featureSet = featureSet;
		this.featureNames = featureNames;
		this.theta = theta;
		this.clusters = new ArrayList<Integer>();		
		this.graphWeights = new double[featureSet.size()][featureSet.size()];
		this.graphClusters = new HashMap<Integer, Set<Integer>>();
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
					System.out.println("????");
					int min_sz = Math.min(feat_i.size(), feat_j.size());
					feat_i = Utilities.subList(feat_i, min_sz);
					feat_j = Utilities.subList(feat_j, min_sz);
				}

				// compute pearsons coeff between two feature vectors
				double corr = Metrics.pearsonsCoeff(Utilities.listToArray(feat_i), Utilities.listToArray(feat_j));
				if (i==j) corr = 0;
//				System.out.println(corr);
				
				graphWeights[i][j] = corr;
				graphWeights[j][i] = corr;
			}
		}
		
//		Utilities.print2dArray(graphWeights);
		
		/*
		 *  loop to update weights to range [0 1] using softmax scaling
		 */	
		for (int i=0; i<graphWeights.length; i++) {
			double[] weights_from_i = graphWeights[i];
			
			double[] new_weights = updateWeights(i, weights_from_i);
			graphWeights[i] = new_weights;
		}
//		Utilities.print2dArray(graphWeights);
		removeEdgesTheta(this.theta);

//		int c=0;
//		for (int i=0; i<graphWeights.length; i++) {
//			for (int j=0; j<graphWeights[i].length; j++) {
//				double w = graphWeights[i][j];
// 				if (w == 0)
//					c++;
//			}
//		}
//		if (c == graphWeights.length * graphWeights.length) {
//			System.out.println("all are zero");
//			for (int i=0; i<graphWeights.length; i++) {
//				for (int j = 0; j < graphWeights[i].length; j++) {
//					graphWeights[i][j] = 0.000001;
//				}
//			}
//		}
		
//		Utilities.print2dArray(graphWeights);
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
	
	/**
	 * Given node i, its corresponding weights and a threshold theta
	 * removes all edges whose weights are below theta
	 * @param theta the threshold
	 */
	public void removeEdgesTheta(double theta) {
		for (int i=0; i<graphWeights.length; i++) {
			for (int j=0; j<graphWeights[i].length; j++) {
				double w = graphWeights[i][j];
				
				if (w < theta) {
					graphWeights[i][j] = 0;
				}
			}
		}
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
	
	/**
	 * method that performs Louvain Community Detection algorithm on graph
	 * @return the list of clusters for the graph
	 */
	public ArrayList<Integer> louvainClustering(int dev_id) {
		ModularityOptimizer modularity = new ModularityOptimizer();
		
//		// init args
//		String[] arguments = new String[] {"/storage/sdcard0/features_graph.txt", 			/* inputFileName */
//				"/storage/sdcard0/features_communities.txt", 	/* outputFileName */
//				"1", 								/* modularityFunction */
//				"1.0", 								/* resolution */
//				"1", 								/* algorithm */
//				"10", 								/* nRandomStarts */
//				"10", 								/* nIterations */
//				"0", 								/* randomSeed */
//				"0" 								/* printOutput */
//		};

//		if (dev_id != -1)
		String[] arguments = new String[] {"/storage/sdcard0/features_graph"+dev_id+".txt", 			/* inputFileName */
											"/storage/sdcard0/features_communities"+dev_id+".txt", 	/* outputFileName */
											"1", 								/* modularityFunction */
											"1.0", 								/* resolution */
											"1", 								/* algorithm */
											"10", 								/* nRandomStarts */
											"10", 								/* nIterations */
											"0", 								/* randomSeed */
											"0" 								/* printOutput */
								};
//		else
//			arguments= new String[] {"/storage/sdcard0/features_graph.txt", 			/* inputFileName */
//					"/storage/sdcard0/features_communities.txt", 	/* outputFileName */
//					"1", 								/* modularityFunction */
//					"1.0", 								/* resolution */
//					"1", 								/* algorithm */
//					"10", 								/* nRandomStarts */
//					"10", 								/* nIterations */
//					"0", 								/* randomSeed */
//					"0" 								/* printOutput */
//			};

		// call louvain clustering
		try {
			this.clusters = ModularityOptimizer.runOptimizer(arguments);
			graphClusters();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.clusters;
	}
	
	public void graphClusters() {
		Set<Integer> uniqueClusters = new HashSet<Integer>(clusters);
		
		for (int i=0; i<uniqueClusters.size(); i++) {
			graphClusters.put(i, new TreeSet<Integer>());
		}
		
		System.out.println("Unique count: " + uniqueClusters.size());
		
		for (int i=0; i<clusters.size(); i++) {
			int c = clusters.get(i);
			
			graphClusters.get(c).add(i);
		}
		
		System.out.println("graphClusters::::::: "+graphClusters);
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

	public HashMap<Integer, Set<Integer>> getGraphClusters() {
		return graphClusters;
	}

	public void setGraphClusters(HashMap<Integer, Set<Integer>> graphClusters) {
		this.graphClusters = graphClusters;
	}
}
