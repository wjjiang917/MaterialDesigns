package com.wjjiang.materialdesigns.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.wjjiang.materialdesigns.R;
import com.wjjiang.materialdesigns.adapter.RecyclerAdapter;
import com.wjjiang.materialdesigns.common.util.HideScrollListener;
import com.wjjiang.materialdesigns.common.util.Logger;
import com.wjjiang.materialdesigns.ui.navigationview.SpaceItem;
import com.wjjiang.materialdesigns.ui.navigationview.SpaceNavigationView;
import com.wjjiang.materialdesigns.ui.navigationview.SpaceOnClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NavigationActivity extends BaseActivity {
    @BindView(R.id.space)
    SpaceNavigationView spaceNavigationView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.navigation_toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        setTitle("Navigation Toolbar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Home", R.drawable.home));
        spaceNavigationView.addSpaceItem(new SpaceItem("Me", R.drawable.account));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Logger.d("onCentreButtonClick...");
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Logger.d("onItemClick... item: " + itemIndex + ",name: " + itemName);
            }
        });

        RecyclerAdapter adapter = new RecyclerAdapter(dummyStrings());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        adapter.setRecyclerClickListener(new RecyclerAdapter.RecyclerClickListener() {
            @Override
            public void onClick(int position) {
                if (position == 0) {
                    spaceNavigationView.changeCurrentItem(0);
                    spaceNavigationView.showBadgeAtIndex(1, 4, ContextCompat.getColor(NavigationActivity.this, R.color.badge_background_color));

                } else if (position == 1) {
                    spaceNavigationView.changeCurrentItem(1);
                    spaceNavigationView.hideBudgeAtIndex(1);
                }
            }
        });

        recyclerView.addOnScrollListener(new HideScrollListener() {
            @Override
            public void onHide() {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) spaceNavigationView.getLayoutParams();
                spaceNavigationView.animate().translationY(spaceNavigationView.getHeight() + lp.bottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
            }

            @Override
            public void onShow() {
                spaceNavigationView.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }

    private List<String> dummyStrings() {
        List<String> colorList = new ArrayList<>();
        colorList.add("#354045");
        colorList.add("#20995E");
        colorList.add("#76FF03");
        colorList.add("#E26D1B");
        colorList.add("#911717");
        colorList.add("#9C27B0");
        colorList.add("#FFC107");
        colorList.add("#01579B");
        return colorList;
    }
}
