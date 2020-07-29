package com.suatkkrer.rehberyedekleme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    Button sendmailButton;
    EditText mailText;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setTitle(getString(R.string.forgotPassword));
        setContentView(R.layout.activity_forgot_password);

        sendmailButton = findViewById(R.id.sendEmailButton);
        mailText = findViewById(R.id.mailText);

        mAuth = FirebaseAuth.getInstance();


    }

    public void sendPassword(View view) {

        mAuth.sendPasswordResetEmail(mailText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Password send to your email", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}