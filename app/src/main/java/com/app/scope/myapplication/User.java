package com.app.scope.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Created by Scope on 17.01.17.
 */

public class User {

    String login = "", password = "";
    public User(String newLogin, String newPassword){
        login = newLogin;
        password = newPassword;


    }
    public void signin(String email , String password){

    }

    public void registration(String email , String password){


    }

    public void callback(){

    }
}
