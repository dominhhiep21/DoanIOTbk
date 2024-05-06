package com.example.doaniotbk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.doaniotbk.Adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private ViewPagerAdapter adapter;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),3);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:bottomNavigationView.getMenu().findItem(R.id.mToday).setChecked(true);
                        break;
                    case 1:bottomNavigationView.getMenu().findItem(R.id.mChart).setChecked(true);
                        break;
                    case 2:bottomNavigationView.getMenu().findItem(R.id.mHistory).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.mToday) viewPager.setCurrentItem(0);
                if(item.getItemId()==R.id.mChart) viewPager.setCurrentItem(1);
                if(item.getItemId()==R.id.mHistory) viewPager.setCurrentItem(2);
                return true;
            }
        });
        /*database = FirebaseDatabase.getInstance();
        DatabaseReference gas_Ref = database.getReference("Khi gas");
        gas_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float gasValue = snapshot.getValue(Float.class);
                if(gasValue>=400){
                    openDialog(Gravity.CENTER);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Lay du lieu khi gas that bai", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
    public void openDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.warning_dialog);
        Window window = dialog.getWindow();
        if(window==null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        AppCompatButton warning_Button = dialog.findViewById(R.id.warning_button);
        warning_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void initView() {
        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.navigation);
    }
}