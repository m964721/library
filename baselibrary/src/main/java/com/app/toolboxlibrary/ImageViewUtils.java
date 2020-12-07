package com.app.toolboxlibrary;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.concurrent.ExecutionException;

/**
 * Created by jun on 16/11/16.
 * 本类主要是和imageview相关的内容设置
 */

public class ImageViewUtils {
    private static volatile ImageViewUtils viewUtils = null;

    public static ImageViewUtils getInstance() {
        if (null == viewUtils) {
            synchronized (ImageViewUtils.class) {
                if (null == viewUtils) {
                    viewUtils = new ImageViewUtils();
                }
            }
        }
        return viewUtils;
    }

    String url = "https://djimg.gobestsoft.cn/";//网络加载图片URL

    public String getUrl() {
        return this.url;
    }

    /**
     * 加载静态资源获取没有默认图片的服务器资源文件
     *
     * @param mContext
     * @param url
     * @param show_img
     */
    public void showUrlImg(Context mContext,
                           String url,
                           ImageView show_img) {
        if(StringUtils.isStringToNUll((url))){
            return;
        }
        if (-1 == url.indexOf("http")) {
            url = DataUtils.backUrl(getUrl(), url);
        }
        LogUtil.showLog("showUrlImg","url:"+url);
        Glide.with(mContext)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(show_img);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 要缓存的url
     *
     * @param mContext
     * @param url
     * @param show_img
     * @param errorRes DiskCacheStrategy.SOURCE：缓存原始数据，
     *                 DiskCacheStrategy.RESULT：缓存变换(如缩放、裁剪等)后的资源数据，
     *                 DiskCacheStrategy.NONE：什么都不缓存，
     *                 DiskCacheStrategy.ALL：缓存SOURC和RESULT。默认采用DiskCacheStrategy.RESULT策略，
     *                 对于download only操作要使用DiskCacheStrategy.SOURCE。
     */
    public void showUrlImg(
            Context mContext,
            String url,
            ImageView show_img,
            int errorRes
            ) {
        if(StringUtils.isStringToNUll((url))){
            return;
        }
        if (-1 == url.indexOf("http")) {
            url = DataUtils.backUrl(getUrl(), url);
        }
        if (null != show_img) {
            Glide.with(mContext)
                    .load(url)
                    .placeholder(errorRes)
                    .error(errorRes)
                    .thumbnail(1.0f)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(show_img);
        }

    }

    /**
     * 缓存的url并剪切显示
     *
     * @param mContext
     * @param url
     * @param show_img
     * @param errorRes
     */

    public void showUrlAsBitmap(Context mContext,
                                String url,
                                ImageView show_img,
                                int errorRes) {
        if(StringUtils.isStringToNUll((url))){
            return;
        }
        if (-1 == url.indexOf("http")) {
            url = DataUtils.backUrl(getUrl(), url);
        }
        Glide.with(mContext).
                load(url).
                asBitmap().
                error(errorRes).
                thumbnail(0.5f).
                diskCacheStrategy(DiskCacheStrategy.RESULT).
                into(show_img);
    }

    //返回图片byte数据
    public byte[] backImgUrlByte(Context context, String url) {
        String iconUrl = DataUtils.backUrl(getUrl(), url) ;
        LogUtil.showLog("backImgUrlByte","iconUrl: "+iconUrl.toString());
        try {
            byte[] bytes = Glide.with(context)
                    .load(iconUrl)
                    .asBitmap()
                    .toBytes()
                    .into(50, 50)
                    .get();
            return bytes;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
