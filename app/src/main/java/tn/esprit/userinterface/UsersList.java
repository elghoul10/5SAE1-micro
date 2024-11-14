package tn.esprit.userinterface;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersList extends AppCompatActivity {
    RecyclerView userslist;

    Database bd;

    ArrayList<String> user_id,username,email,password;
    private static final String TAG = "UsersListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(TAG, "connection");
        bd = new Database(this);
        user_id = new ArrayList<>();
        username = new ArrayList<>();
        email = new ArrayList<>();

        Log.d(TAG, "RecyclerView  initialized");

        userslist = findViewById(R.id.userslist); // Make sure the RecyclerView is initialized
        Log.d(TAG, "DisplayDataInRecycleView");

        DisplayDataInRecycleView();

        Log.d(TAG, "User ID list size: " + user_id.size());
        Log.d(TAG, "Username list size: " + username.size());
        Log.d(TAG, "Email list size: " + email.size());

        // Set up RecyclerView
        usersAdapter adapter = new usersAdapter(UsersList.this,this,user_id, username, email); // Pass the data to the adapter
        userslist.setLayoutManager(new LinearLayoutManager(this)); // Use LinearLayoutManager for vertical list
        userslist.setAdapter(adapter);
    }
    void DisplayDataInRecycleView(){

        Log.d(TAG, "start get");

        Cursor cursor = bd.getAllUsers();

        Log.d(TAG, "end get");

        if(cursor.getCount() == 0){

            Toast.makeText(this,"No user found !",Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d(TAG, "start while");
            Log.d(TAG, "Total users found: " + cursor.getCount());

            while(cursor.moveToNext()){
                user_id.add(cursor.getString(0));
                username.add(cursor.getString(1));
                email.add(cursor.getString(2));

            }
            Log.d(TAG, "end while");

        }
    }
}