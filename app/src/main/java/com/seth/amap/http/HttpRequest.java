package com.seth.amap.http;


import android.app.Dialog;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/6.
 * <p>
 * Author zhanglj
 */

public class HttpRequest {
    private Dialog dialog;

    /*捡瓶子*/
    public static Call<ResponseBody> pickupbottle(String userid) {
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(120, TimeUnit.SECONDS).
                readTimeout(120, TimeUnit.SECONDS).
                writeTimeout(120, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUrl.BASEIP)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Address mAddress = retrofit.create(Address.class);

        Call<ResponseBody> call = mAddress.getPickupbottle(userid);
        return call;
    }
    /*摇一摇*/
    public static Call<ResponseBody> Shake(Object o) {
        String s = ResponseUtils.getRequestBody(o);
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(120, TimeUnit.SECONDS).
                readTimeout(120, TimeUnit.SECONDS).
                writeTimeout(120, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUrl.BASEIP)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Address mAddress = retrofit.create(Address.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);

        Call<ResponseBody> call = mAddress.getCall( HttpUrl.SHAKE , requestBody);
        return call;
    }
    /*附近的人*/
    public static Call<ResponseBody> NearBy(Object o) {
        String s = ResponseUtils.getRequestBody(o);
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(120, TimeUnit.SECONDS).
                readTimeout(120, TimeUnit.SECONDS).
                writeTimeout(120, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUrl.BASEIP)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Address mAddress = retrofit.create(Address.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s);

        Call<ResponseBody> call = mAddress.getCall( HttpUrl.ADDRESS , requestBody);
        return call;
    }


}
