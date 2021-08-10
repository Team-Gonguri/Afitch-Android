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
import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

public class User_login extends AppCompatActivity {

    public TextView goto_signup;
    public EditText edit_id, edit_pw;
    public Button goto_login;
    String id, pw, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        goto_login = (Button) findViewById(R.id.login_btn);
        goto_signup = (TextView) findViewById(R.id.goto_signup);
        edit_id = (EditText) findViewById(R.id.login_id);
        edit_pw = (EditText) findViewById(R.id.login_pw);

        goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), User_signUp.class);
                startActivity(intent);
            }
        });

        goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("체크","로그인 버튼 클릭확인");
                id = edit_id.getText().toString();
                pw = edit_pw.getText().toString();
                String url = "http://3.36.65.27:8080/auth/sign-in/";
                JSONObject values = new JSONObject();
                try {
                    values.put("accountId",id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    values.put("password",pw);
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
            Log.d("체크", "result : " + result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s); //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            if (s == null){
                Log.d("체크","로그인 실패");
            }
            else{
                Log.d("체크","로그인성공");
            }
        }
    }

}