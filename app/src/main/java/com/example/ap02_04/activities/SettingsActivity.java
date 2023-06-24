package com.example.ap02_04.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.ap02_04.R;
import com.example.ap02_04.entities.ServerUrl;
import com.example.ap02_04.webservices.WebChat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.makeramen.roundedimageview.RoundedImageView;

public class SettingsActivity extends AppCompatActivity {

    ImageButton btnBack;
    MaterialTextView tvDisplayName;
    RoundedImageView ivProfilePic;
    MaterialTextView tvLogout;
    Switch sTheme;
    EditText inputUrl;
    MaterialButton btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnBack = findViewById(R.id.btnBack);
        tvLogout = findViewById(R.id.tvLogout);
        sTheme = findViewById(R.id.sTheme);
        inputUrl = findViewById(R.id.inputUrl);
        btnApply = findViewById(R.id.btnApply);
        tvDisplayName = findViewById(R.id.tvDisplayName);
        ivProfilePic = findViewById(R.id.ivProfilePic);

        if (WebChat.getUser() != null) {
            loadUserDetails();
        }

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatsActivity.class);
            startActivity(intent);
        });

        tvLogout.setOnClickListener(v -> {
            WebChat.setToken(null);
            startActivity(new Intent(this, LoginActivity.class));
        });


        btnApply.setOnClickListener(v -> {
            if (isValidSettingsDetails()) {
                WebChat.setBaseUrl(inputUrl.getText().toString());
                WebChat.getDb().serverUrlDao().clear();
                WebChat.getDb().serverUrlDao().insert(new ServerUrl(inputUrl.getText().toString()));
                WebChat.setToken(null);
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        // handle theme change
        sTheme.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public Boolean isValidSettingsDetails() {
        if (inputUrl.getText().toString().trim().isEmpty()) {
            showToast("Enter name");
            return false;
        } else if (!Patterns.WEB_URL.matcher(inputUrl.getText().toString()).matches()) {
            showToast("Enter valid URL");
            return false;
        } else {
            return true;
        }
    }


    private void loadUserDetails() {
        tvDisplayName.setText(WebChat.getUser().getDisplayName());
        inputUrl.setText(WebChat.getContext().getString(R.string.BaseUrl));
        byte[] bytes = Base64.decode(WebChat.getUser().getProfilePic(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        ivProfilePic.setImageBitmap(bitmap);
    }

    // remove eye flickers
    public void recreate() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(getIntent());
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}