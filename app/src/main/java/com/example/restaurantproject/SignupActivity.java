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

/*Activity to create an account to use with the application*/
public class SignupActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword, confirmPassword;
    private Button btnSignIn, btnSignUp;
    private RelativeLayout layoutOverlay;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.email_regis);
        inputPassword = findViewById(R.id.password_regis);
        confirmPassword = findViewById(R.id.confirm_password);
        layoutOverlay = findViewById(R.id.layout_overlay2);


        //Link to Login Page
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

        //On clicking 'Sign Up', the information from the fields will be used to create a new Firebase account
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutOverlay.setVisibility(View.VISIBLE);
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String conpass = confirmPassword.getText().toString().trim();
                //Registration Criteria
                //Check the email field is empty or not
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_noemail), Toast.LENGTH_SHORT).show();
                    layoutOverlay.setVisibility(View.INVISIBLE);
                    return;
                }
                //Check the password field is empty or not
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_nopassword), Toast.LENGTH_SHORT).show();
                    layoutOverlay.setVisibility(View.INVISIBLE);
                    return;
                }
                //Check the minimum of password
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), getString(R.string.minimum_password), Toast.LENGTH_SHORT).show();
                    layoutOverlay.setVisibility(View.INVISIBLE);
                    return;
                }
                //Check Confirmation
                if(!conpass.equals(password)){
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_wrongconpass), Toast.LENGTH_SHORT).show();
                    layoutOverlay.setVisibility(View.INVISIBLE);
                    return;
                }
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Display the warning if sign up is fail
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, getString(R.string.toast_authfail)
                                                    + task.getException(), Toast.LENGTH_SHORT).show();
                                    layoutOverlay.setVisibility(View.INVISIBLE);
                                } else {
                                    Toast.makeText(SignupActivity.this, getString(R.string.toast_authpass),
                                            Toast.LENGTH_SHORT).show();
                                    layoutOverlay.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

}