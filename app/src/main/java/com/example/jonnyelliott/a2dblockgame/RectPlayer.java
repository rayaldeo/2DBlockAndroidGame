package com.example.jonnyelliott.a2dblockgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Jonny Elliott on 12/29/2016.
 */

public class RectPlayer implements GameObject{

    private Rect rectangle;
    private int color;

    public RectPlayer(Rect rectangle,int color){
        this.rectangle =rectangle;
        this.color=color;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(this.color);
        canvas.drawRect(this.rectangle,paint);

    }

    @Override
    public void update() {

    }

    public void update(Point point) {
        //left,top,right,bottom
        rectangle.set(point.x-rectangle.width()/2,point.y-rectangle.height()/2,
                point.x+rectangle.width()/2,point.y+rectangle.height()/2);
    }

    public Rect getRectangle(){
        return rectangle;
    }
}
