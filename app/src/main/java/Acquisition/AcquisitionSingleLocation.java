package Acquisition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class AcquisitionSingleLocation {

	String filename;
	private HashMap<Integer, ArrayList<Double>> data;
	private HashMap<Integer, ArrayList<Double>> dataT;
	private ArrayList<Double> labels;
	
	/**
	 * class constructor
	 * @param filename
	 */
	public AcquisitionSingleLocation(String filename){
		this.filename = filename;
		
		this.data = new HashMap<Integer, ArrayList<Double>>();
		this.dataT = new HashMap<Integer, ArrayList<Double>>();
		this.labels = new ArrayList<Double>();
	}
	
	/**
	 * method that parses a file from a single location
	 * and fills the ArrayList<ArrayList<Double>> dataT
	 * containing the raw data for each sensor channel
	 */
	public boolean parseDataSingleLocation(){
		//Input file which needs to be parsed
        BufferedReader fileReader = null;
         
        //Delimiter used in CSV file
        final String DELIMITER = ",";
        try
        {
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(this.filename));

			if (fileReader == null) {
				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				return false;
			}
             
            //Read the file line by line
            int k=0;
            while ((line = fileReader.readLine()) != null) {
            	ArrayList<Double> row = new ArrayList<Double>();
                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                for(String token : tokens) {
                    row.add(Double.parseDouble(token));
                }
                
                // separate class label from data
                ArrayList<Double> excludeClass = new ArrayList<Double>(row.subList(0, row.size()-1));
                
                if (row.size() == 12) {
                	excludeClass = new ArrayList<Double>(row.subList(1, row.size()-2));
                }
                
                data.put(k, excludeClass);
                labels.add((double) (row.get(row.size()-1)));
                k++;
            }

			fileReader.close();
        }
        catch (Exception e) {
			System.err.println("EXCEPTION");
            e.printStackTrace();
			return false;
        }
//        finally
//        {
//            try {
//                fileReader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//				return false;
//            }
//        }

        //transpose data to get data for each sensor channel
        for (int i=0; i<data.get(0).size(); i++){
        	ArrayList<Double> tmp = new ArrayList<Double>();
        	for (int j=0; j<data.size(); j++){
        		tmp.add(data.get(j).get(i));	
        	}
        	dataT.put(i, tmp);
        }

		return true;
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
}
