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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Ranking_main extends Fragment {

    TextView myranking,myscore;
    TextView[] view_nickname = new TextView[11];
    TextView[] view_score = new TextView[11];
    ImageButton[] gotoBtn = new ImageButton[11];


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


        String exercise_num = "1";
        String url = "http://3.36.65.27:8080/exercises/"+exercise_num+"/participation/list?order=RANKING";

        JSONObject values = new JSONObject();

        User_settings_edit.NetworkTask networkTask = new User_settings_edit.NetworkTask(url, values,"GET");
        networkTask.execute();

        try {

            values.getJSONObject("list").getString("id");
            Log.d("get","plzz");
            System.out.println(values);


        } catch (JSONException e) {
            Log.d("Json","fail");
            e.printStackTrace();
        }

        // AsyncTask를 통해 HttpURLConnection 수행.
//        User_settings_edit.NetworkTask networkTask = new User_settings_edit.NetworkTask(url, values);
//        networkTask.execute();



        for(int i=1;i<=10;i++){
            view_nickname[i].setText("hihi"+i);//get nickname api//옵셔널 사용해야함 ??
        }


        return view;



    }

}

