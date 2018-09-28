package com.example.deepakrattan.apirequestdemo.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.deepakrattan.apirequestdemo.R;
import com.example.deepakrattan.apirequestdemo.adapter.UserAdapter;
import com.example.deepakrattan.apirequestdemo.apiConfiguration.ApiConfiguration;
import com.example.deepakrattan.apirequestdemo.beans.User_Bean;
import com.example.deepakrattan.apirequestdemo.httpRequsetProcessor.HttpRequestProcessor;
import com.example.deepakrattan.apirequestdemo.httpRequsetProcessor.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by deepak.rattan on 12/26/2016.
 */

public class DashboardActivity extends AppCompatActivity {

    private String name, address, phone, image;
    private ListView lv;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlUser, jsonResponseString, jsonStringToPost, imageUrl, completeImageUrl;
    private boolean success;
    private User_Bean user_bean;
    private ArrayList<User_Bean> user_beanArrayList;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //findViewByID
        lv = (ListView) findViewById(R.id.lv);

        //initialization

        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();
        user_beanArrayList = new ArrayList<User_Bean>();

        //Getting BAseURL
        baseURL = apiConfiguration.getApi();
        urlUser = baseURL + "Registration/GetAllUsers";
       // imageUrl = apiConfiguration.getImageURL();


        new UserTask().execute();
        userAdapter = new UserAdapter(DashboardActivity.this, user_beanArrayList);
        lv.setAdapter(userAdapter);



    }

    //Getting users list
    public class UserTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            JSONObject jsonObject = new JSONObject();
            jsonStringToPost = jsonObject.toString();

            response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlUser);
            jsonResponseString = response.getJsonResponseString();
            return jsonResponseString;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("str", s);


            try {
                JSONObject object = new JSONObject(s);
                success = object.getBoolean("success");
                if (success) {
                    JSONArray responseData = object.getJSONArray("responseData");
                    for (int i = 0; i < responseData.length(); i++) {
                        JSONObject object1 = (JSONObject) responseData.get(i);
                        name = object1.getString("Name");
                        Log.d("Name", name);
                        address = object1.getString("Address");
                        phone = object1.getString("Phone");
                        image = object1.getString("Image");
                        completeImageUrl = imageUrl + image;
                        user_bean = new User_Bean(name, address, phone, completeImageUrl);
                        user_beanArrayList.add(user_bean);
                    }
                    userAdapter.notifyDataSetChanged();

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
