package com.neo.widget_core.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class AutoGridView extends GridView
{

    public AutoGridView( Context context) {
        super(context);
    }

    public AutoGridView( Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoGridView( Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;
        // 这几行代码比较重要
        if(getLayoutParams().height == LayoutParams.WRAP_CONTENT){
            heightSpec = MeasureSpec.makeMeasureSpec( Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
        }else{
            heightSpec = heightMeasureSpec;
        }

        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
