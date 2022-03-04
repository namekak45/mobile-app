package com.example.shakecount;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    private static final int POLL_INTERVAL = 500;
    private static final int INTERVAL_MS = 20;
    private final Handler hdr = new Handler();
    private PowerManager.WakeLock wl;
    SensorInfo sensor_info = new SensorInfo();
    SensorInfo previous_sensor_info = new SensorInfo();
    private static final int shake_threshold = 8;
    private int shake_count = 0;
    private static final int shake = 4;
    private long previousTimestamp;
    Boolean shown_dialog = false;
    private final Runnable pollTask = new Runnable() {
        public void run() {
            showDialog();
            hdr.postDelayed(pollTask, POLL_INTERVAL);
        }
    };

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "Sensors Info");
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){
        // TO DO
    }

    public void onSensorChanged(SensorEvent event){
        int type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER) {
            sensor_info.accX=event.values[0];
            sensor_info.accY=event.values[1];
            sensor_info.accZ=event.values[2];
        }
        if (type == Sensor.TYPE_LIGHT) {
            sensor_info.light=event.values[0];
        }
        if (type == Sensor.TYPE_ORIENTATION) {
            sensor_info.orX=event.values[0];
            sensor_info.orY=event.values[1];
            sensor_info.orZ=event.values[2];
        }
        if (type == Sensor.TYPE_PROXIMITY) {
            sensor_info.proximity=event.values[0];
        }
    }

    public void showDialog() {
        TextView text1 = (TextView) findViewById(R.id.introduction);
        ImageView image1 = (ImageView) findViewById(R.id.imageView2);
        Animation vibrate ;
        vibrate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        if( (Math.abs(sensor_info.accX-previous_sensor_info.accX)>shake_threshold) || (Math.abs(sensor_info.accY-previous_sensor_info.accY)>shake_threshold) || (Math.abs(sensor_info.accZ-previous_sensor_info.accZ)>shake_threshold) ) {
            final long now = System.currentTimeMillis();
            if (now - previousTimestamp > INTERVAL_MS) {
                int shake_left = shake - shake_count;
                image1.startAnimation(vibrate);
                shake_count++;
                if(shake_left==0){
                    shake_left=4;
                }
                String message1 = String.format(getResources().getString(R.string.introduction)+" %1$d" +" ครั้ง", shake_left);
                text1.setText(message1);
                previousTimestamp = now;
            }
            if(shake_count==4||shake_count>4){
                Random rand = new Random();
                int kaucim_no = rand.nextInt(10);
                System.out.println(kaucim_no);
                kaucimResult(kaucim_no);
                shake_count=0;
            }
            previous_sensor_info.accX = sensor_info.accX;
            previous_sensor_info.accY = sensor_info.accY;
            previous_sensor_info.accZ = sensor_info.accZ;
        }//end if

    }
    public void kaucimResult(int result){
        AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        if (result==0){
            alert.setTitle("ความหมายเซียมซี ศาลเจ้าพ่อหลักเมืองเลขที่ 1");
            alert.setMessage(String.format(getResources().getString(R.string.kauchim1)));
            alert.setCancelable(true);
        } else if (result==1){
            alert.setTitle("ความหมายเซียมซี ศาลเจ้าพ่อหลักเมืองเลขที่ 2");
            alert.setMessage(String.format(getResources().getString(R.string.kauchim2)));
            alert.setCancelable(true);
        }else if (result==2){
            alert.setTitle("ความหมายเซียมซี ศาลเจ้าพ่อหลักเมืองเลขที่ 3");
            alert.setMessage(String.format(getResources().getString(R.string.kauchim3)));
            alert.setCancelable(true);
        }else if (result==3){
            alert.setTitle("ความหมายเซียมซี ศาลเจ้าพ่อหลักเมืองเลขที่ 4");
            alert.setMessage(String.format(getResources().getString(R.string.kauchim4)));
            alert.setCancelable(true);
        }else if (result==4){
            alert.setTitle("ความหมายเซียมซี ศาลเจ้าพ่อหลักเมืองเลขที่ 5");
            alert.setMessage(String.format(getResources().getString(R.string.kauchim5)));
            alert.setCancelable(true);
        }else if (result==5){
            alert.setTitle("ความหมายเซียมซี ศาลเจ้าพ่อหลักเมืองเลขที่ 6");
            alert.setMessage(String.format(getResources().getString(R.string.kauchim6)));
            alert.setCancelable(true);
        }else if (result==6){
            alert.setTitle("ความหมายเซียมซี ศาลเจ้าพ่อหลักเมืองเลขที่ 7");
            alert.setMessage(String.format(getResources().getString(R.string.kauchim7)));
            alert.setCancelable(true);
        }else if (result==7){
            alert.setTitle("ความหมายเซียมซี ศาลเจ้าพ่อหลักเมืองเลขที่ 8");
            alert.setMessage(String.format(getResources().getString(R.string.kauchim8)));
            alert.setCancelable(true);
        }else if (result==8){
            alert.setTitle("ความหมายเซียมซี ศาลเจ้าพ่อหลักเมืองเลขที่ 9");
            alert.setMessage(String.format(getResources().getString(R.string.kauchim9)));
            alert.setCancelable(true);
        }else if (result==9){
            alert.setTitle("ความหมายเซียมซี ศาลเจ้าพ่อหลักเมืองเลขที่ 10");
            alert.setMessage(String.format(getResources().getString(R.string.kauchim10)));
            alert.setCancelable(true);
        }
        if(!shown_dialog) {
            shown_dialog = true;
            alert.setIcon(android.R.drawable.btn_star_big_on);
            alert.setPositiveButton("ตกลง",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            shown_dialog = false;
                        }
                    });
            AlertDialog alertRes = alert.create();
            alertRes.show();
        }
    }

    @SuppressLint("WakelockTimeout")
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);

        if (!wl.isHeld()) {
            wl.acquire();
        }
        hdr.postDelayed(pollTask, POLL_INTERVAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

        if (wl.isHeld()) {
            wl.release();
        }
        hdr.removeCallbacks(pollTask);
    }

    static class SensorInfo{
        float accX, accY, accZ;
        float light;
        float orX, orY, orZ;
        float proximity;
    }

}//end MainActivity