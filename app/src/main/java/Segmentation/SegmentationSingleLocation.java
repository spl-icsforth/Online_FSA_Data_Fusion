package Segmentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Utilities.CollectionUtilities;

/**
 * class that performs data segmentation in a single location network
 * @author karayan
 *
 */
public class SegmentationSingleLocation {
	
	private HashMap<Integer, ArrayList<Double>> data;
	private ArrayList<Double> labels;
	
	private HashMap<Integer, ArrayList<ArrayList<Double>>> segmentedData;
	private ArrayList<ArrayList<Double>> segmentedLabels;
	
	private ArrayList<Double> finalLabels;
	
	/**
	 * class constructor
	 * @param data : raw data
	 * @param labels : corresponding labels
	 */
	public SegmentationSingleLocation(
			HashMap<Integer, ArrayList<Double>> data, 
			ArrayList<Double> labels) {
		
		this.data = data;
		this.labels = labels;
		
		this.segmentedData = new HashMap<Integer, ArrayList<ArrayList<Double>>>();
		this.segmentedLabels = new ArrayList<ArrayList<Double>>();
		
		this.finalLabels = new ArrayList<Double>();
	}
	
	/**
	 * Method that performs data segmentation using overlapping windows
	 * @param windowSizezeSec : window length in seconds
	 * @param overlapping_a : overlapping percentage in range [0, 1)
	 */
	public void SegmentationOverlapping(int windowSizezeSec, double overlapping_a) {
		int datasizeSamples = data.get(0).size();
		int datasizeSecs = data.get(0).size()/51;
		double numwins = (double)datasizeSecs/windowSizezeSec;
		double samplesPerWin = (double)windowSizezeSec/((double)1/51);
		double samplesPerWinOverl =  (overlapping_a * samplesPerWin);

		for (int i=0; i<data.size(); i++){ //for each channel
			ArrayList<ArrayList<Double>> allSegments = new ArrayList<ArrayList<Double>>();
			int start=0, end= (int)samplesPerWin - 1;

			int c = 0;
			while (end <= datasizeSamples) {
				ArrayList<Double> dataSegment = new ArrayList<Double>();
				ArrayList<Double> labelSegment = new ArrayList<Double>();
				
				//define start and end of segment
				if (c != 0){
					start = end - (int)samplesPerWinOverl;
					end = (int)(start + samplesPerWin);
				}
				if (end >= datasizeSamples){
					end = datasizeSamples-1;
					break;
				}

				//create data and label segment
				dataSegment.addAll(data.get(i).subList(start, end-1));
				labelSegment.addAll(labels.subList(start, end-1));
				
				//add segment to allSegments Arraylist
				allSegments.add(dataSegment);

				// add label segment to segmentedLabels ArrayList
				if (i==0) segmentedLabels.add(labelSegment);
				
				c++;
			}
			segmentedData.put(i, allSegments);
//			System.out.println("segmentedData: "+segmentedData);
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
			double maxOccurrence = CollectionUtilities.getMaxOccurrence(segmentedLabels.get(i));
			finalLabels.add(maxOccurrence);
			
			// eliminate data having label other than the most frequent --> assign NaN value
			ArrayList<Double> labelsPerSegment = segmentedLabels.get(i);
			for (int j=0; j<labelsPerSegment.size(); j++) {
				if (labelsPerSegment.get(j) != maxOccurrence) {
					for (int s=0; s<segmentedData.size(); s++) 
						segmentedData.get(s).get(i).set(j, Double.NaN);
				}
			}
			for (int s=0; s<segmentedData.size(); s++)
				segmentedData.get(s).get(i).removeAll(Collections.singleton(Double.NaN));
		}
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

	public ArrayList<Double> getFinalLabels() {
		return finalLabels;
	}

	public void setFinalLabels(ArrayList<Double> finalLabels) {
		this.finalLabels = finalLabels;
	}
}
