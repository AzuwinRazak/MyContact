package com.azuwinrazak.mycontacts.data.Model;

import com.google.gson.annotations.SerializedName;

public class AddContactRequestData {
    @SerializedName("name")
    private String name;
    @SerializedName("contactNo")
    private String contactNo;

    public AddContactRequestData(String name, String contactNo) {
        this.name = name;
        this.contactNo = contactNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

}
