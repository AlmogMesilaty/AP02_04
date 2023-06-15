package com.example.ap02_04.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.ap02_04.R;

public class SignActivity extends AppCompatActivity {
    ActivityResultLauncher<String> imagePickerLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(v -> selectImage());
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        imageView.setImageURI(uri);
                    }
                });
    }


    private void selectImage() {
        imagePickerLauncher.launch("image/*");
    }
}
//
//    private static final int SELECT_IMAGE_REQUEST = 1;
//
//    private void selectImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, SELECT_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == SELECT_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
//            Uri imageUri = data.getData();
//            ImageView imageView = findViewById(R.id.imageView);
//            imageView.setImageURI(imageUri);
//        }
//    }
//}