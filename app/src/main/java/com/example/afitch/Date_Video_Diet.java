package com.example.afitch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Date_Video_Diet extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_date_video_diet,
                container,
                false);

        LinearLayout layout = rootView.findViewById(R.id.input_exercise);
        for (int i = 0; i< 5; i++) {

            // textview 동적 추가
            TextView text = new TextView(getContext());
            text.setText("스쿼트");
            text.setTextSize(20);
            text.setTextColor(Color.parseColor("#FFFFFF"));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 70;
            layoutParams.gravity = Gravity.CENTER;
            text.setLayoutParams(layoutParams);
            layout.addView(text);

            //imageview 동적 추가
            Button img = new Button(getContext());
            img.setBackgroundResource(R.drawable.play2);
            layoutParams = new LinearLayout.LayoutParams(100,100);
            layoutParams.gravity = Gravity.CENTER;
            img.setLayoutParams(layoutParams);
            layout.addView(img);

        }

        return rootView;

    }
}