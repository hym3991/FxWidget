package com.neo.widget_core.autolayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.neo.widget_core.autolayout.utils.AutoLayoutHelper;

import androidx.constraintlayout.widget.ConstraintLayout;

public class AutoConstraintLayout extends ConstraintLayout
{
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);//autoLayoutHelper 处理数值转换
    public AutoConstraintLayout( Context context) {
        super(context);
    }

    public AutoConstraintLayout( Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoConstraintLayout( Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public AutoConstraintLayout.LayoutParams generateLayoutParams( AttributeSet attrs)
    {
        return new AutoConstraintLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (!isInEditMode())
            mHelper.adjustChildren();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
    }
    public static class LayoutParams extends ConstraintLayout.LayoutParams
            implements AutoLayoutHelper.AutoLayoutParams
    {
        private AutoLayoutInfo mAutoLayoutInfo;

        public LayoutParams( Context c, AttributeSet attrs)
        {
            super(c, attrs);
            mAutoLayoutInfo = AutoLayoutHelper.getAutoLayoutInfo(c, attrs);
        }

        public LayoutParams(int width, int height)
        {
            super(width, height);
        }

        public LayoutParams( ViewGroup.LayoutParams source)
        {
            super(source);
        }

        public LayoutParams( ViewGroup.MarginLayoutParams source)
        {
            super(source);
        }

        @Override
        public AutoLayoutInfo getAutoLayoutInfo()
        {
            return mAutoLayoutInfo;
        }


    }
}
