package com.example.ivan.hophey;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends SlideActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setHeaderView(R.layout.nav_header_main);
        setMenuTextColor(Color.parseColor("#ffffffff"));
        setMenuIconColor(Color.parseColor("#ffffffff"));
        setNavigationMenu(R.menu.activity_main_drawer);
        setNavigationBackgroundColor(android.R.color.transparent);
        setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                replaceFragment(new SecondFragment());
                closeNavigation();
                return true;
            }
        });
        setSlidingFadeColor(android.R.color.transparent);
        setToolbarColor(R.color.colorPrimary);
        addFragment(new FirstFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
