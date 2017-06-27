package Threads;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * Created by spl on 12/5/16.
 */
public class MainThreadExecutor implements Executor {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}
