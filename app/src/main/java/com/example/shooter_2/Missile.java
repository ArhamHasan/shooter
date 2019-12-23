package com.example.shooter_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Missile {

    int mx,my;
    int mvelocity;
    Bitmap missile;

    public Missile(Context context){
        missile= BitmapFactory.decodeResource(context.getResources(), com.example.shooter_2.R.drawable.missile);
        mx=GameView.dWidth/2 - getMWidth()/2;
        my=GameView.dHeight - GameView.tankHeight;
        mvelocity=200;


    }

    public int getMWidth(){
        return missile.getWidth();
    }
    public int getMheight(){
        return missile.getHeight();
    }
}
