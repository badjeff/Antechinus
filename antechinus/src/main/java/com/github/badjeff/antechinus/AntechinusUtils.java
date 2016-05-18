package com.github.badjeff.antechinus;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Method;

/**
 * Created by jeff on 12/16/15.
 * Project: Antechinus
 */
public final class AntechinusUtils {

    /**
     * Generate capitalized string
     *
     * @param  line      string to be capitalized
     * @return capitalized string
     */
    private static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    /**
     * Applies stirng to View by Reflection
     *
     * @param context      Context
     * @param view         View to apply to.
     * @param attrs        Attributes of Layout
     */
    public static void applyResourceStringToView(Context context, View view, AttributeSet attrs) {
        if (attrs != null) {
            Resources res = context.getResources();
            for (int i=0; i < attrs.getAttributeCount(); i++) {
                int resId = attrs.getAttributeResourceValue(i, 0);
                if (resId > 0) {
                    String resType = res.getResourceTypeName(resId);
                    if (resType.equals("string")) {
                        String attrName = attrs.getAttributeName(i);
                        try {
                            Method method = view.getClass().getMethod("set" + capitalize(attrName), CharSequence.class);
                            method.setAccessible(true);
                            method.invoke(view, res.getString(resId));
                        } catch (NoSuchMethodException e) {
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
    }

    private static Boolean sToolbarCheck = null;
    private static Boolean sAppCompatViewCheck = null;

    /**
     * See if the user has added appcompat-v7, this is done at runtime, so we only check once.
     *
     * @return true if the v7.Toolbar is on the classpath
     */
    static boolean canCheckForV7Toolbar() {
        if (sToolbarCheck == null) {
            try {
                Class.forName("android.support.v7.widget.Toolbar");
                sToolbarCheck = Boolean.TRUE;
            } catch (ClassNotFoundException e) {
                sToolbarCheck = Boolean.FALSE;
            }
        }
        return sToolbarCheck;
    }

    /**
     * See if the user has added appcompat-v7 with AppCompatViews
     *
     * @return true if AppcompatTextView is on the classpath
     */
    static boolean canAddV7AppCompatViews() {
        if (sAppCompatViewCheck == null) {
            try {
                Class.forName("android.support.v7.widget.AppCompatTextView");
                sAppCompatViewCheck = Boolean.TRUE;
            } catch (ClassNotFoundException e) {
                sAppCompatViewCheck = Boolean.FALSE;
            }
        }
        return sAppCompatViewCheck;
    }


    private AntechinusUtils() {
    }

}
