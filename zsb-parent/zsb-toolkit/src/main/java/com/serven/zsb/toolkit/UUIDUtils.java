package com.serven.zsb.toolkit;

import java.util.UUID;

/**
 * Created by zhangjiayuan
 * Date: 2018/11/27
 */
public class UUIDUtils {
    public UUIDUtils() {
    }

    public static String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
