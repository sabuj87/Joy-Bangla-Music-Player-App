package com.doel.joybngla.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doel.joybngla.R;
import com.doel.joybngla.extra.Lastplayed;
import com.doel.joybngla.extra.SharedPreference;
import com.doel.joybngla.extra.SubtitleView;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import xyz.hasnat.sweettoast.SweetToast;

public class FavouritePlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton next_button,pre_button,play_pause_button;
    TextView poemNameTV,totalDurationTV,currentDurationTV;
    private LinearLayout linearLayout;
    private ImageView toolBarImageView;
    private CircularImageView image;
    int position;
    SeekBar sb;
    MediaPlayer mp;
    Handler handler;
    Runnable runnable;
    String[] poemName;
    ArrayList<String> poemListName=new ArrayList<>();

    Lastplayed lastplayed;
    SubtitleView subView;
    private Toolbar toolbar;


    private int[] all_poem = {R.raw.joy_bangla,
            R.raw.danobera_dhongso_hok,
            R.raw.durjoy_sontan,
            R.raw.ek_khoka_prio_nam,
            R.raw.amder_slogan,
            R.raw.muktigiti,
            R.raw.uddeg,
            R.raw.he_bibek_jege_otho,
            R.raw.ekattore_muktisanan,
            R.raw.gerilar_sogokti,
            R.raw.durmor_bangladesh
    };
    private int[] all_sub={
            R.raw.joy_bangala_sub,
            R.raw.danobera_dongsohok_sub,
            R.raw.durjoy_sontan_sub,
            R.raw.ek_khoka_priyo_nam_sub,
            R.raw.amader_sologan_sub,
            R.raw.muktigiti_sub,
            R.raw.uddeg_sub,
            R.raw.he_bebek_jege_otho_sub,
            R.raw.ekattore_muktisanan_sub,
            R.raw.gerilar_sogokt_sub,
            R.raw.durmor_bangladesh_sub,

    };

    private int[] all_image={
            R.drawable.f1,
            R.drawable.f2,
            R.drawable.f3,
            R.drawable.f4,
            R.drawable.f5,
            R.drawable.f1,
            R.drawable.f2,
            R.drawable.f3,
            R.drawable.f4,
            R.drawable.f5,
            R.drawable.f1,


    };

    ArrayList<Integer> arrayListPoem;
    ArrayList<String> favouriteListPoem;
    ArrayList<Integer> arrayListSub;
    ArrayList<Integer> arrayListImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        SharedPreference sharedPreference=new SharedPreference();
        toolbar=findViewById(R.id.PlayerToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        favouriteListPoem=sharedPreference.getFavorites(FavouritePlayerActivity.this);
        lastplayed=new Lastplayed();

        arrayListPoem = new ArrayList<>();
        arrayListSub = new ArrayList<>();
        arrayListImage= new ArrayList<>();
        subView=findViewById(R.id.subs_box);
        linearLayout=findViewById(R.id.PlayerBgId);



        Intent intent=getIntent();
        position=intent.getIntExtra("POS",0);
        poemName=getResources().getStringArray(R.array.poem_name);

     /*   if(mp!=null){
            mp.stop();
            mp.release();
        }*/
        for (int i = 0; i < favouriteListPoem.size(); i++) {
            arrayListPoem.add(all_poem[Integer.parseInt(favouriteListPoem.get(i))]);
        }
        for (int i = 0; i < favouriteListPoem.size(); i++) {
            poemListName.add(poemName[Integer.parseInt(favouriteListPoem.get(i))]);
        }
        for (int i = 0; i < favouriteListPoem.size(); i++) {
            arrayListSub.add(all_sub[Integer.parseInt(favouriteListPoem.get(i))]);
        }
        for (int i = 0; i < favouriteListPoem.size(); i++) {
            arrayListImage.add(all_image[Integer.parseInt(favouriteListPoem.get(i))]);
        }

        next_button=findViewById(R.id.PlayerNextBtnid);
        pre_button=findViewById(R.id.PlayerPreBtnId);
        play_pause_button=findViewById(R.id.PlayerPLayPauseBtnId);
        poemNameTV=findViewById(R.id.poemNameid);
        image=findViewById(R.id.PlayerImageId);
        toolBarImageView=findViewById(R.id.toolIvId);
        totalDurationTV=findViewById(R.id.TvTotalPoemDurationId);
        currentDurationTV=findViewById(R.id.TvPoemCurrentDurationId);
        handler =new Handler();
        sb=findViewById(R.id.PlayerSeekbarId);


        next_button.setOnClickListener(this);
        play_pause_button.setOnClickListener(this);
        pre_button.setOnClickListener(this);
        toolBarImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subView.isShown()){
                    subView.setVisibility(View.INVISIBLE);
                    toolBarImageView.setImageResource(R.drawable.ic_subtitleser);
                    SweetToast.success(FavouritePlayerActivity.this,"সাবটাইটেল বন্ধ করা হলো");
                }
                else {
                    subView.setVisibility(View.VISIBLE);
                    toolBarImageView.setImageResource(R.drawable.ic_subtitles);
                    SweetToast.success(FavouritePlayerActivity.this,"সাবটাইটেল চালু করা হলো");
                }
            }
        });

        mp=MediaPlayer.create(getApplicationContext(),arrayListPoem.get(position));
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);


        mp.start();
        subView.setPlayer(mp);
        subView.setSubSource(arrayListSub.get(position), MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
        rotateTheDisk();
        playCycle();
        complete();
        lastplayed.saveData(Integer.parseInt(favouriteListPoem.get(position)),FavouritePlayerActivity.this);
        poemNameTV.setText(poemListName.get(position));
        linearLayout.setBackgroundResource(arrayListImage.get(position));

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mp.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
    public  void playCycle(){
        sb.setMax(mp.getDuration());
        sb.setProgress(mp.getCurrentPosition());
        time(mp.getDuration(),mp.getCurrentPosition());
        if(mp.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            handler.postDelayed(runnable,1000);
        }
    }
    public void complete(){
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer op) {
                mp.stop();
                mp.release();
                position=(position+1)%arrayListPoem.size();
                mp=MediaPlayer.create(getApplicationContext(),arrayListPoem.get(position));
                poemNameTV.setText(poemListName.get(position));
                mp.start();
                linearLayout.setBackgroundResource(arrayListImage.get(position));
                subView.setPlayer(mp);
                subView.setSubSource(arrayListSub.get(position), MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
                rotateTheDisk();
                lastplayed.saveData(Integer.parseInt(favouriteListPoem.get(position)),FavouritePlayerActivity.this);
                complete();

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case  R.id.PlayerPLayPauseBtnId :
                if(mp.isPlaying()){
                    mp.pause();
                    play_pause_button.setImageResource(R.drawable.ic_palyer_play);
                    play_pause_button.setPadding(4,0,0,0);
                }else {
                    mp.start();
                    rotateTheDisk();
                    playCycle();
                    play_pause_button.setImageResource(R.drawable.ic_palyer_pause);
                }
                break;
            case R.id.PlayerNextBtnid:
                mp.stop();
                mp.release();
                position=(position+1)%arrayListPoem.size();
                mp=MediaPlayer.create(getApplicationContext(),arrayListPoem.get(position));
                poemNameTV.setText(poemListName.get(position));
                mp.start();
                linearLayout.setBackgroundResource(arrayListImage.get(position));
                subView.setPlayer(mp);
                subView.setSubSource(arrayListSub.get(position), MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
                rotateTheDisk();
                complete();
                playCycle();
                play_pause_button.setImageResource(R.drawable.ic_palyer_pause);
                lastplayed.saveData(Integer.parseInt(favouriteListPoem.get(position)),FavouritePlayerActivity.this);
                break;
            case R.id.PlayerPreBtnId:
                mp.stop();
                mp.release();
                position=(position-1 < 0)? arrayListPoem.size()-1:position-1;
                mp=MediaPlayer.create(getApplicationContext(),arrayListPoem.get(position));
                poemNameTV.setText(poemListName.get(position));
                mp.start();
                linearLayout.setBackgroundResource(arrayListImage.get(position));
                subView.setPlayer(mp);
                subView.setSubSource(arrayListSub.get(position), MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
                rotateTheDisk();
                complete();
                playCycle();
                play_pause_button.setImageResource(R.drawable.ic_palyer_pause);
                lastplayed.saveData(Integer.parseInt(favouriteListPoem.get(position)),FavouritePlayerActivity.this);
                break;

        }

    }

    /*  @Override
      protected void onResume() {
          super.onResume();
          mp.start();
      }

      @Override
      protected void onPause() {
          super.onPause();
          mp.pause();

      }*/
    public  void time(long td,long cd){

        String totatdu= (new SimpleDateFormat("mm:ss")).format(new Date(td));
        String currentdu=(new SimpleDateFormat("mm:ss")).format(new Date(cd));
        totalDurationTV.setText(totatdu);
        currentDurationTV.setText(currentdu);
    }
    private void rotateTheDisk(){
        if(!mp.isPlaying())return;
        image.animate().setDuration(100).rotation(image.getRotation()+2f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rotateTheDisk();
                super.onAnimationEnd(animation);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
