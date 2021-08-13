package com.example.afitch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

public class User_login extends AppCompatActivity {

    public TextView goto_signup;
    public EditText edit_id, edit_pw;
    public Button goto_login;
    String id, pw, accessToken, refreshToken, temp, accessExpr, refreshExpr;

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
                NetworkTask networkTask = new NetworkTask(url, "post", true, values);
                networkTask.execute();
                System.out.println(temp);
            }
        });
    }

    public class NetworkTask extends AsyncTask<Void, Void, JSONObject> {
        private String url, method;
        private JSONObject values;
        Boolean response;

        public NetworkTask(String url,String method, Boolean response, JSONObject values) {
            this.url = url;
            this.method = method;
            this.values = values;
            this.response = response;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject result;
            Log.d("체크","doInBackground 진입");
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, method, response, values, ""); // 해당 URL로 부터 결과물을 얻어온다.
            Log.d("체크", "result : " + result);
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s); //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            try {
                if (s.getString("status_code").equals("200")){
                    Toast toast = Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.", Toast.LENGTH_SHORT);
                    toast.show();

                    // access token, refresh token 저장
                    temp = s.getString("response");
                    JSONObject jsonObject = new JSONObject(temp);
                    JSONObject access = new JSONObject(jsonObject.getString("access"));
                    JSONObject refresh = new JSONObject(jsonObject.getString("refresh"));
                    accessToken = access.getString("token");
                    accessExpr = access.getString("expiredIn");
                    refreshToken = refresh.getString("token");
                    refreshExpr = access.getString("expiredIn");

                    storeInFile();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast toast1 = Toast.makeText(getApplicationContext(),"잘못된 정보입니다. 다시 입력해주세요", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void storeInFile(){

        //앱이 종료되도 데이터를 사용할 수 있게 함
        SharedPreferences sp = getSharedPreferences("file", MODE_PRIVATE);

        //저장을 하기위해 editor를 이용하여 값을 저장시켜준다.
        SharedPreferences.Editor editor = sp.edit();
        //token 저장
        editor.putString("accessToken", accessToken);
        editor.putString("refreshToken", refreshToken);
        editor.putString("accessExpr", accessExpr);
        editor.putString("refreshExpr", accessExpr);

        //최종 커밋
        editor.commit();
    }


}