package com.example.testing;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testing.databinding.ActivityMain2Binding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity2 extends AppCompatActivity {
    ADAPTERCLASS adapterclass;
    ActivityMain2Binding binding;
    int maxid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(layoutManager);

        Query query= FirebaseDatabase.getInstance().getReference().child("School");

        FirebaseRecyclerOptions<MODELCLASS> firebaseRecyclerOptions=
                new FirebaseRecyclerOptions.Builder<MODELCLASS>().setQuery(query,MODELCLASS.class).build();

        adapterclass=new ADAPTERCLASS(firebaseRecyclerOptions);
        binding.recycler.setAdapter(adapterclass);

    }
    @Override
    protected void onStart(){
        super.onStart();
        adapterclass.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        adapterclass.stopListening();
    }
}