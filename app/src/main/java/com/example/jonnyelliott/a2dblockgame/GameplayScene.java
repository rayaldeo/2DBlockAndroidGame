package com.example.jonnyelliott.a2dblockgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Jonny Elliott on 1/1/2017.
 */

public class GameplayScene implements Scene {

    private Rect r = new Rect();

    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private boolean movingPlayer = false;

    private boolean gameOver = false;
    private long gameOverTime;
    private  OrientationData orientationData;
    private long frameTime;

    public GameplayScene(){
        player = new RectPlayer(new Rect(100,100,200,200), Color.rgb(255,0,0));
        //enemyTile = new Obstacle(new Rect(100,100,200,200), Color.rgb(255,0,255));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        //enemyPoint = new Point(100,200);

        obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);
        //To initialize th GyroScope
        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if(!gameOver) {
            if(frameTime < Constants.INIT_TIME)
                    frameTime = Constants.INIT_TIME;
            int elaspedTime = (int)(System.currentTimeMillis()-frameTime);
            frameTime = System.currentTimeMillis();

            if(orientationData.getOrientation()!=null && orientationData.getStartOrientation()!=null){
                //pitch can equal to -pi to pi
                float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                //roll can equal to -pi/2 to pi/2
                float roll =orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];
                float xSpeed =2 *roll * Constants.SCREEN_WIDTH/1000f;
                float ySpeed = pitch * Constants.SCREEN_HEIGHT/1000f;
                //ternary statement
                playerPoint.x += Math.abs(xSpeed*elaspedTime)>5? xSpeed*elaspedTime:0;
                playerPoint.y -= Math.abs(ySpeed*elaspedTime)>5? ySpeed*elaspedTime:0;
            }
            //This will disable the player from moving off the screen
            if(playerPoint.x<0)
                playerPoint.x=0;
            else if(playerPoint.x > Constants.SCREEN_WIDTH)
                playerPoint.x = Constants.SCREEN_WIDTH;

            if(playerPoint.y<0)
                playerPoint.y=0;
            else if(playerPoint.y > Constants.SCREEN_HEIGHT)
                playerPoint.y = Constants.SCREEN_HEIGHT;


            player.update(playerPoint);
            obstacleManager.update();
            if(obstacleManager.playerCollide(player)){
                gameOver=true;
                gameOverTime=System.currentTimeMillis();
            }

        }

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        obstacleManager.draw(canvas);
        //enemyTile.draw(canvas);

        if(gameOver){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.CYAN);
            drawCenterText(canvas,paint,"Game Over");

        }

    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE =0;
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)event.getX(),(int)event.getY()))
                    movingPlayer = true;
                if(gameOver&& System.currentTimeMillis()-gameOverTime>=2000){
                    reset();
                    gameOver=false;
                    orientationData.newGame();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(movingPlayer&&!gameOver)
                    playerPoint.set((int)event.getX(),(int)event.getY());
                break;

            case MotionEvent.ACTION_UP:
                movingPlayer=false;
                break;
        }

    }
    public void reset(){
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(200,350,75,Color.BLACK);
        movingPlayer=false;

    }

    private void drawCenterText(Canvas canvas, Paint paint,String text){
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
