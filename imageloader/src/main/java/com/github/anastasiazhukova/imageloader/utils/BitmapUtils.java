package com.github.anastasiazhukova.imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class BitmapUtils {

    private static final String LOG_TAG = BitmapUtils.class.getSimpleName();

    public static Bitmap getScaledBitmap(final InputStream pInputStream, final int pWidth, final int pHeight) throws IOException {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(pInputStream.available());
        final byte[] chunk = new byte[1 << 16];
        int bytesRead;

        while ((bytesRead = pInputStream.read(chunk)) > 0) {
            byteArrayOutputStream.write(chunk, 0, bytesRead);
        }

        final byte[] bytes = byteArrayOutputStream.toByteArray(); //we use byteArray because we'll need options

        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSapmleSize(options, pWidth, pHeight);
        options.inJustDecodeBounds = false;

        // Decode bitmap with inSampleSize set
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

    }

    private static int calculateInSapmleSize(final BitmapFactory.Options pOptions, final int pRequiredWidth, final int pRequiredHeight) {

        int sampleSize = 1;
        final int width;
        final int height;

        width = pOptions.outWidth;
        height = pOptions.outHeight;

        if (width > pRequiredWidth || height > pRequiredWidth) {

            final int heightRatio;
            final int widthRatio;

            if (pRequiredHeight == 0) {
                sampleSize = (int) Math.floor((float) width / (float) pRequiredWidth);
            } else if (pRequiredWidth == 0) {
                sampleSize = (int) Math.floor((float) height / (float) pRequiredHeight);
            } else {
                heightRatio = (int) Math.floor((float) height / (float) pRequiredHeight);
                widthRatio = (int) Math.floor((float) width / (float) pRequiredWidth);
                sampleSize = Math.max(heightRatio, widthRatio);
            }
        }

        LogUtils.logD(LOG_TAG, "Sample size: " + sampleSize);

        return sampleSize;
    }

}
