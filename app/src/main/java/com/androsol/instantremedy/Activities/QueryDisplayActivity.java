package com.androsol.instantremedy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.androsol.instantremedy.Adapters.QueryAdapter;
import com.androsol.instantremedy.Constants;
import com.androsol.instantremedy.Models.Query;
import com.androsol.instantremedy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QueryDisplayActivity extends AppCompatActivity {


    ArrayList<Query> queries;
    QueryAdapter adapter;
    RecyclerView recyclerView;
    DatabaseReference queryRef;
    Intent i;
    String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        i = getIntent();
        emailId = i.getStringExtra(Constants.USER_EMAIL);
        queryRef = FirebaseDatabase.getInstance().getReference("query");
        queries = new ArrayList<Query>();
        recyclerView = (RecyclerView) findViewById(R.id.query_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new QueryAdapter(queries,this, emailId);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpViews();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpViews(){
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                queries.clear();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Query q = data.getValue(Query.class);
                    queries.add(q);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
