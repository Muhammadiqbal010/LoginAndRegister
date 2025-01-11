package com.example.uasmobileprogramming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Menu extends AppCompatActivity {

    private EditText etMainUser;
    private TextView tvMain;
    private Button btnLogin;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Inisialisasi elemen tampilan
        tvMain = findViewById(R.id.main);
        btnLogin = findViewById(R.id.btnLogout);

        // Referensi ke Firebase Realtime Database
        database = FirebaseDatabase.getInstance().getReference("users");

        // Set listener untuk tombol login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = tvMain.getText().toString();

                // Memeriksa apakah username diisi
                if (usernameInput.isEmpty()) {
                    Toast.makeText(Menu.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                } else {
                    // Query untuk mencari data pengguna berdasarkan username
                    database.child(usernameInput).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // Mendapatkan email pengguna dari Firebase
                                String email = snapshot.child("email").getValue(String.class);
                                // Menampilkan pesan dengan informasi pengguna
                                Toast.makeText(Menu.this, "Welcome, " + usernameInput + "!\nYour email: " + email, Toast.LENGTH_LONG).show();
                            } else {
                                // Menampilkan pesan jika pengguna tidak ditemukan
                                Toast.makeText(Menu.this, "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Menampilkan pesan error jika query gagal
                            Toast.makeText(Menu.this, "Error accessing database", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
