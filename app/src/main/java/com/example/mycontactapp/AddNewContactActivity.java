package com.example.mycontactapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.mycontactapp.databinding.ActivityAddNewContactBinding;

public class AddNewContactActivity extends AppCompatActivity {
    private ActivityAddNewContactBinding activityAddNewContactBinding;
    Contact contact;
    private NewContactClickListener newContactClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        contact = new Contact();
        activityAddNewContactBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_new_contact);
        activityAddNewContactBinding.setContact(contact);

        newContactClickListener = new NewContactClickListener(this);
        activityAddNewContactBinding.setClickHandler(newContactClickListener);
    }


    public class NewContactClickListener{
        Context context;
        public NewContactClickListener(Context context) {
            this.context = context;
        }

        public void onSaveClick(View view){
            //contact has been binded in the xml file
            if(contact.getName() == null){
                Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_SHORT).show();
            }
            else{
                //passing the contact to the MainActivity via Intent
                Intent i = new Intent();
                i.putExtra("Name",contact.getName());
                i.putExtra("Number",contact.getNumber());
                setResult(RESULT_OK,i);
                finish();
            }
        }

        public void onCancelClick(View view){
            //not to add new number
            finish();
        }
    }
}
