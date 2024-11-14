package tn.esprit.userinterface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText email_input, password_input;

    Button login_button, register_button;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Initialize the database and add a user
        Database db = new Database(this);

        // Add an admin
        db.addUser("manel", "admin@gmail.com", "12345");
        db.addUser("manel2", "admin2@gmail.com", "12345");
        db.addReservation("12345", "9876543210", "150 TND", "Tesla Model 3", "2024-11-15");
        db.addReservation("12345", "9876543210", "200 TND", "clio Model 3", "2024-11-15");
        db.addReservation("12345", "9876543210", "300 TND", "MG ", "2024-11-15");
        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.Password_input);
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
        //register_button = findViewById(R.id.register_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database mydb = new Database(MainActivity.this);
                mydb.checkUserCredentials(email_input.getText().toString(), password_input.getText().toString());
            }
        });



    }
}