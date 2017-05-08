package com.hurricup.mytest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hurricup on 07.05.2017.
 */

public class ButtonManager {
  public static final int BOARD_WIDTH = 4;
  public static final int BOARD_HEIGHT = 4;
  public static final int BOARD_SIZE = BOARD_HEIGHT * BOARD_WIDTH;
  private final List<Item> myItems = new ArrayList<>();
  private final GridLayout myLayout;
  private final Context myContext;

  public ButtonManager(@NonNull Context context, @NonNull GridLayout layout) {
    myLayout = layout;
    myContext = context;
    layout.setColumnCount(BOARD_WIDTH);
    layout.setRowCount(BOARD_HEIGHT);
  }

  public ButtonManager addButtons() {
    for (int y = 0; y < BOARD_HEIGHT; y++) {
      for (int x = 0; x < BOARD_WIDTH; x++) {
        if (y * BOARD_WIDTH + x == BOARD_SIZE - 1) {
          continue;
        }

        // fixme add supporting 2-dementional array to speed up

        Item newItem = new Item(this, x, y);
        myItems.add(newItem);
        ImageButton newButton = newItem.getButton();
        newButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            moveItem(v);
          }
        });

        myLayout.addView(newButton);
      }
    }
    return this;
  }

  private void moveItem(@NonNull View view) {
    Item item = getItem(view);
    if (item == null) {
      return;
    }

    int x = item.getX();
    int y = item.getY();

    boolean r = tryToMove(item, x - 1, y)
                || tryToMove(item, x + 1, y)
                || tryToMove(item, x, y - 1)
                || tryToMove(item, x, y + 1);
  }

  public boolean tryToMove(Item item, int x, int y) {
    if (x < 0 || y < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT || getItem(x, y) != null) {
      return false;
    }

    synchronized (item) {
      if (item.isMoving()) {
        return false;
      }
      item.startMoving();
    }

    makeMove(item, x, y);
    return true;
  }

  private void makeMove(final Item item, final int x, final int y) {
    ImageButton button = item.getButton();

    int deltaX = x - item.getX();
    int deltaY = y - item.getY();
    item.setX(x);
    item.setY(y);

    button.animate()
      .translationXBy(deltaX * button.getWidth())
      .translationYBy(deltaY * button.getMeasuredHeight())
      .setDuration(500)
      .withEndAction(new Runnable() {
        @Override
        public void run() {
          item.endMoving();
        }
      })
      .start();
  }

  public GridLayout getLayout() {
    return myLayout;
  }

  private Item getItem(@NonNull View view) {
    for (Item item : myItems) {
      if (view.equals(item.getButton())) {
        return item;
      }
    }
    return null;
  }

  private Item getItem(int x, int y) {
    for (Item item : myItems) {
      if (item.getX() == x && item.getY() == y) {
        return item;
      }
    }
    return null;
  }

  public Context getContext() {
    return myContext;
  }

  public List<Item> getItems() {
    return myItems;
  }
}
