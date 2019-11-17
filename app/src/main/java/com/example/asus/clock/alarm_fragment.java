package com.example.asus.clock;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS on 2018/2/18.
 */

public class alarm_fragment extends Fragment {
    private ListView listView;
    private SimpleCursorAdapter cursorAdapter;
    private DataBaseOperator dbOpeater;
    private Cursor mCursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        listView=getActivity().findViewById(R.id.listView);
        Button iButton=getActivity().findViewById(R.id.add_button);
        dbOpeater = new DataBaseOperator(getActivity());//数据库对象
        iButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),AlarmEditActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), AlarmEditActivity.class);
                    TextView modify_time = view.findViewById(R.id.alarm_time);
                    TextView note=view.findViewById(R.id.alarm_note);
                    intent.putExtra("time", modify_time.getText());
                    intent.putExtra("position", position+1 );
                    intent.putExtra("notes",note.getText());
                    startActivity(intent);
            }
        });
       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(getActivity(),DeleteAlarmActivity.class);
               startActivity(intent);
               return true;
           }
       });
    }

    @Override
    public void onResume() {
        super.onResume();
        mCursor=dbOpeater.query(MyDataBaseHelper.ALARM_TB_NAME);
        String [] colums = {MyDataBaseHelper.COL_TIME,MyDataBaseHelper.COL_ALARM_NOTE,MyDataBaseHelper.COL_ALARM_STATUS};
        int[] layoutsId = {R.id.alarm_time,R.id.alarm_note,R.id.alarm_status};
        cursorAdapter=new SimpleCursorAdapter(getActivity(),R.layout.item,mCursor,colums,layoutsId, CursorAdapter.FLAG_AUTO_REQUERY);
        listView.setAdapter(cursorAdapter);
    }

    @Override
    public void onStop() {
        mCursor.close();
        super.onStop();
    }


}
