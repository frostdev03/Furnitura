package com.frostdev.furnitura;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext, btnGetStarted;
    int position = 0;
    Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (loadPrefData()){
            Intent mainActtivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActtivity);
            finish();
        }

        //getSupportActionBar().hide();

        tabIndicator = findViewById(R.id.tab_indicator);
        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_anim);

        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Various Classes", "Semua kalangan pasti butuh furniture", R.drawable.furniture));
        mList.add(new ScreenItem("Various Prices", "Semua dompet bisa di habiskan untuk beli furniture", R.drawable.lamp));
        mList.add(new ScreenItem("Various Furniture's", "Di sinilah semua furniture", R.drawable.chair));

        ViewPager screenPager = findViewById(R.id.vp_screen);
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position == mList.size()-1){
                    loadLastScreen();
                }

            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);

                savePrefData();
                finish();
            }
        });
    }

    private boolean loadPrefData(){
        SharedPreferences prefo = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        Boolean isIntroActivityOpened = prefo.getBoolean("isIntroOpened", false);
        return isIntroActivityOpened;
    }

    private void savePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("isIntroOpened", true);
        edit.commit();
    }

    private void loadLastScreen() {
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        btnGetStarted.setAnimation(btnAnim);

    }
}