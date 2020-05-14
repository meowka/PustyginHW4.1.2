package com.example.hw412;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final String TEXT_1 = "text1";
    final String TEXT_2 = "text2";
    ListView lvSimple;
    SharedPreferences sharedPreferences;
    private final String APP_PREFERENCES = "largeTxt";
    private final String LARGE_TEXT = "largeText";
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains(LARGE_TEXT)){
        String [] arrayContent = sharedPreferences.getString(LARGE_TEXT, "").split("\n\n");
            final List<Map<String, String>>list = new ArrayList<>();
            Map<String, String>mapList;
            for (int i = 0; i <arrayContent.length ; i++) {
                mapList = new HashMap<>();
                mapList.put(TEXT_1,arrayContent[i]);
                mapList.put(TEXT_2, String.valueOf(arrayContent[i].length()));
                list.add(mapList);
            }
        }

        swipeRefreshLayout = findViewById(R.id.srl_container);

        editor.putString(LARGE_TEXT,  getString(R.string.large_text));
        editor.apply();
        String[] arrayContent = sharedPreferences.getString(LARGE_TEXT,"").split("\n\n");
        final List<Map<String, String>>list = new ArrayList<>();
        Map<String, String>mapList;
        for (int i = 0; i <arrayContent.length ; i++) {
            mapList = new HashMap<>();
            mapList.put(TEXT_1,arrayContent[i]);
            mapList.put(TEXT_2, String.valueOf(arrayContent[i].length()));
            list.add(mapList);
        }

        String [] from = {TEXT_1, TEXT_2};
        int[] to = {R.id.textView, R.id.textView2};
        final SimpleAdapter sAdapter = new SimpleAdapter(this, list, R.layout.item,
                from, to);
        lvSimple = (ListView) findViewById(R.id.list);

        lvSimple.setAdapter(sAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        lvSimple.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                list.remove(i);
                sAdapter.notifyDataSetChanged();
            }
        });
    }




//    @NonNull
//    private BaseAdapter createAdapter(List<Map<String, String>> values) {
//        String [] from = {TEXT_1, TEXT_2};
//        int[] to = {R.id.textView, R.id.textView2};
//        SimpleAdapter sAdapter = new SimpleAdapter(this, values, R.layout.item,
//                from, to);
//        lvSimple = (ListView) findViewById(R.id.list);
//        return sAdapter;
//    }
//
//    @NonNull
//    private List<Map<String, String>> prepareContent() {
//        String[] arrayContent = getString(R.string.large_text).split("\n\n");
//        List<Map<String, String>>list = new ArrayList<>();
//        Map<String, String>mapList;
//        for (int i = 0; i <arrayContent.length ; i++) {
//            mapList = new HashMap<>();
//            mapList.put(TEXT_1,arrayContent[i]);
//            mapList.put(TEXT_2, String.valueOf(arrayContent[i].length()));
//            list.add(mapList);
//        }
//        return list;
//    }
}
