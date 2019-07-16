package com.doel.joybngla.apapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doel.joybngla.R;
import com.doel.joybngla.pojoclass.IntroContent;

import java.util.List;

public class IntroPagerAdapter extends PagerAdapter {
    Context context;
    List<IntroContent> intro_content_list;

    public IntroPagerAdapter(Context context, List<IntroContent> intro_content_list) {
        this.context = context;
        this.intro_content_list = intro_content_list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.intro_content_layout, null);
        TextView intro_Title = view.findViewById(R.id.intoTextViewTitleID);
        TextView intro_Details = view.findViewById(R.id.introTextViewDetailsID);
        intro_Title.setText(intro_content_list.get(position).getIntro_title());
        intro_Details.setText(intro_content_list.get(position).getIntro_details());
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return intro_content_list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
