package com.example.android.miwok;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Muhammed on 9/24/2017.
 */

public class wordsAdapter extends ArrayAdapter<Word> {

    private int backgroundColour;
    public wordsAdapter(Context x, ArrayList<Word> y,int colour)
    {
        super(x,0,y);
        backgroundColour = colour;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.miwok_list
                            ,parent
                            ,false);

        LinearLayout textLayout = (LinearLayout) convertView.findViewById(R.id.TextLayout);
        int color = ContextCompat.getColor(getContext(),backgroundColour);
        textLayout.setBackgroundColor(color);
        Word word = getItem(position);
        TextView arabicWord = (TextView) convertView.findViewById(R.id.arabicWord);

        arabicWord.setText(word.getArabic());

        TextView nativeWord = (TextView) convertView.findViewById(R.id.nativeWord);

        nativeWord.setText(word.getNative());
        ImageView wordImage = (ImageView) convertView.findViewById(R.id.wordImage);
        if(word.getImage()==0)
            wordImage.setVisibility(View.GONE);
            else
            wordImage.setImageResource(word.getImage());
        return convertView;

    }
}
