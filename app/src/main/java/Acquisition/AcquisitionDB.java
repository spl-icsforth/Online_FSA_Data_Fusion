package Acquisition;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataBase.DBDataSource;
import DataBase.Measurement;

/**
 * Created by karayan on 7/28/16.
 */
public class AcquisitionDB {
    DBDataSource dataSource;

    private HashMap<Integer, ArrayList<Double>> data;
    private HashMap<Integer, ArrayList<Double>> dataT;
    private ArrayList<Double> labels;
    private ArrayList<Double> device_ids;
    private ArrayList<Double> timestamps;

    private int top;

    public int getTop() {
        return top;
    }
    public void setTop(int top) { this.top = top; }

    public AcquisitionDB() {
//        this.dataSource = dataSource;

        this.data = new HashMap<Integer, ArrayList<Double>>();
        this.dataT = new HashMap<Integer, ArrayList<Double>>();
        this.labels = new ArrayList<Double>();
        this.device_ids = new ArrayList<Double>();
        this.timestamps = new ArrayList<Double>();

        this.top = 0;
    }

    public boolean retrieveMeasurementsW(DBDataSource dataSource, int device_id, double W, String tableName) {
        System.out.println("top: "+top);
        double W_sec = W * 60;
        double W_ms = W_sec*1000;

        // init data
        for (int i=0; i<9; i++){
            this.data.put(i, new ArrayList<Double>());
        }
        this.labels.clear();// = new ArrayList<Double>();

//        String tableName = "measurements";
        SQLiteDatabase database = dataSource.getDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName + " WHERE _id = " + device_id +
                                            " AND Timestamp BETWEEN " + this.top +
                                            " AND " + (W_ms + this.top), null);
        cursor.moveToFirst();

        int i=0;
        while (!cursor.isAfterLast()) {
            // import data to hashmaps
            device_ids.add(cursor.getDouble(1));
            // acc
            data.get(0).add(cursor.getDouble(2));
            data.get(1).add(cursor.getDouble(3));
            data.get(2).add(cursor.getDouble(4));
            //gyro
            data.get(3).add(cursor.getDouble(5));
            data.get(4).add(cursor.getDouble(6));
            data.get(5).add(cursor.getDouble(7));
            //mag
            data.get(6).add(cursor.getDouble(8));
            data.get(7).add(cursor.getDouble(9));
            data.get(8).add(cursor.getDouble(10));

            timestamps.add(cursor.getDouble(12));
            labels.add(cursor.getDouble(11));

            // move to next cursor
            cursor.moveToNext();

            i++;
        }
        cursor.moveToLast();
        double lastIndex = cursor.getDouble(cursor.getColumnIndex("Timestamp"));
        this.top = (int) lastIndex;

        cursor.close();

//        System.out.println(data);
//        System.out.println("device_ids: "+device_ids);
//        System.out.println("timestamps: "+timestamps);
//        System.out.println("labels:"+labels);

        return true;
    }

    public void selectAllFromMeasurements(DBDataSource dataSource, int device_id, double W, String tableName) {
        retrieveMeasurementsW(dataSource, device_id, W, tableName);
    }

    public void getMeasurementsPerDevice(String tableName, int device_id) {
        // init data
        for (int i=0; i<9; i++){
            this.data.put(i, new ArrayList<Double>());
        }

        SQLiteDatabase database = dataSource.getDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName + " WHERE _id= " + device_id, null);
        cursor.moveToFirst();

        int i=0;
        while (!cursor.isAfterLast()) {
            // import data to hashmaps
            device_ids.add(cursor.getDouble(1));
            // acc
            data.get(0).add(cursor.getDouble(2));
            data.get(1).add(cursor.getDouble(3));
            data.get(2).add(cursor.getDouble(4));
            //gyro
            data.get(3).add(cursor.getDouble(5));
            data.get(4).add(cursor.getDouble(6));
            data.get(5).add(cursor.getDouble(7));
            //mag
            data.get(6).add(cursor.getDouble(8));
            data.get(7).add(cursor.getDouble(9));
            data.get(8).add(cursor.getDouble(10));

            timestamps.add(cursor.getDouble(11));
            labels.add(cursor.getDouble(12));

            // move to next cursor
            cursor.moveToNext();

            i++;
        }
        cursor.close();

//        System.out.println(data);
//        System.out.println("device_ids: "+device_ids);
//        System.out.println("timestamps: "+timestamps);
//        System.out.println("labels:"+labels);

    }

    public List<Measurement> getMeasurementsPerDevice2(String tableName, int device_id) {
        List<Measurement> measurements = new ArrayList<Measurement>();
        SQLiteDatabase database = dataSource.getDatabase();

        List<Double> list = new ArrayList<Double>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableName + " WHERE _id= " + device_id, null);
        cursor.moveToFirst();

        int i=0;
        while (!cursor.isAfterLast()) {
            // create a Measurement object containing the measurements for a time instance
            Measurement meas = new Measurement();
            meas.setId(cursor.getLong(0));
            meas.setDevice_id(cursor.getInt(1));

            meas.setAcc_x(cursor.getDouble(2));
            meas.setAcc_x(cursor.getDouble(3));
            meas.setAcc_x(cursor.getDouble(4));

            meas.setGyro_x(cursor.getDouble(5));
            meas.setGyro_x(cursor.getDouble(6));
            meas.setGyro_x(cursor.getDouble(7));

            meas.setMag_x(cursor.getDouble(8));
            meas.setMag_x(cursor.getDouble(9));
            meas.setMag_x(cursor.getDouble(10));

            meas.setTimestamp(cursor.getDouble(11));
            meas.setLabel(cursor.getInt(12));

//            System.out.println("meass::: "+meas.toString());
            // add the measurement to the measurements list
            measurements.add(meas);

            // move to next cursor
            cursor.moveToNext();

            i++;
        }
        cursor.close();
        return measurements;
    }

    public HashMap<Integer, ArrayList<Double>> getData() {
        return data;
    }

    public void setData(HashMap<Integer, ArrayList<Double>> data) {
        this.data = data;
    }

    public ArrayList<Double> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<Double> labels) {
        this.labels = labels;
    }

    public ArrayList<Double> getDevice_ids() {
        return device_ids;
    }

    public void setDevice_ids(ArrayList<Double> device_ids) {
        this.device_ids = device_ids;
    }

    public ArrayList<Double> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(ArrayList<Double> timestamps) {
        this.timestamps = timestamps;
    }

    public HashMap<Integer, ArrayList<Double>> getDataT() {
        return dataT;
    }

    public void setDataT(HashMap<Integer, ArrayList<Double>> dataT) {
        this.dataT = dataT;
    }
}
