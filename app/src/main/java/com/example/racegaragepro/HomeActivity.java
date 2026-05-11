package com.example.racegaragepro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnBookingNow;
    Button btnShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // INISIALISASI BUTTON
        btnBookingNow = findViewById(R.id.btnBookingNow);
        btnShop = findViewById(R.id.btnShop);

        // PINDAH KE HALAMAN BOOKING
        btnBookingNow.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            MainActivity.class
                    );

            startActivity(intent);
        });

        // ONLINE SHOP SHOPEE
        btnShop.setOnClickListener(v -> {

            Uri uri = Uri.parse(
                    "https://shopee.co.id/search?keyword=sparepart%20racing"
            );

            Intent intent =
                    new Intent(Intent.ACTION_VIEW, uri);

            startActivity(intent);
        });
    }
}