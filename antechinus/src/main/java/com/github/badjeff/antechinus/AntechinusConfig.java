package com.github.badjeff.antechinus;

import android.os.Build;

/**
 * Created by jeff on 12/16/15.
 * Project: Antechinus
 */
public class AntechinusConfig {

    private static AntechinusConfig sInstance;

    /**
     * Set the default Antechinus Config
     *
     * @param antechinusConfig the config build using the builder.
     * @see AntechinusConfig.Builder
     */
    public static void initDefault(AntechinusConfig antechinusConfig) {
        sInstance = antechinusConfig;
    }

    /**
     * The current Antechinus Config.
     * If not set it will create a default config.
     */
    public static AntechinusConfig get() {
        if (sInstance == null)
            sInstance = new AntechinusConfig(new Builder());
        return sInstance;
    }

    /**
     * Use Reflection to inject the private factory.
     */
    private final boolean mReflection;
    /**
     * Use Reflection to intercept CustomView inflation with the correct Context.
     */
    private final boolean mCustomViewCreation;
    /**
     * Default AntechinusListenerFactory
     */
    private final AntechinusListenerFactory mListenerFactory;

    protected AntechinusConfig(Builder builder) {
        mReflection = builder.reflection;
        mCustomViewCreation = builder.customViewCreation;
        mListenerFactory = builder.listenerFactory;
    }

    public boolean isReflection() {
        return mReflection;
    }

    public boolean isCustomViewCreation() {
        return mCustomViewCreation;
    }

    public AntechinusListenerFactory listenerFactory() { return mListenerFactory; }

    public static class Builder {
        /**
         * Default AttrID if not set.
         */
        public static final int INVALID_ATTR_ID = -1;
        /**
         * Use Reflection to inject the private factory. Doesn't exist pre HC. so defaults to false.
         */
        private boolean reflection = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
        /**
         * Use Reflection to intercept CustomView inflation with the correct Context.
         */
        private boolean customViewCreation = true;
        /**
         * Default AntechinusListenerFactory
         */
        private AntechinusListenerFactory listenerFactory = null;

        /**
         * <p>Turn of the use of Reflection to inject the private factory.
         * This has operational consequences! Please read and understand before disabling.
         * <b>This is already disabled on pre Honeycomb devices. (API 11)</b></p>
         *
         * <p> If you disable this you will need to override your {@link android.app.Activity#onCreateView(android.view.View, String, android.content.Context, android.util.AttributeSet)}
         * as this is set as the {@link android.view.LayoutInflater} private factory.</p>
         * <br>
         * <b> Use the following code in the Activity if you disable FactoryInjection:</b>
         * <pre><code>
         * {@literal @}Override
         * {@literal @}TargetApi(Build.VERSION_CODES.HONEYCOMB)
         * public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
         *   return AntechinusContextWrapper.onActivityCreateView(this, parent, super.onCreateView(parent, name, context, attrs), name, context, attrs);
         * }
         * </code></pre>
         */
        public Builder disablePrivateFactoryInjection() {
            this.reflection = false;
            return this;
        }

        /**
         * Due to the poor inflation order where custom views are created and never returned inside an
         * {@code onCreateView(...)} method. We have to create CustomView's at the latest point in the
         * overrideable injection flow.
         *
         * On HoneyComb+ this is inside the {@link android.app.Activity#onCreateView(android.view.View, String, android.content.Context, android.util.AttributeSet)}
         * Pre HoneyComb this is in the {@link android.view.LayoutInflater.Factory#onCreateView(String, android.util.AttributeSet)}
         *
         * We wrap base implementations, so if you LayoutInflater/Factory/Activity creates the
         * custom view before we get to this point, your view is used. (Such is the case with the
         * TintEditText etc)
         *
         * The problem is, the native methods pass there parents context to the constructor in a really
         * specific place. We have to mimic this in {@link AntechinusLayoutInflater#createCustomViewInternal(android.view.View, android.view.View, String, android.content.Context, android.util.AttributeSet)}
         * To mimic this we have to use reflection as the Class constructor args are hidden to us.
         *
         * We have discussed other means of doing this but this is the only semi-clean way of doing it.
         * (Without having to do proxy classes etc).
         *
         * Calling this will of course speed up inflation by turning off reflection, but not by much,
         * But if you want Antechinus to inject the correct typeface then you will need to make sure your CustomView's
         * are created before reaching the LayoutInflater onViewCreated.
         */
        public Builder disableCustomViewInflation() {
            this.customViewCreation = false;
            return this;
        }

        public Builder listenerFactory(AntechinusListenerFactory listenerFactory) {
            this.listenerFactory = listenerFactory;
            return this;
        }

        public AntechinusConfig build() {
            return new AntechinusConfig(this);
        }
    }
}
