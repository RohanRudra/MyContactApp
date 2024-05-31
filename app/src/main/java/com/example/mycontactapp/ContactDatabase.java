package com.example.mycontactapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    //Join DAO
    public abstract ContactDAO getContactDAO();
}
