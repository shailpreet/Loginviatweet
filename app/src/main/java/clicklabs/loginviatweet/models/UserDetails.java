package clicklabs.loginviatweet.models;

/**
 * Created by gill on 11-03-2016.
 */
public class UserDetails {
    public String firstName;
    public String lastName;
    public String email;
    public String gender;
    public String picUrl;
    public String userName;
public String Address;
    public String pincode;
    public String State;
    public String phoneno;
    public String city;
    public String locality;
    public String getPhoneno() {
        return phoneno;
    }



    public String getLocality() {
        return locality;
    }



    public String getCity() {
        return city;
    }



    public String getPincode() {
        return pincode;
    }

    public String getState() {
        return State;
    }



    public String getAddress() {
        return Address;
    }

    public UserDetails(String firstName, String lastName, String email, String gender, String picUrl,String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.picUrl = picUrl;
        this.userName=userName;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPicUrl() {
        return picUrl;
    }
    public String getUserName() {
        return userName;
    }

}