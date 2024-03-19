package com.example.hello_android.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8083/hello-web-app/rest/") // call to the server
                    .addConverterFactory(GsonConverterFactory.create()) // convert the response to a list of products
                    .build();
        }
        return retrofit;
    }
}
