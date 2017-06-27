package Segmentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * class that represents data from multiple sensor nodes
 * @author karayan
 *
 */
public class MultiLocationData {

	private HashMap<Integer, ArrayList<ArrayList<Double>>> all_data;
	private ArrayList<Double> all_labels;
	private ArrayList<ArrayList<Double>> all_device_ids;
	private HashMap<Integer, HashMap<Integer, ArrayList<ArrayList<Double>>>> separated_data;
	private HashMap<Integer, ArrayList<Double>> separated_labels;
	private ArrayList<Integer> badIndexes;
	/**
	 * class constructor
	 * @param data
	 * @param labels
	 * @param device_ids
	 */
	public MultiLocationData(HashMap<Integer, ArrayList<ArrayList<Double>>> data,
			ArrayList<Double> labels,
			ArrayList<ArrayList<Double>> device_ids) {
		this.all_data = data;
		this.all_labels = labels;
		this.all_device_ids = device_ids;
		
		this.separated_data = new HashMap<Integer, HashMap<Integer, ArrayList<ArrayList<Double>>>>();
		this.separated_labels = new HashMap<Integer, ArrayList<Double>>();
		this.badIndexes = new ArrayList<Integer>();
	}
	
	public ArrayList<Integer> getBadIndexes() {
		return badIndexes;
	}

	public void setBadIndexes(ArrayList<Integer> badIndexes) {
		this.badIndexes = badIndexes;
	}

	/**
	 * method that separates data and stores them in a HashMap
	 * key: sensor node id
	 * value: values for each modality
	 */
	public void separateData() {
		HashMap<Integer, ArrayList<ArrayList<Integer>>> devices = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
		for (int dev=1; dev<=5; dev++) {
			ArrayList<ArrayList<Integer>> all_device_indexes = new ArrayList<ArrayList<Integer>>();
 			for (int i=0; i<all_device_ids.size(); i++) { // foreach segment
				ArrayList<Integer> device_indexes = new ArrayList<Integer>();
				for (int j=0; j<all_device_ids.get(i).size(); j++) { // for each element of segment
					if (all_device_ids.get(i).get(j) == dev) { // get distinct device id
						device_indexes.add(j);
					}
				}
				all_device_indexes.add(device_indexes);
			}
 			devices.put(dev, all_device_indexes);
		}
		
		for (int dev=1; dev<=5; dev++) {
			HashMap<Integer, ArrayList<ArrayList<Double>>> sensor_node_data = new HashMap<Integer, ArrayList<ArrayList<Double>>>();
			for (int i=0; i<all_data.size(); i++){
				ArrayList<ArrayList<Double>> values_segments = new ArrayList<ArrayList<Double>>();
				for (int j=0; j<all_data.get(i).size(); j++) {
					ArrayList<Double> values = new ArrayList<Double>();
					for (int k=0; k<devices.get(dev).get(j).size(); k++) {
						int index = (devices.get(dev).get(j).get(k));
						double val = all_data.get(i).get(j).get(index);
						values.add(val);
					}
					// find indexes-segments where we have < 3 instances of a sensor nodes
					if (values.size() < 3) {
						badIndexes.add(j);
					}
					values_segments.add(values);
				}
				sensor_node_data.put(i, values_segments);
			}
			separated_data.put(dev, sensor_node_data);
		}
		
		// take unique bad indexes
		Set<Integer> hs = new HashSet<>();
		hs.addAll(badIndexes);
		badIndexes.clear();
		badIndexes.addAll(hs);
		
		// sort bad indexes-segments in descending order
		Collections.sort(badIndexes);
		Collections.reverse(badIndexes);
		
		// eliminate bad indexes - where we have < 3 instances of a sensor node
		for (int dev=1; dev<=5; dev++) {
			HashMap<Integer, ArrayList<ArrayList<Double>>> sensor_node_data = separated_data.get(dev);
			for (int i=0; i<sensor_node_data.size(); i++) {
				ArrayList<ArrayList<Double>> node_data = sensor_node_data.get(i);
				for (int j=0; j<badIndexes.size(); j++) {
					node_data.remove(badIndexes.get(j).intValue());
				}
				sensor_node_data.put(i, node_data);
			}
			separated_data.put(dev, sensor_node_data);
		}
		
		// eliminate bad indexes from device ids arraylist
		for (int j=0; j<badIndexes.size(); j++) {
			all_device_ids.remove(badIndexes.get(j).intValue());
		}
	}

	public HashMap<Integer, HashMap<Integer, ArrayList<ArrayList<Double>>>> getSeparated_data() {
		return separated_data;
	}

	public void setSeparated_data(HashMap<Integer, HashMap<Integer, ArrayList<ArrayList<Double>>>> separated_data) {
		this.separated_data = separated_data;
	}
	
	public ArrayList<ArrayList<Double>> getAll_device_ids() {
		return all_device_ids;
	}

	public void setAll_device_ids(ArrayList<ArrayList<Double>> all_device_ids) {
		this.all_device_ids = all_device_ids;
	}
}
