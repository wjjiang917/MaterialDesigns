package com.wjjiang.materialdesigns.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.wjjiang.materialdesigns.R;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    private ActionBarDrawerToggle toggle;
    private int scrollRange = -1;
    private boolean isShow = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(0);
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawer.post(new Runnable() {
            @Override
            public void run() {
                toggle.syncState();
            }
        });
        toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mCollapsingToolbarLayout.setTitle("MD");
        mCollapsingToolbarLayout.setExpandedTitleColor(ViewCompat.MEASURED_STATE_MASK);
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(-1);
        mAppBarLayout.addOnOffsetChangedListener(new CustomOnOffsetChangedListener());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.menu_profile:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.nav_blur:
                startActivity(new Intent(this, BlurActivity.class));
                break;
            case R.id.nav_slideshow:
                startActivity(new Intent(this, NavigationActivity.class));
                break;
            case R.id.nav_manage:
                startActivity(new Intent(this, AlarmActivity.class));
                break;
            case R.id.nav_share:
                break;

            case R.id.nav_danmu:
                startActivity(new Intent(this, DanmuActivity.class));
                break;

            case R.id.nav_full:
                startActivity(new Intent(this, FullActivity.class));
                break;
            case R.id.nav_camera:
                startActivity(new Intent(this, CameraActivity.class));
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    class CustomOnOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (scrollRange == -1) {
                scrollRange = appBarLayout.getTotalScrollRange();
            }

            if (scrollRange + verticalOffset == 0) {
                mCollapsingToolbarLayout.setTitle("Material Designs");
                isShow = true;
            } else if (isShow) {
                mCollapsingToolbarLayout.setTitle("MD");
                isShow = false;
            }
        }
    }
}
