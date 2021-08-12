package com.example.afitch;

import androidx.fragment.app.Fragment;

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
    TextView commentAddBtn ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_ranking_main,
                container,
                false);

        comment[0] = view.findViewById(R.id.comment1);
        comment[1] = view.findViewById(R.id.comment2);
        comment[2] = view.findViewById(R.id.comment3);

        participation[0] = view.findViewById(R.id.participationId1);
        participation[1] = view.findViewById(R.id.participationId2);
        participation[2] = view.findViewById(R.id.participationId3);

        commentAddBtn = view.findViewById(R.id.addComment);

        String exercise_num = "1";//화면전환시 바뀜
        String url = "http://3.36.65.27:8080/exercises/1/participation/exericse_num/comment?authorities=ROLE_USER&id="+exercise_num;

        JSONObject values = new JSONObject();


        try {

            values.getJSONObject("nickname");
            values.getJSONObject("text");

            Log.d("post","plzz");
            System.out.println(values);


        } catch (JSONException e) {
            Log.d("Json","fail");
            e.printStackTrace();
        }

//         AsyncTask를 통해 HttpURLConnection 수행.
        User_settings_edit.NetworkTask networkTask = new User_settings_edit.NetworkTask(url, values,"POST");
        networkTask.execute();

//
//        for(int i=0;i<3;i++){
//            participation[i].setText("hihi"+i);//get nickname api//옵셔널 사용해야함 ??
////            comment[i].setText("bebe"+i);
//        }

        return view;
    }

}