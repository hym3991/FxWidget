package com.neo.widget_core.bindingadapter.command;

import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * <li>Package:com.ttp.newcore.binding.command</li>
 * <li>Author: yehaijian </li>
 * <li>Date: 2018/3/28</li>
 * <li>Description:   </li>
 */
public class LoadMoreReplyCommand<T> extends ReplyCommand<T> {
    private int pageSize;
    public LoadMoreReplyCommand( Action0 execute,int pageSize) {
        super(execute);
        this.pageSize=pageSize;
    }

    public LoadMoreReplyCommand( Action1 execute,int pageSize) {
        super(execute);
        this.pageSize=pageSize;
    }

    public LoadMoreReplyCommand(Action0 execute, Func0 canExecute0) {
        super(execute, canExecute0);
    }

    public LoadMoreReplyCommand(Action1 execute, Func0 canExecute0) {
        super(execute, canExecute0);
    }

    public int getPageSize() {
        return pageSize;
    }
}
