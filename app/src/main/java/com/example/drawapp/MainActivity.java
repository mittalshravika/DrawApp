package com.example.drawapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CanvasViewClient canvasView;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = (CanvasViewClient)findViewById(R.id.canvas);
        client = new Client();
    }

    public void clearCanvas(View v){
        canvasView.clearCanvas();

    }
}