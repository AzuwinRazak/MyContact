package com.azuwinrazak.mycontacts.data.Repository;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.azuwinrazak.mycontacts.data.Interface.ContactApiInterface;
import com.azuwinrazak.mycontacts.data.Model.AddContactRequestData;
import com.azuwinrazak.mycontacts.data.Model.Contact;
import com.azuwinrazak.mycontacts.utils.ServerUrls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactRepository {

    private ContactApiInterface contactAPI;
    private MutableLiveData<List<Contact>> contactsResponse;
    private ArrayList<Contact> contactList = new ArrayList<Contact>();

    public ContactRepository() {
        contactsResponse = new MutableLiveData<List<Contact>>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        contactAPI = new retrofit2.Retrofit.Builder()
                .baseUrl(ServerUrls.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ContactApiInterface.class);

    }

    public LiveData<List<Contact>> getContactsResponseLiveData() {
        Call<List<Contact>> call = contactAPI.getContacts();
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                //finally we are setting the list to our MutableLiveData
                contactsResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {

            }

        });
        return contactsResponse;
    }

    public LiveData<List<Contact>> addNewContact(String nm, String pn) {
        // passing data from our text fields to our modal class.
        AddContactRequestData modal = new AddContactRequestData(nm, pn);

        // calling a method to create a post and passing our modal class.
        Call<Contact> call = contactAPI.addNewContact(modal);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                contactList.add(response.body());
                //contactsResponse.setValue(Collections.sort(contactList, (t1, t2) -> t1.getName().compareTo(t2.getName())););
                contactsResponse.setValue(contactList);
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {

            }

        });

        return contactsResponse;
    }

    public LiveData<List<Contact>> updateContact(int id, String nm, String pn) {
        // passing data from our text fields to our modal class.
        AddContactRequestData modal = new AddContactRequestData(nm, pn);

        // calling a method to create a post and passing our modal class.
        Call<Contact> call = contactAPI.updateContactDetail(id,modal);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                contactList.add(response.body());
                Set<Contact> newContactList = new LinkedHashSet<>(contactList);
                contactList.clear();
                contactList.addAll(newContactList);

                //contactsResponse.setValue(Collections.sort(contactList, (t1, t2) -> t1.getName().compareTo(t2.getName())););
                contactsResponse.setValue(contactList);
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {

            }

        });

        return contactsResponse;
    }

    public LiveData<List<Contact>> deleteContact(int id) {
        // calling a method to create a post and passing our modal class.
        Call<Contact> call = contactAPI.deleteCOntact(id);
        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if(!response.isSuccessful()) {
                    return;
                }
                contactList.add(response.body());
                Set<Contact> newContactList = new LinkedHashSet<>(contactList);
                contactList.clear();
                contactList.addAll(newContactList);

                //contactsResponse.setValue(Collections.sort(contactList, (t1, t2) -> t1.getName().compareTo(t2.getName())););
                contactsResponse.setValue(contactList);
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {

            }

        });

        return contactsResponse;
    }
}
