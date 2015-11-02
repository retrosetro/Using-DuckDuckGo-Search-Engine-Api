package com.example.oluconquer.duckduckgomobilesearchengine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void searchDuckDuckGo(View view) {
        EditText searchText = (EditText) findViewById(R.id.search_edit);
        String searchTerm = searchText.getText().toString();

        Intent intent = new Intent(MainActivity.this, ResultDisplay.class);
        intent.putExtra("searchTerms", searchTerm);

        startActivity(intent);

    }
}
