package com.diabin.latte.net;

import com.diabin.latte.app.ConfigType;
import com.diabin.latte.app.Latte;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 甄焰鑫 on 2019/1/1.
 * REST创建器,创建RestClient,初始化类(使得相关类随着应用的启动而初始化)
 */
public class RestCreator {

    //Rest参数
    private static final class ParamsHolder{
        public static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }

    /**
     * 获取RestService
     * @return
     */
    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    /**********************************************以下静态内部类均为初始化类参数*****************************************************/
    private  static final class RetrofitHolder{
        //基础URL
        private static final String BASE_URL = (String) Latte.getConfigurations().get(ConfigType.API_HOST.name());
        //客户端RETROFIT
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    /**
     * 超时处理
     */
    private static final class OKHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT,TimeUnit.SECONDS)
                .build();
    }

    /**
     * 创建RestService类
     */
    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
}
