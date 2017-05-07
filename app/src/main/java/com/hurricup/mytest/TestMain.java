package com.hurricup.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridLayout;

public class TestMain extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test_main);

    new ButtonManager(this, (GridLayout)findViewById(R.id.mygrid)).addButtons();
  }
}
