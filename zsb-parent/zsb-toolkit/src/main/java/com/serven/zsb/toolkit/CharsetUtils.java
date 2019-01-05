package com.serven.zsb.toolkit;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by zhangjiayuan
 * Date: 2019/1/4
 */
public class CharsetUtils {
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String UTF_8 = "UTF-8";
    public static final String GBK = "GBK";
    public static final Charset CHARSET_ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
    public static final Charset CHARSET_GBK = Charset.forName("GBK");

    private CharsetUtils() {
    }

    public static Charset charset(String charset) {
        return StringUtils.isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset);
    }

    public static String convert(String source, String srcCharset, String destCharset) {
        return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
    }

    public static String convert(String source, Charset srcCharset, Charset destCharset) {
        if (null == srcCharset) {
            srcCharset = StandardCharsets.ISO_8859_1;
        }

        if (null == destCharset) {
            destCharset = StandardCharsets.UTF_8;
        }

        return !StringUtils.isBlank(source) && !srcCharset.equals(destCharset) ? new String(source.getBytes(srcCharset), destCharset) : source;
    }

    public static String systemCharset() {
        return defaultCharsetName();
    }

    public static String defaultCharsetName() {
        return Charset.defaultCharset().name();
    }

    public static Charset defaultCharset() {
        return Charset.defaultCharset();
    }
}
