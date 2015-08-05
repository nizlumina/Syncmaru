package com.nizlumina.model;

import com.nizlumina.utils.WebUnit;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

//Simple class for FireBase CRUD tasks
public class FirebaseUnit
{
    private String mEndPoint;

    private FirebaseUnit(String endpoint, boolean fromBuilder)
    {
        this.mEndPoint = endpoint;
    }

    public boolean push(String httpMethod, String jsonPayload)
    {
        WebUnit webUnit = new WebUnit();
        Call task = createTask(httpMethod, jsonPayload, webUnit, mEndPoint);
        try
        {
            Response response = task.execute();

            return response.isSuccessful();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public String getString()
    {
        WebUnit webUnit = new WebUnit();
        Call task = createTask("GET", null, webUnit, mEndPoint);
        try
        {
            return task.execute().body().string();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private Call createTask(String httpMethod, String jsonPayload, WebUnit webUnit, String url)
    {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);

        if (jsonPayload != null)
        {
            requestBuilder.method(httpMethod,
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonPayload));
        }
        else requestBuilder.method(httpMethod, null);

        return webUnit.getClient().newCall(requestBuilder.build());
    }

    public static class Builder
    {
        private static final String authParam = "auth";
        private URIBuilder mUriBuilder;
        private String mSecretkey;

        public Builder(String secretKey, String endpoint)
        {
            try
            {
                mSecretkey = secretKey;
                mUriBuilder = new URIBuilder(endpoint);
            }
            catch (URISyntaxException e)
            {
                e.printStackTrace();
            }
        }

        public static Builder createNew(String secretKey, String endpoint)
        {
            return new Builder(secretKey, endpoint);
        }

        public String getSamplePath()
        {
            try
            {
                return mUriBuilder.build().toString();
            }
            catch (URISyntaxException e)
            {
                e.printStackTrace();
            }
            return "PATH FAILED TO PARSE";
        }

        public Builder appendQueryParams(String param, String value)
        {
            mUriBuilder.addParameter(param, value);
            return this;
        }

        public Builder appendPath(String path)
        {
            try
            {
                mUriBuilder = new URIBuilder(mUriBuilder.build().toString() + path);
            }
            catch (URISyntaxException e)
            {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * Build the the push object
         *
         * @return A FirebasePush object ready to be pushed. Return null on failure.
         */
        public FirebaseUnit build()
        {
            try
            {
                appendPath(".json");
                mUriBuilder.addParameter(authParam, mSecretkey);


                String finalEndpoint = mUriBuilder.build().toURL().toString();
                return new FirebaseUnit(finalEndpoint, true);
            }
            catch (URISyntaxException e)
            {
                e.printStackTrace();
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}
