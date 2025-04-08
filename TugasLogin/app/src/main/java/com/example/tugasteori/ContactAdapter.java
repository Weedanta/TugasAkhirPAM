package com.example.tugasteori;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements Filterable {

    private ArrayList<Contact> contacts;
    private ArrayList<Contact> contactsFiltered;
    private Context ctx;
    private LayoutInflater inflater;

    public ContactAdapter(Context ctx, ArrayList<Contact> contacts) {
        this.ctx = ctx;
        this.contacts = contacts;
        this.contactsFiltered = new ArrayList<>(contacts);
        this.inflater = LayoutInflater.from(ctx);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = contactsFiltered.get(position);
        holder.nameTextView.setText(contact.name);
        holder.phoneTextView.setText(contact.phone);
        holder.emailTextView.setText(contact.email);

        int avatarResId = ctx.getResources().getIdentifier(contact.avatarName, "drawable", ctx.getPackageName());
        Glide.with(ctx)
                .load(avatarResId)
                .into(holder.avatarImageView);
    }

    @Override
    public int getItemCount() {
        return contactsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Contact> filteredContacts = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredContacts.addAll(contacts);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Contact c : contacts) {
                        if (c.name.toLowerCase().contains(filterPattern)) {
                            filteredContacts.add(c);
                        }
                    }
                }

                results.values = filteredContacts;
                results.count = filteredContacts.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactsFiltered.clear();
                if (results.values != null) {
                    contactsFiltered.addAll((ArrayList) results.values);
                }
                notifyDataSetChanged();
            }
        };
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView;
        TextView phoneTextView;
        TextView emailTextView;
        ImageView avatarImageView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            phoneTextView = itemView.findViewById(R.id.item_phone);
            emailTextView = itemView.findViewById(R.id.item_email);
            avatarImageView = itemView.findViewById(R.id.ItemAvatar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contactsFiltered.get(pos).phone));
            ctx.startActivity(intent);
        }
    }
}
