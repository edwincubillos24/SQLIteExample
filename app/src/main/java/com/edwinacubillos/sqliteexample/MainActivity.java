package com.edwinacubillos.sqliteexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ContactosSQLiteHelper contactosSQLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private EditText eNombre, eTelefono, eCorreo;
    private ContentValues dataBD;
    private Cursor cursor;
    private FirebaseDatabase database;
    private DatabaseReference myRef,myRef2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eNombre = findViewById(R.id.eNombre);
        eTelefono = findViewById(R.id.eTelefono);
        eCorreo = findViewById(R.id.eCorreo);                
        
     /*   contactosSQLiteHelper = new ContactosSQLiteHelper(this,
                "contactosBD",
                null,
                1);
        sqLiteDatabase = contactosSQLiteHelper.getWritableDatabase();*/

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("contactos");
    }

    public void guardarClicked(View view) {

        Contacto contacto = new Contacto(myRef.push().getKey(),
                eNombre.getText().toString(),
                eTelefono.getText().toString(),
                eCorreo.getText().toString());

        myRef.child(contacto.getId()).setValue(contacto);





    /*    contactosSQLiteHelper = new ContactosSQLiteHelper(this,
                "contactosBD",
                null,
                1);
        sqLiteDatabase = contactosSQLiteHelper.getWritableDatabase();

        dataBD = new ContentValues();
        dataBD.put("nombre",eNombre.getText().toString());
        dataBD.put("telefono",eTelefono.getText().toString());
        dataBD.put("correo",eCorreo.getText().toString());
        sqLiteDatabase.insert("contactos",null,dataBD);*/
        Toast.makeText(this, "Contacto Guardado", Toast.LENGTH_SHORT).show();
        limpiarWidgets();
    }

    public void buscarClicked(View view) {

        //buscar por nombre
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Contacto contacto = snapshot.getValue(Contacto.class);
                        if (eNombre.getText().toString().equals(contacto.getNombre())) {
                            eTelefono.setText(contacto.getTelefono());
                            eCorreo.setText(contacto.getCorreo());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*buscar por ID
         myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("-LOKmuZlEBT4Q6g-IwhK").exists()){
                    Contacto contacto = dataSnapshot.child("-LOKmuZlEBT4Q6g-IwhK").getValue(Contacto.class);
                    eNombre.setText(contacto.getNombre());
                    Log.d("dato",dataSnapshot.child("-LOKmuZlEBT4Q6g-IwhK").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
         */

      /*  cursor = sqLiteDatabase.rawQuery("SELECT * FROM contactos WHERE nombre='"+eNombre.getText().toString()+"'",null);

        if (cursor.moveToFirst()){
            eTelefono.setText(cursor.getString(2));
            eCorreo.setText(cursor.getString(3));
        } else {
            Toast.makeText(this, "Contacto no encontrado", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void actualizarClicked(View view) {
        //Actualizar por nombre
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Contacto contacto = snapshot.getValue(Contacto.class);
                        if (eNombre.getText().toString().equals(contacto.getNombre())) {
                            myRef2 = myRef.child(contacto.getId());

                            Map<String, Object> nuevoData = new HashMap<>();
                            nuevoData.put("telefono", eTelefono.getText().toString());
                            myRef2.updateChildren(nuevoData);

                            Map<String, Object> nuevoData2 = new HashMap<>();
                            nuevoData2.put("correo", eCorreo.getText().toString());
                            myRef2.updateChildren(nuevoData2);

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        /*Actualizar por ID
        myRef = myRef.child("-LP65_x4DntAUudKBX_m");

        Map<String, Object> nuevoData = new HashMap<>();
        nuevoData.put("nombre", eNombre.getText().toString());
        nuevoData.put("telefono", eTelefono.getText().toString());
        nuevoData.put("correo", eCorreo.getText().toString());

        myRef.updateChildren(nuevoData);



      /*  dataBD = new ContentValues();
        dataBD.put("telefono",eTelefono.getText().toString());
        dataBD.put("correo",eCorreo.getText().toString());
        int band = sqLiteDatabase.update("contactos",dataBD,"nombre='"+eNombre.getText().toString()+"'",null);
        Log.d("band",String.valueOf(band));
        limpiarWidgets();
        if (band!=0) {
            Toast.makeText(this, "Contacto Actualizado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Contacto no existe", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void eliminarClicked(View view) {

        //Eliminar por nombre
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Contacto contacto = snapshot.getValue(Contacto.class);
                        if (eNombre.getText().toString().equals(contacto.getNombre())) {
                            myRef.child(contacto.getId()).removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /* eliminar por ID
        myRef.child("-LP65eoczsnFHw_uI8Z5").removeValue();

    /*    int band = sqLiteDatabase.delete("contactos","nombre='"+eNombre.getText().toString()+"'",null);
        if (band == 0){
            Toast.makeText(this, "Contacto no existe", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_SHORT).show();
        }
        Log.d("band",String.valueOf(band));
        limpiarWidgets();*/
    }

    public void limpicarClicked(View view) {
        limpiarWidgets();
    }

    private void limpiarWidgets() {
        eNombre.setText("");
        eTelefono.setText("");
        eCorreo.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqLiteDatabase.close();
    }

    public void listaClicked(View view) {
        Intent i = new Intent(this, ListaActivity.class);
        startActivity(i);
    }
}
