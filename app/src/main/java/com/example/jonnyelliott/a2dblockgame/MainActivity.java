package com.example.jonnyelliott.a2dblockgame;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by Jonny Elliott on 12/27/2016.
 */

public class MainActivity extends Activity{



    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Set Application to Full Screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method removes the Title Bar of application
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        //How to get the Size of the Device's Screen
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.SCREEN_WIDTH =dm.widthPixels;

        setContentView(new GamePanel(this));
    }
}
