package com.example.appcall;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LogInActivity extends AppCompatActivity {


    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(this);

        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://appcall-default-rtdb.europe-west1.firebasedatabase.app").getReference("subusers/"+mAuth.getUid()+"/ciego");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Intent intent;
                    if(dataSnapshot.getValue().toString().equals("si")){
                        intent = new Intent(LogInActivity.this, MainActivityCiego.class);
                    }else{
                        intent = new Intent(LogInActivity.this, MainActivity.class);
                    }
                    startActivity(intent);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            });
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        createRequest();
        findViewById(R.id.google_signIn).setOnClickListener(view -> signIn());


    }


    private void createRequest() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();


    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, (OnCompleteListener<com.google.firebase.auth.AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://appcall-default-rtdb.europe-west1.firebasedatabase.app").getReference("subusers/"+mAuth.getUid()+"/ciego");
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Intent intent;
                                if(dataSnapshot.getValue().toString().equals("si")){
                                    intent = new Intent(LogInActivity.this, MainActivityCiego.class);
                                }else{
                                    intent = new Intent(LogInActivity.this, MainActivity.class);
                                }
                                startActivity(intent);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                                startActivity(intent);
                            }

                        });

                    } else {
                        Toast.makeText( LogInActivity.this, "Sorry auth failed.", Toast.LENGTH_SHORT).show();

                    }
                });

    }



}