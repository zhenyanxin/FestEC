package com.diabin.latte.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.diabin.latte.app.Latte;
import com.diabin.latte.net.callback.IRequest;
import com.diabin.latte.net.callback.ISuccess;
import com.diabin.latte.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by 甄焰鑫 on 2019/1/4.
 * 下载流,任务分工
 */
public class SaveFileTask extends AsyncTask<Object,Void,File> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    /**
     * 异步处理文件下载
     * @param params
     * @return
     */
    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();
        if(downloadDir == null || downloadDir.equals("")){
            downloadDir="down_loads";
        }
        if(extension == null || extension.equals("")){
            extension = "";
        }
        if(name == null){
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else {
            return FileUtil.writeToDisk(is,downloadDir,name);
        }
    }

    /**
     * 执行完异步回到主线程的操作
     */
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS != null){
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST != null){
            REQUEST.onRequestEnd();
        }
        autoInstallApk(file);
    }

    /**
     * 自动安装更新包
     * @param file
     */
    private void autoInstallApk(File file){
       if(FileUtil.getExtension(file.getPath()).equals("apk")){
           final Intent install = new Intent();
           install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           install.setAction(Intent.ACTION_VIEW);
           install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
           Latte.getApplicationContext().startActivity(install);
       }
    }
}
