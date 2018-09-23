package ro.alexsicoe.clepsydra.util;

import android.util.Log;

public final class LogUtil {
    private LogUtil() {

    }

    public static void except(String TAG, Exception e) {
        Log.w(TAG, "Listen failed: ", e);
    }
}
