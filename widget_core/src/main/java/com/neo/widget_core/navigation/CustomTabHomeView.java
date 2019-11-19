package com.neo.widget_core.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.neo.widget_core.R;
import com.neo.widget_core.autolayout.AutoLinearLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 *
 */
public class CustomTabHomeView extends AutoLinearLayout
{
	private Context context;
	private CustomViewPage viewPager;
	public BottomNavigationView bnv;
	private AppCompatActivity activity;
	private SparseArray<Fragment> list = new SparseArray<>();
	private ArrayList<Fragment> fragments = new ArrayList<>();
	private ArrayList<BadgeBean> badgeList = new ArrayList<>();
	
	private SpecialTabItem specialTabItem;
	private int insertIndex;
	
	public CustomTabHomeView( Context context )
	{
		this( context,null,0 );
	}
	
	public CustomTabHomeView( Context context ,
			AttributeSet attrs )
	{
		this( context , attrs ,0);
	}
	
	public CustomTabHomeView( Context context ,
			AttributeSet attrs ,
			int defStyleAttr )
	{
		super( context , attrs , defStyleAttr );
		this.context = context;
		initView(attrs);
	}
	
	private void initView( AttributeSet attrs )
	{
		LayoutInflater.from( context ).inflate( R.layout.widget_custom_tabhome,this,true );
		viewPager = findViewById( R.id.widget_tabhome_vp );
		bnv = findViewById( R.id.widget_tabhome_bnv );
		initViewAttr(attrs);
	}
	
	private void initViewAttr( AttributeSet attrs)
	{
		initBnvAttr(attrs);
		initViewPageAttr();
	}
	
	private void initBnvAttr( AttributeSet attrs)
	{
		int menu = 0;
		int normalText = R.style.bottom_normal_text;
		int selectText = R.style.bottom_selected_text;
		if( attrs != null )
		{
			TypedArray typedArray = context.obtainStyledAttributes( attrs,R.styleable.CustomTabHomeView );
			menu = typedArray.getResourceId( R.styleable.CustomTabHomeView_bottomMenu,0 );
			normalText = typedArray.getResourceId( R.styleable.CustomTabHomeView_bottomNormlaText,R.style.bottom_normal_text );
			selectText = typedArray.getResourceId( R.styleable.CustomTabHomeView_bottomSelectText,R.style.bottom_selected_text );
			typedArray.recycle();
		}
		//设置menu
		bnv.inflateMenu( menu );
		bnv.setClipChildren( false );
		//选中文字样式
		//未选中文字样式
		bnv.setItemTextAppearanceInactive(normalText);
		bnv.setItemTextAppearanceActive(selectText);
		//清除动画效果
		bnv.setLabelVisibilityMode( LabelVisibilityMode.LABEL_VISIBILITY_LABELED );
		//删除默认的选中效果
		bnv.setItemIconTintList( null );
		bnv.setItemHorizontalTranslationEnabled(false);
	}
	
	private void initViewPageAttr()
	{
		viewPager.setSlide( false );
		viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageScrolled( int position ,
					float positionOffset ,
					int positionOffsetPixels )
			{ }
			
			@Override
			public void onPageSelected( int position )
			{
				if( bnv != null)
				{
					if( insertIndex != 0 && position >= insertIndex )
					{
						position ++;
					}
					//滑动页面后做的事，这里与BottomNavigationView结合，使其与正确page对应
					bnv.getMenu().getItem( position ).setChecked( true );
				}
			}
			
			@Override
			public void onPageScrollStateChanged( int state )
			{
			
			}
		} );
	}
	
	public CustomTabHomeView bindActivity( AppCompatActivity appCompatActivity )
	{
		this.activity = appCompatActivity;
		return this;
	}
	
	public CustomTabHomeView addItem( Fragment fragment,int navMenuName )
	{
		return addItem( fragment,navMenuName,null );
	}
	
	public CustomTabHomeView addItem(Fragment fragment,int navMenuName , Bundle bundle)
	{
		if( fragment != null )
		{
			if( bundle != null )
			{
				fragment.setArguments( bundle );
			}
			fragments.add( fragment );
			list.put( navMenuName,fragment );
		}
		return this;
	}
	
	public CustomTabHomeView addBadge(int navMenuName,int badgeNumberText,boolean clickRemove)
	{
		addBadge( navMenuName, badgeNumberText+"", clickRemove );
		return this;
	}
	
	public CustomTabHomeView addBadge(int navMenuName,
			String badgeNumberText,boolean clickRemove)
	{
		BadgeBean badgeBean = new BadgeBean();
		badgeBean.setNavMenuName( navMenuName );
		badgeBean.setBadgeNumberText( badgeNumberText );
		badgeBean.setClickRemove( clickRemove );
		badgeBean.setBadgeView( initBadgeView( badgeNumberText ) );
		badgeList.add( badgeBean );
		return this;
	}
	
	public void changeBadgeText(int navMenuName,int newNumber)
	{
		changeBadgeText( navMenuName,newNumber+"" );
	}
	
	public CustomTabHomeView addSpecialTabItem(SpecialTabItem specialTabItem,int insertIndex)
	{
		this.specialTabItem = specialTabItem;
		this.insertIndex = insertIndex;
		return this;
	}
	
	public void changeBadgeText(int navMenuName,
			String newNumberStr)
	{
		if( badgeList.size() > 0 )
		{
			for( BadgeBean bean : badgeList )
			{
				if( bean.getNavMenuName() == navMenuName )
				{
					bean.getBadgeView().setText(newNumberStr);
				}
			}
		}
	}

	public CustomTabHomeView build()
	{
		CustomFragmentAdapter adapter = new CustomFragmentAdapter( activity.getSupportFragmentManager() ,fragments);
		viewPager.setAdapter( adapter );
		viewPager.setOffscreenPageLimit( 1 );
		bnv.setOnNavigationItemSelectedListener( menuItem -> {
			if( list.get( menuItem.getItemId() ) == null )
			{
				return false;
			}
			
			viewPager.setCurrentItem( fragments.indexOf( list.get( menuItem.getItemId() ) ) );
			return false;
		} );
		if( specialTabItem != null )
		{
			addSpecialTabItem();
		}
		for( int i = 0 ; i < badgeList.size() ; i++ )
		{
			final BadgeBean bean = badgeList.get( i );
			final View badgeView = bean.getBadgeView();
			final BottomNavigationItemView itemView = getBnvItemView(bean.getNavMenuName());
			addBadgeView( itemView,badgeView );
			badgeView.setOnTouchListener( ( View v , MotionEvent event ) -> {
				if( bean.isClickRemove() )
				{
					itemView.removeView( badgeView );
				}
				return false;
			} );
		}
		return this;
	}
	
	private void addSpecialTabItem()
	{
		int orderId = bnv.getMenu().getItem( insertIndex-1 ).getOrder();
		bnv.getMenu().add( 0,0,orderId,"" );
		
		BottomNavigationMenuView menuView = (BottomNavigationMenuView) bnv.getChildAt(0);
		menuView.setClipChildren( false );
		
		BottomNavigationItemView itemView = (BottomNavigationItemView)menuView.getChildAt( insertIndex );
		itemView.setClipChildren( false );
		itemView.removeAllViews();
		
		RelativeLayout rootView = new RelativeLayout( context );
		ViewGroup.LayoutParams item_params = itemView.getLayoutParams();
		rootView.setClipChildren( false );
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
		{
			rootView.setBackground( menuView.getBackground() );
		}
		rootView.setLayoutParams( item_params );
		
		SpecialTabItem.createView( specialTabItem,rootView,context);
		
		menuView.removeViewAt( insertIndex );
		menuView.addView( rootView,insertIndex );
	}
	
	private TextView initBadgeView( String badgeNumberText)
	{
		final TextView view = new TextView( context );
		view.setBackgroundResource( R.drawable.shape_red_fe4a49_circle );
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
		view.setGravity( Gravity.CENTER_HORIZONTAL );
		params.gravity = Gravity.CENTER_HORIZONTAL;
		view.setLayoutParams( params );
		view.setTextColor( Color.WHITE );
		view.setTextSize( 10 );
		view.setText( badgeNumberText );
		view.setPadding( 6,0,6,0 );
		view.setTag( "badge" );
		return view;
	}
	
	private BottomNavigationItemView getBnvItemView(int navMenuName)
	{
		BottomNavigationMenuView menuView = (BottomNavigationMenuView) bnv.getChildAt(0);
		Fragment fragment = list.get( navMenuName );
		int index = fragments.indexOf(fragment);
		if( insertIndex != 0 && index >= insertIndex )
		{
			index ++;
		}
		final BottomNavigationItemView itemView = (BottomNavigationItemView)menuView.getChildAt(index);
		return itemView;
	}
	
	private void addBadgeView(BottomNavigationItemView itemView,
			View badgeView)
	{
		ImageView icon = ( ImageView )itemView.getChildAt( 0 );
		int iconWidth = icon.getLayoutParams().width;
		int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		
		itemView.measure(width,height);
		height = itemView.getMeasuredHeight();
		width = itemView.getMeasuredWidth();
		
		badgeView.measure( width,height );
		int badge_width = badgeView.getMeasuredWidth();
		FrameLayout.LayoutParams params = ( FrameLayout.LayoutParams )badgeView.getLayoutParams();
		params.setMargins( width/2+iconWidth/2-badge_width/2,badge_width/2,0,0 );
		itemView.addView( badgeView );
	}
	
	class BadgeBean
	{
		int navMenuName;
		String badgeNumberText;
		boolean clickRemove;
		TextView badgeView;
		
		public int getNavMenuName()
		{
			return navMenuName;
		}
		
		public void setNavMenuName( int navMenuName )
		{
			this.navMenuName = navMenuName;
		}
		
		public String getBadgeNumberText()
		{
			return badgeNumberText;
		}
		
		public void setBadgeNumberText( String badgeNumberText )
		{
			this.badgeNumberText = badgeNumberText;
		}
		
		public boolean isClickRemove()
		{
			return clickRemove;
		}
		
		public void setClickRemove( boolean clickRemove )
		{
			this.clickRemove = clickRemove;
		}
		
		public TextView getBadgeView()
		{
			return badgeView;
		}
		
		public void setBadgeView( TextView badgeView )
		{
			this.badgeView = badgeView;
		}
	}
}
