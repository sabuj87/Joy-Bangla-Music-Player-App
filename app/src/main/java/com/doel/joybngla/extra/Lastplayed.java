package com.doel.joybngla.extra;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Lastplayed {

 public void saveData(int position, Context context){

     SharedPreferences pref = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
     SharedPreferences.Editor editor = pref.edit();
     editor.putInt("POSITION", position);
     editor.commit();
 }
 public int loadData(Context context){
     SharedPreferences pref = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
     int value = pref.getInt("POSITION",0);
     return value;
 }
}
