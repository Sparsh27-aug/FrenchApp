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

public class FamilyActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_family);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ArrayList<Word> family=new ArrayList<Word>();
        family.add(new Word("Grandfather","Grand-père",R.drawable.family_grandfather,R.raw.grandfather));
        family.add(new Word("Grandmother","Grand-mère",R.drawable.family_grandmother,R.raw.grandmother));
        family.add(new Word("Father","Père",R.drawable.family_father,R.raw.father));
        family.add(new Word("Mother","Mère",R.drawable.family_mother,R.raw.mother));
        family.add(new Word("Son","Fils",R.drawable.family_son,R.raw.son));
        family.add(new Word("Daughter","Fille",R.drawable.family_daughter,R.raw.daughter));
        family.add(new Word("Brother","Frère",R.drawable.family_younger_brother,R.raw.brother));
        family.add(new Word("Sister","Sœur",R.drawable.family_younger_sister,R.raw.sister));
        family.add(new Word("Husband","Mari",R.drawable.family_older_brother,R.raw.husband));
        family.add(new Word("Wife","Femme",R.drawable.family_older_sister,R.raw.wife));
        WordAdapter adapter=new WordAdapter(this,family,R.color.family);
        ListView family_list=(ListView)findViewById(R.id.family_list_view);
        family_list.setAdapter(adapter);

        family_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word w = family.get(position);
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(FamilyActivity.this, w.getAudioId());
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