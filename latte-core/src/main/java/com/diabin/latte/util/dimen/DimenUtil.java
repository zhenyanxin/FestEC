package com.diabin.latte.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.diabin.latte.app.Latte;

/**
 * Created by 甄焰鑫 on 2019/1/1.
 * 屏幕尺寸工具类
 */
public class DimenUtil {
    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreenWidth(){
        final Resources resources = Latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
    /**
     * 获取屏幕高度
     * @return
     */
    public static int getScreenHeight(){
        final Resources resources = Latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
