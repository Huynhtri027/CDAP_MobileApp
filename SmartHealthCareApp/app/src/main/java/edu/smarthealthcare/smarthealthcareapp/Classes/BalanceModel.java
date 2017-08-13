package edu.smarthealthcare.smarthealthcareapp.Classes;

/**
 * Created by RG User on 08/13/17.
 */

public class BalanceModel {

    private String PatientId;
    private String Balance;
    private String AddedDate;

    public String getPatientId() {
        return PatientId;
    }

    public void setPatientId(String patientId) {
        PatientId = patientId;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getAddedDate() {
        return AddedDate;
    }

    public void setAddedDate(String addedDate) {
        AddedDate = addedDate;
    }
}
