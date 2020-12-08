package com.demo.application.download;

import android.content.Context;

import com.app.toolboxlibrary.FileUtiles;
import com.app.toolboxlibrary.LogUtil;
import com.app.toolboxlibrary.StringUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;


/**
 * Created by PPH on 18/3/13.
 */

public class AppFileDownload {

    private Context mContext;
    private String filePath = "";
    private int downloadId;
    private OnDownloadFinish onDownloadFinish;
    private String Tag = "AppFileDownload" ;

    public AppFileDownload(Context context) {
        mContext = context;
        FileDownloader.setup(mContext);
    }

    //下载Apk
    public void downloadFile(String url, String fileName) {
        filePath = FileUtiles.createFile(mContext,fileName) + StringUtils.getNameFromUrl(url);
        LogUtil.showLog("AppFileDownload url", "" + url);
        LogUtil.showLog("AppFileDownload apkPath", "" + filePath);
        downloadId = FileDownloader
                .getImpl()
                .create(url)
                .setPath(filePath)
                .setListener(fileDownloadListener).start();
        LogUtil.showLog("AppFileDownload downloadId", "" + downloadId);
    }

    FileDownloadListener fileDownloadListener = new FileDownloadListener() {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            LogUtil.showLog("AppFileDownload", "pending");
        }

        @Override
        protected void connected(BaseDownloadTask task, String etag,
                                 boolean isContinue, int soFarBytes, int totalBytes) {
            LogUtil.showLog("AppFileDownload", "connected");
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            LogUtil.showLog("AppFileDownload", "progress");
            double percent = (double) soFarBytes / (double) totalBytes * 100;//下载百分比
            getOnDownloadFinish().onDownloadProgress((int) percent);
        }

        @Override
        protected void blockComplete(BaseDownloadTask task) {
            LogUtil.showLog("AppFileDownload", "blockcmplte");
        }

        @Override
        protected void retry(final BaseDownloadTask task,
                             final Throwable ex, final int retryingTimes, final int soFarBytes) {

        }

        @Override
        protected void completed(BaseDownloadTask task) {
            LogUtil.showToast(mContext, "下载完成");
            getOnDownloadFinish().onDownloadProgress(100);
            getOnDownloadFinish().onDownloadFinish(task.getPath());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            LogUtil.showLog("AppFileDownload", "paused");
        }

        /**
         * @param task
         * @param e
         */
        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            LogUtil.showToast(mContext, "下载失败，请尝试重新升级");
            LogUtil.showLog("AppFileDownload", "error"+e);
            getOnDownloadFinish().onDownloadError(e);
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            LogUtil.showLog("AppFileDownload warn", "已存在文件继续下载");
            continueDownLoad(task);//如果存在了相同的任务，那么就继续下载
        }
    };

    //断点下载
    private void continueDownLoad(BaseDownloadTask task) {
        while (task.getSmallFileSoFarBytes() != task.getSmallFileTotalBytes()) {
            double percent = (double) task.getSmallFileSoFarBytes() /
                    (double) task.getSmallFileTotalBytes() * 100;//计算百分比
            getOnDownloadFinish().onDownloadProgress((int) percent);
        }
    }

    public OnDownloadFinish getOnDownloadFinish() {
        if(null == onDownloadFinish){
            onDownloadFinish = new OnDownloadFinish() {
                @Override
                public void onDownloadProgress(int progress) {

                }

                @Override
                public void onDownloadFinish(String filePath) {

                }

                @Override
                public void onDownloadError(Object o) {

                }
            };
        }
        return onDownloadFinish;
    }

    public void setOnDownloadFinish(OnDownloadFinish onDownloadFinish) {
        this.onDownloadFinish = onDownloadFinish;
    }

    public interface OnDownloadFinish {
        void onDownloadProgress(int progress);
        void onDownloadFinish(String filePath);
        void onDownloadError(Object o);
    }

}
