package com.example.afitch;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.concurrent.TimeUnit;

public class Video_preview extends AppCompatActivity {

    VideoView videoView;
    SeekBar seekBar;
    Handler updateHandler = new Handler();
    boolean state = true; // true이면 정지버튼, false면 재생버튼


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);

        videoView = (VideoView) findViewById(R.id.videoView);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
//        MediaController mc = new MediaController(this);
        // Get controls
        getControls();
//        videoView.setMediaController(mc);
//        mc.setAnchorView(videoView);
        // Prepare video for VideoView
        prepareVideoViewVideo();

    }

    protected void getControls() {
        // Get buttons
        Button btn = (Button) findViewById(R.id.btnPausePlay);
        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == true){
                    stopVideoView();
                    v.setBackgroundResource(R.drawable.play);
                    state = false;
                }
                else{
                    playVideoView();
                    v.setBackgroundResource(R.drawable.pause);
                    state = true;
                }
            }
        });
        Button btn2 = (Button) findViewById(R.id.btnforward);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goForwardVideoView();
            }
        });
        Button btn3 = (Button) findViewById(R.id.btnback);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackVideoView();
            }
        });
    }

    protected void prepareVideoViewVideo() {
        // Set "sample.mp4" in raw directory
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/squat");
        videoView.setVideoURI(uri);

        // Set the listener
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                playVideoView();
                updateSeekbar();
            }
        });
    }
    protected void playVideoView() {
        videoView.start();
    }

    protected void stopVideoView() {
        videoView.pause();
    }

    protected void goForwardVideoView() {
        int currentPosition = videoView.getCurrentPosition();
        videoView.seekTo(currentPosition+10000);
    }

    protected void goBackVideoView() {
        int currentPosition = videoView.getCurrentPosition();
        videoView.seekTo(currentPosition-10000);
    }

    protected void updateSeekbar(){
        long finalTime = videoView.getDuration();
        TextView videoTime = (TextView) findViewById(R.id.videoTime);
        videoTime.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );
        seekBar.setMax((int) finalTime);
        seekBar.setProgress(0);
        updateHandler.postDelayed(updateVideoTime, 100);
    }

    // seekBar를 이동시키기 위한 쓰레드 객체
    // 100ms 마다 viewView의 플레이 상태를 체크하여, seekBar를 업데이트 한다.
    private Runnable updateVideoTime = new Runnable(){
        public void run(){
            long currentPosition = videoView.getCurrentPosition();
            seekBar.setProgress((int) currentPosition);
            updateHandler.postDelayed(this, 100);

        }
    };
}