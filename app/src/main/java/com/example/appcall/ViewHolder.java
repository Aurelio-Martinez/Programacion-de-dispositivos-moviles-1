package com.example.appcall;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


// ViewHolder Personalizado para las fichas telefonicas incluyendo numero nombre y foto;
public class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView foto;
    public TextView numero;
    public TextView nombre;

    public ViewHolder(View itemView) {
        super(itemView);
        nombre=itemView.findViewById(R.id.Nombre);
        numero=itemView.findViewById(R.id.Numero);
        foto=itemView.findViewById(R.id.Foto);
    }
    public void onBind( Contacto mContacto) {



        if (mContacto.getNombre() != null) {
            nombre.setText(mContacto.getNombre());
        }

        if (mContacto.getNumero() != null) {
            numero.setText(String.valueOf(mContacto.getNumero()));
        }

        // llamada a la libreria picasso para cargar las fotos de perfil
        // en caso de error o ausencia cargara el icono de launcher redondo
        Picasso.get()
                .load(mContacto.getUrl())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(foto);
    }}