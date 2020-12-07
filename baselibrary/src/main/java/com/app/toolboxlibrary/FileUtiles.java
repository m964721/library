package com.app.toolboxlibrary;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author
 * @说明：
 */
public class FileUtiles {

    public static String saveFileName = "goBestSoft";

    /**
     * 向存储设备写入数据
     *
     * @param file     文件夹
     * @param filename 文件名
     * @param data     写入的数据
     */
    public static void writeData(String file, String filename, String data) {
        if (null != file && null != filename) {
            File file_write = Environment.getExternalStorageDirectory();
            file_write = new File(file_write, file);
            file_write.mkdirs();
            file_write = new File(file_write, filename);
            FileOutputStream fileout;
            Writer writer = null;
            try {
                fileout = new FileOutputStream(file_write);
                writer = new OutputStreamWriter(fileout);
                writer.write(data);
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从存储设备读取文件
     *
     * @param File_str     文件夹
     * @param Filename_str 文件名
     * @return 读取出的数据内容
     */
    public static String readFileData(String File_str, String Filename_str) {
        String read_str = "";
        if (null != File_str && null != Filename_str) {
            File file_read = Environment.getExternalStorageDirectory();
            String filename = file_read + File_str + "/" + Filename_str;
            File file = new File(filename);
            if (file.isFile() && file.exists()) {
                try {
                    FileInputStream file_in = new FileInputStream(file);
                    InputStreamReader reader = new InputStreamReader(file_in,
                            "UTF-8");
                    BufferedReader bf = new BufferedReader(reader);
                    String lineTxt = "";
                    while ((lineTxt = bf.readLine()) != null) {
                        read_str = lineTxt + read_str;
                    }
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return read_str;
    }

    /**
     * 删除文件夹以及文件夹包含的文件
     *
     * @param filepath 文件路径
     */

    public static void deleteFile(String filepath) {
        if (null != filepath) {
            File file = new File(Environment.getExternalStorageDirectory()
                    + filepath);
            if (file.exists()) { // 判断文件是否存在
                if (file.isFile()) { // 判断是否是文件
                    file.delete(); // delete()方法
                } else if (file.isDirectory()) { // 如果它是一个目录
                    File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                    for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                        files[i].delete();
                    }
                }
                file.delete();
            } else {
                return;
            }
        } else {
            return;
        }
    }


    /**
     * 删除文件夹包含的文件
     *
     * @param filepath 文件路径
     */

    public static void deleteSaveFile(Context context ,String filepath) {
        if (null != filepath) {
            File file = new File(filepath);
            if (file.exists()) { // 判断文件是否存在
                if (file.isDirectory()) { // 如果它是一个目录
                    File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                    for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                        files[i].delete();
                    }
                }
            } else {
                return;
            }
        } else {
            return;
        }
    }


    /**
     * 删除文件
     *
     * @param url 文件路径
     */

    public static boolean deleteUrlFile(String url) {
        if (!StringUtils.isStringToNUll(url)) {
            File file = new File(url);
            if (file.exists()) { // 判断文件是否存在
                file.delete();
            }
        }
        return false ;
    }



    //读取assets下的文件
    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //读取RW文件
    public static String getFromRw(Context context, int res) {
        String data = "";
        try {
            InputStream is = context.getResources().openRawResource(res);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            while ((data = br.readLine()) != null) {
                data = data + br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }



    /**
     * 创建文件或文件夹
     *
     * @param fileName 文件名或问文件夹名
     */
    public static String createFile(Context context,String fileName) {
        File file ;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.P){
            file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            return file.getAbsolutePath();
        }else {
            String fileUrl = Environment.getExternalStorageDirectory() + "/" + fileName;
            file = new File(fileUrl);
            if(!file.exists()) {
                // 创建文件夹
                file.mkdir();
            }
            return file.getAbsolutePath()+"/";
        }
    }


    /**
     * 图片切割处理,得到的图片处理一次
     *
     * @param inSampleSize 可以根据需求计算出合理的inSampleSize;传值以2的N次方值进行处理
     *                     fileName:保存文件名字
     *                     fromImgUrl:获取到的路径
     */
    public static String compress(Context context,String savefileName, String fromImgUrl, int inSampleSize,String type) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        Bitmap resultBitmap = null ;
        if( isAndroidQ() && "0".equals(type) ){
            resultBitmap = getImageContentUri(context,fromImgUrl);
        }else{
            File originFile = new File(fromImgUrl);
            resultBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath(),options);
            inSampleSize = 1 ;
            int angle = readPictureDegree(fromImgUrl);
            resultBitmap = rotaingImageView(angle, resultBitmap);
        }
        if(null == resultBitmap ){
            return  "";
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100/inSampleSize, bos);
        File outputFile = new File(savefileName);
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != resultBitmap) resultBitmap.recycle();
        }
        return outputFile.getAbsolutePath();
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = -1;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
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
        LogUtil.showLog("readPictureDegree", "图片旋转了：" + degree + " 度");
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param angle  旋转角度
     * @param bitmap 要处理的Bitmap
     * @return 处理后的Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    public static Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    //生成图片的URL
    public static String creatPicUrl(Context context,String picName) {
        String filename = createFile(context,saveFileName);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmss");
        if (!StringUtils.isStringToNUll(picName)) {
            picName = picName + "_";
        }
        String savePath = filename + saveFileName + picName + format2.format(new Date()) + ".jpg";
        LogUtil.showLog("creatPicUrl",savePath);
        return savePath;
    }

    //返回大小
    public static  String FormatFileSize(double fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#0.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format( fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format(fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format(fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format(fileS / 1073741824) +"G";
        }
        return fileSizeString;
    }

    //uri授权
    public static Uri backFileUri(Context context ,String fileUrl,String FILEPROVIDER){
        File file = new File(fileUrl);
        Uri uri;
        if (isAndroidN()) {
            // 适配android7.0 ，不能直接访问原路径
            // 需要对intent 授权
            uri = FileProvider.getUriForFile(context,
                    FILEPROVIDER,
                    file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri ;
    }

    //判断文件是否存在
    public static boolean isAndroidQFileExists(Context context, String path){
        boolean isFileExists = false ;
        if(isAndroidQ()){
            AssetFileDescriptor afd = null;
            ContentResolver cr = context.getContentResolver();
            try {
                Uri uri = Uri.parse(path);
                afd = cr.openAssetFileDescriptor(uri, "r");
                if (afd == null) {
                    isFileExists =  false;
                } else {
                    afd.close();
                    isFileExists = true ;
                }
            } catch (FileNotFoundException e) {

            } catch (IOException e) {

            }
        }else{
            File file = new File(path);
            isFileExists =  file.exists();
        }

        return isFileExists;
    }

    //根据图片path，返回uri，适配Android Q以上版本
    public static Bitmap getImageContentUri(Context context, String path) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
                new String[] { path }, null);
        Uri baseUri = null ;
        // 如果图片不在手机的共享图片数据库，就先把它插入。

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            baseUri = Uri.parse("content://media/external/images/media");
            baseUri = Uri.withAppendedPath(baseUri, "" + id);
        }else if (isAndroidQFileExists(context,path)) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, path);
            baseUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        Bitmap bitmap1 = null ;
        try {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
                bitmap1 = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(baseUri));
            }else {
                bitmap1 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), baseUri);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bitmap1 ;
    }

    //判断是否大于Android Q系统
    public static boolean isAndroidQ(){
        return  Build.VERSION.SDK_INT > Build.VERSION_CODES.P ;
    }

    //判断是否大于Android N系统
    public static boolean isAndroidN(){
        return  Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ;
    }

    /**
     * 删除图片文件
     */
    public static void deleteAppSaveFile(Context context) {
        File file ;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.P){
            file = context.getExternalFilesDir("");
        }else {
            String fileUrl = Environment.getExternalStorageDirectory() + "/" + saveFileName;
            file = new File(fileUrl);
        }
        if (file.isDirectory()) { // 如果它是一个目录
            File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
            for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                files[i].delete();
            }
        }
    }

    //file转bitmap
    public static Bitmap backFileToBitMap(String showUrl) {
        if(StringUtils.isStringToNUll(showUrl)){
            return  null ;
        }
        File originFile = new File(showUrl);
        Bitmap resultBitmap = BitmapFactory.decodeFile(originFile.getAbsolutePath());
        return resultBitmap;
    }

    //bitmap转存文件
    public static String bitmapToFileBackUrl(Context CTX ,Bitmap resultBitmap,String saveFileName){
        if(null == resultBitmap){
            return "" ;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        File outputFile = new File(saveFileName);
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != resultBitmap) resultBitmap.recycle();
        }
        return outputFile.getAbsolutePath();
    }
}
