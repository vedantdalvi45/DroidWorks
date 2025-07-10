package com.example.contactsclonningapp.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactsclonningapp.R;

import java.util.ArrayList;

public class RecyclerContactsAdapter extends RecyclerView.Adapter<RecyclerContactsAdapter.ViewHolder> {
    Context context;
    ArrayList<Contacts> contactList;
    public RecyclerContactsAdapter(Context context, ArrayList<Contacts> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contacts_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.contactImage.setImageResource(contactList.get(position).contactImage);
        holder.contactName.setText(contactList.get(position).contactName);
        holder.contactNumber.setText(contactList.get(position).contactNumbar);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView contactImage;
        TextView contactName;
        TextView contactNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contactImage = itemView.findViewById(R.id.contact_profile);
            contactName = itemView.findViewById(R.id.contact_name);
            contactNumber = itemView.findViewById(R.id.contact_number);
        }
    }
}
