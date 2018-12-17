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

/**
 * Class to Edit User Profile
 */
public class activity_form_edit extends AppCompatActivity {
    private User user = new User();
    EditText etNom, etCognom, etAdresa, etCodiPostal, etEmail, etCiutat;
    Button btnEditar, btnCancelar;
    AlertDialog alertDialog;
    String TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit);
        alertDialog = new AlertDialog.Builder(activity_form_edit.this).create();
        user = (User) getIntent().getSerializableExtra("user");
        TOKEN = getIntent().getStringExtra("token");
        btnEditar = findViewById(R.id.button_editar);
        btnCancelar = findViewById(R.id.button_cancelar);

        etNom = findViewById(R.id.editText_nom);
        etCognom = findViewById(R.id.editText_cognom);
        etAdresa = findViewById(R.id.editText_adresa);
        etCodiPostal= findViewById(R.id.editText_codiPostal);
        etEmail = findViewById(R.id.editText_email);
        etCiutat= findViewById(R.id.editText_ciutat);

        EditText[] arrayEditText = {etNom, etCognom, etAdresa, etCodiPostal, etEmail, etCiutat};

        etNom.setText(user.getName());
        etCognom.setText(user.getSurname());
        etAdresa.setText(user.getAddress());
        etCodiPostal.setText(user.getPostcode());
        etEmail.setText(user.getEmail());
        etCiutat.setText(user.getCity());

        for (int i = 0; i< arrayEditText.length; i++)
        {
            arrayEditText[i].setEnabled(false);
        }


        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnEditar.getText().toString().equals("Guardar"))
                {
                    //Send new Profile
                    send(v);
                    btnEditar.setText("Editar");
                    etAdresa.setEnabled(false);
                    etCodiPostal.setEnabled(false);
                    etCiutat.setEnabled(false);
                    etNom.setEnabled(false);
                    etCognom.setEnabled(false);
                }
                if(btnEditar.getText().toString().equals("Editar"))
                {
                    etAdresa.setEnabled(true);
                    etCodiPostal.setEnabled(true);
                    etCiutat.setEnabled(true);
                    etNom.setEnabled(true);
                    etCognom.setEnabled(true);
                    btnEditar.setText("Guardar");
                }

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
     * Auxiliar method with HTTP objects
     * @param myUrl URL to call
     * @return String Response of server
     * @throws IOException
     * @throws JSONException
     */
    private String httpPost(String myUrl) throws IOException, JSONException {

        JSONObject jsonObject = buidJsonObject();
        String jsonUTF = convertTo8UTF(jsonObject.toString());
        myUrl = myUrl + "&token="+ TOKEN + "&user=" + jsonUTF;
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
        jsonObject.accumulate("email", etEmail.getText().toString());
        jsonObject.accumulate("name", etNom.getText().toString());
        jsonObject.accumulate("surname", etCognom.getText().toString());
        jsonObject.accumulate("address", etAdresa.getText().toString());
        jsonObject.accumulate("postcode", etCodiPostal.getText().toString());
        jsonObject.accumulate("city", etCiutat.getText().toString());
        jsonObject.accumulate("password", "nothing");

        return jsonObject;
    }
    /**
     * AsyncTask call server and manage REsult
     */
    private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        public ProgressDialog loginDialog = new ProgressDialog( activity_form_edit.this );

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
            alertDialog.setTitle("Resultat de l'actualització");
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
        if( UtilsApp.checkNetworkConnection((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)))
            new HTTPAsyncTask().execute(getString(R.string.editUser ));
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
