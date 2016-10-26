package com.wjjiang.materialdesigns.ui.activity;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.wjjiang.materialdesigns.R;
import com.wjjiang.materialdesigns.common.util.Logger;

import butterknife.BindView;

public class CameraActivity extends BaseActivity {
    CameraManager mCameraManager;
    @BindView(R.id.camera_surface_view)
    SurfaceView mSurfaceView;

    private SurfaceHolder mSurfaceHolder;
    private String mCameraId;
    private ImageReader mImageReader;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCameraManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                initCameraAndPreview();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            }
        });
    }

    private void initCameraAndPreview() {
        Logger.d("init camera and preview...");
        HandlerThread handlerThread = new HandlerThread("Camera2");
        handlerThread.start();

        try {
            mCameraId = "" + CameraCharacteristics.LENS_FACING_FRONT;
            mImageReader = ImageReader.newInstance(mSurfaceView.getWidth(), mSurfaceView.getHeight(),
                    ImageFormat.JPEG,/*maxImages*/7);
            mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, mHandler);

            mCameraManager.openCamera(mCameraId, DeviceStateCallback, mHandler);
        } catch (CameraAccessException e) {
            Log.e("linc", "open camera failed." + e.getMessage());
        }
    }
}
