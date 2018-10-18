package com.seth.amap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seth.amap.driftbottle.DriftBottleActivity;
import com.seth.amap.http.HttpRequest;
import com.sinfeeloo.openmapdemo.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class NearByHuman extends AppCompatActivity {
    private TextView tv_useid, tv_longt, tv_lat, tv_address;
    private Button btn_click,btn_shake,btn_bottle;

    private String userid;
    private double longt;
    private double lat;
    private String address;
    private String string;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearbyhuman);
        Intent intent = getIntent();
        tv_useid = findViewById(R.id.tv_useid);
        tv_longt = findViewById(R.id.tv_longt);
        tv_lat = findViewById(R.id.tv_lat);
        tv_address = findViewById(R.id.tv_address);
        btn_click = findViewById(R.id.btn_click);
        btn_shake = findViewById(R.id.btn_shake);
        btn_bottle= findViewById(R.id.btn_bottle);

        userid = intent.getStringExtra("userid");
        longt = intent.getDoubleExtra("longt", 0.0000);
        lat = intent.getDoubleExtra("lat", 0.0000);
        address = intent.getStringExtra("address");

        tv_useid.setText(String.valueOf(userid));
        tv_longt.setText(String.valueOf(longt));
        tv_lat.setText(String.valueOf(lat));
        tv_address.setText(address);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                near();
            }
        });
        btn_shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(NearByHuman.this, ShakeActivity.class);
                i.putExtra("userid", userid);
                i.putExtra("longt", longt);
                i.putExtra("lat", lat);
                startActivity(i);
            }
        });
        btn_bottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(NearByHuman.this, DriftBottleActivity.class);
                startActivity(i);
            }
        });
    }

    public void near() {
        Call<ResponseBody> call = HttpRequest.NearBy(new NearByBeen(
                "Aes89347IudRR", 116.4612, 39.9212, 1, 1));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    Log.i("zxzc", "onResponse: " + s);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(NearByHuman.this, "请检查您的网络环境", Toast.LENGTH_LONG).show();
            }
        });
    }

}
