package com.example.pillpassport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * TODO: picture must be oriented correctly before BitMap
 * https://firebase.google.com/docs/ml-kit/android/recognize-text
 */

public class MainActivity extends AppCompatActivity {

    private static final int PICTURE_REQUEST = 1;
    static final String PICTURE_NAME = "picture";
    static final String PICTURE_TEXT = "text";
    private ImageView mImageView;
    private Intent mIntent;
    private FirebaseVisionTextRecognizer detector;
    private BitmapFactory.Options mOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.scanner);
        detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
    }

    public void startScan(View view) {
        if (view == mImageView) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, PICTURE_REQUEST);
        }
    }

    // Helper function
    private static void log(String msg) {
        Log.d("LABEL", msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICTURE_REQUEST && resultCode == RESULT_OK && data != null) {

            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap bm = (Bitmap) extras.get("data");

                mImageView.setImageBitmap(bm);

                // Move the Bitmap to the next view by converting to byteArray first
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                mIntent = new Intent(this, PillContentActivity.class);
                mIntent.putExtra(PICTURE_NAME, byteArray);

                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bm);
                Task<FirebaseVisionText> result =
                        detector.processImage(image)
                                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                    @Override
                                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                        // Task completed successfully
                                        processTextBlock(firebaseVisionText);

                                        // In the documentation
//                                        for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
//                                            Rect boundingBox = block.getBoundingBox();
//                                            Point[] cornerPoints = block.getCornerPoints();
//                                            String text = block.getText();
//
//                                            for (FirebaseVisionText.Line line: block.getLines()) {
//                                                // ...
//                                                for (FirebaseVisionText.Element element: line.getElements()) {
//                                                    // ...
//                                                }
//                                            }
//                                        }

                                    }
                                })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Task failed with an exception
                                                // ...
                                                e.printStackTrace();
                                            }
                                        });
            }

        }
    }

    private void processTextBlock(FirebaseVisionText result) {
        String resultText = result.getText();

        // TODO: pass into PillContectActivity
        mIntent.putExtra(PICTURE_TEXT, resultText);
        startActivity(mIntent);

        for (FirebaseVisionText.TextBlock block: result.getTextBlocks()) {
            String blockText = block.getText();
            // Each blockText is a line
            Float blockConfidence = block.getConfidence();
            List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();

            for (FirebaseVisionText.Line line: block.getLines()) {
                String lineText = line.getText();
                Float lineConfidence = line.getConfidence();
                List<RecognizedLanguage> lineLanguages = line.getRecognizedLanguages();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (FirebaseVisionText.Element element: line.getElements()) {
                    String elementText = element.getText();
                    Float elementConfidence = element.getConfidence();
                    List<RecognizedLanguage> elementLanguages = element.getRecognizedLanguages();
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                }
            }
        }
    }



}
