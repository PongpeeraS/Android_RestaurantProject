package com.example.restaurantproject;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private Button btnSignup, btnLogin;
    private EditText inputEmail, inputPassword;
    private ProgressBar pBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.email2);
        inputPassword = findViewById(R.id.editText6);
        auth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup);
        pBar = findViewById(R.id.login_pbar);

        //Clicking the signup button will create a new SignupActivity
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });

        //Listener for the login button, authenticating the user via Firebase
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pBar.setVisibility(View.VISIBLE);
                String mail = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                if (TextUtils.isEmpty(mail)) { //No email input -> Toast to user
                    Toast.makeText(getApplicationContext(), getString(R.string.empty_email), Toast.LENGTH_SHORT).show();
                    pBar.setVisibility(View.GONE);
                    return;
                }
                //Authentication, listens on completion whether the login fails or succeeds
                auth.signInWithEmailAndPassword(mail, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Display the warning if login fails
                                if (!task.isSuccessful()) {
                                    // There was an error: password too chore or other auth issues
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                        pBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        pBar.setVisibility(View.GONE);
                                    }
                                } else { //Redirect to MainActivity after logging in
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    pBar.setVisibility(View.GONE);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*Setting the activity's locale through saved SharedPreference*/
        SharedPreferences sharedPref = this.getSharedPreferences("selectedLanguage", Context.MODE_PRIVATE);
        String languageToLoad = sharedPref.getString("language", "");
        Locale locale = new Locale(languageToLoad);//Set Selected Locale
        Locale.setDefault(locale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = locale;//set config locale as selected locale
        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
    }
}