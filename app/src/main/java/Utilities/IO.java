package Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import DataBase.DBDataSource;
import DataBase.Measurement;

public class IO {

	public static void writeFeatureSetToFile(String filename, HashMap<String, ArrayList<Double>> features,
			ArrayList<Double> labels, String[] featureNames) {
		
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {

			int sz = features.get(featureNames[0]).size();

			for (int i=0; i<featureNames.length; i++){
				out.print(featureNames[i]+",");
			}
			out.println("label");
			for (int i=0; i<featureNames.length; i++){
				out.print(i+",");
			}
			out.println(000);

			for (int i=0; i<sz; i++){
				for (int j=0; j<features.size(); j++){
					out.print(features.get(featureNames[j]).get(i)+",");
				}
				out.println(labels.get(i));
			}

		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}
	}

	public static void writeResultsToFile(String filename, ArrayList<Results> results){
		try {
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
//			out.println("participant,alg,winsize,featuresNo,dominantFeatures,exectimeAcq,exectimeSeg,exectimeFE,exectimeFS,memoryAcq,memorySeg,memoryFE,memoryFS,batteryAcq,batterySeg,batteryFE,batteryFS");
			for (int i=0; i<results.size(); i++){
				Results res = results.get(i);
				out.print(res.getParticipantNo()+",");
				out.print(res.getAlgNo()+",");
				out.print(res.getWinsizeSec()+",");
				out.print(res.getFeaturesNo()+",");
				ArrayList<Integer> domFeats = res.getDominantFeatures();
				for (int k=0; k<domFeats.size(); k++){
					out.print(domFeats.get(k)+",");
				}
				out.print(res.getExecTimeAcq()+",");
				out.print(res.getExecTimeSeg()+",");
				out.print(res.getExecTimeFE()+",");
				out.print(res.getExecTimeFS()+",");

				out.print(res.getMemoryAcq()+",");
				out.print(res.getMemorySeg()+",");
				out.print(res.getMemoryFE()+",");
				out.print(res.getMemoryFS()+",");

				out.print(res.getBatteryAcq()+",");
				out.print(res.getBatterySeg()+",");
				out.print(res.getBatteryFE()+",");
				out.print(res.getBatteryFS()+",");

				out.print(res.getElapsedTimeFull());

				out.println();
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeSingleResultToFile(String filename, Results results){
		try {

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
//			out.println("participant,alg,winsize,featuresNo,dominantFeatures,exectimeAcq,exectimeSeg,exectimeFE,exectimeFS,memoryAcq,memorySeg,memoryFE,memoryFS,batteryAcq,batterySeg,batteryFE,batteryFS");
//			for (int i=0; i<results.size(); i++){
				Results res = results;//.get(i);
				out.print(res.getParticipantNo()+",");
				out.print(res.getAlgNo()+",");
				out.print(res.getWinsizeSec()+",");
				out.print(res.getFeaturesNo()+",");
				ArrayList<Integer> domFeats = res.getDominantFeatures();
				for (int k=0; k<domFeats.size(); k++){
					out.print(domFeats.get(k)+",");
				}
				out.print(res.getExecTimeAcq()+",");
				out.print(res.getExecTimeSeg()+",");
				out.print(res.getExecTimeFE()+",");
				out.print(res.getExecTimeFS()+",");

				out.print(res.getMemoryAcq()+",");
				out.print(res.getMemorySeg()+",");
				out.print(res.getMemoryFE()+",");
				out.print(res.getMemoryFS()+",");

				out.print(res.getBatteryAcq()+",");
				out.print(res.getBatterySeg()+",");
				out.print(res.getBatteryFE()+",");
				out.print(res.getBatteryFS()+",");

				out.print(res.getElapsedTimeFull());

				out.println();
//			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeSingleResultToFileCA(String filename, Results results){
		try {

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
//			out.println("participant,alg,winsize,featuresNo,dominantFeatures,exectimeAcq,exectimeSeg,exectimeFE,exectimeFS,memoryAcq,memorySeg,memoryFE,memoryFS,batteryAcq,batterySeg,batteryFE,batteryFS");
//			for (int i=0; i<results.size(); i++){
			Results res = results;//.get(i);
			out.print(res.getParticipantNo()+",");
			out.print(res.getAlgNo()+",");
			out.print(res.getWinsizeSec()+",");
			out.print(res.getFeaturesNo()+",");
			ArrayList<Integer> domFeats = res.getDominantFeatures();
			for (int k=0; k<domFeats.size(); k++){
				out.print(domFeats.get(k)+",");
			}
			out.print(res.getExecTimeAcq()+",");
			out.print(res.getExecTimeSeg()+",");
			out.print(res.getExecTimeFE()+",");
			out.print(res.getExecTimeFS()+",");

			out.print(res.getMemoryAcq()+",");
			out.print(res.getMemorySeg()+",");
			out.print(res.getMemoryFE()+",");
			out.print(res.getMemoryFS()+",");

			out.print(res.getBatteryAcq()+",");
			out.print(res.getBatterySeg()+",");
			out.print(res.getBatteryFE()+",");
			out.print(res.getBatteryFS()+",");

			out.print(res.getElapsedTimeFull()+",");

			out.print(res.getCA()+",");
			out.print(res.getExecTimeCA());

			out.println();
//			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeArrayListToFile(String filename, ArrayList<Double> list) {
		try {

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
			out.println(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeArrayListLongToFile(String filename, ArrayList<Long> list) {
		try {

			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));

			for (int i=0; i<list.size(); i++) {
				out.print(list.get(i) + ", ");
			}
			out.println();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * method that parses log files to populate the Measurements table
	 * @param datasource
     */
	public static void populateMeasurementsTable(int participant_no, DBDataSource datasource) {
		/**
		 * TABLE MEASUREMENTS
		 */
		Measurement meas = new Measurement();

		String path = "/storage/sdcard0/spl/";
//		String path = "/sdcard/";

		String filename = path+"alldata_concatenated_part"+participant_no+".csv";
		System.out.println("file: "+filename);

		ArrayList<Measurement> measurements = new ArrayList<Measurement>();

		//Input file which needs to be parsed
		BufferedReader fileReader = null;
		System.out.println("Parsing file...");
		//Delimiter used in CSV file
		final String DELIMITER = ",";
		try
		{
			String line = "";
			//Create the file reader
			fileReader = new BufferedReader(new FileReader(filename));

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

				meas = new Measurement(k, row.get(0).intValue(), (row.get(1)), (row.get(2)), (row.get(3)),
						(row.get(4)), (row.get(5)), (row.get(6)),
						(row.get(7)), (row.get(8)), (row.get(9)),
						(row.get(10)), (row.get(11).intValue()));

//				datasource.createMeasurement(meas);
				measurements.add(meas);
				k++;

//				if (k==10000) break;
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
		System.out.println("Populating db");
		for (int i=0; i<measurements.size(); i++) {
			datasource.createMeasurement(measurements.get(i), participant_no);
		}
		System.out.println("end DB");
	}

	//https://developer.android.com/training/basics/data-storage/databases.html
	public static void populateFeaturesTable(DBDataSource datasource, HashMap<String, ArrayList<Double>> featureSet, String[] featureNames,
											 ArrayList<Double> labels, int device_id, int participant_no) {
//		datasource.open();
		datasource.upgrade();

		double[][] features_arr = CollectionUtilities.hashMaptoArrayTransposed(featureSet, featureNames);
//		CollectionUtilities.print2dArray(features_arr);

		for (int f=0; f<features_arr.length; f++) {
			double[] feature_vector = features_arr[f];
			int seg_id = f;
			int dev_id = device_id;
			int label = labels.get(f).intValue();

			datasource.createFeatureVector2(featureNames, feature_vector, seg_id, dev_id, label);
		}


//		ArrayList<FeatureVector> featureVectors = (ArrayList<FeatureVector>) datasource.getAllFeatures();
//
//		for (int i=0; i<featureVectors.size(); i++) {
//			System.out.println(featureVectors.get(i).toString());
//		}
	}

}
