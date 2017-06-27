package DataBase;

/**
 * Created by karayan on 7/26/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    /**
     * DATABASE
     */
    private static final String DATABASE_NAME = "HAR.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * DEVICES Table Fields
     */
    public static final String TABLE_DEVICES = "devices";
    public static final String DEVICE_ID = "_id";
    public static final String COLUMN_LOCATION = "location";

    /**
     * MEASUREMENTS Table Fields
     */
    public static final String TABLE_MEASUREMENTS = "measurements";
    public static final String MEASUREMENTS_ID = "id";
    public static final String DEV_ID = "dev_id";
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

    /**
     * FEATURES Table Fields
     */
    public static final String TABLE_FEATURES = "features";
    public static final String SEGMENT_ID = "seg_id";
//    public static final String DEV_ID = "dev_id";
    public static final String MEAN_ACCX = "mean0";
    public static final String MEAN_ACCY = "mean1";
    public static final String MEAN_ACCZ = "mean2";
    public static final String MEAN_GYROX = "mean3";
    public static final String MEAN_GYROY = "mean4";
    public static final String MEAN_GYROZ = "mean5";
    public static final String MEAN_MAGX = "mean6";
    public static final String MEAN_MAGY = "mean7";
    public static final String MEAN_MAGZ = "mean8";

    public static final String MEDIAN_ACCX = "median0";
    public static final String MEDIAN_ACCY = "median1";
    public static final String MEDIAN_ACCZ = "median2";
    public static final String MEDIAN_GYROX = "median3";
    public static final String MEDIAN_GYROY = "median4";
    public static final String MEDIAN_GYROZ = "median5";
    public static final String MEDIAN_MAGX = "median6";
    public static final String MEDIAN_MAGY = "median7";
    public static final String MEDIAN_MAGZ = "median8";

    public static final String STD_ACCX = "std0";
    public static final String STD_ACCY = "std1";
    public static final String STD_ACCZ = "std2";
    public static final String STD_GYROX = "std3";
    public static final String STD_GYROY = "std4";
    public static final String STD_GYROZ = "std5";
    public static final String STD_MAGX = "std6";
    public static final String STD_MAGY = "std7";
    public static final String STD_MAGZ = "std8";

    public static final String VAR_ACCX = "var0";
    public static final String VAR_ACCY = "var1";
    public static final String VAR_ACCZ = "var2";
    public static final String VAR_GYROX = "var3";
    public static final String VAR_GYROY = "var4";
    public static final String VAR_GYROZ = "var5";
    public static final String VAR_MAGX = "var6";
    public static final String VAR_MAGY = "var7";
    public static final String VAR_MAGZ = "var8";

    public static final String RMS_ACCX = "rms0";
    public static final String RMS_ACCY = "rms1";
    public static final String RMS_ACCZ = "rms2";
    public static final String RMS_GYROX = "rms3";
    public static final String RMS_GYROY = "rms4";
    public static final String RMS_GYROZ = "rms5";
    public static final String RMS_MAGX = "rms6";
    public static final String RMS_MAGY = "rms7";
    public static final String RMS_MAGZ = "rms8";

    public static final String SKEWNESS_ACCX = "skewness0";
    public static final String SKEWNESS_ACCY = "skewness1";
    public static final String SKEWNESS_ACCZ = "skewness2";
    public static final String SKEWNESS_GYROX = "skewness3";
    public static final String SKEWNESS_GYROY = "skewness4";
    public static final String SKEWNESS_GYROZ = "skewness5";
    public static final String SKEWNESS_MAGX = "skewness6";
    public static final String SKEWNESS_MAGY = "skewness7";
    public static final String SKEWNESS_MAGZ = "skewness8";

    public static final String KURTOSIS_ACCX = "kurtosis0";
    public static final String KURTOSIS_ACCY = "kurtosis1";
    public static final String KURTOSIS_ACCZ = "kurtosis2";
    public static final String KURTOSIS_GYROX = "kurtosis3";
    public static final String KURTOSIS_GYROY = "kurtosis4";
    public static final String KURTOSIS_GYROZ = "kurtosis5";
    public static final String KURTOSIS_MAGX = "kurtosis6";
    public static final String KURTOSIS_MAGY = "kurtosis7";
    public static final String KURTOSIS_MAGZ = "kurtosis8";

    public static final String IQR_ACCX = "iqr0";
    public static final String IQR_ACCY = "iqr1";
    public static final String IQR_ACCZ = "iqr2";
    public static final String IQR_GYROX = "iqr3";
    public static final String IQR_GYROY = "iqr4";
    public static final String IQR_GYROZ = "iqr5";
    public static final String IQR_MAGX = "iqr6";
    public static final String IQR_MAGY = "iqr7";
    public static final String IQR_MAGZ = "iqr8";

    public static final String ZCR_ACCX = "zcr0";
    public static final String ZCR_ACCY = "zcr1";
    public static final String ZCR_ACCZ = "zcr2";
    public static final String ZCR_GYROX = "zcr3";
    public static final String ZCR_GYROY = "zcr4";
    public static final String ZCR_GYROZ = "zcr5";
    public static final String ZCR_MAGX = "zcr6";
    public static final String ZCR_MAGY = "zcr7";
    public static final String ZCR_MAGZ = "zcr8";

    public static final String MCR_ACCX = "mcr0";
    public static final String MCR_ACCY = "mcr1";
    public static final String MCR_ACCZ = "mcr2";
    public static final String MCR_GYROX = "mcr3";
    public static final String MCR_GYROY = "mcr4";
    public static final String MCR_GYROZ = "mcr5";
    public static final String MCR_MAGX = "mcr6";
    public static final String MCR_MAGY = "mcr7";
    public static final String MCR_MAGZ = "mcr8";

    public static final String SPEC_ACCX = "spec0";
    public static final String SPEC_ACCY = "spec1";
    public static final String SPEC_ACCZ = "spec2";
    public static final String SPEC_GYROX = "spec3";
    public static final String SPEC_GYROY = "spec4";
    public static final String SPEC_GYROZ = "spec5";
    public static final String SPEC_MAGX = "spec6";
    public static final String SPEC_MAGY = "spec7";
    public static final String SPEC_MAGZ = "spec8";

    public static final String PC_accx_accy = "PC_c00_01";
    public static final String PC_accx_accz = "PC_c00_02";
    public static final String PC_accx_gyrox = "PC_c00_03";
    public static final String PC_accx_gyroy = "PC_c00_04";
    public static final String PC_accx_gyroz = "PC_c00_05";
    public static final String PC_accx_magx = "PC_c00_06";
    public static final String PC_accx_magy = "PC_c00_07";
    public static final String PC_accx_magz = "PC_c00_08";

    public static final String PC_accy_accz = "PC_c01_02";
    public static final String PC_accy_gyrox = "PC_c01_03";
    public static final String PC_accy_gyroy = "PC_c01_04";
    public static final String PC_accy_gyroz = "PC_c01_05";
    public static final String PC_accy_magx = "PC_c01_06";
    public static final String PC_accy_magy = "PC_c01_07";
    public static final String PC_accy_magz = "PC_c01_08";

    public static final String PC_accz_gyrox = "PC_c02_03";
    public static final String PC_accz_gyroy = "PC_c02_04";
    public static final String PC_accz_gyroz = "PC_c02_05";
    public static final String PC_accz_magx = "PC_c02_06";
    public static final String PC_accz_magy = "PC_c02_07";
    public static final String PC_accz_magz = "PC_c02_08";

    public static final String PC_gyrox_gyroy = "PC_c03_04";
    public static final String PC_gyrox_gyroz = "PC_c03_05";
    public static final String PC_gyrox_magx = "PC_c03_06";
    public static final String PC_gyrox_magy = "PC_c03_07";
    public static final String PC_gyrox_magz = "PC_c03_08";

    public static final String PC_gyroy_gyroz = "PC_c04_05";
    public static final String PC_gyroy_magx = "PC_c04_06";
    public static final String PC_gyroy_magy = "PC_c04_07";
    public static final String PC_gyroy_magz = "PC_c04_08";

    public static final String PC_gyroz_magx = "PC_c05_06";
    public static final String PC_gyroz_magy = "PC_c05_07";
    public static final String PC_gyroz_magz = "PC_c05_08";

    public static final String PC_magx_magy = "PC_c06_07";
    public static final String PC_magx_magz = "PC_c06_08";

    public static final String PC_magy_magz = "PC_c07_08";


    private static MySQLiteHelper sInstance;


    // Database drop sql statement
    private static final String DATABASE_DROP = "drop table if exists"
            + TABLE_DEVICES;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");

        createTableDevices(TABLE_DEVICES, db);
        for (int i = 0; i < 15; i++)
            createTableMeasurements(TABLE_MEASUREMENTS+i, db);
        createTableFeatures(TABLE_FEATURES, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEASUREMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEATURES);
        onCreate(db);
    }

    public static synchronized MySQLiteHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MySQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Method to create a devices table
     * @param name table name
     * @param db database
     */
    public void createTableDevices(String name, SQLiteDatabase db) {
        String sql = "create table "
                + TABLE_DEVICES + "( " + DEVICE_ID
                + " integer primary key autoincrement UNIQUE, " + COLUMN_LOCATION
                + " text not null UNIQUE);";

        db.execSQL(sql);
    }

    /**
     * Method to create a shimmer 3 signal table
     * @param name table name
     * @param db database
     */
    public void createTableMeasurements(String name, SQLiteDatabase db) {

        String sql = "create table if not exists " + name + " (" + MEASUREMENTS_ID
                + " integer primary key autoincrement, "
//                + "FOREIGN KEY(" + DEVICE_ID + ") REFERENCES " + TABLE_DEVICES + "(" + DEVICE_ID + "),"
                + " " + DEVICE_ID + " REFERENCES " + TABLE_DEVICES + "(" + DEVICE_ID + "),"

                + ACCELEROMETER_X + " double," + ACCELEROMETER_Y + " double," + ACCELEROMETER_Z + " double,"
                + GYROSCOPE_X + " double," + GYROSCOPE_Y + " double," + GYROSCOPE_Z + " double,"
                + MAGNETOMETER_X + " double," + MAGNETOMETER_Y + " double," + MAGNETOMETER_Z + " double,"
                + LABEL +" integer,"+ TIMESTAMP + " double" + ");";

        db.execSQL(sql);
    }

    /**
     * sql command to insert data into measurements table
     */
    public String insertIntoMeasurements =
         "INSERT INTO "	+ TABLE_MEASUREMENTS	+ " (" + MEASUREMENTS_ID  + ", "
                + DEVICE_ID  + ", "
                + ACCELEROMETER_X + ", "	+ ACCELEROMETER_Y
                + ", " + ACCELEROMETER_Z + ", "	+ MAGNETOMETER_X + ", " + MAGNETOMETER_Y + ", "
                + MAGNETOMETER_Z + ", " + GYROSCOPE_X	+ ", " + GYROSCOPE_Y + ", "	+ GYROSCOPE_Z +", "
                + TIMESTAMP + ", "+ LABEL
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    /**
     * Method to create a shimmer 3 signal table
     * @param name table name
     * @param db database
     */
    public void createTableFeatures(String name, SQLiteDatabase db) {
        String sql = "create table if not exists " + name + " (" + SEGMENT_ID
                + " integer, "
                + " " + DEVICE_ID + " REFERENCES " + TABLE_DEVICES + "(" + DEVICE_ID + "),"
                + MEAN_ACCX  + " double," + MEAN_ACCY + " double," + MEAN_ACCZ + " double,"
                + MEAN_GYROX + " double," + MEAN_GYROY + " double," + MEAN_GYROZ + " double,"
                + MEAN_MAGX + " double," + MEAN_MAGY + " double," + MEAN_MAGZ + " double,"

                + MEDIAN_ACCX  + " double," + MEDIAN_ACCY + " double," + MEDIAN_ACCZ + " double,"
                + MEDIAN_GYROX + " double," + MEDIAN_GYROY + " double," + MEDIAN_GYROZ + " double,"
                + MEDIAN_MAGX + " double," + MEDIAN_MAGY + " double," + MEDIAN_MAGZ + " double,"

                + STD_ACCX  + " double," + STD_ACCY + " double," + STD_ACCZ + " double,"
                + STD_GYROX + " double," + STD_GYROY + " double," + STD_GYROZ + " double,"
                + STD_MAGX + " double," + STD_MAGY + " double," + STD_MAGZ + " double,"

                + VAR_ACCX  + " double," + VAR_ACCY + " double," + VAR_ACCZ + " double,"
                + VAR_GYROX + " double," + VAR_GYROY + " double," + VAR_GYROZ + " double,"
                + VAR_MAGX + " double," + VAR_MAGY + " double," + VAR_MAGZ + " double,"

                + RMS_ACCX  + " double," + RMS_ACCY + " double," + RMS_ACCZ + " double,"
                + RMS_GYROX + " double," + RMS_GYROY + " double," + RMS_GYROZ + " double,"
                + RMS_MAGX + " double," + RMS_MAGY + " double," + RMS_MAGZ + " double,"

                + SKEWNESS_ACCX  + " double," + SKEWNESS_ACCY + " double," + SKEWNESS_ACCZ + " double,"
                + SKEWNESS_GYROX + " double," + SKEWNESS_GYROY + " double," + SKEWNESS_GYROZ + " double,"
                + SKEWNESS_MAGX + " double," + SKEWNESS_MAGY + " double," + SKEWNESS_MAGZ + " double,"

                + KURTOSIS_ACCX  + " double," + KURTOSIS_ACCY + " double," + KURTOSIS_ACCZ + " double,"
                + KURTOSIS_GYROX + " double," + KURTOSIS_GYROY + " double," + KURTOSIS_GYROZ + " double,"
                + KURTOSIS_MAGX + " double," + KURTOSIS_MAGY + " double," + KURTOSIS_MAGZ + " double,"

                + IQR_ACCX  + " double," + IQR_ACCY + " double," + IQR_ACCZ + " double,"
                + IQR_GYROX + " double," + IQR_GYROY + " double," + IQR_GYROZ + " double,"
                + IQR_MAGX + " double," + IQR_MAGY + " double," + IQR_MAGZ + " double,"

                + ZCR_ACCX  + " double," + ZCR_ACCY + " double," + ZCR_ACCZ + " double,"
                + ZCR_GYROX + " double," + ZCR_GYROY + " double," + ZCR_GYROZ + " double,"
                + ZCR_MAGX + " double," + ZCR_MAGY + " double," + ZCR_MAGZ + " double,"

                + MCR_ACCX  + " double," + MCR_ACCY + " double," + MCR_ACCZ + " double,"
                + MCR_GYROX + " double," + MCR_GYROY + " double," + MCR_GYROZ + " double,"
                + MCR_MAGX + " double," + MCR_MAGY + " double," + MCR_MAGZ + " double,"

                + SPEC_ACCX  + " double," + SPEC_ACCY + " double," + SPEC_ACCZ + " double,"
                + SPEC_GYROX + " double," + SPEC_GYROY + " double," + SPEC_GYROZ + " double,"
                + SPEC_MAGX + " double," + SPEC_MAGY + " double," + SPEC_MAGZ + " double,"

                +
                PC_accx_accy+ " double, " +
                PC_accx_accz+ " double, " +
                PC_accx_gyrox+ " double, " +
                PC_accx_gyroy+ " double, " +
                PC_accx_gyroz+ " double, " +
                PC_accx_magx+ " double, " +
                PC_accx_magy+ " double, " +
                PC_accx_magz+ " double, " +

                PC_accy_accz+ " double, " +
                PC_accy_gyrox+ " double, " +
                PC_accy_gyroy+ " double, " +
                PC_accy_gyroz+ " double, " +
                PC_accy_magx+ " double, " +
                PC_accy_magy+ " double, " +
                PC_accy_magz+ " double, " +

                PC_accz_gyrox+ " double, " +
                PC_accz_gyroy+ " double, " +
                PC_accz_gyroz+ " double, " +
                PC_accz_magx+ " double, " +
                PC_accz_magy+ " double, " +
                PC_accz_magz+ " double, " +

                PC_gyrox_gyroy+ " double, " +
                PC_gyrox_gyroz+ " double, " +
                PC_gyrox_magx+ " double, " +
                PC_gyrox_magy+ " double, " +
                PC_gyrox_magz+ " double, " +

                PC_gyroy_gyroz+ " double, " +
                PC_gyroy_magx+ " double, " +
                PC_gyroy_magy+ " double, " +
                PC_gyroy_magz+ " double, " +

                PC_gyroz_magx+ " double, " +
                PC_gyroz_magy+ " double, " +
                PC_gyroz_magz+ " double, " +

                PC_magx_magy+ " double, " +
                PC_magx_magz+ " double, " +

                PC_magy_magz+ " double, "


                + LABEL +" integer" + ");";

        db.execSQL(sql);
    }

    public void dropTableFeatures(String name, SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEATURES);
    }

    /**
     * sql command to insert data into features table
     */
    public String insertIntoFeatures =
        "INSERT INTO "	+ TABLE_FEATURES + " (" + SEGMENT_ID  + ", "
                + DEVICE_ID  + ", "

                + MEAN_ACCX + ", "	+ MEAN_ACCY + ", " + MEAN_ACCZ + ", "
                + MEAN_GYROX + ", " + MEAN_GYROY + ", " + MEAN_GYROZ + ", "
                + MEAN_MAGX	+ ", " + MEAN_MAGY + ", "	+ MEAN_MAGZ +", "

                + MEDIAN_ACCX  + ", " + MEDIAN_ACCY + ", " + MEDIAN_ACCZ + ", "
                + MEDIAN_GYROX + ", " + MEDIAN_GYROY + ", " + MEDIAN_GYROZ + ", "
                + MEDIAN_MAGX + ", " + MEDIAN_MAGY + ", " + MEDIAN_MAGZ + ", "

                + STD_ACCX  + ", " + STD_ACCY + ", " + STD_ACCZ + ", "
                + STD_GYROX + ", " + STD_GYROY + ", " + STD_GYROZ + ", "
                + STD_MAGX + ", " + STD_MAGY + ", " + STD_MAGZ + ", "

                + VAR_ACCX  + ", " + VAR_ACCY + ", " + VAR_ACCZ + ", "
                + VAR_GYROX + ", " + VAR_GYROY + ", " + VAR_GYROZ + ", "
                + VAR_MAGX + ", " + VAR_MAGY + ", " + VAR_MAGZ + ", "

                + RMS_ACCX  + ", " + RMS_ACCY + ", " + RMS_ACCZ + ", "
                + RMS_GYROX + ", " + RMS_GYROY + ", " + RMS_GYROZ + ", "
                + RMS_MAGX + ", " + RMS_MAGY + ", " + RMS_MAGZ + ", "

                + SKEWNESS_ACCX  + ", " + SKEWNESS_ACCY + ", " + SKEWNESS_ACCZ + ", "
                + SKEWNESS_GYROX + ", " + SKEWNESS_GYROY + ", " + SKEWNESS_GYROZ + ", "
                + SKEWNESS_MAGX + ", " + SKEWNESS_MAGY + ", " + SKEWNESS_MAGZ + ", "

                + KURTOSIS_ACCX  + ", " + KURTOSIS_ACCY + ", " + KURTOSIS_ACCZ + ", "
                + KURTOSIS_GYROX + ", " + KURTOSIS_GYROY + ", " + KURTOSIS_GYROZ + ", "
                + KURTOSIS_MAGX + ", " + KURTOSIS_MAGY + ", " + KURTOSIS_MAGZ + ", "

                + IQR_ACCX  + ", " + IQR_ACCY + ", " + IQR_ACCZ + ", "
                + IQR_GYROX + ", " + IQR_GYROY + ", " + IQR_GYROZ + ", "
                + IQR_MAGX + ", " + IQR_MAGY + ", " + IQR_MAGZ + ", "

                + ZCR_ACCX  + ", " + ZCR_ACCY + ", " + ZCR_ACCZ + ", "
                + ZCR_GYROX + ", " + ZCR_GYROY + ", " + ZCR_GYROZ + ", "
                + ZCR_MAGX + ", " + ZCR_MAGY + ", " + ZCR_MAGZ + ", "

                + MCR_ACCX  + ", " + MCR_ACCY + ", " + MCR_ACCZ + ", "
                + MCR_GYROX + ", " + MCR_GYROY + ", " + MCR_GYROZ + ", "
                + MCR_MAGX + ", " + MCR_MAGY + ", " + MCR_MAGZ + ", "

                + SPEC_ACCX  + ", " + SPEC_ACCY + ", " + SPEC_ACCZ + ", "
                + SPEC_GYROX + ", " + SPEC_GYROY + ", " + SPEC_GYROZ + ", "
                + SPEC_MAGX + ", " + SPEC_MAGY + ", " + SPEC_MAGZ + ", "
                +
                PC_accx_accy+ ", " +
                PC_accx_accz+ ", " +
                PC_accx_gyrox+ ", " +
                PC_accx_gyroy+ ", " +
                PC_accx_gyroz+ ", " +
                PC_accx_magx+ ", " +
                PC_accx_magy+ ", " +
                PC_accx_magz+ ", " +

                PC_accy_accz+ ", " +
                PC_accy_gyrox+ ", " +
                PC_accy_gyroy+ ", " +
                PC_accy_gyroz+ ", " +
                PC_accy_magx+ ", " +
                PC_accy_magy+ ", " +
                PC_accy_magz+ ", " +

                PC_accz_gyrox+ ", " +
                PC_accz_gyroy+ ", " +
                PC_accz_gyroz+ ", " +
                PC_accz_magx+ ", " +
                PC_accz_magy+ ", " +
                PC_accz_magz+ ", " +

                PC_gyrox_gyroy+ ", " +
                PC_gyrox_gyroz+ ", " +
                PC_gyrox_magx+ ", " +
                PC_gyrox_magy+ ", " +
                PC_gyrox_magz+ ", " +

                PC_gyroy_gyroz+ ", " +
                PC_gyroy_magx+ ", " +
                PC_gyroy_magy+ ", " +
                PC_gyroy_magz+ ", " +

                PC_gyroz_magx+ ", " +
                PC_gyroz_magy+ ", " +
                PC_gyroz_magz+ ", " +

                PC_magx_magy+ ", " +
                PC_magx_magz+ ", " +

                PC_magy_magz+ ", "


                + LABEL
                + ") VALUES (?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, " +
                "?)";

}