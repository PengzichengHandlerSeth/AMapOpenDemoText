package com.seth.amap.driftbottle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import com.sinfeeloo.openmapdemo.R;
public class MyBottle extends Activity {

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_bottle);

        back=(Button) findViewById(R.id.my_bottle_back);
        back.setOnClickListener(listener);
    }

    private OnClickListener listener=new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.my_bottle_back:
                    finish();
                    break;
            }
        }
    };


}
