package com.example.mycontactapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.example.mycontactapp.databinding.ActivityContactDetailBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class contact_detail_activity extends AppCompatActivity {

    private ActivityContactDetailBinding activityContactDetailBinding;
    Contact contact;
    private DetailActivityClickListener detailActivityClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contact = new Contact();
        activityContactDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_contact_detail);
        activityContactDetailBinding.setContact(contact);

        detailActivityClickListener = new DetailActivityClickListener(this);
        activityContactDetailBinding.setClickHandlers(detailActivityClickListener);

        Intent i = getIntent();
        contact.setName(i.getStringExtra("Name"));
        contact.setNumber(i.getStringExtra("Number"));

        int i2 = i.getIntExtra("Icon",0);

        String c = String.valueOf( Objects.requireNonNull(i.getStringExtra("Name")).charAt(0) );

        ////////////////////////////////////////
        List<String> colors;
        colors=new ArrayList<String>();

        colors.add("#F39C12");
        colors.add("#F7DC6F");
        colors.add("#F1948A");
        colors.add("#A3E4D7");
        colors.add("#CCCCFF");
        colors.add("#EDBB99");
        colors.add("#D7DBDD");
        colors.add("#C39BD3");
        colors.add("#6495ED");

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.parseColor(colors.get(i2)));
        ////////////////////////////////

        TextViewData t = new TextViewData(c,draw);
        activityContactDetailBinding.setTextViewData(t);

    }

    public class DetailActivityClickListener{
        Context context;

        public DetailActivityClickListener(Context context) {
            this.context = context;
        }

        public void onBackClicked(View view){
            finish();
        }
    }

    public class TextViewData{
        private String text;
        private Drawable bg;

        public TextViewData(String text, Drawable bg) {
            this.text = text;
            this.bg = bg;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Drawable getBg() {
            return bg;
        }

        public void setBg(Drawable bg) {
            this.bg = bg;
        }


    }

}
