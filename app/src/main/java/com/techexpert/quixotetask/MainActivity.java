package com.techexpert.quixotetask;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.techexpert.quixotetask.Model.User;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    EditText editText1,editText2;
    Button btAdd;
    RecyclerView recyclerView;
    Bitmap bitmap;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;
    FloatingActionButton floatingActionButton;
    public User user;
    public static User s;
//    public static final String MyPREFERENCES = "MyPrefs" ;
//    public static final String Name = "Key";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = findViewById(R.id.edit_text1);
        editText2 = findViewById(R.id.edit_text2);
        btAdd = findViewById(R.id.bt_add);
        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.fab);
        bitmap=null;

        user = (User) getIntent().getSerializableExtra("User");
        s = (User) getIntent().getSerializableExtra("User");
//        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//
//        editor.putString(Name, user.getUserName()+".db");
//        editor.commit();

        TextView tvUser = findViewById(R.id.tvUser);
        if (user != null)
        {

            checkPermission();
            tvUser.setText("WELCOME!! " + user.getUserName().toUpperCase());
            //initialize database
            database = Room.databaseBuilder(this, RoomDB.class, user.getUserName()+".db")
                    .allowMainThreadQueries()
                    .build();
            //store database value in data list
            dataList = database.mainDao().getAll();

            //initialize linear layout manager
            linearLayoutManager = new LinearLayoutManager(this);
            //set layout manager
            recyclerView.setLayoutManager(linearLayoutManager);
            //initialize adapter
            adapter = new MainAdapter(MainActivity.this,dataList);
            //set adapter
            recyclerView.setAdapter(adapter);

            btAdd.setOnClickListener(view ->
            {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),1);
            });

            floatingActionButton.setOnClickListener(view ->
            {

                //get string from edit text
                String sText1 = editText1.getText().toString().trim();
                String sText2 = editText2.getText().toString().trim();
                //check condition
                if(!sText1.equals("")&&!sText2.equals("") && bitmap!=null)
                {
                    //when text is not empty
                    //initialize main data
                    MainData data = new MainData();
                    //set text on main data
                    data.setText1(sText1);
                    data.setText2(sText2);
                    String photo= BitmapManager.bitmapToBase64(bitmap);
                    data.setImg(photo);
                    //insert text in database
                    database.mainDao().insert(data);
                    //clear edit text
                    editText1.setText("");
                    editText2.setText("");
                    // notify when data is inserted
                    //dataList.addAll(database.mainDao().getAll());
                    dataList.add(data);
                    adapter.notifyDataSetChanged();
                }

            });

        }
        else
        {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap=(Bitmap)data.getExtras().get("data");
    }

    public static String m()
    {
        return s.getUserName()+".db";
    }

    public void checkPermission()
    {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted()
            {

            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };

        TedPermission.with(MainActivity.this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.CAMERA)
                .check();
    }

}