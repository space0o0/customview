package com.example.xxx.customview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.example.xxx.customview.view.ShadeView;

public class MainActivity extends Activity {


    SeekBar seekBar;
    ShadeView shadeView, shadeView2, shadeView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        shadeView = (ShadeView) findViewById(R.id.shadeView);
        shadeView2 = (ShadeView) findViewById(R.id.shadeView2);
        shadeView3 = (ShadeView) findViewById(R.id.shadeView3);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                shadeView.setIconAlpha((float) (progress * 0.01));

                shadeView2.setIconAlpha((float) (1 - (progress * 0.01)));
                shadeView3.setIconAlpha((float) (progress * 0.01));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
