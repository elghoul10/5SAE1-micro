package tn.esprit.userinterface;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class Database extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME ="ReservationSite.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="Users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME="user_name";
    private static final String COLUMN_EMAIL="user_email";
    private static final String COLUMN_PASSWORD="user_password";
    private static final String TABLE_NAME2="Reservations";
    private static final String COLUMN_IDRes = "_id";
    private static final String COLUMN_ClientId="ClientId";
    private static final String COLUMN_tel="tel";
    private static final String COLUMN_Price="Price";
    private static final String COLUMN_Vehicule="Vehicule";
    private static final String COLUMN_DateRes="DateRes";
    private static final String TAG = "UsersListActivity";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT);";
        sqLiteDatabase.execSQL(create_query);

        String create_reservationTable_query = "CREATE TABLE " + TABLE_NAME2 + "(" +
                COLUMN_IDRes + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ClientId + " TEXT, " +
                COLUMN_tel + " TEXT, " +
                COLUMN_Price + " TEXT, " +
                COLUMN_Vehicule + " TEXT, " +
                COLUMN_DateRes + " TEXT" +
                ");";
        sqLiteDatabase.execSQL(create_reservationTable_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(sqLiteDatabase);
    }



    // Add User to Database
    void addUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_NAME, null, cv);
        db.close(); // Close the database after insertion

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }
    void addReservation(String clientId, String tel, String price, String vehicule, String dateRes) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ClientId, clientId);
            contentValues.put(COLUMN_tel, tel);
            contentValues.put(COLUMN_Price, price);
            contentValues.put(COLUMN_Vehicule, vehicule);
            contentValues.put(COLUMN_DateRes, dateRes);

            Log.d("Database", "Inserting reservation: " + clientId + ", " + tel + ", " + price + ", " + vehicule + ", " + dateRes);

            long result = db.insert(TABLE_NAME2, null, contentValues);
            if (result == -1) {
                Log.e("Database", "Failed to add reservation");
                Toast.makeText(context, "Failed to add reservation", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Reservation added successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("Database", "Error occurred while inserting reservation", e);
            Toast.makeText(context, "Error occurred while adding reservation", Toast.LENGTH_SHORT).show();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }



    // Check User Credentials
    public void checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close(); // Close the database after query

        if (exists) {
            Toast.makeText(context, "User found", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close(); // Close the database after operation

        if (result == -1) {
            Toast.makeText(context, "Failed to delete user", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "User deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateUser(int id, String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);

        long result = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close(); // Close the database after operation

        if (result == -1) {
            Toast.makeText(context, "Failed to update user", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "User updated successfully", Toast.LENGTH_SHORT).show();
        }
    }


    Cursor getAllUsers(){
        String readAll ="SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =null;
        if(db!=null){
            cursor = db.rawQuery(readAll,null);

        }
        return cursor;
    }

    Cursor getAllReservations(){
        String readAll ="SELECT * FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =null;
        if(db!=null){
            cursor = db.rawQuery(readAll,null);

        }
        return cursor;
    }
}
