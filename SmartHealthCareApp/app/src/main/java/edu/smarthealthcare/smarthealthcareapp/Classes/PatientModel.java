package edu.smarthealthcare.smarthealthcareapp.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RG User on 07/19/17.
 */

public class PatientModel {

    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("FirstName")
    @Expose
    private String FirstName;
    @SerializedName("LastName")
    @Expose
    private String LastName;
    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("Password")
    @Expose
    private String Password;
    @SerializedName("Sex")
    @Expose
    private String Sex;
    @SerializedName("Age")
    @Expose
    private String Age;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("ContactNo")
    @Expose
    private String ContactNo;
    @SerializedName("RfidCode")
    @Expose
    private String RfidCode;
    @SerializedName("RegisterAt")
    @Expose
    private String RegisterAt;

    public PatientModel() {
        Id = null;
        FirstName = null;
        LastName = null;
        Email = null;
        Password = null;
        Sex = null;
        Age = null;
        Address = null;
        ContactNo = null;
        RfidCode = null;
        RegisterAt = null;
    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getRfidCode() {
        return RfidCode;
    }

    public void setRfidCode(String rfidCode) {
        RfidCode = rfidCode;
    }

    public String getRegisterAt() {
        return RegisterAt;
    }

    public void setRegisterAt(String registerAt) {
        RegisterAt = registerAt;
    }
}
