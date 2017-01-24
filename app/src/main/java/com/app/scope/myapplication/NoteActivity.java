package com.app.scope.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //-------Set ToolBar------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_note);
        setSupportActionBar(toolbar);


    }
}
