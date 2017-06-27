package GCNC;

import org.apache.commons.math3.stat.StatUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class GCNC {

	private HashMap<String, ArrayList<Double>> featureSet;
	private Map<Integer, Set<Integer>> reducedClusters;
	private String[] featureNames;
	private int nPatterns;
	private double[][] graph;
	private HashMap<Integer, Set<Integer>> clusters;
	private double delta;
	private double omega;
	private ArrayList<Integer> reducedSet;
	
	public GCNC(HashMap<String, ArrayList<Double>> featureSet, String[] featureNames,
				int nPatterns,
				double[][] graph,
				HashMap<Integer, Set<Integer>> clusters,
				double delta, double omega) {
		super();
		this.featureSet = featureSet;
		this.featureNames = featureNames;
		this.nPatterns = nPatterns;
		this.graph = graph;
		this.clusters = clusters;
		this.delta = delta;
		this.omega = omega;
		
		this.reducedClusters = new HashMap<Integer, Set<Integer>>();
		this.reducedSet = new ArrayList<Integer>();
	}

	public void core() {
		/**
		 * compute Term Variance (TV) for each feature
		 */
		double[] TV = new double[featureNames.length];
		for (int i=0; i<featureNames.length; i++) {
			ArrayList<Double> featureVec = new ArrayList<Double>();
			featureVec = featureSet.get(featureNames[i]);
			double tv = Metrics.TV(featureVec);
			TV[i] = tv;
		}
		
		Map<Integer, Set<Integer>> clusters_prev = new HashMap<Integer, Set<Integer>>();
		Map<Integer, Set<Integer>> clusters_curr = new HashMap<Integer, Set<Integer>>();

		System.out.println("clusters : "+clusters);

		for(Entry<Integer, Set<Integer>> entry : clusters.entrySet()){
		    clusters_curr.put(entry.getKey(), new HashSet<Integer>(entry.getValue()));
		}

		
		double[] LC = new double[featureNames.length];
		int c=0;
		while (!clusters_curr.equals(clusters_prev)) {
			/**
			 * compute Laplacian Centrality (LC) for each feature in its own cluster
			 */
//			System.out.println("ITERATIONS =============================== "+ c++);
			for (int i=0; i<featureNames.length; i++) {
				ArrayList<Double> featureVec = new ArrayList<Double>();
				featureVec = featureSet.get(featureNames[i]);
				
				int cluster = i;
				Set<Integer> nodesInCluster = clusters.get(cluster);
			}
			LC = Metrics.LC(graph);

			for(Entry<Integer, Set<Integer>> entry : clusters_curr.entrySet()){
			    clusters_prev.put(entry.getKey(), new HashSet<Integer>(entry.getValue()));
			}

			/**
			 * for each cluster
			 */
			for (int k=0; k<clusters_curr.size(); k++) {
				ArrayList<Integer> candidateList = new ArrayList<Integer>();
				Set<Integer> ckNodes = clusters_curr.get(k);
				
				/**
				 * for each feature in cluster k
				 * compute the influence value for the feature
				 */
				List<Integer> ckNodesList = new ArrayList<Integer>(ckNodes);
				double[] influences = new double[ckNodesList.size()];
			    for (int l=0; l<ckNodesList.size(); l++) {
					int f = ckNodesList.get(l);
					double tv_f = TV[f];
					double lc_f = LC[f];
					double influence = tv_f * lc_f;
					influences[l] = influence;
				}
			    
			    double delta_threshold = StatUtils.percentile(influences, 20);
			    this.delta = delta_threshold;
			    for (int l=0; l<ckNodesList.size(); l++) {
			    	int f = ckNodesList.get(l);
			    	if (influences[l] < delta_threshold) {
						candidateList.add(f);
					}
			    }
			    
			    /**
				 * if clusterSize - candidateList > omega (minumum number of features allowed)
				 * then remove all candidate list features from cluster k
				 */
				int clusterSize = ckNodes.size();
				int candidateListSize = candidateList.size();
				this.omega = clusterSize/4;
				if ( (clusterSize - candidateListSize) > this.omega ) {
					for (int j=0; j<candidateListSize; j++) {
						ckNodes.remove(candidateList.get(j));
					}
				}

			    clusters_curr.put(k, new HashSet<Integer>());
				clusters_curr.put(k, ckNodes);
			}
		}
		
		for(Entry<Integer, Set<Integer>> entry : clusters_curr.entrySet()){
		    this.reducedClusters.put(entry.getKey(), new HashSet<Integer>(entry.getValue()));
		}
	}

	/**
	 * Getters and Setters
	 */
	
	public HashMap<String, ArrayList<Double>> getFeatureSet() {
		return featureSet;
	}

	public void setFeatureSet(HashMap<String, ArrayList<Double>> featureSet) {
		this.featureSet = featureSet;
	}

	public String[] getFeatureNames() {
		return featureNames;
	}

	public void setFeatureNames(String[] featureNames) {
		this.featureNames = featureNames;
	}
	
	public int getnPatterns() {
		return nPatterns;
	}

	public void setnPatterns(int nPatterns) {
		this.nPatterns = nPatterns;
	}


	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public double getOmega() {
		return omega;
	}

	public void setOmega(double omega) {
		this.omega = omega;
	}
	
	public Map<Integer, Set<Integer>> getReducedClusters() {
		return reducedClusters;
	}

	public void setReducedClusters(Map<Integer, Set<Integer>> reducedClusters) {
		this.reducedClusters = reducedClusters;
	}
	
	public ArrayList<Integer> getReducedSet() {
		for (int i=0; i<this.reducedClusters.size(); i++) {
			Set<Integer> cluster = this.reducedClusters.get(i);
			this.reducedSet.addAll(cluster);
		}
		System.out.println("reducedSet: "+this.reducedSet);
		return reducedSet;
	}

	public void setReducedSet(ArrayList<Integer> reducedSet) {
		this.reducedSet = reducedSet;
	}
}
