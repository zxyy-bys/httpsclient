package com.example.baiyongshu.httpsclient;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;


import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
///import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;

import java.io.EOFException;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;

import java.security.KeyStore;

import java.text.SimpleDateFormat;
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
    /*
    public Https_async(){

    }
    */

    public Https_async(Context context,TextView tv){
        this.tv = tv;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
/*
        try {

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
// From https://www.washington.edu/itconnect/security/ca/load-der.crt
            File root = android.os.Environment.getExternalStorageDirectory();
            InputStream caInput = new BufferedInputStream(new FileInputStream(root + "/server1.crt"));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

// Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

// Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            System.out.println(tmfAlgorithm);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

// Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, tmf.getTrustManagers(), null);

            long startTime = System.currentTimeMillis();
            URL url = new URL("https://192.168.1.101");

            //URLConnection urlConnection = url.openConnection();
            HttpsURLConnection urlConnection =
                    (HttpsURLConnection)url.openConnection();
            urlConnection.setSSLSocketFactory(context.getSocketFactory());
            InputStream in = urlConnection.getInputStream();

            System.out.println("TRY");
            //copyInputStreamToOutputStream(in, System.out);

            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            this.total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                this.total.append(line);
            }
            long stopTime = System.currentTimeMillis();
            elapsedTime = stopTime - startTime;
            System.out.println("Execution Time: " + elapsedTime);

             System.out.println(total);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Exception");
        }
*/
        //myDefaultHttpClient client = new myDefaultHttpClient();
        //client.newFunction();
       //HttpClient httpClient = org.apache.http.impl.client.HttpClients.createMinimal();
        //HttpClient httpClient = new DefaultHttpClient();

        /*
        HttpHost proxy = new HttpHost("127.0.0.1", 8888);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setRoutePlanner(routePlanner)
                .build();
        */

        /*
        CloseableHttpClient httpclient = new DefaultHttpClient();
        try {
            URI uri;
            try {
                uri = new URI("https://192.168.1.107");
            } catch (URISyntaxException e1) {
                System.out.println("error");
                return false;
            }
            HttpHost target = new HttpHost(uri.getHost(), 443, uri.getScheme());
            HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");

            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            //HttpGet request = new HttpGet("/");
            HttpPost request = new HttpPost(uri.getPath());
            request.setConfig(config);

            System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);

            CloseableHttpResponse response = httpclient.execute(target, request);

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                //System.out.println(EntityUtils.toString(response.getEntity()));

        }catch(IOException e){

    }finally {
         //   httpclient.close();
        }
        */
        HttpClient httpClient = getNewHttpClient();

        URI uri;
        try {
            uri = new URI("https://192.168.1.107");
        } catch (URISyntaxException e1) {
            System.out.println("error");
            return false;
        }


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        HttpHost host = new HttpHost(uri.getHost(), 443, uri.getScheme());
        HttpPost httppost = new HttpPost(uri.getPath());

        try {
            long startTime = System.currentTimeMillis();
            System.out.println("start Time: " + formatter.format(startTime));
            HttpResponse response = httpClient.execute(host, httppost);
            if(response == null){
                System.out.println("response null");
                return false;
            }
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            response.getEntity().getContent()
                    )
            );
            long beforeRead = System.currentTimeMillis();
            System.out.println("read Start: " + formatter.format(beforeRead));
            String line = null;
            while ((line = reader.readLine()) != null){
                total += line + "\n";
            }

            long endTime = System.currentTimeMillis();
            System.out.println("end Time: " + formatter.format(endTime));
            this.elapsedTime = endTime - startTime;
            System.out.println(elapsedTime);
           //System.out.println(result);
        } catch (ClientProtocolException e) {
            System.out.println("error1");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("error2");
        }
        return false;

    }

    @Override
    protected void onPostExecute(Boolean result) {
        //tv.append("Elapsed Time: " + String.valueOf(this.elapsedTime) + "\n");
        Intent myWebViewIntent = new Intent(this.context, WebViewActivity.class);
        myWebViewIntent.putExtra("htmlString", this.total);
        context.startActivity(myWebViewIntent);
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
}

