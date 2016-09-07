package com.wjjiang.materialdesigns.common.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Jiangwenjin on 16/8/14.
 */
public class FileUtil {
    public static File getDiskCache(Context context, String uniqueName) {
        File f = new File(getDiskCacheDir(context) + uniqueName);
        if (f.exists()) {
            f.delete();
        }
        return f;
    }

    /**
     * get file cache folder
     *
     * @param context Context
     * @return String
     */
    public static String getDiskCacheDir(Context context) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ?
                getExternalDir(context, "cache").getPath() : context.getCacheDir().getPath();
        File file = new File(cachePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return cachePath + File.separator;
    }

    /**
     * get files folder
     *
     * @param context Context
     * @return String
     */
    public static String getDiskFilesDir(Context context) {
        final String path = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ?
                getExternalDir(context, "files").getPath() : context.getFilesDir().getPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path + File.separator;
    }

    /**
     * get external folder
     *
     * @param context Context
     * @return File
     */
    public static File getExternalDir(Context context, String dir) {
        final String path = "/Android/data/" + context.getPackageName() + "/" + dir + "/";
        return new File(Environment.getExternalStorageDirectory().getPath() + path);
    }
}
