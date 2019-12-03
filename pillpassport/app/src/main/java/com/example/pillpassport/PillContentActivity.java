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

        translateInfo();

        mImageView.setImageBitmap(picture);
//        mImageView.setAdjustViewBounds(true);


        Toast.makeText(this, "On 2nd view", Toast.LENGTH_SHORT).show();

    }

    public void readInfo(View view) {

    }

    public void translateInfo() {

        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
                        .setTargetLanguage(FirebaseTranslateLanguage.DE)
                        .build();
        final FirebaseTranslator englishGermanTranslator =
                FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();

        englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                // Model downloaded successfully. Okay to start translating.
                                // (Set a flag, unhide the translation UI, etc.)
                                Log.d("SUCCESS", "success1");
                                Toast.makeText(PillContentActivity.this, "translation can start", Toast.LENGTH_SHORT).show();
                                englishGermanTranslator.translate(drug_info)
                                        .addOnSuccessListener(
                                                new OnSuccessListener<String>() {
                                                    @Override
                                                    public void onSuccess(@NonNull String translatedText) {
                                                        // Translation successful.

                                                        Log.d("TRANSLATED", "TRANSLATED TEXT: "  + translatedText);
                                                    }
                                                })
                                        .addOnFailureListener(
                                                new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Error.
                                                        // ...

                                                        Log.d("FAIL", "failedHereToo");
                                                    }
                                                });
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...

                                Log.d("FAIL", "failed");
                                Toast.makeText(PillContentActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        });
    }
}
