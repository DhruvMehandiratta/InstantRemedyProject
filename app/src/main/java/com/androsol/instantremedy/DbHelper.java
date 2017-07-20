package com.androsol.instantremedy;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dhruv on 17-07-2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "instantremedydatabase.db";
    public static final int DATABASE_VERSION = 1;

    // Body parts table
    public static final String BODY_PARTS_TABLE = "bodypartsTable";
    public static final String BODY_PARTS_COLUMN_ID = "bp_id";
    public static final String BODY_PARTS_COLUMN_NAME = "bp_name";

    // Symptoms table
    public static final String SYMPTOMS_TABLE = "symptomsTable";
    public static final String SYMPTOMS_COLUMN_ID = "symptoms_id";
    public static final String SYMPTOMS_COLUMN_NAME = "symptoms_name";

    public static final String SYMPTOMS_OTHER_SYMPS = "symptoms_other_symptoms";
    public static final String SYMPTOMS_DESCRIPTION = "symptoms_description";
    public static final String SYMPTOMS_REMEDY = "symptoms_remedy";


    // Bodyparts- symptoms table
    public static final String BODY_SYMPTOM_HELPER_TABLE = "bphelperTable";
    public static final String BODY_PART_ID_HELPER = "bp_helper_id";
    public static final String SYMPTOMS_ID_HELPER = "symptoms_id_helper";

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // body parts table
        sqLiteDatabase.execSQL("CREATE TABLE "+ BODY_PARTS_TABLE + " ( " + BODY_PARTS_COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + BODY_PARTS_COLUMN_NAME + " TEXT " +
                ");");

//        sqLiteDatabase.execSQL("CREATE TABLE "+SYMPTOMS_TABLE + " ( " + SYMPTOMS_COLUMN_ID +
//        " INTEGER PRIMARY KEY AUTOINCREMENT, " + SYMPTOMS_COLUMN_NAME + " TEXT " + ");");

        sqLiteDatabase.execSQL("CREATE TABLE "+SYMPTOMS_TABLE + " ( " + SYMPTOMS_COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + SYMPTOMS_COLUMN_NAME + " TEXT " +

                ");");


        sqLiteDatabase.execSQL("CREATE TABLE "+BODY_SYMPTOM_HELPER_TABLE + " ( " + BODY_PART_ID_HELPER +
                " INTEGER NOT NULL, " + SYMPTOMS_ID_HELPER + " INTEGER NOT NULL, " + " PRIMARY KEY ( "
                + BODY_PART_ID_HELPER + ", " + SYMPTOMS_ID_HELPER + " ) ); "
        );

        //pre populated database
        ContentValues initialValues = new ContentValues();
        initialValues.put(BODY_PARTS_COLUMN_NAME,"Head Scalp");
        sqLiteDatabase.insert(BODY_PARTS_TABLE, null, initialValues);

        initialValues.put(BODY_PARTS_COLUMN_NAME,"Heart");
        sqLiteDatabase.insert(BODY_PARTS_TABLE, null, initialValues);

        initialValues.put(BODY_PARTS_COLUMN_NAME,"Chest");
        sqLiteDatabase.insert(BODY_PARTS_TABLE, null, initialValues);

        initialValues.put(BODY_PARTS_COLUMN_NAME,"Abdomen");
        sqLiteDatabase.insert(BODY_PARTS_TABLE, null, initialValues);

        initialValues.put(BODY_PARTS_COLUMN_NAME,"Eye");
        sqLiteDatabase.insert(BODY_PARTS_TABLE, null, initialValues);

        initialValues.put(BODY_PARTS_COLUMN_NAME,"Back");
        sqLiteDatabase.insert(BODY_PARTS_TABLE, null, initialValues);

        initialValues.put(BODY_PARTS_COLUMN_NAME,"Ear");
        sqLiteDatabase.insert(BODY_PARTS_TABLE, null, initialValues);

        initialValues.put(BODY_PARTS_COLUMN_NAME,"Neck");
        sqLiteDatabase.insert(BODY_PARTS_TABLE, null, initialValues);


        //Symptoms
        initialValues = new ContentValues();
        initialValues.put(SYMPTOMS_COLUMN_NAME,"Apathy");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Anxiety");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Bleeding");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"headache");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Fever");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Dizziness");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Heartburn");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"chest pain and swollen glands");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Rapid Breathing");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Drainage or pus");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Numbness or tingling");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Abdomen bloating or fullness");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Muscle cramps or spasms");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Distended stomach");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"foul smelling stools");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Bleeding in eye");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Blindness");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Flickering light in vision");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"puffy eyelids");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Difficulty in walking");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Curved Spine");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Bruising or discoloration");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Lump or bulge");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Ear ache");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"ringing in ears");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"ear swelling");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Neck choking");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Cough");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Sore throat");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Joint pain");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);

        initialValues.put(SYMPTOMS_COLUMN_NAME,"Tender glands");
        sqLiteDatabase.insert(SYMPTOMS_TABLE, null, initialValues);


        // helper table bodypart + symptoms
        initialValues = new ContentValues();
        int j = 1;
        for(j = 1 ; j <= 5 ; j++) {
            initialValues.put(BODY_PART_ID_HELPER, 1);
            initialValues.put(SYMPTOMS_ID_HELPER, j);//id link karunga
            sqLiteDatabase.insert(BODY_SYMPTOM_HELPER_TABLE,null,initialValues);
        }
        for(j = 6 ; j <= 7 ; j++){
            initialValues.put(BODY_PART_ID_HELPER, 2);
            initialValues.put(SYMPTOMS_ID_HELPER, j);//id link karunga
            sqLiteDatabase.insert(BODY_SYMPTOM_HELPER_TABLE,null,initialValues);
        }
        for(j = 8 ; j <= 11 ; j++){
            initialValues.put(BODY_PART_ID_HELPER, 3);
            initialValues.put(SYMPTOMS_ID_HELPER, j);//id link karunga
            sqLiteDatabase.insert(BODY_SYMPTOM_HELPER_TABLE,null,initialValues);
        }
        for(j = 12 ; j <= 15 ; j++){
            initialValues.put(BODY_PART_ID_HELPER, 4);
            initialValues.put(SYMPTOMS_ID_HELPER, j);//id link karunga
            sqLiteDatabase.insert(BODY_SYMPTOM_HELPER_TABLE,null,initialValues);
        }
        for(j = 16 ; j <= 19 ; j++){
            initialValues.put(BODY_PART_ID_HELPER, 5);
            initialValues.put(SYMPTOMS_ID_HELPER, j);//id link karunga
            sqLiteDatabase.insert(BODY_SYMPTOM_HELPER_TABLE,null,initialValues);
        }
        for(j = 20 ; j<= 23 ; j++){
            initialValues.put(BODY_PART_ID_HELPER, 6);
            initialValues.put(SYMPTOMS_ID_HELPER, j);//id link karunga
            sqLiteDatabase.insert(BODY_SYMPTOM_HELPER_TABLE,null,initialValues);
        }
        for(j = 24 ; j <= 26 ; j++){
            initialValues.put(BODY_PART_ID_HELPER, 7);
            initialValues.put(SYMPTOMS_ID_HELPER, j);//id link karunga
            sqLiteDatabase.insert(BODY_SYMPTOM_HELPER_TABLE,null,initialValues);
        }
        for(j = 27 ; j <= 32 ; j++){
            initialValues.put(BODY_PART_ID_HELPER, 8);
            initialValues.put(SYMPTOMS_ID_HELPER, j);//id link karunga
            sqLiteDatabase.insert(BODY_SYMPTOM_HELPER_TABLE,null,initialValues);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addToBodyParts(String name){
        ContentValues values = new ContentValues();
        values.put(BODY_PARTS_COLUMN_NAME,name);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(BODY_PARTS_TABLE, null, values);
        db.close();
    }

    public void addToSymptoms(String name, String other_symptoms, String description, String remedy){
        ContentValues values = new ContentValues();
        values.put(SYMPTOMS_COLUMN_NAME, name);
        values.put(SYMPTOMS_OTHER_SYMPS,other_symptoms);
        values.put(SYMPTOMS_DESCRIPTION,description);
        values.put(SYMPTOMS_REMEDY, remedy);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(SYMPTOMS_TABLE, null, values);
        db.close();
    }

    public void addToBodySymptomHelper(int body_id, int s_id) {
        ContentValues values = new ContentValues();
        values.put(SYMPTOMS_ID_HELPER, s_id);
        values.put(BODY_PART_ID_HELPER, body_id);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(SYMPTOMS_TABLE, null, values);
        db.close();
    }
}
