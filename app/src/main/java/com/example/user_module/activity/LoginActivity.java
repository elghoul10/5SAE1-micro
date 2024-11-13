package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user_module.AppDatabase;
import com.example.user_module.Dao.UserDao;
import com.example.user_module.R;
import com.example.user_module.entity.User;
import com.example.user_module.util.SessionManager;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;

import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView signUpTextView, forgotPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpTextView = findViewById(R.id.signUpTextView);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);

        loginButton.setOnClickListener(view -> verifyReCaptcha());
    }

    private void verifyReCaptcha() {
        SafetyNet.getClient(this).verifyWithRecaptcha("6LckL3gqAAAAAI-bbARSpuHCZY3Sq1hsiPcUCLW2")
                .addOnSuccessListener(this, response -> {
                    if (response.getTokenResult() != null && !response.getTokenResult().isEmpty()) {
                        // reCAPTCHA completed successfully, now validate the response token
                        validateCaptchaToken(response.getTokenResult());
                    }
                })
                .addOnFailureListener(this, e -> {
                    // Handle reCAPTCHA error
                    Toast.makeText(this, "reCAPTCHA failed. Please try again.", Toast.LENGTH_SHORT).show();
                });
    }

    private void validateCaptchaToken(String token) {
        // Use Volley to send the token to your server for verification
        String url = "https://www.google.com/recaptcha/api/siteverify";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {
                            // CAPTCHA verified, proceed with login
                            loginUser();
                        } else {
                            Toast.makeText(this, "CAPTCHA verification failed.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Verification error.", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("secret", "6LckL3gqAAAAAH5XOlL3MK6eiejGTkwMn04QfOlF");
                params.put("response", token);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loginUser() {
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "user_database").build();

        new Thread(() -> {
            UserDao userDao = db.userDao();
            User user = userDao.getUserByEmail(email);

            if (user != null && BCrypt.checkpw(password, user.password)) {
                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                    sessionManager.createLoginSession(String.valueOf(user.id));
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                });
            } else {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
