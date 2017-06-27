package DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by karayan on 7/26/16.
 */
public class MeasurementsSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_MEASUREMENTS = "measurements";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LOCATION = "location";

    private static final String DATABASE_NAME = "HAR.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Table Fields
     */
    public static final String ID = "id";
    public static final String ACCELEROMETER_X = "AccX";
    public static final String ACCELEROMETER_Y = "AccY";
    public static final String ACCELEROMETER_Z = "AccZ";
    public static final String GYROSCOPE_X = "GyroX";
    public static final String GYROSCOPE_Y = "GyroY";
    public static final String GYROSCOPE_Z = "GyroZ";
    public static final String MAGNETOMETER_X = "MagX";
    public static final String MAGNETOMETER_Y = "MagY";
    public static final String MAGNETOMETER_Z = "MagZ";
    public static final String TIMESTAMP = "Timestamp";
    public static final String LABEL = "Label";


    // Database creation sql statement
    private static final String CREATE_TABLE_MEASUREMENTS = "create table "
            + TABLE_MEASUREMENTS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_LOCATION
            + " text not null);";

    // Database drop sql statement
    private static final String DATABASE_DROP = "drop table if exists"
            + TABLE_MEASUREMENTS;


    public MeasurementsSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.print("on Create");
//        db.execSQL(CREATE_TABLE_MEASUREMENTS);
        createShimmer3Table(TABLE_MEASUREMENTS, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEASUREMENTS);
        onCreate(db);
    }

    /**
     * Method to create a shimmer 3 signal table
     * @param name table name
     */
    public void createShimmer3Table(String name, SQLiteDatabase db) {

        String sql = "create table if not exists " + name + " (" + ID
                + " integer primary key autoincrement, " + ACCELEROMETER_X
                + " double," + ACCELEROMETER_Y + " double," + ACCELEROMETER_Z
                + " double," + GYROSCOPE_X + " double," + GYROSCOPE_Y + GYROSCOPE_Z + " double,"
                + " double," + MAGNETOMETER_X + " double," + MAGNETOMETER_Y
                + " double," + MAGNETOMETER_Z + " double,"
                + LABEL +" text,"+ TIMESTAMP + " double" + ");";

        db.execSQL(sql);
    }

}
