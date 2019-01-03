package com.diabin.latte.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by 甄焰鑫 on 2018/12/31.
 * 系统配置相关方法
 */

public class Configurator {
    //系统配置的相关参数
    private static final HashMap<String,Object> LATTE_CONFIGS = new HashMap<>();
    //图标信息
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    /**
     * 获取配置信息
     * @return
     */
    final HashMap<String,Object> getLatteConfigs(){
        return LATTE_CONFIGS;
    }
    /**
     * 构造方法
     */
    private Configurator(){
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
    }


    /****************************************线程安全的懒汉模式************************************************/

    /**
     * 静态内部类实现单例模式（懒汉模式）
     */
    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    /**
     * 返回配置信息
     * @return
     */
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }
    /*******************************************线程安全的懒汉模式*********************************************/

    /**
     * 配置完成
     */
    public final void configure(){
        initIcons();
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }
    /*******************************************配置域名相关信息*********************************************/
    /**
     * 配置域名
     * @param host 域名
     * @return
     */
    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }

    /**
     * 检查配置是否已完成
     */
    private void checkConfiguration(){
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady){
            throw new RuntimeException("配置尚未完成,请检查配置参数");
        }
    }

    /**
     * 检查配置的完善性,保证配置的完整性
     * @param key 配置参数
     * @param <T> 配置信息
     * @return 未配置的参数
     */
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key){
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key.name());
    }

    /*******************************************配置域名相关信息*********************************************/
    /*******************************************配置图标相关信息*********************************************/
    /**
     * 配置图标
     * @param descriptor
     * @return
     */
    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }

    /**
     * 初始化图标
     */
    private void initIcons(){
        if(ICONS.size() > 0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for(int i=1;i<ICONS.size();i++){
                initializer.with(ICONS.get(i));
            }
        }
    }
    /*******************************************配置图标相关信息*********************************************/
}
