package com.app.toolboxlibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;


@SuppressLint("DefaultLocale")
public class BitmapUtils {

	public static Bitmap getImageByURL(String url) {
		try {
			URL imgURL = new URL(url);
			URLConnection conn = imgURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			Bitmap bm = BitmapFactory.decodeStream(bis); // 关键代码
			bis.close();
			is.close();
			if (bm == null) {
				LogUtil.showLog("MO", "httperror");
			}
			return bm;
		} catch (Exception e) {
			return null;
		}
	}

	//byte转bitmap
	public static Bitmap byteToBitmap(byte[] byteArrayIn) {
		Options options = new Options();
		options.inSampleSize = 1;
		Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayIn, 0, byteArrayIn.length, options);
		return bitmap;
	}
	//bitmap转byte
	public static byte[] bitmapToByte(Bitmap bmp) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();// 初始化一个流对象
		bmp.compress(CompressFormat.PNG, 100, output);// 把bitmap100%高质量压缩 到
														// output对象里
		// bmp.recycle();//自由选择是否进行回收

		byte[] result = output.toByteArray();// 转换成功了
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//是否带有质量压缩
	public static byte[] bitmapToByte(Bitmap bmp , int quality) {
		if(quality>100||quality<=0){
			return  null ;
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream();// 初始化一个流对象
		bmp.compress(CompressFormat.PNG, quality, output);// 把bitmap100%高质量压缩 到
		// output对象里
		// bmp.recycle();//自由选择是否进行回收

		byte[] result = output.toByteArray();// 转换成功了
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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


	/* *
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 *
	 * @param src byte[] data
	 *
	 * @return hex string
	 */
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

	/**
	 * Convert hex string to byte[]
	 *
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
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

	// 此方法用于改变Bitmap对象的大小，需要3个参数 1.要改变的图片
	public static Bitmap DengBichangeBitmapSize(Bitmap bitmap) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float myScale = (float) 60 / height;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	public static Bitmap DengBichangeBitmapSizeByWidth(Bitmap bitmap,
			int limitWidth) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float myScale = (float) limitWidth / height;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	public static Bitmap DengBichangeBitmapSizeByHeight(Bitmap bitmap,
			int limitHeight) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float myScale = (float) limitHeight / height;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	public static Bitmap DengBichangesignBitmapSizeByHeight(Bitmap bitmap,
			int limitHeight) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float myScale = (float) limitHeight / height;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);
		// 向左旋转45度，参数为正则向右旋转
		matrix.postRotate(-90);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	// 此方法用于改变Bitmap对象的大小，需要3个参数 1.要改变的图片 2.区域的宽 3.区域的高
	public static Bitmap DengBichangeBitmapSize(Bitmap bitmap, int newWidth,
			int newHeight) {
		// 得到原图宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 得到宽高的缩放比例
		float wScale = (float) newWidth / width;
		float hScale = (float) newHeight / height;

		float myScale = (wScale < hScale) ? wScale : hScale;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	// 此方法用于改变Bitmap对象的大小，需要3个参数 1.要改变的图片 2.区域的宽 3.区域的高
	public static Bitmap DengBichangeBitmapSize2(Bitmap bitmap, int newWidth,
			int newHeight) {
		// 得到原图宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 得到宽高的缩放比例
		float wScale = (float) newWidth / width;
		float hScale = (float) newHeight / height;

		float myScale = (wScale < hScale) ? wScale : hScale;

		Matrix matrix = new Matrix();

		matrix.postScale(myScale, myScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
	}

	// 此方法用于改变Bitmap对象的大小，需要3个参数 1.要改变的图片 2.想改变到多宽 3.想改变到多高
	public Bitmap changeBitmapSize(Bitmap bitmap, int newWidth, int newHeight) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float wScale = (float) newWidth / width;
		float hScale = (float) newHeight / height;

		Matrix matrix = new Matrix();

		matrix.postScale(wScale, hScale);

		return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	}

	private static String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;// 三元运算符 t>10时取 ""+t
	}

	public static String SaveAsFile(View view, String url) {
		ByteArrayOutputStream baos = null;
		String signpath = null;
		String result = "";
		try {
			Calendar c = Calendar.getInstance();

			String time = c.get(Calendar.YEAR) + // 得到年
					formatTime(c.get(Calendar.MONTH) + 1) + // month加一 //月
					formatTime(c.get(Calendar.DAY_OF_MONTH)) + // 日
					formatTime(c.get(Calendar.HOUR_OF_DAY)) + // 时
					formatTime(c.get(Calendar.MINUTE)) + // 分
					formatTime(c.get(Calendar.SECOND));
			// 秒

			if (PhoneinfoUtils.IsSdCardCanBeUsed()) {
				String sign_dir = Environment.getExternalStorageDirectory()
						+ File.separator + url;
				File file = new File(sign_dir);
				if (!file.exists()) {
					try {
						// 按照指定的路径创建文件夹
						if (!file.mkdirs()) {

							result = "创建文件失败";

						} else {
							signpath = sign_dir + File.separator + time
									+ ".jpg";
							baos = new ByteArrayOutputStream();

							view.setDrawingCacheEnabled(true);
							view.buildDrawingCache();
							Bitmap bmp = view.getDrawingCache();

							bmp.compress(CompressFormat.JPEG, 100, baos);
							byte[] photoBytes = baos.toByteArray();
							if (photoBytes != null) {
								new FileOutputStream(new File(signpath))
										.write(photoBytes);
							}
							result = "图片保存到" + signpath;
						}

					} catch (Exception e) {
					}
				} else {
					signpath = sign_dir + File.separator + time + ".jpg";
					baos = new ByteArrayOutputStream();

					view.setDrawingCacheEnabled(true);
					view.buildDrawingCache();
					Bitmap bmp = view.getDrawingCache();

					bmp.compress(CompressFormat.JPEG, 100, baos);
					byte[] photoBytes = baos.toByteArray();
					if (photoBytes != null) {
						new FileOutputStream(new File(signpath))
								.write(photoBytes);
					}
					result = "图片保存到" + signpath;
				}

			} else {
				result = "SD卡目前不可用";
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;

	}

	public static Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(uri.getPath());
			bitmap = BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	public static Bitmap decodeUriAsBitmap(Uri uri, String op) {
		Bitmap bitmap = null;
		FileInputStream fis = null;
		Options options = new Options();
		options.inSampleSize = 8;
		try {
			fis = new FileInputStream(uri.getPath());
			bitmap = BitmapFactory.decodeStream(fis, null, options);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	public static Bitmap decodeUriAsBitmap(String path, Options op) {
		Bitmap bitmap = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			bitmap = BitmapFactory.decodeStream(fis, null, op);
			// fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	/** 保存方法 */
	public static void saveBitmap(Bitmap bit, File file) {

		try {
			FileOutputStream out = new FileOutputStream(file);
			bit.compress(CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 毛玻璃效果
	 *
	 * **/
	@SuppressLint("NewApi")
	public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {

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

	public static final String md5(byte[] b) {
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

	// content，传入当前调用activity，imgTempName图片存储路径，cache load路径
	public static Bitmap FileurltoBitmap(Context context, String imgTempName,
			String cache) {
		String imgCardName = "";
		Options options = new Options();
		options.inJustDecodeBounds = false;

		File dF = new File(imgTempName);
		long len = dF.length();
		LogUtil.showLog("len = " + Long.toString(len));
		if (len > 100 * 1024) {
			int sample = (int) (len / (100 * 1024));
			options.inSampleSize = sample > 10 ? 10 : sample;
			LogUtil.showLog("sample = " + Integer.toString(sample));
		} else {
			options.inSampleSize = 1;
		}
		Bitmap bitmap = null;
		imgCardName = imgTempName;
		bitmap = BitmapFactory.decodeFile(imgCardName, options);
		String[] name = imgCardName.split("/");
		imgCardName = name[name.length - 1];
		imgCardName = "IMOB_" + imgCardName.substring(1, imgCardName.length());
		imgCardName = BaseBusiness.imageCachePath + imgCardName;
		PreferenceUtil.getInstance(context).saveString(cache, imgCardName);
		AsyncImageLoader.savePic(bitmap, imgCardName);
		return bitmap;
	}

	public static String SavetoFile( Context context,View view, String url) {
		ByteArrayOutputStream baos = null;
		String signpath = null;
		String result = "";
		try {
			String time = DateUtil.DateToStr(new Date());
			LogUtil.showLog("time",""+time);
			if (PhoneinfoUtils.IsSdCardCanBeUsed()) {
				String sign_dir_default = Environment
						.getExternalStorageDirectory()
						+ File.separator
						+ "DCIM/Camera";
				File file_default = new File(sign_dir_default);
				if (file_default.exists()) {

					signpath = sign_dir_default + File.separator + time
							+ ".jpg";
					baos = new ByteArrayOutputStream();

					view.setDrawingCacheEnabled(true);
					view.buildDrawingCache();
					Bitmap bmp = view.getDrawingCache();

					bmp.compress(CompressFormat.JPEG, 100, baos);
					byte[] photoBytes = baos.toByteArray();
					if (photoBytes != null) {
						new FileOutputStream(new File(signpath))
								.write(photoBytes);
					}

					PhotoDirRefresh(context,new File(signpath),time +".jpg");
					result = "图片保存到" + signpath;



				} else {

					String sign_dir = Environment.getExternalStorageDirectory()
							+ File.separator + url;
					File file = new File(sign_dir);
					if (!file.exists()) {
						try {
							// 按照指定的路径创建文件夹
							if (!file.mkdirs()) {

								result = "创建文件失败";

							} else {
								signpath = sign_dir + File.separator + time
										+ ".jpg";
								baos = new ByteArrayOutputStream();

								view.setDrawingCacheEnabled(true);
								view.buildDrawingCache();
								Bitmap bmp = view.getDrawingCache();

								bmp.compress(CompressFormat.JPEG, 100,
										baos);
								byte[] photoBytes = baos.toByteArray();
								if (photoBytes != null) {
									new FileOutputStream(new File(signpath))
											.write(photoBytes);
								}


								PhotoDirRefresh(context,new File(signpath),time +".jpg");
								result = "图片保存到" + signpath;
							}

						} catch (Exception e) {
						}
					} else {
						signpath = sign_dir + File.separator + time + ".jpg";
						baos = new ByteArrayOutputStream();

						view.setDrawingCacheEnabled(true);
						view.buildDrawingCache();
						Bitmap bmp = view.getDrawingCache();

						bmp.compress(CompressFormat.JPEG, 100, baos);
						byte[] photoBytes = baos.toByteArray();
						if (photoBytes != null) {
							new FileOutputStream(new File(signpath))
									.write(photoBytes);
						}

						PhotoDirRefresh(context,new File(signpath),time +".jpg");
						result = "图片保存到" + signpath;
					}

				}

			} else {
				result = "SD卡目前不可用";
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;

	}

	private static void PhotoDirRefresh(Context context, File file, String fileName) {
		// 其次把文件插入到系统图库
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 最后通知图库更新

		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,	Uri.fromFile(new File(file.getPath()))));
	}

	/**
	 * UI布局中的view，拿到绘制的UI,转byte[]
	 * @param view
	 * @return
	 */
	public static byte[] viewBackbyte(View view){
		byte[] photoBytes = null ;
		Bitmap bmp = null ;
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
			view.setDrawingCacheEnabled(true);
			view.buildDrawingCache();
			bmp = view.getDrawingCache();
			bmp.compress(CompressFormat.JPEG, 100, baos);
			photoBytes = baos.toByteArray();
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
		BASE64Decoder decoder = new BASE64Decoder();
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


	//静态资源转byte
	public static byte[] resToByte(Context context,int resImg){
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),resImg);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

}
