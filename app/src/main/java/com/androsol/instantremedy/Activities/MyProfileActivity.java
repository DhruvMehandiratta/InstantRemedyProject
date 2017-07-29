package com.androsol.instantremedy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androsol.instantremedy.Constants;
import com.androsol.instantremedy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class MyProfileActivity extends AppCompatActivity {

    TextView name, emailId, adminText;
    ImageView photo;
    Intent intent;
    Button signOutButton;
    Boolean admin= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();
        String nameText = intent.getStringExtra(Constants.USER_NAME);
        String emailText = intent.getStringExtra(Constants.USER_EMAIL);
        String photoURL = intent.getStringExtra(Constants.USER_PHOTO);
        name = (TextView) findViewById(R.id.profile_name);
        photo = (ImageView) findViewById(R.id.profile_photo);
        emailId = (TextView) findViewById(R.id.profile_email);
        adminText = (TextView) findViewById(R.id.profile_admin_check_text);
        signOutButton = (Button) findViewById(R.id.profile_signout_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MyProfileActivity.this, Login3Activity.class);
                startActivity(i);
            }
        });

        name.setText(nameText);
        emailId.setText(emailText);
        Picasso
                .with(this)
                .load(photoURL)
                .into(photo);
        String[] helper = Constants.adminArray;
        for(String x : helper){
            if(x.equals(emailText)){
                admin = true;
                adminText.setText("Wow! You are an admin of this app!");
            }
        }
        if(!admin){
            adminText.setText("You are one of the good users of this app!");
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
