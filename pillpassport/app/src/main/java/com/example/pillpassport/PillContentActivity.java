package com.example.pillpassport;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PillContentActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mTextView;
    private Button mReadButton;
    private Button mTranslateButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_content);
        mImageView = findViewById(R.id.labelPic);
        mTextView = findViewById(R.id.info);
        mReadButton = findViewById(R.id.read);
        mTranslateButton = findViewById(R.id.translate);
    }

    public void readInfo(View view) {

    }

    public void translateInfo(View view) {

    }
}
