package com.example.owner.esk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinActivity extends AppCompatActivity {
    EditText join_id;
    Button cancelBtn, joinBtn2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        join_id= (EditText) findViewById(R.id.join_id);

        joinBtn2 = (Button) findViewById(R.id.joinBtn2);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        /*가입완료 버튼*/
        joinBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("userId", join_id.getText().toString());

                setResult(RESULT_OK, result);
                finish();
            }
        });

        /*취소 버튼*/
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
