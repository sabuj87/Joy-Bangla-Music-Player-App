package com.doel.joybngla.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.doel.joybngla.R;
import com.doel.joybngla.apapter.MyFavouriteCustomAdapter;
import com.doel.joybngla.extra.SharedPreference;

import java.util.ArrayList;

public class FavouriteListActivity extends AppCompatActivity {
    private ListView  favouritelistView;
    private ArrayList<String> favouritePositionList;
    private SharedPreference sharedPreference;
    private String[] poem_name;
    private TextView noPoemTv;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        toolbar=findViewById(R.id.favouritelistToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        favouritelistView=findViewById(R.id.favouriteListLVId);
        noPoemTv=findViewById(R.id.noPoem);
        sharedPreference=new SharedPreference();
        favouritePositionList=new ArrayList<>();
        favouritePositionList.clear();
        if(sharedPreference.getFavorites(FavouriteListActivity.this)!=null){
            favouritePositionList=sharedPreference.getFavorites(FavouriteListActivity.this);
        }else {
            favouritePositionList=new ArrayList<>();
        }




        poem_name = getResources().getStringArray(R.array.poem_name);
        if(favouritePositionList.isEmpty()){
            noPoemTv.setVisibility(View.VISIBLE);
            favouritelistView.setVisibility(View.GONE);

        }else {

        }
        MyFavouriteCustomAdapter adapter=new MyFavouriteCustomAdapter(FavouriteListActivity.this,poem_name,favouritePositionList);
        favouritelistView.setAdapter(adapter);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
