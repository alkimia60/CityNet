package com.example.sergiogarrido.citynet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Class to create user from App in login view
 */
public class activity_formAlta extends AppCompatActivity {

    AlertDialog alertDialog;

    EditText etNom, etCognom, etAdresa, etCodiPostal, etEmail, etPassword, etCiutat;
    Button btnEnviar, btnCancelar;
    //String myUrl = "http://ec2-35-180-7-53.eu-west-3.compute.amazonaws.com:8080/citynet/UserManager?action=UserRegister&user=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alertDialog = new AlertDialog.Builder(activity_formAlta.this).create();
        setContentView(R.layout.activity_form_alta);


        btnEnviar = findViewById(R.id.button_enviar);
        btnCancelar = findViewById(R.id.button_cancelar);

        etNom = findViewById(R.id.editText_nom);
        etCognom = findViewById(R.id.editText_cognom);
        etAdresa = findViewById(R.id.editText_adresa);
        etCodiPostal= findViewById(R.id.editText_codiPostal);
        etEmail = findViewById(R.id.editText_email);
        etPassword=  findViewById(R.id.editText_password);
        etCiutat= findViewById(R.id.editText_ciutat);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    send(v);
            }
        });
        // Definimos el listener que ejecutará el método onClick del botón cancelar.
        btnCancelar.setOnClickListener(new View.OnClickListener() {
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
     * Auxiliar method with HTTP objects
     * @param myUrl URL to call
     * @return String Response of server
     * @throws IOException
     * @throws JSONException
     */
    private String httpPost(String myUrl) throws IOException, JSONException {

        JSONObject jsonObject = buidJsonObject();
        String jsonUTF = convertTo8UTF(jsonObject.toString());
        myUrl = myUrl + jsonUTF;
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
     * To Make Json object from editTexts
     * @return JsonObject
     * @throws JSONException
     */
    private JSONObject buidJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("email", etEmail.getText().toString());
        jsonObject.accumulate("name", etNom.getText().toString());
        jsonObject.accumulate("surname", etCognom.getText().toString());
        jsonObject.accumulate("address", etAdresa.getText().toString());
        jsonObject.accumulate("postcode", etCodiPostal.getText().toString());
        jsonObject.accumulate("city", etCiutat.getText().toString());
        jsonObject.accumulate("password", etPassword.getText().toString());

        return jsonObject;
    }
    /**
     * AsyncTask call server and manage REsult
     */
    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        public ProgressDialog loginDialog = new ProgressDialog( activity_formAlta.this );

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
            alertDialog.setTitle("No s'ha pogut registrar l'usuari");
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


    public void send(View view) {
        // perform HTTP POST request
        if(checkNetworkConnection())
            new HTTPAsyncTask().execute(getString(R.string.addUserURL));
        else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();

    }
    private String convertTo8UTF(String json) {
        String temp;
        temp = json.replace("{", "%7B");
        temp =  temp.replace("}", "%7D");
        temp =  temp.replace("\"", "%22");
        return temp;
    }

}
