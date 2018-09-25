package com.edwinacubillos.sqliteexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ContactosSQLiteHelper contactosSQLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private EditText eNombre,eTelefono, eCorreo;
    private ContentValues dataBD;
    private Cursor cursor;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        eNombre = findViewById(R.id.eNombre);
        eTelefono = findViewById(R.id.eTelefono);
        eCorreo = findViewById(R.id.eCorreo);                
        
        contactosSQLiteHelper = new ContactosSQLiteHelper(this, 
                "contactosBD",
                null,
                1);
        sqLiteDatabase = contactosSQLiteHelper.getWritableDatabase();      
    }

    public void guardarClicked(View view) {
        dataBD = new ContentValues();
        dataBD.put("nombre",eNombre.getText().toString());
        dataBD.put("telefono",eTelefono.getText().toString());
        dataBD.put("correo",eCorreo.getText().toString());
        sqLiteDatabase.insert("contactos",null,dataBD);
        Toast.makeText(this, "Contacto Guardado", Toast.LENGTH_SHORT).show();
        limpiarWidgets();
    }

    public void buscarClicked(View view) {
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM contactos WHERE nombre='"+eNombre.getText().toString()+"'",null);

        if (cursor.moveToFirst()){
            eTelefono.setText(cursor.getString(2));
            eCorreo.setText(cursor.getString(3));
        } else {
            Toast.makeText(this, "Contacto no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizarClicked(View view) {
        dataBD = new ContentValues();
        dataBD.put("telefono",eTelefono.getText().toString());
        dataBD.put("correo",eCorreo.getText().toString());
        int band = sqLiteDatabase.update("contactos",dataBD,"nombre='"+eNombre.getText().toString()+"'",null);
        Log.d("band",String.valueOf(band));
        limpiarWidgets();
        if (band!=0) {
            Toast.makeText(this, "Contacto Actualizado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Contacto no existe", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarClicked(View view) {
        int band = sqLiteDatabase.delete("contactos","nombre='"+eNombre.getText().toString()+"'",null);
        if (band == 0){
            Toast.makeText(this, "Contacto no existe", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_SHORT).show();
        }
        Log.d("band",String.valueOf(band));
        limpiarWidgets();
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
