package com.example.hp.animalwelfare123;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Doctor_List extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__list);

        recyclerView = (RecyclerView)findViewById(R.id.listRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference().child("Adanimal");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<TaskDoctor, TaskViewHolder> adapter =
                new FirebaseRecyclerAdapter<TaskDoctor, TaskViewHolder>(
                        TaskDoctor.class,
                        R.layout.list_design,
                        TaskViewHolder.class,
                        reference) {
                    @Override
                    protected void populateViewHolder(TaskViewHolder viewHolder, TaskDoctor model, int position) {
                        viewHolder.setName(model.getName());
                        viewHolder.setAdd(model.getAdd());
                        viewHolder.setMobile(model.getMobile());
                        viewHolder.setImage(model.getImage());
                    }
                };
        recyclerView.setAdapter(adapter);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String dName) {
            TextView dname123 = (TextView) mView.findViewById(R.id.name111);
            dname123.setText(dName);
        }

        public void setAdd(String dAdd) {
            TextView dAdd123 = (TextView) mView.findViewById(R.id.add111);
            dAdd123.setText(dAdd);
        }

        public void setMobile(String dMob) {
            TextView dMob123 = (TextView) mView.findViewById(R.id.mobile111);
            dMob123.setText(dMob);
        }

        public void setImage(String dImage) {
            ImageView dImage123 = (ImageView) mView.findViewById(R.id.image111);
            Picasso.get().load(dImage).into(dImage123);
        }

    }
}
