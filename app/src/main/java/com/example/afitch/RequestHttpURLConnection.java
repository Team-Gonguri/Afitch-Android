package com.example.afitch;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import android.content.ContentValues;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

public class RequestHttpURLConnection {

    public JSONObject request(String _url, String method, Boolean response, JSONObject _params, String header) {

        JSONObject result = new JSONObject();
        // HttpURLConnection 참조 변수.
        HttpURLConnection urlConn = null;
        // URL 뒤에 붙여서 보낼 파라미터.
        StringBuffer sbParams = new StringBuffer();

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
                urlConn.setUseCaches(false);
                if (method.equals("post")) {
                 //   urlConn.setDoOutput(true);
                    urlConn.setRequestMethod("POST");// URL 요청에 대한 메소드 설정 :// POST.
                }
                else if (method.equals("get"))
                        urlConn.setRequestMethod("GET");
                if (header != null & !header.equals(""))
                    urlConn.setRequestProperty("Authorization", header);
                urlConn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                urlConn.setRequestProperty("Accept", "application/json; charset=utf-8");

                // post 방식
                if (method.equals("post")){
                    Log.d("체크","post 방식입니다.");
                    // [2-2]. parameter 전달 및 데이터 읽어오기.
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream()));
                    bw.write(_params.toString()); // 출력 스트림에 출력.
                    bw.flush(); // 출력 스트림을 플러시(비운다)하고 버퍼링 된 모든 출력 바이트를 강제 실행.
                    bw.close(); // 출력 스트림을 닫고 모든 시스템 자원을 해제.
                }

                // get 방식
                else if (method.equals("get")){
                      Log.d("체크","get 방식입니다.");
//                    boolean isAnd = false; // 파라미터 키와 값.
//                    String key;
//                    String value;
//                    Iterator<String> iter = _params.keys();
//                    while(iter.hasNext()) {
//                        key = iter.next();
//                        value = _params.get(key).toString();
//                        // 파라미터가 두개 이상일때, 파라미터 사이에 &를 붙인다.
//                        if (isAnd)
//                            sbParams.append("&");
//                        sbParams.append(key).append("=").append(value);
//
//                        // 파라미터가 2개 이상이면 isAnd를 true로 바꾸고 다음 루프부터 &를 붙인다.
//                        if (iter.hasNext())
//                            isAnd = true;
//                    }
//                    Log.d("sbparam" , String.valueOf(sbParams));
                }

                if (response == true) {
                    if (urlConn.getResponseCode() >= 200 & urlConn.getResponseCode() <=299) {
                        // [2-4]. 읽어온 결과물 리턴.
                        // 요청한 URL의 출력물을 BufferedReader로 받는다.
                        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), Charset.forName("UTF-8")));
                        // 출력물의 라인과 그 합에 대한 변수.
                        String line;
                        String page = "";
                        // 라인을 받아와 합친다.
                        while ((line = reader.readLine()) != null) {
                            page += line;
                        }
                        result.put("response", page);
                    }
                }
                result.put("status_code",urlConn.getResponseCode());
                return result;

            } catch (MalformedURLException e) { // for URL.
                Log.d("에러","잘못된 URL");
                e.printStackTrace();
            } catch (IOException e) { // for openConnection().
                Log.d("에러","네트워크 문제");
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e){
                Log.d("에러","기타에러");
                e.printStackTrace();
            } finally {
                if (urlConn != null)
                    urlConn.disconnect();
            }
            Log.d("체크","response 에러");
            return null;
    }
}
