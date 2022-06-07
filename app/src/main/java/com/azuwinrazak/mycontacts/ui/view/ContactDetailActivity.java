package com.azuwinrazak.mycontacts.ui.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.azuwinrazak.mycontacts.R;
import com.azuwinrazak.mycontacts.data.Model.Contact;
import com.azuwinrazak.mycontacts.databinding.ActivityContactDetailBinding;
import com.azuwinrazak.mycontacts.databinding.ActivityMainBinding;

public class ContactDetailActivity extends AppCompatActivity {

    private ActivityContactDetailBinding binding;
    private Contact myContactObj = new Contact();
    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_detail);

        getSupportActionBar().setTitle("Edit Contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myContactObj = (Contact) getIntent().getSerializableExtra("contactobj");

        binding.etName.setText(myContactObj.getName());
        binding.etEditPhone.setText(myContactObj.getPhoneNo());
        contactId = myContactObj.getId().toString();

        binding.btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                String name = binding.etName.getText().toString();
                String phone= binding.etEditPhone.getText().toString();
                data.putExtra("CONTACT_ID", contactId);
                data.putExtra("EDIT_NAME", name);
                data.putExtra("EDIT_NUMBER", phone);
                setResult(Activity.RESULT_OK, data);
                ContactDetailActivity.super.onBackPressed();

            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(ContactDetailActivity.this)
                        .setMessage(ContactDetailActivity.this.getString(R.string.sure_delete))
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent data = new Intent();
                                data.putExtra("CONTACT_ID", contactId);
                                setResult(101, data);
                                ContactDetailActivity.super.onBackPressed();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

