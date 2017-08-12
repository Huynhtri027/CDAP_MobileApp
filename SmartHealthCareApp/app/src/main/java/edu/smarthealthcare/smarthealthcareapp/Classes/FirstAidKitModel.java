package edu.smarthealthcare.smarthealthcareapp.Classes;

/**
 * Created by RG User on 08/12/17.
 */

public class FirstAidKitModel {

    private String DrugPackId;
    private String DrugPackName;
    private String UnitPrice;
    private String Instruction;
    private String Image;

    public FirstAidKitModel(String drugPackId, String drugPackName, String unitPrice, String instruction, String image) {
        DrugPackId = null;
        DrugPackName = null;
        UnitPrice = null;
        Instruction = null;
        Image = null;
    }

    public String getDrugPackId() {
        return DrugPackId;
    }

    public void setDrugPackId(String drugPackId) {
        DrugPackId = drugPackId;
    }

    public String getDrugPackName() {
        return DrugPackName;
    }

    public void setDrugPackName(String drugPackName) {
        DrugPackName = drugPackName;
    }

    public String getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }

    public String getInstruction() {
        return Instruction;
    }

    public void setInstruction(String instruction) {
        Instruction = instruction;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
