package com.example.baiyongshu.httpsclient;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
        //Https_async https =  new Https_async(this,tv,5);
        //https.execute();

        Intent intent = new Intent(this, downloadService.class);
        startService(intent);
        System.out.println("After Start Service");
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

    public void test_elaspedTime5(View view){
        Https_async https =  new Https_async(this,5);
        https.execute();
        //System.out.println("Elapsed Time: " + String.valueOf(https.elapsedTime));

    }
    public void test_elaspedTime10(View view){
        Https_async https =  new Https_async(this,10);
        https.execute();
        //System.out.println("Elapsed Time: " + String.valueOf(https.elapsedTime));

    }
    public void test_elaspedTime50(View view){
        Https_async https =  new Https_async(this,50);
        https.execute();
        //System.out.println("Elapsed Time: " + String.valueOf(https.elapsedTime));

    }
    public void test_elaspedTime100(View view){
        Https_async https =  new Https_async(this,100);
        https.execute();
        //System.out.println("Elapsed Time: " + String.valueOf(https.elapsedTime));

    }
    public void test_elaspedTime500(View view){
        Https_async https =  new Https_async(this,500);
        https.execute();
        //System.out.println("Elapsed Time: " + String.valueOf(https.elapsedTime));

    }
    public void test_elaspedTime1000(View view){
        Https_async https =  new Https_async(this,1000);
        https.execute();
        //System.out.println("Elapsed Time: " + String.valueOf(https.elapsedTime));

    }
    public void test_elaspedTime3000(View view){
        Https_async https =  new Https_async(this,3000);
        https.execute();
        //System.out.println("Elapsed Time: " + String.valueOf(https.elapsedTime));

    }
    public void test_elaspedTime5000(View view){
        Https_async https =  new Https_async(this,5000);
        https.execute();
        //System.out.println("Elapsed Time: " + String.valueOf(https.elapsedTime));

    }
}
