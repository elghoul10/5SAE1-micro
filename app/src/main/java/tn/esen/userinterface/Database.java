package tn.esen.userinterface;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME ="ReservationSite.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="Users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME="user_name";
    private static final String COLUMN_EMAIL="user_email";
    private static final String COLUMN_PASSWORD="user_password";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    // Add User to Database
    void addUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username); // Use 'username' for COLUMN_USERNAME
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password); // Use 'password' for COLUMN_PASSWORD

        long result = db.insert(TABLE_NAME, null, cv);
        db.close(); // Close the database after insertion

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(context, UsersList.class);
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
}
