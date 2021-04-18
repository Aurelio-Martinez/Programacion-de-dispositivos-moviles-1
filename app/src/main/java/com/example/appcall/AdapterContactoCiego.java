package com.example.appcall;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class AdapterContactoCiego extends RecyclerView.Adapter<ViewHolderFicha> {

    // lista de clase Contacto
    private final List<Contacto> mcontactos;
    private final TextToSpeech mtts;

    // Constructor con lista Contacto
    public AdapterContactoCiego(List<Contacto> contactos, TextToSpeech tts) {
        mcontactos = contactos;
        mtts = tts;

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


    //Implements de la RecyclerView.Adapter<ViewHolder> necesarios al extenderla

    // onCreateViewHolder --> funcion que devuelve una vista del elemento personalizado ViewHolder
    @NonNull
    @Override
    public  com.example.appcall.ViewHolderFicha onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.page_content, parent, false);

        return new ViewHolderFicha(view);
    }

    public String deletrea (String a){
        StringBuilder resp= new StringBuilder();
        for ( int b=0; b<a.length();  b++){
            resp.append(" ").append(a.charAt(b));
        }
        return resp.toString();
    }

    public void ttsContacto (int pos)  {
        mtts.stop();
        mtts.speak("contacto:" + mcontactos.get(pos).getNombre() +"\nnumero: " +  deletrea(mcontactos.get(pos).getNumero()),1,null,null);
    }


    // onBindViewHolder --> funcion pasado un holder personalizado y una posicion de la lista de
    // contactos llama al onBind del holder para incluir la informacion y le crea el intent
    // de llamada al numero respectivo a la posicion de la lista de contactos
    @Override
    public void onBindViewHolder(@NonNull com.example.appcall.ViewHolderFicha holder, int position) {

        Contacto mContacto= mcontactos.get(position);
        holder.onBind(mContacto);
        String numero = mContacto.getNumero();
        if ( mcontactos.get(0) == mContacto ){
            ttsContacto(position);
        }
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(Intent.ACTION_CALL,  Uri.parse("tel:" +numero));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "permiso denegado", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions((Activity) v.getContext(),
                        new String[]{Manifest.permission.CALL_PHONE},143);
            }else{
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        assert mcontactos != null;
        return Math.max(mcontactos.size(), 0);
    }
}
