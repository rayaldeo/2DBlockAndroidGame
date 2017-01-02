package com.example.jonnyelliott.a2dblockgame;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Jonny Elliott on 12/28/2016.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private SceneManager manager;
    //private Obstacle enemyTile;
    //private Point enemyPoint;

    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        Constants.CURRENT_CONTEXT= context;
        manager = new SceneManager();
        setFocusable(true);

    }

    public void surfaceChanged(SurfaceHolder holder, int formate, int whidth, int height){}


    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }


    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry =true;
        while(true){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        manager.receieveTouch(event);
        return true;
    }

    public void update(){
        manager.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        manager.draw(canvas);

    }


}
