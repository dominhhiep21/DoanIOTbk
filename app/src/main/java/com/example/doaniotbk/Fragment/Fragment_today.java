package com.example.doaniotbk.Fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.doaniotbk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Fragment_today extends Fragment {
    private TextView tv_time,tv_temperature,tv_humidity,tv_lightbulb_1,tv_fan,tv_lightbulb_2,tv_lightbulb_3;
    private ImageView icon_temperature,icon_humidity,icon_lightbulb_1,icon_fan,icon_lightbulb_2,
    icon_lightbulb_3;
    private LinearLayout layout_infor;
    private TextView title_humidity,title_lightbulb_1,title_lightbulb_2,title_lightbulb_3,title_fan;
    private AppCompatButton bt_lightbulb_1,bt_lightbulb_2,bt_lightbulb_3,bt_fan;
    private Handler handler;
    private FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        //Xu li tv_time
        String months[] = {"January","February","March","April","May","June","July","August",
                "September","October","November","December"};
        String weekdays[] = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int weekday = calendar.get(Calendar.DAY_OF_WEEK);
                SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String time = weekdays[weekday-1]+" "+months[month]+" "+day+" "+year+" | "+
                        format.format(calendar.getTime());
                tv_time.setText(time);
                handler.sendEmptyMessageDelayed(0,1000);
            }
        };
        handler.sendEmptyMessage(0);
        //Xu li tv_temperature
        database = FirebaseDatabase.getInstance();
        DatabaseReference temperature_Ref = database.getReference("data/temperature");
        temperature_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int temperature_Data = snapshot.getValue(Integer.class);
                tv_temperature.setText(temperature_Data+"°C");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lay du lieu nhiet do that bai", Toast.LENGTH_SHORT).show();
            }
        });
        //Xu li tv_humidity

        DatabaseReference humidity_Ref = database.getReference("data/humidity");
        humidity_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float humidity_Data = snapshot.getValue(Float.class);
                tv_humidity.setText(humidity_Data+"%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lay du lieu do am that bai", Toast.LENGTH_SHORT).show();
            }
        });
        //Xu li tv_lightbuld
        DatabaseReference lightbuld_Ref_1 = database.getReference("data/ledState");
        lightbuld_Ref_1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int lightbuld_Data = snapshot.getValue(Integer.class);
                if(lightbuld_Data==0){
                    tv_lightbulb_1.setText("OFF");
                    icon_lightbulb_1.setImageResource(R.drawable.light_bulb_off);
                }else{
                    tv_lightbulb_1.setText("ON");
                    icon_lightbulb_1.setImageResource(R.drawable.light_bulb);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lay trang thai den that bai", Toast.LENGTH_SHORT).show();
            }
        });
        /*
        DatabaseReference lightbuld_Ref_2 = database.getReference("data/led2State");
        lightbuld_Ref_2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int lightbuld_Data = snapshot.getValue(Integer.class);
                if(lightbuld_Data==0){
                    tv_lightbulb_2.setText("OFF");
                    icon_lightbulb_2.setImageResource(R.drawable.light_bulb_off);
                }else{
                    tv_lightbulb_2.setText("ON");
                    icon_lightbulb_2.setImageResource(R.drawable.light_bulb);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lay trang thai den that bai", Toast.LENGTH_SHORT).show();
            }
        });
        DatabaseReference lightbuld_Ref_3 = database.getReference("data");
        lightbuld_Ref_3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int lightbuld_Data = snapshot.getValue(Integer.class);
                if(lightbuld_Data==0){
                    tv_lightbulb_3.setText("OFF");
                    icon_lightbulb_3.setImageResource(R.drawable.light_bulb_off);
                }else{
                    tv_lightbulb_3.setText("ON");
                    icon_lightbulb_3.setImageResource(R.drawable.light_bulb);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lay trang thai den that bai", Toast.LENGTH_SHORT).show();
            }
        });*/
        //Xu li tv_fan
        DatabaseReference fan_Ref = database.getReference("data/fan");
        fan_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int fan_Data = snapshot.getValue(Integer.class);
                if(fan_Data==0){
                    tv_fan.setText("OFF");
                    icon_fan.animate().cancel();
                }else{
                    tv_fan.setText("ON");
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            icon_fan.animate().rotationBy(360).withEndAction(this)
                                    .setDuration(2000).setInterpolator(new LinearInterpolator()).start();
                        }
                    };
                    icon_fan.animate().rotationBy(360).withEndAction(runnable)
                            .setDuration(2000).setInterpolator(new LinearInterpolator()).start();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lay trang thai quat that bai", Toast.LENGTH_SHORT).show();
            }
        });
        //Xu li bt_lightbuld

        DatabaseReference lightbulb_Ref_1 = database.getReference("data/ledState");
            lightbulb_Ref_1.addValueEventListener(new ValueEventListener() {
                @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int lightbuld_Data = snapshot.getValue(Integer.class);
                        bt_lightbulb_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(lightbuld_Data==1){
                                    lightbulb_Ref_1.setValue(0);
                                }
                                if(lightbuld_Data==0){
                                    lightbulb_Ref_1.setValue(1);
                                }
                                Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.button_click);
                                v.startAnimation(animation);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            /*
        DatabaseReference lightbulb_Ref_2 = database.getReference("data");
        lightbulb_Ref_2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int lightbuld_Data = snapshot.getValue(Integer.class);
                bt_lightbulb_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(lightbuld_Data==1){
                            lightbulb_Ref_2.setValue(0);
                        }
                        if(lightbuld_Data==0){
                            lightbulb_Ref_2.setValue(1);
                        }
                        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.button_click);
                        v.startAnimation(animation);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference lightbulb_Ref_3 = database.getReference("data");
        lightbulb_Ref_3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int lightbuld_Data = snapshot.getValue(Integer.class);
                bt_lightbulb_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(lightbuld_Data==1){
                            lightbulb_Ref_3.setValue(0);
                        }
                        if(lightbuld_Data==0){
                            lightbulb_Ref_3.setValue(1);
                        }
                        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.button_click);
                        v.startAnimation(animation);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        //Xu li bt_fan
        DatabaseReference fan_Ref2 = database.getReference("data/fan");
        fan_Ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int fan_Data = snapshot.getValue(Integer.class);
                bt_fan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(fan_Data==1){
                            fan_Ref2.setValue(0);
                        }
                        if(fan_Data==0){
                            fan_Ref2.setValue(1);
                        }
                        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.button_click);
                        v.startAnimation(animation);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
    private void initView(View view) {
        tv_time = view.findViewById(R.id.tv_time);
        tv_temperature = view.findViewById(R.id.tv_temperature);
        tv_humidity = view.findViewById(R.id.tv_humidity);
        tv_lightbulb_1 = view.findViewById(R.id.tv_lightbulb_1);
        tv_lightbulb_2 = view.findViewById(R.id.tv_lightbulb_2);
        tv_lightbulb_3 = view.findViewById(R.id.tv_lightbulb_3);
        tv_fan = view.findViewById(R.id.tv_fan);
        bt_fan = view.findViewById(R.id.button_fan);
        bt_lightbulb_1 = view.findViewById(R.id.button_light_1);
        bt_lightbulb_2 = view.findViewById(R.id.button_light_2);
        bt_lightbulb_3 = view.findViewById(R.id.button_light_3);
        icon_temperature = view.findViewById(R.id.icon_temperature);
        icon_humidity = view.findViewById(R.id.icon_humidity);
        icon_lightbulb_1 = view.findViewById(R.id.icon_lightbulb_1);
        icon_lightbulb_2 = view.findViewById(R.id.icon_lightbulb_2);
        icon_lightbulb_3 = view.findViewById(R.id.icon_lightbulb_3);
        icon_fan = view.findViewById(R.id.icon_fan);
        title_humidity = view.findViewById(R.id.title_humidity);
        title_lightbulb_1 = view.findViewById(R.id.title_lightbuilb_1);
        title_lightbulb_2 = view.findViewById(R.id.title_lightbuilb_2);
        title_lightbulb_3 = view.findViewById(R.id.title_lightbuilb_3);
        title_fan = view.findViewById(R.id.title_fan);
        layout_infor = view.findViewById(R.id.layout_infor);
        LinearLayout linearLayout = view.findViewById(R.id.mainLayout_today);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3000);
        animationDrawable.setExitFadeDuration(10000);
        animationDrawable.start();

        //Hieu ung fate_in
        Animation fateInAnimation = new AlphaAnimation(0,1);
        fateInAnimation.setInterpolator(new AccelerateInterpolator());
        fateInAnimation.setDuration(4000);
        //
        tv_time.startAnimation(fateInAnimation);
        layout_infor.startAnimation(fateInAnimation);
        tv_temperature.startAnimation(fateInAnimation);
        tv_humidity.startAnimation(fateInAnimation);
        tv_lightbulb_1.startAnimation(fateInAnimation);
        tv_lightbulb_2.startAnimation(fateInAnimation);
        tv_lightbulb_3.startAnimation(fateInAnimation);
        tv_fan.startAnimation(fateInAnimation);
        bt_fan.startAnimation(fateInAnimation);
        bt_lightbulb_1.startAnimation(fateInAnimation);
        bt_lightbulb_2.startAnimation(fateInAnimation);
        bt_lightbulb_3.startAnimation(fateInAnimation);
        icon_temperature.startAnimation(fateInAnimation);
        icon_humidity.startAnimation(fateInAnimation);
        icon_lightbulb_1.startAnimation(fateInAnimation);
        icon_lightbulb_2.startAnimation(fateInAnimation);
        icon_lightbulb_3.startAnimation(fateInAnimation);
        icon_fan.startAnimation(fateInAnimation);
        title_humidity.startAnimation(fateInAnimation);
        title_lightbulb_1.startAnimation(fateInAnimation);
        title_lightbulb_2.startAnimation(fateInAnimation);
        title_lightbulb_3.startAnimation(fateInAnimation);
        title_fan.startAnimation(fateInAnimation);
        //Hieu ưng button click
        Animation scale_Down = AnimationUtils.loadAnimation(getContext(),R.anim.button_click);

    }
}
