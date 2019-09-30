package com.example.drawapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

public class Client extends AsyncTask<CanvasObject, Void, CanvasObject> {
    static String SERVER_IP = "192.168.164.156";
    static int SERVER_PORT = 8080;

    static private ObjectOutputStream output;
    static private ObjectInputStream input;
//    static class Thread1 implements Runnable {
//        public void run() {
//            Socket socket;
//            try {
//                socket = new Socket(SERVER_IP, SERVER_PORT);
//                output = new ObjectOutputStream(socket.getOutputStream());
//                input = new ObjectInputStream(socket.getInputStream());
//                Log.d("SOCKET", "Connected");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    static class Thread3 implements Runnable {
        private CanvasObject data;
        Thread3(CanvasObject data) {
            this.data = new CanvasObject(data.x, data.y, data.flag);
        }
        @Override
        public void run() {
            try {
                output.writeObject(data);
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("SOCKET", data.x + " " + data.y + " " + data.flag);
        }
    }

    public static void sendObject(CanvasObject data){
        Log.d("SOCKET", "sendObject");
        Thread3 t = new Thread3(data);
        t.run();
    }

    public Client(){
    }

    @Override
    protected CanvasObject doInBackground(CanvasObject... canvasObjects) {
        Log.d("SOCKET", "I am in execute");
        Socket socket;
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            Log.d("SOCKET", "Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CanvasObject data = canvasObjects[0];
        try {
            output.writeObject(data);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("SOCKET", data.x + " " + data.y + " " + data.flag);
        return data;
    }

//    class Thread2 implements Runnable {
//        @Override
//        public void run() {
//            Log.d("SOCKET", "entered");
//            while (true) {
//                try {
//                    final Point message = (Point)input.readObject();
//                    if (message != null) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                tvMessages.append("server: " + message.x + " " + message.y + "\n");
//                            }
//                        });
//                    } else {
//                        Thread1 = new Thread(new Thread1());
//                        Thread1.start();
//                        return;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

}