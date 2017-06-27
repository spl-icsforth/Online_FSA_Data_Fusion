package DataBase;

/**
 * Created by karayan on 7/26/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBDataSource {

    // Database fields
    private SQLiteDatabase database;
    private DataBase.MySQLiteHelper dbHelper;

    private String[] allColumnsDevices = { MySQLiteHelper.DEVICE_ID,
            MySQLiteHelper.COLUMN_LOCATION};

    private String[] allColumnsMeasurements = {
            MySQLiteHelper.MEASUREMENTS_ID, MySQLiteHelper.DEVICE_ID,
            MySQLiteHelper.ACCELEROMETER_X, MySQLiteHelper.ACCELEROMETER_Y, MySQLiteHelper.ACCELEROMETER_Z,
            MySQLiteHelper.GYROSCOPE_X, MySQLiteHelper.GYROSCOPE_Y, MySQLiteHelper.GYROSCOPE_Z,
            MySQLiteHelper.MAGNETOMETER_X, MySQLiteHelper.MAGNETOMETER_Y, MySQLiteHelper.MAGNETOMETER_Z,
            MySQLiteHelper.TIMESTAMP, MySQLiteHelper.LABEL
    };

    public String[] getAllColumnsFeatures() {
        return allColumnsFeatures;
    }

    private String[] allColumnsFeatures = {
            MySQLiteHelper.SEGMENT_ID, MySQLiteHelper.DEVICE_ID,
            MySQLiteHelper.MEAN_ACCX, MySQLiteHelper.MEAN_ACCY, MySQLiteHelper.MEAN_ACCZ,
            MySQLiteHelper.MEAN_GYROX, MySQLiteHelper.MEAN_GYROY, MySQLiteHelper.MEAN_GYROZ,
            MySQLiteHelper.MEAN_MAGX, MySQLiteHelper.MEAN_MAGY, MySQLiteHelper.MEAN_MAGZ,

            MySQLiteHelper.MEDIAN_ACCX  , MySQLiteHelper.MEDIAN_ACCY , MySQLiteHelper.MEDIAN_ACCZ  ,
            MySQLiteHelper.MEDIAN_GYROX , MySQLiteHelper.MEDIAN_GYROY , MySQLiteHelper.MEDIAN_GYROZ  ,
            MySQLiteHelper.MEDIAN_MAGX , MySQLiteHelper.MEDIAN_MAGY , MySQLiteHelper.MEDIAN_MAGZ  ,

            MySQLiteHelper.STD_ACCX  , MySQLiteHelper.STD_ACCY , MySQLiteHelper.STD_ACCZ  ,
            MySQLiteHelper.STD_GYROX , MySQLiteHelper.STD_GYROY , MySQLiteHelper.STD_GYROZ  ,
            MySQLiteHelper.STD_MAGX , MySQLiteHelper.STD_MAGY , MySQLiteHelper.STD_MAGZ  ,

            MySQLiteHelper.VAR_ACCX  , MySQLiteHelper.VAR_ACCY , MySQLiteHelper.VAR_ACCZ  ,
            MySQLiteHelper.VAR_GYROX , MySQLiteHelper.VAR_GYROY , MySQLiteHelper.VAR_GYROZ  ,
            MySQLiteHelper.VAR_MAGX , MySQLiteHelper.VAR_MAGY , MySQLiteHelper.VAR_MAGZ  ,

            MySQLiteHelper.RMS_ACCX  , MySQLiteHelper.RMS_ACCY , MySQLiteHelper.RMS_ACCZ  ,
            MySQLiteHelper.RMS_GYROX , MySQLiteHelper.RMS_GYROY , MySQLiteHelper.RMS_GYROZ  ,
            MySQLiteHelper.RMS_MAGX , MySQLiteHelper.RMS_MAGY , MySQLiteHelper.RMS_MAGZ  ,

            MySQLiteHelper.SKEWNESS_ACCX  , MySQLiteHelper.SKEWNESS_ACCY , MySQLiteHelper.SKEWNESS_ACCZ  ,
            MySQLiteHelper.SKEWNESS_GYROX , MySQLiteHelper.SKEWNESS_GYROY , MySQLiteHelper.SKEWNESS_GYROZ  ,
            MySQLiteHelper.SKEWNESS_MAGX , MySQLiteHelper.SKEWNESS_MAGY , MySQLiteHelper.SKEWNESS_MAGZ  ,

            MySQLiteHelper.KURTOSIS_ACCX  , MySQLiteHelper.KURTOSIS_ACCY , MySQLiteHelper.KURTOSIS_ACCZ  ,
            MySQLiteHelper.KURTOSIS_GYROX , MySQLiteHelper.KURTOSIS_GYROY , MySQLiteHelper.KURTOSIS_GYROZ  ,
            MySQLiteHelper.KURTOSIS_MAGX , MySQLiteHelper.KURTOSIS_MAGY , MySQLiteHelper.KURTOSIS_MAGZ  ,

            MySQLiteHelper.IQR_ACCX  , MySQLiteHelper.IQR_ACCY , MySQLiteHelper.IQR_ACCZ  ,
            MySQLiteHelper.IQR_GYROX , MySQLiteHelper.IQR_GYROY , MySQLiteHelper.IQR_GYROZ  ,
            MySQLiteHelper.IQR_MAGX , MySQLiteHelper.IQR_MAGY , MySQLiteHelper.IQR_MAGZ  ,

            MySQLiteHelper.ZCR_ACCX  , MySQLiteHelper.ZCR_ACCY , MySQLiteHelper.ZCR_ACCZ  ,
            MySQLiteHelper.ZCR_GYROX , MySQLiteHelper.ZCR_GYROY , MySQLiteHelper.ZCR_GYROZ  ,
            MySQLiteHelper.ZCR_MAGX , MySQLiteHelper.ZCR_MAGY , MySQLiteHelper.ZCR_MAGZ  ,

            MySQLiteHelper.MCR_ACCX  , MySQLiteHelper.MCR_ACCY , MySQLiteHelper.MCR_ACCZ  ,
            MySQLiteHelper.MCR_GYROX , MySQLiteHelper.MCR_GYROY , MySQLiteHelper.MCR_GYROZ  ,
            MySQLiteHelper.MCR_MAGX , MySQLiteHelper.MCR_MAGY , MySQLiteHelper.MCR_MAGZ  ,

            MySQLiteHelper.SPEC_ACCX  , MySQLiteHelper.SPEC_ACCY , MySQLiteHelper.SPEC_ACCZ  ,
            MySQLiteHelper.SPEC_GYROX , MySQLiteHelper.SPEC_GYROY , MySQLiteHelper.SPEC_GYROZ  ,
            MySQLiteHelper.SPEC_MAGX , MySQLiteHelper.SPEC_MAGY , MySQLiteHelper.SPEC_MAGZ  ,

            MySQLiteHelper.PC_accx_accy,
            MySQLiteHelper.PC_accx_accz,
            MySQLiteHelper.PC_accx_gyrox,
            MySQLiteHelper.PC_accx_gyroy,
            MySQLiteHelper.PC_accx_gyroz,
            MySQLiteHelper.PC_accx_magx,
            MySQLiteHelper.PC_accx_magy,
            MySQLiteHelper.PC_accx_magz,

            MySQLiteHelper.PC_accy_accz,
            MySQLiteHelper.PC_accy_gyrox,
            MySQLiteHelper.PC_accy_gyroy,
            MySQLiteHelper.PC_accy_gyroz,
            MySQLiteHelper.PC_accy_magx,
            MySQLiteHelper.PC_accy_magy,
            MySQLiteHelper.PC_accy_magz,

            MySQLiteHelper.PC_accz_gyrox,
            MySQLiteHelper.PC_accz_gyroy,
            MySQLiteHelper.PC_accz_gyroz,
            MySQLiteHelper.PC_accz_magx,
            MySQLiteHelper.PC_accz_magy,
            MySQLiteHelper.PC_accz_magz,

            MySQLiteHelper.PC_gyrox_gyroy,
            MySQLiteHelper.PC_gyrox_gyroz,
            MySQLiteHelper.PC_gyrox_magx,
            MySQLiteHelper.PC_gyrox_magy,
            MySQLiteHelper.PC_gyrox_magz,

            MySQLiteHelper.PC_gyroy_gyroz,
            MySQLiteHelper.PC_gyroy_magx,
            MySQLiteHelper.PC_gyroy_magy,
            MySQLiteHelper.PC_gyroy_magz,

            MySQLiteHelper.PC_gyroz_magx,
            MySQLiteHelper.PC_gyroz_magy,
            MySQLiteHelper.PC_gyroz_magz,

            MySQLiteHelper.PC_magx_magy,
            MySQLiteHelper.PC_magx_magz,

            MySQLiteHelper.PC_magy_magz ,

            MySQLiteHelper.LABEL
    };

    public DBDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public MySQLiteHelper getDbHelper() {
        return dbHelper;
    }

    public SQLiteDatabase getDatabase() {

        return database;
    }

    public void upgrade() {
        dbHelper.dropTableFeatures(dbHelper.TABLE_FEATURES, database);
        dbHelper.createTableFeatures(dbHelper.TABLE_FEATURES, database);
    }

    public Device createDevice(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_LOCATION, comment);

        long insertId = database.insert(MySQLiteHelper.TABLE_DEVICES, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DEVICES,
                allColumnsDevices, MySQLiteHelper.DEVICE_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Device newDevice = cursorToDevice(cursor);
        cursor.close();
        return newDevice;
    }

    public void createMeasurement(Measurement meas, int part) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.MEASUREMENTS_ID, meas.getId());
        values.put(MySQLiteHelper.DEVICE_ID, meas.getDevice_id());

        values.put(MySQLiteHelper.ACCELEROMETER_X, meas.getAcc_x());
        values.put(MySQLiteHelper.ACCELEROMETER_Y, meas.getAcc_y());
        values.put(MySQLiteHelper.ACCELEROMETER_Z, meas.getAcc_z());

        values.put(MySQLiteHelper.GYROSCOPE_X, meas.getGyro_x());
        values.put(MySQLiteHelper.GYROSCOPE_Y, meas.getGyro_y());
        values.put(MySQLiteHelper.GYROSCOPE_Z, meas.getGyro_z());

        values.put(MySQLiteHelper.MAGNETOMETER_X, meas.getMag_x());
        values.put(MySQLiteHelper.MAGNETOMETER_Y, meas.getMag_y());
        values.put(MySQLiteHelper.MAGNETOMETER_Z, meas.getMag_z());

        values.put(MySQLiteHelper.TIMESTAMP, meas.getTimestamp());
        values.put(MySQLiteHelper.LABEL, meas.getLabel());

//        long insertId = database.insert(MySQLiteHelper.TABLE_MEASUREMENTS, null, values);
        long insertId = database.insert("measurements"+part, null, values);
    }


    public void createFeatureVector(FeatureVector featureVector) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.SEGMENT_ID, featureVector.getSegment_id());
        values.put(MySQLiteHelper.DEVICE_ID, featureVector.getDevice_id());

        values.put(MySQLiteHelper.MEAN_ACCX, featureVector.getMean_acc_x());
        values.put(MySQLiteHelper.MEAN_ACCY, featureVector.getMean_acc_y());
        values.put(MySQLiteHelper.MEAN_ACCZ, featureVector.getMean_acc_z());

        values.put(MySQLiteHelper.MEAN_GYROX, featureVector.getMean_gyro_x());
        values.put(MySQLiteHelper.MEAN_GYROY, featureVector.getMean_gyro_y());
        values.put(MySQLiteHelper.MEAN_GYROZ, featureVector.getMean_gyro_z());

        values.put(MySQLiteHelper.MEAN_MAGX, featureVector.getMean_mag_x());
        values.put(MySQLiteHelper.MEAN_MAGY, featureVector.getMean_mag_y());
        values.put(MySQLiteHelper.MEAN_MAGZ, featureVector.getMean_mag_x());

        values.put(MySQLiteHelper.LABEL, featureVector.getLabel());

        long insertId = database.insert(MySQLiteHelper.TABLE_FEATURES, null, values);
    }

    public void createFeatureVector2(String[] featureNames, double[] featureVector, int seg_id, int dev_id, int label) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.SEGMENT_ID, seg_id);
        values.put(MySQLiteHelper.DEVICE_ID, dev_id);

        for (int i=0; i<featureNames.length; i++) {
            values.put(featureNames[i], featureVector[i]);
        }

        values.put(MySQLiteHelper.LABEL, label);

        long insertId = database.insert(dbHelper.TABLE_FEATURES, null, values);
    }


    public void deleteDevice(Device device) {
        long id = device.getId();
        System.out.println("Device deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_DEVICES, MySQLiteHelper.DEVICE_ID
                + " = " + id, null);
    }

    public List<Device> getAllDevices() {
        List<Device> devices = new ArrayList<Device>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_DEVICES,
                allColumnsDevices, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Device device = cursorToDevice(cursor);
            devices.add(device);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return devices;
    }

    public List<Measurement> getAllMeasurements(int participant_no) {
        List<Measurement> measurements = new ArrayList<Measurement>();

        Cursor cursor = database.query("measurements"+participant_no,
                allColumnsMeasurements, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Measurement measurement = cursorToMeasurement(cursor);
            measurements.add(measurement);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return measurements;
    }

    public void deleteAllDevices() {
        String DATABASE_DROP = "drop table if exists "
                + MySQLiteHelper.TABLE_DEVICES;
        database.execSQL(DATABASE_DROP);
    }

    private Device cursorToDevice(Cursor cursor) {
        Device device = new Device();
        device.setId(cursor.getLong(0));
        device.setLocation(cursor.getString(1));
        return device;
    }

    private Measurement cursorToMeasurement(Cursor cursor) {
        Measurement measurement = new Measurement();
        measurement.setId(cursor.getLong(0));
        measurement.setDevice_id(cursor.getInt(1));
        measurement.setAcc_x(cursor.getDouble(2));
        measurement.setAcc_y(cursor.getDouble(3));
        measurement.setAcc_z(cursor.getDouble(4));
        measurement.setGyro_x(cursor.getDouble(5));
        measurement.setGyro_y(cursor.getDouble(6));
        measurement.setGyro_z(cursor.getDouble(7));
        measurement.setMag_x(cursor.getDouble(8));
        measurement.setMag_y(cursor.getDouble(9));
        measurement.setMag_z(cursor.getDouble(10));
        measurement.setTimestamp(cursor.getDouble(11));
        measurement.setLabel(cursor.getInt(12));

        return measurement;
    }

    public List<FeatureVector> getAllFeatures() {
        List<FeatureVector> featureVectors = new ArrayList<FeatureVector>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FEATURES,
                allColumnsFeatures, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FeatureVector featureVector = cursorToFeatureVector(cursor);
            featureVectors.add(featureVector);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return featureVectors;
    }

    private FeatureVector cursorToFeatureVector(Cursor cursor) {
        FeatureVector featureVector = new FeatureVector();
        featureVector.setSegment_id(cursor.getLong(0));
        featureVector.setDevice_id(cursor.getInt(1));

        featureVector.setMean_acc_x(cursor.getDouble(2));
        featureVector.setMean_acc_y(cursor.getDouble(3));
        featureVector.setMean_acc_z(cursor.getDouble(4));
        featureVector.setMean_gyro_x(cursor.getDouble(5));
        featureVector.setMean_gyro_y(cursor.getDouble(6));
        featureVector.setMean_gyro_z(cursor.getDouble(7));
        featureVector.setMean_mag_x(cursor.getDouble(8));
        featureVector.setMean_mag_y(cursor.getDouble(9));
        featureVector.setMean_mag_z(cursor.getDouble(10));

        featureVector.setMedian_acc_x(cursor.getDouble(11));
        featureVector.setMedian_acc_y(cursor.getDouble(12));
        featureVector.setMedian_acc_z(cursor.getDouble(13));
        featureVector.setMedian_gyro_x(cursor.getDouble(14));
        featureVector.setMedian_gyro_y(cursor.getDouble(15));
        featureVector.setMedian_gyro_z(cursor.getDouble(16));
        featureVector.setMedian_mag_x(cursor.getDouble(17));
        featureVector.setMedian_mag_y(cursor.getDouble(18));
        featureVector.setMedian_mag_z(cursor.getDouble(19));

        featureVector.setStd_acc_x(cursor.getDouble(20));
        featureVector.setStd_acc_y(cursor.getDouble(21));
        featureVector.setStd_acc_z(cursor.getDouble(22));
        featureVector.setStd_gyro_x(cursor.getDouble(23));
        featureVector.setStd_gyro_y(cursor.getDouble(24));
        featureVector.setStd_gyro_z(cursor.getDouble(25));
        featureVector.setStd_mag_x(cursor.getDouble(26));
        featureVector.setStd_mag_y(cursor.getDouble(27));
        featureVector.setStd_mag_z(cursor.getDouble(28));

        featureVector.setVar_acc_x(cursor.getDouble(29));
        featureVector.setVar_acc_y(cursor.getDouble(30));
        featureVector.setVar_acc_z(cursor.getDouble(31));
        featureVector.setVar_gyro_x(cursor.getDouble(32));
        featureVector.setVar_gyro_y(cursor.getDouble(33));
        featureVector.setVar_gyro_z(cursor.getDouble(34));
        featureVector.setVar_mag_x(cursor.getDouble(35));
        featureVector.setVar_mag_y(cursor.getDouble(36));
        featureVector.setVar_mag_z(cursor.getDouble(37));

        featureVector.setRms_acc_x(cursor.getDouble(38));
        featureVector.setRms_acc_y(cursor.getDouble(39));
        featureVector.setRms_acc_z(cursor.getDouble(40));
        featureVector.setRms_gyro_x(cursor.getDouble(41));
        featureVector.setRms_gyro_y(cursor.getDouble(42));
        featureVector.setRms_gyro_z(cursor.getDouble(43));
        featureVector.setRms_mag_x(cursor.getDouble(44));
        featureVector.setRms_mag_y(cursor.getDouble(45));
        featureVector.setRms_mag_z(cursor.getDouble(46));

        featureVector.setSkewness_acc_x(cursor.getDouble(47));
        featureVector.setSkewness_acc_y(cursor.getDouble(48));
        featureVector.setSkewness_acc_z(cursor.getDouble(49));
        featureVector.setSkewness_gyro_x(cursor.getDouble(50));
        featureVector.setSkewness_gyro_y(cursor.getDouble(51));
        featureVector.setSkewness_gyro_z(cursor.getDouble(52));
        featureVector.setSkewness_mag_x(cursor.getDouble(53));
        featureVector.setSkewness_mag_y(cursor.getDouble(54));
        featureVector.setSkewness_mag_z(cursor.getDouble(55));

        featureVector.setKurtosis_acc_x(cursor.getDouble(56));
        featureVector.setKurtosis_acc_y(cursor.getDouble(57));
        featureVector.setKurtosis_acc_z(cursor.getDouble(58));
        featureVector.setKurtosis_gyro_x(cursor.getDouble(59));
        featureVector.setKurtosis_gyro_y(cursor.getDouble(60));
        featureVector.setKurtosis_gyro_z(cursor.getDouble(61));
        featureVector.setKurtosis_mag_x(cursor.getDouble(62));
        featureVector.setKurtosis_mag_y(cursor.getDouble(63));
        featureVector.setKurtosis_mag_z(cursor.getDouble(64));

        featureVector.setIqr_acc_x(cursor.getDouble(65));
        featureVector.setIqr_acc_y(cursor.getDouble(66));
        featureVector.setIqr_acc_z(cursor.getDouble(67));
        featureVector.setIqr_gyro_x(cursor.getDouble(68));
        featureVector.setIqr_gyro_y(cursor.getDouble(69));
        featureVector.setIqr_gyro_z(cursor.getDouble(70));
        featureVector.setIqr_mag_x(cursor.getDouble(71));
        featureVector.setIqr_mag_y(cursor.getDouble(71));
        featureVector.setIqr_mag_z(cursor.getDouble(73));

        featureVector.setZcr_acc_x(cursor.getDouble(74));
        featureVector.setZcr_acc_y(cursor.getDouble(75));
        featureVector.setZcr_acc_z(cursor.getDouble(76));
        featureVector.setZcr_gyro_x(cursor.getDouble(77));
        featureVector.setZcr_gyro_y(cursor.getDouble(78));
        featureVector.setZcr_gyro_z(cursor.getDouble(79));
        featureVector.setZcr_mag_x(cursor.getDouble(80));
        featureVector.setZcr_mag_y(cursor.getDouble(81));
        featureVector.setZcr_mag_z(cursor.getDouble(82));

        featureVector.setMcr_acc_x(cursor.getDouble(83));
        featureVector.setMcr_acc_y(cursor.getDouble(84));
        featureVector.setMcr_acc_z(cursor.getDouble(85));
        featureVector.setMcr_gyro_x(cursor.getDouble(86));
        featureVector.setMcr_gyro_y(cursor.getDouble(87));
        featureVector.setMcr_gyro_z(cursor.getDouble(88));
        featureVector.setMcr_mag_x(cursor.getDouble(89));
        featureVector.setMcr_mag_y(cursor.getDouble(90));
        featureVector.setMcr_mag_z(cursor.getDouble(91));

        featureVector.setSpec_acc_x(cursor.getDouble(92));
        featureVector.setSpec_acc_y(cursor.getDouble(93));
        featureVector.setSpec_acc_z(cursor.getDouble(94));
        featureVector.setSpec_gyro_x(cursor.getDouble(95));
        featureVector.setSpec_gyro_y(cursor.getDouble(96));
        featureVector.setSpec_gyro_z(cursor.getDouble(97));
        featureVector.setSpec_mag_x(cursor.getDouble(98));
        featureVector.setSpec_mag_y(cursor.getDouble(99));
        featureVector.setSpec_mag_z(cursor.getDouble(100));

        featureVector.setPC_accx_accy(cursor.getDouble(101));
        featureVector.setPC_accx_accz(cursor.getDouble(102));
        featureVector.setPC_accx_gyrox(cursor.getDouble(103));
        featureVector.setPC_accx_gyroy(cursor.getDouble(104));
        featureVector.setPC_accx_gyroz(cursor.getDouble(105));
        featureVector.setPC_accx_magx(cursor.getDouble(106));
        featureVector.setPC_accx_magy(cursor.getDouble(107));
        featureVector.setPC_accx_magz(cursor.getDouble(108));

        featureVector.setPC_accy_accz(cursor.getDouble(109));
        featureVector.setPC_accy_gyrox(cursor.getDouble(110));
        featureVector.setPC_accy_gyroy(cursor.getDouble(111));
        featureVector.setPC_accy_gyroz(cursor.getDouble(112));
        featureVector.setPC_accy_magx(cursor.getDouble(113));
        featureVector.setPC_accy_magy(cursor.getDouble(114));
        featureVector.setPC_accy_magz(cursor.getDouble(115));

        featureVector.setPC_accz_gyrox(cursor.getDouble(116));
        featureVector.setPC_accz_gyroy(cursor.getDouble(117));
        featureVector.setPC_accz_gyroz(cursor.getDouble(118));
        featureVector.setPC_accz_magx(cursor.getDouble(119));
        featureVector.setPC_accz_magy(cursor.getDouble(120));
        featureVector.setPC_accz_magz(cursor.getDouble(121));

        featureVector.setPC_gyrox_gyroy(cursor.getDouble(122));
        featureVector.setPC_gyrox_gyroz(cursor.getDouble(123));
        featureVector.setPC_gyrox_magx(cursor.getDouble(124));
        featureVector.setPC_gyrox_magy(cursor.getDouble(125));
        featureVector.setPC_gyrox_magz(cursor.getDouble(126));

        featureVector.setPC_gyroy_gyroz(cursor.getDouble(127));
        featureVector.setPC_gyroy_magx(cursor.getDouble(128));
        featureVector.setPC_gyroy_magy(cursor.getDouble(129));
        featureVector.setPC_gyroy_magz(cursor.getDouble(130));

        featureVector.setPC_gyroz_magx(cursor.getDouble(131));
        featureVector.setPC_gyroz_magy(cursor.getDouble(132));
        featureVector.setPC_gyroz_magz(cursor.getDouble(133));

        featureVector.setPC_magx_magy(cursor.getDouble(134));
        featureVector.setPC_magx_magz(cursor.getDouble(135));

        featureVector.setPC_magy_magz(cursor.getDouble(136));


        featureVector.setLabel(cursor.getInt(137));

        return featureVector;
    }
}
