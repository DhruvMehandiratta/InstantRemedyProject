package com.androsol.instantremedy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

public class RemedyActivity extends AppCompatActivity {

    Intent intent;
    TextView otherSympText, descriptionText, remedyText;
    ExpandableRelativeLayout expandableLayout2, expandableLayout3, expandableLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedy_new);
        otherSympText = (TextView) findViewById(R.id.other_symptoms);
        descriptionText = (TextView) findViewById(R.id.description_text);
        remedyText = (TextView) findViewById(R.id.remedy_text);
        intent = getIntent();
        String otherSymp = intent.getStringExtra("OtherSymptoms");
        String description  = intent.getStringExtra("Decription");
        String remedy = intent.getStringExtra("Remedy");
        otherSympText.setText(otherSymp);
        descriptionText.setText(description);
        remedyText.setText(remedy);

    }
    public void expandableButton2(View view) {
        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
        expandableLayout2.toggle(); // toggle expand and collapse
    }

    public void expandableButton3(View view) {
        expandableLayout3 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout3);
        expandableLayout3.toggle(); // toggle expand and collapse
    }

    public void expandableButton4(View view) {
        expandableLayout4 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout4);
        expandableLayout4.toggle(); // toggle expand and collapse
    }
}
