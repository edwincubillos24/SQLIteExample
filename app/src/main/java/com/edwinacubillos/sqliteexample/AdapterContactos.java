package com.edwinacubillos.sqliteexample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterContactos extends RecyclerView.Adapter<AdapterContactos.ContactosViewHolder>{

    private ArrayList<Contacto> listContactos;

    public AdapterContactos(ArrayList<Contacto> listContactos) {
        this.listContactos = listContactos;
    }

    @NonNull
    @Override
    public ContactosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ContactosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactosViewHolder holder, int position) {
        Contacto contacto = listContactos.get(position);
        holder.bindContactos(contacto);
    }

    @Override
    public int getItemCount() {
        return listContactos.size();
    }

    public class ContactosViewHolder extends RecyclerView.ViewHolder{

        private TextView tNombre, tTelefono, tCorreo;

        public ContactosViewHolder(View itemView) {
            super(itemView);
            tNombre = itemView.findViewById(R.id.tNombre);
            tTelefono = itemView.findViewById(R.id.tTelefono);
            tCorreo = itemView.findViewById(R.id.tCorreo);
        }

        public void bindContactos(Contacto contacto){
            tNombre.setText(contacto.getNombre());
            tTelefono.setText(contacto.getTelefono());
            tCorreo.setText(contacto.getCorreo());
        }
    }
}
