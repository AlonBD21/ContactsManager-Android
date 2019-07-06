package contactsproject.alonbendov.contactsproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FirstActivityAdapter extends RecyclerView.Adapter<FirstActivityAdapter.ContactViewHolder> {

    //OnItemClickListener
    interface ContactClickListener{
        void OnClick(int index);
        void OnLongClick(int index);
    }
    //Class
    List<Contact> contacts;
    ContactClickListener listener;

    public FirstActivityAdapter(List<Contact> contacts, ContactClickListener listener) {
        this.contacts = contacts;
        this.listener = listener;
    }
    public void dataChange(ArrayList<Contact> contacts){
        this.contacts = contacts;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView picIv;
        TextView nameTv;
        TextView phoneTv;

        Bitmap deafultPic;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.OnLongClick(getAdapterPosition());
                    return true;
                }
            });
            picIv = itemView.findViewById(R.id.card_iv);
            nameTv = itemView.findViewById(R.id.card_name_tv);
            phoneTv = itemView.findViewById(R.id.card_phone_tv);
            deafultPic = BitmapFactory.decodeResource(itemView.getResources(),R.drawable.contact_default_png);
        }

        public void setDefPic(){
            picIv.setImageBitmap(deafultPic);
        }
    }


    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_card,viewGroup,false);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {
        Contact contact = contacts.get(i);
        contactViewHolder.nameTv.setText(contact.getName());
        contactViewHolder.phoneTv.setText(contact.getPhone());
        Bitmap pic = contact.getPic();
        if (pic == null){
            contactViewHolder.setDefPic();
        }else contactViewHolder.picIv.setImageBitmap(pic);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
