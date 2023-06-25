package com.example.ap02_04.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ap02_04.R;
import com.example.ap02_04.api.ClientAPI;
import com.example.ap02_04.entities.UserNoId;
import com.example.ap02_04.webservices.WebChat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> imagePickerLauncher;
    MaterialTextView tvLogin;
    EditText inputUsername;
    EditText inputPassword;
    EditText inputDisplayName;
    RoundedImageView ivProfilePic;
    MaterialButton btnRegister;

    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        WebChat.setContext(getApplicationContext());

        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        inputDisplayName = findViewById(R.id.inputDisplayName);

        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(view -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });

        ivProfilePic = findViewById(R.id.ivProfilePic);
        ivProfilePic.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });


        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(view -> {
            if (isValidRegisterDetails()) {
                UserNoId user = new UserNoId(inputUsername.getText().toString(),
                        inputPassword.getText().toString(),
                        inputDisplayName.getText().toString(),
                        encodedImage);
                registerUser(user);
            }
        });

    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void registerUser(UserNoId user) {
        Call<Void> call = ClientAPI.getService().addUser(WebChat.getToken(), user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showToast("Saved!");
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else if (response.message() == "Conflict") {
                    showToast("User name taken");
                } else {
                    showToast("An error happened" + response.message());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast(t.getLocalizedMessage());
            }
        });
    }

    // handle image selection by the user
    private String encodeImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        ivProfilePic.setImageBitmap(bitmap);
                        encodedImage = encodeImage(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    private Boolean isValidRegisterDetails() {
        if (encodedImage == null ) {
            showToast("Select profile picture");
            return false;
        } else if (inputUsername.getText().toString().trim().isEmpty()) {
            showToast("Enter display name");
            return false;
        } else if (inputUsername.getText().toString().trim().isEmpty()) {
            showToast("Enter name");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputUsername.getText().toString()).matches()) {
            showToast("Enter valid Email");
            return false;
        } else if (inputPassword.getText().toString().trim().isEmpty()) {
        showToast("Enter Password");
        return false;
        } else {
            return true;
        }
    }

}
