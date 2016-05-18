package com.github.badjeff.antechinus.sample;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.github.badjeff.antechinus.AntechinusConfig;
import com.github.badjeff.antechinus.AntechinusListenerFactory;

/**
 * Created by jeff on 12/16/15.
 * For Antechinus.
 */
public class AntechinusApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AntechinusConfig.initDefault(new AntechinusConfig.Builder()
                .listenerFactory(new AntechinusListenerFactory() {
                    @Override
                    public String getString(Context context, int id, String defaultString) throws Resources.NotFoundException {
                        final String resName = context.getResources().getResourceName(id);
                        // TODO: custom string here
                        android.util.Log.d(getClass().toString(), "##### getString [" + resName + "]");
                        return "<<" + defaultString + ">>";
                    }
                })
                .build());
    }
}
