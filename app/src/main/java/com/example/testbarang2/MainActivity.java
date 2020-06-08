package com.example.testbarang2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {
    private Button bTambah;
    private Button bLihat;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bTambah = (Button) findViewById(R.id.btnTambah);
        bLihat = (Button) findViewById(R.id.btnLihat);

        bTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TambahData.getActIntent(MainActivity.this));
            }
        });
        bLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {

            }
        });
        //Obtain the firebaseAnalytics instance
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }
}
