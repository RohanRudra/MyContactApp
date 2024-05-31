package com.example.mycontactapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ContactDatabase contactDatabase;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ActivityMainBinding activityMainBinding;
    private ContactAdapter myCustomAdapter;

    private MainActivityClickHandler clickHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        clickHandler = new MainActivityClickHandler(this);
        activityMainBinding.setClickHandler(clickHandler);

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


    @Override
    //this is the method will finally add contact to Database and list
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            String name = data.getStringExtra("Name");
            String number = data.getStringExtra("Number");

            Contact newContact = new Contact(0,name,number);
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