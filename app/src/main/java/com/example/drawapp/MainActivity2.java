package com.example.drawapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    private CanvasViewServer canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        canvasView = (CanvasViewServer)findViewById(R.id.canvas);
    }

    public void clearCanvas(View v){
        canvasView.clearCanvas();
    }

    public void updateCanvas(String data){
        runOnUiThread (new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
