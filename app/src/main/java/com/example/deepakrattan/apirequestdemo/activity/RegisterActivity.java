package com.example.deepakrattan.apirequestdemo.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText edtName, edtAddress, edtEmailID, edtPhone, edtUserName, edtPassword;
    private String name, address, emailID, phone, userName, password;
    private Button btnRegister;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlRegister;
    private String jsonPostString, jsonResponseString;
    private String message;
    private boolean success;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //findViewByID
        edtName = (EditText) findViewById(R.id.edtName);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtEmailID = (EditText) findViewById(R.id.edtEmailID);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlRegister = baseURL + "AccountAPI/SaveApplicationUser";

        //On clicking register Button

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting values
                name = edtName.getText().toString();
                address = edtAddress.getText().toString();
                emailID = edtEmailID.getText().toString();
                phone = edtPhone.getText().toString();
                userName = edtUserName.getText().toString();
                password = edtPassword.getText().toString();
                new RegistrationTask().execute(name, address, emailID, phone, userName, password);
            }
        });
    }

    private class RegistrationTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            name = params[0];
            Log.e("Name", name);
            address = params[1];
            emailID = params[2];
            phone = params[3];
            userName = params[4];
            password = params[5];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name", name);
                jsonObject.put("Address", address);
                jsonObject.put("EmailId", emailID);
                jsonObject.put("Phone", phone);
                jsonObject.put("UserName", userName);
                jsonObject.put("Password", password);

                jsonPostString = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlRegister);
                jsonResponseString = response.getJsonResponseString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");
                Log.d("message", message);

                if (success) {

                    JSONArray responseData = jsonObject.getJSONArray("responseData");
                    for (int i = 0; i < responseData.length(); i++) {
                        JSONObject object = responseData.getJSONObject(i);
                        userID = object.getInt("UserId");
                        Log.d("userId", String.valueOf(userID));
                        name = object.getString("Name");
                        Log.d("name", name);
                        address = object.getString("Address");
                        Log.d("address", address);
                        emailID = object.getString("EmailId");
                        Log.d("emailId", emailID);
                        phone = object.getString("Phone");
                        Log.d("phone", phone);
                        userName = object.getString("UserName");
                        Log.d("userName", userName);
                        password = object.getString("UserName");
                        Log.d("password", password);
                    }

                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }


                /*if (success) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
