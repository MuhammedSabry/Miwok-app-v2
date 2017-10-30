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

public class PhrasesFragment extends Fragment {
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
    public PhrasesFragment()
    {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragview, container, false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        list = (ListView) rootView.findViewById(R.id.list);
        //ArrayList of Word class
        words.add(new Word(getString(R.string.phrase_where_are_you_going),
                getString(R.string.miwok_phrase_where_are_you_going), R.raw.phrase_where_are_you_going));
        words.add(new Word(getString(R.string.phrase_what_is_your_name),
                getString(R.string.miwok_phrase_what_is_your_name), R.raw.phrase_what_is_your_name));
        words.add(new Word(getString(R.string.phrase_my_name_is),
                getString(R.string.miwok_phrase_my_name_is), R.raw.phrase_my_name_is));
        words.add(new Word(getString(R.string.phrase_how_are_you_feeling),
                getString(R.string.miwok_phrase_how_are_you_feeling), R.raw.phrase_how_are_you_feeling));
        words.add(new Word(getString(R.string.phrase_im_feeling_good),
                getString(R.string.miwok_phrase_im_feeling_good), R.raw.phrase_im_feeling_good));
        words.add(new Word(getString(R.string.phrase_are_you_coming),
                getString(R.string.miwok_phrase_are_you_coming), R.raw.phrase_are_you_coming));
        words.add(new Word(getString(R.string.phrase_yes_im_coming),
                getString(R.string.miwok_phrase_yes_im_coming), R.raw.phrase_yes_im_coming));
        words.add(new Word(getString(R.string.phrase_im_coming),
                getString(R.string.miwok_phrase_im_coming), R.raw.phrase_im_coming));
        words.add(new Word(getString(R.string.phrase_lets_go),
                getString(R.string.miwok_phrase_lets_go), R.raw.phrase_lets_go));
        words.add(new Word(getString(R.string.phrase_come_here),
                getString(R.string.miwok_phrase_come_here), R.raw.phrase_come_here));
        //Adapter of the words ArrayList
        wordsAdapter numbersArray = new wordsAdapter(getActivity(), words, R.color.category_phrases);
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
