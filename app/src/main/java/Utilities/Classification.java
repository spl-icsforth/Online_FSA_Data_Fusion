package Utilities;

import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

public class Classification {

	public static double KNN(Instances data, int folds, int runs) {
		// perform cross-validation
		double[] accuracy_runs = new double[runs];
		for (int i = 0; i < runs; i++) {
			// randomize data
			int seed = i + 1;
			Random rand = new Random(seed);
			Instances randData = new Instances(data);
			randData.randomize(rand);
			if (randData.classAttribute().isNominal())
				randData.stratify(folds);

			Evaluation eval = null;

			ArrayList<Double> predLabels = new ArrayList<Double>();
			ArrayList<Double> actualLabels = new ArrayList<Double>();
			try {
				double[] acc = new double[folds];
				//			eval = new Evaluation(randData);
				for (int n = 0; n < folds; n++) {
					predLabels = new ArrayList<Double>();
					actualLabels = new ArrayList<Double>();

//					System.out.println("fold: " + n);
					Instances train = randData.trainCV(folds, n);
					Instances test = randData.testCV(folds, n);

					int k = (int) Math.sqrt(train.numAttributes());
//					System.out.println(k +"nearest neighbors");

					// build and evaluate classifier
					Classifier clsCopy = new IBk(k);
					clsCopy.buildClassifier(train);
					eval = new Evaluation(train);

					for (int t = 0; t < test.numInstances(); t++) {
						Instance inst = test.instance(t);
						double predL = clsCopy.classifyInstance(inst);
						predLabels.add(predL);

						double actualL = inst.classValue();
						actualLabels.add(actualL);
					}
//			        System.out.println("pred labels: "+predLabels);
//			        System.out.println("act labels: "+actualLabels);
					eval.evaluateModel(clsCopy, test);

					double accuracy = calculateAccuracy(predLabels, actualLabels);
//					System.out.println("accuracy: " + accuracy);

					acc[n] = accuracy;
				}

				double mean_accuracy = CollectionUtilities.mean(acc);


				// output evaluation
			/*	System.out.println();
				System.out.println("=== Setup run " + (i + 1) + " ===");
				System.out.println("Classifier: " + "kNN" + " ");
				System.out.println("Dataset: " + data.relationName());
				System.out.println("Folds: " + folds);
				System.out.println("Seed: " + seed);
				System.out.println("mean accuracy: " + mean_accuracy);
				System.out.println();
				System.out.println(eval.toSummaryString("=== " + folds + "-fold Cross-validation run " + (i + 1) + "===", false));
*/
				accuracy_runs[i] = mean_accuracy;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		double mean_accuracy_runs = CollectionUtilities.mean(accuracy_runs);

		return mean_accuracy_runs;
	}
	
	public static double calculateAccuracy(ArrayList<Double> predictions, ArrayList<Double> truelabels) {
		double correct = 0;
 
		for (int i = 0; i < predictions.size(); i++) {
			double predictedL = predictions.get(i);
			double trueL = truelabels.get(i);
			if (predictedL == trueL) {
				correct++;
			}
		}
 
		return 100 * correct / predictions.size();
	}
}
