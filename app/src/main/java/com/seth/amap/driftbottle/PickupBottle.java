package com.seth.amap.driftbottle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.seth.amap.http.HttpRequest;
import com.sinfeeloo.openmapdemo.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class PickupBottle extends Activity {
    private AnimationDrawable ad;
    private ImageView pick_spray1, pick_spray2, voice_msg, close;
    private RelativeLayout pick_up_layout;
    int hour, minute, sec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pick_up_bottle);
        //初始化
        pick_spray1 = (ImageView) findViewById(R.id.pick_spray1);
        pick_spray2 = (ImageView) findViewById(R.id.pick_spray2);
        voice_msg = (ImageView) findViewById(R.id.bottle_picked_voice_msg);
        close = (ImageView) findViewById(R.id.bottle_picked_close);
        pick_up_layout = (RelativeLayout) findViewById(R.id.pick_up_layout);
        network();
        voice_msg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PickupBottle.this, BottleMessageActivity.class);
                startActivity(i);
            }
        });
        close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pick_spray1.setVisibility(View.VISIBLE);
                ad.setOneShot(true);
                ad.start();
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pick_spray1.setVisibility(View.GONE);
                pick_spray2.setVisibility(View.VISIBLE);
                ad.setOneShot(true);
                ad.start();
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                pick_spray1.setVisibility(View.GONE);
                pick_spray2.setVisibility(View.GONE);
                voice_msg.setVisibility(View.VISIBLE);
                doStartAnimation(R.anim.pick_up_scale);
                close.setVisibility(View.VISIBLE);
            }
        }, 3000);


    }

    public void network() {
        Call<ResponseBody> call = HttpRequest.pickupbottle("Aes89347IudRR");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                String s = response.toString();
                Log.i("zxzc", "onResponse: " + s);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(PickupBottle.this, "请检查您的网络环境", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void doStartAnimation(int animId) {
        Animation animation = AnimationUtils.loadAnimation(this, animId);
        voice_msg.startAnimation(animation);
    }

    //播放语音动画
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        ad = (AnimationDrawable) getResources().getDrawable(R.drawable.pick_up_spray);
        if (pick_spray1 != null && pick_spray2 != null) {
            pick_spray1.setBackgroundDrawable(ad);
            pick_spray2.setBackgroundDrawable(ad);
        }

    }


}
