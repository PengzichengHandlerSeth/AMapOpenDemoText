package com.seth.amap.driftbottle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.sinfeeloo.openmapdemo.R;

public class DriftBottleActivity extends Activity {

    private PopupWindow popupWindow, pop1;
    private View view;
    private LayoutInflater inflater;
    RelativeLayout rl_first_layout, rl_second_layout, rl_third_layout;
    RelativeLayout layout, bottle_main_layout;
    //    private ImageView bottle_night_moon, bottle_night_floodlight, bottle_night_floodlight_1, bottle_night_floodlight_2;
    private EditText chuck_edit;
    private Button chuck_keyboard, chuck_speak, bottle_back;
    private ImageView chuck_toast;
    private ImageView bottle_img, bottle_title, bottle_img_p;
    private TranslateAnimation ani0, ani1, ani2, ani3, ani4, ani5;
    ImageView chuck_empty2, chuck_empty1, chuck_spray;
    ImageView bottle_night_bottom1, bottle_night_bottom2, bottle_night_bottom3;
    int m = 2;

    int hour, minute, sec;

    AnimationDrawable ad, ad1, ad2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_driftbottle);

        //初始化
        bottle_main_layout = (RelativeLayout) findViewById(R.id.bottle_main_layout);
        rl_first_layout = (RelativeLayout) findViewById(R.id.rl_first_layout);
        rl_second_layout = (RelativeLayout) findViewById(R.id.rl_second_layout);
        rl_third_layout = (RelativeLayout) findViewById(R.id.rl_third_layout);
        rl_first_layout.setOnClickListener(listener);
        rl_second_layout.setOnClickListener(listener);
        rl_third_layout.setOnClickListener(listener);
        bottle_img = (ImageView) findViewById(R.id.bottle_img);
        bottle_img_p = (ImageView) findViewById(R.id.bottle_img_p);
        bottle_title = (ImageView) findViewById(R.id.bottle_title);
        layout = (RelativeLayout) findViewById(R.id.bottle_layout_title);
        bottle_back = (Button) findViewById(R.id.bottle_back);

        bottle_back.setOnClickListener(listener);
        bottle_title.setOnClickListener(listener);
        ani0 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.3f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -0.3f);
        ani1 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.3f, Animation.RELATIVE_TO_PARENT, 0.6f,
                Animation.RELATIVE_TO_SELF, -0.3f, Animation.RELATIVE_TO_SELF, 0.3f);
        ani2 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.6f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF, 0.0f);

        ani3 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -0.3f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -0.4f);
        ani4 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -0.3f, Animation.RELATIVE_TO_PARENT, -0.4f,
                Animation.RELATIVE_TO_SELF, -0.4f, Animation.RELATIVE_TO_SELF, 0.2f);
        ani5 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -0.4f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0.0f);
        ani0.setInterpolator(new AccelerateDecelerateInterpolator());
        ani0.setDuration(15000);
        ani0.setFillEnabled(true);
        ani0.setFillAfter(true);
        ani0.setAnimationListener(animationListener);

        ani1.setInterpolator(new AccelerateDecelerateInterpolator());
        ani1.setDuration(15000);
        ani1.setFillEnabled(true);
        ani1.setFillAfter(true);
        ani1.setAnimationListener(animationListener);

        ani2.setInterpolator(new AccelerateDecelerateInterpolator());
        ani2.setDuration(15000);
        ani2.setFillEnabled(true);
        ani2.setFillAfter(true);
        ani2.setAnimationListener(animationListener);

        ani3.setInterpolator(new AccelerateDecelerateInterpolator());
        ani3.setDuration(15000);
        ani3.setFillEnabled(true);
        ani3.setFillAfter(true);
        ani3.setAnimationListener(animationListener);

        ani4.setInterpolator(new AccelerateDecelerateInterpolator());
        ani4.setDuration(15000);
        ani4.setFillEnabled(true);
        ani4.setFillAfter(true);
        ani4.setAnimationListener(animationListener);

        ani5.setInterpolator(new AccelerateDecelerateInterpolator());
        ani5.setDuration(15000);
        ani5.setFillEnabled(true);
        ani5.setFillAfter(true);
        ani5.setAnimationListener(animationListener);

        bottle_img.startAnimation(ani0);
        bottle_img_p.startAnimation(ani3);


//        //获取系统时间
//        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(System.currentTimeMillis());
//        hour = c.get(Calendar.HOUR_OF_DAY);
//        minute = c.get(Calendar.MINUTE);
//        sec = c.get(Calendar.SECOND);
//        if (hour >= 18 || hour <= 6) {}


    }

    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_first_layout:
                    initChuckPop();
                    popupWindow.showAtLocation(findViewById(R.id.rl_first_layout), Gravity.BOTTOM, 0, 0);
                    break;
                case R.id.rl_second_layout:
                    Intent in = new Intent(DriftBottleActivity.this, PickupBottle.class);
                    startActivity(in);
                    break;
                case R.id.rl_third_layout:
                    Intent in3 = new Intent(DriftBottleActivity.this, MyBottle.class);
                    startActivity(in3);
                    break;
                case R.id.bottle_back:
                    finish();
                    break;
                case R.id.bottle_title:
                    Intent i = new Intent(DriftBottleActivity.this, Driftbottle_setting.class);
                    startActivity(i);
                    break;
                case R.id.chuck_keyboard:
                    if (v.isClickable()) {
                        chuck_edit.setVisibility(View.VISIBLE);
                        chuck_toast.setVisibility(View.GONE);
                        chuck_speak.setText("扔出去");
                        chuck_edit.setFocusable(true);
                        chuck_edit.setFocusableInTouchMode(true);
                        chuck_edit.requestFocus();
//                        view.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                        chuck_keyboard.setBackgroundResource(R.drawable.chatting_setmode_voice_btn_normal);

                    }
                    if (m % 2 == 0) {
                        chuck_keyboard.setBackgroundResource(R.drawable.chat_voice);
                        InputMethodManager inputMethodManager = (InputMethodManager) chuck_edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(chuck_edit, InputMethodManager.RESULT_SHOWN);
                        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    } else if (m % 2 != 0) {
                        chuck_keyboard.setBackgroundResource(R.drawable.chatting_setmode_keyboard_btn_normal);
                        chuck_edit.setVisibility(View.GONE);
                        chuck_speak.setText("按住说话");
                        chuck_toast.setVisibility(View.VISIBLE);
                    }
                    m++;
                    break;
                case R.id.chuck_speak:
                    Intent in4 = new Intent(DriftBottleActivity.this, Chuck_Animation.class);
                    startActivity(in4);
                    popupWindow.dismiss();
                    finish();
                    break;
            }

        }
    };

    private void doStartAnimation(int animId) {
        Animation animation = AnimationUtils.loadAnimation(this, animId);
        chuck_empty1.startAnimation(animation);
        chuck_empty2.startAnimation(animation);
    }

    final AnimationListener animationListener = new AnimationListener() {

        @Override
        public void onAnimationEnd(Animation animation) {
            // TODO Auto-generated method stub
            if (animation == ani0) {
                bottle_img.startAnimation(ani1);
            }
            if (animation == ani1) {
                bottle_img.startAnimation(ani2);
            }
            if (animation == ani2) {
                bottle_img.startAnimation(ani0);
            }
            if (animation == ani3) {
                bottle_img_p.startAnimation(ani4);
            }
            if (animation == ani4) {
                bottle_img_p.startAnimation(ani5);
            }
            if (animation == ani5) {
                bottle_img_p.startAnimation(ani3);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }
    };


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stubs
        super.onWindowFocusChanged(hasFocus);

//        if (bottle_night_floodlight != null) {
//            ad = (AnimationDrawable) getResources().getDrawable(R.drawable.bottle_night_floodlight);
//            bottle_night_floodlight.setBackgroundDrawable(ad);
//        } else if (bottle_night_floodlight_1 != null) {
//            ad1 = (AnimationDrawable) getResources().getDrawable(R.drawable.bottle_night_floodlight1);
//            bottle_night_floodlight_1.setBackgroundDrawable(ad);
//        } else if (bottle_night_floodlight_2 != null) {
//            ad2 = (AnimationDrawable) getResources().getDrawable(R.drawable.bottle_night_floodlight2);
//            bottle_night_floodlight_2.setBackgroundDrawable(ad);
//        }
    }

    //扔瓶子的窗口
    public void initChuckPop() {
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.chuck_pop, null);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        ColorDrawable colorDrawable = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(colorDrawable);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        view.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.chuck_layout1).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (y < height) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                    if (y > height) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                }
                return true;
            }
        });

        chuck_edit = (EditText) view.findViewById(R.id.chuck_edit);
        chuck_toast = (ImageView) view.findViewById(R.id.chuck_toast);
        chuck_keyboard = (Button) view.findViewById(R.id.chuck_keyboard);
        chuck_speak = (Button) view.findViewById(R.id.chuck_speak);

        chuck_keyboard.setOnClickListener(listener);
        chuck_speak.setOnClickListener(listener);
    }

    //扔瓶子的动画
    public void initChuckPop1() {
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.chuck_pop1, null);
        pop1 = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        pop1.setTouchable(true);
        pop1.setOutsideTouchable(true);
        pop1.setFocusable(true);

        view.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.chuck_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (y < height) {
                        pop1.dismiss();
                        pop1 = null;
                    }
                    if (y > height) {
                        pop1.dismiss();
                        pop1 = null;
                    }
                }
                return true;
            }
        });

        chuck_empty2 = (ImageView) view.findViewById(R.id.chuck_empty2);
        chuck_empty1 = (ImageView) view.findViewById(R.id.chuck_empty1);
        chuck_spray = (ImageView) view.findViewById(R.id.chuck_spray);


    }

    //屏幕触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取触摸坐标
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
//					//触摸屏幕时刻
            case MotionEvent.ACTION_DOWN:
                m++;
                if (m % 2 == 0)
                    layout.setVisibility(View.GONE);
                else
                    layout.setVisibility(View.VISIBLE);

                break;
        }
        return super.onTouchEvent(event);

    }


}
