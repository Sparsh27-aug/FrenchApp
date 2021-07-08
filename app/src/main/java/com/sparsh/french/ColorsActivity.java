package com.sparsh.french;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    MediaPlayer mp;
    private AudioManager mAudioManager;
   private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
            {
                mp.pause();
                mp.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
            {
                mp.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener completionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
          releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> colors = new ArrayList<Word>();
        colors.add(new Word("Red", "Rouge",R.drawable.color_red,R.raw.red));
        colors.add(new Word("Yellow", "Jaune",R.drawable.color_mustard_yellow,R.raw.yellow));
        colors.add(new Word("White", "Blanc/Blanche",R.drawable.color_white,R.raw.white));
        colors.add(new Word("Green", "Vert/Verte",R.drawable.color_green,R.raw.green));
        colors.add(new Word("Black", "Noir/Noire",R.drawable.color_black,R.raw.black));
        colors.add(new Word("Gray", "Gris/Grise",R.drawable.color_gray,R.raw.gray));
        colors.add(new Word("Brown", "Marron",R.drawable.color_brown,R.raw.brown));
        WordAdapter colorAdapter = new WordAdapter(this, colors,R.color.colors);
        ListView colorlist = (ListView) findViewById(R.id.Color_list_view);
        colorlist.setAdapter(colorAdapter);

        colorlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word w=colors.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mp=MediaPlayer.create(ColorsActivity.this,w.getAudioId());
                mp.start();
                mp.setOnCompletionListener(completionListener);
            }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer()
    {
        if(mp!=null)
        {
            mp.release();
            mp=null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}