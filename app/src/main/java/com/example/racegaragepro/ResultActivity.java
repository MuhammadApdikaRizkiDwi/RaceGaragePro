package com.example.racegaragepro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView tvResult;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = findViewById(R.id.tvResult);
        btnBack = findViewById(R.id.btnBack);

        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String service = getIntent().getStringExtra("service");
        String vehicle = getIntent().getStringExtra("vehicle");
        String date = getIntent().getStringExtra("date");
        String time = getIntent().getStringExtra("time");
        String tambahan = getIntent().getStringExtra("tambahan");

        int total = getIntent().getIntExtra("total", 0);

        String result =
                "Nama Customer : " + name +
                        "\n\nNomor HP : " + phone +
                        "\n\nJenis Service : " + service +
                        "\n\nKendaraan : " + vehicle +
                        "\n\nTanggal Reservasi : " + date +
                        "\n\nJam Reservasi : " + time +
                        "\n\nPaket Tambahan : " + tambahan +
                        "\n\n----------------------------------" +
                        "\n\nTOTAL PEMBAYARAN" +
                        "\nRp " + total;

        tvResult.setText(result);

        btnBack.setOnClickListener(v -> finish());
    }
}