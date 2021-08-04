package com.example.afitch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

public class Food_add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);

            NumberPicker picker1 = (NumberPicker)findViewById(R.id.picker1);
            picker1.setMinValue(0);
            picker1.setMaxValue(10);
            picker1.setWrapSelectorWheel(false);


    }
}