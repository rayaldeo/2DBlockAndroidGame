package com.example.jonnyelliott.a2dblockgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Jonny Elliott on 12/29/2016.
 */

public class RectPlayer implements GameObject{

    private Rect rectangle;
    private int color;
    private Animation idle,walkRight;
    private Animation walkLeft;
    private BitmapFactory bf  =new BitmapFactory();
    private AnimationManager animManager;

    public RectPlayer(Rect rectangle,int color){
        this.rectangle =rectangle;
        this.color=color;
        //Creating New Animation in Constructor
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.idle);
        Bitmap walksmallStep= bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.walksmallstep);
        Bitmap walkBigStep =bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.walkwidestep);
        idle = new Animation(new Bitmap[]{idleImg},2);
        walkRight= new Animation (new Bitmap[]{walksmallStep,walkBigStep},0.5f);
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap walksmallStep_left = Bitmap.createBitmap(walksmallStep, 0, 0, walksmallStep.getWidth(), walksmallStep.getHeight(), m, false);
        Bitmap walkBigStep_left =Bitmap.createBitmap(walkBigStep, 0, 0, walkBigStep.getWidth(), walkBigStep.getHeight(), m, false);
        walkLeft= new Animation (new Bitmap[]{walksmallStep_left,walkBigStep_left},0.5f);

        animManager = new AnimationManager(new Animation[]{idle,walkRight,walkLeft});
    }
    @Override
    public void draw(Canvas canvas) {
//        Paint paint = new Paint();
//        paint.setColor(this.color);
//        canvas.drawRect(this.rectangle,paint);
        animManager.draw(canvas,rectangle);

    }

    @Override
    public void update() {
        animManager.update();
    }

    public void update(Point point) {
        float oldLeft = rectangle.left;
        //left,top,right,bottom
        rectangle.set(point.x-rectangle.width()/2,point.y-rectangle.height()/2,
                point.x+rectangle.width()/2,point.y+rectangle.height()/2);
        int state = 0;
        if(rectangle.left - oldLeft>5)
            state =1;
        else if(rectangle.left - oldLeft<-5)
            state =2;


        animManager.playAnim(state);
        animManager.update();
    }

    public Rect getRectangle(){
        return rectangle;
    }
}
