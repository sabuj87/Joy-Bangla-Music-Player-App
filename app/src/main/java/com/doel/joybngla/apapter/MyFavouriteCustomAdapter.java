package com.doel.joybngla.apapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doel.joybngla.R;
import com.doel.joybngla.activity.FavouritePlayerActivity;
import com.doel.joybngla.activity.PlayerActivity;
import com.doel.joybngla.extra.SharedPreference;

import java.util.ArrayList;

public class MyFavouriteCustomAdapter extends BaseAdapter {
   private Context context;
  private   String[] poem_name;
  private ArrayList<String> favouritePositionList;
  private SharedPreference sharedPreference=new SharedPreference();
   private String pos;

  private TextView textView;
   private ImageView imageView;



    public MyFavouriteCustomAdapter(Context context, String[] poem_name,ArrayList<String> favouritePositionList ) {
        this.context = context;
        this.poem_name = poem_name;
        this.favouritePositionList=favouritePositionList;
    }


    @Override
    public int getCount() {
        return favouritePositionList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.list_sample_layout,parent,false);

        }
        textView=convertView.findViewById(R.id.poemNameTvId);
        imageView=convertView.findViewById(R.id.favouriteIvId);
        pos=Integer.toString(position);
        imageView.setImageResource(R.drawable.ic_favorite);
        textView.setText(poem_name[Integer.parseInt(favouritePositionList.get(position))]);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, FavouritePlayerActivity.class);
                intent.putExtra("POS",position);
                context.startActivity(intent);

            }
        });
       // favouritePositionList=sharedPreference.getFavorites(context);
        /*if(position==Integer.parseInt(favouritePositionList.get(position))){
            textView.setText(poem_name[position]);
        }*/
       /* if (favouritePositionList!= null) {
            for (String positionFavourite : favouritePositionList) {
                if(position==Integer.parseInt(positionFavourite)){
                    textView.setText(poem_name[Integer.parseInt(positionFavourite)]);
                }
            }

        }*/
        return convertView;
    }

    public boolean checkFavoriteItem(String checkPosition) {
        boolean check = false;
        ArrayList<String> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (String position : favorites) {
                if (position.equals(checkPosition)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }


}
