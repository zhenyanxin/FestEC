package com.diabin.latte.net.download;

import android.os.AsyncTask;

import com.diabin.latte.net.RestCreator;
import com.diabin.latte.net.callback.IError;
import com.diabin.latte.net.callback.IFailure;
import com.diabin.latte.net.callback.IRequest;
import com.diabin.latte.net.callback.ISuccess;
import com.diabin.latte.util.file.FileUtil;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 甄焰鑫 on 2019/1/4.
 */
public class DownloadHandler {

    private final String URL;
    private final IRequest REQUEST;
    private static final WeakHashMap<String,Object> PARAMS=RestCreator.getParams();
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;


    public DownloadHandler(String url, IRequest request, String downloadDir, String extension, String name, ISuccess success, IFailure failure, IError error) {
        this.URL = url;
        this.REQUEST = request;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    public final void handleDownload(){
        if(REQUEST != null){
            REQUEST.onRequestStart();
        }
        RestCreator.getRestService().download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            final ResponseBody responseBody = response.body();
                            final SaveFileTask task = new SaveFileTask(REQUEST,SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DIR,EXTENSION,responseBody,NAME);
                            //结束回调判断,防止下载不全
                            if(task.isCancelled()){
                                if(REQUEST!=null){
                                    REQUEST.onRequestEnd();
                                }
                            }else {
                                if(ERROR!= null){
                                    ERROR.onError(response.code(),response.message());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        if(FAILURE!=null){
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
