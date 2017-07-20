package com.androsol.instantremedy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androsol.instantremedy.Adapters.BodyPartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BodyPartAdapter adapter;
    ArrayList<BodyPart> bodyParts;
    DatabaseReference bodyPartRef, symptomRef;
    SharedPreferences checkFirstPref = null;
    ArrayList<Symptom> symptoms;

    //DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bodyPartRef = FirebaseDatabase.getInstance().getReference("bodyparts");
        //symptomRef = bodyPartRef.child("symptoms");
        //symptomRef = FirebaseDatabase.getInstance().getReference("symptoms");
        checkFirstPref= getSharedPreferences("com.androsol.instantremedy",MODE_PRIVATE);

        //dbHelper = new DbHelper(this);
        bodyParts = new ArrayList<BodyPart>();
        recyclerView = (RecyclerView) findViewById(R.id.body_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BodyPartAdapter(bodyParts,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkFirstPref.getBoolean("firstrun",true)){
            //prepopulated data



            //Symptoms.........................................................................................

            symptoms = new ArrayList<Symptom>();
            String id = symptomRef.push().getKey();
            //pehla
            Symptom s = new Symptom(id, "Apathy","Anxiety, Constipation, Depressed Mood, Difficulty sleeping"
            ,"Disease can be DEPRESSION"+
                    "DESCRIPTION: Depression is a painful sadness or down mood that interferes with daily life. " +
                    "Many people feel down for short periods, but depression lasts a long time and may include anxiety, insomnia, and" +
                    " other symptoms. Life events such as the death of a loved one can trigger depression. The illness can run in families," +
                    " but people with no family history also become depressed. Often, there is no clear cause. Depression is a common but serious" +
                    " illness that usually doesn't go away without treatment." +
                            " Counseling and/or antidepressant medication can treat depression in most people",
                    "Treatment Depression treatment may include: [ LONG TERM DISEASE]\n" +
                            "Antidepressant medications, such as fluoxetine (Prozac), paroxetine (Paxil), sertraline (Zoloft), and venlafaxine (Effexor)\n" +
                            "• Counseling\n" +
                            "• Electroconvulsive therapy for severe depression and if antidepressants don't work\n" +
                            "• EAT NUTRITIOUS FOOD.\n" +
                            "• TRY SPENDING TIME WITH PEOPLE WHO MAKE YOU LAUGH\n" +
                            "MORE: http://www.medicinenet.com/depression/article.htm");
            symptomRef.child(id).setValue(s);
            symptoms.add(s);
            //doosra
            s = new Symptom(id, "Anxiety", "Pain or discomfort, Dizziness, Giddiness, Irregular heartbeat, vomiting"
            ,"Disease can be PANIC ATTACK\n" +
                    "DESCRIPTION: When someone has a panic attack, that person feels a sudden, intense fear that can't be controlled. People who have panic attacks often feel like they are having a heart attack, losing control, suffocating, or dying. During the panic attack, the person also may have chest pain, nausea, shortness of breath, chills, sweating, dizziness, or a feeling of choking. Doctors don't know for certain what causes panic attacks, but it may have to do with genetics or stress",
                    "Treatment for panic attacks may include:\n" +
                            "• Antidepressant medications, such as fluoxetine (Prozac), paroxetine (Paxil), sertraline (Zoloft), and venlafaxine (Effexor)\n" +
                            "• Mild sedatives, such as alprazolam (Xanax) and clonazepam (Klonopin)\n" +
                            "• Cognitive-behavioral therapy, psychodynamic therapy, or other types of therapy\n" +
                            "MORE: http://www.medicinenet.com/symptoms_and_signs/symptomchecker.htm#conditionView");
            symptoms.add(s);


            // ek body part khatam hone ke baad
            //symptoms = new ArrayList<Symptom>(); ....ye line extra add karni hai bas
            // fir next body part tak continue









//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Anxiety");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Bleeding");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "headache");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Fever");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Dizziness");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Heartburn");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "chest pain and swollen glands");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Rapid Breathing");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Drainage or pus");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Numbness or tingling");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Abdomen bloating or fullness");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Muscle cramps or spasms");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Distended stomach");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "foul smelling stools");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Bleeding in eye");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Blindness");
//            symptomRef.child(id).setValue(bp);
//
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Flickering light in vision");
//            symptomRef.child(id).setValue(bp);
//
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "puffy eyelids");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Difficulty in walking");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Curved Spine");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Bruising or discoloration");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Lump or bulge");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Ear ache");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "ringing in ears");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "ear swelling");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Neck choking");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Cough");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Sore throat");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Joint pain");
//            symptomRef.child(id).setValue(bp);
//
//            id = symptomRef.push().getKey();
//            bp = new BodyPart(id, "Tender glands");
//            symptomRef.child(id).setValue(bp);









            id = bodyPartRef.push().getKey();
            BodyPart bp = new BodyPart(id,"Head Scalp",symptoms);
            bodyPartRef.child(id).setValue(bp);

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Heart",symptoms);
            bodyPartRef.child(id).setValue(bp);

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Chest",symptoms);
            bodyPartRef.child(id).setValue(bp);

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Abdomen",symptoms);
            bodyPartRef.child(id).setValue(bp);

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Eye",symptoms);
            bodyPartRef.child(id).setValue(bp);

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Back",symptoms);
            bodyPartRef.child(id).setValue(bp);

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Ear",symptoms);
            bodyPartRef.child(id).setValue(bp);

            id = bodyPartRef.push().getKey();
            bp = new BodyPart(id, "Neck",symptoms);
            bodyPartRef.child(id).setValue(bp);



            ////...........Helper Table...........................................................
            //  myRef.child("bodyparts").setValue(bp);
            Snackbar.make(this.findViewById(android.R.id.content),"Setting up the views...",Snackbar.LENGTH_LONG).show();
            checkFirstPref.edit().putBoolean("firstrun",false).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpViews();
    }

    public void setUpViews(){

        bodyPartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bodyParts.clear();
                for(DataSnapshot bodypartSnapshot : dataSnapshot.getChildren()){
                    BodyPart bp = bodypartSnapshot.getValue(BodyPart.class);
                    bodyParts.add(bp);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        String query = "SELECT * FROM "+dbHelper.BODY_PARTS_TABLE + ";";
//        Cursor c = db.rawQuery(query,null);
//        bodyParts.clear();
//        c.moveToFirst();
//        while(!c.isAfterLast()){
//            int id = c.getInt(c.getColumnIndex(dbHelper.BODY_PARTS_COLUMN_ID));
//            String name = c.getString(c.getColumnIndex(dbHelper.BODY_PARTS_COLUMN_NAME));
//            BodyPart bp = new BodyPart(id, name);
//            bodyParts.add(bp);
//            c.moveToNext();
//        }
//        if(bodyParts.size() == 0){
//            Log.d("DHRUVNODATA","no data");
//            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
//        }else{
//
//            Log.d("DHRUV","data hai");
//        }
//        Toast.makeText(this, bodyParts.get(0).getName() + "  " + bodyParts.get(bodyParts.size()-1).getName() ,Toast.LENGTH_SHORT);
//
//        db.close();
//        adapter.notifyDataSetChanged();
    }
}

