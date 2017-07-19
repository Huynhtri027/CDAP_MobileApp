package edu.smarthealthcare.smarthealthcareapp.Classes;

/**
 * Created by RG User on 07/19/17.
 */

public class PatientModel {

    private String success;
    private String Id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Password;
    private String Sex;
    private String Age;
    private String Address;
    private String ContactNo;
    private String RfidCode;
    private String RegisterAt;

    public PatientModel() {
        this.success = null;
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

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
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
