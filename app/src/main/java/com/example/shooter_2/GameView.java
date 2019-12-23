package com.example.shooter_2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GameView extends View {
    Bitmap background,tank;
    static int tankWidth,tankHeight;
    Rect rect;
    static int dHeight, planeWidth;
    static int dWidth;

    Context context;

    ArrayList<Plane>planes;
    ArrayList<Missile>missiles;
    Plane2 plane2;

    Bitmap plane;
    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS=30;
    int score=0;

    SoundPool sound;
    int fire=0;
    int point=0;
    int bird=0;

    Paint scoreP;
    final int TEXT_SIZE=60;

    Paint livesP;
    int lives;

    public GameView(Context context) {
        super(context);
        this.context=context;
        tank=BitmapFactory.decodeResource(getResources(), com.example.shooter_2.R.drawable.tank);
        tankWidth=tank.getWidth();
        tankHeight=tank.getHeight();

        background= BitmapFactory.decodeResource(getResources(), com.example.shooter_2.R.drawable.background);
        Display display=((Activity)getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth=size.x;
        dHeight=size.y;
        rect=new Rect(0,0,dWidth,dHeight);
        planes=new ArrayList<>();
        missiles=new ArrayList<>();
        for(int i=0; i<2; i++) {
            Plane plane = new Plane(context);
            planes.add(plane);
        }
        plane2=new Plane2(context);
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        sound= new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        fire = sound.load(context, com.example.shooter_2.R.raw.fire,1);
        point= sound.load(context, com.example.shooter_2.R.raw.point,1);
        bird= sound.load(context, com.example.shooter_2.R.raw.bird,1);

        lives=5;

        scoreP=new Paint();
        scoreP.setColor(Color.BLACK);
        scoreP.setTextSize(TEXT_SIZE);
        scoreP.setTextAlign(Paint.Align.LEFT);

        livesP=new Paint();
        livesP.setColor(Color.BLACK);
        livesP.setTextSize(TEXT_SIZE);
        livesP.setTextAlign(Paint.Align.RIGHT);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(background,null,rect,null);
        for (int i=0; i < planes.size();i++){
            canvas.drawBitmap(planes.get(i).getBitmap(),planes.get(i).planeX,planes.get(i).planeY,null);
            planes.get(i).planeX-=planes.get(i).velocity;
            if (planes.get(i).planeX < -planes.get(i).getWidth()){
                planes.get(i).resetPosition();
                lives--;
                if(lives==0){
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("score",score);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            }
        }
        canvas.drawBitmap(plane2.getBitmap(),plane2.planeX,plane2.planeY,null);
        plane2.planeX+=plane2.velocity;
        if (plane2.planeX > dWidth){
            plane2.resetPosition();
        }

        for (int i=0; i<missiles.size();i++){
            if(missiles.get(i).my > -missiles.get(i).getMheight()){

                missiles.get(i).my -= missiles.get(i).mvelocity;
                canvas.drawBitmap(missiles.get(i).missile,missiles.get(i).mx,missiles.get(i).my,null);

                if( (missiles.get(i).mx + missiles.get(i).getMWidth() >= planes.get(0).planeX ) &&
                        ( missiles.get(i).mx  <=  planes.get(0).planeX + planes.get(0).getWidth() ) &&
                        (missiles.get(i).my >= planes.get(0).planeY ) &&
                        ( missiles.get(i).my - (missiles.get(i).getMheight()/10.0) <=  planes.get(0).planeY + planes.get(0).getHeight() ) ){

                    if(point != 0){
                        sound.play(point,1,1,0,0,1);
                    }

                    missiles.remove(i);
                    score++;
                    planes.get(0).resetPosition();

                }
                else if( (missiles.get(i).mx + missiles.get(i).getMWidth() >= planes.get(1).planeX ) &&
                        ( missiles.get(i).mx <=  planes.get(1).planeX + planes.get(1).getWidth() ) &&
                        (missiles.get(i).my >= planes.get(1).planeY ) &&
                        ( missiles.get(i).my - (missiles.get(i).getMheight()/10.0)  <=  planes.get(1).planeY + planes.get(1).getHeight() ) ){

                    if(point != 0){
                        sound.play(point,1,1,0,0,1);
                    }

                    missiles.remove(i);
                    score++;
                    planes.get(1).resetPosition();

                }
                else if( (missiles.get(i).mx + missiles.get(i).getMWidth() >= plane2.planeX ) &&
                        ( missiles.get(i).mx  <=  plane2.planeX + plane2.getWidth() ) &&
                        (missiles.get(i).my >= plane2.planeY ) &&
                        ( missiles.get(i).my - (missiles.get(i).getMheight()/10.0) <=  plane2.planeY + plane2.getHeight() ) ){

                    if(bird != 0){
                        sound.play(bird,1,1,0,0,1);
                    }

                    missiles.remove(i);
                    score-=5;
                    if (score < 0){
                        score=0;
                    }
                    lives--;
                    if(lives==0){
                        Intent intent = new Intent(context, GameOver.class);
                        intent.putExtra("score",score);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }
                    plane2.resetPosition();

                }


            }
            else{
                missiles.remove(i);
            }
        }


        canvas.drawBitmap(tank,dWidth/2 - tankWidth/2,(dHeight-tankHeight),null);
        canvas.drawText("Score: " + score,50,TEXT_SIZE,scoreP);
        canvas.drawText("Lives: " + lives + "/5", dWidth - 50 , TEXT_SIZE, livesP);


        handler.postDelayed(runnable,UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchx=event.getX();
        float touchy=event.getY();
        int action=event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            if(touchx <= dWidth && touchy<= dHeight){
                Log.i("Tank","is tapped");
                if (missiles.size() < 2){
                    Missile m= new Missile(context);
                    missiles.add(m);
                    if(fire != 0){
                        sound.play(fire,1,1,0,0,1);
                    }
                }
            }
        }

        return true;
    }
}
