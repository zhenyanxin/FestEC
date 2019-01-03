package com.diabin.latte.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.diabin.latte.R;
import com.diabin.latte.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by 甄焰鑫 on 2019/1/1.
 */
public class LatteLoader {
    //加载窗体相对屏幕的缩放比
    private static final int LOADER_SIZE_SCALE = 8;
    //加载窗体相对屏幕的偏移量
    private static final int LOADER_OFFSET_SCALE = 10;
    //用于存储所有的加载窗体Dialog
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    //默认窗体类型
    private static final String DEFAULT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();
    /**
     * 带类型显示窗体
     * @param context
     * @param type
     */
    public static void showLoading(Context context,Enum<LoaderStyle> type){
        showLoading(context,type.name());
    }
    /**
     * 带类型显示窗体
     * @param context
     * @param type
     */
    public static void showLoading(Context context,String type){
        //初始化载入样式
        final AppCompatDialog dialog = new AppCompatDialog(context,R.style.dialog);
        //创建一个对应类型的视图
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type,context);
        //将视图加入Dialog
        dialog.setContentView(avLoadingIndicatorView);
        //获取屏幕尺寸
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();
        //获取窗口
        final Window dialogWindow = dialog.getWindow();
        //如果获取到窗口,进行比例缩放和偏移
        if(dialogWindow!=null){
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth/LOADER_SIZE_SCALE;
            lp.height = deviceHeight/LOADER_SIZE_SCALE;
            lp.height = lp.height+deviceHeight/LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    /**
     * 不带类型的显示窗体
     * @param context
     */
    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }
    /**
     * 停止显示加载窗体
     *
     */
    public static void stopLoading(){
        for(AppCompatDialog dialog : LOADERS){
            if(dialog!=null){
                if(dialog.isShowing()){
                    dialog.cancel();
                }
            }
        }
    }
}

