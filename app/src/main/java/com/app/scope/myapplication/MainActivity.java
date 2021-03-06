package com.app.scope.myapplication;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    String id;
    String value = "";
    int testValue=0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference noteCount= database.getReference();
    Intent intent;
    int firstValue;
    boolean check=true;
    ListContentFragment preview;
    ArrayList<String> Name;
    ArrayList<String> Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Poluchenie kol-va zapisok
        numberOfNotes();
        try{
            Thread.sleep(2100);
        }catch (Exception e){
            e.printStackTrace();
        }
       // perehod();
        Log.i("Value is ", String.valueOf(testValue));
        Name = new ArrayList<>();
        Text = new ArrayList<>();

        getData();

        id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
//-------Set ToolBar------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//-------Set Navigation Menu----------------------------
// Create Navigation drawer and inflate layout
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        //--------------------


//-------Set ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
//----------------------------
// Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//Установка картинки гамбургера
            supportActionBar.setDisplayHomeAsUpEnabled(true);//Возварат на уровень выше
        }

// Set behavior of Navigation drawer
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        switch (id) { // проверка нажатия и распределение действий
                            case R.id.LogOut: //кнопка логаут
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                        }
// Set item in checked state
                        menuItem.setChecked(true);
// Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return true;
                    }

                });

//-------Set FAB-------------
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Snackbar.make(v, "Make New Note",
                Snackbar.LENGTH_LONG).show();*/
                fabAction();

            }
        });
    }


    public void getData(){
        myRef.child("suka").setValue("blyat");
        try {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
// This method is called once with the initial value and again
// whenever data at this location is updated.
                    for(int i=0;i<firstValue;i++) {
                        Name.add(dataSnapshot.child("users").child(id).child("NOTES").child("Note"+(i+1)).child("ID").getValue(String.class));
                        Text.add(dataSnapshot.child("users").child(id).child("NOTES").child("Note"+(i+1)).child("Text").getValue(String.class));
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    //intent = new Intent(MainActivityInFullApplication.this,LoginActivity.class);
                    //startActivity(intent);
                    //finish();
                }

            });
        }catch (Exception e){
            Log.e("Exception is ", e.toString());
        }
    }

    //Vse do fabAction sostovlyaushie
    private void numberOfNotes(){
            noteCount.child("suka").setValue("blyat");
            try {
                noteCount.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        value = dataSnapshot.child("users").child(id).child("amountNotes_").getValue(String.class);
                        firstValue = Integer.parseInt(value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
// Failed to read value
                        Toast.makeText(getApplicationContext(), "SUSKA", Toast.LENGTH_LONG).show();
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                });
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
            }
    }

    private void perehod(){
        try {
            testValue = Integer.parseInt(value);
            testValue += 1;
            value = String.valueOf(testValue);
            Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
           myRef.child("users").child(id).child("amountNotes_").setValue(value);
        }catch (Exception e){

        }
        intent = new Intent(MainActivity.this, NoteActivity.class);
        intent.putExtra("FILENAME", "Note" + value);
        startActivity(intent);
    }
    private void fabAction(){
        numberOfNotes();
        perehod();
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ListContentFragment(), "List");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//---Открытие меню по нажаию на гамбургер

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> getMyText() {
        return Text;
    }
    public ArrayList<String> getMyName(){
        return Name;
    }
    public String getMyData(){
        return value;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
    }
}