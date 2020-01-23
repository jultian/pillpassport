package com.example.pillpassport;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.speech.tts.TextToSpeech;
import java.util.Locale;


public class PillContentActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mTextView;
    private Button mReadButton;
    private Button mTranslateButton;
    private String drug_info;

    private Bitmap picture;

    static final String TEXT_TO_TRANSLATE = "text";
    static TextToSpeech t1;


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
        mImageView.setImageBitmap(picture);
//        mImageView.setAdjustViewBounds(true);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if ( i != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });

    }

    public void readInfo(View view) {
        String toSpeak = drug_info;
        t1.speak(toSpeak, TextToSpeech.QUEUE_ADD, null);
    }

    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    public void translateInfo(View view) {
        Intent intent = new Intent(this, TranslationActivity.class);
        intent.putExtra(TEXT_TO_TRANSLATE, drug_info);
        startActivity(intent);
    }
}
