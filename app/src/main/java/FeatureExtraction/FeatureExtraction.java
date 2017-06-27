package FeatureExtraction;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import DataBase.DBDataSource;
import Utilities.CollectionUtilities;
import Utilities.IO;

/**
 * class for feature extraction
 * @author karayan
 *
 */
public class FeatureExtraction {

	private HashMap<Integer, ArrayList<ArrayList<Double>>> data_all;
	private HashMap<Integer, ArrayList<ArrayList<Double>>> data;
	private ArrayList<Double> labels;
	private int device_id;
	private int participant_id;
	private int windowSizezeSec;
	private HashMap<String, ArrayList<Double>> finalFeatureSet;
	private ArrayList<ArrayList<HashMap<String,Double>>> PC_featureSet;
	HashMap<Integer, ArrayList<HashMap<String,Double>>> initialFeatureSet;
	HashMap<Integer, ArrayList<HashMap<String,Double>>> mergedFeatureSet;
	private ArrayList<String> badFeatures;
	private ArrayList<ArrayList<Double>> device_ids;
	private ArrayList<Integer> badSegments;

	private DBDataSource dataSource;
	
	/**
	 * class constructor for single location
	 * @param data
	 * @param labels
	 * @param device_id
	 * @param windowSizezeSec
	 */
	public FeatureExtraction(HashMap<Integer, ArrayList<ArrayList<Double>>> data,
							 ArrayList<Double> labels, int device_id, int participant_id, int windowSizezeSec
						) {
		this.data = data;
		this.labels = labels;
		this.device_id = device_id;
		this.participant_id = participant_id;
		this.windowSizezeSec = windowSizezeSec;
		
		this.PC_featureSet = new ArrayList<ArrayList<HashMap<String,Double>>>();
		this.finalFeatureSet = new HashMap<String, ArrayList<Double>>();
		this.initialFeatureSet = new HashMap<Integer, ArrayList<HashMap<String,Double>>>();
		this.mergedFeatureSet = new HashMap<Integer, ArrayList<HashMap<String,Double>>>();
		this.badFeatures = new ArrayList<String>();
		this.badSegments = new ArrayList<Integer>();

//		this.dataSource = dataSource;
	}
	
	/**
	 * class constructor for multi location
	 * @param data
	 * @param labels
	 * @param device_ids
	 * @param device_id
	 * @param windowSizezeSec
	 */
	public FeatureExtraction(HashMap<Integer, ArrayList<ArrayList<Double>>> data,
			ArrayList<Double> labels, ArrayList<ArrayList<Double>> device_ids, 
			int device_id, int windowSizezeSec) {
		this.data_all = data;
		this.labels = labels;
		this.device_id = device_id;
		this.windowSizezeSec = windowSizezeSec;
		
		this.PC_featureSet = new ArrayList<ArrayList<HashMap<String,Double>>>();
		this.finalFeatureSet = new HashMap<String, ArrayList<Double>>();
		this.initialFeatureSet = new HashMap<Integer, ArrayList<HashMap<String,Double>>>();
		this.mergedFeatureSet = new HashMap<Integer, ArrayList<HashMap<String,Double>>>();
		this.badFeatures = new ArrayList<String>();
		this.device_ids = device_ids;
		this.badSegments = new ArrayList<Integer>();
		
		this.eliminateSegments();
	}
	
	public void eliminateSegments() {
		this.data = new HashMap<Integer, ArrayList<ArrayList<Double>>>();
		ArrayList<Double> x = new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));
		badSegments = new ArrayList<Integer>();
		for (int i=0; i<device_ids.size(); i++){ //for each segment
			ArrayList<Double> segment = device_ids.get(i);
			if (!segment.containsAll(x)){
				badSegments.add(i);
			}
		}
		
		for (int j=0; j<this.data_all.size(); j++) {
			ArrayList<ArrayList<Double>> values = this.data_all.get(j);
			for (int i=badSegments.size()-1; i>=0; i--) {
				values.remove(badSegments.get(i).intValue());
			}
			this.data.put(j, values);
		}
	}

	/**
	 * 
	 * @param normalized: data
	 * @return featureSet: Hashmap of features for each sensor channel
	 */
	public void createFeatureSet(boolean normalized){
		
		for (int i=0; i<data.size(); i++){ //for each channel
			ArrayList<ArrayList<Double>> allSegments = data.get(i);
			ArrayList<HashMap<String,Double>> featureSetVal = new ArrayList<HashMap<String,Double>>();
			for(int j=0; j<allSegments.size(); j++){ //for each segment
				//copy values of each segment into array of doubles
				ArrayList<Double> values = allSegments.get(j);
				
				double[] valuesToArray = new double[values.size()];
				for (int k=0; k<values.size(); k++){
					valuesToArray[k] = values.get(k);
				}
				
				//extract features for each segment
				HashMap<String, Double> featSet = new HashMap<String, Double>();
				
				if (values.size()!=0)
				featSet = Calculations.computeFeatures(
						values, valuesToArray, i, j, device_id, windowSizezeSec);
//				System.out.println("featSet"+featSet);
				featureSetVal.add(j, featSet);
//				System.out.println("------------------------------------------------------- "+j);
			}
			initialFeatureSet.put(i, featureSetVal);

//			System.out.println("================================================================================= "+i);
		}
		
		
		PC_featureSet = pairwiseCorrelationFeatureSet(data, device_id);
		mergedFeatureSet = CollectionUtilities.mergeStructures(initialFeatureSet, PC_featureSet);
		createFinalFeatureSet(mergedFeatureSet, normalized);
	}
	
	/**
	 * method that computes pairwise correlation for each segment
	 * @param segmentedData
	 * @param dev_id
	 * @return the pairwise correlation feature set
	 */
	public ArrayList<ArrayList<HashMap<String,Double>>> pairwiseCorrelationFeatureSet(
			HashMap<Integer, ArrayList<ArrayList<Double>>> segmentedData,
			int dev_id){
		
		ArrayList<ArrayList<HashMap<String,Double>>> featuresAllSegments = new ArrayList<ArrayList<HashMap<String,Double>>>();
		for (int i=0; i<segmentedData.size(); i++){ //for each channel

			ArrayList<ArrayList<Double>> allSegmentsCurr = segmentedData.get(i);
			ArrayList<HashMap<String,Double>> featuresPerSegment = new ArrayList<HashMap<String,Double>>();
			
			for (int ii=0; ii<segmentedData.size(); ii++){
				ArrayList<ArrayList<Double>> allSegmentsNext = segmentedData.get(ii);
				int c = 0;
				
				for (int j=0; j<allSegmentsCurr.size(); j++){
					double[] segment_curr = CollectionUtilities.listToArray(allSegmentsCurr.get(c));
					double[] segment_next = CollectionUtilities.listToArray(allSegmentsNext.get(c));
					
//					if (segment_curr.length == 0){
////						System.out.println("size 0");
//						continue;
//					}
					
					// skip auto correlation
					if (i==ii) continue;
					
					// skip duplicates
					if (ii<i) continue;
					
					if ( (segment_curr.length != 0) || (segment_next.length != 0) ){
						PearsonsCorrelation p = new PearsonsCorrelation();
						double corr = p.correlation(segment_curr, segment_next);

						String str = "PC_c" + String.format("%02d", i) + "_"
								+ String.format("%02d", ii)
//
								+ "s" + String.format("%04d", c)
								+ "d" + dev_id;
						
						HashMap<String, Double> h = new HashMap<String, Double>();
						h.put(str, corr);
						featuresPerSegment.add(h);
					}
					c++;
				}
				
			}
			featuresAllSegments.add(featuresPerSegment);
		}
		return featuresAllSegments;
	}
	
	/**
	 * Method that concatenates all features for each segment/window
	 * @param featureSet: the featureset
	 * @param normalized: boolean that indicates whether to perform z-score normalization
	 * @return the final feature vector that will be used as input in the feature selection algorithms
	 */
	public void createFinalFeatureSet(
			HashMap<Integer, ArrayList<HashMap<String, Double>>> featureSet,
			boolean normalized) {
		
		int no_segments = featureSet.get(0).size();
		HashMap<Integer, HashMap<String, Double>> segments = new HashMap<Integer, HashMap<String, Double>>();
		HashMap<String, ArrayList<Double>> featureVals = new HashMap<String, ArrayList<Double>>();
		
		for (int i = 0; i < no_segments; i++) {
			HashMap<String, Double> seg = new HashMap<String, Double>();
			for (int j = 0; j < featureSet.size(); j++) {
				HashMap<String, Double> v = (featureSet.get(j).get(i));
				seg.putAll(v);
			}
			segments.put(i, seg);
		}

		featureSetVals(segments);
		
		if (normalized) {
			finalFeatureSet = Calculations.z_scoreNormalization(finalFeatureSet);
			badFeatures = Calculations.getBadFeatures();
		}
	}
	
	/**
	 * Method that transforms a feature set into a different structure
	 * in order to be used as input in the FSAs
	 * before : HashMap<Integer, HashMap<String, Double>> --> features for each window separately
	 * after : HashMap<String, ArrayList<Double>> featureSetVals --> features for all windows together
	 * @param featureSet
	 * @return
	 */
	public void featureSetVals(
			HashMap<Integer, HashMap<String, Double>> featureSet){
		Set<String> keyset = featureSet.get(0).keySet();
		
		Iterator it = keyset.iterator();
		while (it.hasNext()){
			ArrayList<Double> values = new ArrayList<Double>();
			String k = it.next().toString();
			for (int i=0; i<featureSet.size(); i++){
				HashMap<String, Double> segment = featureSet.get(i);
				
				double v = 0;
				if ( (segment.get(k) != null) ){
					v = segment.get(k);
					values.add(v);
				}
				finalFeatureSet.put(k, values);
			}
		}
	}

	public void populateDB(DBDataSource dataSource, String tableName) {
		TreeSet<String> featureNames = new TreeSet<String>(finalFeatureSet.keySet());
		String[] featureNames_Arr = featureNames.toArray(new String[featureNames.size()]);

		IO.populateFeaturesTable(dataSource, finalFeatureSet, featureNames_Arr, labels, device_id, participant_id);
	}

	public void selectAllFromFeatures(DBDataSource dataSource, int device_id, String tableName) {
//		dataSource.open();
		getFeaturesPerDevice(dataSource, tableName, device_id);
//		dataSource.close();
	}

	public HashMap<Integer, ArrayList<Double>> getFeaturesPerDevice(DBDataSource dataSource, String tableName, int device_id) {
		HashMap<Integer, ArrayList<Double>> allFeatures = new HashMap<Integer, ArrayList<Double>>();

		SQLiteDatabase database = dataSource.getDatabase();

		List<Double> list = new ArrayList<Double>();
		Cursor cursor = database.rawQuery("SELECT * FROM " + tableName + " WHERE _id= " + device_id, null);

		//init
		int size = cursor.getCount();
		System.out.println("size "+size);
		for (int i=0; i<size; i++) {
			allFeatures.put(i, new ArrayList<Double>());
		}

		cursor.moveToFirst();

		int i=0;
		while (!cursor.isAfterLast()) {			//TODO FIXME FOR ALL FEATURES
			// import data to hashmaps
//			device_ids.add(cursor.getDouble(1));

			// acc
			allFeatures.get(i).add(cursor.getDouble(2));
			allFeatures.get(i).add(cursor.getDouble(3));
			allFeatures.get(i).add(cursor.getDouble(4));
			//gyro
			allFeatures.get(i).add(cursor.getDouble(5));
			allFeatures.get(i).add(cursor.getDouble(6));
			allFeatures.get(i).add(cursor.getDouble(7));
			//mag
			allFeatures.get(i).add(cursor.getDouble(8));
			allFeatures.get(i).add(cursor.getDouble(9));
			allFeatures.get(i).add(cursor.getDouble(10));

//			timestamps.add(cursor.getDouble(11));
//			labels.add(i, cursor.getDouble(11));

			// move to next cursor
			cursor.moveToNext();

			i++;
		}
		cursor.close();

//		System.out.println(data);
//		System.out.println("device_ids: "+device_ids);
//		System.out.println("timestamps: "+timestamps);
//		System.out.println("features: "+allFeatures);
//		System.out.println("labels:"+labels);


		return allFeatures;
	}




	/**
	 * Setters and Getters
	 */
	
	public HashMap<String, ArrayList<Double>> getFinalFeatureSet() {
		return finalFeatureSet;
	}


	public void setFinalFeatureSet(HashMap<String, ArrayList<Double>> finalFeatureSet) {
		this.finalFeatureSet = finalFeatureSet;
	}


	public ArrayList<ArrayList<HashMap<String, Double>>> getPC_featureSet() {
		return PC_featureSet;
	}


	public void setPC_featureSet(ArrayList<ArrayList<HashMap<String, Double>>> pC_featureSet) {
		PC_featureSet = pC_featureSet;
	}


	public HashMap<Integer, ArrayList<HashMap<String, Double>>> getInitialFeatureSet() {
		return initialFeatureSet;
	}


	public void setInitialFeatureSet(HashMap<Integer, ArrayList<HashMap<String, Double>>> initialFeatureSet) {
		this.initialFeatureSet = initialFeatureSet;
	}


	public HashMap<Integer, ArrayList<HashMap<String, Double>>> getMergedFeatureSet() {
		return mergedFeatureSet;
	}


	public void setMergedFeatureSet(HashMap<Integer, ArrayList<HashMap<String, Double>>> mergedFeatureSet) {
		this.mergedFeatureSet = mergedFeatureSet;
	}
	
	public ArrayList<String> getBadFeatures() {
		return this.badFeatures;
	}
	
	public ArrayList<Integer> getBadSegments() {
		return badSegments;
	}

	public void setBadSegments(ArrayList<Integer> badSegments) {
		this.badSegments = badSegments;
	}
}
