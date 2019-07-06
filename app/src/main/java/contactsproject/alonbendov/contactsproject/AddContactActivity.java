package contactsproject.alonbendov.contactsproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;


public class AddContactActivity extends AppCompatActivity {
    ImageButton save;
    ImageButton cancel;
    ImageButton takePic;
    ImageButton deletePic;
    ImageView imageView;

    TextView nameData;
    TextView phoneData;
    TextView mailData;
    TextView siteData;
    TextView addressData;
    TextView bdayData;
    TextView timeData;

    ImageButton pickDate;
    ImageButton pickTime;

    Bitmap pic;

    DataManager dm;

    final int PIC_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_editable);

        final int index = getIntent().getIntExtra(FirstActivity.INDEX_EXTRA,-1);
        dm = DataManager.getInstance(this);

        save = findViewById(R.id.save_btn);
        cancel = findViewById(R.id.cancel_btn);
        takePic = findViewById(R.id.camera_btn);
        deletePic = findViewById(R.id.delete_pic_btn);
        imageView = findViewById(R.id.pic_iv);

        nameData = findViewById(R.id.name_et);
        phoneData = findViewById(R.id.phone_et);
        mailData = findViewById(R.id.mail_et);
        siteData = findViewById(R.id.site_et);
        addressData = findViewById(R.id.address_et);
        bdayData = findViewById(R.id.value_date_tv);
        timeData = findViewById(R.id.value_time_tv);

        pickDate = findViewById(R.id.date_btn);
        pickTime = findViewById(R.id.time_btn);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,PIC_REQUEST);
            }
        });
        deletePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic = null;
                imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.contact_default_png));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact c = new Contact(nameData.getText().toString(),phoneData.getText().toString(),mailData.getText().toString(),addressData.getText().toString(),siteData.getText().toString(),bdayData.getText().toString(),timeData.getText().toString(),pic);
                if (index!=-1) dm.replaceContact(c,index);
                else dm.addContact(c);
                finish();
            }
        });

        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date now = Calendar.getInstance().getTime();
                TimePickerDialog dialog = new TimePickerDialog(AddContactActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeData.setText(hourOfDay+":"+minute);
                    }
                },now.getHours(),now.getMinutes(),true);
                dialog.show();
            }
        });

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(AddContactActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        bdayData.setText(dayOfMonth+"."+(month+1)+"."+year);
                    }
                },2002,1,6);
                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PIC_REQUEST && resultCode == RESULT_OK) {
            pic = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(pic);
        }
    }

}
