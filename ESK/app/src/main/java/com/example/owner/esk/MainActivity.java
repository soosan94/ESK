package com.example.owner.esk;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String str_nation=null, str_minDay=null,str_maxDay=null, str_money=null, travel;
    private ArrayList<String> items;
    private ArrayAdapter adapter;
    private ListView listview;
    final int ADD_INTENT = 0;
    final int MOD_INTENT = 1; //수정할 경우
    String modNation, modMinday, modMaxday, modMoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        items = new ArrayList<>() ; // 빈 데이터 리스트 생성.
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items) ;// ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        listview = (ListView) findViewById(R.id.listview1) ;// listview 생성 및 adapter 지정.
        listview.setAdapter(adapter) ;
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);//여러항목선택가능 설정(MULTIPLE)

        /***동적 리스트 추가***/
       /* FloatingActionButton btn_add_travel = (FloatingActionButton) findViewById(R.id.btn_add_travel);
        btn_add_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddTravelActivity.class);
                startActivityForResult(addIntent, ADD_INTENT);
            }
        });*/
    }

    /***편집 메뉴 만들기***/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mytravel, menu);
        return true;

    }

    /***편집 메뉴 선택했을경우***/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int count, checked;
        switch (item.getItemId()) {
            /**********해당 여행에 대한 상세 리스트 페이지로 이동**********************/
            case R.id.details:
                count = adapter.getCount();
                if (count > 0) {
                    checked = listview.getCheckedItemPosition();// 현재 선택된 아이템의 position 획득.
                    if (checked > -1 && checked < count) {
                        //int

                        String strTravel = items.get(checked).toString();
                        Intent intent = new Intent(MainActivity.this, ListActivity.class);
                        intent.putExtra("strTravel", strTravel);
                        startActivity(intent);
                    }
                    finish();
                }
                break;

            /*동적 리스트 수정*/
            case R.id.btn_mod_travel:// modify button에 대한 이벤트 처리.
                count = adapter.getCount();
                if (count > 0) {
                    checked = listview.getCheckedItemPosition();// 현재 선택된 아이템의 position 획득.
                    if (checked > -1 && checked < count) {
                        // 아이템 수정
                        Intent modIntent = new Intent(MainActivity.this, AddTravelActivity.class);
                        int idx = travel.indexOf("\n");
                        int idxx = travel.indexOf("~");
                        modNation = travel.substring(0, idx);
                        modMinday = travel.substring(idx+1, idxx);
                        modMaxday = travel.substring(idxx+1);

                        modIntent.putExtra("modNation", modNation);
                        modIntent.putExtra("modMaxday", modMaxday);
                        modIntent.putExtra("modMinday", modMinday);

                        startActivityForResult(modIntent,MOD_INTENT);

                        adapter.notifyDataSetChanged();// listview 갱신
                    }
                }
                break;

            /***동적 리스트 삭제***/
            case R.id.btn_del_travel:                // delete button에 대한 이벤트 처리.
                count = adapter.getCount();
                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listview.getCheckedItemPosition();
                    if (checked > -1 && checked < count) {
                        // 아이템 삭제
                        items.remove(checked);
                        // listview 선택 초기화.
                        listview.clearChoices();
                        // listview 갱신.
                        adapter.notifyDataSetChanged();
                    }
                }
                break;

        }//switch
            return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case ADD_INTENT:
                str_nation = data.getStringExtra("str_nation");
                str_minDay =data.getStringExtra("str_minDay");
                str_maxDay= data.getStringExtra("str_maxDay");
                str_money=data.getStringExtra("str_money"); //주요통화

                travel = str_nation + "\n" + str_minDay+"~"+str_maxDay;

                items.add(travel);
                adapter.notifyDataSetChanged();
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

}
