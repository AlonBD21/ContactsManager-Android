package contactsproject.alonbendov.contactsproject;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class EditContactActivity extends AddContactActivity {
    private Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Super Method Already Contains setContentView()

        contact = (Contact) getIntent().getExtras().get(FirstActivity.CONTACT_EXTRA);

        pic = contact.getPic();
        if (pic != null){
            imageView.setImageBitmap(pic);
        }
        else imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.contact_default_png));

        nameData.setText(contact.getName());
        phoneData.setText(contact.getPhone());
        mailData.setText(contact.getMail());
        siteData.setText(contact.getWebsite());
        addressData.setText(contact.getAddress());
        bdayData.setText(contact.getDate());
        timeData.setText(contact.getTime());


    }
}
