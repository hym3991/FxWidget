package com.neo.widget_core.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * <li>Package:com.ttp.core.cores.fres.utils
 * <li>Author: lining
 * <li>Date: 2017/3/21
 * <li>Description:
 * 提供基于Fresco的图片加载工具类
 * <p>
 * 在程序入口处添加下面代码，建议在Application的onCreate()方法内添加
 * Fresco.initialize(this, ImageLoaderConfig.getImagePipelineConfig(this));
 * <p>
 */
public class ImageLoaderUtils
{
    public static void loadImage( SimpleDraweeView simpleDraweeView, String url) {
        loadImage(simpleDraweeView, url, true);
    }

    public static void loadImage( SimpleDraweeView simpleDraweeView, String url, boolean isRetry) {
        if ( TextUtils.isEmpty(url) || simpleDraweeView == null) {
            return;
        }

        Uri uri = Uri.parse(url);
        loadImage(simpleDraweeView, uri, 0, 0, null, null, false, isRetry);
    }

    public static void loadFile(final SimpleDraweeView simpleDraweeView, String filePath) {
        loadFile(simpleDraweeView, filePath, true);
    }

    public static void loadFile(final SimpleDraweeView simpleDraweeView, String filePath, boolean isRetry) {
        if ( TextUtils.isEmpty(filePath)) {
            return;
        }

        Uri uri = new Uri.Builder()
                .scheme( UriUtil.LOCAL_FILE_SCHEME)
                .path(filePath)
                .build();
        loadImage(simpleDraweeView, uri, 0, 0, null, null, false, isRetry);
    }

    public static void loadImage( SimpleDraweeView simpleDraweeView,
                                 Uri uri,
                                 final int reqWidth,
                                 final int reqHeight,
                                 BasePostprocessor postprocessor,
                                 ControllerListener<ImageInfo> controllerListener,
                                 boolean isSmall,
                                 boolean isRetry) {

        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
        imageRequestBuilder.setRotationOptions( RotationOptions.autoRotate());
        imageRequestBuilder.setProgressiveRenderingEnabled(false); // 支持图片渐进式加载

        if (isSmall) {
            imageRequestBuilder.setCacheChoice( ImageRequest.CacheChoice.SMALL);
        }

        if (reqWidth > 0 && reqHeight > 0) {
            imageRequestBuilder.setResizeOptions(new ResizeOptions(reqWidth, reqHeight));
        }

        if ( UriUtil.isLocalFileUri(uri)) {
            imageRequestBuilder.setLocalThumbnailPreviewsEnabled(true);
        }

        if (postprocessor != null) {
            imageRequestBuilder.setPostprocessor(postprocessor);
        }

        ImageRequest imageRequest = imageRequestBuilder.build();

        PipelineDraweeControllerBuilder draweeControllerBuilder = Fresco.newDraweeControllerBuilder();
        draweeControllerBuilder.setOldController(simpleDraweeView.getController());
        draweeControllerBuilder.setImageRequest(imageRequest);

        if (controllerListener != null) {
            draweeControllerBuilder.setControllerListener(controllerListener);
        }

        draweeControllerBuilder.setTapToRetryEnabled(isRetry); // 开启重试功能
        draweeControllerBuilder.setAutoPlayAnimations(true); // 自动播放gif动画
        DraweeController draweeController = draweeControllerBuilder.build();
        simpleDraweeView.setController(draweeController);
    }
}
