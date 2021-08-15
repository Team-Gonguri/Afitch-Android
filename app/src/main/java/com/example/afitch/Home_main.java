
package com.example.afitch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Home_main extends Fragment implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_home_main,
                container,
                false);
//        View whole = inflater.inflate(R.layout.whole_detail, null);
//        View arm = inflater.inflate(R.layout.arm_detail, null);
//        View leg = inflater.inflate(R.layout.leg_detail, null);
//        View abdominal = inflater.inflate(R.layout.abdominal_detail, null);
//        View chest = inflater.inflate(R.layout.chest_detail, null);
        LinearLayout whole = root.findViewById(R.id.whole_exercise);
        LinearLayout arm = root.findViewById(R.id.arm_exercise);
        LinearLayout leg = root.findViewById(R.id.leg_exercise);
        LinearLayout abdominal = root.findViewById(R.id.abdominal_exercise);
        LinearLayout chest = root.findViewById(R.id.chest_exercise);
        whole.setOnClickListener(this);
        arm.setOnClickListener(this);
        leg.setOnClickListener(this);
        abdominal.setOnClickListener(this);
        chest.setOnClickListener(this);

        return root;
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.whole_exercise:
                Log.d("클릭","전신운동");
                ((MainActivity)getActivity()).replaceFragment(new Whole_detail());
                break;

            case R.id.arm_exercise:
                Log.d("클릭","팔운동");
                ((MainActivity)getActivity()).replaceFragment(new Arm_detail());
                break;
            case R.id.leg_exercise:
                Log.d("클릭","다리운동");
                ((MainActivity)getActivity()).replaceFragment(new Leg_detail());
                break;
            case R.id.abdominal_exercise:
                Log.d("클릭","복부운동");
                ((MainActivity)getActivity()).replaceFragment(new Abdominal_detail());
                break;

            case R.id.chest_exercise:
                Log.d("클릭","엉덩이운동");
                ((MainActivity)getActivity()).replaceFragment(new Chest_detail());
                break;
        }
    }


}