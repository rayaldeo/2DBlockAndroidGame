package com.example.jonnyelliott.a2dblockgame;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Jonny Elliott on 1/2/2017.
 */

public class OrientationData implements SensorEventListener {
    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnometer;
    private float[] accelOutput;
    private float[] magOutput;
    private float[] orientation = new float[3];

    public OrientationData(){
        manager= (SensorManager) Constants.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public float[] getOrientation(){
        return orientation;
    }

    public void register(){
        manager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this,magnometer,SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause(){
        manager.unregisterListener(this);
    }

    private float[] startOrientatioin=null;

    public float[] getStartOrientation(){
        return startOrientatioin;
    }

    public void newGame(){
        startOrientatioin=null;
    }

    public float[] getAccelOutput() {
        return accelOutput;
    }

    public float[] getMagOutput() {
        return magOutput;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }


    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
            accelOutput= event.values;
        else if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
            magOutput = event.values;

        if(accelOutput!=null &&magOutput!=null){
            float[] R = new float[9];
            float[] I =new float[9];
            boolean success =SensorManager.getRotationMatrix(R,I,accelOutput,magOutput);
            if(success){
                SensorManager.getOrientation(R,orientation);
                if(startOrientatioin ==null){
                    startOrientatioin = new float[orientation.length];
                    System.arraycopy(orientation,0,startOrientatioin,0,orientation.length);//This is needed because float array and array are not primitive types and making them equal to one another the compiler reference one object
                    //startOrientatioin = orientation;
                }
            }
        }
    }
}
