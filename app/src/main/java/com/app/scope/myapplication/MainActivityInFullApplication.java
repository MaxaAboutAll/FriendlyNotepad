package com.app.scope.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivityInFullApplication extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String id;
    Intent intent;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_in_full_application);
        id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Logged();
        try{
            Thread.sleep(2100);
        }catch (Exception e){

        }
        finish();
    }

    private void Logged(){
        myRef.child("suka").setValue("blyat");
        try {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
// This method is called once with the initial value and again
// whenever data at this location is updated.
                    value = dataSnapshot.child("users").child(id).child("status").getValue(String.class);
                    switch (value){
                       case "online":
                           intent = new Intent(MainActivityInFullApplication.this,MainActivity.class);
                           startActivity(intent);
                           finish();
                           break;
                       default:
                           intent = new Intent(MainActivityInFullApplication.this,LoginActivity.class);
                           startActivity(intent);
                           finish();
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
// Failed to read value

                    intent = new Intent(MainActivityInFullApplication.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            });
        }catch (Exception e){
        }

    }

}
