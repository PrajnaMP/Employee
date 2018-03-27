package com.example.prajna.employee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by prajna on 24/8/16.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static DBHandler mDBHandler;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Employee_db";
    private static final String TABLE_NAME = "Employee";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE_NO = "mobile_no";
    private static final String KEY_DOB = "dob";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_DEPARTMENT = "department";
    private static final String KEY_DESIGNATION = "designation";
    private static final String KEY_ADDRESS = "address";

    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHandler getDBHandlerInstance(Context context) {
        if (mDBHandler == null) mDBHandler = new DBHandler(context);
        return mDBHandler;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ( first_name TEXT PRIMARY KEY, last_name TEXT,email TEXT,mobile_no TEXT," +
                "dob TEXT,company TEXT,department TEXT,designation TEXT,address TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }


    public long insertAllDataIntoEmployeeList(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long rowInserted = 0;

        try {

            values.put(KEY_FIRST_NAME, employee.getFirst_name());
            values.put(KEY_LAST_NAME, employee.getLast_name());
            values.put(KEY_EMAIL, employee.getEmail());
            values.put(KEY_MOBILE_NO, employee.getMobile());
            values.put(KEY_DOB, employee.getDOB());
            values.put(KEY_COMPANY, employee.getCompany());
            values.put(KEY_DEPARTMENT, employee.getDepartment());
            values.put(KEY_DESIGNATION, employee.getDesignation());
            values.put(KEY_ADDRESS, employee.getAddress());

            rowInserted = db.insert(TABLE_NAME, null, values);
            Log.e("", "");
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
        } catch (SQLiteException sqliteexception) {
            sqliteexception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
        return rowInserted;
    }


    public ArrayList<Employee> getAllStoredEmployeeRecord() {
        ArrayList<Employee> EmployeeList = new ArrayList<Employee>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Employee employee = new Employee();
                    employee.setFirst_name(cursor.getString(0));
                    employee.setLast_name(cursor.getString(1));
                    employee.setEmail(cursor.getString(2));
                    employee.setMobile(cursor.getString(3));
                    employee.setDOB(cursor.getString(4));
                    employee.setCompany(cursor.getString(5));
                    employee.setDepartment(cursor.getString(6));
                    employee.setDesignation(cursor.getString(7));
                    employee.setAddress(cursor.getString(8));

                    EmployeeList.add(employee);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqliteException) {
            sqliteException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (db != null)
                db.close();
            if (cursor != null)
                cursor.close();
            if (EmployeeList != null && EmployeeList.size() > 0) {
                return EmployeeList;
            } else {
                return null;
            }
        }
    }

    public void deleteContact(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int deletedCount = db.delete(TABLE_NAME, KEY_MOBILE_NO + " = ?",
                    new String[]{String.valueOf(employee.getMobile())});
            db.close();
        } catch (SQLiteException sqliteexception) {
            sqliteexception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    ArrayList<Employee> getContact(String first_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Employee> EmployeeList = new ArrayList<Employee>();
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME, new String[]{KEY_FIRST_NAME,
                            KEY_LAST_NAME, KEY_EMAIL, KEY_MOBILE_NO, KEY_DOB, KEY_COMPANY, KEY_DEPARTMENT,
                            KEY_DESIGNATION, KEY_ADDRESS}, KEY_FIRST_NAME + "=?",
                    new String[]{String.valueOf(first_name)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            Employee employee = new Employee();
            employee.setFirst_name(cursor.getString(0));
            employee.setLast_name(cursor.getString(1));
            employee.setEmail(cursor.getString(2));
            employee.setMobile(cursor.getString(3));
            employee.setDOB(cursor.getString(4));
            employee.setCompany(cursor.getString(5));
            employee.setDepartment(cursor.getString(6));
            employee.setDesignation(cursor.getString(7));
            employee.setAddress(cursor.getString(8));
            EmployeeList.add(employee);
            return EmployeeList;

        } catch (SQLiteException sqliteException) {
            sqliteException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (db != null)
                db.close();
            if (cursor != null)
                cursor.close();
            if (EmployeeList != null && EmployeeList.size() > 0) {
                return EmployeeList;
            } else {
                return null;
            }
        }
    }

    public int updateContact(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME, employee.getFirst_name());
        values.put(KEY_LAST_NAME, employee.getLast_name());
        values.put(KEY_EMAIL, employee.getEmail());
        values.put(KEY_MOBILE_NO, employee.getMobile());
        values.put(KEY_DOB, employee.getDOB());
        values.put(KEY_COMPANY, employee.getCompany());
        values.put(KEY_DEPARTMENT, employee.getDepartment());
        values.put(KEY_DESIGNATION, employee.getDesignation());
        values.put(KEY_ADDRESS, employee.getAddress());

        // updating row
        return db.update(TABLE_NAME, values, KEY_MOBILE_NO + " = ?",
                new String[]{String.valueOf(employee.getMobile())});
    }
}