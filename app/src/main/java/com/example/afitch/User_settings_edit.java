package com.example.afitch;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

public class User_settings_edit extends Fragment {
    public Button gotoSave;
    public EditText weightEdit, heightEdit, yearEdit;
    public TextView settingsId;
    String weight, height, accessToken,savenickname;
    int saveheight,saveweight;
    JSONObject name = new JSONObject();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_settings_edit,
                container,
                false);
        //accessToken
        SharedPreferences sp = this.getActivity().getSharedPreferences("file", MODE_PRIVATE);
        accessToken = sp.getString("accessToken",null);

        SharedPreferences info = this.getActivity().getSharedPreferences("info", MODE_PRIVATE);
        savenickname = info.getString("nickname",null);
        saveweight = info.getInt("weight", 0);
        saveheight = info.getInt("height", 0);
        System.out.println(saveweight);


        //id weight height loading ...
        settingsId = (TextView) view.findViewById(R.id.settingsId);
        weightEdit = (EditText) view.findViewById(R.id.weightHint);
        heightEdit = (EditText) view.findViewById(R.id.heightHint);

        settingsId.setText(savenickname);
        heightEdit.setHint(Integer.toString(saveheight));
        weightEdit.setHint(Integer.toString(saveweight));

        //weight height save
        gotoSave = (Button) view.findViewById(R.id.gotoSave);
        gotoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight = weightEdit.getText().toString();
                height = heightEdit.getText().toString();

                String url = "http://3.36.65.27:8080/user?authorities=ROLE_USER";
                JSONObject edit = new JSONObject();
                try {
                    edit.put("nickname", savenickname);
                    edit.put("weight", weight);
                    edit.put("height", height);
                    System.out.println(edit);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // AsyncTask를 통해 HttpURLConnection 수행.
                NetworkTask networkTask = new NetworkTask(url, "put", true, edit, 2); //height weight change
                networkTask.execute();

                //화면 전환
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onFragmentChanged(1);

            }
        });

        //edit nickname
        Button nicknameBtn = (Button) view.findViewById(R.id.nicknameBtn);
        final EditText nicknameEdit = new EditText(getContext());
        nicknameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("change nickname");
                builder.setMessage("plzzzzzzzzzz");


                builder.setView(nicknameEdit);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String value = nicknameEdit.getText().toString();

                        String url = "http://3.36.65.27:8080/user?authorities=ROLE_USER";
                        JSONObject changename = new JSONObject();
                        try {

                            changename.put("nickName", value);
                            changename.put("weight",saveweight);
                            changename.put("height",saveheight);
                            System.out.println(changename);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // AsyncTask를 통해 HttpURLConnection 수행.
                        NetworkTask networkTask = new NetworkTask(url, "put", true, changename, 2); //edit name
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
        private int num;


        public NetworkTask(String url, String method, Boolean response, JSONObject values, int num) {
            this.url = url;
            this.method = method;
            this.values = values;
            this.response = response;
            this.num = num;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject result;
            Log.d("체크", "doInBackground 진입");
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, method, response, values, "Bearer "+accessToken); // 해당 URL로 부터 결과물을 얻어온다.
            Log.d("체크", "result : " + result);
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s); //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.

            switch (num) {
                case 1:

                    try {
                        if (s.getString("status_code").equals("200")) {
                            System.out.println("add ok");
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }


                    break;
                case 2: //edit nickname
                    try {
                        if (s.getString("status_code").equals("200")) {
                            System.out.println("add ok");
                        }else{
                            Log.d("tag", "fail");
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }

                    break;



//                case 3:
//                    try {
//                        if (s.getString("status_code").equals("200")) {
//
//                            String response = s.getString("response");
//                            JSONObject jsonObject = new JSONObject(response);
//                            String nickname = jsonObject.getString("nickName");
//                            int hintheight = jsonObject.getInt("height");
//                            int hintweight = jsonObject.getInt("weight");
//                            System.out.println(hintheight + " " +hintweight);
//
//
//                            settingsId.setText(nickname);
//                            heightEdit.setHint(Integer.toString(hintheight));
//                            weightEdit.setHint(Integer.toString(hintweight));
//
//
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    break;


            }
        }
    }
}


