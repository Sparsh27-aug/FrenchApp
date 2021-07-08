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

public class GreetingsActivity extends AppCompatActivity {
    MediaPlayer mp;
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
        setContentView(R.layout.activity_greetings);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ArrayList<Word> greetings=new ArrayList<Word>();
        greetings.add(new Word("Good Day","Bonjour",R.raw.goodday));
        greetings.add(new Word("Good Morning","Bon Matin",R.raw.morning));
        greetings.add(new Word("Good Noon","Bon Midi",R.raw.noon));
        greetings.add(new Word("Good Afternoon","Bonne Après-midi",R.raw.afternoon));
        greetings.add(new Word("Good Evening","Bonne Soirée",R.raw.evening));
        greetings.add(new Word("Good Night","Bonne Nuit",R.raw.night));
        greetings.add(new Word("Hello","Salut",R.raw.hello));
        greetings.add(new Word("Take Care","Prends Soin",R.raw.tc));
        greetings.add(new Word("See You Soon","À Bientôt",R.raw.sys));
        greetings.add(new Word("Bye","Au Revoir",R.raw.bye));

        WordAdapter adapter=new WordAdapter(this,greetings,R.color.greet);
        ListView greet=(ListView)findViewById(R.id.greet_list_view);
        greet.setAdapter(adapter);

       greet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word w = greetings.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(GreetingsActivity.this, w.getAudioId());
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