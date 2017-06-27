package Graph_NC_RE;

import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Graph_NC_RE {
	private double delta;
	private double omega;
	private int no_groups;
	private ArrayList<Integer> groups;
	private double[][] graphWeights;
	private ArrayList<Integer> reduced_set;
	
	public Graph_NC_RE(double delta, double omega, int no_groups, ArrayList<Integer> groups, double[][] graphWeights) {
		super();
		this.delta = delta;
		this.omega = omega;
		this.no_groups = no_groups;
		this.groups = groups;
		this.graphWeights = graphWeights;
//		this.A = MatrixUtils.createRealMatrix(graphWeights);
		this.reduced_set = new ArrayList<Integer>();
	}

	public Graph_NC_RE() {
		super();
	}
	
	public void core() {
		ArrayList<Integer> rem_features = new ArrayList<Integer>();
		
		ArrayList<Integer> cluster_flag = new ArrayList<Integer>();
		for (int i=0; i<no_groups; i++) cluster_flag.add(i, 1);
		// // System.out.println(cluster_flag);
		
		while (cluster_flag.contains(1)) {
			 // System.out.println("CORE");
			for (int g=0; g<no_groups; g++) {
				for (int i=0; i<no_groups; i++) cluster_flag.set(i, 0);
				
				ArrayList<Integer> cluster = new ArrayList<Integer>();
				for (int i=0; i<groups.size(); i++) {
					if (groups.get(i) == g) cluster.add(i);
				}
				 // System.out.println("cluster "+cluster);
				 // System.out.println("rem_features "+rem_features);
				Collections.reverse(rem_features);
				for (int i=0; i<rem_features.size(); i++) {
					int elem = rem_features.get(i);
					if (cluster.contains(elem))
						cluster.remove(cluster.indexOf(elem));
				}
//				 // System.out.println("clusteraft "+cluster);
				if (cluster.size() > 0) {
					ArrayList<Integer> candidate_list = new ArrayList<Integer>();
//					RealMatrix subA = A.getSubMatrix(Utilities.listToArrayInt(cluster), Utilities.listToArrayInt(cluster));
					double[][] subA_data;

					if (cluster.size() == 1)
						subA_data = graphWeights;
					else
						subA_data = Matrix.getSubMatrix(graphWeights, Utilities.listToArrayInt(cluster), Utilities.listToArrayInt(cluster));
//					Utilities.print2dArray(subA_data);

					double[] LC = Metrics.LC(subA_data);
					for (int i=0; i<LC.length; i++) ;// // // // // System.out.println("lc"+LC[i]);
					double[] RE = Metrics.RE(subA_data);

					double[] influence = new double[cluster.size()];
					for (int i=0; i<cluster.size(); i++) {
						influence[i] = LC[i] * RE[i];
					}
					
					Median m = new Median();
					double median_inf = m.evaluate(influence);
					// // System.out.println("median_inf:"+median_inf);
					
					ArrayList<Integer> ind = new ArrayList<Integer>();
					for (int i=0; i<influence.length; i++) {
						if (influence[i] < median_inf) {
							candidate_list.add(cluster.get(i));
							ind.add(i);
						}
					}

					// // System.out.println("candidate_list:"+candidate_list);
					this.omega = cluster.size()/4;
//					if ((length(tmpfeatsind) - length(cand_list)) >= omega) && ~isempty(ind)
					if ( (cluster.size() - candidate_list.size() >= this.omega) && (ind.isEmpty() == false) ) {
						rem_features.addAll(candidate_list);
						cluster_flag.set(g, 1);
					}
					// // System.out.println("rem_features "+rem_features);
					// // System.out.println("cluster_flag "+cluster_flag);
				}
			}
			
//			break;
		}
		
		ArrayList<Integer> groups0 = new ArrayList<Integer>();
		for (int i=0; i<groups.size(); i++) {
			if (groups.get(i) == 0)
				groups0.add(i);
		}
		// // System.out.println("groups0: "+groups0);
		// // System.out.println("rem_features: "+rem_features);
		
		groups0.addAll(rem_features);
		Set<Integer> unique_set = new HashSet<Integer>(groups0);
		ArrayList<Integer> unique = new ArrayList<Integer>(unique_set);
		// // System.out.println("unique: "+unique);
		
		ArrayList<Integer> cluster_leaders = Utilities.rangeArrayList(0, graphWeights.length);
		// // System.out.println("bef:"+cluster_leaders);
		Collections.sort(unique);
		Collections.reverse(unique);
		for (int i=0; i<unique.size(); i++) {
			cluster_leaders.remove(unique.get(i));
		}
		
		// // System.out.println("aft:"+cluster_leaders);
		this.reduced_set = cluster_leaders;
	}

	public ArrayList<Integer> getReduced_set() {
		return reduced_set;
	}

	public void setReduced_set(ArrayList<Integer> reduced_set) {
		this.reduced_set = reduced_set;
	}
}
