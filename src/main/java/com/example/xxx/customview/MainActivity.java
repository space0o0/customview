package com.example.xxx.customview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.xxx.customview.view.ShadeView;
import com.example.xxx.customview.view.roundWave;

public class MainActivity extends Activity implements View.OnClickListener{


    SeekBar seekBar;
    ShadeView shadeView, shadeView2, shadeView3;
    roundWave roundWave;
    Button bt_waveView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        shadeView = (ShadeView) findViewById(R.id.shadeView);
        shadeView2 = (ShadeView) findViewById(R.id.shadeView2);
        shadeView3 = (ShadeView) findViewById(R.id.shadeView3);
        roundWave= (roundWave) findViewById(R.id.roundWave);
        bt_waveView= (Button) findViewById(R.id.bt_waveView);

        bt_waveView.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                shadeView.setIconAlpha((float) (progress * 0.01));

                shadeView2.setIconAlpha((float) (1 - (progress * 0.01)));
                shadeView3.setIconAlpha((float) (progress * 0.01));

                roundWave.invalidateView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_waveView:
                Intent i=new Intent(this,WaveViewActivity.class);
                startActivity(i);
                break;
        }
    }

}
