package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button submit;
    DatabaseReference rootRef, demoRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = findViewById(R.id.etValue);
        submit = findViewById(R.id.btnSubmit);

        // Database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().getReference();
        // Database reference pointing to demo node
        demoRef = rootRef.child("demo");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editText.getText().toString();
                // Push creates a unique id in database

               // demoRef.setValue(value);

               // String name=minsert.getText().toString();
                String id = demoRef.push().getKey();
                demoRef.child(id).push().setValue(value);
                Toast.makeText(getApplicationContext(), "Data Store Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        Button fetchButton = findViewById(R.id.btnFetch);
        final TextView fetchedText = findViewById(R.id.tvValue);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demoRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                        String newPost = dataSnapshot.getValue(String.class);
                        System.out.println("Author: " + newPost.author);
                        System.out.println("Title: " + newPost.title);
                        System.out.println("Previous Post ID: " + prevChildKey);

//                demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        String value = dataSnapshot.getValue(String.class);
//                        fetchedText.setText(value);
//                    }

//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_LONG).show();
//                    }
                });
            }
        });
    }
}
