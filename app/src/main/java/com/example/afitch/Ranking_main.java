package com.example.afitch;

import androidx.fragment.app.Fragment;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Ranking_main extends Fragment {

    TextView myranking, myscore;
    TextView[] view_nickname = new TextView[11];
    TextView[] view_score = new TextView[11];
    ImageButton[] gotoBtn = new ImageButton[11];
    String exerciseid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_ranking_main,
                container,
                false);

        myranking = (TextView) view.findViewById(R.id.myranking);
        myscore = (TextView) view.findViewById(R.id.myscore);

        //nickname TextView
        view_nickname[1] = (TextView) view.findViewById(R.id.view_nickname1);
        view_nickname[2] = (TextView) view.findViewById(R.id.view_nickname2);
        view_nickname[3] = (TextView) view.findViewById(R.id.view_nickname3);
        view_nickname[4] = (TextView) view.findViewById(R.id.view_nickname4);
        view_nickname[5] = (TextView) view.findViewById(R.id.view_nickname5);
        view_nickname[6] = (TextView) view.findViewById(R.id.view_nickname6);
        view_nickname[7] = (TextView) view.findViewById(R.id.view_nickname7);
        view_nickname[8] = (TextView) view.findViewById(R.id.view_nickname8);
        view_nickname[9] = (TextView) view.findViewById(R.id.view_nickname9);
        view_nickname[10] = (TextView) view.findViewById(R.id.view_nickname10);

        //score TextView
        view_score[1] = (TextView) view.findViewById(R.id.view_score);
        view_score[2] = (TextView) view.findViewById(R.id.view_score2);
        view_score[3] = (TextView) view.findViewById(R.id.view_score3);
        view_score[4] = (TextView) view.findViewById(R.id.view_score4);
        view_score[5] = (TextView) view.findViewById(R.id.view_score5);
        view_score[6] = (TextView) view.findViewById(R.id.view_score6);
        view_score[7] = (TextView) view.findViewById(R.id.view_score7);
        view_score[8] = (TextView) view.findViewById(R.id.view_score8);
        view_score[9] = (TextView) view.findViewById(R.id.view_score9);
        view_score[10] = (TextView) view.findViewById(R.id.view_score10);

        //gotoBtn
        gotoBtn[1] = (ImageButton) view.findViewById(R.id.imageButton10);
        gotoBtn[2] = (ImageButton) view.findViewById(R.id.imageButton11);
        gotoBtn[3] = (ImageButton) view.findViewById(R.id.imageButton12);
        gotoBtn[4] = (ImageButton) view.findViewById(R.id.imageButton13);
        gotoBtn[5] = (ImageButton) view.findViewById(R.id.imageButton14);
        gotoBtn[6] = (ImageButton) view.findViewById(R.id.imageButton15);
        gotoBtn[7] = (ImageButton) view.findViewById(R.id.imageButton16);
        gotoBtn[8] = (ImageButton) view.findViewById(R.id.imageButton17);
        gotoBtn[9] = (ImageButton) view.findViewById(R.id.imageButton18);
        gotoBtn[10] = (ImageButton) view.findViewById(R.id.imageButton19);


        gotoBtn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("hi", "btn click");
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onFragmentChanged(2);

            }
        });

        exerciseid = "3";
        String url = "http://3.36.65.27:8080/exercises/" + exerciseid + "/participation/list?order=RANKING";

        JSONObject ids = new JSONObject();

        Ranking_main.NetworkTask networkTask = new Ranking_main.NetworkTask(url, "POST", true, ids);
        networkTask.execute();


        String idid = null;
        System.out.println(ids);


        //http connection  -> JsonObject -> setTex


        return view;

    }

    public class NetworkTask extends AsyncTask<Void, Void, JSONObject> {
        private String url, method;
        private JSONObject values;
        Boolean response;

        public NetworkTask(String url, String method, Boolean response, JSONObject values) {
            this.url = url;
            this.method = method;
            this.values = values;
            this.response = response;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject result;
            Log.d("체크", "doInBackground 진입");
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, method, response, values, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6OCwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTYyODk1MjY4NSwiZXhwIjoxNjMxNTQ0Njg1fQ._P8S-wc1Ie7CINLSHXZaNH8ZK5GZ2b7yzO9tnN0t33Q"); // 해당 URL로 부터 결과물을 얻어온다.
            Log.d("access token ", "result : " + result);
            return result;
        }

        //      @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s); //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            System.out.println("!!!!" + s);
            System.out.println(s.getClass().getName());

            try {
                if (s.getString("status_code").equals("200")) {
                    String response = s.getString("response");

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray lists = jsonObject.getJSONArray("lists");

                    for (int i = 0; i < lists.length(); i++) {
                        JSONObject obj = lists.getJSONObject(i);
                        String name = obj.getString("userName");
                        try {
                            view_nickname[i+1].setText(name);//get nickname api//옵셔널 사용해야함 ??
                        }catch (NullPointerException e){

                        }

                        System.out.println("name(" + i + "): " + name);
                        System.out.println();
                    }

                }


//
//          try {

//                JSONObject obj = new JSONObject(s);
//
//                JSONObject a = obj.getJSONObject("response");
//////                String a = s.getJSONObject("response").toString();
////
////                System.out.println(a);
//////                JSONArray b = a.getJSONArray("lists");
//                JSONObject b = a.getJSONObject("lists");
////                System.out.println("hihi");
//
//                JSONArray c = b.getJSONArray();
//                System.out.println("hihi");


//                try{
//
//                    JSONObject response = s.getJSONObject("response");
//                    JSONArray lists = response.getJSONArray("lists");
//
//                    for(int i=0; i<lists.length(); i++)
//                    {
//                        JSONObject obj = lists.getJSONObject(i);
//
//                        String sibal = obj.getString("userName");
//                        System.out.println(sibal);
//
//                    }
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }


//                for(int i=1;i<=10;i++){ //여기다 api 불러오기
//                    Log.d("hi", "textview");
//                    view_nickname[i].setText(idid);//get nickname api//옵셔널 사용해야함 ??
//                }


//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


