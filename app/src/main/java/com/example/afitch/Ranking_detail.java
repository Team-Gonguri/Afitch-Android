package com.example.afitch;

import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Ranking_detail extends Fragment {

    TextView[] comment = new TextView[3];
    TextView[] participation = new TextView[3];
    TextView commentAddBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_ranking_detail,
                container,
                false);

        comment[0] = (TextView) view.findViewById(R.id.commentAdd1);
        comment[1] = (TextView) view.findViewById(R.id.commentAdd1);
        comment[2] = (TextView) view.findViewById(R.id.commentAdd3);

        participation[0] = (TextView) view.findViewById(R.id.participationId1);
        participation[1] = (TextView) view.findViewById(R.id.participationId2);
        participation[2] = (TextView) view.findViewById(R.id.participationId3);

        commentAddBtn = view.findViewById(R.id.addComment);

        String exercise_num = "3";//화면전환시 바뀜 api 받아오기
        String url = "http://3.36.65.27:8080/exercises/1/participation/exericse_num/comment?authorities=ROLE_USER&id=" + exercise_num;

        JSONObject values = new JSONObject();

        try {

            values.getJSONObject("nickname");
            values.getJSONObject("text");

            Log.d("post", "plzz");
            System.out.println(values);


        } catch (JSONException e) {
            Log.d("Json", "fail");
            e.printStackTrace();
        }

//         AsyncTask를 통해 HttpURLConnection 수행.
        Ranking_detail.NetworkTask networkTask = new Ranking_detail.NetworkTask(url, "GET", true, values);
        networkTask.execute();

//
        for(int i=0;i<3;i++){
            participation[i].setText("hihi"+i);//get nickname api//옵셔널 사용해야함 ??
            comment[i].setText("bebe"+i);
        }

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
            result = requestHttpURLConnection.request(url, method, response, values,"Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6OCwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTYyODk1MjY4NSwiZXhwIjoxNjMxNTQ0Njg1fQ._P8S-wc1Ie7CINLSHXZaNH8ZK5GZ2b7yzO9tnN0t33Q"); // 해당 URL로 부터 결과물을 얻어온다.
            Log.d("체크", "result : " + result);
            return result;
        }
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s); //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
//
//        }
//    }

    }
}