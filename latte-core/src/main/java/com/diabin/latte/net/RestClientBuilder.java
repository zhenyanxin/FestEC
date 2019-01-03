package com.diabin.latte.net;

import android.content.Context;

import com.diabin.latte.net.callback.IError;
import com.diabin.latte.net.callback.IFailure;
import com.diabin.latte.net.callback.IRequest;
import com.diabin.latte.net.callback.ISuccess;
import com.diabin.latte.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by 甄焰鑫 on 2019/1/1.
 * RestClient回调
 */
public class RestClientBuilder {

    private String mUrl = null;
    private static final Map<String, Object> PARAMS = RestCreator.getParams();
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;

    RestClientBuilder() {

    }

    /**
     * 请求网址
     * @param url
     * @return
     */
    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    /**
     * 请求参数
     * @param params
     * @return
     */
    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    /**
     * 请求参数
     * @param key
     * @param value
     * @return
     */
    public final RestClientBuilder params(String key, Object value) {
        this.PARAMS.put(key, value);
        return this;
    }

    /**
     * 请求行
     * @param raw
     * @return
     */
    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    public final RestClientBuilder file(String file) {
        this.mFile = new File(file);
        return this;
    }

    /**
     * 请求信息
     * @param iRequest
     * @return
     */
    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    /**
     * 请求成功
     * @param iSuccess
     * @return
     */
    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    /**
     * 请求失败
     * @param iFailure
     * @return
     */
    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    /**
     * 请求错误
     * @param iError
     * @return
     */
    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    /**
     * 加载连接请求并自定义样式的Loading
     * @param context
     * @param style
     * @return
     */
    public final RestClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    /**
     * 加载连接请求并使用默认样式样式的Loading
     * @param context
     * @return
     */
    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }
    /**
     * 创建RestClient
     *
     * @return
     */
    public final RestClient build() {
        return new RestClient(mUrl, PARAMS, mIRequest, mISuccess, mIFailure, mIError, mBody,mFile, mContext, mLoaderStyle);
    }
}
