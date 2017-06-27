package Utilities;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class CollectionUtilities {

	public static double mean(double[] m) {
		double sum = 0;
		for (int i = 0; i < m.length; i++) {
			sum += m[i];
		}
		return sum / m.length;
	}

	public static double getMaxOccurrence(ArrayList<Double> a) {
		int count = 1, tempCount;
		double popular = a.get(0);
		double temp = 0;
		for (int i = 0; i < (a.size() - 1); i++) {
			temp = a.get(i);
			tempCount = 0;
			for (int j = 1; j < a.size(); j++) {
				if (temp == a.get(j))
					tempCount++;
			}
			if (tempCount > count) {
				popular = temp;
				count = tempCount;
			}
		}
		return popular;
	}

	public static int getMaxOccurrenceInt(ArrayList<Integer> a) {
		int count = 1, tempCount;
		int popular = a.get(0);
		int temp = 0;
		for (int i = 0; i < (a.size() - 1); i++) {
			temp = a.get(i);
			tempCount = 0;
			for (int j = 1; j < a.size(); j++) {
				if (temp == a.get(j))
					tempCount++;
			}
			if (tempCount > count) {
				popular = temp;
				count = tempCount;
			}
		}
		return popular;
	}
	
	public static HashMap<Integer, ArrayList<Double>> transposeHashMap(HashMap<Integer, ArrayList<Double>> data) {
		HashMap<Integer, ArrayList<Double>> dataT = new HashMap<Integer, ArrayList<Double>>();
		//transpose data to get data for each sensor channel
	    for (int i=0; i<data.get(0).size(); i++){
	    	ArrayList<Double> tmp = new ArrayList<Double>();
	    	for (int j=0; j<data.size(); j++){
	    		tmp.add(data.get(j).get(i));	
	    	}
	    	dataT.put(i, tmp);
	    }
	    return dataT;
	}
	
	/**
	 * Method that merges 2 data structures into 1
	 * 
	 * @param featureSet:
	 *            the structure containing all features
	 * @param featureSet2:
	 *            the structure containing Pairwise Correlation features
	 * @return the concatenated data structure
	 */
	public static HashMap<Integer, ArrayList<HashMap<String, Double>>> mergeStructures(
			HashMap<Integer, ArrayList<HashMap<String, Double>>> featureSet,
			ArrayList<ArrayList<HashMap<String, Double>>> featureSet2) {

		HashMap<Integer, ArrayList<HashMap<String, Double>>> featureSet_final = new HashMap<Integer, ArrayList<HashMap<String, Double>>>();

		for (int i = 0; i < featureSet.size(); i++) {
			ArrayList<HashMap<String, Double>> featuresPerChann = featureSet.get(i);
			ArrayList<HashMap<String, Double>> featuresPerChann2 = featureSet2.get(i);
			if (featuresPerChann2 == null)
				continue;

			ArrayList<HashMap<String, Double>> featuresPerChann_final = new ArrayList<HashMap<String, Double>>();

			for (int ii = 0; ii < featuresPerChann.size(); ii++) {
				HashMap<String, Double> h1 = new HashMap<String, Double>();
				HashMap<String, Double> h2 = new HashMap<String, Double>();
				// System.out.println("s:: "+String.format("%03d", ii));
				h1 = featuresPerChann.get(ii);
				for (int j = 0; j < featuresPerChann2.size(); j++) {
					h2 = featuresPerChann2.get(j);
					// System.out.println("h2:"+h2);
					Iterator it = h2.keySet().iterator();
					String s = (String) it.next();
//					String s = h2.keySet().toString();

					if (s.contains("s" + String.format("%04d", ii))) {
//						 System.out.println("sss"+s);
						String new_s = s;//.substring(1, 15);
						if (h2.get(new_s) != null) {
							double v = h2.get(new_s);
							HashMap<String, Double> h = new HashMap<String, Double>();
							h.put(new_s.substring(0, 9)+new_s.substring(14,16), v);
							h1.putAll(h);
						}
					}
				}
				featuresPerChann_final.add(h1);

			}
			featureSet_final.put(i, featuresPerChann_final);
		}

		return featureSet_final;
	}
	
	public static double[] listToArray(ArrayList<Double> list) {
		double[] valuesToArray = new double[list.size()];
		for (int i = 0; i < list.size(); i++) {
			valuesToArray[i] = list.get(i);
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

	public static ArrayList<Integer> arrayToList(int[] array) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			list.add(i, array[i]);
		}
		return  list;
	}

	public static double[] arraySubList(ArrayList<Double> list, int sz) {
		double[] valuesToArray = new double[sz];
		for (int i = 0; i < sz; i++) {
			valuesToArray[i] = list.get(i);
		}
		return valuesToArray;
	}
	
	public static void print2dArray(double[][] array) {
		for (int i=0; i<array.length; i++) {
			System.out.print(i+" -> ");
			for (int j=0; j<array[i].length; j++) {
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
	}

	public static HashMap<String, ArrayList<Double>> cloneMap(HashMap<String, ArrayList<Double>> a) {
		HashMap<String, ArrayList<Double>> b = new HashMap<String, ArrayList<Double>>();

		for(Map.Entry<String, ArrayList<Double>> entry : a.entrySet()){
			b.put(entry.getKey(), new ArrayList<Double>(entry.getValue()));
		}

		return b;
	}

	public static double[][] hashMaptoArrayTransposed(HashMap<String, ArrayList<Double>> featureSet, String[] featureNames) {
		double[][] features_arr = new double[featureSet.get(featureNames[0]).size()][featureNames.length];
		for (int i=0; i<featureSet.size(); i++) {
			String str = featureNames[i];
			ArrayList<Double> values = featureSet.get(str);

			double[] values_arr = new double[values.size()];
			for (int j=0; j<values.size(); j++) {
				features_arr[j][i] = values.get(j);
			}
		}

		return features_arr;
	}

	public static ArrayList<Integer> rangeArrayList(int start, int end) {
		ArrayList<Integer> a = new ArrayList<Integer>(end-start);

		for (int i=start; i<end; i++) {
			a.add(i);
		}

		return a;
	}

	public static HashMap<String, ArrayList<Double>> getDominantFeatures(HashMap<String, ArrayList<Double>> featureSet,
										   String[] featureNames,
										   ArrayList<Integer> reduced_set) {
		HashMap<String, ArrayList<Double>> reducedFeatureSet = new HashMap<String, ArrayList<Double>>();
		for (int i=0; i<reduced_set.size(); i++) {
			int feature_ind = reduced_set.get(i);
			String featureName = featureNames[feature_ind];
			ArrayList<Double> featureValues = featureSet.get(featureName);
			reducedFeatureSet.put(featureName, featureValues);
		}

		return reducedFeatureSet;
	}

	public static ArrayList<HashMap<String, ArrayList<Double>>> mergeDominantFeatures(
			ArrayList<HashMap<String, ArrayList<Double>>> f1,
			ArrayList<HashMap<String, ArrayList<Double>>> f2 ) {

		ArrayList<HashMap<String, ArrayList<Double>>> merged = new ArrayList<HashMap<String, ArrayList<Double>>>();
		for (int i=0; i<f1.size(); i++) {
			HashMap<String, ArrayList<Double>> mergeF = new HashMap<String, ArrayList<Double>>();
			mergeF.putAll(f1.get(i));
			mergeF.putAll(f2.get(i));
			merged.add(mergeF);

//			System.out.println("merged:::: "+ mergeF.size() + " " +mergeF.keySet() );
		}
//		System.out.println("merged:::: "+merged);

		return merged;
	}

	public static ArrayList<HashMap<String, ArrayList<Double>>> computePCInterLocation(
			ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawData1,
			ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawData2,
			ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawData3,
			ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawData4,
			ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawData5
	) {
		ArrayList<HashMap<String, ArrayList<Double>>> PC_features = new ArrayList<HashMap<String, ArrayList<Double>>>();

		// dev1
//		ArrayList<HashMap<String, ArrayList<Double>>> PC12= CollectionUtilities.computePairwiseInterLocation(rawData1, rawData2, 1, 2);
		ArrayList<HashMap<String, ArrayList<Double>>> PC13= CollectionUtilities.computePairwiseInterLocation(rawData1, rawData3, 1, 3);
		ArrayList<HashMap<String, ArrayList<Double>>> PC14= CollectionUtilities.computePairwiseInterLocation(rawData1, rawData4, 1, 4);
//		ArrayList<HashMap<String, ArrayList<Double>>> PC15= CollectionUtilities.computePairwiseInterLocation(rawData1, rawData5, 1, 5);

		// dev2
//		ArrayList<HashMap<String, ArrayList<Double>>> PC23= CollectionUtilities.computePairwiseInterLocation(rawData2, rawData3, 2, 3);
//		ArrayList<HashMap<String, ArrayList<Double>>> PC24= CollectionUtilities.computePairwiseInterLocation(rawData2, rawData4, 2, 4);
//		ArrayList<HashMap<String, ArrayList<Double>>> PC25= CollectionUtilities.computePairwiseInterLocation(rawData2, rawData5, 2, 5);

		// dev3
		ArrayList<HashMap<String, ArrayList<Double>>> PC34= CollectionUtilities.computePairwiseInterLocation(rawData3, rawData4, 3, 4);
//		ArrayList<HashMap<String, ArrayList<Double>>> PC35= CollectionUtilities.computePairwiseInterLocation(rawData3, rawData5, 3, 5);

		//dev4
//		ArrayList<HashMap<String, ArrayList<Double>>> PC45= CollectionUtilities.computePairwiseInterLocation(rawData4, rawData5, 4, 5);

		// merge all PC features
//		PC_features.addAll(PC12);
//		PC_features.addAll(PC13);
		PC_features = mergeDominantFeatures(PC13, PC14);
//		System.out.println("adding PC FEATURES 0SIZE: "+PC_features.get(0).size()+ "--" + PC_features.size());
		PC_features = mergeDominantFeatures(PC_features, PC34);
//		PC_features.addAll(PC14);
		System.out.println("adding PC FEATURES SIZE: "+PC_features.get(0).size()+ "--" + PC_features.size());
//		PC_features.addAll(PC15);
//
//		PC_features.addAll(PC23);
//		PC_features.addAll(PC24);
//		PC_features.addAll(PC25);

//		PC_features.addAll(PC34);
//		PC_features = mergeDominantFeatures(PC_features, PC34);
//		System.out.println("adding PC FEATURES SIZE: "+PC_features.get(0).size()+ "--" + PC_features.size());
//		PC_features.addAll(PC35);
//
//		PC_features.addAll(PC45);


		return PC_features;
	}

	public static ArrayList<HashMap<String, ArrayList<Double>>> computePairwiseInterLocation(
			ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawData1,
			ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawData2,
			int d1, int d2
	) {
		ArrayList<HashMap<String, ArrayList<Double>>> PC_features = new ArrayList<HashMap<String, ArrayList<Double>>>();
		// for each W
		for (int W=0; W<rawData1.size(); W++) {
			HashMap<Integer, ArrayList<ArrayList<Double>>> dataPerW1 = rawData1.get(W);
			HashMap<Integer, ArrayList<ArrayList<Double>>> dataPerW2 = rawData2.get(W);

			HashMap<String, ArrayList<Double>> pc_all_seg = new HashMap<String, ArrayList<Double>>();
			// for each modality for dev1
			for (int m1=0; m1<dataPerW1.size(); m1++) {
				ArrayList<ArrayList<Double>> segments_d1 = dataPerW1.get(m1);

				// for each modality for dev2
				for (int m2=0; m2<dataPerW2.size(); m2++) {
					ArrayList<ArrayList<Double>> segments_d2 = dataPerW2.get(m2);
					String key = "PCinter_"+d1+d2+"_"+m1+"_"+m2;

					int min_size = Math.min(segments_d1.size(), segments_d2.size());

					// for each data segment
					ArrayList<Double> pc_per_seg = new ArrayList<Double>();
					for (int s=0; s<min_size; s++) {
						ArrayList<Double> segment1 = segments_d1.get(s);
						ArrayList<Double> segment2 = segments_d2.get(s);

						double[] seg1, seg2;
						if (segment1.size() != segment2.size()) {
							int min_sz = Math.min(segment1.size(), segment2.size());
							seg1 = arraySubList(segment1, min_sz);
							seg2 = arraySubList(segment2, min_sz);
						}
						else {
							seg1 = listToArray(segment1);
							seg2 = listToArray(segment2);
						}

//						System.out.println("sizes: "+seg1.length + " - "+ seg2.length);

						PearsonsCorrelation p = new PearsonsCorrelation();
						double corr = p.correlation(seg1, seg2);

						pc_per_seg.add(corr);

					}

//					System.out.println("sz: "+pc_per_seg.size());
//					System.out.println("key: "+key+ " -- "+ W + " - "+m1 + " - "+m2 + " - "  + " --> "+pc_per_seg.size());
					ArrayList<Double> pc_per_seg_norm = zScore(pc_per_seg);
					pc_all_seg.put(key, pc_per_seg_norm);
				}
			}

			PC_features.add(pc_all_seg);
			TreeSet<String> featureN = new TreeSet<String>(pc_all_seg.keySet());
			System.out.println(pc_all_seg.keySet());
            String[] featureN_Arr = featureN.toArray(new String[featureN.size()]);
//			for (int i=0; i<pc_all_seg.size(); i++) {
////				for (int j=0; j<pc_all_seg.get(i).size(); j++) {
//					System.out.println(i+" "+ pc_all_seg.get(featureN_Arr[i]));
////				}
//
//				System.out.println("=========================================================");
//			}

		}
		return PC_features;
	}

	public static HashMap<String, ArrayList<Double>> computePCInterLocation(
			HashMap<Integer, ArrayList<ArrayList<Double>>> rawData1,
			HashMap<Integer, ArrayList<ArrayList<Double>>> rawData2,
			HashMap<Integer, ArrayList<ArrayList<Double>>> rawData3,
			HashMap<Integer, ArrayList<ArrayList<Double>>> rawData4,
			HashMap<Integer, ArrayList<ArrayList<Double>>> rawData5,
			int[] dom1, int[] dom2, int[] dom3, int[] dom4, int[] dom5
	) {
		HashMap<String, ArrayList<Double>> PC_features = new HashMap<String, ArrayList<Double>>();

		// dev1
//		ArrayList<HashMap<String, ArrayList<Double>>> PC12= CollectionUtilities.computePairwiseInterLocation(rawData1, rawData2, 1, 2);
		HashMap<String, ArrayList<Double>> PC13= CollectionUtilities.computePairwiseInterLocation(rawData1, rawData3, 1, 3, dom1, dom3);
		HashMap<String, ArrayList<Double>> PC14= CollectionUtilities.computePairwiseInterLocation(rawData1, rawData4, 1, 4, dom1, dom4);
//		ArrayList<HashMap<String, ArrayList<Double>>> PC15= CollectionUtilities.computePairwiseInterLocation(rawData1, rawData5, 1, 5);

		// dev2
//		ArrayList<HashMap<String, ArrayList<Double>>> PC23= CollectionUtilities.computePairwiseInterLocation(rawData2, rawData3, 2, 3);
//		ArrayList<HashMap<String, ArrayList<Double>>> PC24= CollectionUtilities.computePairwiseInterLocation(rawData2, rawData4, 2, 4);
//		ArrayList<HashMap<String, ArrayList<Double>>> PC25= CollectionUtilities.computePairwiseInterLocation(rawData2, rawData5, 2, 5);

		// dev3
		HashMap<String, ArrayList<Double>> PC34= CollectionUtilities.computePairwiseInterLocation(rawData3, rawData4, 3, 4, dom3, dom4);
//		ArrayList<HashMap<String, ArrayList<Double>>> PC35= CollectionUtilities.computePairwiseInterLocation(rawData3, rawData5, 3, 5);

		//dev4
//		ArrayList<HashMap<String, ArrayList<Double>>> PC45= CollectionUtilities.computePairwiseInterLocation(rawData4, rawData5, 4, 5);

		// merge all PC features
		PC_features = PC13;
		PC_features.putAll(PC14);
		PC_features.putAll(PC34);
//		PC_features.addAll(PC12);
//		PC_features.addAll(PC13);
//		PC_features = mergeDominantFeatures(PC13, PC14);
//		System.out.println("adding PC FEATURES 0SIZE: "+PC_features.get(0).size()+ "--" + PC_features.size());
//		PC_features.addAll(PC14);
//		PC_features = PC14;//.putAll(PC14);
//		System.out.println("adding PC FEATURES SIZE: "+PC_features.get(0).size()+ "--" + PC_features.size());
//		PC_features.addAll(PC15);
//
//		PC_features.addAll(PC23);
//		PC_features.addAll(PC24);
//		PC_features.addAll(PC25);

//		PC_features.addAll(PC34);
//		PC_features = mergeDominantFeatures(PC_features, PC34);
//		System.out.println("adding PC FEATURES SIZE: "+PC_features.get(0).size()+ "--" + PC_features.size());
//		PC_features.addAll(PC35);
//
//		PC_features.addAll(PC45);


		return PC_features;
	}


	public static HashMap<String, ArrayList<Double>> computePairwiseInterLocation(
			HashMap<Integer, ArrayList<ArrayList<Double>>> rawData1,
			HashMap<Integer, ArrayList<ArrayList<Double>>> rawData2,
			int d1, int d2, int[] dom1, int[] dom2
	) {
		HashMap<String, ArrayList<Double>> PC_features = new HashMap<String, ArrayList<Double>>();

			HashMap<String, ArrayList<Double>> pc_all_seg = new HashMap<String, ArrayList<Double>>();
			// for each dominant modality for dev1
			for (int m1=0; m1<dom1.length; m1++) {
				int ind1 = dom1[m1];

				ArrayList<ArrayList<Double>> segments_d1 = rawData1.get(ind1);

				// for each dominant modality for dev2
				for (int m2=0; m2<dom2.length; m2++) {
					int ind2 = dom2[m2];
//					System.out.println("ind1: "+ ind1+ " -- " + ind2);

					ArrayList<ArrayList<Double>> segments_d2 = rawData2.get(ind2);
					String key = "PCinter_"+d1+d2+"_"+ind1+"_"+ind2;

					int min_size = Math.min(segments_d1.size(), segments_d2.size());

					// for each data segment
					ArrayList<Double> pc_per_seg = new ArrayList<Double>();
					for (int s=0; s<min_size; s++) {
						ArrayList<Double> segment1 = segments_d1.get(s);
						ArrayList<Double> segment2 = segments_d2.get(s);

						double[] seg1, seg2;
						if (segment1.size() != segment2.size()) {
							int min_sz = Math.min(segment1.size(), segment2.size());
							seg1 = arraySubList(segment1, min_sz);
							seg2 = arraySubList(segment2, min_sz);
						}
						else {
							seg1 = listToArray(segment1);
							seg2 = listToArray(segment2);
						}

//						System.out.println("sizes: "+seg1.length + " - "+ seg2.length);

						PearsonsCorrelation p = new PearsonsCorrelation();
						double corr = p.correlation(seg1, seg2);

						pc_per_seg.add(corr);

					}

//					System.out.println("sz: "+pc_per_seg.size());
//					System.out.println("key: "+key+ " -- "+ W + " - "+m1 + " - "+m2 + " - "  + " --> "+pc_per_seg.size());
					ArrayList<Double> pc_per_seg_norm = zScore(pc_per_seg);
					pc_all_seg.put(key, pc_per_seg_norm);
				}
			}

			TreeSet<String> featureN = new TreeSet<String>(pc_all_seg.keySet());
//			System.out.println("keys:" +pc_all_seg.keySet());
			String[] featureN_Arr = featureN.toArray(new String[featureN.size()]);

		return pc_all_seg;
	}

	public static ArrayList<Double> zScore(ArrayList<Double> pc_per_seg) {
		double[] pc_per_seg_arr = listToArray(pc_per_seg);
		Mean m = new Mean();
		double mean = m.evaluate(pc_per_seg_arr);

		StandardDeviation st = new StandardDeviation();
		double std = st.evaluate(pc_per_seg_arr);

		for (int i=0; i<pc_per_seg.size(); i++) {
			pc_per_seg.set(i, (pc_per_seg.get(i)-mean)/std);
		}

		return pc_per_seg;
	}

	public static int[] getDominantModality(int key) {
		int[] modalities = new int[3];
		switch (key) {
			case 1: //stat acc
				modalities = new int[]{0, 1, 2};
				break;
			case 2: // stat gyro
				modalities = new int[]{3, 4, 5};
				break;
			case 3: // stat mag
				modalities = new int[]{6, 7, 8};
				break;
			case 4: // intra acc-acc
				modalities = new int[]{0, 1, 2};
				break;
			case 5: // intra gyro-gyro
				modalities = new int[]{3, 4, 5};
				break;
			case 6: // intra mag-mag
				modalities = new int[]{6, 7, 8};
				break;
			case 7: // inter acc-gyro
				modalities = new int[]{0, 1, 2, 3, 4, 5};
				break;
			case 8: // inter acc-mag
				modalities = new int[]{0, 1, 2, 6, 7, 8};
				break;
			case 9: // inter gyro-mag
				modalities = new int[]{3, 4, 5, 6, 7, 8};
				break;

		}
		return  modalities;
	}

	public static int[] getDominantModalities(ArrayList<Integer> streams, double threshold) {
		ArrayList<Integer> dominant_streams = new ArrayList<Integer>();
		Set<Integer> unique_streams = new HashSet<Integer>(streams);
//		System.out.println("all_streams: "+streams);
//		System.out.println("unique_streams: "+unique_streams);

		int size_streams = streams.size();

		ArrayList<Double> count_elements = new ArrayList<Double>();
		Iterator iter = unique_streams.iterator();
		while (iter.hasNext()) {
			int element = (int) iter.next();
			double perc = (double) Collections.frequency(unique_streams, element) / size_streams;
			count_elements.add(perc);
			if (perc >= threshold)
				dominant_streams.add(element);
		}

		int[] dom_streams = listToArrayInt(dominant_streams);

		// if no modalities exceed the theshold, keep all of them
		if (dom_streams.length == 0)
			dom_streams = listToArrayInt(streams);

//		System.out.println("count_elements "+count_elements);
//		System.out.println("dominant_streams "+dominant_streams);

		ArrayList<Integer> dominant_modalities = new ArrayList<Integer>();
		for (int i=0; i<dom_streams.length; i++) {
			int[] modalities = getDominantModality(dom_streams[i]);
//			System.out.println("dom_modalities of "+dom_streams[i]);
			for (int j=0; j<modalities.length; j++) {
//				System.out.println(modalities[j]);

				if (!dominant_modalities.contains(modalities[j]))
					dominant_modalities.add(modalities[j]);
			}
		}

//		System.out.println("dominant_modalities :: "+dominant_modalities);

		int[] dom_modalities = listToArrayInt(dominant_modalities);

		return dom_modalities;
	}

	public static int[] getDominantChannels(Set<String> keys, double threshold) {
		Iterator it = keys.iterator();
		ArrayList<Integer> dominant_channels = new ArrayList<Integer>();

		ArrayList<Integer> selected_channels = new ArrayList<Integer>();
		while (it.hasNext()) {
			String key = it.next().toString();
			int channel = -1;
			if (!key.contains("_"))  // statistical feature
				channel = Integer.parseInt(""+key.charAt(key.length()-3));
			else
				channel = Integer.parseInt(""+key.charAt(key.length()-4));
			selected_channels.add(channel);
		}
//		System.out.println("selected_channels: "+selected_channels);

		int size_channels = selected_channels.size();

		Set<Integer> unique_channels = new HashSet<Integer>(selected_channels);

		ArrayList<Double> count_elements = new ArrayList<Double>();


		Iterator iter = unique_channels.iterator();
		while (iter.hasNext()) {
			int element = (int) iter.next();
			double perc = (double) Collections.frequency(selected_channels, element) / size_channels;
			count_elements.add(perc);

			if (perc >= threshold)
				dominant_channels.add(element);
		}

		int[] dom_channels = listToArrayInt(dominant_channels);

		if (dom_channels.length == 0)
			dom_channels = listToArrayInt(selected_channels);

		System.out.println("count_elements "+count_elements);
		System.out.println("dominant_channels "+dominant_channels);

		return dom_channels;
	}

	public static ArrayList<HashMap<String, ArrayList<Double>>> retrieveFeaturesDominantChannels(
			ArrayList<HashMap<String, ArrayList<Double>>> features,
			ArrayList<ArrayList<Integer>> domChannels) {

		ArrayList<HashMap<String, ArrayList<Double>>> featureSubsets = new ArrayList<HashMap<String, ArrayList<Double>>>();

		for (int W=0; W<features.size(); W++) {
			ArrayList<Integer> domChannelsPerW = domChannels.get(W);
			HashMap<String, ArrayList<Double>> featuresPerW = features.get(W);

			TreeSet<String> featureNames = new TreeSet<String>(features.get(W).keySet());
			String[] featureNames_Arr = featureNames.toArray(new String[featureNames.size()]);

			ArrayList<String> dominantFeatures = new ArrayList<String>();
			for (int i=0; i<featureNames_Arr.length; i++) {
				String feat = featureNames_Arr[i];
//				System.out.println("features --------- " +feat);

				if (!feat.contains("PC")) {
					int channel = Integer.parseInt(feat.charAt(feat.length()-3)+"");
	//				System.out.println("channel :"+channel);

					if (domChannelsPerW.contains(channel)) {
						dominantFeatures.add(feat);
					}
				}
				else {
					int channel1 = Integer.parseInt(feat.charAt(feat.length()-6)+"");
					int channel2 = Integer.parseInt(feat.charAt(feat.length()-3)+"");

					if (domChannelsPerW.contains(channel1) && domChannelsPerW.contains(channel2)) {
						dominantFeatures.add(feat);
					}
				}

			}
			HashMap<String, ArrayList<Double>> subset = getFeatureSubset(featuresPerW, dominantFeatures);
			System.out.println("domChannelsPerW: "+domChannelsPerW);
			System.out.println("subset per W: "+subset.keySet());
			featureSubsets.add(subset);
		}
		return featureSubsets;
	}

	public static HashMap<String, ArrayList<Double>> getFeatureSubset(
			HashMap<String, ArrayList<Double>> features,
			ArrayList<String> featureNames) {

		HashMap<String, ArrayList<Double>> featureSubset = new HashMap<String, ArrayList<Double>>();

		for (int i=0; i<featureNames.size(); i++) {
			String key = featureNames.get(i);
			ArrayList<Double> value = features.get(key);

			featureSubset.put(key, value);
		}

		return featureSubset;
	}

	public static Instances createkNNInput(
			HashMap<String, ArrayList<Double>> featureSet,
			String[] featureNames_Arr, ArrayList<Double> labels) {

		// create attributes
		FastVector fv = new FastVector();
		for (int i = 0; i <= featureNames_Arr.length; i++) {
			if (i == featureNames_Arr.length) {
				fv.addElement(new Attribute("@@class@@"));
				continue;
			}
			fv.addElement(new Attribute(featureNames_Arr[i]));
		}

		ArrayList<ArrayList<Double>> dataset = new ArrayList<ArrayList<Double>>();
		for (int i=0; i<featureNames_Arr.length; i++) {
			String featureN = featureNames_Arr[i];
			ArrayList<Double> featureV = featureSet.get(featureN);

			dataset.add(featureV);
		}

		// transform dataset so that each line represents each window - add
		// class label as well
		ArrayList<ArrayList<Double>> ReliefInput = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < dataset.get(0).size(); i++) {
			ArrayList<Double> featT = new ArrayList<Double>();
			for (int j = 0; j < dataset.size(); j++) {
				featT.add(dataset.get(j).get(i));
			}
			featT.add(labels.get(i));
			ReliefInput.add(featT);
		}

		// transform dataset into Instances type
		Instances ReliefInstances = new Instances("Features", fv, dataset.size());
		for (int i = 0; i < ReliefInput.size(); i++) {
			double[] vals = CollectionUtilities.listToArray(ReliefInput.get(i));

			Instance instWeka = new Instance(vals.length);

			for (int j = 0; j < vals.length; j++) {
				instWeka.setValue(j, vals[j]);
			}
			ReliefInstances.add(instWeka);
		}
		ReliefInstances.setClassIndex(ReliefInstances.numAttributes() - 1);

		return ReliefInstances;

	}
}
