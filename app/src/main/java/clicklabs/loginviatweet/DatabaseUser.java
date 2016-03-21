package clicklabs.loginviatweet;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import clicklabs.loginviatweet.models.UserDetails;

/**
 * Created by ashutosh on 2/29/2016.
 */
public class DatabaseUser extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userManager";
    private static final String TABLE_INFO = "information";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_LOCALITY="locality";
    private static final String KEY_CITY="city";
    private static final String KEY_PINCODE="pincode";

    public DatabaseUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_INFO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRST_NAME + " TEXT,"
                + KEY_LAST_NAME + " TEXT,"
                + KEY_GENDER + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_IMAGE_URL + " TEXT,"
                + KEY_PH_NO + " TEXT,"
                + KEY_LOCALITY+"TEXT,"
        +KEY_CITY+"TEXT,"
        +KEY_PINCODE+"TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Upgrading database


    // code to add the new contact
    public void addUser(UserDetails userInformation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

      Log.d("TAG","FRIST"+userInformation.getFirstName());

        values.put(KEY_FIRST_NAME, userInformation.getFirstName());
        values.put(KEY_LAST_NAME, userInformation.getLastName());
        values.put(KEY_GENDER, userInformation.getGender());
        values.put(KEY_EMAIL, userInformation.getEmail());
        values.put(KEY_ADDRESS, userInformation.getAddress());
        values.put(KEY_IMAGE_URL, userInformation.getPicUrl());
        values.put(KEY_PH_NO, userInformation.getPhoneno());
        values.put(KEY_LOCALITY,userInformation.getLocality());
        values.put(KEY_CITY,userInformation.getCity());
        values.put(KEY_PINCODE,userInformation.getPincode());


        // Inserting Row
       db.insert(TABLE_INFO, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }



}
