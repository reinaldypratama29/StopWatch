package com.hfad.stopwatch;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;


public class StopwatchActivity extends Activity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRun;//ketika running
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if(savedInstanceState != null){     //ketika
            seconds = savedInstanceState.getInt("seconds"); //Restore activity state by getting values
            running = savedInstanceState.getBoolean("running");
            wasRun = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) { //menyimpan data pada saat running jadi
        savedInstanceState.putInt("seconds", seconds);           //jadi ketika sedang berjalan terus user ada mengubah layar
        savedInstanceState.putBoolean("running", running);       //dari verital ke horizontal data counter tetap aman
        savedInstanceState.putBoolean("wasRunning",wasRun);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRun = running;
        running = false;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if(wasRun){
            running=true;
        }
    }
     // @Override
   // protected void onStop() {
     //   super.onStop();
       // wasRun = running;
       // running = false;
    //}
     //@Override
    //protected void onStart() {
      //  super.onStart();
        //if(wasRun){
          //  running=true;
        //}
    //}
        //@Override
    //protected void onDestroy(){
      //  super.onDestroy();
       // finish();
    //}
    //Start the stopwatch running when the Start button is clicked.
    public void onClickStart(View view) {
        running = true;
    }
    //Stop the stopwatch running when the Stop button is clicked.
    public void onClickStop(View view) {
        running = false;
    }
    //Reset the stopwatch when the Reset button is clicked.
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }


    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }
}
