package com.zer.gallery.customview;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.flyco.animation.Attention.Swing;
import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;
import com.zer.gallery.R;

/**
 * Created by ngodi on 15/05/2016.
 */
public class CustomBaseDialog extends BaseDialog<CustomBaseDialog> {
    private Context context;
    public CustomBaseDialog(Context context) {
        super(context);
        this.context = context;
    }
    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new Swing());

        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(context, R.layout.dialog_custom_base, null);
        inflate.setBackgroundDrawable(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));

        return inflate;
    }

    @Override
    public void setUiBeforShow() {


    }

}
