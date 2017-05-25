package com.zer.gallery.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by ngodi on 19/05/2017.
 */
public class GridViewEx extends GridView {

    private int mRequestedNumColumns = 0;

    public GridViewEx(Context context) {
        super(context);
    }

    public GridViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewEx(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setNumColumns(int numColumns) {
        super.setNumColumns(numColumns);

        if (numColumns != mRequestedNumColumns) {
            mRequestedNumColumns = numColumns;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;

        if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            // The great Android "hackatlon", the love, the magic.
            // The two leftmost bits in the height measure spec have
            // a special meaning, hence we can't use them to describe height.
            heightSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        }
        else {
            // Any other height should be respected as is.
            heightSpec = heightMeasureSpec;
        }

        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
