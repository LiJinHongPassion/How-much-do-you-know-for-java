package com.codeant.rpc.five;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class IOUtil {

    public static void closeQuietly(java.io.Closeable o) {
        if (null == o) return;
        try {
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式化文件大小
     *
     * @param length
     * @return
     */
    public static String getFormatFileSize(long length) {
        double size = ((double) length) / (1 << 30);
        if (size >= 1) {
            return fileSizeFormater.format(size) + "GB";
        }
        size = ((double) length) / (1 << 20);
        if (size >= 1) {
            return fileSizeFormater.format(size) + "MB";
        }
        size = ((double) length) / (1 << 10);
        if (size >= 1) {
            return fileSizeFormater.format(size) + "KB";
        }
        return length + "B";
    }

    private static DecimalFormat fileSizeFormater = FormatUtil.decimalFormat(1);

    /**
     * 格式化
     */
    static class FormatUtil {
        /**
         * 设置数字格式，保留有效小数位数为fractions
         *
         * @param fractions 保留有效小数位数
         * @return 数字格式
         */
        public static DecimalFormat decimalFormat(int fractions) {

            DecimalFormat df = new DecimalFormat("#0.0");
            df.setRoundingMode(RoundingMode.HALF_UP);
            df.setMinimumFractionDigits(fractions);
            df.setMaximumFractionDigits(fractions);
            return df;
        }
    }
}