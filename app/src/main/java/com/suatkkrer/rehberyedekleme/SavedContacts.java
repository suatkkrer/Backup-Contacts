package com.suatkkrer.rehberyedekleme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavedContacts extends AppCompatActivity {

    ListView listView;
    private FirebaseAuth mAuthorize;
    private DatabaseReference userReference;
    String validUser;
    String contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_contacts);

        listView = findViewById(R.id.savedContactsList);
        mAuthorize = FirebaseAuth.getInstance();
        userReference = FirebaseDatabase.getInstance().getReference();
        validUser = mAuthorize.getCurrentUser().getUid();



        userReference.child("Users").child(validUser)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists() && snapshot.hasChild("contact")){

                            contacts = snapshot.child("contact").getValue().toString();
                            System.out.println(contacts);

                            List<String> al = new ArrayList<>(Arrays.asList(contacts.split(",")));

                            ArrayAdapter arrayAdapter = new ArrayAdapter(SavedContacts.this,R.layout.list_item_2,al);
                            listView.setAdapter(arrayAdapter);

                        } else {
                            Toast.makeText(SavedContacts.this, R.string.savedConntact, Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}