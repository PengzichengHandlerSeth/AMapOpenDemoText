package com.seth.amap.http;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Address {
    @POST
    Call<ResponseBody> getCall(@Url String url, @Body RequestBody requestBody);

    //捡瓶子
    @GET("position/bottle/{userid}")
    Call<ResponseBody> getPickupbottle(@Path("userid") String userid);

}