package com.neo.widget_core.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neo.widget_core.R;

import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * @author hongyaming
 * @description 自定义的toolbar
 * 使用ToolBar替代现有的TitleBar
 * @date 14:36 2019-08-09
 */
public class CustomToolbar extends LinearLayout
{
	private Toolbar toolbar;
	private TextView textView;
	private AppCompatActivity activity;
	private int menuId = 0;
	public CustomToolbar( Context context )
	{
		this( context ,null,0);
	}
	
	public CustomToolbar( Context context , @Nullable AttributeSet attrs )
	{
		this( context , attrs ,0);
	}
	
	public CustomToolbar( Context context , @Nullable AttributeSet attrs , int defStyleAttr )
	{
		super( context , attrs , defStyleAttr );
		initView(context , attrs , defStyleAttr);
	}
	
	public Toolbar get()
	{
		return toolbar;
	}
	
	public CustomToolbar bindActivity( AppCompatActivity activity)
	{
		if( activity != null )
		{
			this.activity = activity;
			activity.setSupportActionBar( toolbar );
		}
		return this;
	}
	
	public CustomToolbar setMenu(@MenuRes int menuRes)
	{
		if( menuRes == 0 )
		{
			hideMenu();
			return this;
		}
		if( menuRes != this.menuId )
		{
			this.menuId = menuRes;
			activity.invalidateOptionsMenu();
		}
		show( true );
		return this;
	}
	
	public void show(boolean isShow)
	{
		if( toolbar != null && toolbar.getParent() != null)
		{
			this.setVisibility( isShow?VISIBLE:GONE );
		}
	}
	
	public void onPrepareOptionsMenu( Menu menu )
	{
		hideMenu();
		if( menuId == 0 )
		{
			return;
		}
		activity.getMenuInflater().inflate( menuId,menu );
	}
	
	public CustomToolbar setBackListener( final CustomToolBarBackListener listener )
	{
		toolbar.setNavigationOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				if( listener != null )
				{
					listener.onBarLeftCkick();
				}
			}
		} );
		return this;
	}
	
	public CustomToolbar setMenuListener( final CustomToolBarMenuListener listener )
	{
		toolbar.setOnMenuItemClickListener( new Toolbar.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick( MenuItem item )
			{
				if( listener != null )
				{
					listener.onMenuItemClick( item.getItemId() );
				}
				return true;
			}
		} );
		return this;
	}
	
	public CustomToolbar setBarBackgroundColor(int color)
	{
		toolbar.setBackgroundColor( color );
		return this;
	}
	
	public CustomToolbar setTitleTextColor(int color){
		textView.setTextColor( color );
		return this;
	}
	
	private void initView( Context context , @Nullable AttributeSet attrs , int defStyleAttr )
	{
		LayoutInflater.from(context).inflate( R.layout.widget_custom_toolbar, this, true);
		
		toolbar = findViewById( R.id.widget_toolbar );
		textView = findViewById( R.id.widget_toolbar_title_tv );
		toolbar.setTitle( "" );
		
		if( attrs != null )
		{
			initView( context,attrs );
		}
	}
	
	private void initView( final Context context , @Nullable AttributeSet attrs)
	{
		TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar);
		boolean leftIconShow = attr.getBoolean( R.styleable.CustomToolbar_leftIconShow,false );
		int leftIcoSrc = attr.getResourceId( R.styleable.CustomToolbar_leftIcoSrc,-1 );
		String titleText = attr.getString( R.styleable.CustomToolbar_titleText );
		int titleTextColor = attr.getColor( R.styleable.CustomToolbar_titleTextColor ,-1);
		int toolbarBgColor = attr.getColor( R.styleable.CustomToolbar_toolbarBgColor,-1 );
		//左边按钮设置
		if( !leftIconShow )
		{
			toolbar.setNavigationIcon( null );
		}else
		{
			toolbar.setNavigationIcon( leftIcoSrc );
		}
		
		//设置titile
		if( !TextUtils.isEmpty( titleText ) )
		{
			textView.setText( titleText );
		}else
		{
			textView.setVisibility( INVISIBLE );
		}
		if( titleTextColor != -1 )
		{
			textView.setTextColor( titleTextColor );
		}
		
		if( toolbarBgColor != -1 )
		{
			toolbar.setBackgroundColor( toolbarBgColor );
		}
		attr.recycle();
	}
	
	public void hideMenu()
	{
		toolbar.getMenu().clear();
	}
	
	public void hideNavigationIcon(){toolbar.setNavigationIcon( null );}
	
	public interface CustomToolBarBackListener
	{
		void onBarLeftCkick();
	}
	
	public interface CustomToolBarMenuListener
	{
		void onMenuItemClick( int id );
	}
}
