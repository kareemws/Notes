package com.example.kareemwaleed.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    static ArrayList<String> notes;
    static ArrayAdapter arrayAdapter;
    private ListView notesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prepare();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            Intent intent = new Intent(this, NoteActivity.class);
            intent.putExtra("Position", -1);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepare() {
        notesListView = (ListView) findViewById(R.id.notesListView);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.kareemwaleed.notes", Context.MODE_PRIVATE);
        Set<String> temp = null;
        if (notes != null) {
            System.out.println("notes != null");
            temp = new HashSet<>();
            temp.addAll(notes);
            sharedPreferences.edit().putStringSet("notes", temp).apply();
        }else {
            temp = sharedPreferences.getStringSet("notes", null);
            notes = new ArrayList<String>();
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
            if (temp != null)
                notes.addAll(temp);
        }
        notesListView.setAdapter(arrayAdapter);
        notesListView.setOnItemClickListener(this);
        notesListView.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("Position", position);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Sure You want to delete ?")
                .setMessage("Are you sure you want to permanently delete this note ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null).show();
        return true;
    }
}
