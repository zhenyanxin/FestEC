package com.diabin.latte.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by 甄焰鑫 on 2018/12/31
 * 系统配置相关类.
 */

public final class Latte {
    /**
     * 将配置初始化整个应用中
     * @param context 上下文
     * @return 配置完成的对象
     */
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }

    /**
     * 获取配置信息
     * @return
     */
    public static HashMap<String,Object> getConfigurations(){
        return Configurator.getInstance().getLatteConfigs();
    }

    /**
     * 获取应用上下文
     * @return
     */
    public static Context getApplication(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
