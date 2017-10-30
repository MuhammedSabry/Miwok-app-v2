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

public class ColorsFragment extends Fragment {

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
    public ColorsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragview, container, false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        
                list = (ListView) rootView.findViewById(R.id.list);
                words.add(new Word(getString(R.string.color_red), getString(R.string.miwok_color_red),
                        R.drawable.color_red, R.raw.color_red));
                words.add(new Word(getString(R.string.color_mustard_yellow), getString(R.string.miwok_color_mustard_yellow),
                        R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
                words.add(new Word(getString(R.string.color_dusty_yellow), getString(R.string.miwok_color_dusty_yellow),
                        R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
                words.add(new Word(getString(R.string.color_green), getString(R.string.miwok_color_green),
                        R.drawable.color_green, R.raw.color_green));
                words.add(new Word(getString(R.string.color_brown), getString(R.string.miwok_color_brown),
                        R.drawable.color_brown, R.raw.color_brown));
                words.add(new Word(getString(R.string.color_gray), getString(R.string.miwok_color_gray),
                        R.drawable.color_gray, R.raw.color_gray));
                words.add(new Word(getString(R.string.color_black), getString(R.string.miwok_color_black),
                        R.drawable.color_black, R.raw.color_black));
                words.add(new Word(getString(R.string.color_white), getString(R.string.miwok_color_white),
                        R.drawable.color_white, R.raw.color_white));
                //Adapter of the words ArrayList
                wordsAdapter numbersArray = new wordsAdapter(getActivity(), words, R.color.category_colors);
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
