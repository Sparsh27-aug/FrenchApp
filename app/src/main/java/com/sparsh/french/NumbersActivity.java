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

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mp;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
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
        setContentView(R.layout.activity_numbers);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ArrayList<Word> words=new ArrayList<Word>();
        words.add(new Word("One","Un",R.drawable.number_one,R.raw.one));
        words.add(new Word("Two","Deux",R.drawable.number_two,R.raw.two));
        words.add(new Word("Three","Trois",R.drawable.number_three,R.raw.three));
        words.add(new Word("Four","Quatre",R.drawable.number_four,R.raw.four));
        words.add(new Word("Five","Cinq",R.drawable.number_five,R.raw.five));
        words.add(new Word("Six","Six",R.drawable.number_six,R.raw.six));
        words.add(new Word("Seven","Sept",R.drawable.number_seven,R.raw.seven));
        words.add(new Word("Eight","Huit",R.drawable.number_eight,R.raw.eight));
        words.add(new Word("Nine","Neuf",R.drawable.number_nine,R.raw.nine));
        words.add(new Word("Ten","Dix",R.drawable.number_ten,R.raw.ten));
        WordAdapter adapter=new WordAdapter(this,words,R.color.numbers);
        ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word w = words.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mp = MediaPlayer.create(NumbersActivity.this, w.getAudioId());
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
            //mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}