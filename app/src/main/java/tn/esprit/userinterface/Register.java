package tn.esprit.userinterface;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    EditText confirm_password_input, Password_input, email_input, username_input;
    Button signup_button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email_input = findViewById(R.id.email_input);
        Password_input = findViewById(R.id.Password_input);
        confirm_password_input = findViewById(R.id.confirm_password_input);
        username_input = findViewById(R.id.username_input);
        signup_button = findViewById(R.id.signup_button);

        Database db = new Database(this);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = Password_input.getText().toString();
                String confirmPassword = confirm_password_input.getText().toString();

                if (password.equals(confirmPassword)) {
                    db.addUser(username_input.getText().toString(), email_input.getText().toString(), password);

                    Toast.makeText(Register.this, "User added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
