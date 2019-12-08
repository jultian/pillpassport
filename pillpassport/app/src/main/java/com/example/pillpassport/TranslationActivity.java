package com.example.pillpassport;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

public class TranslationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private String drug_info;
    private TextView translated_textview;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_activity);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);



        Intent intent = getIntent();
        drug_info = intent.getStringExtra(PillContentActivity.TEXT_TO_TRANSLATE);




    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        Log.d("TRANSLATION" , "item selected: " + parent.getItemAtPosition(pos).toString());

        String item_selected = parent.getItemAtPosition(pos).toString();




        translated_textview = findViewById(R.id.textView);

        if (item_selected.equals("English")) {
            translated_textview.setText(drug_info);
        } else if (item_selected.equals("Spanish")) {
            FirebaseTranslatorOptions options =
                    new FirebaseTranslatorOptions.Builder()
                            .setSourceLanguage(FirebaseTranslateLanguage.EN)
                            .setTargetLanguage(FirebaseTranslateLanguage.ES)
                            .build();

            final FirebaseTranslator englishSpanishTranslator =
                    FirebaseNaturalLanguage.getInstance().getTranslator(options);

            FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                    .requireWifi()
                    .build();

            englishSpanishTranslator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void v) {
                                    Log.d("SUCCESS", "success1");
                                    Toast.makeText(TranslationActivity.this, "translation can start", Toast.LENGTH_SHORT).show();
                                    englishSpanishTranslator.translate(drug_info)
                                            .addOnSuccessListener(
                                                    new OnSuccessListener<String>() {
                                                        @Override
                                                        public void onSuccess(@NonNull String translatedText) {
                                                            // Translation successful.

                                                            Log.d("TRANSLATED", "TRANSLATED TEXT: "  + translatedText);


                                                            translated_textview.setText(translatedText);
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
                                    Toast.makeText(TranslationActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                }
                            });

        } else if (item_selected.equals("Japanese")) {

            FirebaseTranslatorOptions options =
                    new FirebaseTranslatorOptions.Builder()
                            .setSourceLanguage(FirebaseTranslateLanguage.EN)
                            .setTargetLanguage(FirebaseTranslateLanguage.JA)
                            .build();

            final FirebaseTranslator englishJapaneseTranslator =
                    FirebaseNaturalLanguage.getInstance().getTranslator(options);

            FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                    .requireWifi()
                    .build();


            englishJapaneseTranslator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void v) {
                                    // Model downloaded successfully. Okay to start translating.
                                    // (Set a flag, unhide the translation UI, etc.)
                                    Log.d("SUCCESS", "success1");
                                    Toast.makeText(TranslationActivity.this, "translation can start", Toast.LENGTH_SHORT).show();
                                    englishJapaneseTranslator.translate(drug_info)
                                            .addOnSuccessListener(
                                                    new OnSuccessListener<String>() {
                                                        @Override
                                                        public void onSuccess(@NonNull String translatedText) {
                                                            // Translation successful.

                                                            Log.d("TRANSLATED", "TRANSLATED TEXT: "  + translatedText);


                                                            translated_textview.setText(translatedText);
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
                                    Toast.makeText(TranslationActivity.this, "failed", Toast.LENGTH_SHORT).show();
                                }
                            });


        } else if (item_selected.equals("German")) {

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
                                Toast.makeText(TranslationActivity.this, "translation can start", Toast.LENGTH_SHORT).show();
                                englishGermanTranslator.translate(drug_info)
                                        .addOnSuccessListener(
                                                new OnSuccessListener<String>() {
                                                    @Override
                                                    public void onSuccess(@NonNull String translatedText) {
                                                        // Translation successful.

                                                        Log.d("TRANSLATED", "TRANSLATED TEXT: "  + translatedText);


                                                        translated_textview.setText(translatedText);
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
                                Toast.makeText(TranslationActivity.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        });
    }





    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback

        Log.d("TRANSLATION", "nothing selected");
    }
}
