package com.luxevista.resort;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;

    // Tables
    private static final String TABLE_USERS = "users";
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String TABLE_SERVICES = "services";

    // Users Table Columns
    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";

    // Bookings Table Columns
    private static final String BOOKING_ID = "id";
    private static final String BOOKING_USER_ID = "user_id";
    private static final String ROOM_TYPE = "room_type";
    private static final String CHECK_IN_DATE = "check_in_date";
    private static final String CHECK_OUT_DATE = "check_out_date";

    // Services Table Columns
    private static final String SERVICE_ID = "id";
    private static final String SERVICE_USER_ID = "user_id";
    private static final String SERVICE_NAME = "service_name";
    private static final String SERVICE_DATE = "service_date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " ("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT, "
                + USER_EMAIL + " TEXT UNIQUE, "
                + USER_PASSWORD + " TEXT)");

        // Create Bookings Table
        db.execSQL("CREATE TABLE " + TABLE_BOOKINGS + " ("
                + BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BOOKING_USER_ID + " INTEGER, "
                + ROOM_TYPE + " TEXT, "
                + CHECK_IN_DATE + " TEXT, "
                + CHECK_OUT_DATE + " TEXT)");

        // Create Services Table
        db.execSQL("CREATE TABLE " + TABLE_SERVICES + " ("
                + SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SERVICE_USER_ID + " INTEGER, "
                + SERVICE_NAME + " TEXT, "
                + SERVICE_DATE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICES);
        onCreate(db);
    }

    // User Operations
    public boolean registerUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, name);
        values.put(USER_EMAIL, email);
        values.put(USER_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null,
                USER_EMAIL + "=? AND " + USER_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        boolean loggedIn = cursor.getCount() > 0;
        cursor.close();
        return loggedIn;
    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USERS, null, USER_EMAIL + "=?", new String[]{email}, null, null, null);
    }

    public boolean updateUser(int userId, String updatedName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, updatedName);
        int rowsUpdated = db.update(TABLE_USERS, values, USER_ID + " = ?", new String[]{String.valueOf(userId)});
        return rowsUpdated > 0;
    }

    // Booking Operations
    public boolean addBooking(int userId, String roomType, String checkIn, String checkOut) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOKING_USER_ID, userId);
        values.put(ROOM_TYPE, roomType);
        values.put(CHECK_IN_DATE, checkIn);
        values.put(CHECK_OUT_DATE, checkOut);

        long result = db.insert(TABLE_BOOKINGS, null, values);
        return result != -1;
    }

    public Cursor getUserBookings(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_BOOKINGS, null, BOOKING_USER_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);
    }

    public boolean deleteBooking(int bookingId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_BOOKINGS, BOOKING_ID + " = ?", new String[]{String.valueOf(bookingId)});
        return rowsAffected > 0;
    }

    // Service Operations
    public boolean bookService(int userId, String serviceName, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SERVICE_USER_ID, userId);
        values.put(SERVICE_NAME, serviceName);
        values.put(SERVICE_DATE, date);

        long result = db.insert(TABLE_SERVICES, null, values);
        return result != -1;
    }

    public Cursor getServicesByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_SERVICES, null, SERVICE_USER_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);
    }

    public boolean deleteService(int serviceId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_SERVICES, SERVICE_ID + " = ?", new String[]{String.valueOf(serviceId)});
        return rowsAffected > 0;
    }
}
