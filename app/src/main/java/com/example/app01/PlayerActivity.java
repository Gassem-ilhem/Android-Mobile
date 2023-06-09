package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {
    Button btnPlay,btnNext,btnPrevious,btnFastForward,btnFastBackward;
    TextView txtSongName,txtSongStart,txtSongEnd;
    SeekBar seekBar;
    ImageView imgView;
    Thread updateSeekBar;
    String songName;
    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> mySongs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);




        btnPrevious=(Button) findViewById(R.id.btnPrevious);
        btnPlay=(Button) findViewById(R.id.btnPlay);
        btnNext=(Button) findViewById(R.id.btnNext);
        btnFastForward=(Button) findViewById(R.id.btnFastForward);
        btnFastBackward=(Button) findViewById(R.id.btnFastBackward);


        txtSongEnd=(TextView) findViewById(R.id.txtSongEnd);
        txtSongStart=(TextView) findViewById(R.id.txtSongStart);
        txtSongName=(TextView) findViewById(R.id.txtSong);

        seekBar=(SeekBar) findViewById(R.id.seekBar);

        imgView=(ImageView) findViewById(R.id.imgView);

        if(mediaPlayer !=null){
            mediaPlayer.start();
            mediaPlayer.release();
        }

        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();

        mySongs = (ArrayList)bundle.getParcelableArrayList("songs");
        String sName = intent.getStringExtra("songname");
        position=bundle.getInt("pos",0);
        txtSongName.setSelected(true);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        songName=mySongs.get(position).getName();
        txtSongName.setText(songName);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();



        //seekBar
        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int totalDuration= mediaPlayer.getDuration();
                int currentPosition=0;


                while(currentPosition<totalDuration){
                    try{
                            sleep(500);
                            currentPosition = mediaPlayer.getCurrentPosition();
                            seekBar.setProgress(currentPosition);
                    }
                    catch (InterruptedException| IllegalStateException e){
                            e.printStackTrace();
                    }
                }
            }
        };

        seekBar.setMax(mediaPlayer.getDuration());
        updateSeekBar.start();
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.MULTIPLY);
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.purple_500) , PorterDuff.Mode.SRC_IN);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

            String endTime=createTime(mediaPlayer.getDuration());
            txtSongEnd.setText(endTime);

            final Handler handler= new Handler();
            final int delay=1000;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String currentTime= createTime(mediaPlayer.getCurrentPosition());
                    txtSongStart.setText(currentTime);
                    handler.postDelayed(this,delay);

                }
            },delay);


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    btnPlay.setBackgroundResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                }
                else{
                   btnPlay.setBackgroundResource(R.drawable.ic_pause);
                   mediaPlayer.start();
                    TranslateAnimation moveAnim= new TranslateAnimation(-25,25,-25,25);
                    moveAnim.setInterpolator(new AccelerateInterpolator());
                    moveAnim.setDuration(600);
                    moveAnim.setFillEnabled(true);
                    moveAnim.setFillAfter(true);
                    moveAnim.setRepeatMode(Animation.REVERSE);
                    moveAnim.setRepeatCount(1);
                    imgView.startAnimation(moveAnim);

                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btnNext.performClick();
            }
        });



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.stop();
                mediaPlayer.release();
                position=((position+1)%mySongs.size());
                Uri uri= Uri.parse(mySongs.get(position).toString());
                mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                songName=mySongs.get(position).getName();
                txtSongName.setText(songName);
                mediaPlayer.start();


                startAnimation(imgView,360f);
            }
        });






        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.stop();
                mediaPlayer.release();
                position=((position-1)<0)?(mySongs.size()-1):position-1;
                Uri uri= Uri.parse(mySongs.get(position).toString());
                mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
                songName=mySongs.get(position).getName();
                txtSongName.setText(songName);
                mediaPlayer.start();

                startAnimation(imgView,-360f);
            }
        });






        btnFastForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
                    }
            }
        });


        btnFastBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-10000);
                }

            }
        });


    }

    //Animation du image
    public void startAnimation(View view,Float degree){
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(imgView,"rotation",0f,degree);
        objectAnimator.setDuration(1000);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(objectAnimator);
        animatorSet.start();
    }


    public String createTime(int duration){
        String time="";
        int min=duration/1000/60;
        int sec=duration/1000%60;


        time=time+min+":";
        if(sec<10){
            time+="0";
        }
        time+=sec;
        return time;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

}