package tn.esen.userinterface;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateUser extends AppCompatActivity {
    EditText confirm_password_input, Password_input, email_input, username_input;
    Button update_button;
    String id,username,email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_user);
        email_input = findViewById(R.id.email_input);
        Password_input = findViewById(R.id.Password_input);
        confirm_password_input = findViewById(R.id.confirm_password_input);
        username_input = findViewById(R.id.username_input);
        update_button = findViewById(R.id.update_button);
        getAndSetIntentData();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                Database myDB = new Database(UpdateUser.this);
                username = username_input.getText().toString().trim();
                email = email_input.getText().toString().trim();
                password = Password_input.getText().toString().trim();
                myDB.updateUser(Integer.parseInt(id), username, email, password);
            }
        });
    }



    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("username") &&
                getIntent().hasExtra("email") ){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            username = getIntent().getStringExtra("username");
            email = getIntent().getStringExtra("email");

            //Setting Intent Data
            username_input.setText(username);
            email_input.setText(email);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

}