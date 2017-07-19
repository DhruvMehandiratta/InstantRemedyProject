package com.appuptechnologies.instantremedy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appuptechnologies.instantremedy.Adapters.SymptomAdapter;

import java.util.ArrayList;

public class ProblemsActivity extends AppCompatActivity {

    TextView textView;
    RecyclerView recyclerView;
    SymptomAdapter adapter;
    DbHelper dbHelper;
    ArrayList<Symptom> symptoms;
    Button button;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);
//        symptoms = new ArrayList<String>();
//        symptoms.add("Apathy");
//        symptoms.add("Anxiety");
//        symptoms.add("Bleeding");
//        symptoms.add("headache");
//        symptoms.add("Fever");
//        symptoms.add("Dizziness");
//        symptoms.add("Heartburn");
//        symptoms.add("chest pain and swollen glands");
//        symptoms.add("Rapid Breathing");
//        symptoms.add("Drainage or pus");
//        symptoms.add("Numbness or tingling");
//        symptoms.add("Abdomen bloating or fullness");
//        symptoms.add("Muscle cramps or spasms");
//        symptoms.add("Distended stomach");
//        symptoms.add("foul smelling stools");
//        symptoms.add("Bleeding in eye");
//        symptoms.add("Blindness");
//        symptoms.add("Flickering light in vision");
//        symptoms.add("puffy eyelids");
//        symptoms.add("Difficulty in walking");
//        symptoms.add("Curved Spine");
//        symptoms.add("Bruising or discoloration");
//        symptoms.add("Lump or bulge");
//        symptoms.add("Ear ache");
//        symptoms.add("ringing in ears");
//        symptoms.add("ear swelling");
//        symptoms.add("Neck choking");
//        symptoms.add("Cough");
//        symptoms.add("Sore throat");
//        symptoms.add("Joint pain");
//        symptoms.add("Tender glands");


        dbHelper = new DbHelper(this);
        button = (Button) findViewById(R.id.problems_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProblemsActivity.this, QueryActivity.class);
                startActivity(intent);
            }
        });
        textView = (TextView) findViewById(R.id.problems_text);
        Intent i = getIntent();
        String x =  i.getStringExtra("BodyPartName");
        textView.setText(x);
        symptoms = new ArrayList<Symptom>();
        adapter = new SymptomAdapter(symptoms, this);
        recyclerView = (RecyclerView) findViewById(R.id.problems_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpViews();
    }

    public void setUpViews(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        i = getIntent();
        int body_part_id = (int) i.getLongExtra("BodyPartId",0);


        String query = "SELECT " + dbHelper.SYMPTOMS_TABLE + ".* FROM "+
                dbHelper.BODY_SYMPTOM_HELPER_TABLE + " INNER JOIN "+dbHelper.SYMPTOMS_TABLE
                + " ON " + dbHelper.BODY_SYMPTOM_HELPER_TABLE + "." + dbHelper.BODY_PART_ID_HELPER+
                " = "+body_part_id + " AND "+ dbHelper.BODY_SYMPTOM_HELPER_TABLE+ "." + dbHelper.SYMPTOMS_ID_HELPER+
                " = "+dbHelper.SYMPTOMS_TABLE+"."+dbHelper.SYMPTOMS_COLUMN_ID+";";

        //String query = "SELECT * FROM " + dbHelper.SYMPTOMS_TABLE + ";";

        Cursor c = db.rawQuery(query,null);
        symptoms.clear();
        c.moveToFirst();
        while(!c.isAfterLast()){
            int id = c.getInt(c.getColumnIndex(dbHelper.SYMPTOMS_COLUMN_ID));
            String name = c.getString(c.getColumnIndex(dbHelper.SYMPTOMS_COLUMN_NAME));
            Symptom s = new Symptom(id, name);
            symptoms.add(s);
            c.moveToNext();
        }
        if(symptoms.size() == 0){
            Log.d("DHRUVNODATA","no data");
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{

            Log.d("DHRUV","data hai");
        }
        Toast.makeText(this, symptoms.get(0).getName() + "  " + symptoms.get(symptoms.size()-2).getName() ,Toast.LENGTH_SHORT);

        db.close();
        adapter.notifyDataSetChanged();

    }
}
