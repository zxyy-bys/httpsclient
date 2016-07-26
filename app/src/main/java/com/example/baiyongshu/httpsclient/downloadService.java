package com.example.baiyongshu.httpsclient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.text.SimpleDateFormat;

/**
 * Created by zxyy_bys on 5/18/16.
 */
public class downloadService extends Service {
    private int filesize ;
    private static final String TAG = "httpsclientService";
    String total;
    private ServerSocket lserver;
    TextView tv = null;
    @Override
    public void onCreate(){
        super.onCreate();
        System.out.println("Service OnCreate");

        filesize = 5;
        startTask();
    }

    @Override
    public int onStartCommand(Intent intent, int flags,int startid){
        return 0;
    }


    public void startTask(){
        System.out.print("start Task\n");
        SystemClock.sleep(3000);
        new Thread(new Runnable() {
            public void run() {
                try {
                    lserver = new ServerSocket(1234);
                    InetAddress addr = lserver.getInetAddress();
                    int port =  lserver.getLocalPort();
                    while(true){
                        System.out.println("accpet" + 1);
                        Socket client = lserver.accept();
                        System.out.println("after accept");
                        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        String input = reader.readLine();

                        System.out.println("input: " + input);
                        int file_size = 0;
                        if(!input.isEmpty()){
                            file_size = Integer.valueOf(input);
                            System.out.println(file_size);
                            startDownload(client,file_size);
                        }

                        OutputStream out = client.getOutputStream();
                        PrintStream pout = new PrintStream(out, true);
                        pout.write(("Download Finish123").getBytes());
                        System.out.println("Download Finish");
                        client.close();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
       // stopSelf();
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG,"Service Destroy");
    }


    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    public void startDownload(Socket client,int filesize){
        System.out.println("startdownload");
        Https_async https =  new Https_async(this,client,filesize);
        try {
            https.execute().get();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
