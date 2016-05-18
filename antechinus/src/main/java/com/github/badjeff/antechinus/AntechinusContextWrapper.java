package com.github.badjeff.antechinus;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by jeff on 12/16/15.
 * Project: Antechinus
 */
public class AntechinusContextWrapper extends ContextWrapper {

    private AntechinusLayoutInflater mInflater;

    private final Resources mResources;

    /**
     * Uses the default configuration from {@link AntechinusConfig}
     *
     * Remember if you are defining default in the
     * {@link AntechinusConfig} make sure this is initialised before
     * the activity is created.
     *
     * @param base ContextBase to Wrap.
     * @return ContextWrapper to pass back to the activity.
     */
    public static ContextWrapper wrap(Context base) { return new AntechinusContextWrapper(base); }

    /**
     * You only need to call this <b>IF</b> you call
     * {@link AntechinusConfig.Builder#disablePrivateFactoryInjection()}
     * This will need to be called from the
     * {@link android.app.Activity#onCreateView(android.view.View, String, android.content.Context, android.util.AttributeSet)}
     * method to enable view font injection if the view is created inside the activity onCreateView.
     *
     * You would implement this method like so in you base activity.
     * <pre>
     * {@code
     * public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
     *   return AntechinusContextWrapper.onActivityCreateView(this, parent, super.onCreateView(parent, name, context, attrs), name, context, attrs);
     * }
     * }
     * </pre>
     *
     * @param activity The activity the original that the ContextWrapper was attached too.
     * @param parent   Parent view from onCreateView
     * @param view     The View Created inside onCreateView or from super.onCreateView
     * @param name     The View name from onCreateView
     * @param context  The context from onCreateView
     * @param attr     The AttributeSet from onCreateView
     * @return The same view passed in, or null if null passed in.
     */
    public static View onActivityCreateView(Activity activity, View parent, View view, String name, Context context, AttributeSet attr) {
        return get(activity).onActivityCreateView(parent, view, name, context, attr);
    }

    /**
     * Get the Antechinus Activity Fragment Instance to allow callbacks for when views are created.
     *
     * @param activity The activity the original that the ContextWrapper was attached too.
     * @return Interface allowing you to call onActivityViewCreated
     */
    static AntechinusActivityFactory get(Activity activity) {
        if (!(activity.getLayoutInflater() instanceof AntechinusLayoutInflater)) {
            throw new RuntimeException("This activity does not wrap the Base Context! See AntechinusContextWrapper.wrap(Context)");
        }
        return (AntechinusActivityFactory) activity.getLayoutInflater();
    }

    /**
     * Uses the default configuration from {@link AntechinusConfig}
     *
     * Remember if you are defining default in the
     * {@link AntechinusConfig} make sure this is initialised before
     * the activity is created.
     *
     * @param base ContextBase to Wrap
     */
    AntechinusContextWrapper(Context base) {
        super(base);
        mResources = makeResource(base);
    }

    /**
     * Override the default AttributeId, this will always take the custom attribute defined here
     * and ignore the one set in {@link AntechinusConfig}.
     *
     * Remember if you are defining default in the
     * {@link AntechinusConfig} make sure this is initialised before
     * the activity is created.
     *
     * @param base        ContextBase to Wrap
     * @param attributeId Attribute to lookup.
     * @deprecated use {@link #wrap(android.content.Context)}
     */
    @Deprecated
    public AntechinusContextWrapper(Context base, int attributeId) {
        super(base);
        mResources = makeResource(base);
    }

    private Resources makeResource(final Context base) {
        return new Resources(base.getAssets(), base.getResources().getDisplayMetrics(), base.getResources().getConfiguration()) {
            @Override
            public String getString(int id) throws NotFoundException {
                AntechinusListenerFactory listenerFactory = AntechinusConfig.get().listenerFactory();
                if (listenerFactory != null) {
                    return listenerFactory.getString(base, id, super.getString(id));
                }
                return super.getString(id);
            }
        };
    }

    @Override
    public Resources getResources() {
        return mResources;
    }

    @Override
    public Object getSystemService(String name) {
        if (LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (mInflater == null) {
                mInflater = new AntechinusLayoutInflater(LayoutInflater.from(getBaseContext()), this, false);
            }
            return mInflater;
        }
        return super.getSystemService(name);
    }

}
