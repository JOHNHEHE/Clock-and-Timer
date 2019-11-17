package com.example.asus.clock;

import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

/**
 * Created by ASUS on 2018/2/18.
 */

public class timer_fragment extends Fragment {
    long mRecordTime;
    boolean flag = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Chronometer mchronometer=getActivity().findViewById(R.id.chronometer);
        Button start =  getActivity().findViewById(R.id.start);
        Button pause =  getActivity().findViewById(R.id.pause);
        Button reset =  getActivity().findViewById(R.id.reset);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag){
                    flag = !flag;
                    if(mRecordTime != 0){
                        mchronometer.setBase(mchronometer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                    }else{
                        mchronometer.setBase(SystemClock.elapsedRealtime());
                    }
                    mchronometer.start();
                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    flag = !flag;
                    mchronometer.stop();
                    mRecordTime = SystemClock.elapsedRealtime();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mchronometer.stop();
                mchronometer.setBase(SystemClock.elapsedRealtime());
                mRecordTime = 0;
                flag = false;
            }
        });
    }
}
