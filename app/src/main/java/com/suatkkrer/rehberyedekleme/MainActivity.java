package com.suatkkrer.rehberyedekleme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText emailText, passwordText;

    private FirebaseAuth mAuth;

    ProgressDialog loginDialog;

    private FirebaseUser presentuser;
    private DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = findViewById(R.id.login_mail);
        passwordText = findViewById(R.id.login_password);
        mAuth = FirebaseAuth.getInstance();
        presentuser = mAuth.getCurrentUser();
        userReference = FirebaseDatabase.getInstance().getReference();

        loginDialog = new ProgressDialog(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CONTACTS},1);
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_CONTACTS},7);
        }
    }

    public void SignIn(View view) {

        Intent intent = new Intent(getApplicationContext(),SignUp.class);
        startActivity(intent);

    }

    public void Login(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            Snackbar.make(view, R.string.permission_required,Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.give_permission, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_CONTACTS)){
                                ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_CONTACTS},3);
                            } else {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",MainActivity.this.getPackageName(),null);
                                intent.setData(uri);
                                MainActivity.this.startActivity(intent);
                            }
                        }
                    }).show();
            } else {
             AuthorizetoLogin();
            }
        }

    private void AuthorizetoLogin()

    {
        String email= emailText.getText().toString();
        String password= passwordText.getText().toString();

        if(TextUtils.isEmpty(email))

        {
            Toast.makeText(MainActivity.this, R.string.email_cannot_be_empty2, Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password))

        {
            Toast.makeText(MainActivity.this, R.string.password_cannot_be_empty2, Toast.LENGTH_SHORT).show();

        }
        else
        {

            //Progress
            loginDialog.setTitle(getString(R.string.logging_your_account));
            loginDialog.setMessage(getString(R.string.please_wait2));
            loginDialog.setCanceledOnTouchOutside(true);
            loginDialog.show();

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                Intent mainPage = new Intent(MainActivity.this,ContactList.class);
                                mainPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainPage);
                                finish();
                                Toast.makeText(MainActivity.this, R.string.login_successful, Toast.LENGTH_SHORT).show();
                                loginDialog.dismiss();

                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(MainActivity.this, getString(R.string.error2)+message+ getString(R.string.check_your_information2), Toast.LENGTH_SHORT).show();
                                loginDialog.dismiss();
                            }

                        }
                    });

         }
      }

    public void ForgotPassword(View view) {
        Intent intent = new Intent(getApplicationContext(),ForgotPassword.class);
        startActivity(intent);
    }
}
