package com.example.afitch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String accessToken, refreshToken, accessExpr;
    Long expr = 0L;
    SharedPreferences sf;
    SharedPreferences.Editor editor;
    ArrayList<String> exerciseCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // token 데이터 불러오기
        sf = getSharedPreferences("file", MODE_PRIVATE);
        accessToken = sf.getString("accessToken", "");
        refreshToken = sf.getString("refreshToken", "");
        System.out.println(sf.getAll());
        if (accessToken != "")
            expr = Long.parseLong(sf.getString("accessExpr", ""));

        if (accessToken == "") {
            Intent intent = new Intent(getApplicationContext(), User_login.class);
            startActivity(intent);
        } else {
            String url = "http://3.36.65.27:8080/exercises/categories";
            NetworkTask tokenAccess = null;
            try {
                tokenAccess = new NetworkTask(url, "get", true, null, accessToken, 1);
                tokenAccess.execute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavi);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new Home_main()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) { //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml띄우기
                    case R.id.fragment1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Home_main()).commit();
                        break;
                    case R.id.fragment2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Food_Main()).commit();
                        break;
                    case R.id.fragment3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Date_Video_Diet()).commit();
                        break;
                    case R.id.fragment4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new User_settings()).commit();
                        break;
                }
                return true;
            }
        });
    }

    // 비동기 통신
    // num == 1 -> accesstoken 인증
    // num == 2 -> 운동카테고리 요청
    public class NetworkTask extends AsyncTask<Void, Void, JSONObject> {
        String accessToken, method, url, header;
        Boolean response;
        private JSONObject values, result;
        int num;

        public NetworkTask(String _url, String method, Boolean response, JSONObject values, String _token, Integer num) throws MalformedURLException {
            this.accessToken = _token;
            this.method = method;
            this.response = response;
            this.values = values;
            this.url = _url;
            this.num = num;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            if (num == 1) {
                header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
            }
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, method, response, values, header); // 해당 URL로 부터 결과물을 얻어온다.
            return result;

        }

        protected void onPostExecute(JSONObject s) {
            if (num == 1) {
                if (accessToken != null & !accessToken.equals("")) {
                    try {
                        String temp = s.getString("status_code");
                        long now = System.currentTimeMillis();
                        // accessToken 사용가능 , result -> 운동카테고리
                        if (Integer.parseInt(temp) >= 200 & Integer.parseInt(temp) <= 299 & now <= expr) {
                            temp = s.getString("response");
                            JSONObject obj = new JSONObject(temp);
                            JSONArray jsonArray = new JSONArray(obj.getString("categories"));
                            exerciseCategory = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //Adding each element of JSON array into ArrayList
                                exerciseCategory.add((String) jsonArray.get(i));
                            }
                            System.out.println(exerciseCategory);
                        }
                        // 기존 token 정보 지우기 & accessToken 재발급 요청
                        else {
                            // 기존 token 삭제
                            editor = sf.edit();
                            editor.remove("accessToken");
                            editor.remove("accessExpr");
                            editor.commit();

                            // 재발급 요청
                            Log.d("토큰 재발급","재발급요청하겠습니다.");
                            String url2 = "http://3.36.65.27:8080/auth/refresh/";
                            JSONObject refreshVal = new JSONObject();
                            refreshVal.put("refreshToken", refreshToken);
                            NetworkTask nt = new NetworkTask(url2, "post", true, refreshVal, null, 2);
                            nt.execute();

                        }
                    } catch (JSONException | MalformedURLException e) {
                        e.printStackTrace();
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(), User_login.class);
                    startActivity(intent);
                }
            }

            // result -> 새로운 accessToken
            else if (num == 2) {
                try {
                    if (s.getString("status_code").equals("200")) {
                        // access token, refresh token 저장
                        String temp2 = s.getString("response");
                        JSONObject jsonObject = new JSONObject(temp2);
                        JSONObject access = new JSONObject(jsonObject.getString("access"));
                        accessToken = access.getString("token");
                        accessExpr = access.getString("expiredIn");

                        editor.putString("accessToken", accessToken);
                        editor.putString("accessExpr", accessExpr);
                        editor.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private long time = 0;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            Log.d("백키", "2");
            if (System.currentTimeMillis() - time >= 2000) {
                time = System.currentTimeMillis();
                Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
            } else {
                finish();
            }
        } else {
            Log.d("백키", "3");
            super.onBackPressed();
        }
    }

    // FragMainActivity 또는 Fragment 에서 Fragment로 전환할 때 사용
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public void onFragmentChanged(int index) {
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new User_settings_edit()).commit();
        }
        if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new User_settings()).commit();
        }
        if(index == 2){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new Ranking_detail()).commit();
        }
    }
}