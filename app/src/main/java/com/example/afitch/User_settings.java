package com.example.afitch;

import static org.webrtc.ContextUtils.getApplicationContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class User_settings extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_settings,
                container,
                false);
        TextView editBtn= (TextView) view.findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onFragmentChanged(0);

            }
        });

        TextView feedbackBtn = (TextView) view.findViewById(R.id.feedbackBtn);
        feedbackBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("feedback","feedbackplz");
            }
        });


        return view;
    }
}

//
//        @Override
//        public void onClick (View view){
//        switch (view.getId()) {
//            case R.id.feedbackBtn:
//                Log.d("mytag", "feedback");
//                break;
//            case R.id.editBtn:
//                //edit Btn click
//                Intent intent = new Intent(getApplicationContext(), User_settings_edit.class);
//                startActivity(intent);
//                break;
//            case R.id.musicBtn:
//                Intent intent2 = new Intent(getApplicationContext(), User_settings_edit.class);
//                startActivity(intent2);
//                break;
//            case R.id.login_btn:
//                Intent intent3 = new Intent(getApplicationContext(), User_settings_edit.class);
//                startActivity(intent3);
//                break;
//
//        }
//
//    }
//    }

//    // feedback popup
//
//    @Override
//    public void onClick(View view) {
//         switch (view.getId()){
//             case R.id.feedbackBtn:
//                 Intent intent = new Intent(getBaseContext(), PopupActivity.class);
//                 intent.putExtra("type", PopupType.NORMAL);
//                 intent.putExtra("gravity", PopupGravity.CENTER);
//                 intent.putExtra("title", "공지사항");
//                 intent.putExtra("content", "Popup Activity was made by Lakue");
//                 intent.putExtra("buttonCenter", "종료");
//                 startActivityForResult(intent, 1);
//
//         }
//
////    });
//}