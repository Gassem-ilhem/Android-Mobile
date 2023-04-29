package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;


public class JouetActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ToyAdapter toyAdapter;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jouet);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<ToyModel> options =
                new FirebaseRecyclerOptions.Builder<ToyModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), ToyModel.class)
                        .build();

        toyAdapter = new ToyAdapter(options);
        recyclerView.setAdapter(toyAdapter);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        toyAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        toyAdapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item= menu.findItem(R.id.search);
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str){
        FirebaseRecyclerOptions<ToyModel> options =
                new FirebaseRecyclerOptions.Builder<ToyModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products").orderByChild("name").startAt(str).endAt(str+"-"), ToyModel.class)
                        .build();
        toyAdapter = new ToyAdapter(options);
        toyAdapter.startListening();
        recyclerView.setAdapter(toyAdapter);

    }
}