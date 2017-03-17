package com.app.scope.myapplication;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class NoteActivity extends AppCompatActivity {
    //String FILENAME1 = (String)getIntent().getSerializableExtra("FILENAME");
    String text="";
     // имя файла
    private EditText mEditText;
    String FILENAME= "";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String FILENAME2;
    String FILENAME1;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        try {
        id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        //-------Set ToolBar------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_note);
        setSupportActionBar(toolbar);
        mEditText = (EditText) findViewById(R.id.input_note);

            Intent intent = getIntent();
            FILENAME2 = intent.getStringExtra("FILENAME");
            FILENAME1 = FILENAME2 + ".txt";
            FILENAME = FILENAME1;
            myRef.child("users").child(id).child("NOTES").child(FILENAME2).child("ID").setValue(FILENAME);
        }catch (Exception e){
            Log.e("EXCEPTION:   ",e.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;    
    }
//wtf??
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           /* case R.id.action_open:
                openFile(FILENAME);
                return true;*/
            case R.id.action_save:
                saveFile();
                return true;
            default:
                return true;
        }
    }

    // Метод для открытия файла
  /*  private void openFile(String fileName) {
        try {
            InputStream inputStream = openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }

                inputStream.close();
                mEditText.setText(builder.toString());
            }
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }*/

    // Метод для сохранения файла
    private void saveFile() {
        try {
            text = mEditText.getText().toString();
            myRef.child("users").child(id).child("NOTES").child(FILENAME2).child("Text").setValue(text);
            Intent intent = new Intent(NoteActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }catch(Exception e){

        }
    }


}
