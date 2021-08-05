package com.example.afitch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

public class User_signUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);

        // logo 에서 I 만 색깔 변경
        TextView logo = (TextView)findViewById(R.id.a_fitch2);
        String content = logo.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF9595")), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logo.setText(spannableString);

        TextView goto_login = (TextView) findViewById(R.id.goto_login);

        goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),User_login.class);
                startActivity(intent);



            }
        });
    }
}