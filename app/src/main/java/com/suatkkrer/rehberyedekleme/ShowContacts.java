package com.suatkkrer.rehberyedekleme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.firebase.auth.FirebaseAuth;

public class ShowContacts extends AppCompatActivity {

    ListView contactList;
    private FirebaseAuth mAuthorize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contacts);

        contactList = findViewById(R.id.contactList);
        mAuthorize = FirebaseAuth.getInstance();

        if (ContextCompat.checkSelfPermission(ShowContacts.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
            startManagingCursor(cursor);

            String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};

            int[] to = {R.id.textnew, R.id.textnew2};


            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor, from, to);
            contactList.setAdapter(simpleCursorAdapter);
            contactList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.example_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.aktarma) ;
        {
            mAuthorize.signOut();
            Intent intent = new Intent(ShowContacts.this,MainActivity.class);
            startActivity(intent);
        }
        return true;
    }
}