package com.hss01248.bspatch;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;

import javax.xml.transform.sax.TemplatesHandler;

/**
 * @Despciption todo
 * @Author hss
 * @Date 03/12/2021 11:35
 * @Version 1.0
 */
public class BsUtil {
    final static String TAG = "BsUtil";

    static {
        try {
            System.loadLibrary("mybspatch");
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
    }

    public static boolean bsPatch(String oldpath,String newpath,String patch){
        File oldFile = new File(oldpath);
        if(!oldFile.exists() || oldFile.length() ==0){
            Log.w(TAG,"old file not exist or length ==0 "+oldFile.exists()+","+ oldFile.length());
            return false;
        }


        File patchFile = new File(patch);
        if(!patchFile.exists()){
            Log.w(TAG,"patch file not exist  ");
            return false;
        }
        if(patchFile.length() ==0){
            Log.w(TAG,"patch file length == 0  ");
            return false;
        }
        if(TextUtils.isEmpty(newpath)){
            Log.w(TAG,"newpath is null  ");
            return false;
        }
        try {
            int code = patch(oldpath, newpath, patch);
            if(code != 0){
                Log.w(TAG,"合并失败,错误码 为:"+code);
                return false;
            }
            File newFile = new File(newpath);
            if(!newFile.exists()){
                Log.w(TAG,"虽然code=0,但合并后的文件不存在");
                return false;
            }

            if(newFile.length() ==0){
                Log.w(TAG,"虽然code=0,但合并后的文件大小为0");
                return false;
            }
            //todo 检查大小?
           /* if(newFile.length() != (oldFile.length() + patchFile.length())){
                Log.w(TAG,"虽然code=0,但合并后的文件大小和原始两份文件大小不相等");
                return false;
            }*/

            return true;

        }catch (Throwable throwable){
            Log.w(TAG,"bspatch出错",throwable);
            return false;

        }

    }

    /**
     * native方法 使用路径为oldPath的文件与路径为patchPath的补丁包，合成新的文件，并存储于newPath
     * 返回：0，说明操作成功
     *
     * @param oldpath   示例:/sdcard/old.apk
     * @param newpath   示例:/sdcard/new.apk
     * @param patch 示例:/sdcard/xx.patch
     */
     native static int patch(String oldpath,String newpath,String patch);
}
