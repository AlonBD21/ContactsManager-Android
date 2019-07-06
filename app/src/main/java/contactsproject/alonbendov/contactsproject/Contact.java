package contactsproject.alonbendov.contactsproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class Contact implements Serializable, Comparable<Contact> {
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String website;
    private String date;
    private String time;
    boolean hasImage = false;
    private transient Bitmap pic;

    public Contact(
    ) {
    }

    public Contact(String name, String phone, String mail, String address, String website, String date, String time, Bitmap pic) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.website = website;
        this.date = date;
        this.time = time;
        this.pic = pic;
    }

    @Override
    public int compareTo(Contact o) {
        return this.getName().compareTo(o.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        if(pic != null){
            pic.compress(Bitmap.CompressFormat.JPEG, 100, out);}

    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
            pic = BitmapFactory.decodeStream(in);
    }

}