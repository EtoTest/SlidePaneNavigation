package com.example.ivan.hophey;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.transitionseverywhere.TransitionManager;

public class SlideActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        final LinearLayout view_drawer = (LinearLayout)findViewById(R.id.view_drawer);
        final View view_content = (View)findViewById(R.id.content);
        final SlidingPaneLayout layout = (SlidingPaneLayout) findViewById(R.id.sliding_pane_layout);
        ImageView back = (ImageView) findViewById(R.id.backGr);

        Picasso.with(this).load(R.drawable.test_bg).fit().centerCrop().into(back);
        layout.setSliderFadeColor(Color.TRANSPARENT);

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
                if (layout.isOpen()){
                    layout.closePane();
                } else {
                    layout.openPane();
                }
            }
        });

        TransitionManager.getDefaultTransition().setDuration(200);

        layout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.e("offset", slideOffset + "");

              //  TransitionManager.beginDelayedTransition(ll_content);

                ViewGroup.LayoutParams contentParams = view_content.getLayoutParams();
                contentParams.height = (int)(height - slideOffset * ( height / 3));

                view_content.setLayoutParams(contentParams);

            }

            @Override
            public void onPanelOpened(View panel) {
             //   TransitionManager.beginDelayedTransition(ll_content);

                ViewGroup.LayoutParams contentParams = view_content.getLayoutParams();
                contentParams.height = 2 * height / 3;

                view_content.setLayoutParams(contentParams);
            }

            @Override
            public void onPanelClosed(View panel) {
             //   TransitionManager.beginDelayedTransition(ll_content);
                ViewGroup.LayoutParams contentParams = view_content.getLayoutParams();
                contentParams.height = height;

                view_content.setLayoutParams(contentParams);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
            getFragmentManager().beginTransaction().replace(R.id.content, new FirstFragment()).commit();
        } else if (id == R.id.nav_slideshow) {
            getFragmentManager().beginTransaction().replace(R.id.content, new SecondFragment()).commit();
        } else if (id == R.id.nav_manage) {
            getFragmentManager().beginTransaction().replace(R.id.content, new ThirdFragment()).commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        return true;
    }
}
