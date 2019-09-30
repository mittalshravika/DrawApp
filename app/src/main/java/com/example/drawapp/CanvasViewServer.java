package com.example.drawapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// MainActivity2.java

public class CanvasViewServer extends View {

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    Context context;
    public static String SERVER_IP = "192.168.164.156";
    public static final int SERVER_PORT = 8080;
    ServerSocket serverSocket;

    public CanvasViewServer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        mPath = new Path();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);

        new AsynchServer().execute();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
    }

    private void StartTouch(float x, float y){
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void moveTouch(float x, float y){
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if(dx >= TOLERANCE || dy >= TOLERANCE){
            mPath.quadTo(mX, mY, (x+mX) / 2, (y+mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas(){
        mPath.reset();
        invalidate();
    }

    private void upTouch(){
        mPath.lineTo(mX, mY);
    }

    private ObjectOutputStream output;
    private ObjectInputStream input;
    class AsynchServer extends AsyncTask<Void, Void, CanvasObject> {

        @Override
        protected CanvasObject doInBackground(Void... voids) {
            Socket socket;
            CanvasObject data= new CanvasObject(0,0,0);
            try {
                serverSocket = new ServerSocket(SERVER_PORT);
                Log.d("SOCKET", "socket created");
                try {
                    socket = serverSocket.accept();
                    output = new ObjectOutputStream(socket.getOutputStream());
                    input = new ObjectInputStream(socket.getInputStream());
                    Log.d("SOCKET", "Connected");
                    while(!Thread.currentThread().isInterrupted())
                    {
                        try {
                            data = (CanvasObject) input.readObject();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(CanvasObject data) {
            super.onPostExecute(data);
            try {
                if (data != null) {
                    Log.d("SOCKET",data.x + " " + data.y + " " + data.flag);
                    float x = data.x;
                    float y = data.y;

                    switch (data.flag){

                        case -1:
                            StartTouch(x, y);
                            invalidate();
                            break;
                        case 0:
                            moveTouch(x, y);
                            invalidate();
                            break;
                        case 1:
                            upTouch();
                            invalidate();
                            break;
                    }
                }
                else{
                    Log.d("SOCKET","In else");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        float x = event.getX();
//        float y = event.getY();
//
//        switch (event.getAction()){
//
//            case MotionEvent.ACTION_DOWN:
//                StartTouch(x, y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                moveTouch(x, y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//                upTouch();
//                invalidate();
//                break;
//        }
//        return true;
//    }
}
