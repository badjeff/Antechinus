package com.github.badjeff.antechinus.sample;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.badjeff.antechinus.AntechinusContextWrapper;

import static butterknife.ButterKnife.findById;

/**
 * Created by jeff on 12/16/15.
 * For Antechinus.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inject pragmatically
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, PlaceholderFragment.getInstance())
                .commit();
    }

    /*
        Uncomment if you disable PrivateFactory injection. See AntechinusConfig#disablePrivateFactoryInjection()
     */
//    @Override
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public View onCreateView(View parent, String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//        return AntechinusContextWrapper.onActivityCreateView(this, parent, super.onCreateView(parent, name, context, attrs), name, context, attrs);
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(AntechinusContextWrapper.wrap(newBase));
    }

}
