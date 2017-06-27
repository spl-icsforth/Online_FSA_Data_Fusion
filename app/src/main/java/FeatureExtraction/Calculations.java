package FeatureExtraction;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import Utilities.CollectionUtilities;

public class Calculations {
	private static ArrayList<String> badFeatures = new ArrayList<String>();
	private static DescriptiveStatistics da = new DescriptiveStatistics();
	
	public static double rms(double[] nums){
        double ms = 0;
        for (int i = 0; i < nums.length; i++)
            ms += nums[i] * nums[i];
        ms /= nums.length;
        return Math.sqrt(ms);
    }
	
	/**
	 * 1. Get FFT Spectrum for given data sample 
	 * 2. Get Power Spectrum of the FFT spectrum 
	 * 3. Perform Normalisation of the power spectrum 
	 * 4. Calculate Spectral Entropy
	 */
	public static double spec(double[] data){
		double[]  normalizedData, powerSpectrum;
		FFT fftData = null;
		try {
			fftData = new FFT(data, null, false, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		powerSpectrum = fftData.getPowerSpectrum();
		normalizedData = SpectralEntropy
				.performFFTNormalisation(powerSpectrum);
		double spectralEntropy = SpectralEntropy
				.calculateSpectralEntropy(normalizedData);
		
		return spectralEntropy;
	}
	
	public static double zcr(double[] signals, int winlen){
		int lengthInSecond = winlen; //window length TODO: specify
        int numZC=0;
        int size=signals.length;
        
        for (int i=0; i<size-1; i++){
                if((signals[i]>=0 && signals[i+1]<0) || (signals[i]<0 && signals[i+1]>=0)){
                        numZC++;
                }
        }                 
        Double res = (double)numZC/(size);
//        if (numZC == 0)
//        	return 0.0;
        return (double)numZC/(size);
	}
	
	public static double mcr(double[] signals, double mean, int winlen){
		int lengthInSecond = winlen; //window length TODO: specify
        int numZC=0;
        int size=signals.length;
        
        for (int i=0; i<size-1; i++){
                if((signals[i]>=mean && signals[i+1]<mean) || (signals[i]<mean && signals[i+1]>=mean)){
                        numZC++;
                }
        }                       

        return (double)numZC/(size);
	}
	
	/**
	 * Method that performs z-score normalization to a feature set
	 * From each value in each feature vector (per segment) 
	 * we substract the vector's mean value and divide by the vector's std
	 * @return
	 */
	public static HashMap<String, ArrayList<Double>> z_scoreNormalization(HashMap<String, ArrayList<Double>> featureVals){
		HashMap<String, ArrayList<Double>> featureValsNormalized = featureVals;
		Set<Entry<String, ArrayList<Double>>> entryset = featureVals.entrySet();
		
		Iterator<Entry<String, ArrayList<Double>>> it = entryset.iterator();
		while(it.hasNext()){
			Entry<String, ArrayList<Double>> ent = it.next();
			String key = ent.getKey();
			ArrayList<Double> vals = ent.getValue();
			double[] valuesToArray = CollectionUtilities.listToArray(vals);
			
			//mean
			Mean m = new Mean();
			double mean = m.evaluate(valuesToArray);
			
			//std
			StandardDeviation st = new StandardDeviation();
			double std = st.evaluate(valuesToArray);
			
			if ((mean != 0) && (std != 0) && (!vals.contains(Double.NaN))) {
				for (int i=0; i<vals.size(); i++){
					double norm = (vals.get(i)-mean)/std;
					vals.set(i, norm);
				}
				featureValsNormalized.put(key, vals);
			}
			else {
				badFeatures.add(key);
//				System.out.println("bad key" + key);
			}

		}
		
		return featureValsNormalized;
	}
	
	
	public static ArrayList<String> getBadFeatures(){
		return badFeatures;
	}
	
	/**
	 * 
	 * @param valuesToArray: a data segment
	 * @return HashMap<String,Double> featureSet for the specific data segment
	 */
	public static HashMap<String, Double> computeFeatures(ArrayList<Double> values, double[] valuesToArray, 
			int channel, int seg, int dev_id, int windowSizezeSec){
		HashMap<String, Double> featSet = new HashMap<String, Double>();
		da = new DescriptiveStatistics(valuesToArray);
		
		//mean
		Mean m = new Mean();
		double mean = m.evaluate(valuesToArray);
//		System.out.println("mean: "+mean);
		
		//variance
		Variance v = new Variance();
		double var = v.evaluate(valuesToArray);
//		System.out.println("var: "+var);
		
		//median
		Median med = new Median();
		double median = med.evaluate(valuesToArray);
//		System.out.println("median: "+median);
		
		//std
		StandardDeviation st = new StandardDeviation();
		double std = st.evaluate(valuesToArray);
//		System.out.println("std: "+std);
		
		//kurtosis
		Kurtosis k = new Kurtosis();
		double kurt = k.evaluate(valuesToArray);
//		System.out.println("kurtosis: "+kurt);
		
		//skewness
		Skewness s = new Skewness();
		double skew = s.evaluate(valuesToArray);
//		System.out.println("skewness: "+skew);
		
		//interquantile range
		double iqr = da.getPercentile(75) - da.getPercentile(25);
//		System.out.println("interquantile range: "+iqr);
		
		//root mean square
		double rms = Calculations.rms(valuesToArray);
//		System.out.println("root mean square: "+rms);
		
		//spectral entropy
		if (values.size() < 3)
			System.out.println(values);
		//https://github.com/ebbywiselyn/android-sensor/blob/master/TweetSensor/src/tweet/sensor/environment/audio/SpectralEntropy.java
		double spectralEntropy = Calculations.spec(valuesToArray);
//		System.out.println("spectral entropy: "+spectralEntropy);
		
		//zero crossing rate
		double zcr = Calculations.zcr(valuesToArray, windowSizezeSec);
//		System.out.println("zcr: "+values);
//		System.out.println("zcr: "+zcr);
		
		//mean crossing rate
		double mcr = Calculations.mcr(valuesToArray, mean, windowSizezeSec);
//		System.out.println("mcr: "+mcr);
		
		//mean derivatives TODO
	
		//put values in features set HashMap
		featSet.put("mean"+channel+"d"+dev_id, mean);
		featSet.put("var"+channel+"d"+dev_id, var);
		featSet.put("median"+channel+"d"+dev_id, median);
		featSet.put("std"+channel+"d"+dev_id, std);
		featSet.put("kurtosis"+channel+"d"+dev_id, kurt);
		featSet.put("skewness"+channel+"d"+dev_id, skew);
		featSet.put("iqr"+channel+"d"+dev_id, iqr);
		featSet.put("rms"+channel+"d"+dev_id, rms);
		featSet.put("spec"+channel+"d"+dev_id, spectralEntropy);
		featSet.put("zcr"+channel+"d"+dev_id, zcr);
		featSet.put("mcr"+channel+"d"+dev_id, mcr);
		
		return featSet;
	}
}
