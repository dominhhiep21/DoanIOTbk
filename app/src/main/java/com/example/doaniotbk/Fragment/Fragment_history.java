package com.example.doaniotbk.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doaniotbk.Adapter.ItemAdapter;
import com.example.doaniotbk.Dal.SQLiteHelper;
import com.example.doaniotbk.Model.Item;
import com.example.doaniotbk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Fragment_history extends Fragment implements ItemAdapter.ItemListener {
    private SQLiteHelper sqLiteHelper;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private Handler handler;
    private int temperature_Data;
    private float humidity_Data;
    private TextView tv_humiditySelectedItem,tv_temperatureSelectedItem,tv_timeSelectedItem;
    private ImageView statusSelectedItem;
    private FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        adapter = new ItemAdapter(getContext());
        sqLiteHelper = new SQLiteHelper(getContext(),"DoanIOT.db",null,1);
        //sqLiteHelper.deleteAll();
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                if(second==0){
                    generateData();
                }
                //Toast.makeText(getContext(), ""+second, Toast.LENGTH_SHORT).show();
                List<Item> data = sqLiteHelper.getAll();
                adapter.setData(data);

                handler.sendEmptyMessageDelayed(0,1000);
            }
        };
        handler.sendEmptyMessage(0);


        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }


    private void generateData() {
        final Calendar calendar = Calendar.getInstance();
        int count = 0;
        Item item = new Item();
        int img,time;
        time = calendar.get(Calendar.MINUTE);
        database = FirebaseDatabase.getInstance();
        DatabaseReference temperature_Ref = database.getReference("data/temperature");
        temperature_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temperature_Data = snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lay du lieu nhiet do that bai", Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference humidity_Ref = database.getReference("data/humidity");
        humidity_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                humidity_Data = snapshot.getValue(Float.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lay du lieu do am that bai", Toast.LENGTH_SHORT).show();
            }
        });
        if(temperature_Data>32){
            img = R.drawable.hot;
        }else if(temperature_Data<=32&&temperature_Data>20){
            img = R.drawable.cool;
        }else {
            img = R.drawable.cold;
        }
        item = new Item(img,time,temperature_Data,humidity_Data);
        sqLiteHelper = new SQLiteHelper(getContext(),"data",null,1);
        if(count==0){
            sqLiteHelper.addItem(item);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Xử lý ngoại lệ nếu có
            }
            count++;
        }
        List<Item> data = sqLiteHelper.getAll();
        if (data.size() > 12) {
            sqLiteHelper.delete(data.get(0));
        }
    }


    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle_view);
        tv_humiditySelectedItem = view.findViewById(R.id.humidity_selected_item);
        tv_temperatureSelectedItem = view.findViewById(R.id.temperature_selected_item);
        tv_timeSelectedItem = view.findViewById(R.id.time_selected_item);
        statusSelectedItem = view.findViewById(R.id.status_selected_item);
    }

    @Override
    public void onItemListener(View view, int position) {
        Item item = adapter.getItem(position);
        tv_temperatureSelectedItem.setText(item.getTemperature()+"°C");
        tv_humiditySelectedItem.setText(item.getHumidity()+"%");
        tv_timeSelectedItem.setText(item.getTime()+"H");
        statusSelectedItem.setImageResource(item.getImage());
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Item> list = sqLiteHelper.getAll();
        if(list.size()>12){
            list.remove(0);
        }
        adapter.setData(list);
    }
}
