package com.neo.widget_core.navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

/**
 *
 */
public class CustomTabNavHostFragment extends NavHostFragment
{
	@NonNull
	@Override
	protected Navigator<? extends FragmentNavigator.Destination> createFragmentNavigator()
	{
		return new CustomNavigator(requireContext(),getChildFragmentManager(),getId());
	}
	
	@Navigator.Name("fragment")
	public class CustomNavigator extends FragmentNavigator
	{
		private Context context;
		private FragmentManager manager;
		private int containerId;
		
		public CustomNavigator( Context context,FragmentManager manager,int containerId)
		{
			super(context,manager,containerId);
			this.context = context;
			this.manager = manager;
			this.containerId = containerId;
		}
		
		@Nullable
		@Override
		public NavDestination navigate( @NonNull Destination destination ,
				@Nullable Bundle args ,
				@Nullable NavOptions navOptions ,
				@Nullable Navigator.Extras navigatorExtras )
		{
			try
			{
				if( manager.isStateSaved() )
				{
					return null;
				}
				
				String className = destination.getClassName();
				if (className.charAt(0) == '.')
				{
					className = context.getPackageName() + className;
				}
				final FragmentTransaction ft = manager.beginTransaction();
			
				int enterAnim = navOptions != null ? navOptions.getEnterAnim() : -1;
				int exitAnim = navOptions != null ? navOptions.getExitAnim() : -1;
				int popEnterAnim = navOptions != null ? navOptions.getPopEnterAnim() : -1;
				int popExitAnim = navOptions != null ? navOptions.getPopExitAnim() : -1;
				if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
					enterAnim = enterAnim != -1 ? enterAnim : 0;
					exitAnim = exitAnim != -1 ? exitAnim : 0;
					popEnterAnim = popEnterAnim != -1 ? popEnterAnim : 0;
					popExitAnim = popExitAnim != -1 ? popExitAnim : 0;
					ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
				}
				
				String tag = String.valueOf( destination.getId() );
				Fragment currentFragment = manager.getPrimaryNavigationFragment();
				if( currentFragment != null && currentFragment.isAdded() )
				{
					ft.hide( currentFragment );
				}
				
				Fragment frag = manager.findFragmentByTag( tag );
				if( frag == null )
				{
					frag = instantiateFragment(context,manager,className,args);
					frag.setArguments( args );
					ft.add( containerId,frag,tag );
					Log.d( "nav","frag == null ->"+frag );
				}else
				{
					ft.show( frag );
				}
				ft.setPrimaryNavigationFragment( frag );
				
				ft.setReorderingAllowed(true);
				ft.commitAllowingStateLoss();
				return destination;
			}catch( Exception e )
			{
				e.printStackTrace();
				return super.navigate(destination, args, navOptions, navigatorExtras);
			}
		}
	}
}
