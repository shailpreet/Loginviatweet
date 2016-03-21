package clicklabs.loginviatweet;

import android.content.Context;

import clicklabs.loginviatweet.SharedPreferences.Prefs;
import clicklabs.loginviatweet.SharedPreferences.SharedPreferencesName;
import clicklabs.loginviatweet.models.UserDetails;
import clicklabs.loginviatweet.utils.CommonData;

/**
 * Created by cl-macmini92 on 3/16/16.
 */
public class Commondata {
    static UserDetails userDetails = null;

    public static UserDetails getUserDetails(Context context) {
        if (userDetails == null) {
            userDetails = Prefs.with(context).getObject(SharedPreferencesName.APP_USER, UserDetails.class);
        }
        return userDetails;
    }

    public static void setUserDetails(UserDetails userDetails, Context context) {
        CommonData.userDetails = userDetails;
        Prefs.with(context).save(SharedPreferencesName.APP_USER, userDetails);
    }

public static void clearAllAppData(Context context){
    userDetails=null;
    Prefs.with(context).removeAll();
}
}