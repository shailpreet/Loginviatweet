package clicklabs.loginviatweet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.login.widget.ProfilePictureView;
import com.squareup.picasso.Picasso;

import clicklabs.loginviatweet.models.UserDetails;

/**
 * Created by cl-macmini92 on 3/17/16.
 */
public class RegSecond extends AppCompatActivity {
    String gender, imageUrl,username;
    EditText etGender, etPhoneNumber, etLocality, etCity, etState, etPincode,etUserName;
    ProfilePictureView profilePic;
    ImageView ivProfilePic;
    Button btnLogout;
    Button btnSubmit;
    DatabaseUser db;


    public void init() {
        etGender = (EditText) findViewById(R.id.et_gender);
        etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        etLocality = (EditText) findViewById(R.id.et_locality);
        etCity = (EditText) findViewById(R.id.et_city);
        etState = (EditText) findViewById(R.id.et_state);
        etPincode = (EditText) findViewById(R.id.et_pincode);
        //etUserName=(EditText)findViewById(R.id.username);
        // profilePic = (ProfilePictureView) findViewById(R.id.iv_profilepic);
        ivProfilePic = (ImageView) findViewById(R.id.iv_profilepic);
        imageUrl = Commondata.getUserDetails(RegSecond.this).getPicUrl();
        //btFinish=(Button) findViewById(R.id.bt_finish);
        btnLogout=(Button)findViewById(R.id.Logout);
    btnSubmit=(Button)findViewById(R.id.Submit);}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content2);
        init();
        gender = Commondata.getUserDetails(RegSecond.this).getGender();
        username=Commondata.getUserDetails(RegSecond.this).getUserName();
        etGender.setText(gender);
//     etUserName.setText(username);
//        profilePic.setProfileId(CommonData.getUserDetails(RegSecond.this).getPicUrl());
        Picasso.with(this)
                .load(imageUrl)
                .into(ivProfilePic);
       btnLogout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Log.d("logout","rio");
                Commondata.clearAllAppData(getApplicationContext());
                Intent intent = new Intent(RegSecond.this, MainActivity.class);
                startActivity(intent);
              finish();
           }
       });
        db=new DatabaseUser(this);
btnSubmit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        db.addUser(new UserDetails("shail", "preet", "female", "@GMIAL", "JKL", "DJW"));
    }
});
    }

}