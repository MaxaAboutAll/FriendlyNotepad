package com.app.scope.myapplication;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Provides UI for the view with List.
 */
public class ListContentFragment extends Fragment {

    String value;
    ArrayList<HashMap<String,String>> data;
    HashMap<String, String> map;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    String ID;
    int COUNT;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        int myDataFromActivity = 0;
        myRef = database.getReference();
        try{

        }catch (Exception e){
            Log.e("Exception is ", e.toString());
        }

        MainActivity activity = (MainActivity) getActivity();
        try{
            COUNT = Integer.valueOf(activity.getMyData());
        }catch (Exception e){
            Log.e("Exception is ", e.toString());
        }

        String[] Name;
        String[] Text;

        COUNT = myDataFromActivity;

        Name = activity.getMyName();
        Text = activity.getMyText();

        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext(),ID,Name.length, Name, Text);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView text;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));
            name = (TextView) itemView.findViewById(R.id.list_title);
            text = (TextView) itemView.findViewById(R.id.list_desc);

            //----нажатие и переход к заметке
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, NoteActivity.class);
                    context.startActivity(intent);

                }
            });
        }
    }


    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        public static int LENGTH;
        String ID;
        private String Name[];
        private String Text[];
        DatabaseReference myRef;

        public ContentAdapter(Context context, final String ID, int count, String[] Name, String[] Text) {
            LENGTH = count;
            this.Name = Name;
            this.Text = Text;
           // this.ID = ID;

        /*   ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        // Get Post object and use the values to update the UI
                        Name[0] = dataSnapshot.child("users").child(ID).child("NOTES").child("Note1").child("ID").getValue(String.class);
                        Text[0] = dataSnapshot.child("users").child(ID).child("NOTES").child("Note1").child("Text").getValue(String.class);
                    }catch (Exception e){
                        Log.e("Exception is ", e.toString());
                    }
                    // ...
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            try {
            }catch (Exception e){
                Log.e("Efqrwrq is ", e.toString());
            }
        }*/
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i("view holder created","test");
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //Toast.makeText(this, "index is"+String.valueOf(position), Toast.LENGTH_SHORT).show();
            Log.i("123","321");

          /*Name = new String[LENGTH];
            Text = new String[LENGTH];*/

            Log.i("position is", String.valueOf(position));
            Log.i("Data on Bind is ", "Name is " + Name[0] + " Text is " + Text[0]);
            holder.name.setText(Name[position]);
            holder.text.setText(Text[position]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }


    }

}