package com.azuwinrazak.mycontacts.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.azuwinrazak.mycontacts.data.Model.Contact;
import com.azuwinrazak.mycontacts.data.Repository.ContactRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository contactRepository;
    private LiveData<List<Contact>> contactsResponseLiveData;

    public ContactViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Contact>> list() {
        return contactsResponseLiveData;
    }

    public void init() {
        contactRepository = new ContactRepository();
        contactsResponseLiveData = contactRepository.getContactsResponseLiveData();
    }

    public LiveData<List<Contact>> createContact(String nm, String pn) {
        contactsResponseLiveData = contactRepository.addNewContact(nm, pn);
        return contactsResponseLiveData;
    }

    public LiveData<List<Contact>> updateContact(int id, String nm, String pn) {
        contactsResponseLiveData = contactRepository.updateContact(id,nm, pn);
        return contactsResponseLiveData;
    }

    public LiveData<List<Contact>> deleteContact(int id) {
        contactsResponseLiveData = contactRepository.deleteContact(id);
        return contactsResponseLiveData;
    }


}
