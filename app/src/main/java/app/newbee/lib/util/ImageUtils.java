package app.newbee.lib.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtils {
    private static final int MAX_WIDTH = 768;
    private static final int MAX_HEIGHT = 1024;
    private static AlertDialog alertDialog;
    public static Uri photoUri;

    private ImageUtils() {
    }

    public static BitmapDrawable readDrawable(Context context, int resId) {
        return readDrawable(context, resId, null);
    }

    public static BitmapDrawable readDrawable(Context context, int resId, Config bitmapConfig) {
        return readDrawable(context.getResources(), resId, bitmapConfig);
    }

    /**
     * 很优的算法, 压缩速度很快
     *
     * @param image
     * @param size
     * @param file
     */
    public static void compressBitmap(Bitmap image, int size, File file) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 85, out);
        float zoom = (float) Math.sqrt(size * 1024 / (float) out.toByteArray().length);

        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);
        Bitmap result = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        out.reset();
        result.compress(Bitmap.CompressFormat.JPEG, 85, out);
        while (out.toByteArray().length > size * 1024) {
            System.out.println(out.toByteArray().length);
            matrix.setScale(0.9f, 0.9f);
            result = Bitmap.createBitmap(result, 0, 0, result.getWidth(),
                    result.getHeight(), matrix, true);
            out.reset();
            result.compress(Bitmap.CompressFormat.JPEG, 85, out);
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            out.writeTo(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }

                image.recycle();
                image = null;
                result.recycle();
                result = null;
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 很优的算法, 压缩速度很快
     *
     * @param image
     * @param size
     * @param file
     */
    public static void compressBitmapAndSave(Bitmap image, int size, File file) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 85, out);
        float zoom = (float) Math.sqrt(size * 1024 / (float) out.toByteArray().length);

        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);
        Bitmap result = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        out.reset();
        result.compress(Bitmap.CompressFormat.JPEG, 85, out);
        while (out.toByteArray().length > size * 1024) {
            System.out.println(out.toByteArray().length);
            matrix.setScale(0.9f, 0.9f);
            result = Bitmap.createBitmap(result, 0, 0, result.getWidth(),
                    result.getHeight(), matrix, true);
            out.reset();
            result.compress(Bitmap.CompressFormat.JPEG, 85, out);
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            out.writeTo(fos);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 以最省内存的方式读取本地资源的图片 ,摘自Xutils
     * <p>
     * </br>修改为</br> imageButton_fav.setImageBitmap(BitmapUtils.readBitMap(this,
     * R.drawable.guide_fav_1));
     *
     * @param context
     * @return
     */

    private static final Object lock = new Object();

    public static Bitmap decodeSampledBitmapFromFile(String filename, int width, int height, Config config) {
        synchronized (lock) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPurgeable = true;
            options.inInputShareable = true;
            BitmapFactory.decodeFile(filename, options);
            options.inSampleSize = calculateInSampleSize(options, width, height);
            options.inJustDecodeBounds = false;
            if (config != null) {
                options.inPreferredConfig = config;
            }
            try {
                return BitmapFactory.decodeFile(filename, options);
            } catch (Throwable e) {
                return null;
            }
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (width > maxWidth || height > maxHeight) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) maxHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) maxWidth);
            }

            final float totalPixels = width * height;

            final float maxTotalPixels = maxWidth * maxHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > maxTotalPixels) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    /**
     * 按正方形裁切图片
     */
    public static Bitmap ImageCrop(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长
//        int retX = w > h ? (w - h) / 2 : 0;//基于原图，取正方形左下角x坐标
//        int retY = w > h ? 0 : (h - w) / 2;
//        return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
        return Bitmap.createBitmap(bitmap, 0, 0, wh, wh, null, false);
    }

    /**
     * 按正方形裁切图片
     */
    public static Bitmap ImageCrop(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

        int retX = w > h ? (w - h) / 2 : 0;//基于原图，取正方形左上角x坐标
        int retY = w > h ? 0 : (h - w) / 2;

        //下面这句是关键
        return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
    }

    public static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     *
     * @param angle
     *
     * @param bitmap
     *
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    public static void saveBitmapToLacal(Bitmap bitmap, String absPath) {
        File img = new File(absPath);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 按原长宽比缩放得到图像
    public static Bitmap resizeScaleImage(String path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inJustDecodeBounds = true;// 不加载bitmap到内存中
        BitmapFactory.decodeFile(path, options);
        if ((width == -1 || height == -1 || width == 0 || height == 0)) { // 宽度
            // 不设置大小为
            // 320，小于320时取原始尺寸；高度不设置大小为
            // 480，小于480时取原始尺寸
            if (options.outWidth > MAX_WIDTH || options.outHeight > MAX_HEIGHT) {
                options.inSampleSize = computeSampleSize(options, -1, MAX_WIDTH * MAX_HEIGHT);
            } else {
                options.inSampleSize = 1;
            }
        } else {
            options.inSampleSize = computeSampleSize(options, -1, width * height);
        }
        options.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(path, options);

        // 处理旋转
        int degree = readPictureDegree(path);
        if (degree != 0) {
            Bitmap oldbm = bm;
            bm = rotaingImageView(degree, oldbm);
            oldbm.recycle();
            oldbm = null;
        }
        return bm;
    }

    // 按给定长宽比缩放得到图像
    public static Bitmap resizeRealImage(String path, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inJustDecodeBounds = true;// 不加载bitmap到内存中

        BitmapFactory.decodeFile(path, options);

        if ((width == -1 || height == -1 || width == 0 || height == 0)) { // 宽度
            // 不设置大小为
            // 320，小于320时取原始尺寸；高度不设置大小为
            // 480，小于480时取原始尺寸
            if (options.outWidth > MAX_WIDTH || options.outHeight > MAX_HEIGHT) {
                options.inSampleSize = computeSampleSize(options, -1, MAX_WIDTH * MAX_HEIGHT);
            } else {
                options.inSampleSize = 1;
            }
        } else {
            options.inSampleSize = computeSampleSize(options, -1, width * height);
        }

        options.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(path, options);

        // 处理旋转
        int degree = readPictureDegree(path);
        if (degree != 0) {
            Bitmap oldbm = bm;
            bm = rotaingImageView(degree, oldbm);
            oldbm.recycle();
            oldbm = null;
        }

        if (width > 0 && height > 0 && (width != bm.getWidth() || height != bm.getHeight())) {
            // 计算缩放比例
            float scaleWidth = ((float) width) / bm.getWidth();
            float scaleHeight = ((float) height) / bm.getHeight();
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            // 释放bm
            bm.recycle();
            bm = null;
            return newbm;
        } else {
            return bm;
        }
    }


    public static Bitmap getBorderBitmap(Drawable drawable, int width, int height, float roundPx, int widthPx, int borderColor) {
        Bitmap bitmap = drawableToBitmap(drawable, width, height);
        Bitmap output1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        // 得到画布
        Canvas canvas1 = new Canvas(output1);
        // 将画布的四角圆化
        Paint paint = new Paint();
        // 得到与图像相同大小的区域 由构造的四个值决定区域的位置以及大小
        Rect rect = new Rect(widthPx, widthPx, bitmap.getWidth() - widthPx, bitmap.getHeight() - widthPx);
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas1.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.BLUE);
        // drawRoundRect的第2,3个参数一样则画的是正圆的一角，如果数值不同则是椭圆的一角
        canvas1.drawRoundRect(rectF, roundPx - widthPx, roundPx - widthPx, paint); // roundPx值越大角度越明显
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        // 得到与图像相同大小的区域 由构造的四个值决定区域的位置以及大小
        rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        rectF = new RectF(rect);
        paint.setAntiAlias(true);
        paint.setColor(borderColor);
        // drawRoundRect的第2,3个参数一样则画的是正圆的一角，如果数值不同则是椭圆的一角
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
        canvas1.drawRoundRect(rectF, roundPx, roundPx, paint); // roundPx值越大角度越明显

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        rect = new Rect(widthPx, widthPx, bitmap.getWidth() - widthPx, bitmap.getHeight() - widthPx);
        rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.RED);
        // drawRoundRect的第2,3个参数一样则画的是正圆的一角，如果数值不同则是椭圆的一角
        canvas.drawRoundRect(rectF, roundPx - widthPx, roundPx - widthPx, paint); // roundPx值越大角度越明显
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        rectF = new RectF(rect);
        paint.setAntiAlias(true);
        paint.setColor(borderColor);
        // drawRoundRect的第2,3个参数一样则画的是正圆的一角，如果数值不同则是椭圆的一角
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OVER));
        canvas.drawBitmap(output1, rect, rect, paint);

        output1.recycle();

        return output;
    }

    public static BitmapDrawable readDrawable(Resources res, int resId, Config bitmapConfig) {
        BitmapDrawable drawable = null;
        Bitmap bitmap = null;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Config.RGB_565;
        if (bitmapConfig != null) {
            opts.inPreferredConfig = bitmapConfig;
        }
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        try {
            InputStream ips = res.openRawResource(resId);
            if (ips != null) {
                bitmap = BitmapFactory.decodeStream(ips, null, opts);
            }
            if (bitmap != null) {
                drawable = new BitmapDrawable(res, bitmap);
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return drawable;
    }

    public static BitmapDrawable readDrawable(Resources res, File file) {
        BitmapDrawable drawable = null;
        Bitmap bitmap = null;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Config.RGB_565;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        try {
            InputStream ips = new FileInputStream(file);
            if (ips != null) {
                bitmap = BitmapFactory.decodeStream(ips, null, opts);
            }
            if (bitmap != null) {
                drawable = new BitmapDrawable(res, bitmap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return drawable;
    }

    public static Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
        // 取 drawable 的颜色格式
        Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    // 得到圆角矩形的图
    public static Bitmap getRoundedCornerBitmap(Drawable drawable, int width, int height, float roundPx) {
        Bitmap bitmap = drawableToBitmap(drawable, width, height);
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        // 得到画布
        Canvas canvas = new Canvas(output);
        // 将画布的四角圆化
        final int color = Color.RED;
        final Paint paint = new Paint();
        // 得到与图像相同大小的区域 由构造的四个值决定区域的位置以及大小
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // drawRoundRect的第2,3个参数一样则画的是正圆的一角，如果数值不同则是椭圆的一角
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint); // roundPx值越大角度越明显
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    // 得到半径为roundPx的居中圆的图
    public static Bitmap getCircleBitmap(Drawable drawable, int width, int height, float roundPx) {
        Bitmap bitmap = drawableToBitmap(drawable, width, height);
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        // 得到画布
        Canvas canvas = new Canvas(output);
        // 将画布圆化
        final int color = Color.RED;
        final Paint paint = new Paint();
        // 得到与图像相同大小的区域 由构造的四个值决定区域的位置以及大小
        int left = bitmap.getWidth() / 2 - (int) roundPx;
        int top = bitmap.getHeight() / 2 - (int) roundPx;
        final Rect rect = new Rect(left, top, bitmap.getWidth() - left, bitmap.getHeight() - top);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // drawRoundRect的第2,3个参数一样则画的是正圆的一角，如果数值不同则是椭圆的一角
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint); // roundPx值越大角度越明显
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    // 得到半径为roundPx的居中圆的图
    public static Bitmap getCircleBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        // 得到画布
        Canvas canvas = new Canvas(output);
        // 将画布圆化
        final int color = Color.RED;
        final Paint paint = new Paint();
        // 得到与图像相同大小的区域 由构造的四个值决定区域的位置以及大小
        int left = bitmap.getWidth() / 2 - (int) roundPx;
        int top = bitmap.getHeight() / 2 - (int) roundPx;
        final Rect rect = new Rect(left, top, bitmap.getWidth() - left, bitmap.getHeight() - top);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // drawRoundRect的第2,3个参数一样则画的是正圆的一角，如果数值不同则是椭圆的一角
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint); // roundPx值越大角度越明显
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    // 得到圆角矩形的图
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        // 得到画布
        Canvas canvas = new Canvas(output);
        // 将画布的四角圆化
        final int color = Color.RED;
        final Paint paint = new Paint();
        // 得到与图像相同大小的区域 由构造的四个值决定区域的位置以及大小
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // drawRoundRect的第2,3个参数一样则画的是正圆的一角，如果数值不同则是椭圆的一角
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint); // roundPx值越大角度越明显
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     * <p>
     * </br>修改为</br>
     * imageButton_fav.setImageBitmap(BitmapUtils.readBitMap(this,
     * R.drawable.guide_fav_1));
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        try {
            InputStream is = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(is, null, opt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     * <p>
     * </br>修改为</br>
     * imageButton_fav.setImageBitmap(BitmapUtils.readBitMap(this,
     * R.drawable.guide_fav_1));
     *
     * @param context
     * @return
     */
    public static Bitmap readBitmapFromFile(Context context, File file) {
        if (file.exists()) {
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inPurgeable = true;
            option.inPreferredConfig = Config.RGB_565;
            option.inInputShareable = true;
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), option);
            return bitmap;
        }
        return null;

    }

    /**
     * 获取view的位图拷贝
     *
     * @param view
     * @return Bitmap
     */
    public static Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
    }

    public static InputStream bitmap2IS(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
        return sbs;
    }

    /**
     * Bitmap转Byte
     *
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    // 使用BitmapFactory.Options的inSampleSize参数来缩放
    public static Bitmap resizeImage(String path, int scaleWidth, int scaleHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inJustDecodeBounds = true;// 不加载bitmap到内存中
        BitmapFactory.decodeFile(path, options);
        options.inPreferredConfig = Config.RGB_565;
        int wRatio = (int) Math.ceil(options.outWidth / (float) scaleWidth);
        int hRatio = (int) Math.ceil(options.outHeight / (float) scaleHeight);
        int be = hRatio < wRatio ? wRatio : hRatio;
        if (be <= 0) {
            be = 1;// be=1表示不缩放
        }
        options.inSampleSize = be;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        // long size = (bitmap.getRowBytes()*bitmap.getHeight())/1024;
        // System.out.println("bitmap size === "+size);
        try {
            return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
        } catch (OutOfMemoryError oom) {
            oom.printStackTrace();
        }
        return bitmap;
    }


    private static void doTakePhoto(Activity activity, int CAMERA_WITH_DATA1) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, filename);

            photoUri = activity.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            activity.startActivityForResult(intent, CAMERA_WITH_DATA1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "请检测SD是否存在", Toast.LENGTH_SHORT).show();
        }
    }

    // 请求Gallery程序

    private static void doPickPhotoFromGallery(Activity activity, int PHOTO_PICKED_WITH_DATA1) {
        try {
            final Intent intent = getPhotoPickIntent();
            activity.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "相册图片打开异常", Toast.LENGTH_SHORT).show();
        }
    }

    // 封装请求Gallery的intent
    public static Intent getPhotoPickIntent() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        Intent intentFromGallery = new Intent();
        intentFromGallery.setType("image/*"); // 设置文件类型
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        return intentFromGallery;
    }
}
