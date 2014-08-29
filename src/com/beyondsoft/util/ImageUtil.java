package com.beyondsoft.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.os.Environment;

public class ImageUtil {
	
	/**
	 * 计算相应需要的大小，存在问题
	 * 
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {

		int initialSize = 1;

		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));

		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			initialSize = lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			initialSize = 1;
		} else if (minSideLength == -1) {
			initialSize = lowerBound;
		} else {
			initialSize = upperBound;
		}

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
	 * 创建带有影子的图片
	 * 
	 * @param originalImage
	 *            原图片
	 * @param scale
	 *            缩放比例
	 * @return
	 */
	public static Bitmap createReflectedImage(Bitmap originalImage,
			float reflectRatio, float scale) {

		int width = (int) (originalImage.getWidth() * scale);
		int height = (int) (originalImage.getHeight() * scale);

		final Rect srcRect = new Rect(0, 0, originalImage.getWidth(),
				originalImage.getHeight());
		final Rect dstRect = new Rect(0, 0, width, height);

		final int reflectionGap = 1;

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(int) (height + height * reflectRatio), Config.ARGB_8888);
		Canvas canvasRef = new Canvas(bitmapWithReflection);

		canvasRef.drawBitmap(originalImage, srcRect, dstRect, null);

		Matrix matrix = new Matrix();
		matrix.setTranslate(0, height + height + reflectionGap);
		matrix.preScale(scale, -scale);

		canvasRef.drawBitmap(originalImage, matrix, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, height, 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x80ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvasRef.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		originalImage.recycle();
		return bitmapWithReflection;
	}

	/**
	 * 得到缩小的图片，这里缩小的是图片质量
	 * 
	 * @param dataBytes
	 * @param maxWidth
	 * @return
	 */
	public static Bitmap getCorrectBmp(byte dataBytes[], int inSampleSize,
			Bitmap.Config config) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = config;
		opts.inSampleSize = inSampleSize;
		opts.inJustDecodeBounds = false;
		Bitmap originalImage = BitmapFactory.decodeByteArray(dataBytes, 0,
				dataBytes.length, opts);
		return originalImage;
	}

	/**
	 * 得到圆角图片
	 * 
	 * @param bitmap
	 *            原图像
	 * @param scale
	 *            缩放比例
	 * @param roundPx
	 *            圆角像素
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float scale,
			float roundPx, Bitmap.Config config) {

		int width = (int) (bitmap.getWidth() * scale);
		int height = (int) (bitmap.getHeight() * scale);

		Bitmap output = null;
		output = Bitmap.createBitmap(width, height, config);
		Canvas canvas = new Canvas(output);

		final int color = 0xff000000;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(0, 0, width, height);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		// draw的方式缩放
		canvas.drawBitmap(bitmap, rect, rectF, paint);

		// Matrix的方式缩放
		// Matrix matrix = new Matrix();
		// matrix.postScale(scale, scale);
		// canvas.drawBitmap(bitmap, matrix, paint);

		return output;
	}

	/**
	 * 得到缩放后的图片
	 * 
	 * @param bitmap
	 * @param scale
	 * @return
	 */
	public static Bitmap getScaleBitmap(Bitmap bitmap, float scale) {
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return dstbmp;
	}

	/**
	 * 
	 * @param inStream
	 * @throws IOException
	 * @return
	 */
	public static byte[] readStream(InputStream inStream) throws IOException {

		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024]; // 用数据装
		int len = -1;
		int download = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outstream.write(buffer, 0, len);
			download += len;
		}
		outstream.close();
		return outstream.toByteArray();
	}

	/**
	 * 得到手机data目录下的图片
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static Bitmap getBmpFromFile(Context context, String fileName) {
		try {
			FileInputStream imgInputStream = context.openFileInput(fileName);
			Bitmap bmp = BitmapFactory.decodeStream(imgInputStream);
			return bmp;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保存图片到指定位置(jpg)
	 * 
	 * @param context
	 * @param bmp
	 * @param fileName
	 * @return
	 */
	public static void saveBmpToJPG(Bitmap bmp,String filePath,String fileName ,int quality) {
		try {
			File file=new File(filePath);
			if(!file.exists()){
				file.mkdirs();
			}
			file=new File(filePath,fileName);
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream fileOut=new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG,quality, fileOut);
			fileOut.flush();
			fileOut.close();
			if (Logs.IS_DEBUG)
				Logs.i("debug", "保存文件: " + fileName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存图片到SDcard中
	 * @param context
	 * @param bmp
	 * @param fileName
	 */
	public static void saveBmpToSdcard(Context context, Bitmap bmp, String fileName) {
		String filePath = "";
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_REMOVED)) {
			if (Logs.IS_DEBUG)
				Logs.i("img", "sdcard");
			filePath  = Environment
					.getExternalStorageDirectory()
					.getAbsolutePath()
					+ File.pathSeparator +"haoqiuimage" + File.pathSeparator + fileName + ".png";
		} else {
			return;
//			if (Logs.IS_DEBUG)
//				Log.i("img", Environment.MEDIA_REMOVED);
//			filePath = Environment.getDataDirectory()
//					.getAbsolutePath() + File.pathSeparator + fileName + ".png";
		}
		File imageFile = new File(filePath);
		try {
			fileName = fileName + ".png";
			
			if (Logs.IS_DEBUG)
				Logs.i("debug", "保存文件: " + fileName);
			
			FileOutputStream fileOut = new FileOutputStream(imageFile);
			bmp.compress(Bitmap.CompressFormat.PNG, 0, fileOut);
			fileOut.flush();
			fileOut.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将Bitmap转换为byte数组
	 * 
	 * @param bmp
	 * @return
	 */
	public static byte[] getBytesFromBitmap(Bitmap bmp) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
			return baos.toByteArray();

		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * 获得比特图
	 * @param bytes
	 * @param size
	 * @return
	 */
	public static Bitmap getBitmap(byte[] bytes,
			int size) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither=false;
		options.inPurgeable = true;
		options.inTempStorage = new byte[12*1024];
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		int width = options.outWidth;
		int height = options.outHeight;
		Logs.i("img", "width:" + width + "height" + height);
		/** 计算缩放比例 **/
		int scale = 0;
		if (width < height) {
			/** 宽小于高时，竖向 **/
			scale = width / size;
		} else {
			scale = height / size;
		}
		if (scale < 1) {
			scale = 1;
		}
		if (scale < 1) {
			scale = 1;
		}
		if (Logs.IS_DEBUG)
			Logs.i("TEST", "scale" + scale);
		options.inJustDecodeBounds = false;
		options.inSampleSize = scale;
		bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		return bitmap;
	}
	/**
	 * 得到圆形图片
	 *
	 * @param bitmap 原图像
	 * @return 
	 */
	public static Bitmap getRoundBitmap(Bitmap bitmap,Bitmap.Config config) {
		
		int width=bitmap.getWidth();
	    Bitmap output = Bitmap.createBitmap(width,width, config);
	    Canvas canvas = new Canvas(output);
	    final int color = 0xff000000;
	    final Paint paint = new Paint();
	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawCircle(width/2,width/2, width/2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap,0, 0, paint);
	    return output;
	}
	
	/** 得到有边框的图片
	 * @param bitmap  原图像
	 * @return
	 */
	public static Bitmap getBorderBitmap(Bitmap bitmap){
		int width=bitmap.getWidth()+20;
		int height=bitmap.getHeight()+20;
		Bitmap output=Bitmap.createBitmap(width, height,Config.ARGB_8888);
		Canvas canvas=new Canvas(output);
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(bitmap,10,10,null);
		bitmap.recycle();
		bitmap=null;
		return output;
	}
	
	/**旋转图片
	 * @param angle
	 * @param bitmap
	 * @return
	 */
	public static Bitmap rotaingImageView(int angle,Bitmap bitmap){
		Matrix matrix=new Matrix();
		matrix.postRotate(angle);
		Bitmap resizedBitmap=Bitmap.createBitmap(bitmap, 0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		return resizedBitmap;
	}
	
    /**
     * recreate the bitmap, and make it be scaled to box (maxWeight, maxHeight)
     * note: the old bitmap has not being recycled, you must do it yourself.
     * @param bitmap the bitmap
     * @param boxHeight box height
     * @param boxWidth box width
     * @return the new Bitmap
     */
    public static Bitmap xform(Bitmap bitmap, int boxWidth, int boxHeight) {
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();

        if (boxHeight <= 0 && boxWidth <= 0) {
            return Bitmap.createScaledBitmap(bitmap, src_w, src_h, true);
        } else if (boxHeight <= 0) {
            boxHeight = (int)(src_h / (float)src_w * boxWidth);
        } else if (boxWidth <= 0) {
            boxWidth = (int)(src_w / (float)src_h * boxHeight);
        }


        return Bitmap.createScaledBitmap(bitmap, boxWidth, boxHeight, true);
    }

    final static int ROUNDED_CORNER_COLOR = 0xff424242;
    /**
     * Get Rounded Corner Bitmap
     * @param bitmap ori bitmap
     * @param roundPx round size
     * @return new bitmap
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(ROUNDED_CORNER_COLOR);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * automatically compute the inSampleSize when decode byteArray
     * @param data data
     * @param offset offset
     * @param length length
     * @param reqWidth reqWidth
     * @param reqHeight reqHeight
     * @return bitmap
     */
    public static Bitmap decodeSampledBitmapFromByteArray(byte[] data, int offset, int length,
                                                          int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, offset, length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, offset, length, options);
    }

    /**
     * automatically compute the inSampleSize when decode from resource
     * @param res res
     * @param resId resId
     * @param reqWidth reqWidth
     * @param reqHeight reqHeight
     * @return bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }
        return inSampleSize;
    }

}