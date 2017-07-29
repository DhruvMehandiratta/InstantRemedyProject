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
import android.widget.TextView;
import android.widget.Toast;

import com.androsol.instantremedy.Models.BodyPart;
import com.androsol.instantremedy.Models.Symptom;
import com.androsol.instantremedy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubmitRemedyActivity extends AppCompatActivity {

    TextView doctorSympText, doctorOtherSympText;
    Intent intent;
    String sympAns, otherAns, bodyId;
    EditText descDoc, remedyDoc;
    Button submitButton;
    DatabaseReference bodyPartRef, symRef, queryRef;
    BodyPart updateHelperBodyPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_remedy);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bodyPartRef = FirebaseDatabase.getInstance().getReference("bodyparts");
        symRef = bodyPartRef.child("symptoms");
        queryRef = FirebaseDatabase.getInstance().getReference("query");

        doctorOtherSympText = (TextView) findViewById(R.id.doctor_other_symptom_text);
        doctorSympText = (TextView) findViewById(R.id.doctor_symptom_name_text);
        intent = getIntent();
        sympAns = intent.getStringExtra("QuerySymptom");
        otherAns = intent.getStringExtra("QueryOther");
        bodyId = intent.getStringExtra("QueryBodyId");
        doctorSympText.setText(sympAns);
        doctorOtherSympText.setText(otherAns);
        descDoc = (EditText) findViewById(R.id.doctor_description);
        remedyDoc = (EditText) findViewById(R.id.doctor_remedy);
        submitButton = (Button)findViewById(R.id.doctor_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(descDoc.getText().length() == 0 || remedyDoc.getText().length() == 0){
                    Snackbar.make(SubmitRemedyActivity.this.findViewById(android.R.id.content),"Please fill in the spaces properly!",Snackbar.LENGTH_LONG).show();
                    return;
                }
                // symptoms mein add karna hai
                Toast.makeText(SubmitRemedyActivity.this, "Please wait...",Toast.LENGTH_SHORT).show();



                bodyPartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren()){
                            if(data.getKey().equals(bodyId)){
                                Log.d("DHRUV","ids equals");
                                updateHelperBodyPart = data.getValue(BodyPart.class);
                                break;
                            }
                        }
                        String bodyPartId = updateHelperBodyPart.getId();
                        String bodyPartName = updateHelperBodyPart.getName();
                        ArrayList<Symptom> bodyPartSymptoms = updateHelperBodyPart.getSymptoms();
                        String id = symRef.push().getKey();
                        Symptom s = new Symptom(id, sympAns, otherAns, descDoc.getText().toString(), remedyDoc.getText().toString());
                        ArrayList<Symptom> newSymps = new ArrayList<Symptom>();
                        newSymps.addAll(bodyPartSymptoms);
                        newSymps.add(s);
                        BodyPart newBodyPart = new BodyPart(bodyPartId, bodyPartName, newSymps);
                        bodyPartRef.child(bodyPartId).setValue(newBodyPart);

//                        bodyPartSymptoms.add(s);
//                        updateHelperBodyPart = new BodyPart(bodyPartId,bodyPartName,bodyPartSymptoms);
//                        bodyPartRef.child(bodyPartId).setValue(updateHelperBodyPart);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //query list se remove karna hai
                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren()){
                            if(data.getKey().equals(sympAns)){
                                data.getRef().removeValue();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




                AlertDialog.Builder builder = new AlertDialog.Builder(SubmitRemedyActivity.this);
                builder.setTitle("Thank You!")
                        .setMessage(("Successfully submitted remedy and the query is removed from the pending query list\""))
                        .setPositiveButton("You're Welcome!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                onBackPressed();
                            }
                        })
                        .create().show();
//                Toast.makeText(this, "Successfully submitted remedy and the query is removed from the pending query list",)
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
