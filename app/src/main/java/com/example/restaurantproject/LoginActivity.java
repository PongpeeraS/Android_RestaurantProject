package com.example.restaurantproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/*Activity for users to log in into the system. This is the starting activity.*/
public class LoginActivity extends AppCompatActivity {
    private Button btnSignup, btnLogin;
    private EditText inputEmail, inputPassword;
    private RelativeLayout layoutOverlay;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.email_login);
        inputPassword = findViewById(R.id.password_login);
        auth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup);
        layoutOverlay = findViewById(R.id.layout_overlay);

        //Clicking the signup button will create a new SignupActivity
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });

        /* Listener for the login button, authenticating the user via Firebase
           After pressing the button, an overlay with a ProgressBar will appear while
           authenticating with Firebase and will be hidden once the process is complete.
         */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutOverlay.setVisibility(View.VISIBLE);
                String mail = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                if (TextUtils.isEmpty(mail)) { //No email input -> Toast to user
                    Toast.makeText(getApplicationContext(), getString(R.string.empty_email), Toast.LENGTH_SHORT).show();
                    layoutOverlay.setVisibility(View.INVISIBLE);
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
                                        layoutOverlay.setVisibility(View.INVISIBLE);
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        layoutOverlay.setVisibility(View.INVISIBLE);
                                    }
                                } else { //Redirect to MainActivity after logging in
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    layoutOverlay.setVisibility(View.INVISIBLE);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

}