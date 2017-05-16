package com.hurricup.mytest;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by hurricup on 07.05.2017.
 */

public class Item {
  private final ImageView myButton;
  private int myX;
  private int myY;
  private boolean isMoving = false;

  public Item(ButtonManager manager, int x, int y) {
    myX = x;
    myY = y;
    Context context = manager.getContext();
    myButton = new ImageView(context);
    myButton.setImageDrawable(context.getResources().getDrawable(R.drawable.squareava));
  }

  public int getX() {
    return myX;
  }

  public void setX(int x) {
    myX = x;
  }

  public int getY() {
    return myY;
  }

  public void setY(int y) {
    myY = y;
  }

  public ImageView getButton() {
    return myButton;
  }

  public void startMoving() {
    isMoving = true;
  }

  public void endMoving() {
    isMoving = false;
  }

  public boolean isMoving() {
    return isMoving;
  }
}
