package com.example.sergiogarrido.citynet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Container_Management_Activity extends AppCompatActivity {

    private final static int MAPSACTIVITY = 6;
    private String TOKEN;
    AlertDialog alertDialog;
    Button btnEnviar, btnCancelar, btnSelecciona;
    JSONArray containers = null;
    EditText etId, etLatitud, etLongitud, etTipus, etOperatiu;
    Spinner spIncident;
    String type, id;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == MAPSACTIVITY){
            if (resultCode == RESULT_OK){
                Toast.makeText(this, "HAS CLICADO EN UN MARCADOR " + data.getExtras().getString("marker"), Toast.LENGTH_LONG)
                        .show();
                for (int i = 0 ; i< containers.length(); i++)
                {
                    try {
                        if(containers.getJSONObject(i).get("id").equals(data.getExtras().getString("marker"))){
                            System.out.println(containers.getJSONObject(i));
                            id = containers.getJSONObject(i).getString("id");
                            type = containers.getJSONObject(i).get("type").toString();
                            etId.setText(containers.getJSONObject(i).getString("id"));
                            etLatitud.setText(containers.getJSONObject(i).get("latitude").toString());
                            etLongitud.setText(containers.getJSONObject(i).get("longitude").toString());
                            etTipus.setText(containers.getJSONObject(i).get("type").toString());
                            etOperatiu.setText(containers.getJSONObject(i).get("operative").toString());


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container__management_);
        TOKEN =  getIntent().getStringExtra("token");
        btnSelecciona = findViewById(R.id.button_selecciona);
        btnEnviar = findViewById(R.id.button_enviar);
        btnCancelar = findViewById(R.id.button_cancelar);

        Spinner spIncident = (Spinner) findViewById(R.id.Incident_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Incidents_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spIncident.setAdapter(adapter);

        etId = findViewById(R.id.idContainerET);
        etLatitud = findViewById(R.id.latitudET);
        etLongitud = findViewById(R.id.longitudET);
        etTipus= findViewById(R.id.typeET);
        etOperatiu = findViewById(R.id.OperativeET);

        btnSelecciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("selecciona");
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
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("notifica");
            }
        });
    }

    public void send(String chooise) {
        // perform HTTP POST request
        if( UtilsApp.checkNetworkConnection((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)))

            if(chooise.equals("selecciona"))
            {
                new Container_Management_Activity.HTTPAsyncTask().execute(getString(R.string.containerUrl), "selecciona");
            }
            else if (chooise.equals("notifica"))
            {
                new Container_Management_Activity.HTTPAsyncTask().execute(getString(R.string.crearIncident), "notifica");
            }

        else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();
    }

    /**
     * AsyncTask call server and manage REsult
     */
    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        public ProgressDialog loginDialog = new ProgressDialog(Container_Management_Activity.this);

        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    return httpPost(urls[0], urls[1]);
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
                Intent in = new Intent(Container_Management_Activity.this, MapsContainerActivity.class);

                try {
                    containers = putMarkersonExtra(in, result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                in.putExtra("containers", containers.toString());
                in.putExtra("token", TOKEN);
                startActivityForResult(in, MAPSACTIVITY);

            }else
            {
                loginDialog.dismiss();
                System.out.println("result is " + result);

            }
        }

        private JSONArray putMarkersonExtra(Intent in, String result) throws JSONException {

            JSONObject obj = new JSONObject(result);
             JSONArray containers =  obj.getJSONArray("containers");
            System.out.println("Number of containers " + containers.length());
            return containers;

        }

        /**
         * Auxiliar method with HTTP objects
         * @param myUrl URL to call
         * @param cridaWs tipus de crida al WS del server
         * @return String Response of server
         * @throws IOException
         * @throws JSONException
         */
        private String httpPost(String myUrl, String cridaWs) throws IOException, JSONException {

            if(cridaWs.equals("selecciona"))
            {
                System.out.println(myUrl);
                myUrl = myUrl + "&token="+ TOKEN + "&screen=0&filterField=null&filterValue=null&step=0";
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
            else {
                System.out.println(myUrl);
                myUrl = myUrl + "&token=" + TOKEN + "&incident=%7B%22id%22:%220%22,%22container%22:%22"+ id + "%22,%22type%22:%22full%22%7D";
                URL url = new URL(myUrl);
                System.out.println(url);
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

}
