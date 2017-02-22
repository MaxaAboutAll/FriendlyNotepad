package com.app.scope.myapplication;

import  android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String nick;
    DatabaseReference myRef;
    String id;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    int i=0;
    private EditText ETemail;
    private EditText ETpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                } else {
                    // User is signed out

                }

            }
        };

        ETemail = (EditText) findViewById(R.id.LoginEditText);
        ETpassword = (EditText) findViewById(R.id.PasswordEditText);

        findViewById(R.id.LoginBtn).setOnClickListener(this);
        findViewById(R.id.RegBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(TextUtils.isEmpty(ETemail.getText().toString())||TextUtils.isEmpty(ETpassword.getText().toString())) {
            Snackbar.make(findViewById(R.id.LoginBtn), "Поля пусты", Snackbar.LENGTH_SHORT).show();
            Snackbar.make(findViewById(R.id.RegBtn), "Поля пусты", Snackbar.LENGTH_SHORT).show();

        }else {
            if (view.getId() == R.id.LoginBtn) {
                signin(ETemail.getText().toString(), ETpassword.getText().toString());
            } else if (view.getId() == R.id.RegBtn) {
                registration(ETemail.getText().toString(), ETpassword.getText().toString());
            }
        }

    }

    public void signin(String email , String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Snackbar.make(findViewById(R.id.LoginBtn), "Автоизация успешна", Snackbar.LENGTH_SHORT).show();
                    myRef.child("users").child(id).child("status").setValue("online");//Отправить в базу статус
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else
                    Snackbar.make(findViewById(R.id.LoginBtn), "Авторизация провалена", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void registration (final String email , String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Snackbar.make(findViewById(R.id.RegBtn), "Регистрация успешна", Snackbar.LENGTH_SHORT).show();
                    id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);//vzyat' id polzovatelya
                    try {
                        myRef.child("users").child(id).child("status").setValue("online");
                        myRef.child("users").child(id).child("amountNotes_").setValue("0");
                        nick = Login(email);
                        myRef.child("users").child(id).child("NickName").setValue(nick);
                    }catch (Exception e){
                        Log.e("EXCEPTION!!!!!!",e.toString());
                    }
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Snackbar.make(findViewById(R.id.RegBtn), "Регистрация провалена", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    public String Login(String email){
        char[] nickWithEmail = email.toCharArray();
        while(nickWithEmail[i]!='@'){
            i++;
        }
        char[] nickWithoutEmail = new char[i];
        i=0;
        for(;i<nickWithoutEmail.length;i++){
            nickWithoutEmail[i]=nickWithEmail[i];
        }
        String Nick = new String(nickWithoutEmail);
        return Nick;
    }

}