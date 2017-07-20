package com.androsol.instantremedy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androsol.instantremedy.Adapters.SymptomAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProblemsActivity extends AppCompatActivity {

    TextView textView;
    RecyclerView recyclerView;
    SymptomAdapter adapter;
    //DbHelper dbHelper;
    ArrayList<Symptom> symptoms;
    Button button;
    Intent i;
    DatabaseReference symptomsForBodyPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);



      //  dbHelper = new DbHelper(this);
        button = (Button) findViewById(R.id.problems_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProblemsActivity.this, QueryActivity.class);
                startActivity(intent);
            }
        });
        textView = (TextView) findViewById(R.id.problems_text);
        i = getIntent();
        String x =  i.getStringExtra("BodyPartName");
        textView.setText(x);
        symptoms = new ArrayList<Symptom>();
        adapter = new SymptomAdapter(symptoms, this);
        recyclerView = (RecyclerView) findViewById(R.id.problems_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        String id = i.getStringExtra("BodyPartId");
        symptomsForBodyPart = FirebaseDatabase.getInstance().getReference("symptoms").child(id);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpViews();
    }

    public void setUpViews(){
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        String body_part_id = i.getStringExtra("BodyPartId");

















//        String query = "SELECT " + dbHelper.SYMPTOMS_TABLE + ".* FROM "+
//                dbHelper.BODY_SYMPTOM_HELPER_TABLE + " INNER JOIN "+dbHelper.SYMPTOMS_TABLE
//                + " ON " + dbHelper.BODY_SYMPTOM_HELPER_TABLE + "." + dbHelper.BODY_PART_ID_HELPER+
//                " = "+body_part_id + " AND "+ dbHelper.BODY_SYMPTOM_HELPER_TABLE+ "." + dbHelper.SYMPTOMS_ID_HELPER+
//                " = "+dbHelper.SYMPTOMS_TABLE+"."+dbHelper.SYMPTOMS_COLUMN_ID+";";
//
//        //String query = "SELECT * FROM " + dbHelper.SYMPTOMS_TABLE + ";";
//
//        Cursor c = db.rawQuery(query,null);
//        symptoms.clear();
//        c.moveToFirst();
//        while(!c.isAfterLast()){
//            int id = c.getInt(c.getColumnIndex(dbHelper.SYMPTOMS_COLUMN_ID));
//            String name = c.getString(c.getColumnIndex(dbHelper.SYMPTOMS_COLUMN_NAME));
//            String otherSymps = c.getString(c.getColumnIndex(dbHelper.SYMPTOMS_OTHER_SYMPS));
//            String description = c.getString(c.getColumnIndex(dbHelper.SYMPTOMS_DESCRIPTION));
//            String remedy = c.getString(c.getColumnIndex(dbHelper.SYMPTOMS_REMEDY));
//            Symptom s = new Symptom(id, name,otherSymps,description, remedy);
//            symptoms.add(s);
//            c.moveToNext();
//        }
//        if(symptoms.size() == 0){
//            Log.d("DHRUVNODATA","no data");
//            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
//        }else{
//
//            Log.d("DHRUV","data hai");
//        }
//        Toast.makeText(this, symptoms.get(0).getName() + "  " + symptoms.get(symptoms.size()-2).getName() ,Toast.LENGTH_SHORT);
//
//        db.close();
//        adapter.notifyDataSetChanged();

    }
}
