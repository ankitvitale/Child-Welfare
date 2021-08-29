package com.example.hp.animalwelfare123;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class InjuredAnimal extends AppCompatActivity {

    EditText name, location, mob;
    ImageView imageView;
    Uri uri = null;
    ProgressDialog dialog;
    StorageReference storageReference;
    DatabaseReference reference;

    String latitude_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injured_animal);

        dialog = new ProgressDialog(this);

        Intent intent = getIntent();
        latitude_data = intent.getStringExtra("latitude");

        imageView = (ImageView) findViewById(R.id.image123);
        name = (EditText) findViewById(R.id.oName);
        location = (EditText) findViewById(R.id.pName);
        mob = (EditText) findViewById(R.id.oMob);

        storageReference = FirebaseStorage.getInstance().getReference().child("item");
        reference = FirebaseDatabase.getInstance().getReference().child("childData");


    }

    public void SelectImage(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK) ;
        {
            try {
                uri = data.getData();
                imageView.setImageURI(uri);
            } catch (Exception e) {

            }
        }
    }

    public void Submit(View view) {

        dialog.setTitle("Data uploading");
        dialog.show();
        StorageReference filepath = storageReference.child(uri.getLastPathSegment());
        filepath.putFile(uri).addOnSuccessListener(InjuredAnimal.this,
                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Uri download_url = taskSnapshot.getDownloadUrl();

                        String name12 = name.getText().toString();
                        String location12 = location.getText().toString();
                        String mobile12 = mob.getText().toString();

                        HashMap<String, String> map = new HashMap<>();
                        map.put("name", name12);
                        map.put("location", location12);
                        map.put("mobile", mobile12);
                        map.put("image", String.valueOf(download_url));
                        map.put("map", latitude_data);
                        reference.push().setValue(map);

                        Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent intent = new Intent(InjuredAnimal.this, Home.class);
                        startActivity(intent);
                    }
                });

    }
}
