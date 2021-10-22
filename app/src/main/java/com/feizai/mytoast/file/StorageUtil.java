package com.feizai.mytoast.file;

import android.os.StatFs;

import java.text.DecimalFormat;

public class StorageUtil {

    private static final long KB = 1024;
    private static final long MB = KB * 1024;
    private static final long GB = MB * 1024;

    private static final String INTERNAL_STORAGE_PATH = "/storage/emulated/0";


    public static long getTotalSpace(CharSequence path) {
        try {
            StatFs sf = new StatFs((String) path);
            return sf.getBlockSizeLong() * sf.getBlockCountLong();
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getAvailSpace(CharSequence path) {
        try {
            StatFs sf = new StatFs((String) path);
            return sf.getBlockSizeLong() * sf.getAvailableBlocksLong();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * * @param 获取显示的存储空间大小
     */
    public static String getFileSizeDescription(long size) {
        String fileSizeString = "0KB";
        DecimalFormat decimalFormat = new DecimalFormat(".0");
        float resultSize;
        if (size < KB) {
            fileSizeString = String.valueOf(size) + "B";
        } else if (size > KB && size <= MB) {
            resultSize = size / KB;
            fileSizeString = String.valueOf(resultSize) + "KB";
        } else if (size > MB && size <= GB) {
            resultSize = size / MB;
            fileSizeString = String.valueOf(resultSize) + "MB";
        } else {
            resultSize = size * 1.0f / GB;
            fileSizeString = decimalFormat.format(resultSize) + "G";
        }
        return fileSizeString;
    }

}
