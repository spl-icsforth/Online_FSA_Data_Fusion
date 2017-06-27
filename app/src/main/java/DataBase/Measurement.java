package DataBase;

/**
 * Created by karayan on 7/27/16.
 */
public class Measurement {
    private long id;
    private int device_id;

    //acc
    private double acc_x;
    private double acc_y;
    private double acc_z;
    //gyro
    private double gyro_x;
    private double gyro_y;
    private double gyro_z;
    //mag
    private double mag_x;
    private double mag_y;
    private double mag_z;

    private double timestamp;
    private int label;

    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", device_id=" + device_id +
                ", acc_x=" + acc_x +
                ", acc_y=" + acc_y +
                ", acc_z=" + acc_z +
                ", gyro_x=" + gyro_x +
                ", gyro_y=" + gyro_y +
                ", gyro_z=" + gyro_z +
                ", mag_x=" + mag_x +
                ", mag_y=" + mag_y +
                ", mag_z=" + mag_z +
                ", timestamp=" + timestamp +
                ", label=" + label +
                '}';
    }

    public double getAcc_y() {
        return acc_y;
    }

    public void setAcc_y(double acc_y) {
        this.acc_y = acc_y;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public double getAcc_x() {
        return acc_x;
    }

    public void setAcc_x(double acc_x) {
        this.acc_x = acc_x;
    }

    public double getAcc_z() {
        return acc_z;
    }

    public void setAcc_z(double acc_z) {
        this.acc_z = acc_z;
    }

    public double getGyro_x() {
        return gyro_x;
    }

    public void setGyro_x(double gyro_x) {
        this.gyro_x = gyro_x;
    }

    public double getGyro_y() {
        return gyro_y;
    }

    public void setGyro_y(double gyro_y) {
        this.gyro_y = gyro_y;
    }

    public double getGyro_z() {
        return gyro_z;
    }

    public void setGyro_z(double gyro_z) {
        this.gyro_z = gyro_z;
    }

    public double getMag_x() {
        return mag_x;
    }

    public void setMag_x(double mag_x) {
        this.mag_x = mag_x;
    }

    public double getMag_y() {
        return mag_y;
    }

    public void setMag_y(double mag_y) {
        this.mag_y = mag_y;
    }

    public double getMag_z() {
        return mag_z;
    }

    public void setMag_z(double mag_z) {
        this.mag_z = mag_z;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public Measurement(int id, int device_id,
                       double acc_x, double acc_y, double acc_z,
                       double gyro_x, double gyro_y, double gyro_z,
                       double mag_x, double mag_y, double mag_z,
                       double timestamp, int label) {

        this.id = id;
        this.device_id = device_id;

        this.acc_x = acc_x;
        this.acc_y = acc_y;
        this.acc_z = acc_z;

        this.gyro_x = gyro_x;
        this.gyro_y = gyro_y;
        this.gyro_z = gyro_z;

        this.mag_x = mag_x;
        this.mag_y = mag_y;
        this.mag_z = mag_z;

        this.timestamp = timestamp;
        this.label = label;
    }

    public Measurement(long id) {

        this.id = id;
    }

    public Measurement() {

    }
}
