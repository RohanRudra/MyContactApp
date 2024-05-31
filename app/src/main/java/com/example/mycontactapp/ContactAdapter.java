package com.example.mycontactapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    private ArrayList<Contact> contacts;
    private MainActivity mainActivity;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contact contact = contacts.get(holder.getAdapterPosition());
        Character c = contact.getName().charAt(0);
        holder.name.setText(contact.getName());
        holder.number.setText(contact.getNumber());

        /////////////////////////////////////////////////////
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

        Random r = new Random();
        int i1 = r.nextInt(8- 0) + 0;
        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.parseColor(colors.get(i1)));

        holder.icon.setBackground(draw);
        holder.icon.setText(c.toString());
        //////////////////////////////////////////////////////

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mainActivity, contact_detail_activity.class);
                i.putExtra("Icon",i1);
                i.putExtra("Name",contact.getName());
                i.putExtra("Number",contact.getNumber());
                v.getContext().startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(contacts != null){
            return contacts.size();
        }
        else{
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView number;
        private TextView icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.number = itemView.findViewById(R.id.number);
            this.icon = itemView.findViewById(R.id.icon);
        }
    }

    public ContactAdapter(ArrayList<Contact> contacts,MainActivity mainActivity) {
        this.contacts = contacts;
        this.mainActivity = mainActivity;
    }

    public void setContacts(ArrayList<Contact> contacts){
        this.contacts = contacts;
        notifyDataSetChanged();
    }


}
