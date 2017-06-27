package DataBase;

/**
 * Created by karayan on 7/27/16.
 */
public class FeatureVector {
    private long id;
    private long segment_id;
    private int device_id;

    // statistical - 99 features
    private double mean_acc_x;
    private double mean_acc_y;
    private double mean_acc_z;
    private double mean_gyro_x;
    private double mean_gyro_y;
    private double mean_gyro_z;
    private double mean_mag_x;
    private double mean_mag_y;
    private double mean_mag_z;

    private double median_acc_x;
    private double median_acc_y;
    private double median_acc_z;
    private double median_gyro_x;
    private double median_gyro_y;
    private double median_gyro_z;
    private double median_mag_x;
    private double median_mag_y;
    private double median_mag_z;

    private double std_acc_x;
    private double std_acc_y;
    private double std_acc_z;
    private double std_gyro_x;
    private double std_gyro_y;
    private double std_gyro_z;
    private double std_mag_x;
    private double std_mag_y;
    private double std_mag_z;

    private double var_acc_x;
    private double var_acc_y;
    private double var_acc_z;
    private double var_gyro_x;
    private double var_gyro_y;
    private double var_gyro_z;
    private double var_mag_x;
    private double var_mag_y;
    private double var_mag_z;

    private double rms_acc_x;
    private double rms_acc_y;
    private double rms_acc_z;
    private double rms_gyro_x;
    private double rms_gyro_y;
    private double rms_gyro_z;
    private double rms_mag_x;
    private double rms_mag_y;
    private double rms_mag_z;

    private double skewness_acc_x;
    private double skewness_acc_y;
    private double skewness_acc_z;
    private double skewness_gyro_x;
    private double skewness_gyro_y;
    private double skewness_gyro_z;
    private double skewness_mag_x;
    private double skewness_mag_y;
    private double skewness_mag_z;

    private double kurtosis_acc_x;
    private double kurtosis_acc_y;
    private double kurtosis_acc_z;
    private double kurtosis_gyro_x;
    private double kurtosis_gyro_y;
    private double kurtosis_gyro_z;
    private double kurtosis_mag_x;
    private double kurtosis_mag_y;
    private double kurtosis_mag_z;

    private double iqr_acc_x;
    private double iqr_acc_y;
    private double iqr_acc_z;
    private double iqr_gyro_x;
    private double iqr_gyro_y;
    private double iqr_gyro_z;
    private double iqr_mag_x;
    private double iqr_mag_y;
    private double iqr_mag_z;

    private double zcr_acc_x;
    private double zcr_acc_y;
    private double zcr_acc_z;
    private double zcr_gyro_x;
    private double zcr_gyro_y;
    private double zcr_gyro_z;
    private double zcr_mag_x;
    private double zcr_mag_y;
    private double zcr_mag_z;

    private double mcr_acc_x;
    private double mcr_acc_y;
    private double mcr_acc_z;
    private double mcr_gyro_x;
    private double mcr_gyro_y;
    private double mcr_gyro_z;
    private double mcr_mag_x;
    private double mcr_mag_y;
    private double mcr_mag_z;

    private double spec_acc_x;
    private double spec_acc_y;
    private double spec_acc_z;
    private double spec_gyro_x;
    private double spec_gyro_y;
    private double spec_gyro_z;
    private double spec_mag_x;
    private double spec_mag_y;
    private double spec_mag_z;

    // Pairwise correlations - 36 features

    private double PC_accx_accy;
    private double PC_accx_accz;
    private double PC_accx_gyrox;
    private double PC_accx_gyroy;
    private double PC_accx_gyroz;
    private double PC_accx_magx;
    private double PC_accx_magy;
    private double PC_accx_magz;

    private double PC_accy_accz;
    private double PC_accy_gyrox;
    private double PC_accy_gyroy;
    private double PC_accy_gyroz;
    private double PC_accy_magx;
    private double PC_accy_magy;
    private double PC_accy_magz;

    private double PC_accz_gyrox;
    private double PC_accz_gyroy;
    private double PC_accz_gyroz;
    private double PC_accz_magx;
    private double PC_accz_magy;
    private double PC_accz_magz;

    private double PC_gyrox_gyroy;
    private double PC_gyrox_gyroz;
    private double PC_gyrox_magx;
    private double PC_gyrox_magy;
    private double PC_gyrox_magz;

    private double PC_gyroy_gyroz;
    private double PC_gyroy_magx;
    private double PC_gyroy_magy;
    private double PC_gyroy_magz;

    private double PC_gyroz_magx;
    private double PC_gyroz_magy;
    private double PC_gyroz_magz;

    private double PC_magx_magy;
    private double PC_magx_magz;

    private double PC_magy_magz;

    private int label;

    /**
     * Getters - Setters - Constructors
     */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSegment_id() {
        return segment_id;
    }

    public void setSegment_id(long segment_id) {
        this.segment_id = segment_id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public double getMean_acc_x() {
        return mean_acc_x;
    }

    public void setMean_acc_x(double mean_acc_x) {
        this.mean_acc_x = mean_acc_x;
    }

    public double getMean_acc_y() {
        return mean_acc_y;
    }

    public void setMean_acc_y(double mean_acc_y) {
        this.mean_acc_y = mean_acc_y;
    }

    public double getMean_acc_z() {
        return mean_acc_z;
    }

    public void setMean_acc_z(double mean_acc_z) {
        this.mean_acc_z = mean_acc_z;
    }

    public double getMean_gyro_x() {
        return mean_gyro_x;
    }

    public void setMean_gyro_x(double mean_gyro_x) {
        this.mean_gyro_x = mean_gyro_x;
    }

    public double getMean_gyro_y() {
        return mean_gyro_y;
    }

    public void setMean_gyro_y(double mean_gyro_y) {
        this.mean_gyro_y = mean_gyro_y;
    }

    public double getMean_gyro_z() {
        return mean_gyro_z;
    }

    public void setMean_gyro_z(double mean_gyro_z) {
        this.mean_gyro_z = mean_gyro_z;
    }

    public double getMean_mag_x() {
        return mean_mag_x;
    }

    public void setMean_mag_x(double mean_mag_x) {
        this.mean_mag_x = mean_mag_x;
    }

    public double getMean_mag_y() {
        return mean_mag_y;
    }

    public void setMean_mag_y(double mean_mag_y) {
        this.mean_mag_y = mean_mag_y;
    }

    public double getMean_mag_z() {
        return mean_mag_z;
    }

    public void setMean_mag_z(double mean_mag_z) {
        this.mean_mag_z = mean_mag_z;
    }

    public double getMedian_acc_x() {
        return median_acc_x;
    }

    public void setMedian_acc_x(double median_acc_x) {
        this.median_acc_x = median_acc_x;
    }

    public double getMedian_acc_y() {
        return median_acc_y;
    }

    public void setMedian_acc_y(double median_acc_y) {
        this.median_acc_y = median_acc_y;
    }

    public double getMedian_acc_z() {
        return median_acc_z;
    }

    public void setMedian_acc_z(double median_acc_z) {
        this.median_acc_z = median_acc_z;
    }

    public double getMedian_gyro_x() {
        return median_gyro_x;
    }

    public void setMedian_gyro_x(double median_gyro_x) {
        this.median_gyro_x = median_gyro_x;
    }

    public double getMedian_gyro_y() {
        return median_gyro_y;
    }

    public void setMedian_gyro_y(double median_gyro_y) {
        this.median_gyro_y = median_gyro_y;
    }

    public double getMedian_gyro_z() {
        return median_gyro_z;
    }

    public void setMedian_gyro_z(double median_gyro_z) {
        this.median_gyro_z = median_gyro_z;
    }

    public double getMedian_mag_x() {
        return median_mag_x;
    }

    public void setMedian_mag_x(double median_mag_x) {
        this.median_mag_x = median_mag_x;
    }

    public double getMedian_mag_y() {
        return median_mag_y;
    }

    public void setMedian_mag_y(double median_mag_y) {
        this.median_mag_y = median_mag_y;
    }

    public double getMedian_mag_z() {
        return median_mag_z;
    }

    public void setMedian_mag_z(double median_mag_z) {
        this.median_mag_z = median_mag_z;
    }

    public double getStd_acc_x() {
        return std_acc_x;
    }

    public void setStd_acc_x(double std_acc_x) {
        this.std_acc_x = std_acc_x;
    }

    public double getStd_acc_y() {
        return std_acc_y;
    }

    public void setStd_acc_y(double std_acc_y) {
        this.std_acc_y = std_acc_y;
    }

    public double getStd_acc_z() {
        return std_acc_z;
    }

    public void setStd_acc_z(double std_acc_z) {
        this.std_acc_z = std_acc_z;
    }

    public double getStd_gyro_x() {
        return std_gyro_x;
    }

    public void setStd_gyro_x(double std_gyro_x) {
        this.std_gyro_x = std_gyro_x;
    }

    public double getStd_gyro_y() {
        return std_gyro_y;
    }

    public void setStd_gyro_y(double std_gyro_y) {
        this.std_gyro_y = std_gyro_y;
    }

    public double getStd_gyro_z() {
        return std_gyro_z;
    }

    public void setStd_gyro_z(double std_gyro_z) {
        this.std_gyro_z = std_gyro_z;
    }

    public double getStd_mag_x() {
        return std_mag_x;
    }

    public void setStd_mag_x(double std_mag_x) {
        this.std_mag_x = std_mag_x;
    }

    public double getStd_mag_y() {
        return std_mag_y;
    }

    public void setStd_mag_y(double std_mag_y) {
        this.std_mag_y = std_mag_y;
    }

    public double getStd_mag_z() {
        return std_mag_z;
    }

    public void setStd_mag_z(double std_mag_z) {
        this.std_mag_z = std_mag_z;
    }

    public double getVar_acc_x() {
        return var_acc_x;
    }

    public void setVar_acc_x(double var_acc_x) {
        this.var_acc_x = var_acc_x;
    }

    public double getVar_acc_y() {
        return var_acc_y;
    }

    public void setVar_acc_y(double var_acc_y) {
        this.var_acc_y = var_acc_y;
    }

    public double getVar_acc_z() {
        return var_acc_z;
    }

    public void setVar_acc_z(double var_acc_z) {
        this.var_acc_z = var_acc_z;
    }

    public double getVar_gyro_x() {
        return var_gyro_x;
    }

    public void setVar_gyro_x(double var_gyro_x) {
        this.var_gyro_x = var_gyro_x;
    }

    public double getVar_gyro_y() {
        return var_gyro_y;
    }

    public void setVar_gyro_y(double var_gyro_y) {
        this.var_gyro_y = var_gyro_y;
    }

    public double getVar_gyro_z() {
        return var_gyro_z;
    }

    public void setVar_gyro_z(double var_gyro_z) {
        this.var_gyro_z = var_gyro_z;
    }

    public double getVar_mag_x() {
        return var_mag_x;
    }

    public void setVar_mag_x(double var_mag_x) {
        this.var_mag_x = var_mag_x;
    }

    public double getVar_mag_y() {
        return var_mag_y;
    }

    public void setVar_mag_y(double var_mag_y) {
        this.var_mag_y = var_mag_y;
    }

    public double getVar_mag_z() {
        return var_mag_z;
    }

    public void setVar_mag_z(double var_mag_z) {
        this.var_mag_z = var_mag_z;
    }

    public double getRms_acc_x() {
        return rms_acc_x;
    }

    public void setRms_acc_x(double rms_acc_x) {
        this.rms_acc_x = rms_acc_x;
    }

    public double getRms_acc_y() {
        return rms_acc_y;
    }

    public void setRms_acc_y(double rms_acc_y) {
        this.rms_acc_y = rms_acc_y;
    }

    public double getRms_acc_z() {
        return rms_acc_z;
    }

    public void setRms_acc_z(double rms_acc_z) {
        this.rms_acc_z = rms_acc_z;
    }

    public double getRms_gyro_x() {
        return rms_gyro_x;
    }

    public void setRms_gyro_x(double rms_gyro_x) {
        this.rms_gyro_x = rms_gyro_x;
    }

    public double getRms_gyro_y() {
        return rms_gyro_y;
    }

    public void setRms_gyro_y(double rms_gyro_y) {
        this.rms_gyro_y = rms_gyro_y;
    }

    public double getRms_gyro_z() {
        return rms_gyro_z;
    }

    public void setRms_gyro_z(double rms_gyro_z) {
        this.rms_gyro_z = rms_gyro_z;
    }

    public double getRms_mag_x() {
        return rms_mag_x;
    }

    public void setRms_mag_x(double rms_mag_x) {
        this.rms_mag_x = rms_mag_x;
    }

    public double getRms_mag_y() {
        return rms_mag_y;
    }

    public void setRms_mag_y(double rms_mag_y) {
        this.rms_mag_y = rms_mag_y;
    }

    public double getRms_mag_z() {
        return rms_mag_z;
    }

    public void setRms_mag_z(double rms_mag_z) {
        this.rms_mag_z = rms_mag_z;
    }

    public double getSkewness_acc_x() {
        return skewness_acc_x;
    }

    public void setSkewness_acc_x(double skewness_acc_x) {
        this.skewness_acc_x = skewness_acc_x;
    }

    public double getSkewness_acc_y() {
        return skewness_acc_y;
    }

    public void setSkewness_acc_y(double skewness_acc_y) {
        this.skewness_acc_y = skewness_acc_y;
    }

    public double getSkewness_acc_z() {
        return skewness_acc_z;
    }

    public void setSkewness_acc_z(double skewness_acc_z) {
        this.skewness_acc_z = skewness_acc_z;
    }

    public double getSkewness_gyro_x() {
        return skewness_gyro_x;
    }

    public void setSkewness_gyro_x(double skewness_gyro_x) {
        this.skewness_gyro_x = skewness_gyro_x;
    }

    public double getSkewness_gyro_y() {
        return skewness_gyro_y;
    }

    public void setSkewness_gyro_y(double skewness_gyro_y) {
        this.skewness_gyro_y = skewness_gyro_y;
    }

    public double getSkewness_gyro_z() {
        return skewness_gyro_z;
    }

    public void setSkewness_gyro_z(double skewness_gyro_z) {
        this.skewness_gyro_z = skewness_gyro_z;
    }

    public double getSkewness_mag_x() {
        return skewness_mag_x;
    }

    public void setSkewness_mag_x(double skewness_mag_x) {
        this.skewness_mag_x = skewness_mag_x;
    }

    public double getSkewness_mag_y() {
        return skewness_mag_y;
    }

    public void setSkewness_mag_y(double skewness_mag_y) {
        this.skewness_mag_y = skewness_mag_y;
    }

    public double getSkewness_mag_z() {
        return skewness_mag_z;
    }

    public void setSkewness_mag_z(double skewness_mag_z) {
        this.skewness_mag_z = skewness_mag_z;
    }

    public double getKurtosis_acc_x() {
        return kurtosis_acc_x;
    }

    public void setKurtosis_acc_x(double kurtosis_acc_x) {
        this.kurtosis_acc_x = kurtosis_acc_x;
    }

    public double getKurtosis_acc_y() {
        return kurtosis_acc_y;
    }

    public void setKurtosis_acc_y(double kurtosis_acc_y) {
        this.kurtosis_acc_y = kurtosis_acc_y;
    }

    public double getKurtosis_acc_z() {
        return kurtosis_acc_z;
    }

    public void setKurtosis_acc_z(double kurtosis_acc_z) {
        this.kurtosis_acc_z = kurtosis_acc_z;
    }

    public double getKurtosis_gyro_x() {
        return kurtosis_gyro_x;
    }

    public void setKurtosis_gyro_x(double kurtosis_gyro_x) {
        this.kurtosis_gyro_x = kurtosis_gyro_x;
    }

    public double getKurtosis_gyro_y() {
        return kurtosis_gyro_y;
    }

    public void setKurtosis_gyro_y(double kurtosis_gyro_y) {
        this.kurtosis_gyro_y = kurtosis_gyro_y;
    }

    public double getKurtosis_gyro_z() {
        return kurtosis_gyro_z;
    }

    public void setKurtosis_gyro_z(double kurtosis_gyro_z) {
        this.kurtosis_gyro_z = kurtosis_gyro_z;
    }

    public double getKurtosis_mag_x() {
        return kurtosis_mag_x;
    }

    public void setKurtosis_mag_x(double kurtosis_mag_x) {
        this.kurtosis_mag_x = kurtosis_mag_x;
    }

    public double getKurtosis_mag_y() {
        return kurtosis_mag_y;
    }

    public void setKurtosis_mag_y(double kurtosis_mag_y) {
        this.kurtosis_mag_y = kurtosis_mag_y;
    }

    public double getKurtosis_mag_z() {
        return kurtosis_mag_z;
    }

    public void setKurtosis_mag_z(double kurtosis_mag_z) {
        this.kurtosis_mag_z = kurtosis_mag_z;
    }

    public double getIqr_acc_x() {
        return iqr_acc_x;
    }

    public void setIqr_acc_x(double iqr_acc_x) {
        this.iqr_acc_x = iqr_acc_x;
    }

    public double getIqr_acc_y() {
        return iqr_acc_y;
    }

    public void setIqr_acc_y(double iqr_acc_y) {
        this.iqr_acc_y = iqr_acc_y;
    }

    public double getIqr_acc_z() {
        return iqr_acc_z;
    }

    public void setIqr_acc_z(double iqr_acc_z) {
        this.iqr_acc_z = iqr_acc_z;
    }

    public double getIqr_gyro_x() {
        return iqr_gyro_x;
    }

    public void setIqr_gyro_x(double iqr_gyro_x) {
        this.iqr_gyro_x = iqr_gyro_x;
    }

    public double getIqr_gyro_y() {
        return iqr_gyro_y;
    }

    public void setIqr_gyro_y(double iqr_gyro_y) {
        this.iqr_gyro_y = iqr_gyro_y;
    }

    public double getIqr_gyro_z() {
        return iqr_gyro_z;
    }

    public void setIqr_gyro_z(double iqr_gyro_z) {
        this.iqr_gyro_z = iqr_gyro_z;
    }

    public double getIqr_mag_x() {
        return iqr_mag_x;
    }

    public void setIqr_mag_x(double iqr_mag_x) {
        this.iqr_mag_x = iqr_mag_x;
    }

    public double getIqr_mag_y() {
        return iqr_mag_y;
    }

    public void setIqr_mag_y(double iqr_mag_y) {
        this.iqr_mag_y = iqr_mag_y;
    }

    public double getIqr_mag_z() {
        return iqr_mag_z;
    }

    public void setIqr_mag_z(double iqr_mag_z) {
        this.iqr_mag_z = iqr_mag_z;
    }

    public double getZcr_acc_x() {
        return zcr_acc_x;
    }

    public void setZcr_acc_x(double zcr_acc_x) {
        this.zcr_acc_x = zcr_acc_x;
    }

    public double getZcr_acc_y() {
        return zcr_acc_y;
    }

    public void setZcr_acc_y(double zcr_acc_y) {
        this.zcr_acc_y = zcr_acc_y;
    }

    public double getZcr_acc_z() {
        return zcr_acc_z;
    }

    public void setZcr_acc_z(double zcr_acc_z) {
        this.zcr_acc_z = zcr_acc_z;
    }

    public double getZcr_gyro_x() {
        return zcr_gyro_x;
    }

    public void setZcr_gyro_x(double zcr_gyro_x) {
        this.zcr_gyro_x = zcr_gyro_x;
    }

    public double getZcr_gyro_y() {
        return zcr_gyro_y;
    }

    public void setZcr_gyro_y(double zcr_gyro_y) {
        this.zcr_gyro_y = zcr_gyro_y;
    }

    public double getZcr_gyro_z() {
        return zcr_gyro_z;
    }

    public void setZcr_gyro_z(double zcr_gyro_z) {
        this.zcr_gyro_z = zcr_gyro_z;
    }

    public double getZcr_mag_x() {
        return zcr_mag_x;
    }

    public void setZcr_mag_x(double zcr_mag_x) {
        this.zcr_mag_x = zcr_mag_x;
    }

    public double getZcr_mag_y() {
        return zcr_mag_y;
    }

    public void setZcr_mag_y(double zcr_mag_y) {
        this.zcr_mag_y = zcr_mag_y;
    }

    public double getZcr_mag_z() {
        return zcr_mag_z;
    }

    public void setZcr_mag_z(double zcr_mag_z) {
        this.zcr_mag_z = zcr_mag_z;
    }

    public double getMcr_acc_x() {
        return mcr_acc_x;
    }

    public void setMcr_acc_x(double mcr_acc_x) {
        this.mcr_acc_x = mcr_acc_x;
    }

    public double getMcr_acc_y() {
        return mcr_acc_y;
    }

    public void setMcr_acc_y(double mcr_acc_y) {
        this.mcr_acc_y = mcr_acc_y;
    }

    public double getMcr_acc_z() {
        return mcr_acc_z;
    }

    public void setMcr_acc_z(double mcr_acc_z) {
        this.mcr_acc_z = mcr_acc_z;
    }

    public double getMcr_gyro_x() {
        return mcr_gyro_x;
    }

    public void setMcr_gyro_x(double mcr_gyro_x) {
        this.mcr_gyro_x = mcr_gyro_x;
    }

    public double getMcr_gyro_y() {
        return mcr_gyro_y;
    }

    public void setMcr_gyro_y(double mcr_gyro_y) {
        this.mcr_gyro_y = mcr_gyro_y;
    }

    public double getMcr_gyro_z() {
        return mcr_gyro_z;
    }

    public void setMcr_gyro_z(double mcr_gyro_z) {
        this.mcr_gyro_z = mcr_gyro_z;
    }

    public double getMcr_mag_x() {
        return mcr_mag_x;
    }

    public void setMcr_mag_x(double mcr_mag_x) {
        this.mcr_mag_x = mcr_mag_x;
    }

    public double getMcr_mag_y() {
        return mcr_mag_y;
    }

    public void setMcr_mag_y(double mcr_mag_y) {
        this.mcr_mag_y = mcr_mag_y;
    }

    public double getMcr_mag_z() {
        return mcr_mag_z;
    }

    public void setMcr_mag_z(double mcr_mag_z) {
        this.mcr_mag_z = mcr_mag_z;
    }

    public double getSpec_acc_x() {
        return spec_acc_x;
    }

    public void setSpec_acc_x(double spec_acc_x) {
        this.spec_acc_x = spec_acc_x;
    }

    public double getSpec_acc_y() {
        return spec_acc_y;
    }

    public void setSpec_acc_y(double spec_acc_y) {
        this.spec_acc_y = spec_acc_y;
    }

    public double getSpec_acc_z() {
        return spec_acc_z;
    }

    public void setSpec_acc_z(double spec_acc_z) {
        this.spec_acc_z = spec_acc_z;
    }

    public double getSpec_gyro_x() {
        return spec_gyro_x;
    }

    public void setSpec_gyro_x(double spec_gyro_x) {
        this.spec_gyro_x = spec_gyro_x;
    }

    public double getSpec_gyro_y() {
        return spec_gyro_y;
    }

    public void setSpec_gyro_y(double spec_gyro_y) {
        this.spec_gyro_y = spec_gyro_y;
    }

    public double getSpec_gyro_z() {
        return spec_gyro_z;
    }

    public void setSpec_gyro_z(double spec_gyro_z) {
        this.spec_gyro_z = spec_gyro_z;
    }

    public double getSpec_mag_x() {
        return spec_mag_x;
    }

    public void setSpec_mag_x(double spec_mag_x) {
        this.spec_mag_x = spec_mag_x;
    }

    public double getSpec_mag_y() {
        return spec_mag_y;
    }

    public void setSpec_mag_y(double spec_mag_y) {
        this.spec_mag_y = spec_mag_y;
    }

    public double getSpec_mag_z() {
        return spec_mag_z;
    }

    public void setSpec_mag_z(double spec_mag_z) {
        this.spec_mag_z = spec_mag_z;
    }

    public double getPC_accx_accy() {
        return PC_accx_accy;
    }

    public void setPC_accx_accy(double PC_accx_accy) {
        this.PC_accx_accy = PC_accx_accy;
    }

    public double getPC_accx_accz() {
        return PC_accx_accz;
    }

    public void setPC_accx_accz(double PC_accx_accz) {
        this.PC_accx_accz = PC_accx_accz;
    }

    public double getPC_accx_gyrox() {
        return PC_accx_gyrox;
    }

    public void setPC_accx_gyrox(double PC_accx_gyrox) {
        this.PC_accx_gyrox = PC_accx_gyrox;
    }

    public double getPC_accx_gyroy() {
        return PC_accx_gyroy;
    }

    public void setPC_accx_gyroy(double PC_accx_gyroy) {
        this.PC_accx_gyroy = PC_accx_gyroy;
    }

    public double getPC_accx_gyroz() {
        return PC_accx_gyroz;
    }

    public void setPC_accx_gyroz(double PC_accx_gyroz) {
        this.PC_accx_gyroz = PC_accx_gyroz;
    }

    public double getPC_accx_magx() {
        return PC_accx_magx;
    }

    public void setPC_accx_magx(double PC_accx_magx) {
        this.PC_accx_magx = PC_accx_magx;
    }

    public double getPC_accx_magy() {
        return PC_accx_magy;
    }

    public void setPC_accx_magy(double PC_accx_magy) {
        this.PC_accx_magy = PC_accx_magy;
    }

    public double getPC_accx_magz() {
        return PC_accx_magz;
    }

    public void setPC_accx_magz(double PC_accx_magz) {
        this.PC_accx_magz = PC_accx_magz;
    }

    public double getPC_accy_accz() {
        return PC_accy_accz;
    }

    public void setPC_accy_accz(double PC_accy_accz) {
        this.PC_accy_accz = PC_accy_accz;
    }

    public double getPC_accy_gyrox() {
        return PC_accy_gyrox;
    }

    public void setPC_accy_gyrox(double PC_accy_gyrox) {
        this.PC_accy_gyrox = PC_accy_gyrox;
    }

    public double getPC_accy_gyroy() {
        return PC_accy_gyroy;
    }

    public void setPC_accy_gyroy(double PC_accy_gyroy) {
        this.PC_accy_gyroy = PC_accy_gyroy;
    }

    public double getPC_accy_gyroz() {
        return PC_accy_gyroz;
    }

    public void setPC_accy_gyroz(double PC_accy_gyroz) {
        this.PC_accy_gyroz = PC_accy_gyroz;
    }

    public double getPC_accy_magx() {
        return PC_accy_magx;
    }

    public void setPC_accy_magx(double PC_accy_magx) {
        this.PC_accy_magx = PC_accy_magx;
    }

    public double getPC_accy_magy() {
        return PC_accy_magy;
    }

    public void setPC_accy_magy(double PC_accy_magy) {
        this.PC_accy_magy = PC_accy_magy;
    }

    public double getPC_accy_magz() {
        return PC_accy_magz;
    }

    public void setPC_accy_magz(double PC_accy_magz) {
        this.PC_accy_magz = PC_accy_magz;
    }

    public double getPC_accz_gyrox() {
        return PC_accz_gyrox;
    }

    public void setPC_accz_gyrox(double PC_accz_gyrox) {
        this.PC_accz_gyrox = PC_accz_gyrox;
    }

    public double getPC_accz_gyroy() {
        return PC_accz_gyroy;
    }

    public void setPC_accz_gyroy(double PC_accz_gyroy) {
        this.PC_accz_gyroy = PC_accz_gyroy;
    }

    public double getPC_accz_gyroz() {
        return PC_accz_gyroz;
    }

    public void setPC_accz_gyroz(double PC_accz_gyroz) {
        this.PC_accz_gyroz = PC_accz_gyroz;
    }

    public double getPC_accz_magx() {
        return PC_accz_magx;
    }

    public void setPC_accz_magx(double PC_accz_magx) {
        this.PC_accz_magx = PC_accz_magx;
    }

    public double getPC_accz_magy() {
        return PC_accz_magy;
    }

    public void setPC_accz_magy(double PC_accz_magy) {
        this.PC_accz_magy = PC_accz_magy;
    }

    public double getPC_accz_magz() {
        return PC_accz_magz;
    }

    public void setPC_accz_magz(double PC_accz_magz) {
        this.PC_accz_magz = PC_accz_magz;
    }

    public double getPC_gyrox_gyroy() {
        return PC_gyrox_gyroy;
    }

    public void setPC_gyrox_gyroy(double PC_gyrox_gyroy) {
        this.PC_gyrox_gyroy = PC_gyrox_gyroy;
    }

    public double getPC_gyrox_gyroz() {
        return PC_gyrox_gyroz;
    }

    public void setPC_gyrox_gyroz(double PC_gyrox_gyroz) {
        this.PC_gyrox_gyroz = PC_gyrox_gyroz;
    }

    public double getPC_gyrox_magx() {
        return PC_gyrox_magx;
    }

    public void setPC_gyrox_magx(double PC_gyrox_magx) {
        this.PC_gyrox_magx = PC_gyrox_magx;
    }

    public double getPC_gyrox_magy() {
        return PC_gyrox_magy;
    }

    public void setPC_gyrox_magy(double PC_gyrox_magy) {
        this.PC_gyrox_magy = PC_gyrox_magy;
    }

    public double getPC_gyrox_magz() {
        return PC_gyrox_magz;
    }

    public void setPC_gyrox_magz(double PC_gyrox_magz) {
        this.PC_gyrox_magz = PC_gyrox_magz;
    }

    public double getPC_gyroy_gyroz() {
        return PC_gyroy_gyroz;
    }

    public void setPC_gyroy_gyroz(double PC_gyroy_gyroz) {
        this.PC_gyroy_gyroz = PC_gyroy_gyroz;
    }

    public double getPC_gyroy_magx() {
        return PC_gyroy_magx;
    }

    public void setPC_gyroy_magx(double PC_gyroy_magx) {
        this.PC_gyroy_magx = PC_gyroy_magx;
    }

    public double getPC_gyroy_magy() {
        return PC_gyroy_magy;
    }

    public void setPC_gyroy_magy(double PC_gyroy_magy) {
        this.PC_gyroy_magy = PC_gyroy_magy;
    }

    public double getPC_gyroy_magz() {
        return PC_gyroy_magz;
    }

    public void setPC_gyroy_magz(double PC_gyroy_magz) {
        this.PC_gyroy_magz = PC_gyroy_magz;
    }

    public double getPC_gyroz_magx() {
        return PC_gyroz_magx;
    }

    public void setPC_gyroz_magx(double PC_gyroz_magx) {
        this.PC_gyroz_magx = PC_gyroz_magx;
    }

    public double getPC_gyroz_magy() {
        return PC_gyroz_magy;
    }

    public void setPC_gyroz_magy(double PC_gyroz_magy) {
        this.PC_gyroz_magy = PC_gyroz_magy;
    }

    public double getPC_gyroz_magz() {
        return PC_gyroz_magz;
    }

    public void setPC_gyroz_magz(double PC_gyroz_magz) {
        this.PC_gyroz_magz = PC_gyroz_magz;
    }

    public double getPC_magx_magy() {
        return PC_magx_magy;
    }

    public void setPC_magx_magy(double PC_magx_magy) {
        this.PC_magx_magy = PC_magx_magy;
    }

    public double getPC_magx_magz() {
        return PC_magx_magz;
    }

    public void setPC_magx_magz(double PC_magx_magz) {
        this.PC_magx_magz = PC_magx_magz;
    }

    public double getPC_magy_magz() {
        return PC_magy_magz;
    }

    public void setPC_magy_magz(double PC_magy_magz) {
        this.PC_magy_magz = PC_magy_magz;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "FeatureVector{" +
                "id=" + id +
                ", segment_id=" + segment_id +
                ", device_id=" + device_id +
                ", mean_acc_x=" + mean_acc_x +
                ", mean_acc_y=" + mean_acc_y +
                ", mean_acc_z=" + mean_acc_z +
                ", mean_gyro_x=" + mean_gyro_x +
                ", mean_gyro_y=" + mean_gyro_y +
                ", mean_gyro_z=" + mean_gyro_z +
                ", mean_mag_x=" + mean_mag_x +
                ", mean_mag_y=" + mean_mag_y +
                ", mean_mag_z=" + mean_mag_z +
                ", median_acc_x=" + median_acc_x +
                ", median_acc_y=" + median_acc_y +
                ", median_acc_z=" + median_acc_z +
                ", median_gyro_x=" + median_gyro_x +
                ", median_gyro_y=" + median_gyro_y +
                ", median_gyro_z=" + median_gyro_z +
                ", median_mag_x=" + median_mag_x +
                ", median_mag_y=" + median_mag_y +
                ", median_mag_z=" + median_mag_z +
                ", std_acc_x=" + std_acc_x +
                ", std_acc_y=" + std_acc_y +
                ", std_acc_z=" + std_acc_z +
                ", std_gyro_x=" + std_gyro_x +
                ", std_gyro_y=" + std_gyro_y +
                ", std_gyro_z=" + std_gyro_z +
                ", std_mag_x=" + std_mag_x +
                ", std_mag_y=" + std_mag_y +
                ", std_mag_z=" + std_mag_z +
                ", var_acc_x=" + var_acc_x +
                ", var_acc_y=" + var_acc_y +
                ", var_acc_z=" + var_acc_z +
                ", var_gyro_x=" + var_gyro_x +
                ", var_gyro_y=" + var_gyro_y +
                ", var_gyro_z=" + var_gyro_z +
                ", var_mag_x=" + var_mag_x +
                ", var_mag_y=" + var_mag_y +
                ", var_mag_z=" + var_mag_z +
                ", rms_acc_x=" + rms_acc_x +
                ", rms_acc_y=" + rms_acc_y +
                ", rms_acc_z=" + rms_acc_z +
                ", rms_gyro_x=" + rms_gyro_x +
                ", rms_gyro_y=" + rms_gyro_y +
                ", rms_gyro_z=" + rms_gyro_z +
                ", rms_mag_x=" + rms_mag_x +
                ", rms_mag_y=" + rms_mag_y +
                ", rms_mag_z=" + rms_mag_z +
                ", skewness_acc_x=" + skewness_acc_x +
                ", skewness_acc_y=" + skewness_acc_y +
                ", skewness_acc_z=" + skewness_acc_z +
                ", skewness_gyro_x=" + skewness_gyro_x +
                ", skewness_gyro_y=" + skewness_gyro_y +
                ", skewness_gyro_z=" + skewness_gyro_z +
                ", skewness_mag_x=" + skewness_mag_x +
                ", skewness_mag_y=" + skewness_mag_y +
                ", skewness_mag_z=" + skewness_mag_z +
                ", kurtosis_acc_x=" + kurtosis_acc_x +
                ", kurtosis_acc_y=" + kurtosis_acc_y +
                ", kurtosis_acc_z=" + kurtosis_acc_z +
                ", kurtosis_gyro_x=" + kurtosis_gyro_x +
                ", kurtosis_gyro_y=" + kurtosis_gyro_y +
                ", kurtosis_gyro_z=" + kurtosis_gyro_z +
                ", kurtosis_mag_x=" + kurtosis_mag_x +
                ", kurtosis_mag_y=" + kurtosis_mag_y +
                ", kurtosis_mag_z=" + kurtosis_mag_z +
                ", iqr_acc_x=" + iqr_acc_x +
                ", iqr_acc_y=" + iqr_acc_y +
                ", iqr_acc_z=" + iqr_acc_z +
                ", iqr_gyro_x=" + iqr_gyro_x +
                ", iqr_gyro_y=" + iqr_gyro_y +
                ", iqr_gyro_z=" + iqr_gyro_z +
                ", iqr_mag_x=" + iqr_mag_x +
                ", iqr_mag_y=" + iqr_mag_y +
                ", iqr_mag_z=" + iqr_mag_z +
                ", zcr_acc_x=" + zcr_acc_x +
                ", zcr_acc_y=" + zcr_acc_y +
                ", zcr_acc_z=" + zcr_acc_z +
                ", zcr_gyro_x=" + zcr_gyro_x +
                ", zcr_gyro_y=" + zcr_gyro_y +
                ", zcr_gyro_z=" + zcr_gyro_z +
                ", zcr_mag_x=" + zcr_mag_x +
                ", zcr_mag_y=" + zcr_mag_y +
                ", zcr_mag_z=" + zcr_mag_z +
                ", mcr_acc_x=" + mcr_acc_x +
                ", mcr_acc_y=" + mcr_acc_y +
                ", mcr_acc_z=" + mcr_acc_z +
                ", mcr_gyro_x=" + mcr_gyro_x +
                ", mcr_gyro_y=" + mcr_gyro_y +
                ", mcr_gyro_z=" + mcr_gyro_z +
                ", mcr_mag_x=" + mcr_mag_x +
                ", mcr_mag_y=" + mcr_mag_y +
                ", mcr_mag_z=" + mcr_mag_z +
                ", spec_acc_x=" + spec_acc_x +
                ", spec_acc_y=" + spec_acc_y +
                ", spec_acc_z=" + spec_acc_z +
                ", spec_gyro_x=" + spec_gyro_x +
                ", spec_gyro_y=" + spec_gyro_y +
                ", spec_gyro_z=" + spec_gyro_z +
                ", spec_mag_x=" + spec_mag_x +
                ", spec_mag_y=" + spec_mag_y +
                ", spec_mag_z=" + spec_mag_z +
                ", PC_accx_accy=" + PC_accx_accy +
                ", PC_accx_accz=" + PC_accx_accz +
                ", PC_accx_gyrox=" + PC_accx_gyrox +
                ", PC_accx_gyroy=" + PC_accx_gyroy +
                ", PC_accx_gyroz=" + PC_accx_gyroz +
                ", PC_accx_magx=" + PC_accx_magx +
                ", PC_accx_magy=" + PC_accx_magy +
                ", PC_accx_magz=" + PC_accx_magz +
                ", PC_accy_accz=" + PC_accy_accz +
                ", PC_accy_gyrox=" + PC_accy_gyrox +
                ", PC_accy_gyroy=" + PC_accy_gyroy +
                ", PC_accy_gyroz=" + PC_accy_gyroz +
                ", PC_accy_magx=" + PC_accy_magx +
                ", PC_accy_magy=" + PC_accy_magy +
                ", PC_accy_magz=" + PC_accy_magz +
                ", PC_accz_gyrox=" + PC_accz_gyrox +
                ", PC_accz_gyroy=" + PC_accz_gyroy +
                ", PC_accz_gyroz=" + PC_accz_gyroz +
                ", PC_accz_magx=" + PC_accz_magx +
                ", PC_accz_magy=" + PC_accz_magy +
                ", PC_accz_magz=" + PC_accz_magz +
                ", PC_gyrox_gyroy=" + PC_gyrox_gyroy +
                ", PC_gyrox_gyroz=" + PC_gyrox_gyroz +
                ", PC_gyrox_magx=" + PC_gyrox_magx +
                ", PC_gyrox_magy=" + PC_gyrox_magy +
                ", PC_gyrox_magz=" + PC_gyrox_magz +
                ", PC_gyroy_gyroz=" + PC_gyroy_gyroz +
                ", PC_gyroy_magx=" + PC_gyroy_magx +
                ", PC_gyroy_magy=" + PC_gyroy_magy +
                ", PC_gyroy_magz=" + PC_gyroy_magz +
                ", PC_gyroz_magx=" + PC_gyroz_magx +
                ", PC_gyroz_magy=" + PC_gyroz_magy +
                ", PC_gyroz_magz=" + PC_gyroz_magz +
                ", PC_magx_magy=" + PC_magx_magy +
                ", PC_magx_magz=" + PC_magx_magz +
                ", PC_magy_magz=" + PC_magy_magz +
                ", label=" + label +
                '}';
    }

    public FeatureVector() {

    }

    /**
     * FEATURES TABLE FIELDS
     */

//    public static final String MEAN_ACCX = "mean_accX";
//    public static final String MEAN_ACCY = "mean_accY";
//    public static final String MEAN_ACCZ = "mean_accZ";
//    public static final String MEAN_GYROX = "mean_gyroX";
//    public static final String MEAN_GYROY = "mean_gyroY";
//    public static final String MEAN_GYROZ = "mean_gyroZ";
//    public static final String MEAN_MAGX = "mean_magX";
//    public static final String MEAN_MAGY = "mean_magY";
//    public static final String MEAN_MAGZ = "mean_magZ";
//
//    public static final String MEDIAN_ACCX = "MEDIAN_accX";
//    public static final String MEDIAN_ACCY = "MEDIAN_accY";
//    public static final String MEDIAN_ACCZ = "MEDIAN_accZ";
//    public static final String MEDIAN_GYROX = "MEDIAN_gyroX";
//    public static final String MEDIAN_GYROY = "MEDIAN_gyroY";
//    public static final String MEDIAN_GYROZ = "MEDIAN_gyroZ";
//    public static final String MEDIAN_MAGX = "MEDIAN_magX";
//    public static final String MEDIAN_MAGY = "MEDIAN_magY";
//    public static final String MEDIAN_MAGZ = "MEDIAN_magZ";
//
//    public static final String STD_ACCX = "STD_accX";
//    public static final String STD_ACCY = "STD_accY";
//    public static final String STD_ACCZ = "STD_accZ";
//    public static final String STD_GYROX = "STD_gyroX";
//    public static final String STD_GYROY = "STD_gyroY";
//    public static final String STD_GYROZ = "STD_gyroZ";
//    public static final String STD_MAGX = "STD_magX";
//    public static final String STD_MAGY = "STD_magY";
//    public static final String STD_MAGZ = "STD_magZ";
//
//    public static final String VAR_ACCX = "VAR_accX";
//    public static final String VAR_ACCY = "VAR_accY";
//    public static final String VAR_ACCZ = "VAR_accZ";
//    public static final String VAR_GYROX = "VAR_gyroX";
//    public static final String VAR_GYROY = "VAR_gyroY";
//    public static final String VAR_GYROZ = "VAR_gyroZ";
//    public static final String VAR_MAGX = "VAR_magX";
//    public static final String VAR_MAGY = "VAR_magY";
//    public static final String VAR_MAGZ = "VAR_magZ";
//
//    public static final String RMS_ACCX = "RMS_accX";
//    public static final String RMS_ACCY = "RMS_accY";
//    public static final String RMS_ACCZ = "RMS_accZ";
//    public static final String RMS_GYROX = "RMS_gyroX";
//    public static final String RMS_GYROY = "RMS_gyroY";
//    public static final String RMS_GYROZ = "RMS_gyroZ";
//    public static final String RMS_MAGX = "RMS_magX";
//    public static final String RMS_MAGY = "RMS_magY";
//    public static final String RMS_MAGZ = "RMS_magZ";
//
//    public static final String SKEWNESS_ACCX = "SKEWNESS_accX";
//    public static final String SKEWNESS_ACCY = "SKEWNESS_accY";
//    public static final String SKEWNESS_ACCZ = "SKEWNESS_accZ";
//    public static final String SKEWNESS_GYROX = "SKEWNESS_gyroX";
//    public static final String SKEWNESS_GYROY = "SKEWNESS_gyroY";
//    public static final String SKEWNESS_GYROZ = "SKEWNESS_gyroZ";
//    public static final String SKEWNESS_MAGX = "SKEWNESS_magX";
//    public static final String SKEWNESS_MAGY = "SKEWNESS_magY";
//    public static final String SKEWNESS_MAGZ = "SKEWNESS_magZ";
//
//    public static final String KURTOSIS_ACCX = "KURTOSIS_accX";
//    public static final String KURTOSIS_ACCY = "KURTOSIS_accY";
//    public static final String KURTOSIS_ACCZ = "KURTOSIS_accZ";
//    public static final String KURTOSIS_GYROX = "KURTOSIS_gyroX";
//    public static final String KURTOSIS_GYROY = "KURTOSIS_gyroY";
//    public static final String KURTOSIS_GYROZ = "KURTOSIS_gyroZ";
//    public static final String KURTOSIS_MAGX = "KURTOSIS_magX";
//    public static final String KURTOSIS_MAGY = "KURTOSIS_magY";
//    public static final String KURTOSIS_MAGZ = "KURTOSIS_magZ";
//
//    public static final String IQR_ACCX = "IQR_accX";
//    public static final String IQR_ACCY = "IQR_accY";
//    public static final String IQR_ACCZ = "IQR_accZ";
//    public static final String IQR_GYROX = "IQR_gyroX";
//    public static final String IQR_GYROY = "IQR_gyroY";
//    public static final String IQR_GYROZ = "IQR_gyroZ";
//    public static final String IQR_MAGX = "IQR_magX";
//    public static final String IQR_MAGY = "IQR_magY";
//    public static final String IQR_MAGZ = "IQR_magZ";
//
//    public static final String ZCR_ACCX = "ZCR_accX";
//    public static final String ZCR_ACCY = "ZCR_accY";
//    public static final String ZCR_ACCZ = "ZCR_accZ";
//    public static final String ZCR_GYROX = "ZCR_gyroX";
//    public static final String ZCR_GYROY = "ZCR_gyroY";
//    public static final String ZCR_GYROZ = "ZCR_gyroZ";
//    public static final String ZCR_MAGX = "ZCR_magX";
//    public static final String ZCR_MAGY = "ZCR_magY";
//    public static final String ZCR_MAGZ = "ZCR_magZ";
//
//    public static final String MCR_ACCX = "MCR_accX";
//    public static final String MCR_ACCY = "MCR_accY";
//    public static final String MCR_ACCZ = "MCR_accZ";
//    public static final String MCR_GYROX = "MCR_gyroX";
//    public static final String MCR_GYROY = "MCR_gyroY";
//    public static final String MCR_GYROZ = "MCR_gyroZ";
//    public static final String MCR_MAGX = "MCR_magX";
//    public static final String MCR_MAGY = "MCR_magY";
//    public static final String MCR_MAGZ = "MCR_magZ";
//
//    public static final String SPEC_ACCX = "SPEC_accX";
//    public static final String SPEC_ACCY = "SPEC_accY";
//    public static final String SPEC_ACCZ = "SPEC_accZ";
//    public static final String SPEC_GYROX = "SPEC_gyroX";
//    public static final String SPEC_GYROY = "SPEC_gyroY";
//    public static final String SPEC_GYROZ = "SPEC_gyroZ";
//    public static final String SPEC_MAGX = "SPEC_magX";
//    public static final String SPEC_MAGY = "SPEC_magY";
//    public static final String SPEC_MAGZ = "SPEC_magZ";



    //TODO PC features
}
