package com.example.afitch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavi);

        getSupportFragmentManager().beginTransaction().add(R.id.main_frame, new Home_main()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) { //item을 클릭시 id값을 가져와 FrameLayout에 fragment.xml띄우기
                    case R.id.fragment1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Home_main()).commit();
                        break;
                    case R.id.fragment2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Food_Main()).commit();
                        break;
                    case R.id.fragment3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Date_Video_Diet()).commit();
                        break;
                    case R.id.fragment4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new Ranking_main()).commit();//ranking view
                        break;
                }
                return true;
            }
        });
    }

    public void onFragmentChanged(int index) {
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new User_settings_edit()).commit();
        }
        if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new User_settings()).commit();
        }
        if(index == 2){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,new Ranking_detail()).commit();
        }

    }

}