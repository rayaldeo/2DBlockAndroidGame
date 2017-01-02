package com.example.jonnyelliott.a2dblockgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Jonny Elliott on 12/29/2016.
 */

public class Obstacle implements GameObject {

    private Rect rectangle,rectangle2;
    private int color,startX,playerGap;


    public Obstacle(int rectHeight,int color,int startX,int startY,int playerGap){
      //left,top, right, bottom
       this.rectangle = new Rect(0,startY,startX,startY+rectHeight);
        this.rectangle2 = new Rect(startX+playerGap,startY,Constants.SCREEN_WIDTH,startY+rectHeight);
        this.color=color;

    }

    public boolean playerCollide(RectPlayer player){
        return Rect.intersects(rectangle,player.getRectangle())
                ||Rect.intersects(rectangle2,player.getRectangle());

    }
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
        canvas.drawRect(rectangle2,paint);

    }

    @Override
    public void update() {

    }

    public Rect getRectangle(){
        return rectangle;
    }

    public void incrementY(float y){
        this.rectangle.top+=y;
        this.rectangle.bottom+=y;
        this.rectangle2.top+=y;
        this.rectangle2.bottom+=y;
    }
}
