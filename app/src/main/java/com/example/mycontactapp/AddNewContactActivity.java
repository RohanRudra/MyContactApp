package com.example.mycontactapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawableContainerCompat;
import androidx.appcompat.graphics.drawable.DrawableWrapperCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;

import com.example.mycontactapp.databinding.ActivityAddNewContactBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
                Random r = new Random();
                int num = r.nextInt(8- 0) + 0;

                //passing the contact to the MainActivity via Intent
                Intent i = new Intent();
                i.putExtra("Name",contact.getName());
                i.putExtra("Number",contact.getNumber());
                i.putExtra("iconColor", num);

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
