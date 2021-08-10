package com.example.afitch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class User_signUp extends AppCompatActivity {

    public TextView goto_login;
    EditText edit_id, edit_pw, edit_height, edit_weight, edit_nickName;
    public Button goto_signup;
    String id, pw, height, weight, nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        goto_login = (TextView) findViewById(R.id.goto_login);
        edit_id = (EditText) findViewById(R.id.signup_id);
        edit_pw = (EditText) findViewById(R.id.signup_pw);
        edit_height = (EditText) findViewById(R.id.signup_height);
        edit_weight = (EditText) findViewById(R.id.signup_weight);
        edit_nickName = (EditText) findViewById(R.id.signup_nickName);
        goto_signup = (Button) findViewById(R.id.signup_btn);

        goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),User_login.class);
                startActivity(intent);
            }
        });

        goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("체크","회원가입 버튼 클릭확인");
                id = edit_id.getText().toString();
                pw = edit_pw.getText().toString();
                height = edit_height.getText().toString();
                weight = edit_weight.getText().toString();
                nickName = edit_nickName.getText().toString();
                String url = "http://3.36.65.27:8080/auth/sign-up?role=ROLE_USER";
                JSONObject values = new JSONObject();
                try {
                    values.put("accountId",id);
                    values.put("adminCode","");
                    values.put("nickName",nickName);
                    values.put("password",pw);

                    System.out.println(values);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // AsyncTask를 통해 HttpURLConnection 수행.
                NetworkTask networkTask = new NetworkTask(url, values);
                networkTask.execute();
            }
        });
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private JSONObject values;

        public NetworkTask(String url, JSONObject values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.d("체크","doInBackground 진입");
            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s); //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.

        }
    }
}