package ua.yurezcv.stoic;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.concurrent.Executors;

import ua.yurezcv.stoic.utils.threading.AppExecutors;
import ua.yurezcv.stoic.utils.threading.DiskIOThreadExecutor;

public class StoicWisdomApp extends Application {
    // Single instance Application object
    private static StoicWisdomApp sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        sInstance = this;
    }

    public static StoicWisdomApp getInstance() {
        return sInstance;
    }

    public static AppExecutors getExecutors() {
        return AppExecutors.getInstance(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(AppExecutors.THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if(cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }

        return false;
    }
}
