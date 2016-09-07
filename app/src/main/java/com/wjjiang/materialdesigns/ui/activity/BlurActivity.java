package com.wjjiang.materialdesigns.ui.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;

import com.wjjiang.materialdesigns.R;
import com.wjjiang.materialdesigns.common.util.BlurBitmap;

import butterknife.BindView;

public class BlurActivity extends BaseActivity {
    @BindView(R.id.blur_button)
    FloatingActionButton mFab;
    @BindView(R.id.blur_image)
    ImageView mImage;
    @BindView(R.id.blur_image2)
    ImageView mImage2;
    private boolean mBlur = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_blur;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mBlur) {
                    mImage.setImageBitmap(BlurBitmap.blur(BlurActivity.this, BitmapFactory.decodeResource(getResources(), R.mipmap.panda)));
                    mImage2.setImageBitmap(BlurBitmap.blur(BitmapFactory.decodeResource(getResources(), R.mipmap.panda), 25, false));
                } else {
                    mImage.setImageResource(R.mipmap.panda);
                    mImage2.setImageResource(R.mipmap.panda);
                }
                mBlur = !mBlur;
            }
        });
    }
}
