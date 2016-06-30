package com.a1996.ben.collectivecookbook.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.a1996.ben.collectivecookbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    ProgressBar mProgressBar;
    Button mLoginButton;
    EditText mEmail;
    Button mRegister;
    EditText mPassword;
    boolean wasVisible = false;

    private void setProgressVisibility(boolean wasVisible) {
        if(wasVisible) {
            mProgressBar.setVisibility(View.VISIBLE);
            mLoginButton.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            wasVisible = savedInstanceState.getBoolean("was_visible");
        }
        setContentView(R.layout.activity_login);
        mProgressBar = (ProgressBar) findViewById(R.id.loginProgress);
        mRegister = (Button) findViewById(R.id.goToRegister);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mEmail = (EditText) findViewById(R.id.loginEmail);
        mPassword = (EditText) findViewById(R.id.loginPassword);
        setProgressVisibility(wasVisible);
        mAuth = FirebaseAuth.getInstance();
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wasVisible = true;
                setProgressVisibility(wasVisible);
                if(mEmail.getText().toString() != null && mPassword.getText().toString() != null) {
                    try {
                        mAuth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        mLoginButton.setVisibility(View.VISIBLE);
                                        mProgressBar.setVisibility(View.INVISIBLE);
                                        wasVisible = false;
                                        Log.i("Login activity", "signInWithEmail:onComplete:" + task.isSuccessful());

                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            Log.w("login Activity", "signInWithEmail", task.getException());
                                            Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    } catch (Exception e){
                        mLoginButton.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.INVISIBLE);
                        wasVisible = false;
                        }
                }
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.i("User login", "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.i("User Login", "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("was_visible", wasVisible);
        super.onSaveInstanceState(savedInstanceState);
    }
}
