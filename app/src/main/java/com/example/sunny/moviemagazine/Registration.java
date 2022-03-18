package com.example.sunny.moviemagazine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    //Button register;
    AdView mAdView;
    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAdView=findViewById(R.id.banner);
        progressBar = findViewById(R.id.progressBar);
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-6336523906622902~6817819328");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("36A42FC6E1626423A544631795888788")
                .build();
        mAdView.loadAd(adRequest);

        inputEmail = findViewById(R.id.id_emailregister);
        inputPassword = findViewById(R.id.id_passwordregister);
        //btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.id_buttonRegister);
        auth = FirebaseAuth.getInstance();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  String email = inputEmail.getText().toString().trim();
                Log.i("email",inputEmail.getText().toString().trim());
                String password = inputPassword.getText().toString().trim();
                Log.i("email",inputPassword.getText().toString().trim());*/
                if (TextUtils.isEmpty(inputEmail.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), R.string.email, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(inputPassword.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), R.string.password, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (inputPassword.getText().toString().trim().length() < 6) {
                    Toast.makeText(getApplicationContext(), R.string.maxlengthpassword, Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim())
                        .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Registration.this, getString(R.string.createremail_reg) + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btnSignUp.setTextColor(getResources().getColor(R.color.green));
                                btnSignUp.setText(R.string.reg);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Registration.this, getString(R.string.athentii_reg) + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(Registration.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
    public void clickAlreadyRegistred(View view) {
        startActivity(new Intent(this,Login.class));
    }
}
