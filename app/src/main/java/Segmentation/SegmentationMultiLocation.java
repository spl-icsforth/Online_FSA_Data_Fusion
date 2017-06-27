package Segmentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Utilities.CollectionUtilities;

/**
 * class that performs data segmentation in a multiple location network
 * @author karayan
 *
 */
public class SegmentationMultiLocation {

	private HashMap<Integer, ArrayList<Double>> data;
	private ArrayList<Double> labels;
	private ArrayList<Double> timestamps;
	private ArrayList<Double> device_ids;
	
	private HashMap<Integer, ArrayList<ArrayList<Double>>> segmentedData;
	private ArrayList<ArrayList<Double>> segmentedLabels;
	private ArrayList<ArrayList<Double>> segmentedDevice_ids;
	
	private ArrayList<Double> finalLabels;
	
	/**
	 * class constructor
	 * @param data : raw data
	 * @param labels : corresponding labels
	 * @param timestamps : corresponding timestamps
	 * @param device_ids : corresponding device_ids
	 */
	public SegmentationMultiLocation(
			HashMap<Integer, ArrayList<Double>> data, 
			ArrayList<Double> labels, 
			ArrayList<Double> timestamps,
			ArrayList<Double> device_ids) {
		
		this.data = data;
		this.labels = labels;
		this.timestamps = timestamps;
		this.device_ids = device_ids;
		
		this.segmentedData = new HashMap<Integer, ArrayList<ArrayList<Double>>>();
		this.segmentedLabels = new ArrayList<ArrayList<Double>>();
		this.segmentedDevice_ids = new ArrayList<ArrayList<Double>>();
		
		this.finalLabels = new ArrayList<Double>();
	}
	

	/**
	 * Method that performs data segmentation using overlapping windows
	 * Segmentation is performed with respect to timestamp
	 * @param windowSizezeSec : window length in seconds
	 * @param overlapping_a : overlapping percentage in range [0, 1)
	 */	
	public void SegmentationOverlapping(int windowSizezeSec, double overlapping_a) {
		int windowSizeMillis = windowSizezeSec * 1000;
		double datasizeSamples = timestamps.get(timestamps.size()-1);
		int datasizeSecs = data.get(0).size()/51;
		double samplesPerWin = (double)windowSizezeSec/((double)1/51);
		double samplesPerWinOverl =  (overlapping_a * windowSizeMillis);
		
//		System.out.println("windowSizezeMillis "+windowSizeMillis);
//		System.out.println("datasizeSamples "+datasizeSamples);
//		System.out.println("samplesPerWin "+samplesPerWin);
//		System.out.println("samplesPerWinOverl "+samplesPerWinOverl);
		
		for (int i=0; i<data.size(); i++){
			ArrayList<ArrayList<Double>> allSegments = new ArrayList<ArrayList<Double>>();
			int c = 0;
			double start = timestamps.get(0), end = timestamps.get(0) + windowSizeMillis;
			int index_start = 0, index_end = 0;
			
			for (int j=0; j<timestamps.size(); j++){
				if (end >= timestamps.get(j)){
					index_end = j;
				}
			}
			
			while (index_end <= timestamps.size()) {
				ArrayList<Double> dataSegment = new ArrayList<Double>();
				ArrayList<Double> labelSegment = new ArrayList<Double>();
				ArrayList<Double> device_id_Segment = new ArrayList<Double>();
				
				if (c!=0) {
					start = end - (int)samplesPerWinOverl;
					end = (int)(start + windowSizeMillis);
					
					for (int j=0; j<timestamps.size(); j++){
						if (start >= timestamps.get(j)){
							index_start = j;
						}
					}
					
					for (int j=0; j<timestamps.size(); j++){
						if (end >= timestamps.get(j)){
							index_end = j;
						}
					}
				}
				
				if (index_end >= timestamps.size()){
					index_end = timestamps.size()-1;
				}
				
				if (index_start >= index_end){
					break;
				}
				
//				System.out.println("start: "+start);
//				System.out.println("end :"+end);
//				
//				System.out.println("index start: "+index_start);
//				System.out.println("index end :"+index_end);
				
				//create data and label segment
				dataSegment.addAll(data.get(i).subList(index_start, index_end-1));
				labelSegment.addAll(labels.subList(index_start, index_end-1));
				device_id_Segment.addAll(device_ids.subList(index_start, index_end-1));
				
				//add segment to allSegments Arraylist
				allSegments.add(dataSegment);
				
				// add label segment to segmentedLabels ArrayList
				if (i==0) { 
					segmentedLabels.add(labelSegment);
					segmentedDevice_ids.add(device_id_Segment);
				}
				
				c++;
			}
			segmentedData.put(i, allSegments);
		}
		eliminateOutliers();
	}
	
	/**
	 * method that creates finalLabels for each segment --> the most frequent one
	 * and eliminates data having label different to the most frequent one
	 */
	public void eliminateOutliers() {
		for (int i=0; i<segmentedLabels.size(); i++) {
			// final label for each segment is the most frequent one
//			if (segmentedLabels.get(i).size()!=0) {
				double maxOccurrence = CollectionUtilities.getMaxOccurrence(segmentedLabels.get(i));
				finalLabels.add(maxOccurrence);
				
				// eliminate data having label other than the most frequent --> assign NaN value
				ArrayList<Double> labelsPerSegment = segmentedLabels.get(i);
				for (int j=0; j<labelsPerSegment.size(); j++) {
					if (labelsPerSegment.get(j) != maxOccurrence) {
						for (int s=0; s<segmentedData.size(); s++) 
							segmentedData.get(s).get(i).set(j, Double.NaN);
						segmentedDevice_ids.get(i).set(j, Double.NaN);
					}
				}
				for (int s=0; s<segmentedData.size(); s++)
					segmentedData.get(s).get(i).removeAll(Collections.singleton(Double.NaN));
				segmentedDevice_ids.get(i).removeAll(Collections.singleton(Double.NaN));
//			}
//			else {
////				finalLabels.add(i, -1.0);
//				
//				ArrayList<Double> labelsPerSegment = segmentedLabels.get(i);
//				for (int j=0; j<labelsPerSegment.size(); j++) {
//					for (int s=0; s<segmentedData.size(); s++) 
//						segmentedData.get(s).get(i).set(j, Double.NaN);
//					segmentedDevice_ids.get(i).set(j, Double.NaN);
//				}
//				for (int s=0; s<segmentedData.size(); s++)
//					segmentedData.get(s).get(i).removeAll(Collections.singleton(Double.NaN));
//				segmentedDevice_ids.get(i).removeAll(Collections.singleton(Double.NaN));
//				
//				for (int i1=finalLabels.size()-1; i1>0; i1--){
//					if (finalLabels.get(i1).equals(-1.0)) {
//						finalLabels.remove(i1);
//					}
//				}
//			}
		}
		
		
		System.out.println("finalLabels" + finalLabels);
	}
	
	/**
	 * Getters and Setters
	 */
	
	public HashMap<Integer, ArrayList<ArrayList<Double>>> getSegmentedData() {
		return segmentedData;
	}

	public void setSegmentedData(HashMap<Integer, ArrayList<ArrayList<Double>>> segmentedData) {
		this.segmentedData = segmentedData;
	}

	public ArrayList<ArrayList<Double>> getSegmentedLabels() {
		return segmentedLabels;
	}

	public void setSegmentedLabels(ArrayList<ArrayList<Double>> segmentedLabels) {
		this.segmentedLabels = segmentedLabels;
	}
	
	public ArrayList<ArrayList<Double>> getSegmentedDevice_ids() {
		return segmentedDevice_ids;
	}

	public void setSegmentedDevice_ids(ArrayList<ArrayList<Double>> segmentedDevice_ids) {
		this.segmentedDevice_ids = segmentedDevice_ids;
	}

	public ArrayList<Double> getFinalLabels() {
		return finalLabels;
	}

	public void setFinalLabels(ArrayList<Double> finalLabels) {
		this.finalLabels = finalLabels;
	}
}
