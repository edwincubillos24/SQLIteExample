package com.edwinacubillos.sqliteexample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    private ContactosSQLiteHelper contactosSQLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    private ArrayList<Contacto> listContactos;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private AdapterContactos adapterContactos;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        listContactos = new ArrayList<>();

        adapterContactos = new AdapterContactos(listContactos);
        recyclerView.setAdapter(adapterContactos);

        loadData();
    }

    private void loadData() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("contactos");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Contacto contacto = snapshot.getValue(Contacto.class);
                        listContactos.add(contacto);
                    }
                }
                adapterContactos.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    /*    contactosSQLiteHelper = new ContactosSQLiteHelper(this,
                "contactosBD",
                null,
                1);
        sqLiteDatabase = contactosSQLiteHelper.getWritableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM contactos",null);

        if (cursor.moveToFirst()){
            do{
                Contacto contacto = new Contacto(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                listContactos.add(contacto);
            }while(cursor.moveToNext());
            adapterContactos.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Lista vacia", Toast.LENGTH_SHORT).show();
        }*/
    }
}
