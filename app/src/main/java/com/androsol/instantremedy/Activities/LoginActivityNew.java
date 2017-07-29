package com.androsol.instantremedy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androsol.instantremedy.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class LoginActivityNew extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 0;
    private FirebaseAuth auth;
   // private FirebaseAuth.AuthStateListener mAuthListener;
    Button toApp, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new2);
        auth = FirebaseAuth.getInstance();


        toApp = (Button) findViewById(R.id.button_continue_to_app);
        logout = (Button) findViewById(R.id.button_log_out);
        toApp.setOnClickListener(this);
        logout.setOnClickListener(this);
        if (auth.getCurrentUser() != null) {
            //user already signed in
            Log.d("DHRUVAUTH", auth.getCurrentUser().getEmail());
        } else {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                            .build()
                    , RC_SIGN_IN);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                //user logeed in
                Log.d("DHRUVAUTH",auth.getCurrentUser().getEmail());
            }else{
                //user not authenticated
                Log.d("DHRUVAUTH","User not authemticated");
            }
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.button_log_out){
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            finish();
                            Log.d("DHRUVUI","user logged out");
                        }
                    });
        }else if(id == R.id.button_continue_to_app){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
}
