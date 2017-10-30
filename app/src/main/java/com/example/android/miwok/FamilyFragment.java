package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyFragment extends Fragment {
    ListView list;
    ArrayList<Word> words = new ArrayList<>();
    MediaPlayer audio;
    AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener AudioChanged = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK == i) {
                audio.pause();
                audio.seekTo(0);
            } else if (AudioManager.AUDIOFOCUS_GAIN == i) {
                audio.start();
            } else if (AudioManager.AUDIOFOCUS_LOSS == i) {
                releaseMediaPlayer();
            }
        }
    };
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    public FamilyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragview, container, false);
        list = (ListView) rootView.findViewById(R.id.list);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        //ArrayList of Word class
        words.add(new Word(getString(R.string.family_father), getString(R.string.miwok_family_father),
                R.drawable.family_father, R.raw.family_father));
        words.add(new Word(getString(R.string.family_mother), getString(R.string.miwok_family_mother),
                R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word(getString(R.string.family_son), getString(R.string.miwok_family_son),
                R.drawable.family_son, R.raw.family_son));
        words.add(new Word(getString(R.string.family_daughter), getString(R.string.miwok_family_daughter),
                R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word(getString(R.string.family_older_brother), getString(R.string.miwok_family_older_brother),
                R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word(getString(R.string.family_younger_brother), getString(R.string.miwok_family_younger_brother),
                R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word(getString(R.string.family_older_sister), getString(R.string.miwok_family_older_sister),
                R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word(getString(R.string.family_younger_sister), getString(R.string.miwok_family_younger_sister),
                R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word(getString(R.string.family_grandmother), getString(R.string.miwok_family_grandmother),
                R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word(getString(R.string.family_grandfather), getString(R.string.miwok_family_grandfather),
                R.drawable.family_grandfather, R.raw.family_grandfather));
        //Adapter of the words ArrayList
        wordsAdapter numbersArray = new wordsAdapter(getActivity(), words, R.color.category_family);
        list.setAdapter(numbersArray);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();

                int focus = audioManager.requestAudioFocus(AudioChanged
                        , AudioManager.STREAM_MUSIC
                        , AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
                );
                if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    audio = MediaPlayer.create(getActivity(), words.get(i).getAudio());
                    audio.start();
                }
                audio.setOnCompletionListener(mCompletionListener);

            }
        });
        return rootView;
    }
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (audio != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            audio.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            audio = null;
            audioManager.abandonAudioFocus(AudioChanged);

        }
    }
}
