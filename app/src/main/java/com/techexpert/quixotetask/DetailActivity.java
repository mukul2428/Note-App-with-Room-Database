package com.techexpert.quixotetask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {


    TextView textView1,textView2;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String title = getIntent().getStringExtra("data1");
        String description = getIntent().getStringExtra("data2");
        String image = getIntent().getStringExtra("data3");

        textView1 = findViewById(R.id.title);
        textView2 = findViewById(R.id.des);
        img = findViewById(R.id.img);

        Bitmap photo=BitmapManager.base64ToBitmap(image);
        img.setImageBitmap(photo);
        textView1.setText(title);
        textView2.setText(description);

    }
}