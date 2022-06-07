package com.azuwinrazak.mycontacts.data.Interface;

import com.azuwinrazak.mycontacts.data.Model.AddContactRequestData;
import com.azuwinrazak.mycontacts.data.Model.Contact;
import com.azuwinrazak.mycontacts.utils.ServerUrls;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContactApiInterface {

    @GET(ServerUrls.ALL_CONTACTS)
    Call<List<Contact>> getContacts();

    @POST(ServerUrls.ADD_CONTACT)
    Call<Contact> addNewContact(@Body AddContactRequestData body);

    @PUT(ServerUrls.UPDATE_CONTACT)
    Call<Contact> updateContactDetail(@Path("id") int id, @Body AddContactRequestData body);

    @DELETE(ServerUrls.DELETE_CONTACT)
    Call<Contact> deleteCOntact(@Path("id") int id);
}
