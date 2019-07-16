package com.doel.joybngla.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.doel.joybngla.R;
import com.doel.joybngla.apapter.MyCustomAdapter;

import java.util.ArrayList;

public class AllpoemListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView play_listView;
    String[] poem_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allpoem_list);
        toolbar = findViewById(R.id.playlistToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        play_listView = findViewById(R.id.playListId);

        poem_name = getResources().getStringArray(R.array.poem_name);

        MyCustomAdapter arrayAdapter=new MyCustomAdapter(AllpoemListActivity.this,poem_name);
        play_listView.setAdapter(arrayAdapter);

        play_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),PlayerActivity.class);
                intent.putExtra("POS",position);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}