package com.goldenapps.startshopping.ui.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.goldenapps.startshopping.R;

public class SplashScreen extends AppCompatActivity {

    private ProgressBar progressBar;
    private int i;
    private boolean progress=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.splash_screen);

        i = 0;
        progressBar = findViewById(R.id.progressBar);
        progressBarHorizontal();
    }

    private void progressBarHorizontal(){
        if(!progress) {
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (i <= 100) {
                        progressBar.setProgress(i);
                        try {
                            Thread.sleep(10);//60
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (i == 100) {
                            Intent main = new Intent(SplashScreen.this, MenuActivity.class);
                            startActivity(main);
                            finish();
                        }
                        i++;
                        progress=true;
                    }
                }
            });
            hilo.start();
        }
    }
}