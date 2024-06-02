package com.example.mycontactapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.mycontactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ContactDatabase contactDatabase;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ActivityMainBinding activityMainBinding;
    private ContactAdapter myCustomAdapter;

    private MainActivityClickHandler clickHandler;
    private SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        clickHandler = new MainActivityClickHandler(this);
        activityMainBinding.setClickHandler(clickHandler);
        searchBar = findViewById(R.id.searchBar);

        RecyclerView recyclerView = activityMainBinding.recyclerView;
        /* activityMainBinding.recyclerView will automatically refer to RecyclerView in
           binding class (here MainActivity) which is generated based on XML Layout */
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        contactDatabase = Room.databaseBuilder(getApplicationContext(),ContactDatabase.class,"ContactsDB").allowMainThreadQueries().build();
        myCustomAdapter = new ContactAdapter(contacts,MainActivity.this);

        //Load Data
        LoadData();

        recyclerView.setAdapter(myCustomAdapter);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterContacts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterContacts(newText);
                return false;
            }
        });

    }


    public class MainActivityClickHandler{
        Context context;

        public MainActivityClickHandler(Context context) {
            this.context = context;
        }

        public void onFABClick(View view){
            //view here describes the Floating Action Button
            Intent i = new Intent(MainActivity.this,AddNewContactActivity.class);

            startActivityForResult(i,1);
            //if this 1 gets converted to RESULT_OK then it saves contact
        }
    }


    public void LoadData(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //on background
                contacts.addAll(contactDatabase.getContactDAO().getAllContacts());

                //on post execution
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCustomAdapter.setContacts(contacts);
                        myCustomAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    public void filterContacts(String query){
        ArrayList<Contact> filteredContacts = new ArrayList<>();
        for(Contact contact: contacts){
            if(contact.getName().toLowerCase().contains(query.toLowerCase())
                || contact.getNumber().toLowerCase().contains(query.toLowerCase())){

                filteredContacts.add(contact);
            }
        }

        if(query.isEmpty()){
            myCustomAdapter.setContacts(contacts);
            myCustomAdapter.notifyDataSetChanged();
        }else{
            myCustomAdapter.setContacts(filteredContacts);
            myCustomAdapter.notifyDataSetChanged();
        }
    }


    @Override
    //this is the method will finally add contact to Database and list
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            String name = data.getStringExtra("Name");
            String number = data.getStringExtra("Number");
            Integer icon = data.getIntExtra("iconColor", 0);

            //Log.d("check","REACHED HERE" );
            Contact newContact = new Contact(0,name,number,icon);
            AddNewContact(newContact);
        }

    }


    private void AddNewContact(Contact contact){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //on background
                contactDatabase.getContactDAO().insert(contact);
                contacts.add(contact);

                //on post execution
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCustomAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


}