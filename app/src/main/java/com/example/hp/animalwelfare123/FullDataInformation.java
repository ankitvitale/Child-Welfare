package com.example.hp.animalwelfare123;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class FullDataInformation extends AppCompatActivity {

    DatabaseReference reference;
    String Key12 = null;
    TextView name11, mobile11, address11;
    ImageView imageView;
    String mobileGet;
    String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_data_information);

        imageView = (ImageView) findViewById(R.id.display_image);
        name11 = (TextView) findViewById(R.id.display_name);
        mobile11 = (TextView) findViewById(R.id.display_mobile);
        address11 = (TextView) findViewById(R.id.display_address);

        reference = FirebaseDatabase.getInstance().getReference().child("childData");

        Key12 = getIntent().getExtras().getString("Key");
        reference.child(Key12).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String nameGet = (String) dataSnapshot.child("name").getValue();
                mobileGet = (String) dataSnapshot.child("mobile").getValue();
                String addressGet = (String) dataSnapshot.child("location").getValue();
                 latitude = (String) dataSnapshot.child("map").getValue();
                String imageGet = (String) dataSnapshot.child("image").getValue();


                name11.setText("Hey "+nameGet);
                mobile11.setText("Mobile : "+mobileGet);
                address11.setText("Address : "+addressGet);
                Picasso.get().load(imageGet).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void map(View view) {

        String packageName = "com.google.android.apps.maps";
        String query = "google.navigation:q="+latitude;

        Intent intent = this.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(query));
        startActivity(intent);
        }
    }

















