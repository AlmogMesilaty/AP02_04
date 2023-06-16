package com.example.ap02_04.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ap02_04.R;
import com.example.ap02_04.api.ClientAPI;
import com.example.ap02_04.entities.UserNoId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> imagePickerLauncher;
    Button btnLogin;
    EditText etUsername;
    EditText etPassword;
    EditText etDisplayName;
    ImageView ivProfilePic;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etDisplayName = findViewById(R.id.etDisplayName);


        ivProfilePic = findViewById(R.id.ivProfilePic);
        ivProfilePic.setOnClickListener(v -> selectImage());
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        ivProfilePic.setImageURI(uri);
                    }
                });

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(view -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(view -> {
            if (TextUtils.isEmpty(etDisplayName.getText().toString())
                    || TextUtils.isEmpty(etUsername.getText().toString())
                    || TextUtils.isEmpty(etPassword.getText().toString())) {

                String message = "All fields required..";
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            } else {
                UserNoId user = new UserNoId(etUsername.getText().toString(),
                        etPassword.getText().toString(),
                        etDisplayName.getText().toString(),
                        Integer.toString(ivProfilePic.getId()));

                registerUser(user);
            }
        });

    }

    private void selectImage() {
        imagePickerLauncher.launch("image/*");
    }

    public void registerUser(UserNoId user) {
        Call<Void> call = ClientAPI.getService().addUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    String message = "Saved!";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    String message = "An error occurred";
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
