package com.example.shooter_2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class GameOver extends Activity {

    TextView tvScore,tvPersonalBest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.shooter_2.R.layout.game_over);
        int score=getIntent().getExtras().getInt("score");
        SharedPreferences pref=getSharedPreferences("My Pref",0);
        int scoreSP=pref.getInt("scoreSP",0);
        SharedPreferences.Editor editor=pref.edit();
        if (score > scoreSP){
            scoreSP = score;
            editor.putInt("scoreSP",scoreSP);
            editor.commit();
        }
        tvScore=findViewById(com.example.shooter_2.R.id.tvScore);
        tvPersonalBest=findViewById(com.example.shooter_2.R.id.tvPersonalBest);
        tvScore.setText(""+score);
        tvPersonalBest.setText(""+scoreSP);
    }
    public void restart(View view){
        Intent intent=new Intent(GameOver.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view){
        finish();
    }
}
