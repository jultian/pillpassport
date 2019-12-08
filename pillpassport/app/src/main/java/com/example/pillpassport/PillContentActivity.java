package com.example.pillpassport;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

public class PillContentActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mTextView;
    private Button mReadButton;
    private Button mTranslateButton;
    private String drug_info;

    private Bitmap picture;

    static final String TEXT_TO_TRANSLATE = "text";



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
        drug_info = intent.getStringExtra(MainActivity.PICTURE_TEXT);
        byte[] byteArray = intent.getByteArrayExtra(MainActivity.PICTURE_NAME);
        picture = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        mTextView.setText(drug_info);

//        translateInfo();

        mImageView.setImageBitmap(picture);
//        mImageView.setAdjustViewBounds(true);


        Toast.makeText(this, "On 2nd view", Toast.LENGTH_SHORT).show();

    }

    public void readInfo(View view) {

    }

    public void translateInfo(View view) {




        Intent intent = new Intent(this, TranslationActivity.class);
        intent.putExtra(TEXT_TO_TRANSLATE, drug_info);
        startActivity(intent);


    }
}
