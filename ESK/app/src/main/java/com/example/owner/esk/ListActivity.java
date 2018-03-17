package com.example.owner.esk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
//import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListActivity extends Activity {
/*

    boolean visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        LinearLayout inflatedLayout = (LinearLayout)findViewById(R.id.inflatedLayout);
        LayoutInflater inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflated_Layout.xml로 구성한 레이아웃을 inflatedLayout 영역으로 확장
//        inflater.inflate(R.layout.inflated_layout, inflatedLayout);
        TextView tv_day = (TextView)findViewById(R.id.tv_day);
        Intent intent = getIntent();
        String strTravel = intent.getStringExtra("strTravel");

        int i;
        for(i=0; i<5; i++){ //for(i=0;i<day; i++){
            inflater.inflate(R.layout.inflated_layout, inflatedLayout);
        }
    }
    */
/*


    */
/*
    activity_main.xml에서 정의한 버튼을 클릭할 때 동적으로 확장되도록 할 수도 있음.
    public void onClick(View v){

        if(visible == false)
        {
            LinearLayout inflatedLayout = (LinearLayout)findViewById(R.id.inflatedLayout);

            LayoutInflater inflater =  (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.lnflated_layout, inflatedLayout);

            visible = true;
        }
    }
    *//*

}*/

    LinearLayout dynamicLayout;
//    FloatingActionButton fab1, fab2, fab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        int getNumber = intent.getExtras().getInt("input");

        dynamicLayout = (LinearLayout)findViewById(R.id.dynamicLayout);
       /* fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)findViewById(R.id.fab3);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

//        for(int i = 0; i<getNumber; i++) {
        for(int i = 0; i<5; i++) {
            LinearLayout dayList = new LinearLayout(this);
            TextView daytv = new TextView(this);
            daytv.setText("Day"+(i+1));
            TextView subday = new TextView(this);
            subday.setText("Sub"+(i+1));
            dayList.setOrientation(LinearLayout.VERTICAL);
            dayList.addView(daytv);
            dayList.addView(subday);
            dayList.setBackgroundColor(getResources().getColor(R.color.material_grey_300));
            dynamicLayout.addView(dayList);
        }
    }


    /*********뒤로 가기***********/
    /*public void back(View view) {
        Intent intent = new Intent(SubActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }*/

}