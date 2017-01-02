package com.example.jonnyelliott.a2dblockgame;

import android.graphics.Bitmap;

/**
 * Created by Jonny Elliott on 1/1/2017.
 */

public class Animation {

    private Bitmap[] frames;
    private int frameIndex;

    private boolean isPlaying= false;

    public boolean isPlayed(){
        return isPlaying;
    }

    public void stop(){
        isPlaying= false;
    }

    private float frameTime;
    private long lastFrame;

    public Animation(Bitmap[] frames, float animTime){
        this.frames = frames;
        frameIndex =0;

        frameTime = animTime/frames.length;
        lastFrame = System.currentTimeMillis();

    }

    public void update(){
        if(System.currentTimeMillis()-lastFrame>frameTime*1000){
            frameIndex++;
            //Ternary Statement in Java
            frameIndex = frameIndex>= frames.length ? 0:frameIndex;

        }
    }
}
