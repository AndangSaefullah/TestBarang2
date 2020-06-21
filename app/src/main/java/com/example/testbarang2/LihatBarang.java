package com.example.testbarang2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LihatBarang extends AppCompatActivity implements AdapterLihatBarang.FirebaseDataListener {
    // Inisialisasi variable yang dipakai
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Barang> daftarBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_barang);

        //Inisialisasi RecylerView dan komponennya

        rvView = (RecyclerView) findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        //Inisialisasi dan mengambil firebase database reference
        database = FirebaseDatabase.getInstance().getReference();

        //Mengambil data barang dari firebase realtime db
        database.child("Barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Saat data baru, masukan datanya ke Array list
                daftarBarang = new ArrayList<>();
                for (DataSnapshot noteDataSnapShot : dataSnapshot.getChildren()){

                    //Mapping data pada DataSnapshot ke dalam object barang
                    //dan juga menyimpan primary key pada object barang
                    // untuk keperluan edit dan delete data
                    Barang barang = noteDataSnapShot.getValue(Barang.class);
                    barang.setKode(noteDataSnapShot.getKey());

                    //Menambahkan object barang yang sudah dimapping
                    //kedalam arraylist
                    daftarBarang.add(barang);
                }

                //Inisialisasi adapter dan data barang dalam bentuk arraylist
                //dan mengeset adapter ke dalam recylerview
                adapter = new AdapterLihatBarang(daftarBarang, LihatBarang.this);
                rvView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Kode ini akan dipanggil ketika ada error dan pengambilan data gagal
                //dan memprint errornya ke Logcat
                System.out.println(databaseError.getDetails()+""+databaseError.getMessage());

            }
        });
    }
    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, LihatBarang.class);
    }

    @Override
    public void onDeleteData(Barang barang, final int position) {
        //kode ini dugunakan ketika methode onDelete dipangil dari adapter lewat interface
        //yang kemudian akan mendelete data dari firebase realtime Db berdasarkan key barang
        //jika sukses memunculkan toast
        if (database != null){
            database.child("Barang")
                    .child(barang.getKode())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(LihatBarang.this, "Success delete", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}
