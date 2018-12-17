package com.example.sergiogarrido.citynet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Login Activiy Class
 */
public class LoginActivity extends AppCompatActivity {
    private final static int NEWUSER = 0;
    AlertDialog alertDialog;
    EditText etEmail, etPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.buttonLogin);
        Button btnNouUsuari = findViewById(R.id.buttonNouUsuari);

        etEmail = findViewById(R.id.editText_email_login);
        etPwd=  findViewById(R.id.editText_password_login);

        alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        //Listener of button login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etEmail.getText().toString().equals("") & !etPwd.getText().toString().equals(""))
                {
                    send(etEmail.getText().toString(), etPwd.getText().toString());
                }else {
                        Toast.makeText(LoginActivity.this, "Has d'omplir les dades", Toast.LENGTH_LONG).show();
                    }

            }
        });
        //Listener button New User
        btnNouUsuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, activity_formAlta.class), NEWUSER);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_CANCELED".
        if (resultCode == RESULT_CANCELED) {
            // Si es así mostramos mensaje de cancelado por pantalla.
            Toast.makeText(this, "Resultado cancelado", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     *
     * @param user Json User to login in server
     * @param pwd String Password
     */
    public void send(String user, String pwd) {
        // perform HTTP POST request
        if(checkNetworkConnection())
           // new HTTPAsyncTask().execute(getString(R.string.loginUrl) + "&user=" + user + "&password="+pwd);
            new HTTPAsyncTask().execute(getString(R.string.loginUrlhtpps) + "&user=" + user + "&password="+pwd);
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
        public ProgressDialog loginDialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                try {
                    //do in background the call waiting the response server
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
            //If hasn't error get token an rol and start Main Activity
            if (("No json data".equals(error))
                    && (!"No json data".equals(JsonUtils.findJsonValue(result, "token")))) {
                String TOKEN = JsonUtils.findJsonValue(result, "token");
                String ROL = JsonUtils.findJsonValue(result, "rol");
                Intent in = new Intent(LoginActivity.this, MainActivity.class);
                in.putExtra("userRol", ROL);
                in.putExtra("token", TOKEN);
                startActivity(in);

            } else {
                loginDialog.dismiss();

                System.out.println("result is " + result);
                alertDialog.setTitle(getString(R.string.msg_login));
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
         *
         * @param myUrl URL to call
         * @return String Response of server
         * @throws IOException
         * @throws JSONException
         */
        private String httpPost(String myUrl) throws IOException, JSONException {

            System.out.println(myUrl);
            URL url;
            BufferedReader br = null;
            String toBeReturned="";

            try {
                url = new URL(myUrl);
                HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                    @Override

                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public X509Certificate[] getAcceptedIssuers() {
                                X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                                return myTrustedAnchors;
                            }

                            @Override
                            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                            }
                        }
                };

                // Create an SSLContext that uses our TrustManager
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, null);


                // 1. create HttpURLConnection
                //HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                conn.setSSLSocketFactory(sc.getSocketFactory());
                conn.setHostnameVerifier(hostnameVerifier);

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
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
