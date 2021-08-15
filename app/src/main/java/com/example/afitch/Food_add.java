package com.example.afitch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Food_add extends Fragment {

    public TextView Food1, Food2, Food3, Food4, Food5, Food6, Food7, Food8, Food9, Food10;
    public TextView Calories1;
    public EditText Search_Food, Page_number;
    public Button Search_btn;
    String Food_Name, Page_N, Food_Search_url, Page_Search_url, Food;
    JSONObject temp, Food_keyword, Food_page;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = (View) inflater.inflate(R.layout.activity_food_add, container, false);

        Search_Food = (EditText) view.findViewById(R.id.search_food);
        Search_btn = (Button) view.findViewById(R.id.search_food_btn);


        Food1 = (TextView) view.findViewById(R.id.text_food_1);
        Food2 = (TextView) view.findViewById(R.id.text_food_2);
        Food3 = (TextView) view.findViewById(R.id.text_food_3);
        Food4 = (TextView) view.findViewById(R.id.text_food_4);
        Food5 = (TextView) view.findViewById(R.id.text_food_5);
        Food6 = (TextView) view.findViewById(R.id.text_food_6);
        Food7 = (TextView) view.findViewById(R.id.text_food_7);
        Food8 = (TextView) view.findViewById(R.id.text_food_8);
        Food9 = (TextView) view.findViewById(R.id.text_food_9);
        Food10 = (TextView) view.findViewById(R.id.text_food_10);

        Calories1 = (TextView) view.findViewById(R.id.calories_1);




        Search_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("체크","음식검색 버튼 클릭확인");
                Food_Name = Search_Food.getText().toString();
                Page_N = "1";


                Food_Search_url = "http://3.36.65.27:8080/food/search?keyword="+Food_Name+"&page="+Page_N;
                Food_keyword = new JSONObject();
                Food_page = new JSONObject();

                try {
                    Food_keyword.put("keyword",Food_Name);
                    Food_page.put("page",Page_N);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                // AsyncTask를 통해 HttpURLConnection 수행.
                NetworkTask networkTask1 = new NetworkTask(Food_Search_url, "get", true, Food_keyword, 1);
                networkTask1.execute();
                NetworkTask networkTask2 = new NetworkTask(Food_Search_url, "get", true, Food_page, 2);
                networkTask2.execute();

                System.out.println(temp);

            }


        });




        //음식 생성 버튼
        Button make_food_btn = (Button) view.findViewById(R.id.enter_make_food);
        make_food_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onFragmentChanged(12);
            }
        });

        //바코드 인식 + 아이콘 색상 수정
        ImageView barcode_img = (ImageView) view.findViewById(R.id.image_barcode);
        ImageView searchicon_img = (ImageView) view.findViewById(R.id.search_icon);

        int color = ContextCompat.getColor(getActivity(), R.color.white);
        barcode_img.setColorFilter(color);
        searchicon_img.setColorFilter(color);

        barcode_img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                IntentIntegrator qrScan = new IntentIntegrator(getActivity());
                qrScan.setOrientationLocked(false);
                qrScan.setPrompt("스캔하는중...");
                qrScan.initiateScan();
            }
        });

        //음식 수량 넘버피커
        NumberPicker picker1 = (NumberPicker)view.findViewById(R.id.picker1);
        picker1.setMinValue(0);
        picker1.setMaxValue(10);
        picker1.setWrapSelectorWheel(false);

        NumberPicker picker2 = (NumberPicker)view.findViewById(R.id.picker2);
        picker2.setMinValue(0);
        picker2.setMaxValue(10);
        picker2.setWrapSelectorWheel(false);

        NumberPicker picker3 = (NumberPicker)view.findViewById(R.id.picker3);
        picker3.setMinValue(0);
        picker3.setMaxValue(10);
        picker3.setWrapSelectorWheel(false);

        NumberPicker picker4 = (NumberPicker)view.findViewById(R.id.picker4);
        picker4.setMinValue(0);
        picker4.setMaxValue(10);
        picker4.setWrapSelectorWheel(false);

        NumberPicker picker5 = (NumberPicker)view.findViewById(R.id.picker5);
        picker5.setMinValue(0);
        picker5.setMaxValue(10);
        picker5.setWrapSelectorWheel(false);

        NumberPicker picker6 = (NumberPicker)view.findViewById(R.id.picker6);
        picker6.setMinValue(0);
        picker6.setMaxValue(10);
        picker6.setWrapSelectorWheel(false);

        NumberPicker picker7 = (NumberPicker)view.findViewById(R.id.picker7);
        picker7.setMinValue(0);
        picker7.setMaxValue(10);
        picker7.setWrapSelectorWheel(false);

        NumberPicker picker8 = (NumberPicker)view.findViewById(R.id.picker8);
        picker8.setMinValue(0);
        picker8.setMaxValue(10);
        picker8.setWrapSelectorWheel(false);

        NumberPicker picker9 = (NumberPicker)view.findViewById(R.id.picker9);
        picker9.setMinValue(0);
        picker9.setMaxValue(10);
        picker9.setWrapSelectorWheel(false);

        NumberPicker picker10 = (NumberPicker)view.findViewById(R.id.picker10);
        picker10.setMinValue(0);
        picker10.setMaxValue(10);
        picker10.setWrapSelectorWheel(false);


        return view;

    }

    public class NetworkTask extends AsyncTask<Void, Void, JSONObject> {
        private String url, method;
        private JSONObject values;
        private int num;
        Boolean response;

        public NetworkTask(String url,String method, Boolean response, JSONObject values, int num) {
            this.url = url;
            Log.d("url", url);
            this.method = method;
            this.values = values;
            this.response = response;
            this.num = num;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject result;
            Log.d("체크","doInBackground 진입");
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, method, response, values, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6Nywicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTYyODk1MjY3MCwiZXhwIjoxNjMxNTQ0NjcwfQ.IkxV3xT6pNoopxE0MIFW3AeCnKQH5fEqRNzVg5C4gS8"); // 해당 URL로 부터 결과물을 얻어온다.
            Log.d("체크", "result : " + result);
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s); //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            temp = s;
            System.out.println(temp);

            try {
                if (s.getString("status_code").equals("200")){
                    Food = s.getString("response");

                    JSONObject jsonObject = new JSONObject(Food);
                    JSONArray foodArray = jsonObject.getJSONArray("foods");


                    JSONObject obj1 = foodArray.getJSONObject(0);
                    String name1 = obj1.getString("name");
                    Food1.setText(name1);

                    JSONObject obj2 = foodArray.getJSONObject(1);
                    String name2 = obj2.getString("name");
                    Food2.setText(name2);

                    JSONObject obj3 = foodArray.getJSONObject(2);
                    String name3 = obj3.getString("name");
                    Food3.setText(name3);

                    JSONObject obj4 = foodArray.getJSONObject(3);
                    String name4 = obj4.getString("name");
                    Food4.setText(name4);

                    JSONObject obj5 = foodArray.getJSONObject(4);
                    String name5 = obj5.getString("name");
                    Food5.setText(name5);

                    JSONObject obj6 = foodArray.getJSONObject(5);
                    String name6 = obj6.getString("name");
                    Food6.setText(name6);

                    JSONObject obj7 = foodArray.getJSONObject(6);
                    String name7 = obj7.getString("name");
                    Food7.setText(name7);

                    JSONObject obj8 = foodArray.getJSONObject(7);
                    String name8 = obj8.getString("name");
                    Food8.setText(name8);

                    JSONObject obj9 = foodArray.getJSONObject(8);
                    String name9 = obj9.getString("name");
                    Food9.setText(name9);

                    JSONObject obj10 = foodArray.getJSONObject(9);
                    String name10 = obj10.getString("name");
                    Food10.setText(name10);



                }
                else{
                    Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),"등록된 음식이 없습니다.", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}