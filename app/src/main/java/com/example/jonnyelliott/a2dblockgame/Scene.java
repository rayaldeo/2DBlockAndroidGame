package com.example.jonnyelliott.a2dblockgame;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Jonny Elliott on 1/1/2017.
 */

public interface Scene {

    public void update();

    public void draw(Canvas canvas);

    public void terminate();

    public void recieveTouch(MotionEvent event);
}
