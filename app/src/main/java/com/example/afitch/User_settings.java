package com.example.afitch;

import static org.webrtc.ContextUtils.getApplicationContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class User_settings extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_settings,
                container,
                false);
        //회원 정보 수정 버
        TextView editBtn = (TextView) view.findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onFragmentChanged(0);

            }
        });
        //피드백 버튼
        TextView feedbackBtn = (TextView) view.findViewById(R.id.feedbackBtn);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("feedback", "feedbackplz");

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("feedback");
                builder.setMessage("plzzzzzzzzzz");
                builder.setPositiveButton("ok", null);
                builder.create().show();

            }
        });

        return view;
    }
}

        /*//로그아out 버튼
        TextView logoutBtn = (TextView) view.findViewById(R.id.logoutBtn);
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you logout ??");
                builder.setMessage("plzzzzzzzzzz");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String url = "http://3.36.65.27:8080/auth";
                        JSONObject values = new JSONObject();
                        try {
                            System.out.println(values);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // AsyncTask를 통해 HttpURLConnection 수행.
                        User_settings.NetworkTask networkTask = new User_settings.NetworkTask(url, values);
                        networkTask.execute();

                    }
                });
                builder.setNegativeButton("no", null);
                builder.create().show();

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
    }*/


