package com.doel.joybngla.apapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doel.joybngla.R;
import com.doel.joybngla.extra.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import xyz.hasnat.sweettoast.SweetToast;

public class MyCustomAdapter extends BaseAdapter {
   private Context context;
  private   String[] poem_name;
  SharedPreference sharedPreference=new SharedPreference();
  String pos;

   TextView textView;
   private ImageView imageView;

    public MyCustomAdapter(Context context, String[] poem_name) {
        this.context = context;
        this.poem_name = poem_name;
    }


    @Override
    public int getCount() {
        return poem_name.length;
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
        textView.setText(poem_name[position]);
        pos=Integer.toString(position);
        if(checkFavoriteItem(pos)){
            imageView.setImageResource(R.drawable.ic_favorite);
        }else {
            imageView.setImageResource(R.drawable.ic_favorite_border);
        }
        final View finalConvertView = convertView;
        imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String po=Integer.toString(position);
                    if (!checkFavoriteItem(po)){
                        SharedPreference sharedPreference=new SharedPreference();
                        sharedPreference.addFavorite(context,Integer.toString(position));
                      ImageView imageViewt= finalConvertView.findViewById(R.id.favouriteIvId);
                        imageViewt.setImageResource(R.drawable.ic_favorite);
                        SweetToast.success(context,"প্রিয় কবিতা হিসাবে যুক্ত হলো");

                    }else {
                        SharedPreference sharedPreference=new SharedPreference();
                        sharedPreference.removeFavorite(context,Integer.toString(position));
                        SweetToast.success(context,"প্রিয় কবিতা থেকে মুছে ফেলা হলো");
                        ImageView imageViewt= finalConvertView.findViewById(R.id.favouriteIvId);
                        imageViewt.setImageResource(R.drawable.ic_favorite_border);
                    }

                }
            });

  /*      imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreference sharedPreference=new SharedPreference();
                sharedPreference.addFavorite(context,Integer.toString(position));
                ArrayList<String> favorites = sharedPreference.getFavorites(context);
                if (favorites != null) {
                    for (String position : favorites) {
                       Toast.makeText(context,position,Toast.LENGTH_LONG).show();
                    }
                }

            }
        });*/


 /*       final View finalConvertView = convertView;
     *//*   imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView= finalConvertView.findViewById(R.id.favouriteIvId);
                imageView.setImageResource(R.drawable.ic_favorite);
            }
        });
*/

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
