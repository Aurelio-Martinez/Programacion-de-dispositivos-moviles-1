package com.example.appcall;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager2.widget.ViewPager2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivityCiego extends AppCompatActivity   {

    private DatabaseReference mDatabase;
     TextToSpeech tts;
    ViewPager2 myViewPager2;
    AdapterContactoCiego mAdapterContactoCiego;
    private ArrayList<Contacto> mContactos;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tts= new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(new Locale("es", "ES"));
            }
        });

        setContentView(R.layout.activity_main_ciego);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mContactos = new ArrayList<>();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
            startActivity(intent);
        }
        myViewPager2 = findViewById(R.id.view_pager);
        mDatabase = FirebaseDatabase.getInstance("https://appcall-default-rtdb.europe-west1.firebasedatabase.app").getReference("subusers/"+user.getUid()+"/contactos");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Recycler();
    }

    public void Recycler() {

        mAdapterContactoCiego= new AdapterContactoCiego(mContactos, tts);
        myViewPager2.setAdapter(mAdapterContactoCiego);
        myViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mAdapterContactoCiego.ttsContacto(position);
            }
        });
        Content();
    }

    private void Content() {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mContactos.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Contacto contacto = new Contacto( (String)postSnapshot.child("nombre").getValue() , String.valueOf(postSnapshot.child("numero").getValue()), (String) postSnapshot.child("url").getValue()  );
                    mContactos.add(contacto);
                }
                mAdapterContactoCiego.ttsContacto(0);
                mAdapterContactoCiego.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (mContactos.isEmpty()){
                    for (int i=0; i<9; i++) {
                        Contacto contacto = new Contacto("ejemplo"+i , "000000000000" );
                        mContactos.add(contacto);
                    }
                }
                Toast.makeText(MainActivityCiego.this,"Contactos de ejemplo", Toast.LENGTH_SHORT).show();
            }

        });
    }

}