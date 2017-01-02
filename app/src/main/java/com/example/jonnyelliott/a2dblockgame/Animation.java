package com.example.jonnyelliott.a2dblockgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Jonny Elliott on 1/1/2017.
 */

public class Animation {

    private Bitmap[] frames;
    private int frameIndex;

    private boolean isPlaying= false;

    public void play(){
        isPlaying= true;
        frameIndex=0;
        lastFrame = System.currentTimeMillis();
    }

    public boolean isPlaying(){
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

    public void draw(Canvas canvas, Rect destination){
        if(!isPlaying){
            return;
        }
        scaleRect(destination);
        canvas.drawBitmap(frames[frameIndex],null, destination, new Paint());
    }
    public void update(){

        if(!isPlaying)
            return;
        if(System.currentTimeMillis()-lastFrame>frameTime*1000){
            frameIndex++;
            //Ternary Statement in Java
            frameIndex = frameIndex>= frames.length ? 0:frameIndex;
            lastFrame =System.currentTimeMillis();

        }
    }

    public void scaleRect(Rect rect){
        float whRatio = (float)frames[frameIndex].getWidth()/frames[frameIndex].getHeight();
        if(rect.width()>rect.height()){
            rect.left = rect.right-(int)(rect.height()*whRatio);
        }else{
            rect.top = rect.bottom-(int)(rect.width()*(1/whRatio));
        }
    }
}
