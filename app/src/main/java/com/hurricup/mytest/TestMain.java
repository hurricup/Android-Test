package com.hurricup.mytest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridLayout;

public class TestMain extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_main);

    final ButtonManager buttonManager = new ButtonManager(this, (GridLayout)findViewById(R.id.mygrid));
    buttonManager.addButtons();
    SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    sensorManager.registerListener(new SensorEventListener() {
      @Override
      public void onSensorChanged(SensorEvent event) {
        float forceX = event.values[0];
        float forceY = event.values[1];

        //getWindowManager().getDefaultDisplay().getRotation();
        int deltaX = forceX < -3 ? 1 : forceX > 3 ? -1 : 0;
        int deltaY = forceY < -3 ? -1 : forceY > 3 ? 1 : 0;
        if (deltaX == 0 && deltaY == 0) {
          return;
        }

        for (Item item : buttonManager.getItems()) {
          if (item.isMoving()) {
            return;
          }
        }
        for (Item item : buttonManager.getItems()) {
          if (buttonManager.tryToMove(item, item.getX() + deltaX, item.getY() + deltaY)) {
            return;
          }
        }
      }

      @Override
      public void onAccuracyChanged(Sensor sensor, int accuracy) {
      }
    }, gyroscope, 100);
  }
}
