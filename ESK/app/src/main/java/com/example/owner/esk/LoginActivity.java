package com.example.owner.esk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity{
    EditText et_id;
    Button loginBtn, joinBtn;
    String userid;

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    //private static final String TAG_ADD = "address";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    Boolean validation =false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = (EditText)findViewById(R.id.et_id);

        loginBtn=(Button)findViewById(R.id.loginBtn);
        joinBtn=(Button)findViewById(R.id.joinBtn);


        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
         public void onClick(View v){
                userid = et_id.getText().toString();
                /*check if you succeed to login or not*/
                loginValidation(userid);

            }
        });

        /*Click '회원가입'button and pass to the JoinAcitivy.java*/
        joinBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent,1000);
                //1000은 요청에 대한 식별자
            }
        });


    }
    /*when you try to login check the ID */
    private boolean loginValidation(String id){
        getData("http://172.24.40.17:85/PHP_connection.php");

        if(TextUtils.isEmpty(userid)){ //sign in first
            Toast.makeText(LoginActivity.this,"Please Sign in first", Toast.LENGTH_LONG).show();
            validation = false;
            //return false;
        }
        return true;
    }

    protected void showList() {
        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                //String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                if(userid.equals(name)) {
                    validation = true;
                    break;
                    //Toast.makeText(LoginActivity.this, name, Toast.LENGTH_LONG).show();
                }else
                    validation = false;
                //Toast.makeText(LoginActivity.this, name, Toast.LENGTH_LONG).show();
            }
            if(validation){ //when you succeed to 'login'
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(it);
                //save id to DB
            }
            else{ //when you failed to login
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();

                //goto LoginActivity
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    /*when you finish to join*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // setResult를 통해 받아온 요청번호, 상태, 데이터
        Log.d("RESULT", requestCode + "");
        Log.d("RESULT", resultCode + "");
        Log.d("RESULT", data + "");
        if(requestCode == 1000 && resultCode == RESULT_OK) {
            Toast.makeText(LoginActivity.this, "회원가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
            et_id.setText(data.getStringExtra("userId"));
        }
    }


    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}

