package DominantStreams;

import java.util.ArrayList;

public class DominantStreams {

	private ArrayList<String> stream_clusters;
	private ArrayList<Integer> stream_indexes;
	private ArrayList<Integer> featureSet;
	private String[] featureNames;
	
	
	public DominantStreams(ArrayList<Integer> featureSet, String[] featureNames, int no_modalities) {
		super();
		this.featureSet = featureSet;
		this.featureNames = featureNames;
		
		this.stream_clusters = new ArrayList<String>();
		this.stream_indexes = new ArrayList<Integer>();
	}
	
	public ArrayList<String> getStream_clusters() {
		return stream_clusters;
	}

	public void setStream_clusters(ArrayList<String> stream_clusters) {
		this.stream_clusters = stream_clusters;
	}

	public ArrayList<Integer> getStream_indexes() {
		return stream_indexes;
	}

	public void setStream_indexes(ArrayList<Integer> stream_indexes) {
		this.stream_indexes = stream_indexes;
	}

	public void categorizeFeatures() {
		
		for (int i=0; i<featureSet.size(); i++) {
			// get feature name and strip last two characters (device id)
			int feature_index = featureSet.get(i);
			String featureName = featureNames[featureSet.get(i)];
//			System.out.println("featureName: "+featureName);

			
			if (!featureName.contains("_")){		// statistical domain
				// get modality -- last character of string
				featureName = featureName.substring(0, featureName.length()-2);
				int modality = Integer.parseInt(featureName.substring(featureName.length()-1, featureName.length()));
//				System.out.println("modality: "+modality);
				// check modality to categorize into statistical clusters
				switch (modality) {
					case 0: case 1: case 2:							// statistical acc
						stream_indexes.add(1);
						break;
					case 3: case 4: case 5:							// statistical gyro
						stream_indexes.add(2);
						break;
					case 6: case 7: case 8:							// statistical mag
						stream_indexes.add(3);
						break;
					default:
						System.out.println("invalid modality number");
						break;
				}
			}
			else {									// PC domain
				// check modality to categorize into PC clusters
				int modality1 = Integer.parseInt(featureName.substring(4, 6));
				int modality2 = Integer.parseInt(featureName.substring(7, 9));
//				System.out.println("modality1 : "+modality1 + "  modality2: "+modality2);
				switch (modality1) {
					case 0: case 1: case 2:							// acc
						switch (modality2) {
							case 0: case 1: case 2:					// intra acc-acc
								stream_indexes.add(4);
								break;
							case 3: case 4: case 5:					// inter acc-gyro
								stream_indexes.add(7);
								break;
							case 6: case 7: case 8:					// inter acc-mag
								stream_indexes.add(8);
								break;
							default:
								break;
						}
						break;
					case 3: case 4: case 5:							// gyro
						switch (modality2) {
							case 0: case 1: case 2:					// inter gyro-acc
								stream_indexes.add(7);
								break;
							case 3: case 4: case 5:					// intra gyro-gyro
								stream_indexes.add(5);
								break;
							case 6: case 7: case 8:					// inter gyro-mag
								stream_indexes.add(9);
								break;
							default:
								break;
						}
						break;
					case 6: case 7: case 8:							// mag
						switch (modality2) {
							case 0: case 1: case 2:					// inter mag-acc
								stream_indexes.add(8);
								break;
							case 3: case 4: case 5:					// inter mag-gyro
								stream_indexes.add(9);
								break;
							case 6: case 7: case 8:					// intra mag-mag
								stream_indexes.add(6);
								break;
							default:
								break;
						}
						break;
					default:
						System.out.println("invalid modality number");
						break;
				}
			}
		}
	}
	
	
}
