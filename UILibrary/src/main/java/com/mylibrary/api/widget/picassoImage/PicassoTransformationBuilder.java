/*
 * Copyright (C) 2017 Vincent Mi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mylibrary.api.widget.picassoImage;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;

import java.security.MessageDigest;


public final class PicassoTransformationBuilder {
    private final DisplayMetrics mDisplayMetrics;
    //四个角的角度
    private float[] mCornerRadii = new float[]{0, 0, 0, 0};
    //是否为圆形图片
    private boolean mOval = false;
    //图片的边线宽度
    private float mBorderWidth = 0;
    //图片的边线颜色
    private ColorStateList mBorderColor = ColorStateList.valueOf(PicassoDrawable.DEFAULT_BORDER_COLOR);
    //图片的填充类型
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;

    public PicassoTransformationBuilder() {
        mDisplayMetrics = Resources.getSystem().getDisplayMetrics();
    }

    public PicassoTransformationBuilder scaleType(ImageView.ScaleType scaleType) {
        mScaleType = scaleType;
        return this;
    }

    /**
     * Set corner radius for all corners in px.
     * 设置 4个角的半径 单位px
     *
     * @param radius the radius in px
     * @return the builder for chaining.
     */
    public PicassoTransformationBuilder cornerRadius(float radius) {
        mCornerRadii[Corner.TOP_LEFT] = radius;
        mCornerRadii[Corner.TOP_RIGHT] = radius;
        mCornerRadii[Corner.BOTTOM_RIGHT] = radius;
        mCornerRadii[Corner.BOTTOM_LEFT] = radius;
        return this;
    }

    /**
     * Set corner radius for a specific corner in px.
     * 设置 指定的角的半径 单位px
     *
     * @param corner the corner to set.  0 左上 1右上 2 右下  3 左下
     * @param radius the radius in px.
     * @return the builder for chaning.
     */
    public PicassoTransformationBuilder cornerRadius(@Corner int corner, float radius) {
        mCornerRadii[corner] = radius;
        return this;
    }

    /**
     * Set corner radius for all corners in density independent pixels.
     * 设置 4个角的半径 单位dp
     *
     * @param radius the radius in density independent pixels.
     * @return the builder for chaining.
     */
    public PicassoTransformationBuilder cornerRadiusDp(float radius) {
        return cornerRadius(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, mDisplayMetrics));
    }

    /**
     * Set corner radius for a specific corner in density independent pixels.
     * 设置 指定的角的半径 单位dp
     *
     * @param corner the corner to set
     * @param radius the radius in density independent pixels.
     * @return the builder for chaining.
     */
    public PicassoTransformationBuilder cornerRadiusDp(@Corner int corner, float radius) {
        return cornerRadius(corner,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, mDisplayMetrics));
    }

    /**
     * Set the border width in pixels.
     * 设置边线的宽度 单位px
     *
     * @param width border width in pixels.
     * @return the builder for chaining.
     */
    public PicassoTransformationBuilder borderWidth(float width) {
        mBorderWidth = width;
        return this;
    }

    /**
     * Set the border width in density independent pixels.
     * 设置边线的宽度 单位dp
     *
     * @param width border width in density independent pixels.
     * @return the builder for chaining.
     */
    public PicassoTransformationBuilder borderWidthDp(float width) {
        mBorderWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, mDisplayMetrics);
        return this;
    }

    /**
     * Set the border color.
     * 设置边线的颜色
     *
     * @param color the color to set.
     * @return the builder for chaining.
     */
    public PicassoTransformationBuilder borderColor(int color) {
        mBorderColor = ColorStateList.valueOf(color);
        return this;
    }

    /**
     * Set the border color as a {@link ColorStateList}.
     * 设置边线的颜色
     *
     * @param colors the {@link ColorStateList} to set.
     * @return the builder for chaining.
     */
    public PicassoTransformationBuilder borderColor(ColorStateList colors) {
        mBorderColor = colors;
        return this;
    }

    /**
     * Sets whether the image should be oval or not.
     * 是否为圆形图片
     *
     * @param oval if the image should be oval.
     * @return the builder for chaining.
     */
    public PicassoTransformationBuilder oval(boolean oval) {
        mOval = oval;
        return this;
    }

    /**
     * Creates a {@link Transformation} for use with picasso.
     *
     * @return the {@link Transformation}
     */
    public Transformation build() {
        return new Transformation<Bitmap>() {


            @Override
            public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

            }

            @NonNull
            @Override
            public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
                Bitmap transformed = PicassoDrawable.fromBitmap( resource.get())
                        .setScaleType(mScaleType)
                        .setCornerRadius(mCornerRadii[0], mCornerRadii[1], mCornerRadii[2], mCornerRadii[3])
                        .setBorderWidth(mBorderWidth)
                        .setBorderColor(mBorderColor)
                        .setOval(mOval)
                        .toBitmap();
                if (!resource.get().equals(transformed)) {
                    resource.get().recycle();
                }


                return resource;
            }

        };
    }
}
