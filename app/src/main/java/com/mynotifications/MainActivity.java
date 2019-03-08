package com.mynotifications;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView mProfileLabel;
    private TextView mUsersLabel;
    private TextView mNotificationLabel;

    private ViewPager mMainPager;

    private PagerViewAdapter mPagerViewAdapter;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {

            sendToLogin();
        }
    }

    private void sendToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        mProfileLabel = (TextView) findViewById(R.id.profileLabel);
        mUsersLabel = (TextView) findViewById(R.id.userLabel);
        mNotificationLabel = (TextView) findViewById(R.id.notificationLabel);

        mMainPager = (ViewPager) findViewById(R.id.mainPager);
        mPagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mMainPager.setAdapter(mPagerViewAdapter);


        mProfileLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(0);
            }
        });
        mUsersLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(1);
            }
        });
        mNotificationLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(2);
            }
        });


        mMainPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                changeTabs(position);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void changeTabs(int position) {
        if (position == 0) {
            mProfileLabel.setTextColor(getResources().getColor(R.color.textTabBright));
            mProfileLabel.setTextSize(22);

            mUsersLabel.setTextColor(getResources().getColor(R.color.textTabLight));
            mUsersLabel.setTextSize(16);
            mNotificationLabel.setTextColor(getResources().getColor(R.color.textTabLight));
            mNotificationLabel.setTextSize(16);

        }
        if (position == 1) {
            mUsersLabel.setTextColor(getResources().getColor(R.color.textTabBright));
            mUsersLabel.setTextSize(22);

            mProfileLabel.setTextColor(getResources().getColor(R.color.textTabLight));
            mProfileLabel.setTextSize(16);
            mNotificationLabel.setTextColor(getResources().getColor(R.color.textTabLight));
            mNotificationLabel.setTextSize(16);

        }
        if (position == 2) {
            mNotificationLabel.setTextColor(getResources().getColor(R.color.textTabBright));
            mNotificationLabel.setTextSize(22);

            mUsersLabel.setTextColor(getResources().getColor(R.color.textTabLight));
            mUsersLabel.setTextSize(16);
            mProfileLabel.setTextColor(getResources().getColor(R.color.textTabLight));
            mProfileLabel.setTextSize(16);

        }

    }
}
