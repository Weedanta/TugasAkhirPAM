package com.example.tugasteori;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ArrayList<Contact> contacts;
    private Context ctx;
    private LayoutInflater inflater;

    public ContactAdapter(Context ctx, ArrayList<Contact> contacts) {
        this.ctx = ctx;
        this.contacts = contacts;
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact c = contacts.get(position);
        holder.nameTextView.setText(c.name);
        holder.phoneTextView.setText(c.phone);
        holder.emailTextView.setText(c.email);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView phoneTextView;
        TextView emailTextView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            phoneTextView = itemView.findViewById(R.id.item_phone);
            emailTextView = itemView.findViewById(R.id.item_email);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Toast.makeText(ctx, "Nama: " + contacts.get(pos).name, Toast.LENGTH_SHORT).show();
        }
    }
}
