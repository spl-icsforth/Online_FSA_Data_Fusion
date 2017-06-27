package Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import GCNC.*;
//import GCNC.Preprocessing;
import Graph_NC_RE.Preprocessing2;

/**
 * Created by spl on 1/31/17.
 */
public class FSA {
    private ArrayList<Integer> reducedSet;
    private Results results;

    public FSA() {
    }

    public void runGCNC(int device_id, int participant_no, int windowSizeSec, HashMap<String, ArrayList<Double>> finalFeatureSet, String[] featureNames_Arr, ArrayList<Double> finalLabels,
                                  long elapsed_time_ACQ, long elapsed_time_SEG, long elapsed_time_FE) {
        /**
         * Initialize GCNC
         * PREPROCESSING
         */
        int nPatterns = finalLabels.size();
        String graph_path = "";
        graph_path = "/storage/sdcard0/";

        double theta = 0.5;
        long start_time_GCNC = System.nanoTime();
        theta = 0.5;
        Preprocessing prep2 = new Preprocessing(finalFeatureSet, featureNames_Arr, theta);
        prep2.initializeGraph();
        prep2.writeGraphToFile(prep2.getGraphWeights(), "/storage/sdcard0/features_graph"+device_id+".txt");

        ArrayList<Integer> gcnc2Clusters = prep2.louvainClustering(device_id);
        System.out.println("graphClusters: "+gcnc2Clusters);
        HashMap<Integer, Set<Integer>> GCNCclusters = prep2.getGraphClusters();


        /**
         * Start GCNC
         */
        ArrayList<Results> results4 = new ArrayList<Results>();

        double delta = 0.0;
        double om = 0;
        GCNC gcnc2 = new GCNC(finalFeatureSet, featureNames_Arr, nPatterns, prep2.getGraphWeights(), GCNCclusters, delta, om);
//		GCNC gcnc= new GCNC(finalFeatureSet, featureNames_Arr, nPatterns, graph, delta, om);
        gcnc2.core();

        long elapsed_time_GCNC = System.nanoTime() - start_time_GCNC;
        System.out.println("elapsed_time_GCNC: " + elapsed_time_GCNC);

        this.reducedSet = gcnc2.getReducedSet();
        System.out.println("GCNC reduced_set "+reducedSet);

        this.results = new Results(participant_no, "6", windowSizeSec, this.reducedSet.size(), this.reducedSet, elapsed_time_ACQ, elapsed_time_SEG, elapsed_time_FE, elapsed_time_GCNC, 0, 0, 0, 0, 0, 0, 0, 0, 0);// battACQ, battSEG, battFE, battNCRE);
    }

    public void runNCRE(int device_id, int participant_no, int windowSizeSec, HashMap<String, ArrayList<Double>> finalFeatureSet, String[] featureNames_Arr, ArrayList<Double> finalLabels,
                        long elapsed_time_ACQ, long elapsed_time_SEG, long elapsed_time_FE) {

           /**
             * Initialize NCRE
             * PREPROCESSING
             */
            double theta = 0.5;
            long start_time_NCRE = System.nanoTime();
            Preprocessing2 p_nc_re = new Preprocessing2(finalFeatureSet, featureNames_Arr, theta);
            p_nc_re.initializeGraph();
            ArrayList<Integer> groups = p_nc_re.iterativeDominantSetsExtraction();

            /**
             * Start NCRE
             */
            double delta = 0.2;
            double omega = 1;
            int no_groups = p_nc_re.getNo_groups();
            double[][] graphWeights = p_nc_re.getGraphWeights();

            Graph_NC_RE.Graph_NC_RE ncre = new Graph_NC_RE.Graph_NC_RE(delta, omega, no_groups, groups, graphWeights);
            ncre.core();

            long elapsed_time_NCRE = System.nanoTime() - start_time_NCRE;
            System.out.println("elapsed_time_NCRE: " + elapsed_time_NCRE);

            this.reducedSet = ncre.getReduced_set();
            System.out.println("NCRE reduced_set "+reducedSet);


            this.results = new Results(participant_no, "5", windowSizeSec, this.reducedSet.size(), this.reducedSet, elapsed_time_ACQ, elapsed_time_SEG, elapsed_time_FE, elapsed_time_NCRE, 0, 0, 0, 0, 0, 0, 0, 0, 0);// battACQ, battSEG, battFE, battNCRE);
    }

    public ArrayList<Integer> getReducedSet() {
        return reducedSet;
    }

    public void setReducedSet(ArrayList<Integer> reducedSet) {
        this.reducedSet = reducedSet;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }
}
