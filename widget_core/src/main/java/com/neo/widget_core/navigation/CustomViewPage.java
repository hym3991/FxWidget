package com.neo.widget_core.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 *
 */
public class CustomViewPage extends ViewPager
{
	private boolean isSlide;
	
	public boolean isSlide()
	{
		return isSlide;
	}
	
	public void setSlide( boolean slide )
	{
		isSlide = slide;
	}
	
	public CustomViewPage( @NonNull Context context )
	{
		super( context );
	}
	
	public CustomViewPage( @NonNull Context context ,
			@Nullable AttributeSet attrs )
	{
		super( context , attrs );
	}
	
	@Override
	public boolean onInterceptTouchEvent( MotionEvent ev )
	{
		return isSlide();
	}
	
	@Override
	public boolean onTouchEvent( MotionEvent ev )
	{
		return isSlide();
	}
}
