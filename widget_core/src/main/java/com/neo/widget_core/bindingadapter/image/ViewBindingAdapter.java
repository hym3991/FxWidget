package com.neo.widget_core.bindingadapter.image;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.neo.widget_core.utils.ImageLoaderUtils;
import com.neo.widget_core.bindingadapter.command.ReplyCommand;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;

/**
 * Created by kelin on 16-3-24.
 */
public final class ViewBindingAdapter
{
    @BindingAdapter({"simpleDraweeViewUrl"})
    public static void loadImage(SimpleDraweeView simpleDraweeView, String uri) {
        if(TextUtils.isEmpty(uri))
            return;
        if (uri.startsWith("http")) {
            ImageLoaderUtils.loadImage(simpleDraweeView, uri);
        } else {
            ImageLoaderUtils.loadFile(simpleDraweeView, uri);
        }
    }

    @BindingAdapter(value = {"imageViewUrl", "placeholderImageRes", "request_width", "request_height", "onSuccessCommand", "onFailureCommand"}, requireAll = false)
    public static void loadImage(final ImageView imageView, String uri,
                                 @DrawableRes int placeholderImageRes,
                                 int width, int height,
                                 final ReplyCommand<Bitmap> onSuccessCommand,
                                 final ReplyCommand<DataSource<CloseableReference<CloseableImage>>> onFailureCommand) {
        imageView.setImageResource(placeholderImageRes);
        if (!TextUtils.isEmpty(uri)) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource( Uri.parse(uri));
            if (width > 0 && height > 0) {
                builder.setResizeOptions(new ResizeOptions(width, height));
            }
            ImageRequest request = builder.build();
            DataSource<CloseableReference<CloseableImage>>
                    dataSource = imagePipeline.fetchDecodedImage(request, null);
            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                protected void onFailureImpl( DataSource<CloseableReference<CloseableImage>> dataSource) {
                    if (onFailureCommand != null) {
                        onFailureCommand.execute(dataSource);
                    }
                }

                @Override
                protected void onNewResultImpl( Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                    if (onSuccessCommand != null) {
                        onSuccessCommand.execute(bitmap);
                    }
                }
            }, UiThreadImmediateExecutorService.getInstance());
        }
    }

}

