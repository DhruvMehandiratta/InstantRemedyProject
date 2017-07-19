package com.appuptechnologies.instantremedy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.appuptechnologies.instantremedy.Adapters.BodyPartAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BodyPartAdapter adapter;
    ArrayList<BodyPart> bodyParts;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper(this);
        bodyParts = new ArrayList<BodyPart>();
        recyclerView = (RecyclerView) findViewById(R.id.body_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BodyPartAdapter(bodyParts,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpViews();
    }

    public void setUpViews(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String query = "SELECT * FROM "+dbHelper.BODY_PARTS_TABLE + ";";
        Cursor c = db.rawQuery(query,null);
        bodyParts.clear();
        c.moveToFirst();
        while(!c.isAfterLast()){
            int id = c.getInt(c.getColumnIndex(dbHelper.BODY_PARTS_COLUMN_ID));
            String name = c.getString(c.getColumnIndex(dbHelper.BODY_PARTS_COLUMN_NAME));
            BodyPart bp = new BodyPart(id, name);
            bodyParts.add(bp);
            c.moveToNext();
        }
        if(bodyParts.size() == 0){
            Log.d("DHRUVNODATA","no data");
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{

            Log.d("DHRUV","data hai");
        }
        Toast.makeText(this, bodyParts.get(0).getName() + "  " + bodyParts.get(bodyParts.size()-1).getName() ,Toast.LENGTH_SHORT);

        db.close();
        adapter.notifyDataSetChanged();
    }
}

