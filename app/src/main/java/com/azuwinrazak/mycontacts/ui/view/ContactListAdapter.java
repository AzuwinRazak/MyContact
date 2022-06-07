package com.azuwinrazak.mycontacts.ui.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.azuwinrazak.mycontacts.R;
import com.azuwinrazak.mycontacts.data.Model.Contact;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.MyViewHolder> {
    List<Contact> contactList;
    Context context;
    String letter;
    private OnItemClicked onClick;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phoneNo;
        public ImageView letterImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            letterImg = (ImageView) itemView.findViewById(R.id.ivLetter);
            name = (TextView) itemView.findViewById(R.id.tvName);
            phoneNo = (TextView) itemView.findViewById(R.id.tvPhoneNo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onClick.onItemClick(position);
                }
            });

        }
    }


    public ContactListAdapter(Context context, @NonNull List<Contact> contactList) {
        this.contactList = contactList;
        this.context = context;
    }


    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Contact contactListDetail = contactList.get(position);
        holder.name.setText(contactListDetail.getName());
        holder.phoneNo.setText(contactListDetail.getPhoneNo());
        letter = String.valueOf(contactListDetail.getName().charAt(0));
        TextDrawable drawable = new TextDrawable.Builder()
                .setColor(context.getResources().getColor(R.color.purple_200))
                .setShape(TextDrawable.SHAPE_ROUND)
                .setText(letter)
                .build();
        holder.letterImg.setImageDrawable(drawable);
    }

    public void updateContactList(List<Contact> items) {
        if (contactList != null && contactList.size() > 0) {
            contactList.clear();
            contactList.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void addContact(Contact contact) {
        this.contactList.add(0, contact);
        notifyItemInserted(0);
    }

    public void updateContactToPostion(Contact contact, int position) {
        this.contactList.set(position, contact);
        notifyItemChanged(position);
    }

    public void removeAt(int position) {
        this.contactList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, this.contactList.size());
    }

    @Override
    public int getItemCount() {
        return contactList != null ? contactList.size() : 0;
    }

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public void setOnClick(OnItemClicked onClick){
        this.onClick=onClick;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

