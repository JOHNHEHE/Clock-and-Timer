package com.example.asus.clock;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Fragment> myFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter myFragmentAdapter;
    private ViewPager viewPager;
    private TextView alarm_clock,timer;
    private alarm_fragment Alarm_fragment;
    private timer_fragment Timer_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        myFragmentAdapter=new FragmentAdapter(this.getSupportFragmentManager(),myFragmentList);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(myFragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*此方法在页面被选中时调用*/
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，mma，2）。
                arg0 ==1的时辰默示正在滑动，
                arg0==2的时辰默示滑动完毕了，
                arg0==0的时辰默示什么都没做。*/
            }
        });
    }

    private void initview(){
        alarm_clock=findViewById(R.id.alarm_clock);
        timer=findViewById(R.id.timer);
        viewPager=findViewById(R.id.viewpager);
        timer.setOnClickListener(this);
        alarm_clock.setOnClickListener(this);
        Alarm_fragment=new alarm_fragment();
        Timer_fragment=new timer_fragment();
        myFragmentList.add(Alarm_fragment);
        myFragmentList.add(Timer_fragment);
        alarm_clock.setTextColor(Color.parseColor("#66CDAA"));
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.alarm_clock:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.timer:
                viewPager.setCurrentItem(1,true);
                break;
        }
    }
    public class FragmentAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragmentList=new ArrayList<Fragment>();

        FragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }
    private void changeTextColor(int position){
        if(position==0){
            alarm_clock.setTextColor(Color.parseColor("#66CDAA"));
            timer.setTextColor(Color.parseColor("#000000"));
        }
        if(position==1){
            alarm_clock.setTextColor(Color.parseColor("#000000"));
            timer.setTextColor(Color.parseColor("#66CDAA"));
        }
    }
}




