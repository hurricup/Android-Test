package com.hurricup.mytest;

import android.hardware.SensorEvent;
import android.view.Surface;
import android.view.WindowManager;

import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 16.05.2017.
 */

public class SensorUtil {

  @NotNull
  public static Point3D getNormalizedAccelerometerForces(@NotNull WindowManager windowManager,
                                                         @NotNull SensorEvent sensorEvent) {
    Point3D result = new Point3D(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
    int rotation = windowManager.getDefaultDisplay().getRotation();

    if (rotation == Surface.ROTATION_90) {
      float tmp = result.x;
      result.x = result.y;
      result.y = tmp;
    }
    else if (rotation == Surface.ROTATION_180) {
      result.y = -result.y;
      result.x = -result.x;
    }
    else if (rotation == Surface.ROTATION_270) {
      float tmp = -result.x;
      result.x = result.y;
      result.y = tmp;
    }

    return result;
  }
}
