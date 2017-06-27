package Utilities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by karayan on 1/5/17.
 */
public class DeviceFeatures {
    private ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawData;
    private ArrayList<HashMap<String, ArrayList<Double>>> allFeatures;
    private ArrayList<HashMap<String, ArrayList<Double>>> selectedFeatures;
    private ArrayList<ArrayList<Integer>> dominantStreams;
    private ArrayList<Double> labels;

    public DeviceFeatures() {
        this.rawData = new ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>>();
        this.allFeatures = new ArrayList<HashMap<String, ArrayList<Double>>>();
        this.selectedFeatures = new ArrayList<HashMap<String, ArrayList<Double>>>();
        this.dominantStreams = new ArrayList<ArrayList<Integer>>();
        this.labels = new ArrayList<Double>();
    }


    public ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> getRawData() {
        return rawData;
    }

    public void setRawData(ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawData) {
        this.rawData = rawData;
    }

    public ArrayList<HashMap<String, ArrayList<Double>>> getAllFeatures() {
        return allFeatures;
    }

    public void setAllFeatures(ArrayList<HashMap<String, ArrayList<Double>>> allFeatures) {
        this.allFeatures = allFeatures;
    }

    public ArrayList<HashMap<String, ArrayList<Double>>> getSelectedFeatures() {
        return selectedFeatures;
    }

    public void setSelectedFeatures(ArrayList<HashMap<String, ArrayList<Double>>> selectedFeatures) {
        this.selectedFeatures = selectedFeatures;
    }

    public ArrayList<ArrayList<Integer>> getDominantStreams() {
        return dominantStreams;
    }

    public void setDominantStreams(ArrayList<ArrayList<Integer>> dominantStreams) {
        this.dominantStreams = dominantStreams;
    }

    public ArrayList<Double> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<Double> labels) {
        this.labels = labels;
    }

}
