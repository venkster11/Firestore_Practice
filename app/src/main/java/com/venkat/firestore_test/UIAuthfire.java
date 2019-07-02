package com.venkat.firestore_test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class UIAuthfire extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiauthfire);

        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            //user already signed in
            Log.d("AUTH",auth.getCurrentUser().getEmail());
        }
        else {
            startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build())).build(),
                    RC_SIGN_IN
            );
        }

        findViewById(R.id.btnlogout).setOnClickListener(this);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            if(resultCode==RESULT_OK){
                Log.d("AUTH",auth.getCurrentUser().getEmail());
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                Toast.makeText(this, "User Id" + currentFirebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
            }
            else {
                Log.d("AUTH","NOT AUTHENTICATED");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnlogout) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            startActivity(new Intent(UIAuthfire.this, MainActivity.class));
                            Log.d("AUTH","USER LOGGED OUT");
                            finish();
                        }
                    });
        }
    }
}
