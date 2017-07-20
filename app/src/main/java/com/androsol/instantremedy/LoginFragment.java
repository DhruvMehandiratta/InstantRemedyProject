package com.androsol.instantremedy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;


/**
 * Created by Dhruv on 14-07-2017.
 */

public class LoginFragment extends Fragment {

    private TextView textDetails;
    private TextView textID;
    private ImageView imageView;
    Button enableButton;
    String imageURL;

    private CallbackManager mCallbackManager;
    private ProfileTracker tracker;
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            AccessToken token = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
//            Toast.makeText(getActivity(),token+"",Toast.LENGTH_SHORT).show();
            if(profile == null){
                tracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        textDetails.setText("Welcome " + currentProfile.getName());
                        textID.setText("Copy this and send to me :-"+currentProfile.getId());
//                        Uri x = currentProfile.getProfilePictureUri(100,100);
//                        imageView.setImageURI(null);
//                        imageView.setImageURI(x);

                        imageURL = "https://graph.facebook.com/" + currentProfile.getId() + "/picture?width=" + 150 +"&height=" + 150;
                        Picasso.with(getActivity()).load(imageURL).fit().into(imageView);
                        tracker.stopTracking();
                    }
                };

            }else{
                textDetails.setText("Welcome " + profile.getName());
                textID.setText("Copy this and send to me :-"+profile.getId());
                //Uri x = profile.getProfilePictureUri(100,100);
                //imageView.setImageURI(null);
                //imageView.setImageURI(x);

            }
            //enableButton.setEnabled(true);


        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            Toast.makeText(getActivity(),"There is some error in login, please inform AppUpTechnologies",Toast.LENGTH_LONG).show();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment,container,false);
        textDetails = (TextView) v.findViewById(R.id.text_details);
        textID = (TextView) v.findViewById(R.id.text_fb_id);
        imageView = (ImageView) v.findViewById(R.id.fb_image);
        enableButton = (Button) v.findViewById(R.id.enable_button);
        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager,mCallback);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
    }
}

