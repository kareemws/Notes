package com.example.kareemwaleed.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

public class NoteActivity extends AppCompatActivity {

    private EditText noteEditText;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        prepare();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                if(position != -1 && noteEditText.getText().toString().isEmpty())
                {
                    MainActivity.notes.remove(position);
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                }
                else if(position != -1 && !noteEditText.getText().toString().isEmpty())
                {
                    MainActivity.notes.remove(position);
                    MainActivity.notes.add(position, noteEditText.getText().toString());
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                }
                else if(position == -1 && !noteEditText.getText().toString().isEmpty())
                {
                    MainActivity.notes.add(noteEditText.getText().toString());
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                }
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void prepare()
    {
        noteEditText = (EditText) findViewById(R.id.noteEditText);
        Intent intent = getIntent();
        position = intent.getIntExtra("Position", -2);
        System.out.println(position);
        if(position >= 0)
        {
            noteEditText.setText(MainActivity.notes.get(position));
            noteEditText.setSelection(noteEditText.getText().length());
        }
    }
}
