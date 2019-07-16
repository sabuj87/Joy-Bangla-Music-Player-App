package com.doel.joybngla.activity.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.doel.joybngla.R;
import com.doel.joybngla.activity.home.MainActivity;
import com.doel.joybngla.apapter.IntroPagerAdapter;
import com.doel.joybngla.pojoclass.IntroContent;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    IntroPagerAdapter adapter;
    Animation button_anim;
    private ViewPager intro_viewPager;
    private TabLayout intro_tabLayout;
    private Button intro_button_started;
    TextView intro_textView_next;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        intro_viewPager=findViewById(R.id.introViewPagerID);
        intro_tabLayout=findViewById(R.id.introTabLayoutID);
        intro_textView_next=findViewById(R.id.introNextTextViewID);
        intro_button_started=findViewById(R.id.introStartButtonID);
        button_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        if (loadData()) {

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }

        String intro_title_1=getResources().getString(R.string.intro_title_1);
        String intro_title_2=getResources().getString(R.string.intro_title_2);
        String intro_title_3=getResources().getString(R.string.intro_title_3);

        String intro_detail_1=getResources().getString(R.string.intro_details_1);
        String intro_detail_2=getResources().getString(R.string.intro_details_2);
        String intro_detail_3=getResources().getString(R.string.intro_details_3);

        final List<IntroContent> intro_list=new ArrayList<>();

        intro_list.add(new IntroContent(intro_title_1,intro_detail_1));
        intro_list.add(new IntroContent(intro_title_2,intro_detail_2));
        intro_list.add(new IntroContent(intro_title_3,intro_detail_3));

        adapter=new IntroPagerAdapter(this,intro_list);
        intro_viewPager.setAdapter(adapter);
        intro_tabLayout.setupWithViewPager(intro_viewPager);

        intro_textView_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position=intro_viewPager.getCurrentItem();
                if (position<intro_list.size()){
                    position++;
                    intro_viewPager.setCurrentItem(position);
                }if(position==intro_list.size()-1){
                   loadLastScreen();
                }
            }
        });
        intro_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==intro_list.size()-1){
                  loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        intro_button_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                saveData();
                finish();
            }
        });
    } //ending onCreate
    private boolean loadData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean value = pref.getBoolean("isIntroOpen", false);
        return value;

    }
    private void saveData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpen", true);
        editor.commit();
    }
    private void loadLastScreen() {
        intro_textView_next.setVisibility(View.INVISIBLE);
        intro_button_started.setVisibility(View.VISIBLE);
        intro_tabLayout.setVisibility(View.INVISIBLE);
        intro_button_started.setAnimation(button_anim);
    }
}
