package com.example.owner.esk;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*나의 여행 추가하기*/

public class AddTravelActivity extends Activity {

    TextView tv_min,tv_max;
    Spinner sp_nation;
    Button btn_add_check;
    final int DATE_MIN = 3, DATE_MAX = 4;
    String str_minDay, str_maxDay, str_nation, str_money;
    String modNation, modMinday, modMaxday,modMoney,modTravel;
    String mod_nation, mod_minDay, mod_maxDay, mod_money;
    int minDay, maxDay, dayCount;
    final int ADD_INTENT=0, MOD_INTENT=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtravel);
        setTitle("Travel List");

        sp_nation = (Spinner)findViewById(R.id.sp_nation);
        btn_add_check = (Button)findViewById(R.id.btn_add_check);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.nation, android.R.layout.simple_spinner_item);//어댑터 생성  list는 string.xml에 있음
        sp_nation.setAdapter(adapter);        //스피너와 어댑터 연결

        /*일정 선택하기*/
        tv_min = (TextView)findViewById(R.id.tv_min);
        tv_max = (TextView)findViewById(R.id.tv_max);
        tv_min.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_MIN); // 날짜 설정 다이얼로그 띄우기
            }
        });
        tv_max.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_MAX);
            }
        });
        dayCount = maxDay - minDay + 1;/*총 여행일 수*/

        //리스트 수정중
        /*Intent modIntent = getIntent();
        modNation = modIntent.getStringExtra("modNation");
        modMinday = modIntent.getStringExtra("modMinday");
        modMaxday = modIntent.getStringExtra("modMaxday");
        switch(modNation){
            case "한국":sp_nation.setSelection(0);break;
            case "중국":sp_nation.setSelection(1);break;
            case "일본":sp_nation.setSelection(2);break;
            case "미국":sp_nation.setSelection(3);break;
            case "프랑스":sp_nation.setSelection(4);break;
        }
        tv_min.setText(modMinday);
        tv_max.setText(modMaxday);
*/
    }

    /*확인 버튼 클릭 // 선택한 나라이름 str_nation을 MainActivity에 전달해줌.*/
    public void mOnClose(View v){
            str_nation = sp_nation.getSelectedItem().toString();
            switch (str_nation) {
                case "한국":
                    str_money = "￦";
                    break;
                case "중국":
                    str_money = "￥";
                    break;
                case "일본":
                    str_money = "￥";
                    break;
                case "미국":
                    str_money = "＄";
                    break;
                case "프랑스":
                    str_money = "€";
                    break;
            }

            Intent addIntent = new Intent(AddTravelActivity.this, MainActivity.class);
            addIntent.putExtra("str_nation", str_nation);
            addIntent.putExtra("str_minDay", str_minDay);
            addIntent.putExtra("str_maxDay", str_maxDay);
            addIntent.putExtra("str_money", str_money);
            setResult(0, addIntent);
        finish();  //        액티비티(팝업) 닫기
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case MOD_INTENT:
                modNation = data.getStringExtra("modNation");
                modMaxday =data.getStringExtra("modMaxday");
                modMinday= data.getStringExtra("modMinday");
                /*switch(modNation){
                    case "한국":sp_nation.setSelection(0);break;
                    case "중국":sp_nation.setSelection(1);break;
                    case "일본":sp_nation.setSelection(2);break;
                    case "미국":sp_nation.setSelection(3);break;
                    case "프랑스":sp_nation.setSelection(4);break;
                }*/
                tv_min.setText(modMinday);
                tv_max.setText(modMaxday);

//                modMoney=data.getStringExtra("modMoney"); //주요통화

//                modTravel = modNation + "\n" + modMinday+"~"+modMaxday;

//                items.add(modTravel);
//                adapter.notifyDataSetChanged();
                break;

            /*case MOD_INTENT:
                modNation = data.getStringExtra("mod_nation");
                modMinday = data.getStringExtra("mod_minday");
                modMaxday = data.getStringExtra("mod_maxday");
                modTravel = modNation+"\n"+modMinday+"~"+modMaxday;
                items.add(modTravel);
                adapter.notifyDataSetChanged();
                break;*/
            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {//안드로이드 백버튼 막기
        return;
    }

    /*일정 선택하는 함수*/
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_MIN: //시작 날짜
                DatePickerDialog dpd = new DatePickerDialog(AddTravelActivity.this, // 현재화면의 제어권자
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv_min.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                                str_minDay = tv_min.getText().toString();
                                minDay = dayOfMonth;
                            }
                        }, // 사용자가 날짜설정 후 다이얼로그 빠져나올때 //    호출할 리스너 등록
                        2018, 2, 30); // 기본값 연월일
                return dpd;
            case DATE_MAX: //종료 날짜
                DatePickerDialog dpdd = new DatePickerDialog(AddTravelActivity.this, // 현재화면의 제어권자
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv_max.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                                str_maxDay = tv_max.getText().toString();
                                maxDay = dayOfMonth;
                            }
                        }, 2018, 2, 30);
                return dpdd;
        }
        return super.onCreateDialog(id);
    }
}
