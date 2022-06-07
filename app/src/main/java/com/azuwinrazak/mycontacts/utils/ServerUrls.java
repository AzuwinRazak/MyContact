package com.azuwinrazak.mycontacts.utils;

public class ServerUrls {

    public static final String BASE_URL = "http://10.0.2.2:9999";
    public static final String ALL_CONTACTS = BASE_URL + "/contacts";
    public static final String ADD_CONTACT = BASE_URL + "/addcontact";
    public static final String UPDATE_CONTACT = BASE_URL + "/updatecontact/{id}";
    public static final String DELETE_CONTACT = BASE_URL + "/deletecontact/{id}";
}
