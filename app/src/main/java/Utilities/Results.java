package Utilities;

import java.util.ArrayList;

public class Results {

	private int participantNo;
	private String AlgNo;
	private int winsizeSec;
	private int featuresNo;
	private ArrayList<Integer> dominantFeatures;

	private long execTimeFS;
	private long execTimeAcq;

	private long execTimeSeg;
	private long execTimeFE;

	private double memoryFS;
	private double memoryAcq;
	private double memorySeg;
	private double memoryFE;

	private double batteryFS;
	private double batteryAcq;
	private double batterySeg;
	private double batteryFE;

	private double CA;
	private long execTimeCA;

	public Results(long elapsedTimeFull) {

	}

	private long elapsedTimeFull;


	public long getExecTimeAcq() {
		return execTimeAcq;
	}

	public void setExecTimeAcq(long execTimeAcq) {
		this.execTimeAcq = execTimeAcq;
	}

	public long getExecTimeSeg() {
		return execTimeSeg;
	}

	public void setExecTimeSeg(long execTimeSeg) {
		this.execTimeSeg = execTimeSeg;
	}

	public double getMemoryFS() {
		return memoryFS;
	}

	public void setMemoryFS(double memoryFS) {
		this.memoryFS = memoryFS;
	}

	public double getMemoryAcq() {
		return memoryAcq;
	}

	public void setMemoryAcq(double memoryAcq) {
		this.memoryAcq = memoryAcq;
	}

	public double getMemorySeg() {
		return memorySeg;
	}

	public void setMemorySeg(double memorySeg) {
		this.memorySeg = memorySeg;
	}

	public double getMemoryFE() {
		return memoryFE;
	}

	public void setMemoryFE(double memoryFE) {
		this.memoryFE = memoryFE;
	}

	public double getBatteryFS() {
		return batteryFS;
	}

	public void setBatteryFS(double batteryFS) {
		this.batteryFS = batteryFS;
	}

	public double getBatteryAcq() {
		return batteryAcq;
	}

	public void setBatteryAcq(double batteryAcq) {
		this.batteryAcq = batteryAcq;
	}

	public double getBatterySeg() {
		return batterySeg;
	}

	public void setBatterySeg(double batterySeg) {
		this.batterySeg = batterySeg;
	}

	public double getBatteryFE() {
		return batteryFE;
	}

	public void setBatteryFE(double batteryFE) {
		this.batteryFE = batteryFE;
	}

	public double getCA() {
		return CA;
	}

	public void setCA(double CA) {
		this.CA = CA;
	}

	public long getExecTimeCA() {
		return execTimeCA;
	}

	public void setExecTimeCA(long execTimeCA) {
		this.execTimeCA = execTimeCA;
	}

	public Results(int participantNo, String algNo, int winsizeSec, int featuresNo, ArrayList<Integer> dominantFeatures,
				   long execTimeAcq, long execTimeSeg, long execTimeFE, long execTimeFS,
				   double memoryAcq, double memorySeg, double memoryFE, double memoryFS,
				   double batteryAcq, double batterySeg, double batteryFE, double batteryFS,
				   long elapsedTimeFull, double CA, long execTimeCA) {
		this.participantNo = participantNo;
		AlgNo = algNo;
		this.winsizeSec = winsizeSec;
		this.featuresNo = featuresNo;
		this.dominantFeatures = dominantFeatures;
		this.execTimeFS = execTimeFS;
		this.execTimeAcq = execTimeAcq;
		this.execTimeSeg = execTimeSeg;
		this.execTimeFE = execTimeFE;
		this.memoryFS = memoryFS;
		this.memoryAcq = memoryAcq;
		this.memorySeg = memorySeg;
		this.memoryFE = memoryFE;
		this.batteryFS = batteryFS;
		this.batteryAcq = batteryAcq;
		this.batterySeg = batterySeg;
		this.batteryFE = batteryFE;
		this.CA = CA;
		this.execTimeCA = execTimeCA;
		this.elapsedTimeFull = elapsedTimeFull;
	}

	public Results(int participantNo, String algNo, int winsizeSec, int featuresNo, ArrayList<Integer> dominantFeatures,
				   long execTimeAcq, long execTimeSeg, long execTimeFE, long execTimeFS,
				   double memoryAcq, double memorySeg, double memoryFE, double memoryFS,
				   double batteryAcq, double batterySeg, double batteryFE, double batteryFS, long elapsedTimeFull) {

		this.execTimeSeg = execTimeSeg;
		this.participantNo = participantNo;
		AlgNo = algNo;
		this.winsizeSec = winsizeSec;
		this.featuresNo = featuresNo;
		this.dominantFeatures = dominantFeatures;
		this.execTimeFS = execTimeFS;
		this.execTimeAcq = execTimeAcq;
		this.execTimeFE = execTimeFE;

		this.memoryFS = memoryFS;
		this.memoryAcq = memoryAcq;
		this.memorySeg = memorySeg;
		this.memoryFE = memoryFE;
		this.batteryFS = batteryFS;
		this.batteryAcq = batteryAcq;
		this.batterySeg = batterySeg;
		this.batteryFE = batteryFE;
		this.elapsedTimeFull = elapsedTimeFull;
	}

	public Results(int participantNo, int winsizeSec){
		this.participantNo = participantNo;
		this.winsizeSec = winsizeSec;
		this.dominantFeatures = new ArrayList<Integer>();
	}

	public void setBattery(double battery) {
		this.batteryFS = battery;
	}

	public void setMemory(double memory) {
		this.memoryFS = memory;
	}

	public double getMemory() {
		return memoryFS;
	}

	public double getBattery() {
		return batteryFS;
	}

	public int getParticipantNo() {
		return participantNo;
	}

	public void setParticipantNo(int participantNo) {
		this.participantNo = participantNo;
	}

	public String getAlgNo() {
		return AlgNo;
	}

	public void setAlgNo(String algNo) {
		AlgNo = algNo;
	}

	public int getWinsizeSec() {
		return winsizeSec;
	}

	public void setWinsizeSec(int winsizeSec) {
		this.winsizeSec = winsizeSec;
	}

	public int getFeaturesNo() {
		return featuresNo;
	}

	public void setFeaturesNo(int featuresNo) {
		this.featuresNo = featuresNo;
	}

	public ArrayList<Integer> getDominantFeatures() {
		return dominantFeatures;
	}

	public void setDominantFeatures(ArrayList<Integer> dominantFeatures) {
		this.dominantFeatures = dominantFeatures;
	}

	public long getExecTimeFS() {
		return execTimeFS;
	}

	public void setExecTimeFS(long execTime) {
		this.execTimeFS = execTime;
	}
	
	public long getExecTimeFE() {
		return execTimeFE;
	}

	public void setExecTimeFE(long execTimeFE) {
		this.execTimeFE = execTimeFE;
	}

	public long getElapsedTimeFull() {
		return elapsedTimeFull;
	}

	public void setElapsedTimeFull(long elapsedTimeFull) {
		this.elapsedTimeFull = elapsedTimeFull;
	}

	@Override
	public String toString() {
		return "Results{" +
				"participantNo=" + participantNo +
				", AlgNo='" + AlgNo + '\'' +
				", winsizeSec=" + winsizeSec +
				", featuresNo=" + featuresNo +
				", dominantFeatures=" + dominantFeatures +
				", execTimeFS=" + execTimeFS +
				", execTimeAcq=" + execTimeAcq +
				", execTimeSeg=" + execTimeSeg +
				", execTimeFE=" + execTimeFE +
				", memoryFS=" + memoryFS +
				", memoryAcq=" + memoryAcq +
				", memorySeg=" + memorySeg +
				", memoryFE=" + memoryFE +
				", batteryFS=" + batteryFS +
				", batteryAcq=" + batteryAcq +
				", batterySeg=" + batterySeg +
				", batteryFE=" + batteryFE +
				", CA=" + CA +
				", execTimeCA=" + execTimeCA +
				", elapsedTimeFull=" + elapsedTimeFull +
				'}';
	}

}
