package com.example.deepakrattan.apirequestdemo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deepakrattan.apirequestdemo.R;
import com.example.deepakrattan.apirequestdemo.apiConfiguration.ApiConfiguration;
import com.example.deepakrattan.apirequestdemo.httpRequsetProcessor.HttpRequestProcessor;
import com.example.deepakrattan.apirequestdemo.httpRequsetProcessor.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by deepak.rattan on 12/24/2016.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText edtName, edtPasswd;
    private Button btnLogin, btnRegister;
    private String name, passwd;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlLogin, jsonStringToPost, jsonResponseString;
    private boolean success;
    private String message, address, emailID, phone, password, userName;
    private int userID;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //findViewById

        edtName = (EditText) findViewById(R.id.edtNameLogin);
        edtPasswd = (EditText) findViewById(R.id.edtPswdLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister1);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlLogin = baseURL + "AccountAPI/GetLoginUser";

        progressDialog = new ProgressDialog(this);


        //On clicking login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                //Getting name and password
                name = edtName.getText().toString();
                passwd = edtPasswd.getText().toString();

                new LoginTask().execute(name, passwd);

            }
        });

        //On clicking Register Button move to registration screen

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

    }

    public class LoginTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            name = params[0];
            passwd = params[1];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("UserName", name);
                jsonObject.put("Password", passwd);

                jsonStringToPost = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlLogin);
                jsonResponseString = response.getJsonResponseString();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d("Response String", s);
            progressDialog.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(s);
                String err = jsonObject.getString("ErrorMessage");
                String msg = "User Authenticated!!";
                if (err.equals(msg)) {
                    Toast.makeText(LoginActivity.this, "User is authenticated...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "User is not authenticated...", Toast.LENGTH_SHORT).show();

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
