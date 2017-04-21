package com.example.administrator.huaweiview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    MyView mv;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.tv);
        mv= (MyView) findViewById(R.id.mv);
        mv.change(200);
       ll= (LinearLayout) findViewById(R.id.ll);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mv.change(200);
            }
        });

        mv.setOnAngleColorListener(onAngleColorListener);
        mv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mv.change(200);
            }
        });
    }
    private MyView.OnAngleColorListener onAngleColorListener=new MyView.OnAngleColorListener() {
        @Override
        public void onAngleColorListener(int red, int green) {
            Color color=new Color();
            int c=color.argb(150, red, green, 0);
            ll.setBackgroundColor(c);
        }
    };
}
