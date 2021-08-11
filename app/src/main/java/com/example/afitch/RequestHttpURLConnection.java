package com.example.afitch;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestHttpURLConnection {

    public String request(String _url, JSONObject _params,String type) {

        // HttpURLConnection 참조 변수.
        HttpURLConnection urlConn = null;
        // URL 뒤에 붙여서 보낼 파라미터.
        //StringBuffer sbParams = new StringBuffer();

            // * 1. HttpURLConnection을 통해 web의 데이터를 가져온다.
            //* */
            try {
                URL url = new URL(_url);
                urlConn = (HttpURLConnection) url.openConnection();

                // [2-1]. urlConn 설정.
                Log.d("체크","2-1 urlConn 설정");
                urlConn.setConnectTimeout(15000);
                urlConn.setReadTimeout(5000);
                urlConn.setDoInput(true);
                urlConn.setDoOutput(true);
                urlConn.setUseCaches(false);
                urlConn.setRequestMethod(type);// URL 요청에 대한 메소드 설정 :// POST.
//                urlConn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
                urlConn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                urlConn.setRequestProperty("Accept", "application/json; charset=utf-8");


                // [2-2]. parameter 전달 및 데이터 읽어오기.
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream()));

                bw.write(_params.toString()); // 출력 스트림에 출력.
                bw.flush(); // 출력 스트림을 플러시(비운다)하고 버퍼링 된 모든 출력 바이트를 강제 실행.
                Log.d("체크","os.flush");
                bw.close(); // 출력 스트림을 닫고 모든 시스템 자원을 해제.

                // [2-3]. 연결 요청 확인.
                // 실패 시 null을 리턴하고 메서드를 종료.
                switch (urlConn.getResponseCode()){
                    case HttpURLConnection.HTTP_OK:
                        Log.d("체크","200");
                        break;
                    case HttpURLConnection.HTTP_CREATED:
                        Log.d("체크","201");
                        break;
                    default :
                        Log.d("체크", String.valueOf(urlConn.getResponseCode()));
                        return null;
                }

                // [2-4]. 읽어온 결과물 리턴.
                // 요청한 URL의 출력물을 BufferedReader로 받는다.
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));

                // 출력물의 라인과 그 합에 대한 변수.
                String line;
                String page = "";

                // 라인을 받아와 합친다.
                while ((line = reader.readLine()) != null) {
                    page += line;
                }
                Log.d("체크","page : " + page);
                return page;

            } catch (MalformedURLException e) { // for URL.
                e.printStackTrace();
            } catch (IOException e) { // for openConnection().
                e.printStackTrace();
            } finally {
                if (urlConn != null)
                    urlConn.disconnect();
            }
            Log.d("체크","response 에러");
            return null;
    }
}
