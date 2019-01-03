package com.diabin.latte.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.diabin.latte.R;
import com.diabin.latte.delegates.LatteDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by 甄焰鑫 on 2019/1/1.
 */
public abstract class ProxyActivity extends SupportActivity {
    /**
     * 定义根Delegate
     * @return
     */
    public abstract LatteDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }

    /**
     * 初始化容器
     * @param savedInstanceState
     */
    private void initContainer(@Nullable Bundle savedInstanceState){
        //创建容器
        final ContentFrameLayout container = new ContentFrameLayout(this);
        //设置容器ID
        container.setId(R.id.delegate_container);
        //将容器填入视图
        setContentView(container);
        //第一次使用容器进行初始加载
        if(savedInstanceState==null){
            loadRootFragment(R.id.delegate_container,setRootDelegate());
        }
    }

    /**
     * 释放应用时垃圾回收机制对视图进行回收
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
