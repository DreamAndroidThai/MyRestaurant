package com.restaurant.saran.myrestaurant;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    //Explicit
    private UserTABLE objUserTABLE;
    private FoodTABLE objFoodTABLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create and connected database
        createAndConnectedDatabase();

        //tester Add Value
        //testerAddValue();

        //deleteAllData
        deleteAllData();

        //Synchronize Json to SQLite
        synJSONtoSQLite();


    }   //onCreate

    private void synJSONtoSQLite() {

        //Change policy
        StrictMode.ThreadPolicy objThreadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(objThreadPolicy);

        int intTimes = 0;
        while (intTimes <=1) {

            InputStream objInputStream = null;
            String strJSON = null;
            String strUserURL = "http://swiftcodingthai.com/3sep/php_get_data_saran.php";
            String strFoodURL = "http://swiftcodingthai.com/3sep/php_get_data_food.php";
            HttpPost objHttpPost = null;

            //1. Create Input Stream
            try {

                HttpClient objHttpClient = new DefaultHttpClient();

                if (intTimes != 1) {

                    objHttpPost = new HttpPost(strUserURL);

                } else {

                    objHttpPost = new HttpPost(strFoodURL);

                }

                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();

            } catch (Exception e) {
                Log.d("Rest", "Input ==> " + e.toString());
            }

            //2.Create StrJSON

            //3.Update to SQLite


            intTimes += 1;
        }   //while


    }   //synJSONtoSQLite

    private void deleteAllData() {

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("Restaurant.db", MODE_PRIVATE, null);
        objSqLiteDatabase.delete("userTABLE", null, null);
        objSqLiteDatabase.delete("foodTABLE", null, null);

    }   //deleteAllData

    private void testerAddValue() {
        objUserTABLE.addNewUser("testUser", "1234", "name");
        objFoodTABLE.addNewFood("ข้าวหน้าเป็ด", "3", "50");
    }   //test

    private void createAndConnectedDatabase() {
        objUserTABLE = new UserTABLE(this);
        objFoodTABLE = new FoodTABLE(this);
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
}   //main class
