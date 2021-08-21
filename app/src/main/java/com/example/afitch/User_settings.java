package com.example.afitch;


import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class User_settings extends Fragment {
    JSONObject info = new JSONObject();
    TextView settingsId;
    String accessToken;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_settings,
                container,
                false);

        //accessToken
        SharedPreferences sp = this.getActivity().getSharedPreferences("file", MODE_PRIVATE);
        accessToken = sp.getString("accessToken",null);

        //info edit btn
        TextView editBtn = (TextView) view.findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onFragmentChanged(0);

            }
        });

        settingsId = (TextView) view.findViewById(R.id.settingsId);
        String url = "http://3.36.65.27:8080/user?authorities=ROLE_USER";

        User_settings.NetworkTask networkTask = new User_settings.NetworkTask(url,"get",true,info);
        networkTask.execute();

        Log.d("get?? ",info.toString());



        //feedback btn
        TextView feedbackBtn = (TextView) view.findViewById(R.id.feedbackBtn);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("feedback");
                builder.setMessage("plzzzzzzzzzz");
                builder.setPositiveButton("ok", null);
                builder.create().show();

            }
        });

        TextView musicBtn = (TextView) view.findViewById(R.id.musicBtn);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("music");
                builder.setMessage("ing ... ");
                builder.setPositiveButton("ok", null);
                builder.create().show();

            }
        });

        TextView resetBtn = (TextView) view.findViewById(R.id.resetBtn);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("reset");
                builder.setMessage("ing ... ");
                builder.setPositiveButton("ok", null);
                builder.create().show();

            }
        });

        //로그아out 버튼
        Intent intent = new Intent(getActivity(),User_login.class);

        TextView logoutBtn = (TextView) view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you logout ??");
                builder.setMessage("plzzzzzzzzzz");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        accessToken = null;
                        logout();

                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("no", null);
                builder.create().show();

            }
        });

        return view;

    }
    public void logout(){


        SharedPreferences sp = this.getActivity().getSharedPreferences("file", MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putString("accessToken", null);
        editor.putString("refreshToken", null);
        editor.putString("accessExpr", null);
        editor.putString("refreshExpr", null);

        editor.commit();
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
            result = requestHttpURLConnection.request(url, method, response, values,"Bearer "+accessToken); // 해당 URL로 부터 결과물을 얻어온다.
            Log.d("체크", "result : " + result);
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s);

//            id loading
            try {
                if (s.getString("status_code").equals("200")) {

                    String response = s.getString("response");
                    JSONObject jsonObject = new JSONObject(response);

                    String nickname = jsonObject.getString("nickName");
                    int weight = jsonObject.getInt("weight");
                    int height = jsonObject.getInt("height");
                    settingsId.setText(nickname);

                    save(nickname, weight, height);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public void save(String nickname,int weight,int height){


        SharedPreferences sp = this.getActivity().getSharedPreferences("info", MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putString("nickname", nickname);
        editor.putInt("weight", weight);
        editor.putInt("height", height);

        editor.commit();
    }
}