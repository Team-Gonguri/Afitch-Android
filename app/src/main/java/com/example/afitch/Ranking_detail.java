package com.example.afitch;

import static android.content.Context.MODE_PRIVATE;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ranking_detail extends Fragment  {

    TextView[] comment = new TextView[10];
    TextView[] participation = new TextView[10];
    TextView commentid,exerciseKind,commentNum;
    EditText addcomments;
    Button addcommentsBtn;
    ImageView detailLogo;
    String videourl,accessToken;
    VideoView videoView;
    int participationNum;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_ranking_detail,
                container,
                false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("file", MODE_PRIVATE);
        accessToken = sp.getString("accessToken",null);

        SharedPreferences page = this.getActivity().getSharedPreferences("page", MODE_PRIVATE);
        participationNum = page.getInt("page",0);//participation num

        commentid = (TextView) view.findViewById(R.id.commentid);
        exerciseKind = (TextView) view.findViewById(R.id.exerciseKind);
        commentNum = (TextView) view.findViewById(R.id.commentNum);

        videoView = (VideoView) view.findViewById(R.id.videoView2);

        comment[0] = (TextView) view.findViewById(R.id.commentAdd2);
        comment[1] = (TextView) view.findViewById(R.id.commentAdd3);
        comment[2] = (TextView) view.findViewById(R.id.commentAdd4);
        comment[3] = (TextView) view.findViewById(R.id.commentAdd5);
        comment[4] = (TextView) view.findViewById(R.id.commentAdd6);
        comment[5] = (TextView) view.findViewById(R.id.commentAdd7);
        comment[6] = (TextView) view.findViewById(R.id.commentAdd8);
        comment[7] = (TextView) view.findViewById(R.id.commentAdd9);
        comment[8] = (TextView) view.findViewById(R.id.commentAdd10);
        comment[9] = (TextView) view.findViewById(R.id.commentAdd11);


        participation[0] = (TextView) view.findViewById(R.id.participationId3);
        participation[1] = (TextView) view.findViewById(R.id.participationId3);
        participation[2] = (TextView) view.findViewById(R.id.participationId4);
        participation[3] = (TextView) view.findViewById(R.id.participationId5);
        participation[4] = (TextView) view.findViewById(R.id.participationId6);
        participation[5] = (TextView) view.findViewById(R.id.participationId7);
        participation[6] = (TextView) view.findViewById(R.id.participationId8);
        participation[7] = (TextView) view.findViewById(R.id.participationId9);
        participation[8] = (TextView) view.findViewById(R.id.participationId10);
        participation[9] = (TextView) view.findViewById(R.id.participation11);

        //input comments
        int exerciseNum = 3;
        String addcommenturl = "http://3.36.65.27:8080/exercises/"+exerciseNum+"/participation/"+participationNum+"/comment?authorities=ROLE_USER";
        JSONObject comments = new JSONObject();
        addcomments = (EditText) view.findViewById(R.id.addcomments);
        addcommentsBtn = view.findViewById(R.id.addcommentsBtn);
        addcommentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    comments.put("text", addcomments);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NetworkTask networkTask = new NetworkTask(addcommenturl, "post", true, comments, 3);
                networkTask.execute();

            }
        });

        //go to home
//        Intent intent = new Intent(getActivity(),Home_main.class);
//
//        detailLogo = (ImageView) view.findViewById(R.id.detailLogo);
//        detailLogo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(intent);
//            }
//        });


        String url = "http://3.36.65.27:8080/exercises/"+exerciseNum+"/participation/"+participationNum+"?authorities=ROLE_USER";
        JSONObject values = new JSONObject();

        Ranking_detail.NetworkTask networkTask = new Ranking_detail.NetworkTask(url, "get", true, values,1);
        networkTask.execute();

        return view;
    }

    public class NetworkTask extends AsyncTask<Void, Void, JSONObject> {
        private String url, method;
        private JSONObject values;
        private int num;
        Boolean response;

        public NetworkTask(String url, String method, Boolean response, JSONObject values,int num) {
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
            result = requestHttpURLConnection.request(url, method, response, values,"Bearer "+accessToken); // 해당 URL로 부터 결과물을 얻어온다.
            Log.d("체크", "result : " + result);
            return result;
        }
        @Override
        protected void onPostExecute(JSONObject s) {
            Log.d("tag", "hello case onPostExecute");


            super.onPostExecute(s);
            try {
                if (s.getString("status_code").equals("200")) {

                    switch (num) {
                        case 1:
                            Log.d("tag", "get data");

                            String response = s.getString("response");

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray lists = jsonObject.getJSONArray("comments");
                            String exerciseName = jsonObject.getString("exerciseName");
                            String userName = jsonObject.getString("nickName");
                            int commentnum = jsonObject.getInt("commentNum");
                            videourl = jsonObject.getString("url");
                            Log.d("url", videourl);

                            //url + accesstoken
//                            JSONObject v = new JSONObject();
//                            NetworkTask networkTask = new NetworkTask(videourl, "get", true, v, 2);
//                            networkTask.execute();
//                            Log.d("video", "execute");

                            commentid.setText(userName);
                            commentNum.setText(Integer.toString(commentnum));
                            exerciseKind.setText(exerciseName);


                            for (int i = 0; i < 10; i++) {
                                JSONObject obj = lists.getJSONObject(i);
                                String name = obj.getString("nickName");
                                String text = obj.getString("text");
                                try {
                                    participation[i].setText(name + " : ");
                                    comment[i].setText(text);

                                    videoView.setVideoURI(Uri.parse("file://"+videourl));
//                                    videoView.setVideoURI(Uri.parse(videourl));

                                    videoView.setMediaController(new MediaController(getActivity()));
                                    videoView.requestFocus();


                                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                        @Override
                                        public void onPrepared(MediaPlayer mp) {
                                            videoView.seekTo(0);
                                            videoView.start();
                                        }

                                    });



                                } catch (NullPointerException e) {

                                }

                            }

                            break;

                        case 2:

                            Log.d("video", "onPostExecute");
                            Log.d("video", s.toString());


                            break;

                        case 3:
                            Log.d("tag", "hello case 3");

                            try {
                                if (s.getString("status_code").equals("200")) {
                                    Log.d("tag", "add ok");
                                }
                            } catch (JSONException k) {
                                k.printStackTrace();
                            }
                            break;


                    }
                }
            }catch(JSONException e) {
                e.printStackTrace();
            }


        }

    }
}