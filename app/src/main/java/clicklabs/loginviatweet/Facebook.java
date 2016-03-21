package clicklabs.loginviatweet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import clicklabs.loginviatweet.models.UserDetails;

public class Facebook extends AppCompatActivity {
    CallbackManager callbackManager;
    LoginButton loginButton;
    Profile profile;
    String firstName;
    String lastName;
    String email;
    String gender;
    String picUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_facebook);
        callbackManager = CallbackManager.Factory.create();
        loginButton=(LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v("TAG", "success");
                profile = Profile.getCurrentProfile();
                Log.v("TAG SUCCESS", profile.getFirstName());

                firstName = profile.getFirstName();
                lastName= profile.getLastName();
                picUrl=profile.getId();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.d("Tag", response.toString());
                                try {
                                    email = object.getString("email");
                                    gender = object.getString("gender");
                                    Log.d("Tag", email);
                                    Log.d("Tag", gender);
                                    UserDetails userdetails=new UserDetails(firstName,lastName,email,gender,picUrl,null);
                                    Commondata.setUserDetails(userdetails,getApplicationContext());
                                    Intent intent=new Intent(Facebook.this,Reg.class);
                                   startActivity(intent);



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("Tag", "inside catch");
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }



            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }


        });
    }

    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}