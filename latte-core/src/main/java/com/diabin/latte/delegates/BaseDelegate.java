package com.diabin.latte.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by 甄焰鑫 on 2019/1/1.
 */
public abstract class BaseDelegate extends SwipeBackFragment {
    /**
     * 设置框架的Layout
     * @return
     */
    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;
    //设置Layout
    public abstract Object setLayout();
    //强制子类使用该方法填充视图

    /**
     * 对控件进行集中操作
     *
     * @param savedInstanceState
     * @param rootView
     */
    public abstract void onBindView(@Nullable Bundle savedInstanceState,View rootView);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //视图的Layout
        View rootView = null;
        if(setLayout() instanceof Integer){
            rootView = inflater.inflate((Integer) setLayout(),container,false);
        }else if(setLayout() instanceof View){
            rootView = (View) setLayout();
        }
        if(rootView != null){
            //绑定视图
            mUnbinder = ButterKnife.bind(this,rootView);
            //填充视图
            onBindView(savedInstanceState,rootView);
        }
        return rootView;
    }

    /**
     * 释放后台
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
    }
}
