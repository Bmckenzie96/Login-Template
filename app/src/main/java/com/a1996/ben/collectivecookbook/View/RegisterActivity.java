package com.a1996.ben.collectivecookbook.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a1996.ben.collectivecookbook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    EditText mEmail;
    EditText mPassword;
    EditText mConfirmPassword;
    Button mRegButton;
    ProgressBar mRegProgressBar;
    TextView mPasswordMatch;
    boolean wasVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            wasVisible = savedInstanceState.getBoolean("was_visible");
        }
        mAuth = FirebaseAuth.getInstance();
        mEmail = (EditText) findViewById(R.id.regEmail);
        mPassword = (EditText) findViewById(R.id.regPassword);
        mConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        mRegButton = (Button) findViewById(R.id.regButton);
        mRegProgressBar = (ProgressBar) findViewById(R.id.regProgress);
        mPasswordMatch = (TextView) findViewById(R.id.passwordMatch);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.i("User regis", "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.i("User regis", "onAuthStateChanged:signed_out");
                }
            }
        };
        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegButton.setVisibility(View.INVISIBLE);
                mRegProgressBar.setVisibility(View.VISIBLE);
                wasVisible = true;
                try {
                    mAuth.createUserWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.i("register", "createUserWithEmail:onComplete:" + task.isSuccessful());
                                    mRegButton.setVisibility(View.VISIBLE);
                                    mRegProgressBar.setVisibility(View.INVISIBLE);
                                    wasVisible = false;
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } catch (Exception e) {
                    mRegButton.setVisibility(View.VISIBLE);
                    mRegProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(RegisterActivity.this, "Fields must not be empty", Toast.LENGTH_LONG);
                }

            }
        });
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
                    mRegButton.setVisibility(View.INVISIBLE);
                    mPasswordMatch.setVisibility(View.VISIBLE);
                }
                else {
                    mRegButton.setVisibility(View.VISIBLE);
                    mPasswordMatch.setVisibility(View.INVISIBLE);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
                    mRegButton.setVisibility(View.INVISIBLE);
                    mPasswordMatch.setVisibility(View.VISIBLE);
                }
                else {
                    mRegButton.setVisibility(View.VISIBLE);
                    mPasswordMatch.setVisibility(View.INVISIBLE);
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
