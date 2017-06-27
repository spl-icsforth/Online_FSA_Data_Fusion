package com.example.karayan.online_fsa;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import Acquisition.AcquisitionDB;
import DataBase.DBDataSource;
import DataBase.Device;
import DataBase.Measurement;
import DominantStreams.DominantStreams;
import FeatureExtraction.FeatureExtraction;
import Segmentation.SegmentationSingleLocation;
import Threads.DefaultExecutorSupplier;
import Utilities.Classification;
import Utilities.CollectionUtilities;
import Utilities.DeviceFeatures;
import Utilities.FSA;
import Utilities.IO;
import Utilities.Results;
import weka.core.Instances;

public class MainActivity extends AppCompatActivity {
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    ArrayList<Double> memoryList = new ArrayList<Double>();
    ArrayList<Double> memoryList2 = new ArrayList<Double>();
    ArrayList<Integer> terminationList = new ArrayList<Integer>(2);
    ArrayList<Integer> terminationList2 = new ArrayList<Integer>(1);

    ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> mergedRawData = new ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>>();
    ArrayList<HashMap<String, ArrayList<Double>>> mergedFeatures = new ArrayList<HashMap<String, ArrayList<Double>>>();
    ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawDataDev1 = new ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>>();
    ArrayList<HashMap<String, ArrayList<Double>>> allFeaturesDev1 = new ArrayList<HashMap<String, ArrayList<Double>>>();
    ArrayList<HashMap<String, ArrayList<Double>>> selectedFeaturesDev1 = new ArrayList<HashMap<String, ArrayList<Double>>>();
    ArrayList<ArrayList<Integer>> domStreamsDev1 = new ArrayList<ArrayList<Integer>>();

    ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawDataDev2 = new ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>>();
    ArrayList<HashMap<String, ArrayList<Double>>> allFeaturesDev2 = new ArrayList<HashMap<String, ArrayList<Double>>>();
    ArrayList<HashMap<String, ArrayList<Double>>> selectedFeaturesDev2 = new ArrayList<HashMap<String, ArrayList<Double>>>();
    ArrayList<ArrayList<Integer>> domStreamsDev2 = new ArrayList<ArrayList<Integer>>();

    ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawDataDev3 = new ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>>();
    ArrayList<HashMap<String, ArrayList<Double>>> allFeaturesDev3 = new ArrayList<HashMap<String, ArrayList<Double>>>();
    ArrayList<HashMap<String, ArrayList<Double>>> selectedFeaturesDev3 = new ArrayList<HashMap<String, ArrayList<Double>>>();
    ArrayList<ArrayList<Integer>> domStreamsDev3 = new ArrayList<ArrayList<Integer>>();

    ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawDataDev4 = new ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>>();
    ArrayList<HashMap<String, ArrayList<Double>>> allFeaturesDev4 = new ArrayList<HashMap<String, ArrayList<Double>>>();
    ArrayList<HashMap<String, ArrayList<Double>>> selectedFeaturesDev4 = new ArrayList<HashMap<String, ArrayList<Double>>>();
    ArrayList<ArrayList<Integer>> domStreamsDev4 = new ArrayList<ArrayList<Integer>>();

    ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawDataDev5 = new ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>>();
    ArrayList<HashMap<String, ArrayList<Double>>> allFeaturesDev5 = new ArrayList<HashMap<String, ArrayList<Double>>>();
    ArrayList<HashMap<String, ArrayList<Double>>> selectedFeaturesDev5 = new ArrayList<HashMap<String, ArrayList<Double>>>();
    ArrayList<ArrayList<Integer>> domStreamsDev5 = new ArrayList<ArrayList<Integer>>();

    ArrayList<ArrayList<Double>> all_labels = new ArrayList<ArrayList<Double>>();

    ArrayList<Long> elapsed_times = new ArrayList<Long>();

    int global_cnt = 0;

    int participant_no = 0;

    int W_win = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 3; i++)
            terminationList.add(0);

        for (int i = 0; i < 1; i++)
            terminationList2.add(0);

        // uncomment to delete DB and recreate
//        this.deleteDatabase("HAR.db");
//        new CreateDataBases().execute("");

        // run pipeline on multiple sensor locations
        runMultipleAsyncTask();

        // wait until all threads are terminated
        while (terminationList.contains(0)) ;

        // execute second stage of FSA after all threads are terminated
        if (!terminationList.contains(0)) {
            new FeatureSelectionStage2().execute("");
//            new ClassificationStage2().execute("");
        }
    }

    public DeviceFeatures pipeline(double W, int device_id, int participant_no) {
        // **** DATA ACQUISITION **** //
        final DBDataSource datasource = new DBDataSource(this);
        datasource.open();
        AcquisitionDB acquisitionDB = new AcquisitionDB();

        int top = 0;
        int prev_top = -100;
        int WW = 0;
        int cnt = 0;

        ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>> rawDataPerW = new ArrayList<HashMap<Integer, ArrayList<ArrayList<Double>>>>();
        ArrayList<HashMap<String, ArrayList<Double>>> allFeaturesPerW = new ArrayList<HashMap<String, ArrayList<Double>>>();
        ArrayList<HashMap<String, ArrayList<Double>>> selectedFeaturesPerW = new ArrayList<HashMap<String, ArrayList<Double>>>();
        ArrayList<ArrayList<Integer>> dominantStreamsPerW = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Double>> labelsPerW = new ArrayList<ArrayList<Double>>();
        while (true) {
            long start_time_full = System.nanoTime();
            if (WW > W_win) break; // TODO one iteration ??
            System.out.println("EXECUTING PIPELINE FOR W_p = " + WW);
            ArrayList<Results> results = new ArrayList<Results>();

            System.out.println(WW);
            System.out.println("prev :" + prev_top + " top :" + top);
            if (prev_top >= top) break;

            long start_time_ACQ = System.nanoTime();
            acquisitionDB.selectAllFromMeasurements(datasource, device_id, W, "measurements"+participant_no);
            long elapsed_time_ACQ = System.nanoTime() - start_time_ACQ;

//            if (WW != 1 && WW != 3) {
//            if (WW!=4 && WW!=5 && WW!=6 && WW!=7
//                    && WW!=12 && WW!=13 && WW!=14 && WW!=15) {
//                System.out.println("WW is : " + WW);
                if (WW < W_win ) {

                    prev_top = top;
                top = acquisitionDB.getTop();
                System.out.println("prev top::::" + prev_top + " top ::::" + top);

                WW++;
                continue;
            }

            HashMap<Integer, ArrayList<Double>> rawData = acquisitionDB.getData();
            ArrayList<Double> labels = new ArrayList<Double>();
            labels = acquisitionDB.getLabels();

            // **** SEGMENTATION **** //
            int windowSizeSec = 2;
            double overlapping_a = 0.5;
            System.out.println("DATA SEGMENTATION");
            long start_time_SEG = System.nanoTime();
            SegmentationSingleLocation segmentation = new SegmentationSingleLocation(rawData, labels);
            segmentation.SegmentationOverlapping(windowSizeSec, overlapping_a);
            long elapsed_time_SEG = System.nanoTime() - start_time_SEG;

            HashMap<Integer, ArrayList<ArrayList<Double>>> segmentedData = segmentation.getSegmentedData();
            ArrayList<Double> finalLabels = segmentation.getFinalLabels();
            System.out.println("finalLabels: " + finalLabels);
            this.all_labels.add(cnt, new ArrayList<Double>());
            this.all_labels.set(cnt, finalLabels);

            if (finalLabels.size() < 1) {
                System.out.println("too few data");
                break;
            }

            // **** FEATURE EXTRACTION **** //
            System.out.println("FEATURE EXTRACTION");
            boolean normalized = true;
            final FeatureExtraction FE = new FeatureExtraction(segmentedData, finalLabels, device_id, participant_no, windowSizeSec);
            long start_time_FE = System.nanoTime();
            FE.createFeatureSet(normalized);
            long elapsed_time_FE = System.nanoTime() - start_time_FE;

            HashMap<Integer, ArrayList<HashMap<String, Double>>> featureSet = FE.getInitialFeatureSet();
            HashMap<String, ArrayList<Double>> finalFeatureSet_all = FE.getFinalFeatureSet();
            HashMap<String, ArrayList<Double>> finalFeatureSet = new HashMap<String, ArrayList<Double>>();
            finalFeatureSet.putAll(finalFeatureSet_all);

            if (normalized) {
                ArrayList<String> badFeatures = FE.getBadFeatures();
//                System.out.println("badFeatures: " + badFeatures);
                for (int i = 0; i < badFeatures.size(); i++) {
                    finalFeatureSet.remove(badFeatures.get(i));
                }
            }
            System.out.println("prev size: " + finalFeatureSet_all.size() + " curr size: " + finalFeatureSet.size());

            TreeSet<String> featureNames = new TreeSet<String>(finalFeatureSet.keySet());
            String[] featureNames_Arr = featureNames.toArray(new String[featureNames.size()]);

            TreeSet<String> featureNames_all = new TreeSet<String>(finalFeatureSet_all.keySet());
            String[] featureNames_Arr_all = featureNames_all.toArray(new String[featureNames_all.size()]);

            // **** FEATURE SELECTION **** //
            System.out.println("FEATURE SELECTION");



//            long start_time_FS = System.nanoTime();
//            FSA fsares = new FSA();
//            fsares.runGCNC(device_id, participant_no, windowSizeSec, finalFeatureSet, featureNames_Arr, finalLabels,
//                    elapsed_time_ACQ, elapsed_time_SEG, elapsed_time_FE);
//            long elapsed_time_FS = System.nanoTime() - start_time_FS;
//            ArrayList<Integer> gcnc_reduced_set = fsares.getReducedSet();
//            Results res5 = fsares.getResults();
////
////
//            DominantStreams streams_gcnc = new DominantStreams(gcnc_reduced_set, featureNames_Arr, 9);
//            streams_gcnc.categorizeFeatures();
//            ArrayList<Integer> dominantStreams = streams_gcnc.getStream_indexes();
////            System.out.println(streams.getStream_indexes());
//            System.out.println(streams_gcnc.getStream_indexes());
//
//            HashMap<String, ArrayList<Double>> dominantFeatures = CollectionUtilities.getDominantFeatures(finalFeatureSet, featureNames_Arr, gcnc_reduced_set);
//            System.out.println("gcnc dominantFeatures:::: "+dominantFeatures.keySet());

            long start_time_FS = System.nanoTime();
            FSA fsares2 = new FSA();
            fsares2.runNCRE(device_id, participant_no, windowSizeSec, finalFeatureSet, featureNames_Arr, finalLabels,
                    elapsed_time_ACQ, elapsed_time_SEG, elapsed_time_FE);
            long elapsed_time_FS = System.nanoTime() - start_time_FS;
            ArrayList<Integer> ncre_reduced_set = fsares2.getReducedSet();
            Results res5 = fsares2.getResults();

            ArrayList<Integer> range = CollectionUtilities.rangeArrayList(0, finalFeatureSet.size());
            DominantStreams streams = new DominantStreams(range, featureNames_Arr, 9);
            streams.categorizeFeatures();

            DominantStreams streams_ncre = new DominantStreams(ncre_reduced_set, featureNames_Arr, 9);
            streams_ncre.categorizeFeatures();
            ArrayList<Integer> dominantStreams = streams_ncre.getStream_indexes();
            System.out.println(streams.getStream_indexes());
            System.out.println(streams_ncre.getStream_indexes());

            HashMap<String, ArrayList<Double>> dominantFeatures = CollectionUtilities.getDominantFeatures(finalFeatureSet, featureNames_Arr, ncre_reduced_set);
            System.out.println("dominantFeatures:::: " + dominantFeatures.keySet());

            /**
             * change value of top to proceed to next WW chunk
             */
            prev_top = top;
            top = acquisitionDB.getTop();

            rawDataPerW.add(segmentedData);
            allFeaturesPerW.add(finalFeatureSet);
            selectedFeaturesPerW.add(dominantFeatures);
            dominantStreamsPerW.add(dominantStreams);

            long elapsed_time_full = System.nanoTime() - start_time_full;
            res5.setElapsedTimeFull(elapsed_time_full);

            results.add(res5);

            IO.writeFeatureSetToFile("/storage/sdcard0/final_experiments/3dev/exp30sec/stage1/part" + participant_no + "_dev" + device_id + "_W" + W + "_" + cnt + "_winsize" + windowSizeSec + "_features.csv", finalFeatureSet_all,
                    finalLabels, featureNames_Arr_all);

            IO.writeResultsToFile("/storage/sdcard0/final_experiments/3dev/exp30sec/stage1/TRACEstats" + "_dev" + device_id + "_W" + W + "_" + cnt + ".csv", results);

            WW++;
            cnt++;


            elapsed_times.add(elapsed_time_ACQ);
            elapsed_times.add(elapsed_time_SEG);
            elapsed_times.add(elapsed_time_FE);
            elapsed_times.add(elapsed_time_FS);
        }

        DeviceFeatures deviceFeatures = new DeviceFeatures();
        deviceFeatures.setRawData(rawDataPerW);
        deviceFeatures.setAllFeatures(allFeaturesPerW);
        deviceFeatures.setSelectedFeatures(selectedFeaturesPerW);
        deviceFeatures.setDominantStreams(dominantStreamsPerW);

        return deviceFeatures;
    }


    private class Arch1_FeatureSelectionStage2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            int size = selectedFeaturesDev1.size();
            long start_time_stage1 = System.nanoTime();
            // all WW
//
            ArrayList<HashMap<String, ArrayList<Double>>> PC_features = CollectionUtilities.computePCInterLocation(rawDataDev1, rawDataDev2, rawDataDev3, rawDataDev4, rawDataDev5);
            System.out.println("PC size" + PC_features.get(0).size());

//            IO.writeFeatureSetToFile("/storage/sdcard0/parallel/PC.csv", PC_features.get(0), all_labels.get(0), featureN_Arr);

            ArrayList<Results> results = new ArrayList<Results>();
            for (int WW = 0; WW < size; WW++) {
//                HashMap<String, ArrayList<Double>> curr_features = mergedFeatures.get(WW);
                ArrayList<Double> curr_labels = all_labels.get(WW);

//                TreeSet<String> featureNamesDev1 = new TreeSet<String>(curr_features.keySet());
//                String[] featureNames_Arr = featureNamesDev1.toArray(new String[featureNamesDev1.size()]);


                TreeSet<String> featureN = new TreeSet<String>(PC_features.get(WW).keySet());
                String[] featureN_Arr = featureN.toArray(new String[featureN.size()]);
                IO.writeFeatureSetToFile("/storage/sdcard0/final_experiments/3dev/exp30sec/stage1/PCfeatures_part" + participant_no + "_WW" + WW + ".csv", PC_features.get(WW), curr_labels, featureN_Arr);

            }
            long elapsed_time_arch1 = System.nanoTime() - start_time_stage1;
            System.out.println("elapsed time arch1: " + elapsed_time_arch1);
            return ("ok");
        }

        @Override
        protected void onPostExecute(String result) {
//            wakeLock.release();
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
//            powerManager = (PowerManager) getSystemService(POWER_SERVICE);
//            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
//                    "MyWakelockTag");
//            wakeLock.acquire();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class ClassificationStage2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            int size = selectedFeaturesDev1.size();
            double threshold = 0.2;

            ArrayList<Long> exec_times_1 = new ArrayList<Long>(size);

            // merge features
            mergedFeatures = CollectionUtilities.mergeDominantFeatures(selectedFeaturesDev1, selectedFeaturesDev3);
            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, selectedFeaturesDev4);
//            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, selectedFeaturesDev4);
//            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, selectedFeaturesDev5);
//            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, PC_features);
            // start new thread for FS
//            System.out.println("allFeaturesDev1: "+allFeaturesDev1);

            ArrayList<Results> results = new ArrayList<Results>();
            for (int WW = 0; WW < size; WW++) {
                long start_time2 = System.nanoTime();

                HashMap<String, ArrayList<Double>> curr_features = mergedFeatures.get(WW);
                ArrayList<Double> curr_labels = all_labels.get(WW);

                TreeSet<String> featureNames_curr = new TreeSet<String>(curr_features.keySet());
                String[] featureNames_Arr = featureNames_curr.toArray(new String[featureNames_curr.size()]);

                // **** FEATURE SELECTION **** //
                System.out.println("CLASSIFICATION STAGE 2");
                System.out.println("FOR WW " + WW);
                System.out.println("curr_features: "+curr_features.keySet());

                Instances data = null;
                System.out.println("labels: "+curr_labels);
                data = CollectionUtilities.createkNNInput(curr_features, featureNames_Arr, curr_labels);
                data.setClassIndex(data.numAttributes() - 1);


                int folds = 10;
                int runs = 10;


                double mean_Accuracy = Classification.KNN(data, folds, runs);
                System.out.println("mean_Accuracy all runs: "+mean_Accuracy);

                long elapsed_time2 = System.nanoTime() - start_time2;

                Results res = new Results(participant_no, "4", 2, 0, new ArrayList<Integer>(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, mean_Accuracy, elapsed_time2);// battACQ, battSEG, battFE, battNCRE);
                IO.writeSingleResultToFileCA("/storage/sdcard0/final_experiments/3dev/exp30sec/classification/TRACEstats_classification_stage2" + "_WW" + WW + ".csv", res);

            }

            return ("ok");
        }

        @Override
        protected void onPostExecute(String result) {
            wakeLock.release();
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
            powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "MyWakelockTag");
            wakeLock.acquire();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class FeatureSelectionStage2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            int size = selectedFeaturesDev1.size();
            double threshold = 0.2;

            ArrayList<Long> exec_times_1 = new ArrayList<Long>(size);

            /**
             *  create a subset of PC features based on dominant channels
             */
            ArrayList<HashMap<String, ArrayList<Double>>> PC_features = new ArrayList<HashMap<String, ArrayList<Double>>>();
            ArrayList<ArrayList<Integer>> dominant_channelsDev1 = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> dominant_channelsDev3 = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> dominant_channelsDev4 = new ArrayList<ArrayList<Integer>>();

            for (int W = 0; W < size; W++) {
                long start_time1 = System.nanoTime();

                HashMap<String, ArrayList<Double>> pc_perW = new HashMap<String, ArrayList<Double>>();

                Set<String> keysDev1 = selectedFeaturesDev1.get(W).keySet();
                int[] channels1 = CollectionUtilities.getDominantChannels(keysDev1, threshold);
                dominant_channelsDev1.add(CollectionUtilities.arrayToList(channels1));
//                int[] modalities1 = new int[]{dominant_channel1};

//                Set<String> keysDev2 = selectedFeaturesDev2.get(W).keySet();
//                int dominant_channel2 = CollectionUtilities.getDominantChannels(keysDev2);
//
                Set<String> keysDev3 = selectedFeaturesDev3.get(W).keySet();
                int[] channels3 = CollectionUtilities.getDominantChannels(keysDev3, threshold);
                dominant_channelsDev3.add(CollectionUtilities.arrayToList(channels3));

                Set<String> keysDev4 = selectedFeaturesDev4.get(W).keySet();
                int[] channels4 = CollectionUtilities.getDominantChannels(keysDev4, threshold);
                dominant_channelsDev4.add(CollectionUtilities.arrayToList(channels4));
//                int[] modalities4 = new int[]{dominant_channel4};

//                Set<String> keysDev5 = selectedFeaturesDev5.get(W).keySet();
//                int dominant_channel5 = CollectionUtilities.getDominantChannels(keysDev5);


                pc_perW = CollectionUtilities.computePCInterLocation(
                        rawDataDev1.get(W), null, rawDataDev3.get(W), rawDataDev4.get(W), null,
                        channels1, null, channels3, channels4, null);

                PC_features.add(pc_perW);

//                System.out.println("pc_perW : "+pc_perW.keySet());

                long elapsed_time1 = System.nanoTime() - start_time1;
                exec_times_1.add(elapsed_time1);
            }

            ArrayList<HashMap<String, ArrayList<Double>>> dominantChannelsDev1 = CollectionUtilities.retrieveFeaturesDominantChannels(allFeaturesDev1, dominant_channelsDev1);
            ArrayList<HashMap<String, ArrayList<Double>>> dominantChannelsDev3 = CollectionUtilities.retrieveFeaturesDominantChannels(allFeaturesDev3, dominant_channelsDev3);
            ArrayList<HashMap<String, ArrayList<Double>>> dominantChannelsDev4 = CollectionUtilities.retrieveFeaturesDominantChannels(allFeaturesDev4, dominant_channelsDev4);

            // merge features
            mergedFeatures = CollectionUtilities.mergeDominantFeatures(dominantChannelsDev1, dominantChannelsDev3);
            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, dominantChannelsDev4);
//            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, selectedFeaturesDev4);
//            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, selectedFeaturesDev5);
            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, PC_features);
            // start new thread for FS
//            System.out.println("allFeaturesDev1: "+allFeaturesDev1);

            ArrayList<Results> results = new ArrayList<Results>();
            // TODO iterate features ...
            for (int WW = 0; WW < size; WW++) {
                long start_time2 = System.nanoTime();

                HashMap<String, ArrayList<Double>> curr_features = mergedFeatures.get(WW);
                ArrayList<Double> curr_labels = all_labels.get(WW);

                TreeSet<String> featureNames_curr = new TreeSet<String>(curr_features.keySet());
                String[] featureNames_Arr = featureNames_curr.toArray(new String[featureNames_curr.size()]);

                // **** FEATURE SELECTION **** //
                System.out.println("FEATURE SELECTION STAGE 2");
                System.out.println("FOR WW " + WW);
                System.out.println("curr_features: "+curr_features.keySet());
                FSA fsares = new FSA();
                fsares.runGCNC(-1, participant_no, 2, curr_features, featureNames_Arr, curr_labels,
                        0, 0, 0);
                ArrayList<Integer> gcnc_reduced_set = fsares.getReducedSet();
                Results res5 = fsares.getResults();
//
//                results.add(res5);

//                FSA fsares2 = new FSA();
//                fsares2.runNCRE(-1, participant_no, 2, curr_features, featureNames_Arr, curr_labels,
//                        0, 0, 0);
//                ArrayList<Integer> ncre_reduced_set = fsares2.getReducedSet();
//                Results res5 = fsares2.getResults();

                long elapsed_time2 = System.nanoTime() - start_time2;

                res5.setElapsedTimeFull(exec_times_1.get(WW) + elapsed_time2);

                results.add(res5);

//                System.out.println("bef" + elapsed_time2);
                IO.writeFeatureSetToFile("/storage/sdcard0/final_experiments/3dev/exp30sec/arch2b/gcnc20p_arch2b_stage2_features_part" + participant_no + "_WW" + WW + ".csv", curr_features,
                        curr_labels, featureNames_Arr);

                IO.writeSingleResultToFile("/storage/sdcard0/final_experiments/3dev/exp30sec/arch2b/TRACEstats_arch2b_stage2" + "_WW" + WW + ".csv", res5);
            }

            return ("ok");
        }

        @Override
        protected void onPostExecute(String result) {
            wakeLock.release();
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
            powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "MyWakelockTag");
            wakeLock.acquire();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private void runMultipleAsyncTask() {
        /**
         * Device 1 thread
         */
        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some background work here.
                        double W = 0.5;
                        int device_id = 1;
                        int participant_no = 0;
                        DeviceFeatures featuresDev1 = pipeline(W, device_id, participant_no);
                        rawDataDev1 = featuresDev1.getRawData();
                        allFeaturesDev1 = featuresDev1.getAllFeatures();
                        selectedFeaturesDev1 = featuresDev1.getSelectedFeatures();
                        domStreamsDev1 = featuresDev1.getDominantStreams();

                        System.out.println("background job 1");

                        terminationList.set(0, 1);
                    }
                });


        /**
         * Device 2 thread
         */
        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some background work here.
                        double W = 0.5;
                        int device_id = 4;
                        int participant_no = 0;
                        DeviceFeatures featuresDev4 = pipeline(W, device_id, participant_no);
                        rawDataDev4 = featuresDev4.getRawData();
                        allFeaturesDev4 = featuresDev4.getAllFeatures();
                        selectedFeaturesDev4 = featuresDev4.getSelectedFeatures();
                        domStreamsDev4 = featuresDev4.getDominantStreams();
                        System.out.println("background job 4");

                        terminationList.set(1, 1);
                        System.out.println("background job 2");
                    }
                });


        /**
         * Device 3 thread
         */
        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some background work here.
                        double W = 0.5;
                        int device_id = 3;
                        int participant_no = 0;
                        DeviceFeatures featuresDev3 = pipeline(W, device_id, participant_no);
                        rawDataDev3 = featuresDev3.getRawData();
                        allFeaturesDev3 = featuresDev3.getAllFeatures();
                        selectedFeaturesDev3= featuresDev3.getSelectedFeatures();
                        domStreamsDev3 = featuresDev3.getDominantStreams();
                        terminationList.set(2, 1);
                        System.out.println("background job 3");
                    }
                });

        /**
         * Memory monitoring thread
         */
        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some background work here.
                        while (terminationList.contains(0)) {
                            double mem = logHeap();
                            memoryList.add(mem);

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("memoryList = "+memoryList);
//                        IO.writeArrayListToFile("/storage/sdcard0/memory_fsa/R_gcnc_memorypart"+participant_no+"_W"+W_win+".csv", memoryList);
                    }
                });
    }

    private void runMultipleAsyncTask2() {
        /**
         * classification thread
         */
//        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
//                .execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        // do some background work here.
//                        int size = selectedFeaturesDev1.size();
//                        double threshold = 0.2;
//
//                        ArrayList<Long> exec_times_1 = new ArrayList<Long>(size);
//
//                        // merge features
//                        mergedFeatures = CollectionUtilities.mergeDominantFeatures(selectedFeaturesDev1, selectedFeaturesDev3);
//                        mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, selectedFeaturesDev4);
////            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, selectedFeaturesDev4);
////            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, selectedFeaturesDev5);
////            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, PC_features);
//                        // start new thread for FS
////            System.out.println("allFeaturesDev1: "+allFeaturesDev1);
//
//                        ArrayList<Results> results = new ArrayList<Results>();
//                        // TODO iterate features ...
//                        for (int WW = 0; WW < size; WW++) {
//
//
//                            HashMap<String, ArrayList<Double>> curr_features = mergedFeatures.get(WW);
//                            ArrayList<Double> curr_labels = all_labels.get(WW);
//
//                            TreeSet<String> featureNames_curr = new TreeSet<String>(curr_features.keySet());
//                            String[] featureNames_Arr = featureNames_curr.toArray(new String[featureNames_curr.size()]);
//
//                            // **** FEATURE SELECTION **** //
//                            System.out.println("CLASSIFICATION STAGE 2");
//                            System.out.println("FOR WW " + WW);
////                            System.out.println("curr_features: " + curr_features.keySet());
//
//
//
//
//
//                            Instances data = null;
////                            System.out.println("labels: " + curr_labels);
//                            data = CollectionUtilities.createkNNInput(curr_features, featureNames_Arr, curr_labels);
//                            data.setClassIndex(data.numAttributes() - 1);
//
//
//                            int folds = 10;
//                            int runs = 10;
//                            long start_time2 = System.nanoTime();
//                            double mean_Accuracy = Classification.KNN(data, folds, runs);
//                            long elapsed_time2 = System.nanoTime() - start_time2;
//                            System.out.println("mean_Accuracy all runs: " + mean_Accuracy);
//                            elapsed_times.add(elapsed_time2);
//                        }
//                            terminationList2.set(0, 1);
//                        System.out.println("classification job end");
//                    }
//                });

        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some background work here.
                        int size = selectedFeaturesDev1.size();
                        double threshold = 0.2;

                        ArrayList<Long> exec_times_1 = new ArrayList<Long>(size);


                        ArrayList<HashMap<String, ArrayList<Double>>> PC_features = new ArrayList<HashMap<String, ArrayList<Double>>>();
                        ArrayList<ArrayList<Integer>> dominant_channelsDev1 = new ArrayList<ArrayList<Integer>>();
                        ArrayList<ArrayList<Integer>> dominant_channelsDev3 = new ArrayList<ArrayList<Integer>>();
                        ArrayList<ArrayList<Integer>> dominant_channelsDev4 = new ArrayList<ArrayList<Integer>>();

                        for (int W = 0; W < size; W++) {
                            long start_time1 = System.nanoTime();

                            HashMap<String, ArrayList<Double>> pc_perW = new HashMap<String, ArrayList<Double>>();

                            Set<String> keysDev1 = selectedFeaturesDev1.get(W).keySet();
                            int[] channels1 = CollectionUtilities.getDominantChannels(keysDev1, threshold);
                            dominant_channelsDev1.add(CollectionUtilities.arrayToList(channels1));
//                int[] modalities1 = new int[]{dominant_channel1};

//                Set<String> keysDev2 = selectedFeaturesDev2.get(W).keySet();
//                int dominant_channel2 = CollectionUtilities.getDominantChannels(keysDev2);
//
                            Set<String> keysDev3 = selectedFeaturesDev3.get(W).keySet();
                            int[] channels3 = CollectionUtilities.getDominantChannels(keysDev3, threshold);
                            dominant_channelsDev3.add(CollectionUtilities.arrayToList(channels3));

                            Set<String> keysDev4 = selectedFeaturesDev4.get(W).keySet();
                            int[] channels4 = CollectionUtilities.getDominantChannels(keysDev4, threshold);
                            dominant_channelsDev4.add(CollectionUtilities.arrayToList(channels4));
//                int[] modalities4 = new int[]{dominant_channel4};

//                Set<String> keysDev5 = selectedFeaturesDev5.get(W).keySet();
//                int dominant_channel5 = CollectionUtilities.getDominantChannels(keysDev5);


                            pc_perW = CollectionUtilities.computePCInterLocation(
                                    rawDataDev1.get(W), null, rawDataDev3.get(W), rawDataDev4.get(W), null,
                                    channels1, null, channels3, channels4, null);

                            PC_features.add(pc_perW);

//                System.out.println("pc_perW : "+pc_perW.keySet());

                            long elapsed_time1 = System.nanoTime() - start_time1;
                            exec_times_1.add(elapsed_time1);
                        }

                        ArrayList<HashMap<String, ArrayList<Double>>> dominantChannelsDev1 = CollectionUtilities.retrieveFeaturesDominantChannels(allFeaturesDev1, dominant_channelsDev1);
                        ArrayList<HashMap<String, ArrayList<Double>>> dominantChannelsDev3 = CollectionUtilities.retrieveFeaturesDominantChannels(allFeaturesDev3, dominant_channelsDev3);
                        ArrayList<HashMap<String, ArrayList<Double>>> dominantChannelsDev4 = CollectionUtilities.retrieveFeaturesDominantChannels(allFeaturesDev4, dominant_channelsDev4);

                        // merge features
                        mergedFeatures = CollectionUtilities.mergeDominantFeatures(dominantChannelsDev1, dominantChannelsDev3);
                        mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, dominantChannelsDev4);
//            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, selectedFeaturesDev4);
//            mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, selectedFeaturesDev5);
                        mergedFeatures = CollectionUtilities.mergeDominantFeatures(mergedFeatures, PC_features);
                        // start new thread for FS
//            System.out.println("allFeaturesDev1: "+allFeaturesDev1);

                        ArrayList<Results> results = new ArrayList<Results>();
                        // TODO iterate features ...
                        for (int WW = 0; WW < size; WW++) {
                            long start_time2 = System.nanoTime();

                            HashMap<String, ArrayList<Double>> curr_features = mergedFeatures.get(WW);
                            ArrayList<Double> curr_labels = all_labels.get(WW);

                            TreeSet<String> featureNames_curr = new TreeSet<String>(curr_features.keySet());
                            String[] featureNames_Arr = featureNames_curr.toArray(new String[featureNames_curr.size()]);

                            // **** FEATURE SELECTION **** //
                            System.out.println("FEATURE SELECTION STAGE 2");
                            System.out.println("FOR WW " + WW);
                            System.out.println("curr_features: "+curr_features.keySet());

//                            FSA fsares = new FSA();
//                            long start_timeFSA = System.nanoTime();
//                            fsares.runGCNC(-1, participant_no, 2, curr_features, featureNames_Arr, curr_labels,
//                                    0, 0, 0);
//                            long elapsed_timeFSA = System.nanoTime() - start_timeFSA;
//                            elapsed_times.add(elapsed_timeFSA);
//                            ArrayList<Integer> gcnc_reduced_set = fsares.getReducedSet();
//                            Results res5 = fsares.getResults();

//                            results.add(res5);

                            FSA fsares2 = new FSA();
                            long start_timeFSA = System.nanoTime();
                            fsares2.runNCRE(-1, participant_no, 2, curr_features, featureNames_Arr, curr_labels,
                                    0, 0, 0);
                            long elapsed_timeFSA = System.nanoTime() - start_timeFSA;
                            elapsed_times.add(elapsed_timeFSA);
                            ArrayList<Integer> ncre_reduced_set = fsares2.getReducedSet();
                            Results res5 = fsares2.getResults();

                            long elapsed_time2 = System.nanoTime() - start_time2;

                            res5.setElapsedTimeFull(exec_times_1.get(WW) + elapsed_time2);
//
//                            results.add(res5);

//                System.out.println("bef" + elapsed_time2);
//                IO.writeFeatureSetToFile("/storage/sdcard0/3dev/exp30sec15p/arch2b/ncre_arch2b_stage2_features_part" + participant_no + "_WW" + WW + ".csv", curr_features,
//                        curr_labels, featureNames_Arr);
//
//                IO.writeSingleResultToFile("/storage/sdcard0/3dev/exp30sec15p/arch2b/TRACEstats_arch2b_stage2" + "_WW" + WW + ".csv", res5);

                        }
                        terminationList2.set(0, 1);
                        System.out.println("fsa stage2 job end");
                    }
                });

        /**
         * Memory monitoring thread
         */
        DefaultExecutorSupplier.getInstance().forBackgroundTasks()
                .execute(new Runnable() {
                    @Override
                    public void run() {
                        // do some background work here.
                        while (terminationList2.contains(0)) {
                            double mem = logHeap();
                            memoryList2.add(mem);

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("memoryList2 = "+memoryList2);
                        System.out.println("elapsed_times = "+elapsed_times);
//                        IO.writeArrayListToFile("/storage/sdcard0/memory_fsa/R_gcnc_memory2part"+participant_no+"_W"+W_win+".csv", memoryList2);
//                        IO.writeArrayListLongToFile("/storage/sdcard0/memory_fsa/R_gcnc_elapsedtimes_part"+participant_no+"_W"+W_win+".csv", elapsed_times);
                    }
                });
    }


    private class CreateDataBases extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            createDataBases();
            return ("ok");
        }

        @Override
        protected void onPostExecute(String result) {
            wakeLock.release();
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
            powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "MyWakelockTag");
            wakeLock.acquire();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void createDataBases() {
        Device device;
        DBDataSource datasource = new DBDataSource(this);

        // open datasource
        datasource.open();

        /**
         * TABLE DEVICES
         */
        if (datasource.getAllDevices().isEmpty()) {
            datasource.createDevice("Left Wrist");
            datasource.createDevice("Right Wrist");
            datasource.createDevice("Torso");
            datasource.createDevice("Right Thigh");
            datasource.createDevice("Left Ankle");
        }

        List<Device> deviceList = datasource.getAllDevices();
        for (int i = 0; i < deviceList.size(); i++) {
            System.out.println(deviceList.get(i).getId() + " " + deviceList.get(i).getLocation());
        }

        int[] participants = new int[] {0,1,2,3,4,5,6,7,9,10,11,12,13,14};
        for (int p=0; p<participants.length; p++) {
            int participant_no = participants[p]; //TODO for loop
            IO.populateMeasurementsTable(participant_no, datasource);
        }

        System.out.println("measurements");
        List<Measurement> measurementList = datasource.getAllMeasurements(10);
        for (int i = 0; i < measurementList.size(); i++) {
            Measurement measurement = measurementList.get(i);
            System.out.println(measurement.toString());
        }
        // close datasource
        datasource.close();
    }

    public Double logHeap() {
        Double allocated = new Double(Debug.getNativeHeapAllocatedSize()) / new Double((1048576));
        Double available = new Double(Debug.getNativeHeapSize()) / 1048576.0;
        Double free = new Double(Debug.getNativeHeapFreeSize()) / 1048576.0;
        Double used = allocated - free;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        Double allocatedMem = new Double(Runtime.getRuntime().totalMemory() / 1048576);
        Double maxMem = new Double(Runtime.getRuntime().maxMemory() / 1048576);
        Double freeMem = new Double(Runtime.getRuntime().freeMemory() / 1048576);

//        Log.d("tag", "debug. =================================");
//        Log.d("tag", "debug.heap native: allocated " + df.format(allocated) + "MB of " + df.format(available) + "MB (" + df.format(free) + "MB free)");
//        Log.d("tag", "debug.memory: allocated: " + df.format(allocatedMem) + "MB of " + df.format(maxMem)+ "MB (" + df.format(freeMem) +"MB free)");
//        Log.d("tag", "debug.memory: used: " + df.format(used));

        return (allocatedMem);
    }
}
