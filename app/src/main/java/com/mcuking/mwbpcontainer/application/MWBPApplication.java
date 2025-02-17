package com.mcuking.mwbpcontainer.application;

import android.content.Context;
import android.app.Application;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.hht.webpackagekit.PackageManager;
import com.hht.webpackagekit.core.Constants;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.alt236.webviewdebug.DebugWebChromeClientLogger;
import uk.co.alt236.webviewdebug.DebugWebViewClientLogger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MWBPApplication extends MultiDexApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        EventBus.getDefault().register(this);

        PackageManager.getInstance().init(context);
        getPackageIndex(Constants.BASE_PACKAGE_INDEX);


    }

    private void getPackageIndex(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Log.d("getPackageIndex", "request : "+url);
                    Request request = new Request.Builder().url(url).build();
                    try (Response response = client.newCall(request).execute()) {
                        String data =  response.body().string();
                        Log.d("getPackageIndex", "result: "+url+"\n"+data);
                        EventBus.getDefault().post(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updatePackageManager(String res){
        Log.d("getPackageIndex", "updatePackageManager");
        PackageManager.getInstance().update(res);
    }

    public static Context getGlobalContext() {
        return context;
    }
}
