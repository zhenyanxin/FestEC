package com.diabin.latte.net;

import android.content.Context;

import com.diabin.latte.net.callback.IError;
import com.diabin.latte.net.callback.IFailure;
import com.diabin.latte.net.callback.IRequest;
import com.diabin.latte.net.callback.ISuccess;
import com.diabin.latte.net.callback.RequestCallbacks;
import com.diabin.latte.ui.LatteLoader;
import com.diabin.latte.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by 甄焰鑫 on 2019/1/1.
 *
 * 构造者模式构建Restful请求处理
 */
public class RestClient {
    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS=RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;

    //加载样式
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;
    //文件上传
    private final File  FILE;

    public RestClient(String url, Map<String, Object> params,
                      IRequest rquest, ISuccess success,
                      IFailure failure, IError error,
                      RequestBody body ,File file,
                      Context context, LoaderStyle loaderStyle) {
        URL = url;
        PARAMS.putAll(params);
        REQUEST = rquest;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
        BODY = body;
        FILE = file;
        CONTEXT=context;
        LOADER_STYLE=loaderStyle;
    }
    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    /**
     * HTTP请求处理
     * @param method
     */
    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null) {
            LatteLoader.showLoading(CONTEXT, LOADER_STYLE);
        }

        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL, BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL, BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallback());
        }
    }

    /**
     * 回调
     * @return
     */
    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

//
//    public final void upload() {
//        request(HttpMethod.UPLOAD);
//    }
//
//    public final void download() {
//        new DownloadHandler(URL, REQUEST, DOWNLOAD_DIR, EXTENSION, NAME,
//                SUCCESS, FAILURE, ERROR)
//                .handleDownload();
//    }
}
