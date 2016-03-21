package clicklabs.loginviatweet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by cl-macmini92 on 3/17/16.
 */
public class Reg extends AppCompatActivity {
    String firstName, lastName, email;
    EditText etFirstName, etLastName, etEmail, etuserName;
    Button btnsignin;
    Button btnLogout;

    public void init() {
        etFirstName = (EditText) findViewById(R.id.edtFirstname);
        etLastName = (EditText) findViewById(R.id.edtLastname);
        etEmail = (EditText) findViewById(R.id.edtEmail);
        btnsignin = (Button) findViewById(R.id.signin);



        firstName = Commondata.getUserDetails(Reg.this).getFirstName();
        lastName = Commondata.getUserDetails(Reg.this).getLastName();
        email = Commondata.getUserDetails(Reg.this).getEmail();


    }

    public void setValue() {


        etFirstName.setText(firstName);
        etLastName.setText(lastName);
        etEmail.setText(email);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        init();
        setValue();

//        Log.d("lastname", lastName);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Reg.this, RegSecond.class);
                startActivity(intent);
                Log.d("tag", "second");
                //}
            }
        });


    }
}
// private boolean onValidate() {
       /* boolean isValid = true;
        if (etFirstName == null) {
            firstName=etFirstName.getText().toString();
            AlertBox.alertDialogShow(Reg.this, "enter first name");
            return false;
        }
        if (etLastName == null) {
            lastName=etLastName.getText().toString();
            AlertBox.alertDialogShow(Reg.this, "enter last name");
            return false;
        }
        if (etEmail.equals(null)) {
            AlertBox.alertDialogShow(Reg.this, "enter email");
            email=etEmail.getText().toString();
            return false;
        }
        if (!(!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            AlertBox.alertDialogShow(Reg.this, "enter valid email");
            return false;
        }
        return isValid;
    }/*
}
*/
