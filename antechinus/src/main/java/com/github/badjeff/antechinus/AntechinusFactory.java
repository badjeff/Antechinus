package com.github.badjeff.antechinus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jeff on 12/16/15.
 * Project: Antechinus
 */
class AntechinusFactory {

    public AntechinusFactory() {
    }

    /**
     * Handle the created view
     *
     * @param view    nullable.
     * @param context shouldn't be null.
     * @param attrs   shouldn't be null.
     * @return null if null is passed in.
     */

    public View onViewCreated(View view, Context context, AttributeSet attrs) {
        if (view != null && view.getTag(R.id.antechinus_tag_id) != Boolean.TRUE) {
            onViewCreatedInternal(view, context, attrs);
            view.setTag(R.id.antechinus_tag_id, Boolean.TRUE);
        }
        return view;
    }

    void onViewCreatedInternal(View view, final Context context, AttributeSet attrs) {
        AntechinusUtils.applyResourceStringToView(context, view, attrs);
    }

}
