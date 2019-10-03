package com.example.drawapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CanvasViewClient canvasView;
    private Client client;
    private Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = (CanvasViewClient)findViewById(R.id.canvas);
        client = new Client();
        but = (Button)findViewById(R.id.button2);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Client().execute(canvasView.pl);
            }
        });
    }

    public void clearCanvas(View v){
        canvasView.clearCanvas();
        canvasView.pl = "";
    }
}