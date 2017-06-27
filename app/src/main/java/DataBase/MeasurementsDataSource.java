package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karayan on 7/27/16.
 */
public class MeasurementsDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MeasurementsSQLiteHelper dbHelper;
    private String[] allColumns = { MeasurementsSQLiteHelper.COLUMN_ID,
            MeasurementsSQLiteHelper.COLUMN_LOCATION};

    public MeasurementsDataSource(Context context) {
        dbHelper = new MeasurementsSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Device createDevice(double comment) {
        ContentValues values = new ContentValues();
        values.put(MeasurementsSQLiteHelper.ACCELEROMETER_X, comment);
        long insertId = database.insert(MeasurementsSQLiteHelper.TABLE_MEASUREMENTS, null,
                values);
        Cursor cursor = database.query(MeasurementsSQLiteHelper.TABLE_MEASUREMENTS,
                allColumns, MeasurementsSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Device newDevice = cursorToDevice(cursor);
        cursor.close();
        return newDevice;
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
                allColumns, null, null, null, null, null);
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
}
