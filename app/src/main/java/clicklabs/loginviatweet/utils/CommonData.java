package clicklabs.loginviatweet.utils;

import android.content.Context;

import clicklabs.loginviatweet.SharedPreferences.Prefs;
import clicklabs.loginviatweet.SharedPreferences.SharedPreferencesName;
import clicklabs.loginviatweet.models.UserDetails;


/**
 * Created by gill on 11-03-2016.
 */
public class CommonData {
    public static UserDetails userDetails=null;

    public static UserDetails getUserDetails(Context context) {
        if (userDetails == null) {
            userDetails = Prefs.with(context).getObject(SharedPreferencesName.APP_USER, UserDetails.class);
        }
        return userDetails;
    }

    public static void setUserDetails(UserDetails userDetails,Context context) {
        CommonData.userDetails = userDetails;
        Prefs.with(context).save(SharedPreferencesName.APP_USER, userDetails);
    }
}
