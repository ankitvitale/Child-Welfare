package com.example.hp.animalwelfare123;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Home extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    FirebaseAuth auth;

    public static final int RequestPermissionCode = 1;
    protected GoogleApiClient googleApiClient;
    private FusedLocationProviderClient fusedLocationProviderClient;

    String latetude;
    String longitude ;
    String data;

    RecyclerView recyclerView;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference().child("childData");
    }

    public void SignOut(View view) {

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient signInClient = GoogleSignIn.getClient(getApplicationContext(), signInOptions);

        signInClient.revokeAccess().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), mAuth.getCurrentUser().getDisplayName()+" logged out successfully.", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Unable to logout!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    public void Injured_Animal(View view) {

        Intent intent= new Intent(getApplicationContext() ,InjuredAnimal.class);
        intent.putExtra("latitude",data);
        startActivity(intent);
    }

    public void Sell_Animal(View view) {
        Intent intent = new Intent(Home.this,SellingAnimal.class);
        startActivity(intent);
    }

    public void Doctor_List(View view) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=child ngo");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    protected void onStop() {
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        super.onStop();
    }


    @Override
    protected void onStart() {
        super.onStart();

        googleApiClient.connect();

        FirebaseRecyclerAdapter<Task, TaskViewHolder> adapter =
                new FirebaseRecyclerAdapter<Task, TaskViewHolder>(
                        Task.class,
                        R.layout.layout_design,
                        TaskViewHolder.class,
                        reference) {
                    @Override
                    protected void populateViewHolder(TaskViewHolder viewHolder, Task model, int position) {

                        viewHolder.setName(model.getName());
                        viewHolder.setLocation(model.getLocation());
                        viewHolder.setMobile(model.getMobile());
                        viewHolder.setImage(model.getImage());

                        final String Task_Key = getRef(position).getKey().toString();

                        viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(getApplicationContext(), FullDataInformation.class);
                                intent.putExtra("Key", Task_Key);
                                startActivity(intent);
                            }
                        });

                    }
                };
        recyclerView.setAdapter(adapter);
    }



    public static class TaskViewHolder extends RecyclerView.ViewHolder
    {
        View mview;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setName(String name) {
            TextView name12 = (TextView)mview.findViewById(R.id.name222);
            name12.setText(name);
        }
        public void setLocation(String location) {
            TextView location12 = (TextView)mview.findViewById(R.id.location222);
            location12.setText(location);

        }
        public void setMobile(String mobile) {
            TextView mobile12 = (TextView)mview.findViewById(R.id.mobile222);
            mobile12.setText(mobile);
        }
        public void setImage(String image) {
            ImageView image12 = (ImageView)mview.findViewById(R.id.image222);
            Picasso.get().load(image).into(image12);
        }
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object

                                latetude = String.valueOf(location.getLatitude());
                                longitude = String.valueOf(location.getLongitude());

                                data = latetude+","+longitude;

                            }
                        }
                    });
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(Home.this, new
                String[]{ACCESS_FINE_LOCATION}, RequestPermissionCode);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("MainActivity", "Connection failed: " + connectionResult.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("MainActivity", "Connection suspendedd");
    }

}

