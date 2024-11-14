package tn.esen.userinterface;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class User_Row extends AppCompatActivity {
    Button delete_button, update_button;
    TextView user_id, username, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_row);

        // Initialize views
        user_id = findViewById(R.id.user_id);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);




    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get the actual text from user_id TextView
        String userIdText = user_id.getText().toString();

        // Set the title and message using the actual user ID value
        builder.setTitle("Delete " + userIdText + " ?");
        builder.setMessage("Are you sure you want to delete " + userIdText + " ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Pass the actual user ID to the delete method
                Database myDB = new Database(User_Row.this);
                try {
                    int userId = Integer.parseInt(userIdText); // Use the actual user ID
                    myDB.deleteUser(userId);
                    Log.d("User_Row", "User with ID " + userId + " deleted successfully");
                    finish();
                } catch (NumberFormatException e) {
                    Log.e("User_Row", "Failed to parse user ID for deletion", e);
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Close the dialog without doing anything
            }
        });

        builder.create().show();
    }
}
