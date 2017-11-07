package helper;

/**
 * Created by HP on 24/02/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLiteHandler extends SQLiteOpenHelper{
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api.db";

    // Login table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_UPCOMING_EVENTS = "upcoming_events";
    private static final String TABLE_STUDENTS = "students";
    private static final String TABLE_FACULTY = "faculty";
    private static final String TABLE_CORPORATE  = "corporates";
    private static final String TABLE_INSTITUTE = "institute";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "name";
    private static final String KEY_PASSWORD="password";

    //Upcoming events Table Columns names
    private static final String KEY_DAY = "day";
    private static final String KEY_EVENTS = "events";
    private static final String KEY_DETAILS = "details";

    //students table column names
    private static final String KEY_STUDENT_NAME = "name";
    private static final String KEY_STUDENT_EMAIL = "email";

    //faculty table column names
    private static final String KEY_FACULTY_NAME = "name";
    private static final String KEY_FACULTY_EMAIL = "email";

    //corporate table column names
    private static final String KEY_CORPORATE_NAME = "name";
    private static final String KEY_CORPORATE_EMAIL = "email";

    //institute table column names
    private static final String KEY_INSTITUE_NAME = "name";
    private static final String KEY_INSTITUTE_EMAIL = "email";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private static final String CREATE_LOGIN_TABLE="CREATE TABLE " + TABLE_USER + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USERNAME + " TEXT NOT NULL,"
            + KEY_PASSWORD+ " TEXT NOT NULL)";

    private static final String CREATE_UPCOMING_EVENTS_TABLE="CREATE TABLE " + TABLE_UPCOMING_EVENTS + "("
            + KEY_DAY + " TEXT NOT NULL,"
            + KEY_EVENTS + " TEXT NOT NULL,"
            + KEY_DETAILS + " TEXT NOT NULL)";

    private static final String CREATE_STUDENT_TABLE = "CREATE TABLE "+TABLE_STUDENTS +"("
            + KEY_STUDENT_NAME + " TEXT NOT NULL,"
            + KEY_STUDENT_EMAIL + " TEXT NOT NULL)";

    private static final String CREATE_FACULTY_TABLE = "CREATE TABLE "+TABLE_FACULTY +"("
            + KEY_FACULTY_NAME + " TEXT NOT NULL,"
            + KEY_FACULTY_EMAIL + " TEXT NOT NULL)";

    private static final String CREATE_CORPORATE_TABLE = "CREATE TABLE "+TABLE_CORPORATE +"("
            + KEY_CORPORATE_NAME + " TEXT NOT NULL,"
            + KEY_CORPORATE_EMAIL + " TEXT NOT NULL)";

    private static final String CREATE_INSTITUE_TABLE = "CREATE TABLE "+TABLE_INSTITUTE +"("
            + KEY_INSTITUE_NAME + " TEXT NOT NULL,"
            + KEY_INSTITUTE_EMAIL + " TEXT NOT NULL)";

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_UPCOMING_EVENTS_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_FACULTY_TABLE);
        db.execSQL(CREATE_INSTITUE_TABLE);
        db.execSQL(CREATE_CORPORATE_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPCOMING_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACULTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CORPORATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTITUTE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name,String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, name); // UserName
        values.put(KEY_PASSWORD,password);
        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        //db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addStudents(String name,String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_NAME, name); // UserName
        values.put(KEY_STUDENT_EMAIL,email);
        // Inserting Row
        long id = db.insert(TABLE_STUDENTS, null, values);
        //db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addStudent(String name,String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_NAME, name); // UserName
        values.put(KEY_STUDENT_EMAIL,email);
        // Inserting Row
        long id = db.insert(TABLE_STUDENTS, null, values);
        //db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addFaculty(String name,String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FACULTY_NAME, name); // UserName
        values.put(KEY_FACULTY_EMAIL,email);
        // Inserting Row
        long id = db.insert(TABLE_FACULTY, null, values);
        //db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addCorporate(String name,String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CORPORATE_NAME, name); // UserName
        values.put(KEY_CORPORATE_EMAIL,email);
        // Inserting Row
        long id = db.insert(TABLE_CORPORATE, null, values);
        //db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addInstitute(String name,String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_INSTITUE_NAME, name); // UserName
        values.put(KEY_INSTITUTE_EMAIL,email);
        // Inserting Row
        long id = db.insert(TABLE_INSTITUTE, null, values);
        //db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }


    /**
     * Storing upcoming events details in database
     * */
    public void addUpcomingEvents(String day,String events,String details) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DAY,day);
        values.put(KEY_EVENTS,events);
        values.put(KEY_DETAILS,details);
        // Inserting Row
        long id = db.insert(TABLE_UPCOMING_EVENTS, null, values);
        //db.close(); // Closing database connection

        Log.d(TAG, "Upcoming events inserted into sqlite: " + id);
    }

    public List<HashMap<String,List<String>>> getUpcomingEvents(){
        List<HashMap<String, List<String>>> upcoming_events = new ArrayList<HashMap<String, List<String>>>();
        String selectQuery = "SELECT  * FROM " + TABLE_UPCOMING_EVENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            //upcoming_events.put("events", cursor.getString(2));
            HashMap<String,List<String>> events = new HashMap<String,List<String>>();
            List<String> detailsDay = new ArrayList<String>();
            detailsDay.add(cursor.getString(2));
            detailsDay.add(cursor.getString(1));
            detailsDay.add(cursor.getString(3));
            //upcoming_events.put("day",cursor.getString(1));
            events.put("events",detailsDay);
            upcoming_events.add(events);
        }
        //cursor.close();
        // db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + upcoming_events.toString());

        return upcoming_events;
    }
    /**
     * Getting user data from database
     * */
    public String getUser()
    {
        String selectQuery = "SELECT * FROM " + TABLE_USER;
        String email="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            email = cursor.getString(1);
        }
        Log.d(TAG, "Fetching user from Sqlite: " + email);

        return email;
    }
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("username", cursor.getString(1));
            user.put("password",cursor.getString(2));
        }
        //cursor.close();
       // db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public HashMap<String, String> getStudentDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email",cursor.getString(2));
        }
        //cursor.close();
        // db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public HashMap<String, String> getFacultyDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_FACULTY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email",cursor.getString(2));
        }
        //cursor.close();
        // db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public HashMap<String, String> getInstituteDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_INSTITUTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email",cursor.getString(2));
        }
        //cursor.close();
        // db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    public HashMap<String, String> getCorporateDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_CORPORATE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email",cursor.getString(2));
        }
        //cursor.close();
        // db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
       // db.close();
        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void deleteUpcomingEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_UPCOMING_EVENTS, null, null);
        // db.close();
        Log.d(TAG, "Deleted all upcoming events info from sqlite");
    }

    public void deleteStudentDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_STUDENTS, null, null);
        // db.close();
        Log.d(TAG, "Deleted all students info from sqlite");
    }

    public void deleteFacultyDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_FACULTY, null, null);
        // db.close();
        Log.d(TAG, "Deleted all faculty info from sqlite");
    }

    public void deleteCorporateDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CORPORATE, null, null);
        // db.close();
        Log.d(TAG, "Deleted all corporate info from sqlite");
    }

    public void deleteInstituteDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_INSTITUTE, null, null);
        // db.close();
        Log.d(TAG, "Deleted all institute info from sqlite");
    }


}
