package com.example.racegaragepro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnBooking, btnMaps, btnShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnBooking = findViewById(R.id.btnBookingNow);
        btnMaps = findViewById(R.id.btnMapsHome);
        btnShop = findViewById(R.id.btnShop);

        // MASUK KE HALAMAN BOOKING
        btnBooking.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            MainActivity.class
                    );

            startActivity(intent);
        });

        // BUKA MAPS
        btnMaps.setOnClickListener(v -> {

            Uri uri =
                    Uri.parse(
                            "geo:0,0?q=RaceGarage Workshop Jakarta"
                    );

            Intent mapIntent =
                    new Intent(Intent.ACTION_VIEW, uri);

            startActivity(mapIntent);
        });

        // TOKO SPAREPART
        btnShop.setOnClickListener(v -> {

            Uri uri =
                    Uri.parse(
                            "https://www.tokopedia.com/"
                    );

            Intent intent =
                    new Intent(
                            Intent.ACTION_VIEW,
                            uri
                    );

            startActivity(intent);
        });
    }
}