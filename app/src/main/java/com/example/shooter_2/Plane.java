package com.example.shooter_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Plane {

    Bitmap plane;
    int planeX, planeY, velocity, velocityIncrease;
    Random random;



    public Plane(Context context) {
        plane = BitmapFactory.decodeResource(context.getResources(), com.example.shooter_2.R.drawable.plane);
        random = new Random();
        resetPosition();


    }

        public Bitmap getBitmap(){
           return plane;
        }


        public int getWidth () {
            return plane.getWidth();
        }

        public int getHeight () {
            return plane.getHeight();
        }
        public void resetPosition(){
            planeX = GameView.dWidth + random.nextInt(600);
            planeY = random.nextInt(650);
            velocity = 15 + random.nextInt(10) + velocityIncrease;
            //velocity=0;

            if (velocityIncrease < 15) {
                velocityIncrease += 2;
            }

        }

    }


