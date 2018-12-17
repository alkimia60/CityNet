package com.example.sergiogarrido.citynet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Main Class with top Menu and main content of app
 */
public class MainActivity extends AppCompatActivity {

    private final static int EDITUSER = 1;
    private final static int CHANGEPWD = 2;
    private final static int CONTAINERMANAGEMENT = 3;

    private String TOKEN;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // receiving our object
        TOKEN =  getIntent().getStringExtra("token");
        ImageView containersImg = (ImageView)findViewById(R.id.containerSection);
        alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        containersImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllContainers(TOKEN);
                Intent mapsActivity = new Intent(MainActivity.this, Container_Management_Activity.class);
                mapsActivity.putExtra("token", TOKEN);
                startActivityForResult(mapsActivity , CONTAINERMANAGEMENT);
                //Toast.makeText(MainActivity.this, "You clicked on ImageView", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getAllContainers(String token) {
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Method to manage Clicks in Menu
     * @param item element in menu
     * @return option user
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        switch (item.getItemId()) {
            case R.id.Menu:
                //Toast.makeText(context, "Go to profile!", duration).show();
                getUserProfile(TOKEN);
                return true;
            case R.id.changePwd:
               // Toast.makeText(context, "Go to Change Pwd!", duration).show();
                Intent inChangePwd = new Intent(MainActivity.this, activity_ChangePwd.class);
                inChangePwd.putExtra("token", TOKEN);
                startActivityForResult(inChangePwd , CHANGEPWD);
                return true;
            case R.id.Ajuda:
                finish();
                System.exit(0);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Method Async to get User Profile from server before check internet connection
     * @param Token String needed to call server
     */
    public void getUserProfile(String Token) {
        // perform HTTP POST request
        if(checkNetworkConnection())
            new HTTPAsyncTask().execute(getString(R.string.getProfileUrl) + "&token=" + Token );
        else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();

    }

    /**
     * Check if user has Internet
     * @return boolean if has internet
     */
    public boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            // show "Connected" & type of network "WIFI or MOBILE"
            Toast.makeText(this, "Connected "+networkInfo.getTypeName(), Toast.LENGTH_SHORT).show();
        } else {
            // show "Not Connected"
            Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
        }

        return isConnected;
    }

    /**
     * AsyncTask call server and manage REsult
     */
    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        public ProgressDialog loginDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return httpPost(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            String error = JsonUtils.findJsonValue(result, "error");
            if (("No json data".equals(error))) {
                Gson gson = new Gson();
                User user = gson.fromJson(result, User.class);
                Intent in = new Intent(MainActivity.this, activity_form_edit.class);
                in.putExtra("user", user);
                in.putExtra("token", TOKEN);
                startActivityForResult(in , EDITUSER);

            }else
            {
                loginDialog.dismiss();
                System.out.println("result is " + result);
                alertDialog.setTitle(getString(R.string.msg_login));
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setMessage(result);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                alertDialog.show();
            }
        }

        /**
         * Auxiliar method with HTTP objects
         * @param myUrl URL to call
         * @return String Response of server
         * @throws IOException
         * @throws JSONException
         */
        private String httpPost(String myUrl) throws IOException, JSONException {

            System.out.println(myUrl);
            URL url = new URL(myUrl);

            // 1. create HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.connect();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print in String
           // System.out.println(response.toString());
            //Read JSON response and print
            JSONObject myResponse = new JSONObject(response.toString());
            System.out.println(myResponse);
            // 5. return response message
            return response.toString();
        }

    }

}
