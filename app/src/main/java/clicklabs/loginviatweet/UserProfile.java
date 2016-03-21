package clicklabs.loginviatweet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import clicklabs.loginviatweet.models.UserDetails;
import clicklabs.loginviatweet.utils.CommonData;

/**
 * Created by cl-macmini92 on 3/16/16.
 */
public class UserProfile extends AppCompatActivity {
    ImageView profile_pic;
    TextView user_name, user_email;
    Button Logout;
    private static final String host = "api.linkedin.com";
    private static final String topCardUrl = "https://" + host + "/v1/people/~:" +
            "(email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        profile_pic = (ImageView) findViewById(R.id.profile_pic);
        user_name = (TextView) findViewById(R.id.username);
        user_email = (TextView) findViewById(R.id.email);
        Logout = (Button) findViewById(R.id.logout);
        getUserData();

        //Logout.setOnClickListener(new View.OnClickListener() {
        // @Override
        //public void onClick(View v) {
        //LISessionManager.getInstance(getApplicationContext()).clearSession();
        //Intent intent = new Intent(UserProfile.this, MainActivity.class);
        //startActivity(intent);
        //
        //});}
    }public void getUserData(){
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(UserProfile.this,topCardUrl, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse result) {
                try {

                    setUserProfile(result.getResponseDataAsJson());


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onApiError(LIApiError LIApiError) {

            }

            public void setUserProfile(JSONObject response) {

                try {
                    UserDetails userDetails = new UserDetails(response.get("formattedName").toString(), null,response.get("emailAddress").toString() , null,response.getString("pictureUrl"),null);
                    Log.d("Tag", response.get("formattedName").toString());
                    Log.d("TAG", response.get("emailAddress").toString());
                    CommonData.setUserDetails(userDetails, UserProfile.this);
                    Intent intent = new Intent(getApplicationContext(),RegSecond.class);
                    startActivity(intent);
                    //user_email.setText(response.get("emailAddress").toString());
                   // user_name.setText(response.get("formattedName").toString());


                    Picasso.with(getApplicationContext()).load(response.getString("pictureUrl"))
                            .into(profile_pic);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });}}