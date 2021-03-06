package com.app.toolboxlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.view.View;

import com.app.comparator.Base64ToDecoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;


@SuppressLint("DefaultLocale")
public class BitmapUtils {


	//图片静态资源转byte
	public static byte[] imgResToByte( Context context , int resImg ){
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),resImg);
		if(null != bmp){
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			bmp.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
			byte[] bytes = byteArrayOutputStream.toByteArray();
			try {
				bmp.recycle();
				byteArrayOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bytes;
		}
		return null;
	}

	// byte 转 bitmap
	public static Bitmap byteToBitmap(byte[] byteArrayIn) {
		Options options = new Options();
		options.inSampleSize = 1;
		Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayIn, 0, byteArrayIn.length, options);
		return bitmap;
	}

	//bitmap  转 byte 带有质量压缩
	public static byte[] bitmapToByte(Bitmap bmp , int quality) {
		if(quality>100){
			quality = 100 ;
		}else if(quality<0){
			quality = 100 ;
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream();// 初始化一个流对象
		bmp.compress(CompressFormat.PNG, quality, output);// 把bitmap100%高质量压缩 到output对象里
		byte[] result = output.toByteArray();// 转换成功了
		try {
			output.close();
			 bmp.recycle();//自由选择是否进行回收
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 此方法用于改变Bitmap对象的大小，需要3个参数 1.要改变的图片 2.区域的宽 3.区域的高
	public static Bitmap changeBitmapSize(Bitmap bitmap, int newWidth, int newHeight, boolean filter) {
		// 得到原图宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 得到宽高的缩放比例
		float wScale = (float) newWidth / width;
		float hScale = (float) newHeight / height;

		float myScale = (wScale < hScale) ? wScale : hScale;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, filter);
	}

	// 此方法用于改变Bitmap对象的大小，需要3个参数 1.要改变的图片 2.想改变到多宽 3.想改变到多高
	public static Bitmap changeBitmapSize(Bitmap bitmap, int newWidth, int newHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float wScale = (float) newWidth / width;
		float hScale = (float) newHeight / height;
		Matrix matrix = new Matrix();
		matrix.postScale(wScale, hScale);
		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	/***
	 * 毛玻璃效果
	 *
	 * **/
	@SuppressLint("NewApi")
	public static Bitmap fastBlur(Bitmap sentBitmap, int radius) {

		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

		if (radius < 1) {
			return (null);
		}

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int[] pix = new int[w * h];
		// Log.e("pix", w + " " + h + " " + pix.length);
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int temp = 256 * divsum;
		int dv[] = new int[temp];
		for (i = 0; i < temp; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				// Preserve alpha channel: ( 0xff000000 & pix[yi] )
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
						| (dv[gsum] << 8) | dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (x == 0) {
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];

				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];

				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];

				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;

				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];

				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];

				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];

				yi += w;
			}
		}

		// Log.e("pix", w + " " + h + " " + pix.length);
		bitmap.setPixels(pix, 0, w, 0, 0, w, h);
		return (bitmap);
	}

	/**
	 * UI布局中的view，拿到绘制的UI,转byte[]
	 * @param view
	 * @return
	 */
	public static byte[] viewToByte(View view){
		byte[] photoBytes = null ;
		Bitmap bmp = null ;
		try{
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
			view.setDrawingCacheEnabled(true);
			view.buildDrawingCache();
			bmp = view.getDrawingCache();
			bmp.compress(CompressFormat.JPEG, 100, byteArrayOutputStream);
			photoBytes = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.close();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(null != bmp)bmp.recycle();
		}

		return photoBytes ;
	}

	/**
	 * 图片Base64转byte
	 * @param imgStr base64编码
	 */
	public static byte[] base64ToByte(String imgStr ) {
		Base64ToDecoder decoder = new Base64ToDecoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			return b ;
		} catch (Exception e) {

		}
		return null;
	}

	//byte转HexString
	public static String bytesToHexString(byte[] src) {
		String result = "";
		try {
			result = URLEncoder.encode(Base64Utils.encode(src), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String changeBytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static String md5(byte[] b) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = b;
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

}
