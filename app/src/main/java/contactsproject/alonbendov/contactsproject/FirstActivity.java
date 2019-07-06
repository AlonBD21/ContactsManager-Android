package contactsproject.alonbendov.contactsproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_first, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addContact) {
            Intent intent = new Intent(this, AddContactActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public static final String Itag = "TAG";
    public static final String FILE = "ContactsData";
    public static final String CONTACT_EXTRA = "contact";
    public static final String INDEX_EXTRA = "index";
    RecyclerView recyclerView;
    DataManager dataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        dataManager = DataManager.getInstance(this);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        recyclerView.setAdapter(new FirstActivityAdapter(dataManager.getContacts(), new FirstActivityAdapter.ContactClickListener() {
            @Override
            public void OnLongClick(int index) {
                Intent intent = new Intent(FirstActivity.this, EditContactActivity.class);
                intent.putExtra(CONTACT_EXTRA, dataManager.getContactAt(index));
                intent.putExtra(INDEX_EXTRA,index);
                startActivity(intent);
            }
            public void OnClick(int index){
                Intent intent = new Intent(FirstActivity.this,ViewContactActivity.class);
                intent.putExtra(CONTACT_EXTRA,dataManager.getContactAt(index));
                intent.putExtra(INDEX_EXTRA,index);
                startActivity(intent);
            }
        }));

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
                if (i == ItemTouchHelper.START) {
                    final AlertDialog.OnClickListener ocl = new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int index = viewHolder.getAdapterPosition();
                            if (which == AlertDialog.BUTTON_POSITIVE) {
                                dataManager.removeContactAt(index);
                                ((FirstActivityAdapter)recyclerView.getAdapter()).dataChange(dataManager.getContacts());
                                recyclerView.getAdapter().notifyItemRemoved(index);
                            }else{
                                //TODO Check If Swipe Do Cancels
                                ((FirstActivityAdapter)recyclerView.getAdapter()).dataChange(dataManager.getContacts());
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }
                        }
                    };
                    AlertDialog.Builder adb = new AlertDialog.Builder(FirstActivity.this);
                    adb.setCancelable(true).setIcon(R.drawable.ic_delete).setPositiveButton(getString(R.string.dialog_remove_positive), ocl).setNegativeButton(getString(R.string.dialog_remove_negative), ocl)
                            .setMessage(getString(R.string.dialog_remove_info)).setTitle(getString(R.string.dialog_remove_title)).setOnCancelListener(new AlertDialog.OnCancelListener(){
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            ocl.onClick(dialog,AlertDialog.BUTTON_NEUTRAL);
                        }
                    });
                    adb.show();

                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((FirstActivityAdapter)recyclerView.getAdapter()).dataChange(dataManager.getContacts());
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
