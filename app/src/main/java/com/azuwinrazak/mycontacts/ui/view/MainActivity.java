package com.azuwinrazak.mycontacts.ui.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.azuwinrazak.mycontacts.R;
import com.azuwinrazak.mycontacts.data.Model.Contact;
import com.azuwinrazak.mycontacts.databinding.ActivityMainBinding;
import com.azuwinrazak.mycontacts.ui.viewmodel.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactListAdapter.OnItemClicked {
    private ActivityMainBinding binding;
    private ContactListAdapter adapter;
    private ContactViewModel contactViewModel;
    private ArrayList<Contact> contactList = new ArrayList<Contact>();
    private int clickItemPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initUI();
        getContactList();

        binding.progressbar.setVisibility(View.VISIBLE);

    }

    private void getContactList() {
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.init();
        contactViewModel.list().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contactsResponse) {
                if(contactsResponse != null){
                    binding.swiperefreshlayout.setRefreshing(false);
                    binding.progressbar.setVisibility(View.GONE);
                    setRecyclerView(contactsResponse);
                    contactList.addAll(contactsResponse);
                }
            }
        });
    }

    private void setRecyclerView(List<Contact> contactsResponse) {
        adapter = new ContactListAdapter(MainActivity.this,contactsResponse);
        adapter.setOnClick(this);
        binding.rvContact.setAdapter(adapter);
        //adapter.updateContactList(contactsResponse);
        adapter.notifyDataSetChanged();
    }

    void initUI(){

        binding.swiperefreshlayout.setOnRefreshListener(() -> {
            binding.swiperefreshlayout.setRefreshing(true);
            getContactList();
        });

        binding.rvContact.setHasFixedSize(true);
        binding.rvContact.setLayoutManager(new LinearLayoutManager(this));

        binding.addNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsView = li.inflate(R.layout.alert_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MainActivity.this);

                // set alert_dialog.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText nameInput = (EditText) promptsView.findViewById(R.id.etName);
                final EditText phoneNoInput = (EditText) promptsView.findViewById(R.id.etPhone);
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //checking user input
                                if(nameInput.getText().toString().equals("") || phoneNoInput.getText().toString().equals("")){
                                    new AlertDialog.Builder(MainActivity.this)
                                            .setTitle("Alert")
                                            .setMessage(MainActivity.this.getString(R.string.name_cannot_be_empty))
                                            .setPositiveButton(android.R.string.yes, null)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }
                                else{
                                    // get user input
                                    //callApi Add contact
                                    postData(nameInput.getText().toString(), phoneNoInput.getText().toString());
                                }


                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });


    }

    private void postData(String name, String phoneNo) {
        contactViewModel.createContact(name, phoneNo);
        Contact contact = new Contact();
        contact.setName(name);
        contact.setPhoneNo(phoneNo);
        adapter.addContact(contact);
        Toast.makeText(MainActivity.this, "Successfully Add New Contact", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(int position) {
        clickItemPosition = position;
        Contact contactListDetail = contactList.get(position);
        Intent intent = new Intent(this, ContactDetailActivity.class);
        intent.putExtra("contactobj", contactListDetail);
        activityResultLauncher.launch(intent);

    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String id = data.getStringExtra("CONTACT_ID");
                        String name = data.getStringExtra("EDIT_NAME");
                        String phoneNo = data.getStringExtra("EDIT_NUMBER");
                        contactViewModel.updateContact(Integer.parseInt(id), name, phoneNo);
                        Contact contact = new Contact();
                        contact.setName(name);
                        contact.setPhoneNo(phoneNo);
                        adapter.updateContactToPostion(contact,clickItemPosition);
                    }

                    else if(result.getResultCode() == 101){
                        Intent data = result.getData();
                        String id = data.getStringExtra("CONTACT_ID");
                        contactViewModel.deleteContact(Integer.parseInt(id));
                        adapter.removeAt(clickItemPosition);
                    }
                }
            });

}
