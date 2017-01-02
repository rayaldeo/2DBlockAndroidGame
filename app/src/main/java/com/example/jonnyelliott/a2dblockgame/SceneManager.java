package com.example.jonnyelliott.a2dblockgame;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jonny Elliott on 1/1/2017.
 */

public class SceneManager  {

    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;


    public SceneManager(){
        ACTIVE_SCENE =0;
        scenes.add(new GameplayScene());
    }

    public void receieveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).recieveTouch(event);
    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();
    }


    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);

    }


}
