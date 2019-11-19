package com.neo.widget_core.navigation;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 *
 */
public class CustomFragmentAdapter extends FragmentPagerAdapter
{
	
	private ArrayList<Fragment> list;
	
	public CustomFragmentAdapter( FragmentManager fm ,
			ArrayList<Fragment> list )
	{
		super( fm );
		this.list = list;
	}
	
	@Override
	public Fragment getItem( int position )
	{
		return list.get( position );
	}
	
	@Override
	public int getCount()
	{
		return list.size();
	}
}
