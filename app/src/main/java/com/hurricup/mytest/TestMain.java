package com.hurricup.mytest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.GridLayout;

public class TestMain extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_main);

    final ButtonManager buttonManager = new ButtonManager(this, (GridLayout)findViewById(R.id.mygrid));
    buttonManager.addButtons();
    final SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
    Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    sensorManager.registerListener(new SensorEventListener() {
      @Override
      public void onSensorChanged(SensorEvent event) {
        CheckBox tiltCheckbox = (CheckBox)findViewById(R.id.tilt_checkbox);
        if (!tiltCheckbox.isChecked()) {
          return;
        }

        Point3D forces = SensorUtil.getNormalizedAccelerometerForces(getWindowManager(), event);
        int deltaX = forces.x < -3 ? 1 : forces.x > 3 ? -1 : 0;
        int deltaY = forces.y < -3 ? -1 : forces.y > 3 ? 1 : 0;
        if (deltaX == 0 && deltaY == 0) {
          return;
        }

        for (Item item : buttonManager.getItems()) {
          if (item.isMoving()) {
            return;
          }
        }
        for (Item item : buttonManager.getItems()) {
          // fixme we should first process axis with max force
          if (deltaX != 0 && buttonManager.tryToMove(item, item.getX() + deltaX, item.getY())) {
            return;
          }
          if (deltaY != 0 && buttonManager.tryToMove(item, item.getX(), item.getY() + deltaY)) {
            return;
          }
        }
      }

      @Override
      public void onAccuracyChanged(Sensor sensor, int accuracy) {
      }
    }, gyroscope, 200);
  }
}
