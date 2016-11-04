package com.example.ivan.hophey;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

public class SlideActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        final LinearLayout view_drawer = (LinearLayout)findViewById(R.id.view_drawer);
        final SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        ImageView back = (ImageView) findViewById(R.id.backGr);

        final LinearLayout content_view_group = new LinearLayout(this);
        content_view_group.setId(R.id.content_view_group);
        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        content_view_group.setLayoutParams(ll_params);
        content_view_group.setGravity(Gravity.CENTER_VERTICAL);
        content_view_group.setOrientation(LinearLayout.VERTICAL);
        slidingPaneLayout.addView(content_view_group);

        final LinearLayout view_content = new LinearLayout(this);
        view_content.setId(R.id.viewgroup_content);
        view_content.setOrientation(LinearLayout.VERTICAL);
        view_content.setLayoutParams(ll_params);
        content_view_group.addView(view_content);

        Picasso.with(this).load(R.drawable.test_bg).fit().centerCrop().into(back);
        slidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        final int height = size.y - getStatusBarHeight();

        ViewGroup.LayoutParams drawerParams = view_drawer.getLayoutParams();
        drawerParams.width = 2 * width / 3;
        view_drawer.setLayoutParams(drawerParams);

        ViewGroup.LayoutParams contentParams = view_content.getLayoutParams();
        view_content.setLayoutParams(contentParams);

        view_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slidingPaneLayout.isOpen()){
                    slidingPaneLayout.closePane();
                } else {
                    slidingPaneLayout.openPane();
                }
            }
        });

        SlidingPaneLayout.PanelSlideListener panelSlideListener = new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                ViewGroup.LayoutParams contentParams = view_content.getLayoutParams();
                contentParams.height = (int)(height - slideOffset * ( height / 3));
                view_content.setLayoutParams(contentParams);
            }

            @Override
            public void onPanelOpened(View panel) {
                ViewGroup.LayoutParams contentParams = view_content.getLayoutParams();
                contentParams.height = 2 * height / 3;
                view_content.setLayoutParams(contentParams);
            }

            @Override
            public void onPanelClosed(View panel) {
                ViewGroup.LayoutParams contentParams = view_content.getLayoutParams();
                contentParams.height = height;
                view_content.setLayoutParams(contentParams);
            }
        };

        slidingPaneLayout.setPanelSlideListener(panelSlideListener);

        NavigationView navigationView = new NavigationView(this);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setId(R.id.navigation_view);
        NavigationView.LayoutParams navLayoutParams = new NavigationView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        navigationView.setLayoutParams(navLayoutParams);
        navigationView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null, false);
        navigationView.addHeaderView(headerView);
        navigationView.getMenu().clear(); //clear old inflated items.
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        navigationView.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
        navigationView.inflateMenu(R.menu.activity_main_drawer); //inflate new items.
        view_drawer.addView(navigationView);

        Toolbar toolbar = new Toolbar(this);
        toolbar.setId(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        //todo correct toolbar height
        Toolbar.LayoutParams toolparParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120);
        toolbar.setLayoutParams(toolparParams);
        view_content.addView(toolbar);

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.frame_layout);
        FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        frameLayout.setLayoutParams(frameLayoutParams);
        view_content.addView(frameLayout);
        getFragmentManager().beginTransaction().add(R.id.frame_layout, new FirstFragment()).commit();
    }

    private int getStatusBarHeight(){
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarTop = rectangle.top;
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return Math.abs(statusBarTop - contentViewTop);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            getFragmentManager().beginTransaction().replace(R.id.frame_layout, new FirstFragment()).commit();
        } else if (id == R.id.nav_slideshow) {
            getFragmentManager().beginTransaction().replace(R.id.frame_layout, new SecondFragment()).commit();
        } else {
            getFragmentManager().beginTransaction().replace(R.id.frame_layout, new ThirdFragment()).commit();
        }


        return true;
    }
}
