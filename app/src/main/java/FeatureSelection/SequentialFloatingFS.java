package FeatureSelection;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import Utilities.CollectionUtilities;

public class SequentialFloatingFS {
	private HashMap<String, ArrayList<Double>> features;
	private ArrayList<Double> labels;
	
	public SequentialFloatingFS(HashMap<String, ArrayList<Double>> features, ArrayList<Double> labels){
		this.features = features;
		this.labels = labels;
	}

	public static double correlationCriterion(
			HashMap<String, ArrayList<Double>> featureVec, ArrayList<Double> labels, 
			String[] keyset, int index){
		
		// convert ArrayLists to arrays for Pearsons correlation
		double[] labels_arr = CollectionUtilities.listToArray(labels);
		
		PearsonsCorrelation p = new PearsonsCorrelation();
		
		double class_corr = 0;
		double[] priors = Calculations.priorProbability(labels);
		for (int i=0; i<featureVec.size(); i++){
			// get i-th element
			ArrayList<Double> elem_i = featureVec.get(keyset[i]);
			// convert to array for Pearsons correlation
			double[] elem_i_arr = CollectionUtilities.listToArray(elem_i);

			for (int j=0; j<priors.length; j++){
				class_corr =+ (priors[j])*p.correlation(labels_arr, elem_i_arr);
			}
		}
		
		double feature_corr = 0;
		
		if (featureVec.size() <= 1) {
			feature_corr = 1.0;
			double corr = class_corr / feature_corr;
		}
		else {
			for (int i=0; i<featureVec.size(); i++){
				for (int j=i+1; j<featureVec.size(); j++){
					// get features
					ArrayList<Double> element_i = featureVec.get(keyset[i]);
					ArrayList<Double> element_j = featureVec.get(keyset[j]);
										
					// convert to arrays for Pearsons correlation
					double[] element_i_arr = CollectionUtilities.listToArray(element_i);
					double[] element_j_arr = CollectionUtilities.listToArray(element_j);
					
					Mean m = new Mean();
					double m1 = m.evaluate(element_i_arr);
					double m2 = m.evaluate(element_j_arr);
					
					double v1 = 0, v2 = 0;
					for (int k=0; k<element_i_arr.length; k++){
						v1 += Math.pow((element_i_arr[k] - m1), 2);
						v2 += Math.pow((element_j_arr[k] - m1), 2);
					}
					v1 = Math.sqrt(v1);
					v2 = Math.sqrt(v2);
					if (v1 != 0 && v2 !=0)
						feature_corr += p.correlation(element_i_arr, element_j_arr);
					else 
						feature_corr = 1;
					
				}
			}
		}
		
		if (class_corr < 0)
			class_corr *= (-1);

		if (feature_corr < 0)
			feature_corr *= (-1);
		double corr = class_corr/feature_corr;
		
		return corr;
	}
	
	public HashMap<String, ArrayList<Double>> SFFS(boolean print_steps){
	
		//initialization
		HashMap<String, ArrayList<Double>> feature_subset = new HashMap<String, ArrayList<Double>>();
		HashMap<String, ArrayList<Double>> features_copy = new HashMap<String, ArrayList<Double>>(features);
		int k = 0;
		int f1=0, f2=0, sum_flags=0, iterations=0;
		double corr_last=0;
		
		while(iterations<30 && sum_flags!=2) {
//			System.out.println("iterations: "+iterations+" sum_flags: "+sum_flags);
			//Step 1: Inclusion
			if (print_steps)
				System.out.println("Inclusion from features: "+features_copy);
			
			TreeSet<String> keyset = new TreeSet<String>(features_copy.keySet());
			String[] keyset_arr = keyset.toArray(new String[keyset.size()]);
			
			String best_feat_key = "";
			ArrayList<Double> best_feat_val = new ArrayList<Double>();
			
//			if (features_copy.size() > 0){
				HashMap<String, ArrayList<Double>> feat_subset_tmp = new HashMap<String, ArrayList<Double>>();
//				feature_subset.forEach(feat_subset_tmp::putIfAbsent);
			feat_subset_tmp = Utilities.CollectionUtilities.cloneMap(feature_subset);

				feat_subset_tmp.put(keyset_arr[0], features_copy.get(keyset_arr[0]));
				
				TreeSet<String> keyset_tmp = new TreeSet<String>(feat_subset_tmp.keySet());
				String[] keyset_tmp_arr = keyset_tmp.toArray(new String[keyset_tmp.size()]);
				
				double criterion_func_max = correlationCriterion(feat_subset_tmp, labels, keyset_tmp_arr, 0);
				
				HashMap<String, ArrayList<Double>> best_feat = new HashMap<String, ArrayList<Double>>();
				best_feat.put(keyset_arr[0], features_copy.get(keyset_arr[0]));
				best_feat_key = keyset_arr[0];
				best_feat_val = best_feat.get(keyset_arr[0]);
				
				if (features_copy.size() > 1){
					for (int i=1; i<features_copy.size(); i++){
						feat_subset_tmp = new HashMap<String, ArrayList<Double>>(feature_subset);
						feat_subset_tmp.put(keyset_arr[i], features_copy.get(keyset_arr[i]));
						
						keyset_tmp = new TreeSet<String>(feat_subset_tmp.keySet());
						keyset_tmp_arr = keyset_tmp.toArray(new String[keyset_tmp.size()]);
						
						double criterion_func_eval = correlationCriterion(feat_subset_tmp, labels, keyset_tmp_arr, i);
						
						if (criterion_func_eval > criterion_func_max){
							criterion_func_max = criterion_func_eval;
							best_feat.put(keyset_arr[i], features_copy.get(keyset_arr[i]));
							best_feat_key = keyset_arr[i];
							best_feat_val = best_feat.get(keyset_arr[i]);
						}
					}
				}
				if ((corr_last < criterion_func_max) && (features_copy.size() > 0)) {
					features_copy.remove(best_feat_key);//, best_feat_val);
					feature_subset.put(best_feat_key, best_feat_val);
					corr_last = criterion_func_max;
					f1=0;
				}
				else
					f1=1;
				
				if (print_steps){
					System.out.println("--------------------------------------------");
					System.out.println("include: "+best_feat+" --> feature_subset: "+feature_subset);
				}
				
				TreeSet<String> keyset_sub = new TreeSet<String>(feature_subset.keySet());
				String[] keyset_sub_arr = keyset_sub.toArray(new String[keyset_sub.size()]);
	
				//Step 2: Conditional Exclusion
				ArrayList<Double> worst_feature_val = null;
				int worst_feature_index = (int) Double.NaN;
				if (feature_subset.size() > 2) {
					keyset_sub = new TreeSet<String>(feature_subset.keySet());
					keyset_sub_arr = keyset_sub.toArray(new String[keyset_sub.size()]);
					
					criterion_func_max = correlationCriterion(feature_subset, labels, keyset_sub_arr, feature_subset.size());
					
					for (int i=feature_subset.size()-1; i>0; i--){
						HashMap<String, ArrayList<Double>> tmp = new HashMap<String, ArrayList<Double>>();
						tmp.putAll(feature_subset);
						tmp.remove(keyset_sub_arr[i]);//, feature_subset.get(keyset_sub_arr[i]));
						
						keyset_tmp = new TreeSet<String>(tmp.keySet());
						keyset_tmp_arr = keyset_tmp.toArray(new String[keyset_tmp.size()]);
						double criterion_func_eval = correlationCriterion(tmp, labels, keyset_tmp_arr, i);
						
						if (criterion_func_eval > criterion_func_max){						
							criterion_func_max = criterion_func_eval;
							worst_feature_index = i;
							worst_feature_val = feature_subset.get(keyset_sub_arr[worst_feature_index]);
						}
					}
					if (worst_feature_val != null){
						if ((corr_last < criterion_func_max) && (feature_subset.size() > 0)) {
							feature_subset.remove(keyset_sub_arr[worst_feature_index]);//, worst_feature_val);
							corr_last = criterion_func_max;
							f2=0;
						}
						else
							f2=1;
					}
				}
				if (print_steps){
					System.out.println("-----------------------------------------------------");
					System.out.println("exclude: "+worst_feature_val+" --> feature_subset: "+feature_subset);
				}
				sum_flags = f1+f2;
//			}
			
			iterations++;
		}
		
		
		return feature_subset;
	}
}
