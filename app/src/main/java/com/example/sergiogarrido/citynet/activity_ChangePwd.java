package com.example.sergiogarrido.citynet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class activity_ChangePwd extends AppCompatActivity {

    EditText etOldPwd, etNewPwd, etNewPwdRepeated;
    Button btnSend, btnCancel;
    AlertDialog alertDialog;
    String TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__change_pwd);

        alertDialog = new AlertDialog.Builder(activity_ChangePwd.this).create();
        TOKEN = getIntent().getStringExtra("token");
        btnSend = findViewById(R.id.button_change_pwd);
        btnCancel = findViewById(R.id.button_cancelar);

        etOldPwd = findViewById(R.id.editText_pwd_old);
        etNewPwd = findViewById(R.id.editText_new_pwd);
        etNewPwdRepeated = findViewById(R.id.editTex_new_pwd_repeat);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send(v);
            }
        });

        // Definimos el listener que ejecutará el método onClick del botón cancelar.
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si se pulsa el botón, establecemos el resultado como cancelado.
                // Al igual que con "RESULT_OK", esta variable es de la activity.
                setResult(RESULT_CANCELED);
                // Finalizamos la Activity para volver a la anterior
                finish();
            }
        });
    }

    public void send(View view) {
        // perform HTTP POST request
        if( UtilsApp.checkNetworkConnection((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)))
            new activity_ChangePwd.HTTPAsyncTask().execute(getString(R.string.ChangePwd ));
        else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();
    }

    /**
     * AsyncTask call server and manage REsult
     */
    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        public ProgressDialog loginDialog = new ProgressDialog( activity_ChangePwd.this );

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
            loginDialog.dismiss();

            System.out.println("result is " + result);
            alertDialog.setTitle("Resultat del canvi de pwd");
            //alertDialog.setIcon(R.drawable.success);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setMessage(result);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            setResult(RESULT_OK, getIntent());
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

        JSONObject jsonObject = buidJsonObject();
       // String jsonUTF = UtilsApp.convertTo8UTF(jsonObject.toString());
        myUrl = myUrl + "&token="+ TOKEN + "&oldPassword=" + etOldPwd.getText().toString() + "&newPassword=" + etNewPwd.getText().toString();
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
        System.out.println(response.toString());
        //Read JSON response and print
        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println(myResponse);
        // 5. return response message
        return response.toString();
    }

    /**
     * Method to take Json String From editTexts
     * @return
     * @throws JSONException
     */
    private JSONObject buidJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("oldPassword", etOldPwd.getText().toString());
        jsonObject.accumulate("newPassword", etNewPwd.getText().toString());
        return jsonObject;
    }

}
