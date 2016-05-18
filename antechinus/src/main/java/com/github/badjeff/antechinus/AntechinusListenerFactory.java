package com.github.badjeff.antechinus;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by jeff on 16/12/15.
 * Project: Antechinus
 */
public interface AntechinusListenerFactory {

    /**
     * Used to custom string from Resource getString method.
     *
     * You implement this method like so in you config builder.
     * <pre>
     * {@code
     * public String getString(Context context, int id, String localizedString) throws Resources.NotFoundException {
     *  final String resName = context.getResources().getResourceName(id);
     *  // TODO: custom string here
     *  // android.util.Log.d(getClass().toString(), "##### getString [" + resName + "]");
     *  return localizedString;
     * }
     * }
     * </pre>
     *
     * @param context          current context (normally the Activity's)
     * @param id               resource id (normally the String's)
     * @param defaultString    Default getString result
     * @return Custom localized string
     */
    String getString(Context context, int id, String defaultString) throws Resources.NotFoundException;
}
