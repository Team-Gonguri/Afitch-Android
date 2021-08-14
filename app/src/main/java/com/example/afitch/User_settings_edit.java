package com.example.afitch;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

public class User_settings_edit extends Fragment {
    public Button gotoSave;
    public EditText weightEdit,heightEdit,yearEdit;
    String weight,height,year;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_settings_edit,
                container,
                false);

        weightEdit = (EditText) view.findViewById(R.id.weightEdit);
        heightEdit = (EditText) view.findViewById(R.id.weightEdit);
        yearEdit = (EditText) view.findViewById(R.id.weightEdit);

        gotoSave = (Button) view.findViewById(R.id.gotoSave);

        gotoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight = weightEdit.getText().toString();
                height = heightEdit.getText().toString();
                year = yearEdit.getText().toString();

                String url = "http://3.36.65.27:8080/user";
                JSONObject values = new JSONObject();
                try {
                    values.put("height",height);
                    values.put("weight",weight);

                    System.out.println(values);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // AsyncTask를 통해 HttpURLConnection 수행.
                NetworkTask networkTask = new NetworkTask(url, "POST",true,values);
                networkTask.execute();


                //화면 전환
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onFragmentChanged(1);
            }
        });

        //닉네임 바꾸기
        Button nicknameBtn = (Button) view.findViewById(R.id.nicknameBtn);
        final EditText nicknameEdit = new EditText(getContext());
        nicknameBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("change nickname");
                builder.setMessage("plzzzzzzzzzz");


                builder.setView(nicknameEdit);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String value = nicknameEdit.getText().toString();

                        String url = "http://3.36.65.27:8080/user";
                        JSONObject values = new JSONObject();
                        try {
                            values.put("nickname",value);

                            System.out.println(values);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // AsyncTask를 통해 HttpURLConnection 수행.
                        NetworkTask networkTask = new NetworkTask(url, "POST",true,values);
                        networkTask.execute();

                    }
                });
                builder.create().show();

            }
        });

        return view;

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
            result = requestHttpURLConnection.request(url, method, response, values,"Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6OCwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTYyODk1MjY4NSwiZXhwIjoxNjMxNTQ0Njg1fQ._P8S-wc1Ie7CINLSHXZaNH8ZK5GZ2b7yzO9tnN0t33Q"); // 해당 URL로 부터 결과물을 얻어온다.
            Log.d("체크", "result : " + result);
            return result;
        }

//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s); //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
//
//        }
    }
}




