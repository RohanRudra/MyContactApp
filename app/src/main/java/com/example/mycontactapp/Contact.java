package com.example.mycontactapp;

import android.graphics.drawable.Drawable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Contact_table")
public class Contact extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    int id;

    private String name;
    private String number;
    private int iconColor;

    @Ignore
    public Contact(){};

    public Contact(int id, String name, String number, int iconColor) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.iconColor = iconColor;
    }


    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
        notifyPropertyChanged(BR.number);
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public int getIconColor() {
        return iconColor;
    }

    public void setIconColor(Integer iconColor) {
        this.iconColor = iconColor;
        notifyPropertyChanged(BR.iconColor);
    }
}
