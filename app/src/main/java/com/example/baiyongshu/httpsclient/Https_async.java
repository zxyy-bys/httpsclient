package com.example.baiyongshu.httpsclient;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.impl.client.CloseableHttpClient;
///import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.client.HttpClientBuilder;


/**
 * Created by baiyongshu on 9/1/15.
 */

public class Https_async extends AsyncTask<Void, Long, Boolean> {
  //  private Exception exception;
    public long elapsedTime;
    TextView tv;

    Context context;
    String total;
    Socket client;
    int interval;
    int serverResponseCode;
    File mylog;
    final int ROUNDTIME = 10;
    final int times = 6;

    TestTypeEnum type;
    /*
    public Https_async(){

    }
    */

    int fileSize;


    public Https_async(Context context, Socket client,int fileSize){
        this.client = client;
        this.context = context;
        this.fileSize = fileSize;
    }
    public Https_async(Context context, TestTypeEnum type){

        this.context = context;
        this.type = type;
    }
    @Override
    protected Boolean doInBackground(Void... params) {

      //  mylog = new File(Environment.getExternalStorageDirectory().getPath() + "/overhead"+ "/overheadResult.log");

        //for(TestTypeEnum state : TestTypeEnum.values()){
        //     this.type = state;
        //    if(this.type == TestTypeEnum.HTTPDOWNLOAD || this.type == TestTypeEnum.HTTPUPLOAD){
         //       continue;
         //   }
            SystemClock.sleep(1000);
            long curtime = System.currentTimeMillis();
            switch (type) {
                case HTTPDOWNLOAD:
                    mylog = new File(Environment.getExternalStorageDirectory().getPath() + "/overhead/cpu" + "/HttpDownload_" + curtime + ".log");
                    doTask();
                    break;
                case HTTPSDOWNLOAD:
                    mylog = new File(Environment.getExternalStorageDirectory().getPath() + "/overhead/cpu" + "/HttpsDownload_" + curtime + ".log");
                    doTask();
                    break;
                case HTTPUPLOAD:
                    mylog = new File(Environment.getExternalStorageDirectory().getPath() + "/overhead/cpu" + "/HttpUpload_" + curtime + ".log");
                    uploadFile();
                    break;
                case HTTPSUPLOAD:
                    mylog = new File(Environment.getExternalStorageDirectory().getPath() + "/overhead/cpu" + "/HttpsUpload_" + curtime + ".log");
                    System.out.println("upload file");
                    uploadFile();
                    break;

            }
        //}

    /*\\    mylog = new File(Environment.getExternalStorageDirectory().getPath() + "/overheadResult.log");
        //doTask;
        String filename = Environment.getExternalStorageDirectory().getPath() + "/files/files_10K/file_10K_1";
        uploadFile(filename);
        if(this.client != null) {

        }*/
        return false;
    }

    private void writeToLog(String data){
        try {
            FileOutputStream fout = new FileOutputStream(mylog,true);
            PrintWriter pw = new PrintWriter(fout);
            pw.println(data);
            pw.flush();
            pw.close();
            fout.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void writeToLog(long start, long end, long one_elapsed, long total_elapsed){
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            FileOutputStream fout = new FileOutputStream(mylog,true);

            PrintWriter pw = new PrintWriter(fout);
            //pw.print("Start Time: " + formatter.format(start) );
            //pw.print(" End Time: " + formatter.format(end));
            pw.print(" one_elapsed : " + one_elapsed);
            pw.print(" total_elapsed : " + total_elapsed);
            pw.println();
            pw.flush();
            pw.close();
            fout.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private boolean doTask(){


        double[] avg  = new double[10];

        String http_head = null;
        //String host = "149.125.80.173";
        String host = "10.42.0.111";

        switch(type){
            case HTTPDOWNLOAD:
                http_head = "http";
                break;
            case HTTPSDOWNLOAD:
                http_head = "https";
        }

        writeToLog("------------------------ "+  http_head + "Dowland File start: ----------------------");

        for(int j = 5; j < times; j ++ ) {
            long one_elapsed = 0;
            long total_elapsed = 0;
                SystemClock.sleep(5000);
                for (int i = 0; i < ROUNDTIME; ++i) {
                    //HttpClient httpclient = new DefaultHttpClient();
                    //URi uri = new URI("http://149.125.81.252/files/file_500K_1");
                    // Prepare a request object
                    URL url = null;
                HttpGet httpget = null;
                System.out.println("https_async start" + j);
                HttpURLConnection uconn = null;
                InputStream is = null;

                disableSSLCertificateChecking();
                String remote_file_path = null;
                try {
                    if (j == 0) {
                        remote_file_path = "/files/file_5K_1";
                    }
                    else if (j == 1) {
                        remote_file_path = "/files/file_10K_1";
                    }
                    else if (j == 2) {
                        remote_file_path = "/files/file_50K_1";
                    }
                    else if (j == 3) {
                        remote_file_path = "/files/file_100K_1";
                    }
                    else if (j == 4) {
                        remote_file_path = "/files/file_500K_1";
                    }
                    else if (j == 5) {
                        remote_file_path = "/files/file_1M_1";
                    }
                    else if (j == 6) {
                        remote_file_path = "/files/file_3M_1";
                    }
                    else if (j == 7) {
                        remote_file_path = "/files/file_5M_1";
                    }
                    else
                        System.exit(0);

                    url = new URL(http_head + "://" + host + remote_file_path);
                    //url = new URL("https://www.google.com");

                    SSLContext sslcontext = SSLContext.getInstance("TLSv1");

                    sslcontext.init(null,
                            null,
                            null);
                    SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());

                    HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);

                    writeToLog("==>"+ http_head + " Dowland File: " + remote_file_path);
                  //  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    long startTime = System.currentTimeMillis();
                    //start download file
                    //System.out.println(startTime);
                    uconn =(HttpURLConnection) url.openConnection();
                    is = uconn.getInputStream();
                    BufferedInputStream bufferinstream = new BufferedInputStream(is);
                    byte[] array = new byte[1024];

                    int current;
                    int recv_size = 0;
//                    do {
//                        recv_size = bufferinstream.available();
//                        System.out.println("bufferinstream length" + recv_size);
//                    }while(recv_size < 1024 * 1024 * 1);
                    //while((current = bufferinstream.read(array,0,1024)) != -1){
                        //do nothing
                        //String str = new String(array, "UTF-8");
                        //System.out.println(str);
                    //}
                    int respoceCode = 0;
                    if((respoceCode = uconn.getResponseCode()) != 0){
                        System.out.println("Download Response Code: \n" + respoceCode);
                    }

                    //download file down
                    long endTime = System.currentTimeMillis();
                    //System.out.println("end Time: " + formatter.format(endTime));
                    one_elapsed = endTime - startTime;
                    total_elapsed += one_elapsed;
                    writeToLog(startTime,endTime,one_elapsed,total_elapsed);
                    /* write start time, endTime  elapsed time to log */
                    //System.out.println(elapsed);
//                    System.out.println("result: " + total.length());

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Exception");
                }finally {
                    if(is != null){
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(uconn != null){
//                        uconn.disconnect();
                    }
                }
                SystemClock.sleep(2000);
            }
            //
            writeToLog("Average time: " + total_elapsed / ROUNDTIME);
            //System.out.println("Average time: " + total_elapsed / ROUNDTIME);
            avg[j] = total_elapsed / ROUNDTIME;
        }
        for(int i = 0 ; i < times; ++i){
            writeToLog("" + avg[i]);
          //  System.out.println(i + " Average time: " + avg[i]);
        }
        return false;

    }


    public int uploadFile() {
     //   final int ROUNDTIME = 5;
        double[] avg  = new double[10];
        String http_head = null;
        //String host = "149.125.80.173";
        String host = "10.42.0.111";
        switch(type){
            case HTTPUPLOAD:
                http_head = "http";
                break;
            case HTTPSUPLOAD:
                http_head = "https";
        }
        String fileDir = Environment.getExternalStorageDirectory().getPath() ;

        for(int j = 1; j < times; j ++ ) {
            long one_elapsed = 0;
            long total_elapsed = 0;
            SystemClock.sleep(5000);
            String filename = null;
            for (int i = 0; i < ROUNDTIME; ++i) {
                if (j == 0) {
                    filename = "/files/files_5K/file_5K_" + (i + 1);
                }
                else if (j == 1) {
                    filename = "/files/files_10K/file_10K_" + (i + 1);
                }
                else if (j == 2) {
                    filename = "/files/files_50K/file_50K_" + (i + 1);
                }
                else if (j == 3) {
                    filename = "/files/files_100K/file_100K_"+ (i + 1);
                }
                else if (j == 4) {
                    filename = "/files/files_500K/file_500K_" + (i + 1);
                }
                else if (j == 5) {
                    filename = "/files/files_1M/file_1M_" + (i + 1);
                }
                else if (j == 6) {
                    filename = "/files/files_3M/file_3M_" + (i + 1);
                }
                else if (j == 7) {
                    filename = "/files/files_5M/file_5M_" + i;
                }
                else
                    System.exit(0);


                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
                File sourceFile = new File(fileDir + filename);
                System.out.println(fileDir + filename);
                if (!sourceFile.isFile()) {
                    System.out.println("Not File");
                    return 0;

                } else {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(sourceFile);
                        URL url = new URL(http_head + "://"+host+"/upload.php");
                        disableSSLCertificateChecking();

                        long startTime = System.currentTimeMillis();
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // Allow Inputs
                        conn.setDoOutput(true); // Allow Outputs
                        conn.setUseCaches(false); // Don't use a Cached Copy
                        conn.setRequestMethod("POST");
                        //conn.setRequestProperty("Connection", "Keep-Alive");
                        //  conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                        conn.setRequestProperty("fileToUpload", filename);


                        dos = new DataOutputStream(conn.getOutputStream());

//Adding Parameter media file(audio,video and image)

                        dos.writeBytes(twoHyphens + boundary + lineEnd);

                        dos.writeBytes("Content-Disposition: form-data; name=\"fileToUpload\";filename=\"" + filename + "\"" + lineEnd);
                        dos.writeBytes(lineEnd);
                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];
                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {
                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                        }

                        // send multipart form data necesssary after file data...
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


                        serverResponseCode = conn.getResponseCode();
                        String serverResponseMessage = conn.getResponseMessage();


                        Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                        if (serverResponseCode == 200) {
                            Log.e("Upload file to server", "successfully: ");
                        }

                        // close the streams //
                        fileInputStream.close();
                        dos.flush();
                        long endTime = System.currentTimeMillis();
                        InputStream is = conn.getInputStream();
                        byte[] array = new byte[1024];
                        BufferedInputStream bufferinstream = new BufferedInputStream(is);
                        int current;
                        while((current = bufferinstream.read(array,0,1024)) != -1){
                            //do nothing
                            String str = new String(array, "UTF-8");
                            System.out.println(str);
                        }

                        dos.close();
                        one_elapsed = endTime - startTime;
                        total_elapsed += one_elapsed;
                        writeToLog(startTime,endTime, one_elapsed,total_elapsed);
                    } catch (MalformedURLException ex) {

                        Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                    } catch (final Exception e) {

                        return serverResponseCode;
                    }finally {
                        if(dos != null){
                            try {
                                dos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if(conn != null){
                        //conn.disconnect();
                        }
                    }
                }
                SystemClock.sleep(2000);
            }
            writeToLog("Average time: " + total_elapsed / ROUNDTIME);
            //System.out.println("Average time: " + total_elapsed / ROUNDTIME);
            avg[j] = total_elapsed / ROUNDTIME;
        }
        for(int i = 0 ; i < times; ++i){
            writeToLog("" + avg[i]);
            //  System.out.println(i + " Average time: " + avg[i]);
        }
        return 1;
    }
    @Override
    protected void onPostExecute(Boolean result) {
        //tv.append("Elapsed Time: " + String.valueOf(this.elapsedTime) + "\n");
       // Intent myWebViewIntent = new Intent(this.context, WebViewActivity.class);
        //myWebViewIntent.putExtra("htmlString", this.total);
        //context.startActivity(myWebViewIntent);
    }

    public HttpClient getNewHttpClient() {
        try {

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https",sf,443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient();
        } catch (Exception e) {
            System.out.println("Exception");
            return new DefaultHttpClient();
        }
    }
    private static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                        // not implemented
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                        // not implemented
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                }
        };

        try {

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }

            });
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

