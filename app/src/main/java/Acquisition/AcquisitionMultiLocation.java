package Acquisition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class AcquisitionMultiLocation {

	String filename;
	private HashMap<Integer, ArrayList<Double>> data;
	private HashMap<Integer, ArrayList<Double>> dataT;
	private ArrayList<Double> labels;
	private ArrayList<Double> device_ids;
	private ArrayList<Double> timestamps;
	
	/**
	 * class constructor
	 * @param filename
	 */
	public AcquisitionMultiLocation(String filename){
		this.filename = filename;
		
		this.data = new HashMap<Integer, ArrayList<Double>>();
		this.dataT = new HashMap<Integer, ArrayList<Double>>();
		this.labels = new ArrayList<Double>();
		this.device_ids = new ArrayList<Double>();
		this.timestamps = new ArrayList<Double>();
	}
	
	/**
	 * method that parses a file from a single location
	 * and fills the ArrayList<ArrayList<Double>> dataT
	 * containing the raw data for each sensor channel
	 */
	public void parseDataMultiLocation(){
		//Input file which needs to be parsed
	    BufferedReader fileReader = null;
	     
	    //Delimiter used in CSV file
	    final String DELIMITER = ",";
	    try
	    {
	        String line = "";
	        //Create the file reader
	        fileReader = new BufferedReader(new FileReader(this.filename));
	         
	        //Read the file line by line
	        int k=0;
	        while ((line = fileReader.readLine()) != null) {
	        	ArrayList<Double> row = new ArrayList<Double>();
	            //Get all tokens available in line
	            String[] tokens = line.split(DELIMITER);
	            for(String token : tokens) {
	                row.add(Double.parseDouble(token));
	            }
	            
	            // separate raw data from class, timestamp, and device id
	            ArrayList<Double> rawData = new ArrayList<Double>(row.subList(1, row.size()-2));
	            data.put(k, rawData);
	            device_ids.add((double) (row.get(0))); 				// device id is the first column
	            labels.add((double) (row.get(row.size()-1))); 		// labels is the last column
	            timestamps.add((double) (row.get(row.size()-2))); 	// timestamp is the column before the last one
	            k++;
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    finally
	    {
	        try {
	            fileReader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	    dataT = Utilities.CollectionUtilities.transposeHashMap(data);
	    //transpose data to get data for each sensor channel
//	    for (int i=0; i<data.get(0).size(); i++){
//	    	ArrayList<Double> tmp = new ArrayList<Double>();
//	    	for (int j=0; j<data.size(); j++){
//	    		tmp.add(data.get(j).get(i));	
//	    	}
//	    	dataT.put(i, tmp);
//	    }
	}

	/**
	 * Getters and Setters
	 */
	
	public HashMap<Integer, ArrayList<Double>> getData() {
		return data;
	}

	public void setData(HashMap<Integer, ArrayList<Double>> data) {
		this.data = data;
	}

	public HashMap<Integer, ArrayList<Double>> getDataT() {
		return dataT;
	}

	public void setDataT(HashMap<Integer, ArrayList<Double>> dataT) {
		this.dataT = dataT;
	}

	public ArrayList<Double> getLabels() {
		return labels;
	}

	public void setLabels(ArrayList<Double> labels) {
		this.labels = labels;
	}
	
	public ArrayList<Double> getDevice_ids() {
		return device_ids;
	}

	public void setDevice_ids(ArrayList<Double> device_ids) {
		this.device_ids = device_ids;
	}

	public ArrayList<Double> getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(ArrayList<Double> timestamps) {
		this.timestamps = timestamps;
	}
}