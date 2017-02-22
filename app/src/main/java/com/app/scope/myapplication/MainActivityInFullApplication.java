package com.app.scope.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_in_full_application);
        if(isOnline(this)&&isLogged()){
            intent = new Intent(MainActivityInFullApplication.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public String Login(String mail){
        char[] nickInMail= mail.toCharArray();
        String nick= new String(nickInMail);
        return nick;
    }

    public boolean isLogged(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            //Проверка на зарегистрированность
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                intent = new Intent(MainActivityInFullApplication.this,LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
        return true;
    }

    //Проверка на подкючение к интернету
    public boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        intent = new Intent(MainActivityInFullApplication.this,LoginActivity.class);
        startActivity(intent);
        return false;
    }


}
