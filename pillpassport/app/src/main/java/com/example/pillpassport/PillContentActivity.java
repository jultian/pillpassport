package com.example.pillpassport;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PillContentActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mTextView;
    private Button mReadButton;
    private Button mTranslateButton;

    private Bitmap picture;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_content);
        mImageView = findViewById(R.id.labelPic);
        mTextView = findViewById(R.id.info);
        mReadButton = findViewById(R.id.read);
        mTranslateButton = findViewById(R.id.translate);

        // Set the ImageView to the picture taken
        Intent intent = getIntent();
        byte[] byteArray = intent.getByteArrayExtra(MainActivity.PICTURE_NAME);
        picture = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        mImageView.setImageBitmap(picture);

        Toast.makeText(this, "On 2nd view", Toast.LENGTH_SHORT).show();

    }

    public void readInfo(View view) {

    }

    public void translateInfo(View view) {

    }
}
