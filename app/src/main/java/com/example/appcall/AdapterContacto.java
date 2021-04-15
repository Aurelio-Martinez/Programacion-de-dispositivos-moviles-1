 package com.example.appcall;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterContacto extends RecyclerView.Adapter<ViewHolder> {


    private final List<Contacto> mcontactos;

    public AdapterContacto(List<Contacto> contactos) {
        mcontactos = contactos;
    }


    //Hechos para el siguiente proyecto donde se realizara el input de la agenda por lo que se
    // tendran que añadir/borrar contactos

  /*  public void deleteItem(int position) {
        if (mcontactos != null & mcontactos.size() > 0) {
            mcontactos.remove(position);
        }
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void addItems(List<Contacto> contactos) {
        mcontactos.addAll(contactos);
        notifyDataSetChanged();
    }  */

    @NonNull
    @Override
    public  com.example.appcall.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.appcall.ViewHolder holder, int position) {
        holder.onBind(position);

        Contacto ficha = this.mcontactos.get(position);
        String numero = ficha.getNumero();
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(Intent.ACTION_CALL,  Uri.parse("tel:" +numero));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "permission not granted", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions((Activity) v.getContext(),
                        new String[]{Manifest.permission.CALL_PHONE},143);
            }else{
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        if (mcontactos != null & mcontactos.size() > 0) {
            return mcontactos.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends com.example.appcall.ViewHolder {

        ImageView foto;
        TextView numero;
        TextView nombre;


        public ViewHolder(View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.Nombre);
            numero=itemView.findViewById(R.id.Numero);
            foto=itemView.findViewById(R.id.Foto);
        }

        protected void clear() {
            foto.setImageDrawable(null);
            nombre.setText("");
            numero.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            Contacto mContacto= mcontactos.get(position);


            if (mContacto.getNombre() != null) {
                nombre.setText(mContacto.getNombre());
            }

            if (mContacto.getNumero() != null) {
                numero.setText(String.valueOf(mContacto.getNumero()));
            }

            Picasso.get()
                    .load(mContacto.getUrl())
                    .placeholder(R.mipmap.ic_launcher_round)
                   // .resize(128,128)
                    .error(R.mipmap.ic_launcher_round)
                    .into(foto);

            //if (mContacto.getUrl()==0){
          //  foto.setImageResource(R.mipmap.ic_launcher_round);}

            itemView.setOnClickListener(v -> {
             /*   Intent call = new Intent(Intent.ACTION_CALL);

                call.setData(Uri.parse("tel:"+ numero.getText().toString()));
                if(ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Main.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
                }
                else
                {
                    startActivity(call);
                }*/

                Toast.makeText( itemView.getContext(), "Llamando", Toast.LENGTH_SHORT).show();
                
            });
        }
    }
}
