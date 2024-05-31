package com.example.mycontactapp;

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

    @Ignore
    public Contact(){};

    public Contact(int id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
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
}
