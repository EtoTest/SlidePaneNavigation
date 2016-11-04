package com.example.ivan.hophey;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

public class SlideActivity extends AppCompatActivity {

    private LinearLayout.LayoutParams ll_params =
            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    protected void setHeaderView(View view){
        navigationView.addHeaderView(view);
    }

    protected void setHeaderView(int id){
        View headerView = LayoutInflater.from(this).inflate(id, null, false);
        navigationView.addHeaderView(headerView);
    }

    protected NavigationView getNavigationView(){
        return navigationView;
    }

    protected View getHeaderView(){
        return navigationView.getHeaderView(0);
    }

    protected void setMenuTextColor(ColorStateList colorStateList){
        navigationView.setItemTextColor(colorStateList);
    }

    protected void setMenuTextColor(int color){
        navigationView.setItemTextColor(ColorStateList.valueOf(color));
    }

    protected void setNavigationMenu(int menu){
        navigationView.getMenu().clear();
        navigationView.inflateMenu(menu);
    }

    protected Menu getNavigationMenu(){
        return navigationView.getMenu();
    }

    protected void setMenuIconColor(ColorStateList colorStateList){
        navigationView.setItemIconTintList(colorStateList);
    }

    protected void setNavigationBackgroundColor(int color){
        navigationView.setBackgroundColor(ContextCompat.getColor(this, color));
    }

    protected void setMenuIconColor(int color){
        navigationView.setItemIconTintList(ColorStateList.valueOf(color));
    }

    protected void setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener listener){
        navigationView.setNavigationItemSelectedListener(listener);
    }

    protected void setSlidingFadeColor(int color){
        slidingPaneLayout.setSliderFadeColor(color);
    }

    protected Toolbar getToolbar(){
        return toolbar;
    }

    protected void setToolbarColor(int color){
        toolbar.setBackgroundColor(ContextCompat.getColor(this, color));
    }

    private NavigationView navigationView;
    private SlidingPaneLayout slidingPaneLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout mainView = new RelativeLayout(this);
        mainView.setLayoutParams(ll_params);
        setContentView(mainView);

        ImageView background_imageView = new ImageView(this);
        background_imageView.setLayoutParams(ll_params);
        background_imageView.setId(R.id.background_imageView);
        background_imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mainView.addView(background_imageView);

        slidingPaneLayout = new SlidingPaneLayout(this);
        slidingPaneLayout.setId(R.id.sliding_pane_layout);
        slidingPaneLayout.setLayoutParams(new SlidingPaneLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mainView.addView(slidingPaneLayout);

        final LinearLayout view_drawer = new LinearLayout(this);
        view_drawer.setLayoutParams(ll_params);
        view_drawer.setOrientation(LinearLayout.VERTICAL);
        view_drawer.setId(R.id.view_drawer);
        slidingPaneLayout.addView(view_drawer);

        navigationView = new NavigationView(this);
        navigationView.setId(R.id.navigation_view);
        NavigationView.LayoutParams navLayoutParams = new NavigationView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        navigationView.setLayoutParams(navLayoutParams);
        view_drawer.addView(navigationView);

        final LinearLayout content_view_group = new LinearLayout(this);
        content_view_group.setId(R.id.content_view_group);
        content_view_group.setLayoutParams(ll_params);
        content_view_group.setGravity(Gravity.CENTER_VERTICAL);
        content_view_group.setOrientation(LinearLayout.VERTICAL);
        slidingPaneLayout.addView(content_view_group);

        final LinearLayout view_content = new LinearLayout(this);
        view_content.setId(R.id.viewgroup_content);
        view_content.setOrientation(LinearLayout.VERTICAL);
        view_content.setLayoutParams(ll_params);
        content_view_group.addView(view_content);

        toolbar = new Toolbar(this);
        toolbar.setId(R.id.toolbar);

        //todo correct toolbar height
        Toolbar.LayoutParams toolparParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120);
        toolbar.setLayoutParams(toolparParams);
        view_content.addView(toolbar);

        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.frame_layout);
        FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        frameLayout.setLayoutParams(frameLayoutParams);
        view_content.addView(frameLayout);

        Picasso.with(this).load(R.drawable.test_bg).fit().centerCrop().into(background_imageView);

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

    }

    protected void openNavigation(){
        slidingPaneLayout.openPane();
    }

    protected void closeNavigation(){
        slidingPaneLayout.closePane();
    }

    protected void addFragment(Fragment fragment){
        getFragmentManager().beginTransaction().add(R.id.frame_layout, fragment).commit();
    }

    protected void replaceFragment(Fragment fragment){
        getFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }

    private int getStatusBarHeight(){
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarTop = rectangle.top;
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return Math.abs(statusBarTop - contentViewTop);
    }

}
