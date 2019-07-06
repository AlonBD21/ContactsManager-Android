package contactsproject.alonbendov.contactsproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Permission;

public class ViewContactActivity extends AppCompatActivity {
    private Contact contact;
    private Bitmap pic;
    ImageView imageView;

    TextView nameData;
    TextView phoneData;
    TextView mailData;
    TextView siteData;
    TextView addressData;
    TextView bdayData;
    TextView timeData;

    int PR_CALL = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PR_CALL){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        nameData = findViewById(R.id.name_et);
        phoneData = findViewById(R.id.phone_et);
        mailData = findViewById(R.id.mail_et);
        siteData = findViewById(R.id.site_et);
        addressData = findViewById(R.id.address_et);
        bdayData = findViewById(R.id.value_date_tv);
        timeData = findViewById(R.id.value_time_tv);
        imageView = findViewById(R.id.pic_iv);

        contact = (Contact) getIntent().getExtras().get(FirstActivity.CONTACT_EXTRA);
        pic = contact.getPic();
        if (pic != null) {
            imageView.setImageBitmap(pic);
        } else
            imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.contact_default_png));

        nameData.setText(contact.getName());
        phoneData.setText(contact.getPhone());
        mailData.setText(contact.getMail());
        siteData.setText(contact.getWebsite());
        addressData.setText(contact.getAddress());
        bdayData.setText(contact.getDate());
        timeData.setText(contact.getTime());


        phoneData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = ((TextView) v).getText().toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        callPhone(txt);
                    }else{
                        requestPermissions(new String[] {Manifest.permission.CALL_PHONE},PR_CALL);
                    }
                }else{
                    callPhone(txt);
                }
            }
            private void callPhone(String txt){
                Uri uri = Uri.parse("tel://"+txt);
                Intent intent = new Intent(Intent.ACTION_CALL,uri);
                startActivity(intent);
            }
        });


    }
}
