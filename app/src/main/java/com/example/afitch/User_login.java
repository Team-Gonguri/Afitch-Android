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

import org.w3c.dom.Text;

public class User_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // logo 에서 I 만 색깔 변경
        TextView logo = (TextView)findViewById(R.id.a_fitch);
        String content = logo.getText().toString();
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF9595")), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logo.setText(spannableString);

        TextView goto_signup = (TextView) findViewById(R.id.goto_signup);

        goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),User_signUp.class);
                startActivity(intent);

            }
        });
    }


}