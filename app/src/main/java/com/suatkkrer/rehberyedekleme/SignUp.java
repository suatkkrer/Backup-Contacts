package com.suatkkrer.rehberyedekleme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText registerMail, registerPassword;
    FirebaseAuth mAuth;
    DatabaseReference rootReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        registerMail = findViewById(R.id.register_mail);
        registerPassword = findViewById(R.id.register_password);

        mAuth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

    }

    public void haveAlreadyAccount(View view) {

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);

    }

    public void registerButton (View view){

            CreateNewAccount();

    }

    private void CreateNewAccount()
    {
        String email = registerMail.getText().toString();
        String password = registerPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(SignUp.this, R.string.email_cannot_be_empty, Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(SignUp.this, R.string.password_cannot_be_empty, Toast.LENGTH_SHORT).show();
        }

        else
        {
            progressDialog.setTitle(getString(R.string.new_account_is_creating));
            progressDialog.setMessage(getString(R.string.please_wait));
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();


            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())

                            {
                               String validUserId= mAuth.getCurrentUser().getUid();
                               rootReference.child("Users").child(validUserId).setValue("");


                                Intent mainPage = new Intent(SignUp.this,MainActivity.class);
                                mainPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                startActivity(mainPage);
                                finish();

                                Toast.makeText(SignUp.this, R.string.account_created_successfully, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(SignUp.this, getString(R.string.error)+ message+getString(R.string.check_your_information), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }
}