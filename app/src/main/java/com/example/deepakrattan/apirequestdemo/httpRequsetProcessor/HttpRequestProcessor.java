package com.example.deepakrattan.apirequestdemo.httpRequsetProcessor;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by deepak.rattan on 12/23/2016.
 */

public class HttpRequestProcessor {
    String jsonString, requestMethod, requestURL, jsonResponseString, responseCode;
    StringBuilder sb;
    Response response; // Response class will store http JSON Response string and Response Code

    // This method will process POST request and  return a response object containing Response String and Response Code
    public Response pOSTRequestProcessor(String jsonString, String requestURL) {
        sb = new StringBuilder();
        response = new Response();
        try {
            // Sending data to API
            URL url = new URL(requestURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setReadTimeout(15000); // Timeout on waiting to read data
            httpURLConnection.setConnectTimeout(15000); //Timeout in making the initial connection
            OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream());
            out.write(jsonString); // Transmit data by writing to the stream returned by getOutputStream()
            out.flush();
            out.close();
            // Read the response
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String responseData = br.readLine();
            while (responseData != null) {
                sb.append(responseData);
                responseData = br.readLine();
            }
            // Reading the response code
            int responseCode = httpURLConnection.getResponseCode();
            Log.d("Response Code", String.valueOf(responseCode));
            response.setResponseCode(responseCode);
            br.close();
            httpURLConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonResponseString = sb.toString();
        response.setJsonResponseString(jsonResponseString);
        return response; //return response object
    }

    // This method will process http GET request and return json response string
    public String gETRequestProcessor(String requestURL) {
        sb = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Length", "0");
            int status = urlConnection.getResponseCode();
            Log.e("Status", String.valueOf(status));
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String responseData = br.readLine();
            while (responseData != null) {
                sb.append(responseData);
                responseData = br.readLine();
            }
            br.close();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonResponseString = sb.toString();
        return jsonResponseString;
    }

    public String pUTRequestProcessor(String jsonString, String requestURL) {
        sb = new StringBuilder();
        try {
            //sending data to API
            URL urlMobileUser = new URL(requestURL);
            HttpURLConnection urlConnection = (HttpURLConnection) urlMobileUser.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setReadTimeout(15000); //Sets the maximum time to wait for an input stream read to complete before giving up
            urlConnection.setConnectTimeout(15000); //Sets the maximum time in milliseconds to wait while connecting
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonString);
            out.flush();
            out.close();
            //getting response from API
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String responseData = br.readLine();
            while (responseData != null) {
                sb.append(responseData);
                responseData = br.readLine();
            }
            br.close();
            urlConnection.disconnect();
        } catch (Exception ex) {
            Log.e("Connection error", ex.toString());
        }
        jsonResponseString = sb.toString();
        // Log.e("JSON Response String", jsonResponseString);
        return jsonResponseString;
    }

}

