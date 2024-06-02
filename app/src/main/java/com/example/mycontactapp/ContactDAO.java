package com.example.mycontactapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kotlinx.coroutines.flow.Flow;

@Dao
public interface ContactDAO {

    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("select * from contact_table")
    List<Contact> getAllContacts();

}
