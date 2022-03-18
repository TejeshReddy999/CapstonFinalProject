package com.example.sunny.moviemagazine;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnReset;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        btnReset = findViewById(R.id.id_button_Resetpassword);
        inputEmail = (EditText) findViewById(R.id.id_emailforget);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
    }

    @SuppressLint("ResourceAsColor")
    public void clickToReturn(View view) {

        startActivity(new Intent(this, Login.class));
        finish();
    }

    public void clciktoreset(View view) {


        String email = inputEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplication(), R.string.requiredmailid_ferpass, Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPassword.this, R.string.inst_resetpass_forpass, Toast.LENGTH_SHORT).show();
                            btnReset.setTextColor(getResources().getColor(R.color.green));
                            btnReset.setText(R.string.please_check_ur_password);
                        } else {
                            Toast.makeText(ForgetPassword.this, R.string.fail_reste_pass_forpass, Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}