package com.example.drawapp;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Server extends AsyncTask<Void, Void, CanvasObject> {

    public static String SERVER_IP = "192.168.164.156";
    public static final int SERVER_PORT = 8080;
    ServerSocket serverSocket;

    public Server(){
        Thread1 t = new Thread1();
        t.run();
    }

    @Override
    protected CanvasObject doInBackground(Void... voids) {
        Socket socket;
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            try {
                socket = serverSocket.accept();
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                Log.d("SOCKET", "Connected");
                while(!Thread.currentThread().isInterrupted())
                {
                    final CanvasObject data;
                    try {
                        data = (CanvasObject) input.readObject();
                        if (data != null) {
                            Log.d("SOCKET",data.x + " " + data.y + " " + data.flag);
                        }
                        else{
                            Log.d("SOCKET","Inelse");
                        }
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

        return null;
    }

    private ObjectOutputStream output;
    private ObjectInputStream input;
    class Thread1 implements Runnable {
        @Override
        public void run() {
            Socket socket;
            try {
                serverSocket = new ServerSocket(SERVER_PORT);
                try {
                    socket = serverSocket.accept();
                    output = new ObjectOutputStream(socket.getOutputStream());
                    input = new ObjectInputStream(socket.getInputStream());
                    Log.d("SOCKET", "Connected");
                    while(!Thread.currentThread().isInterrupted())
                    {
                        final CanvasObject data;
                        try {
                            data = (CanvasObject) input.readObject();
                            if (data != null) {
                                Log.d("SOCKET",data.x + " " + data.y + " " + data.flag);
                            }
                            else{
                                Log.d("SOCKET","Inelse");
                            }
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
        }
    }
}
