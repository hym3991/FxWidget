package com.neo.widget_core.gridview;

import androidx.databinding.ViewDataBinding;

/**
 * <li>Package: cn.ttpai.business.widget</li>
 * <li>Author: subuhui  </li>
 * <li>Date: 2019-04-19</li>
 * <li>Description:  </li>
 */
public interface BindGridInterface<T extends ViewDataBinding> {
    void onBindView( int position ,
		    T viewDataBinding );
}
