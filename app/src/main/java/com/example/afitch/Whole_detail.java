package com.example.afitch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class Whole_detail extends Fragment implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.whole_detail, container, false);
        LinearLayout whole_1 = root.findViewById(R.id.whole_1);
        LinearLayout whole_2 = root.findViewById(R.id.whole_2);
        LinearLayout whole_3= root.findViewById(R.id.whole_3);
        LinearLayout whole_4 = root.findViewById(R.id.whole_4);
        LinearLayout whole_5 = root.findViewById(R.id.whole_5);
        LinearLayout whole_6 = root.findViewById(R.id.whole_6);
        whole_1.setOnClickListener(this);
        whole_2.setOnClickListener(this);
        whole_3.setOnClickListener(this);
        whole_4.setOnClickListener(this);
        whole_5.setOnClickListener(this);
        whole_6.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.whole_1:
                Intent intent = new Intent(getActivity(), Video_preview.class);
                startActivity(intent);
                break;
        }
    }
}
