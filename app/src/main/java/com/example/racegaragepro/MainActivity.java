package com.example.racegaragepro;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etName, etPhone;

    Spinner spService;

    RadioGroup radioVehicle;

    CheckBox cbNitrogen, cbWash, cbOil;

    Button btnBooking,
            btnShare,
            btnGmail,
            btnMaps,
            btnDate,
            btnTime;

    TextView tvEstimate;

    String selectedDate = "";
    String selectedTime = "";

    // AGAR ESTIMASI AWAL TETAP 0
    boolean firstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // INISIALISASI
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);

        spService = findViewById(R.id.spService);

        radioVehicle = findViewById(R.id.radioVehicle);

        cbNitrogen = findViewById(R.id.cbNitrogen);
        cbWash = findViewById(R.id.cbWash);
        cbOil = findViewById(R.id.cbOil);

        btnBooking = findViewById(R.id.btnBooking);
        btnShare = findViewById(R.id.btnShare);
        btnGmail = findViewById(R.id.btnGmail);
        btnMaps = findViewById(R.id.btnMaps);

        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);

        tvEstimate = findViewById(R.id.tvEstimate);

        // TAMPILAN AWAL ESTIMASI
        tvEstimate.setText("Estimasi Harga : Rp 0");

        // DATA SPINNER
        String[] services = {
                "Pilih Paket Servis",
                "Tune Up Racing",
                "Ganti Oli Racing",
                "Dyno Test",
                "ECU Remap",
                "Servis Mesin Full"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        services
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spService.setAdapter(adapter);

        // POSISI AWAL SPINNER
        spService.setSelection(0);

        // UPDATE ESTIMASI HARGA
        spService.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id
                    ) {

                        // AGAR AWAL TIDAK LANGSUNG HITUNG
                        if (firstLoad) {

                            firstLoad = false;
                            return;
                        }

                        updateEstimate();
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent
                    ) {

                    }
                }
        );

        cbNitrogen.setOnCheckedChangeListener(
                (buttonView, isChecked) -> updateEstimate()
        );

        cbWash.setOnCheckedChangeListener(
                (buttonView, isChecked) -> updateEstimate()
        );

        cbOil.setOnCheckedChangeListener(
                (buttonView, isChecked) -> updateEstimate()
        );

        // TAMBAHKAN DI SINI
        radioVehicle.setOnCheckedChangeListener(
                (group, checkedId) -> updateEstimate()
        );

        // DATE PICKER
        btnDate.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            DatePickerDialog dialog =
                    new DatePickerDialog(
                            MainActivity.this,

                            (view, year, month, dayOfMonth) -> {

                                selectedDate =
                                        dayOfMonth + "/" +
                                                (month + 1) + "/" +
                                                year;

                                btnDate.setText(selectedDate);
                            },

                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );

            dialog.show();
        });

        // TIME PICKER
        btnTime.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            TimePickerDialog dialog =
                    new TimePickerDialog(
                            MainActivity.this,

                            (view, hourOfDay, minute) -> {

                                selectedTime =
                                        hourOfDay + ":" + minute;

                                btnTime.setText(selectedTime);
                            },

                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                    );

            dialog.show();
        });

        // BUTTON BOOKING
        btnBooking.setOnClickListener(v -> {

            String name =
                    etName.getText().toString().trim();

            String phone =
                    etPhone.getText().toString().trim();

            String service =
                    spService.getSelectedItem().toString();

            // VALIDASI PAKET SERVIS
            if (service.equals("Pilih Paket Servis")) {

                Toast.makeText(
                        MainActivity.this,
                        "Pilih paket servis terlebih dahulu",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // VALIDASI NAMA
            if (name.isEmpty()) {

                etName.setError("Nama wajib diisi");

                etName.requestFocus();

                return;
            }

            // VALIDASI HP
            if (phone.isEmpty()) {

                etPhone.setError("Nomor HP wajib diisi");

                etPhone.requestFocus();

                return;
            }

            // VALIDASI DIGIT
            if (phone.length() < 10) {

                etPhone.setError("Nomor HP minimal 10 digit");

                etPhone.requestFocus();

                return;
            }

            // VALIDASI KENDARAAN
            int selectedId =
                    radioVehicle.getCheckedRadioButtonId();

            if (selectedId == -1) {

                Toast.makeText(
                        MainActivity.this,
                        "Pilih jenis kendaraan",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // VALIDASI TANGGAL
            if (selectedDate.isEmpty()) {

                Toast.makeText(
                        MainActivity.this,
                        "Pilih tanggal reservasi",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // VALIDASI JAM
            if (selectedTime.isEmpty()) {

                Toast.makeText(
                        MainActivity.this,
                        "Pilih jam reservasi",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // AMBIL VEHICLE
            RadioButton rbVehicle =
                    findViewById(selectedId);

            String vehicle =
                    rbVehicle.getText().toString();

            // TOTAL HARGA
            int total = calculateTotal(service);

            String tambahan = "";

            if (cbNitrogen.isChecked()) {

                tambahan += "Nitrogen Ban ";
            }

            if (cbWash.isChecked()) {

                tambahan += "Cuci Premium ";
            }

            if (cbOil.isChecked()) {

                tambahan += "Ganti Oli Gratis ";
            }

            // PINDAH KE RESULT ACTIVITY
            Intent intent =
                    new Intent(
                            MainActivity.this,
                            ResultActivity.class
                    );

            intent.putExtra("name", name);
            intent.putExtra("phone", phone);
            intent.putExtra("service", service);
            intent.putExtra("vehicle", vehicle);
            intent.putExtra("date", selectedDate);
            intent.putExtra("time", selectedTime);
            intent.putExtra("tambahan", tambahan);
            intent.putExtra("total", total);

            startActivity(intent);
        });

        // SHARE BOOKING
        btnShare.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String service = spService.getSelectedItem().toString();

            int selectedId = radioVehicle.getCheckedRadioButtonId();

            String vehicle = "-";

            if (selectedId != -1) {

                RadioButton rbVehicle = findViewById(selectedId);

                vehicle = rbVehicle.getText().toString();
            }

            String tambahan = "";

            if (cbNitrogen.isChecked()) {
                tambahan += "• Nitrogen Ban\n";
            }

            if (cbWash.isChecked()) {
                tambahan += "• Cuci Premium\n";
            }

            if (cbOil.isChecked()) {
                tambahan += "• Ganti Oli Gratis\n";
            }

            if (tambahan.isEmpty()) {
                tambahan = "Tidak ada";
            }

            String shareText =
                    "🏁 RACEGARAGE PRO 🏁\n" +
                            "Sistem Reservasi Bengkel Balap Online\n\n" +

                            "📋 DETAIL BOOKING\n\n" +

                            "👤 Nama Customer : " + name + "\n" +
                            "📱 Nomor HP : " + phone + "\n" +
                            "🏎️ Kendaraan : " + vehicle + "\n" +
                            "🔧 Service : " + service + "\n" +
                            "📅 Tanggal : " + selectedDate + "\n" +
                            "⏰ Jam : " + selectedTime + "\n\n" +

                            "✨ Paket Tambahan :\n" +
                            tambahan + "\n" +

                            "✅ Reservasi berhasil dilakukan.\n\n" +

                            "Terima kasih telah menggunakan\n" +
                            "RaceGarage Pro 🚀";

            Intent shareIntent =
                    new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");

            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    shareText
            );

            startActivity(
                    Intent.createChooser(
                            shareIntent,
                            "Bagikan Booking"
                    )
            );
        });

        // SHARE KE GMAIL
        btnGmail.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String service = spService.getSelectedItem().toString();

            int selectedId = radioVehicle.getCheckedRadioButtonId();

            String vehicle = "-";

            if (selectedId != -1) {

                RadioButton rbVehicle = findViewById(selectedId);

                vehicle = rbVehicle.getText().toString();
            }

            String tambahan = "";

            if (cbNitrogen.isChecked()) {
                tambahan += "• Nitrogen Ban\n";
            }

            if (cbWash.isChecked()) {
                tambahan += "• Cuci Premium\n";
            }

            if (cbOil.isChecked()) {
                tambahan += "• Ganti Oli Gratis\n";
            }

            if (tambahan.isEmpty()) {
                tambahan = "Tidak ada";
            }

            String shareText =
                    "🏁 RACEGARAGE PRO 🏁\n" +
                            "Sistem Reservasi Bengkel Balap Online\n\n" +

                            "📋 DETAIL BOOKING\n\n" +

                            "👤 Nama Customer : " + name + "\n" +
                            "📱 Nomor HP : " + phone + "\n" +
                            "🏎️ Kendaraan : " + vehicle + "\n" +
                            "🔧 Service : " + service + "\n" +
                            "📅 Tanggal : " + selectedDate + "\n" +
                            "⏰ Jam : " + selectedTime + "\n\n" +

                            "✨ Paket Tambahan :\n" +
                            tambahan + "\n" +

                            "✅ Reservasi berhasil dilakukan.\n\n" +

                            "Terima kasih telah menggunakan\n" +
                            "RaceGarage Pro 🚀";

            Intent emailIntent =
                    new Intent(Intent.ACTION_SENDTO);

            emailIntent.setData(Uri.parse("mailto:"));

            emailIntent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "Booking RaceGarage Pro"
            );

            emailIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    shareText
            );

            try {

                startActivity(emailIntent);

            } catch (android.content.ActivityNotFoundException e) {

                Toast.makeText(
                        MainActivity.this,
                        "Tidak ada aplikasi email",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // MAPS
        btnMaps.setOnClickListener(v -> {

            Uri uri =
                    Uri.parse(
                            "geo:0,0?q=RaceGarage Workshop Jakarta"
                    );

            Intent mapIntent =
                    new Intent(Intent.ACTION_VIEW, uri);

            startActivity(mapIntent);
        });
    }

    // METHOD HITUNG TOTAL
    private int calculateTotal(String service) {

        int total = 0;

        // CEK JENIS KENDARAAN
        int selectedId = radioVehicle.getCheckedRadioButtonId();

        String vehicle = "";

        if (selectedId != -1) {

            RadioButton rbVehicle = findViewById(selectedId);

            vehicle = rbVehicle.getText().toString();
        }

        // HARGA MOTOR RACING
        switch (service) {

            case "Tune Up Racing":
                total = 150000;
                break;

            case "Ganti Oli Racing":
                total = 120000;
                break;

            case "Dyno Test":
                total = 300000;
                break;

            case "ECU Remap":
                total = 500000;
                break;

            case "Servis Mesin Full":
                total = 850000;
                break;
        }

        // JIKA MOBIL RACING → HARGA LEBIH MAHAL
        if (vehicle.equals("Mobil Racing")) {

            switch (service) {

                case "Tune Up Racing":
                    total = 250000;
                    break;

                case "Ganti Oli Racing":
                    total = 200000;
                    break;

                case "Dyno Test":
                    total = 450000;
                    break;

                case "ECU Remap":
                    total = 750000;
                    break;

                case "Servis Mesin Full":
                    total = 1200000;
                    break;
            }
        }

        // TAMBAHAN
        if (cbNitrogen.isChecked()) {
            total += 20000;
        }

        if (cbWash.isChecked()) {
            total += 35000;
        }

        return total;
    }

    // METHOD UPDATE ESTIMASI
    private void updateEstimate() {

        String service =
                spService.getSelectedItem().toString();

        // JIKA BELUM PILIH SERVIS
        if (service.equals("Pilih Paket Servis")) {

            tvEstimate.setText("Estimasi Harga : Rp 0");
            return;
        }

        int total =
                calculateTotal(service);

        NumberFormat rupiah =
                NumberFormat.getCurrencyInstance(
                        new Locale("id", "ID")
                );

        tvEstimate.setText(
                "Estimasi Harga : " +
                        rupiah.format(total)
        );
    }
}