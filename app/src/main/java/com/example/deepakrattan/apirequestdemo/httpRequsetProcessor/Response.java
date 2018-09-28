package com.example.deepakrattan.apirequestdemo.httpRequsetProcessor;

/**
 * Created by deepak.rattan on 12/23/2016.
 */

public class Response {

    String jsonResponseString;
    int responseCode;

    public String getJsonResponseString() {
        return jsonResponseString;
    }

    public void setJsonResponseString(String jsonResponseString) {
        this.jsonResponseString = jsonResponseString;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
