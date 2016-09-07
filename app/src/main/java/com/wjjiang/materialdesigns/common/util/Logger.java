package com.wjjiang.materialdesigns.common.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jiangwenjin on 16/8/14.
 */
public class Logger {
    private static boolean isDebugEnabled = true;
    private static boolean isErrorEnabled = true;
    private static boolean isInfoEnabled = true;
    private static boolean isVerboseEnabled = true;
    private static boolean isWarnEnabled = true;
    private static boolean isWtfEnabled = true;
    private static boolean isSaveLog = true;

    private static String logFileFolder;

    // expire time, to clean log files
    private static final long EXPIRE = 7 * 24 * 60 * 60 * 1000L;

    public static void setLogFileFolder(Context context) {
        logFileFolder = FileUtil.getDiskFilesDir(context) + "log";
    }

    private static String getTag() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String clazzName = stackTrace[4].getClassName();
        clazzName = clazzName.substring(clazzName.lastIndexOf(".") + 1);

        // Logger.getTag(54)
        return clazzName + "." + stackTrace[4].getMethodName() + "(" + stackTrace[4].getLineNumber() + ")";
    }

    public static void d(String msg) {
        if (!isDebugEnabled) return;

        String tag = getTag();
        Log.d(tag, msg);

        if (isSaveLog) {
            saveLog(tag, " DEBUG | " + msg);
        }
    }

    public static void d(String msg, Throwable tr) {
        if (!isDebugEnabled) return;

        String tag = getTag();
        Log.d(tag, msg, tr);

        if (isSaveLog) {
            saveLog(tag, " DEBUG | " + msg + '\n' + Log.getStackTraceString(tr));
        }
    }

    public static void e(String msg) {
        if (!isErrorEnabled) return;

        String tag = getTag();
        Log.e(tag, msg);

        if (isSaveLog) {
            saveLog(tag, " ERROR | " + msg);
        }
    }

    public static void e(String msg, Throwable tr) {
        if (!isErrorEnabled) return;

        String tag = getTag();
        Log.e(tag, msg, tr);

        if (isSaveLog) {
            saveLog(tag, " ERROR | " + msg + '\n' + Log.getStackTraceString(tr));
        }
    }

    public static void i(String msg) {
        if (!isInfoEnabled) return;
        Log.i(getTag(), msg);
    }

    public static void i(String msg, Throwable tr) {
        if (!isInfoEnabled) return;
        Log.i(getTag(), msg, tr);
    }

    public static void v(String msg) {
        if (!isVerboseEnabled) return;
        Log.v(getTag(), msg);
    }

    public static void v(String msg, Throwable tr) {
        if (!isVerboseEnabled) return;
        Log.v(getTag(), msg, tr);
    }

    public static void w(String msg) {
        if (!isWarnEnabled) return;

        String tag = getTag();
        Log.w(getTag(), msg);

        if (isSaveLog) {
            saveLog(tag, " WARN | " + msg);
        }
    }

    public static void w(String msg, Throwable tr) {
        if (!isWarnEnabled) return;

        String tag = getTag();
        Log.w(getTag(), msg, tr);

        if (isSaveLog) {
            saveLog(tag, " WARN | " + msg + '\n' + Log.getStackTraceString(tr));
        }
    }

    public static void w(Throwable tr) {
        if (!isWarnEnabled) return;

        String tag = getTag();
        Log.w(getTag(), tr);

        if (isSaveLog) {
            saveLog(tag, " WARN | " + Log.getStackTraceString(tr));
        }
    }

    public static void wtf(String msg) {
        if (!isWtfEnabled) return;
        Log.wtf(getTag(), msg);
    }

    public static void wtf(String msg, Throwable tr) {
        if (!isWtfEnabled) return;
        Log.wtf(getTag(), msg, tr);
    }

    public static void wtf(Throwable tr) {
        if (!isWtfEnabled) return;
        Log.wtf(getTag(), tr);
    }

    private static void saveLog(String tag, String msg) {
        if (null == logFileFolder) {
            return;
        }

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                || Environment.getExternalStorageDirectory().exists()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String time = sdf.format(new Date());

            File dirFile = new File(logFileFolder);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            String log = logFileFolder + time + ".log";
            File logFile = new File(log);
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (!logFile.exists()) {
                return;
            }

            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            time = sdf.format(new Date());

            try {
                FileUtils.write(logFile, time + " | " + tag + " | " + msg + "\n", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * only save logs in 7 days
     */
    public static void clear() {
        if (null == logFileFolder) {
            return;
        }
        File dirFile = new File(logFileFolder);
        if (!dirFile.exists()) {
            return;
        }

        File[] logs = dirFile.listFiles();
        if (null != logs && logs.length > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fileName;
            for (File tLog : logs) {
                fileName = tLog.getName();
                if (fileName.contains(".")) {
                    fileName = fileName.substring(0, fileName.indexOf("."));

                    try {
                        long fileTime = sdf.parse(fileName).getTime();
                        if (new Date().getTime() - fileTime > EXPIRE) {
                            System.out.println("Delete log file, file name: " + fileName);
                            FileUtils.deleteQuietly(tLog);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
    }
}
