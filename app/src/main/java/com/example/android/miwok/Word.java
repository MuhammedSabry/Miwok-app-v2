package com.example.android.miwok;

/**
 * Created by Muhammed on 9/24/2017.
 */

public class Word {
    private String Native, Arabic;
    private int image,audio;
    public Word(String s, String y) {
        Native = y;
        Arabic = s;
        image = 0;
    }

    public Word(String s, String y, int z) {
        Native = y;
        Arabic = s;
        audio = z;
    }

    public Word(String s, String y, int z, int aud) {
        Native = y;
        Arabic = s;
        image = z;
        audio = aud;
    }

    public String getNative() {
        return Native;
    }

    public String getArabic() {
        return Arabic;
    }

    public int getImage() {
        return image;
    }
    public int getAudio()
    {return  audio;}
}
