package clicklabs.loginviatweet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONObject;

import clicklabs.loginviatweet.models.UserDetails;
import clicklabs.loginviatweet.utils.CommonData;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener,GoogleApiClient.ConnectionCallbacks{
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "0fCryOaJxJYbBIz0TjlcNZTgo";
    private static final String TWITTER_SECRET = "	G7a7ir08mDWDEf1vn7wl0N5zpgXIZkemrFlp50iZ68OgVAKjrK ";
    private TwitterLoginButton TwitterloginButton;
    TwitterSession session;
    UserDetails userDetails;
    Button Linkedin;
    private GoogleApiClient mGoogleApiClient;
private ProgressDialog mProgressDialog;
    String firstName;
    Button Flogin;
    String picUrl,userName;

    private static final String host = "api.linkedin.com";
    private static final String topCardUrl = "https://" + host + "/v1/people/~:" +
            "(email-address,formatted-name,phone-numbers,public-profile-url,picture-url,picture-urls::(original))";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        Log.v("TAG", "HELLO");
        setContentView(R.layout.activity_main);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        //List<String> permissions = Arrays.asList("basic_info", "email", "user_photos",  "user_birthday", "public_profile");
        //permissions.add("friend_list");

        Log.v("TAG", "Hi");
        Linkedin=(Button)findViewById(R.id.linkedbtn);
        TwitterloginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
Flogin=(Button)findViewById(R.id.FacebookLogin);

       Linkedin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               login_linkedin();
               Log.d("Tag", "hello");
           }
       });
        Flogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Facebook.class);
                startActivity(intent);
            }
        });
        googlesignin();
     //   getUserData();
        TwitterloginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.v("Tag", "buy");
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                //TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                //String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                session = Twitter.getSessionManager().getActiveSession();

                Twitter.getApiClient(session).getAccountService()
                        .verifyCredentials(true, false, new Callback<User>() {
                            @Override
                            public void success(Result<User> result) {
                                User user = result.data;
                               picUrl = user.profileImageUrl;
                                firstName = user.screenName;
                                userName = user.name;
                                Log.d("tag","piopi");
                                Log.d("tag",userName);
                                Log.d("Tag",firstName);

                               userDetails=new UserDetails(firstName,null,null,null,picUrl,userName);
                                CommonData.setUserDetails(userDetails, MainActivity.this);
                                Intent intent = new Intent(getApplicationContext(),Reg.class);
                        startActivity(intent);
                                //user_picture = (ImageView) findViewById(R.id.profile_pic);
                                // Picasso.with(getApplicationContext()).load(twitterImage.toString())
                                // .into(user_picture);

                                //screen_name = (TextView) findViewById(R.id.screen_name);
                                //screen_name.setText("Username : " + screenname);

                                //user_name = (TextView) findViewById(R.id.user_name);
                                // user_name.setText("Name : "+username);

                                //user_location = (TextView) findViewById(R.id.user_location);
                                //user_location.setText("Location : "+location);

                                // user_timezone = (TextView) findViewById(R.id.user_timezone);
                                //user_timezone.setText("Timezone : "+timeZone);

                                //user_description = (TextView) findViewById(R.id.user_description);
                                //user_description.setText("Description : "+description);
                            }

                            @Override
                            public void failure(TwitterException e) {
                            }
                        });

            }


            @Override
            public void failure(TwitterException e) {
                Log.d("TwitterKit", "Login with Twitter failure", e);
            }

        });
        /*loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v("TAG", "success");
               profile= Profile.getCurrentProfile();
                Log.v("TAG SUCCESS", profile.getFirstName());

                firstName= profile.getFirstName();
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
                                   userDetails=new UserDetails(firstName,lastName,email,gender,picUrl);
                                    CommonData.setUserDetails(userDetails,MainActivity.this);

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
            }*/

            /*@Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }


        });*/
            }

            //@Override
        //protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            //super.onActivityResult(requestCode, resultCode, data);
            // Make sure that the loginButton hears the result from any
            // Activity that it triggered.
            //TwitterloginButton.onActivityResult(requestCode, resultCode, data);
        //}
    public void login_linkedin(){
        Log.d("Tag", "hillo"); //
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                getUserData();

                Toast.makeText(getApplicationContext(), "success" + LISessionManager.getInstance(getApplicationContext()).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAuthError(LIAuthError error) {

                Toast.makeText(getApplicationContext(), "failed " + error.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }, true);
    }




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TwitterloginButton.onActivityResult(requestCode, resultCode, data);
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this,
                requestCode, resultCode, data);

        //Intent intent = new Intent(MainActivity.this,UserProfile.class);
        //startActivity(intent);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    // This method is used to make permissions to retrieve data from linkedin

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS);
    }
    public void googlesignin()
    {
        if(mGoogleApiClient!=null)
        {mGoogleApiClient.disconnect();}
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());}
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        //OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
       // if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            //Log.d(TAG, "Got cached sign-in");
            //GoogleSignInResult result = opr.get();
           // handleSignInResult(result);
        //} else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
           // showProgressDialog();
            //opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                //@Override
                //public void onResult(GoogleSignInResult googleSignInResult) {
                  //  hideProgressDialog();
                    //handleSignInResult(googleSignInResult);
            //    }
            //});
       // }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            firstName= acct.getDisplayName();
            String acctEmail= acct.getEmail();
            picUrl = String.valueOf(acct.getPhotoUrl());
            userDetails = new UserDetails(acct.getDisplayName(), null, acctEmail, null, picUrl,null);
            CommonData.setUserDetails(userDetails, MainActivity.this);
            Intent intent = new Intent(this,Reg.class);
          startActivity(intent);

            ;
            //personPhoto = String.valueOf(acct.getPhotoUrl());

    }}private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    //private void signOut() {
       // Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                //new ResultCallback<Status>() {
                    //@Override
                    //public void onResult(Status status) {
                   //     // [START_EXCLUDE]
                     //   updateUI(false);
                        // [END_EXCLUDE]
                    //}
                //});
   // }
    // [END signOut]

    // [START revokeAccess]

    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    //private void updateUI(boolean signedIn) {
        //if (signedIn) {
           // findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
       // } else {
           // mStatusTextView.setText(R.string.signed_out);
//
            //findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
           // findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        //}
    //}
    //private void revokeAccess() {
        //Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
               // new ResultCallback<Status>() {
                   // @Override
                   // public void onResult(Status status) {
                        // [START_EXCLUDE]
                       // updateUI(false);
                        // [END_EXCLUDE]
                   // }
                //});
   // }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;


        }


}

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    public void getUserData(){
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(MainActivity.this,topCardUrl, new ApiListener() {
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
                    UserDetails userDetails = new UserDetails(response.get("formattedName").toString(), null,response.get("emailAddress").toString() , null,response.get("pictureUrl").toString(),null);

                    Commondata.setUserDetails(userDetails, MainActivity.this);
                    Intent intent = new Intent(getApplicationContext(),Reg.class);
                    startActivity(intent);
                    Log.d("Nmae",response.get("formattedName").toString());
                    Log.d("eml", response.get("emailAddress").toString());
                    Log.d("pic", response.get("pictureUrl").toString());
                   // user_email.setText(response.get("emailAddress").toString());
                    //user_name.setText(response.get("formattedName").toString());


                    //Picasso.with(getApplicationContext()).load(response.getString("pictureUrl"))
                            //.into(profile_pic);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("tag","error"+e);
                }
            }

        });}
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
}
