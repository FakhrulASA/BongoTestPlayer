package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer ;
    private Handler handler = new Handler();
    private TextView start,end;
    private Button playz,next,prev;
    private ImageView play;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start=findViewById(R.id.start);
        end=findViewById(R.id.end);
        play=findViewById(R.id.play);
        prev=findViewById(R.id.prev);
        next=findViewById(R.id.next);
        seekBar=findViewById(R.id.seekBar);
        mediaPlayer =new MediaPlayer();
        seekBar.setMax(100);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                media("https://dnbshare.com/download/Zara_Larsson_MNEK_-_Never_Forget_You_TDay_Bootleg_mp3.mp3.html?file=Zara_Larsson_MNEK_-_Never_Forget_You_TDay_Bootleg_mp3.mp3&payload=23405e4cafb15a8e9fd71a4207f81c0f8236fb2516d1a2ff332d4e60db36426a,1595802055&play=1");
                end.setText(timer(mediaPlayer.getDuration()));
                mediaPlayer.start();
                updateseekbar();
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                media("https://dnbshare.com/download/SodaSneakers_The_Cities_Beautiful_httpssoundcloud.comdj-super-t.mp3.html?file=SodaSneakers_The_Cities_Beautiful_httpssoundcloud.comdj-super-t.mp3&payload=49f3644c7daba4001ae0f8ada11dd8197b56a8af012cefe701cf5d287e089945,1595802004&play=1");
                end.setText(timer(mediaPlayer.getDuration()));
                mediaPlayer.start();
                updateseekbar();
            }
        });
        media("https://dnbshare.com/download/Wiz_Khalifa_-_See_You_Again_ft._Charlie_Puth_Danger_Bootleg_1.mp3.html?file=Wiz_Khalifa_-_See_You_Again_ft._Charlie_Puth_Danger_Bootleg_1.mp3&payload=6e0e3fa41cddaf281f57aa1184cf50cbb396a10d53d54a4763da23e6afd7be3a,1595801495&play=1");
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mediaPlayer.isPlaying())
                {
                    handler.removeCallbacks(set);
                    mediaPlayer.pause();
                    play.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);

                }
                else
                {
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                    updateseekbar();

                }
            }
        });

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar seekBar  =(SeekBar) view;
                //getting current song position
                int position=(mediaPlayer.getDuration()/100)*seekBar.getProgress();
                mediaPlayer.seekTo(position);
                start.setText(timer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
    }

    private void media(String url)
    {
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            end.setText(timer(mediaPlayer.getDuration()));
        }catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG);
        }
    }


    private Runnable set= new Runnable() {
        @Override
        public void run() {
            updateseekbar();
            long duration=mediaPlayer.getCurrentPosition();
            start.setText(timer(duration));
        }
    };

    private void updateseekbar()
    {
        if(mediaPlayer.isPlaying())
        {
            seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration())*100));
            handler.postDelayed(set,1000);
        }
    }

    private String timer(long millSec)
    {
      String timeString="";
      String secondString;
      int hour=(int)(millSec/(1000*60*60));
      int min=(int)(millSec%(1000*60*60))/(1000*60);
      int sec=((int)(millSec%(1000*60*60))%(1000*60)/1000);
      if(hour>0){
          timeString=hour+":";
      }
      if(sec<10)
      {

          secondString="0"+sec;
      }
      else
      {
          secondString=""+sec;
      }
      timeString=timeString + min+":"+secondString;
      return timeString;
    }



}