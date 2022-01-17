package ice.lib.crash;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

public class CrashHandler implements Thread.UncaughtExceptionHandler{

    /*
    *
    * getSafeThread
    *
    * 获取一个Thread，已经设置了CrashHandler
    *
    * */
    public static Thread getSafeThread(Context context, Runnable runnable) {
        Thread thread = new Thread(runnable);
        new CrashHandler(context, thread);
        return thread;
    }

    private final Context applicationContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private final String TAG = "IceCrashHandler";

    /*
    *
    * CrashHandler(Context)
    *
    * 对主线程设置CrashHandler
    *
    * */
    public CrashHandler(Context context) {
        applicationContext = context.getApplicationContext();
        init();
    }

    /*
    *
    * CrashHandler(Context, Thread)
    *
    * 对传入的线程设置CrashHandler
    *
    * */
    public CrashHandler(Context context, Thread thread) {
        applicationContext = context.getApplicationContext();
        init(thread);
    }

    private void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (mDefaultHandler == null) {
            mDefaultHandler = this;
            Thread.setDefaultUncaughtExceptionHandler(this);
            return;
        }
        if (!mDefaultHandler.getClass().getName().startsWith("com.android.internal.os") && !mDefaultHandler.getClass().getName().equals(this.getClass().getName())) {
            Log.d(TAG, "You already have an UncaughtExceptionHandler!");
        }
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private void init(Thread thread) {
        mDefaultHandler = thread.getUncaughtExceptionHandler();
        if (mDefaultHandler == null) {
            mDefaultHandler = this;
            thread.setUncaughtExceptionHandler(this);
            return;
        }
        if (!mDefaultHandler.getClass().getName().startsWith("com.android.internal.os") && !mDefaultHandler.getClass().getName().equals(this.getClass().getName())) {
            Log.d(TAG, "You already have an UncaughtExceptionHandler!");
        }
        thread.setUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (applicationContext != null) {
            Intent intent = new Intent(applicationContext, CrashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("error", Log.getStackTraceString(throwable));
            applicationContext.startActivity(intent);
        } else {
            Log.d(TAG, "Context is null!");
            if (mDefaultHandler != this) {
                mDefaultHandler.uncaughtException(thread, throwable);
            }
        }
        Process.killProcess(Process.myPid());
        System.exit(233);
    }
}
