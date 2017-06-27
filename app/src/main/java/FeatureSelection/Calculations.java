package FeatureSelection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Calculations {

	
	public static double[] priorProbability(ArrayList<Double> labels){
		ArrayList<Double> unique = new ArrayList<Double>(labels);
		Set<Double> hs = new HashSet<Double>();
		hs.addAll(unique);
		unique.clear();
		unique.addAll(hs);
		
		int[] size = new int[unique.size()];
		double[] priors = new double[unique.size()];
		Arrays.fill(size, 0);
		Arrays.fill(priors, 0);
		
		for (int k=0; k<unique.size(); k++){
			for (int i=0; i<labels.size(); i++){
//				if (labels.get(i) == unique.get(k)){
		//		if (Math.abs(labels.get(i) - unique.get(k)) == 0){
				if (labels.get(i).doubleValue() == unique.get(k).doubleValue()) {
					size[k] = size[k] + 1;
				}
			}
			priors[k] = (double)size[k] / labels.size();
		}
		
		return priors;
	}
}
