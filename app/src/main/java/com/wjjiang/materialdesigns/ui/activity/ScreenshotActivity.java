package com.wjjiang.materialdesigns.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wjjiang.materialdesigns.R;
import com.wjjiang.materialdesigns.common.util.FileUtil;
import com.wjjiang.materialdesigns.common.util.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class ScreenshotActivity extends BaseActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.image_preview)
    ImageView imagePreview;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_screenshot;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.button})
    public void clickScreenshot(View view) {
        switch (view.getId()) {
            case R.id.button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        View decorView = getWindow().getDecorView();
                        decorView.setDrawingCacheEnabled(true);
                        decorView.buildDrawingCache();
                        final Bitmap bitmap = decorView.getDrawingCache();
                        if (null != bitmap) {
                            try {
                                String filePath = FileUtil.getDiskFilesDir(ScreenshotActivity.this) + File.separator + "screenshot.png";
                                File file = new File(filePath);
                                FileOutputStream fos = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                fos.flush();
                                fos.close();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        imagePreview.setImageBitmap(bitmap);
                                    }
                                });
                            } catch (IOException e) {
                                Logger.e("", e);
                            }
                        }
                    }
                }).start();
                break;
        }
    }
}
