package com.doel.joybngla.activity.home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.doel.joybngla.R;
import com.doel.joybngla.activity.AllpoemListActivity;
import com.doel.joybngla.activity.FavouriteListActivity;
import com.doel.joybngla.activity.PlayerActivity;
import com.doel.joybngla.extra.Lastplayed;

import java.util.concurrent.atomic.AtomicBoolean;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    CardView cardView_allPoem,cardView_favourite,cardView_lastPlayed,cardView_poem_player;
    MediaPlayer mp;
    int lastPosition;
    Lastplayed lastplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.libraryToolbar);
        setSupportActionBar(toolbar);

         lastplayed=new Lastplayed();


        cardView_allPoem=findViewById(R.id.allPoemList);
        cardView_favourite=findViewById(R.id.favouriteList);
        cardView_lastPlayed=findViewById(R.id.lastPlayedList);
        cardView_poem_player=findViewById(R.id.playerId);


        cardView_allPoem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AllpoemListActivity.class);
                startActivity(intent);
            }
        });
        cardView_lastPlayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              lastPosition=lastplayed.loadData(MainActivity.this);
              Intent intent =new Intent(MainActivity.this, PlayerActivity.class);
              intent.putExtra("POS",lastPosition);
              startActivity(intent);
            }
        });
        cardView_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), FavouriteListActivity.class);
                startActivity(intent);
            }
        });
        cardView_poem_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, PlayerActivity.class);
                intent.putExtra("POS",0);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.aboutwritterId){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            LayoutInflater inflater=LayoutInflater.from(this);
            View view=inflater.inflate(R.layout.writter_layout,null);
            TextView writterTvid=view.findViewById(R.id.writerTvid);
            justify(writterTvid);
            builder.setView(view);
            builder.show();

        }

        return super.onOptionsItemSelected(item);
    }

    public static void justify(final TextView textView) {

        final AtomicBoolean isJustify = new AtomicBoolean(false);

        final String textString = textView.getText().toString();

        final TextPaint textPaint = textView.getPaint();

        final SpannableStringBuilder builder = new SpannableStringBuilder();

        textView.post(new Runnable() {
            @Override
            public void run() {

                if (!isJustify.get()) {

                    final int lineCount = textView.getLineCount();
                    final int textViewWidth = textView.getWidth();

                    for (int i = 0; i < lineCount; i++) {

                        int lineStart = textView.getLayout().getLineStart(i);
                        int lineEnd = textView.getLayout().getLineEnd(i);

                        String lineString = textString.substring(lineStart, lineEnd);

                        if (i == lineCount - 1) {
                            builder.append(new SpannableString(lineString));
                            break;
                        }

                        String trimSpaceText = lineString.trim();
                        String removeSpaceText = lineString.replaceAll(" ", "");

                        float removeSpaceWidth = textPaint.measureText(removeSpaceText);
                        float spaceCount = trimSpaceText.length() - removeSpaceText.length();

                        float eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount;

                        SpannableString spannableString = new SpannableString(lineString);
                        for (int j = 0; j < trimSpaceText.length(); j++) {
                            char c = trimSpaceText.charAt(j);
                            if (c == ' ') {
                                Drawable drawable = new ColorDrawable(0x00ffffff);
                                drawable.setBounds(0, 0, (int) eachSpaceWidth, 0);
                                ImageSpan span = new ImageSpan(drawable);
                                spannableString.setSpan(span, j, j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }

                        builder.append(spannableString);
                    }

                    textView.setText(builder);
                    isJustify.set(true);
                }
            }
        });
    }
}
