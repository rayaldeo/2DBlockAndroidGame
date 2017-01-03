package com.example.jonnyelliott.a2dblockgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Jonny Elliott on 12/30/2016.
 */

public class ObstacleManager {
    //higher Index = lower on Screen = higher y values;
    private ArrayList<Obstacle>obstacles;
    private int score, playerGap,obstaclesGap,obstacleHeight,color;
    private long startTime,initTime;


    public ObstacleManager(int playerGap, int obstaclesGap,int obstacleHeight, int color){
        this.playerGap = playerGap;
        this.obstaclesGap =obstaclesGap;
        this.obstacleHeight=obstacleHeight;
        this.color =color;
        startTime = initTime=System.currentTimeMillis();
        obstacles = new ArrayList<>();
        populateObstacles();


    }

    private void populateObstacles(){
        int currY = -5*Constants.SCREEN_HEIGHT/4;
        while(currY<0){
            int xStart =(int)(Math.random()*(Constants.SCREEN_WIDTH-playerGap));
            obstacles.add(new Obstacle(obstacleHeight,color,xStart,currY,playerGap));
            currY+=obstacleHeight+obstaclesGap;
        }


    }

    public void update(){
        if(startTime< Constants.INIT_TIME){
            startTime = Constants.INIT_TIME;
        }
        int elaspeTime =(int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)(Math.sqrt(1+(startTime-initTime)/3000.0))* Constants.SCREEN_HEIGHT/10000.0f;
        for(Obstacle ob:obstacles){
            ob.incrementY(speed*elaspeTime);
        }
        if(obstacles.get(obstacles.size()-1).getRectangle().top>=Constants.SCREEN_HEIGHT){
            int xStart =(int)(Math.random()*(Constants.SCREEN_WIDTH-playerGap));
            obstacles.add(0,new Obstacle(obstacleHeight,color,xStart,obstacles.get(0).getRectangle().top-obstacleHeight-obstaclesGap,playerGap));
            obstacles.remove(obstacles.size()-1);
            score++;
        }

    }

    public void draw(Canvas canvas){
        for(Obstacle ob: obstacles)
            ob.draw(canvas);
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.BLUE);
           canvas.drawText(""+score,50,50+paint.descent()-paint.ascent(),paint);


    }
    public boolean playerCollide(RectPlayer player){
        for(Obstacle ob:obstacles){
            if(ob.playerCollide(player))
                return true;
        }
        return false;
    }


}
