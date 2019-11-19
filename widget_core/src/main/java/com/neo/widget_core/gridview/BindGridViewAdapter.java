package com.neo.widget_core.gridview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import androidx.annotation.LayoutRes;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * <li>Package: cn.ttpai.business.widget</li>
 * <li>Author: subuhui  </li>
 * <li>Date: 2019-04-19</li>
 * <li>Description:  </li>
 */
public class BindGridViewAdapter extends BaseAdapter
{
    private BindGridInterface<ViewDataBinding> gridInterface;
    private ObservableList data = new ObservableArrayList<>();
    private int layoutRes;
    private ItemBinding itemBinding;

    public void setGridInterface(BindGridInterface gridInterface) {
        this.gridInterface = gridInterface;
    }

    public void setData(ObservableList data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setLayoutRes(@LayoutRes int layoutRes) {
        this.layoutRes = layoutRes;
    }

    @Override
    public int getCount() {
        return data != null && data.size() != 0 ? data.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding binding = null;
        if (convertView == null) {
            binding = DataBindingUtil.inflate( LayoutInflater.from(parent.getContext()), layoutRes, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setVariable(itemBinding.variableId(), data.get(position));
        gridInterface.onBindView(position, binding);

        return binding.getRoot();
    }


    @BindingAdapter(value = {"onBind", "items", "adapter", "itemBinding"}, requireAll = false)
    public static void setAdapter( GridView gridView, BindGridInterface<? extends ViewDataBinding> onBind, ObservableList items, BindGridViewAdapter adapter, ItemBinding itemBinding) {
        BindGridViewAdapter oldAdapter = (BindGridViewAdapter) gridView.getAdapter();
        if (adapter == null) {
            if (oldAdapter == null) {
                adapter = new BindGridViewAdapter();
            } else {
                adapter = oldAdapter;
            }
        }
        adapter.setGridInterface(onBind);
        adapter.setLayoutRes(itemBinding.layoutRes());
        adapter.setItemBing(itemBinding);
        if (oldAdapter != adapter) {
            gridView.setAdapter(adapter);
        }
        adapter.setData(items);
    }

    private void setItemBing(ItemBinding itemBinding) {
        this.itemBinding = itemBinding;
    }
}
