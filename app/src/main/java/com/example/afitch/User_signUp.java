package com.example.afitch;

import androidx.appcompat.app.AlertDialog;
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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class User_signUp extends AppCompatActivity {

    public TextView goto_login;
    EditText edit_id, edit_pw, edit_nickName;
    public Button goto_signup;
    String id, pw,nickName;
    String checkIdResponse, checkNickResponse, checkSignupResponse, signup_url, checkId_url, checkNickname_url;
    JSONObject temp,signup_info, json_nickname,json_id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        goto_login = (TextView) findViewById(R.id.goto_login);
        edit_id = (EditText) findViewById(R.id.signup_id);
        edit_pw = (EditText) findViewById(R.id.signup_pw);
        edit_nickName = (EditText) findViewById(R.id.signup_nickName);
        goto_signup = (Button) findViewById(R.id.signup_btn);
        temp = new JSONObject();

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
                nickName = edit_nickName.getText().toString();
                signup_url = "http://3.36.65.27:8080/auth/sign-up?role=ROLE_USER";
                checkId_url = "http://3.36.65.27:8080/auth/id-duplicate?id=";
                checkNickname_url = "http://3.36.65.27:8080/auth/nickname-duplicate?nickName=";
                json_id = new JSONObject();
                json_nickname = new JSONObject();
                signup_info = new JSONObject();
                try {
                    json_id.put("accountId",id);
                    json_nickname.put("nickName",nickName);
                    signup_info.put("accountId",id);
                    signup_info.put("adminCode","");
                    signup_info.put("nickName",nickName);
                    signup_info.put("password",pw);

                    System.out.println(signup_info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (id.equals("") | pw.equals("") | nickName.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(),"모든 정보를 입력해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    // 아이디 중복확인
                    NetworkTask networkTask = new NetworkTask(checkId_url.concat(id), "get", true, json_id, 1);
                    networkTask.execute();

                    // 닉네임 중복확인
                    NetworkTask networkTask2 = new NetworkTask(checkNickname_url.concat(nickName), "get", true, json_nickname, 2);
                    networkTask2.execute();

                }
            }
        });
    }

    public class NetworkTask extends AsyncTask<Void, Void, JSONObject> {
        private String url, method;
        private JSONObject values;
        private int num;
        Boolean response;

        public NetworkTask(String url, String method, Boolean response, JSONObject values, int num) {
            this.url = url;
            Log.d("url", url);
            this.method = method;
            this.values = values;
            this.response = response;
            this.num = num;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            Log.d("체크","doInBackground 진입");
            JSONObject result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, method, response, values, ""); // 해당 URL 로부터 결과물을 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s); //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            temp = s;
            System.out.println(temp);

            // 아이디 중복결과
            if (num == 1){
                try {
                    // "true" 이면 중복X
                    checkIdResponse = s.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // 닉네임 중복결과
            else if (num == 2){
                try {
                    // "true" 이면 중복X
                    checkNickResponse = s.getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // 아이디가 중복, 닉네임은 OK
                if (!checkIdResponse.equals("true") && checkNickResponse.equals("true")) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"중복된 아이디 입니다.", Toast.LENGTH_SHORT);
                    toast1.show();
                }
                // 아이디는 OK, 닉네임이 중복
                else if (checkIdResponse.equals("true") && !checkNickResponse.equals("true")){
                    Toast toast2 = Toast.makeText(getApplicationContext(),"중복된 닉네임 입니다.", Toast.LENGTH_SHORT);
                    toast2.show();
                }
                // 둘다 중복
                else if (!checkIdResponse.equals("true") && !checkNickResponse.equals("true")){
                    Toast toast3 = Toast.makeText(getApplicationContext(),"중복된 아이디와 닉네임 입니다.", Toast.LENGTH_SHORT);
                    toast3.show();
                }
                // 둘다 중복아님
                else if(checkIdResponse.equals("true") && checkNickResponse.equals("true")){
                    // 회원가입
                    NetworkTask networkTask3 = new NetworkTask(signup_url, "post", true, signup_info, 3);
                    networkTask3.execute();
                }
            }
            else if (num == 3){
                try {
                    checkSignupResponse = s.getString("status_code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (checkSignupResponse.equals("201")){
                    Toast toast4 = Toast.makeText(getApplicationContext(),"회원가입에 성공하였습니다.", Toast.LENGTH_SHORT);
                    toast4.show();
                    Intent intent = new Intent(getApplicationContext(),User_login.class);
                    startActivity(intent);
                }
            }
        }
    }
}