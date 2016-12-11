package com.example.baiyongshu.httpsclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;



public class MainActivity extends ActionBarActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("OnCreate");
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.myTextView);
        tv.setMovementMethod(new ScrollingMovementMethod());
        /*
        Https_async https =  new Https_async(this,5);
        https.execute();

        Intent intent = new Intent(this, downloadService.class);
        startService(intent);
        System.out.println("After Start Service");
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void httpdownload_test(View view){
        Https_async https =  new Https_async(this,TestTypeEnum.HTTPDOWNLOAD);
        https.execute();
        //System.out.println("Elapsed Time: " + String.valueOf(https.elapsedTime));

    }
    public void httpupload_test(View view){
        Https_async https =  new Https_async(this,TestTypeEnum.HTTPUPLOAD);
        https.execute();
        //System.out.println("Elapsed Time: " + String.valueOf(https.elapsedTime));

    }

    public void httpsdownload_test(View view){
        Https_async https =  new Https_async(this,TestTypeEnum.HTTPSDOWNLOAD);
        https.execute();
        //System.out.println("Elapsed Time: " + String.valueOf(https.elapsedTime));

    }
    public void httpsupload_test(View view){
        Https_async https =  new Https_async(this,TestTypeEnum.HTTPSUPLOAD);
        https.execute();
        //System.out.println("Elapsed Time: " + String.valueOf(https.elapsedTime));

    }

}



