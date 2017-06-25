package juda.zhang.studio.avspider.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Juda.Zhang on 2017/6/6.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 过滤空格并且大写
     *
     * @param s
     * @return
     */
    public static String trimAndUpper(String s) {
        return org.apache.commons.lang3.StringUtils.upperCase(org.apache.commons.lang3.StringUtils.trim(s));
    }

    /**
     * 过滤目录名，去除特殊字符<>\/|:*?"
     *
     * @param dir
     * @return
     */
    public static String filterSpecialDirString(String dir) {
        if (StringUtils.isBlank(dir)) {
            return dir;
        }
        String regex = "[*\"<>/|:?\\\\]";
        Matcher m = Pattern.compile(regex).matcher(dir);
        return m.replaceAll("");
    }
}
