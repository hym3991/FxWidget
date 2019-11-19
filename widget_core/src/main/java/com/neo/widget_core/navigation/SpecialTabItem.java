package com.neo.widget_core.navigation;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.neo.widget_core.R;

import androidx.annotation.ColorInt;

/**
 *
 */
public class SpecialTabItem
{
	private int imageResId;
	private int imageWidth;
	private int imageHeight;
	private View.OnClickListener imageClickListener;
	
	private String textContent;
	private  int textColorId;
	private int textSize;
	
	private int floatImageResId;
	private int floatMarginBottom;
	private View.OnClickListener floatClickListener;
	private int floatItemLayoutId;
	
	private int itemType;
	
	public static final int SPECIAL_TYPE_FLOATING = 1;
	public static final int SPECIAL_TYPE_CUSTOM = 2;
	
	public static SpecialTabItem createSpecialItemFloat(int floatImageResId,int floatMarginBottom,
			View.OnClickListener floatClickListener)
	{
		return createSpecialItemFloat(floatImageResId,floatMarginBottom,0,floatClickListener);
	}
	
	public static SpecialTabItem createSpecialItemFloat(int floatImageResId,int floatMarginBottom,int floatItemLayoutId,
			View.OnClickListener floatClickListener)
	{
		SpecialTabItem specialTabItem = new SpecialTabItem();
		specialTabItem.setFloatImageResId( floatImageResId );
		specialTabItem.setFloatMarginBottom( floatMarginBottom );
		specialTabItem.setFloatClickListener( floatClickListener );
		specialTabItem.setFloatItemLayoutId( 0 );
		specialTabItem.setFloatItemLayoutId( floatItemLayoutId );
		specialTabItem.setItemType( SPECIAL_TYPE_FLOATING );
		return specialTabItem;
	}
	
	public static SpecialTabItem createSpecialItemCustom(
			int imageResId,
			int imageWidth,
			int imageHeight,
			View.OnClickListener imageClickListener,
			String textContent,
			@ColorInt int textColorId,
			int textSize)
	{
		SpecialTabItem specialTabItem = new SpecialTabItem();
		specialTabItem.setImageResId( imageResId );
		specialTabItem.setImageWidth( imageWidth );
		specialTabItem.setImageHeight( imageHeight );
		specialTabItem.setImageClickListener( imageClickListener );
		specialTabItem.setTextContent( textContent );
		specialTabItem.setTextColorId( textColorId );
		specialTabItem.setTextSize( textSize );
		specialTabItem.setItemType( SPECIAL_TYPE_CUSTOM );
		return specialTabItem;
	}
	
	public static SpecialTabItem createSpecialItemCustom(
			int imageResId,
			View.OnClickListener imageClickListener,
			String textContent,
			@ColorInt int textColorId,
			int textSize)
	{
		return createSpecialItemCustom( imageResId,0,0,imageClickListener,textContent,textColorId,textSize );
	}
	
	public static RelativeLayout createView(SpecialTabItem item,
			RelativeLayout rootView, Context context )
	{
		RelativeLayout relativeLayout = null;
		switch( item.getItemType() )
		{
			case SPECIAL_TYPE_CUSTOM:
				relativeLayout = createCustomView(item,rootView,context);
				break;
			case SPECIAL_TYPE_FLOATING:
				relativeLayout = createFloatingView(item,rootView,context);
				break;
		}
		return relativeLayout;
	}
	
	private static RelativeLayout createFloatingView(SpecialTabItem item,
			RelativeLayout rootView, Context context)
	{
		if( item.getFloatItemLayoutId()!=0 )
		{
			return ( RelativeLayout )LayoutInflater.from( context ).inflate(item.getFloatItemLayoutId() ,rootView );
		}
		RelativeLayout relativeLayout = ( RelativeLayout )LayoutInflater.from( context ).inflate( R.layout.widget_special_floating ,rootView );
		relativeLayout.setClipChildren( false );
		FloatingActionButton button = (FloatingActionButton)relativeLayout.findViewById( R.id.item_special_btn );
		if( item.getFloatImageResId() != 0)
		{
			//button.setBackgroundResource( item.getFloatImageResId() );
			button.setImageResource( item.getFloatImageResId() );
		}
		if( item.getFloatClickListener()!= null)
		{
			button.setOnClickListener( item.getFloatClickListener() );
		}
		if( item.getFloatMarginBottom() != 0 )
		{
			RelativeLayout.LayoutParams layoutParams = ( RelativeLayout.LayoutParams )button.getLayoutParams();
			layoutParams.setMargins( 0, -item.getFloatMarginBottom(),0,0);
			button.setLayoutParams( layoutParams );
		}
		return relativeLayout;
	}
	
	private static RelativeLayout createCustomView(SpecialTabItem item,
			RelativeLayout rootView, Context context)
	{
		RelativeLayout relativeLayout =  ( RelativeLayout )LayoutInflater.from( context ).inflate( R.layout.widget_special_custom ,rootView );
		relativeLayout.setClipChildren( false );
		ImageView imageView = ( ImageView )relativeLayout.findViewById( R.id.item_special_custom_icon );
		if( item.getImageResId() != 0 )
		{
			imageView.setImageResource( item.getImageResId() );
		}
		if( item.getImageWidth() > 0 )
		{
			imageView.getLayoutParams().width = item.getImageWidth();
		}
		if( item.getImageHeight() > 0 )
		{
			imageView.getLayoutParams().height = item.getImageHeight();
		}
		if( item.getImageClickListener() != null )
		{
			imageView.setClickable( true );
			imageView.setOnClickListener( item.getImageClickListener() );
		}
		
		TextView textView = ( TextView )relativeLayout.findViewById( R.id.item_special_custom_title );
		if( TextUtils.isEmpty( item.getTextContent() ) )
		{
			relativeLayout.removeView( textView );
		}
		else
		{
			textView.setText( item.getTextContent() );
		}
		if( item.getTextColorId() != 0 )
		{
			textView.setTextColor( item.getTextColorId() );
		}
		if( item.getTextSize() != 0 )
		{
			textView.setTextSize( item.getTextSize() );
		}
		return relativeLayout;
	}
	
	
	public int getImageResId()
	{
		return imageResId;
	}
	
	public void setImageResId( int imageResId )
	{
		this.imageResId = imageResId;
	}
	
	public int getImageWidth()
	{
		return imageWidth;
	}
	
	public void setImageWidth( int imageWidth )
	{
		this.imageWidth = imageWidth;
	}
	
	public int getImageHeight()
	{
		return imageHeight;
	}
	
	public void setImageHeight( int imageHeight )
	{
		this.imageHeight = imageHeight;
	}
	
	public View.OnClickListener getImageClickListener()
	{
		return imageClickListener;
	}
	
	public void setImageClickListener( View.OnClickListener imageClickListener )
	{
		this.imageClickListener = imageClickListener;
	}
	
	public String getTextContent()
	{
		return textContent;
	}
	
	public void setTextContent( String textContent )
	{
		this.textContent = textContent;
	}
	
	public int getTextColorId()
	{
		return textColorId;
	}
	
	public void setTextColorId( int textColorId )
	{
		this.textColorId = textColorId;
	}
	
	public int getTextSize()
	{
		return textSize;
	}
	
	public void setTextSize( int textSize )
	{
		this.textSize = textSize;
	}
	
	public int getFloatImageResId()
	{
		return floatImageResId;
	}
	
	public void setFloatImageResId( int floatImageResId )
	{
		this.floatImageResId = floatImageResId;
	}
	
	public int getFloatMarginBottom()
	{
		return floatMarginBottom;
	}
	
	public void setFloatMarginBottom( int floatMarginBottom )
	{
		this.floatMarginBottom = floatMarginBottom;
	}
	
	public View.OnClickListener getFloatClickListener()
	{
		return floatClickListener;
	}
	
	public void setFloatClickListener( View.OnClickListener floatClickListener )
	{
		this.floatClickListener = floatClickListener;
	}
	
	public int getItemType()
	{
		return itemType;
	}
	
	public void setItemType( int itemType )
	{
		this.itemType = itemType;
	}
	
	public int getFloatItemLayoutId()
	{
		return floatItemLayoutId;
	}
	
	public void setFloatItemLayoutId( int floatItemLayoutId )
	{
		this.floatItemLayoutId = floatItemLayoutId;
	}
}
