package com.androsol.instantremedy.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androsol.instantremedy.Models.Query;
import com.androsol.instantremedy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QueryActivity extends AppCompatActivity {
    EditText uniqueSymp, otherSymp, optional;
    Button queryButton;
    DatabaseReference queryRef;
    Boolean check = true;
    Query q;
    Intent i;
    String bodyPartId, bodyPartName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        i = getIntent();
        bodyPartId = i.getStringExtra("BodyPartId");
        bodyPartName = i.getStringExtra("BodyPartName");
        queryRef = FirebaseDatabase.getInstance().getReference("query");
        queryButton = (Button) findViewById(R.id.query_go_button);
        uniqueSymp = (EditText) findViewById(R.id.unique_edittext);
        otherSymp = (EditText) findViewById(R.id.other_edittext);
        optional = (EditText) findViewById(R.id.optional_edittext);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uniqueSymp.getText()== null || otherSymp.getText() == null || uniqueSymp.getText().length() == 0 || otherSymp.getText().length() == 0){
                if(uniqueSymp.getText() == null || uniqueSymp.getText().length() == 0){
                    Snackbar.make(QueryActivity.this.findViewById(android.R.id.content),"Please add some symptom",Snackbar.LENGTH_LONG).show();
                }
                else if (otherSymp .getText() == null || otherSymp.getText().length() == 0){
                    Snackbar.make(QueryActivity.this.findViewById(android.R.id.content), "Please add some other symptoms so that we can know better!",
                            Snackbar.LENGTH_LONG).show();
                }
                }
                if(uniqueSymp.getText() != null && otherSymp.getText() != null && uniqueSymp.getText().length() != 0 && otherSymp.getText().length() != 0) {
                    final String uniqueSymptom = uniqueSymp.getText().toString();
                    queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot data : dataSnapshot.getChildren()){
                                if(data.getKey().equals(uniqueSymptom)){
                                    check = false;
                                    Log.d("DHRUV","Data already present");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(QueryActivity.this);
                                    builder.setMessage("The unique symptom you have added is either already present in the app," +
                                            "or the query is already being submitted by some user and is in process." +
                                            "If you feel that its not, then try adding some other symptom name...")
                                            .setPositiveButton("I got it!", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            })
                                            .create().show();
                                    break;
                                }
                            }
                            // query is unique

                            if(check) {
                                if(optional.getText() != null && optional.getText().length() != 0) {
                                    q = new Query(uniqueSymp.getText().toString(), otherSymp.getText().toString(),
                                            optional.getText().toString(),bodyPartId,bodyPartName);
                                }else{
                                    q = new Query(uniqueSymp.getText().toString(), otherSymp.getText().toString(),
                                            " ",bodyPartId,bodyPartName);
                                }
                                queryRef.child(uniqueSymp.getText().toString()).setValue(q);
                                AlertDialog.Builder builder = new AlertDialog.Builder(QueryActivity.this);
                                builder.setMessage(("Your query is submitted successfully and added to the query list." +
                                        "The remedy will be added if your problem seems appropriate to our health experts and they" +
                                        " get a solution for it. Please stay in touch by then !"))
                                        .setPositiveButton("Thank You!", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                onBackPressed();
                                            }
                                        })
                                        .create().show();
                                //back after submitting
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
