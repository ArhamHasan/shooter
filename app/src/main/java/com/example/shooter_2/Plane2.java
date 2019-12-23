package com.example.shooter_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Plane2 extends Plane {

    Bitmap plane2;

    public Plane2(Context context) {
        super(context);
        plane2= BitmapFactory.decodeResource(context.getResources(), com.example.shooter_2.R.drawable.plane2);
        resetPosition();

    }

    @Override
    public Bitmap getBitmap() {
        return plane2;
    }

    @Override
    public int getWidth() {
        return plane2.getWidth();
    }

    @Override
    public int getHeight() {
        return plane2.getHeight();
    }

    @Override
    public void resetPosition() {
            planeX= -(1750 + random.nextInt(400) );
            planeY=random.nextInt(450);
            velocity=10 + random.nextInt(10) + velocityIncrease;
       // velocity=0;
            if (velocityIncrease < 20){
                velocityIncrease+=2;
            }

    }
}
