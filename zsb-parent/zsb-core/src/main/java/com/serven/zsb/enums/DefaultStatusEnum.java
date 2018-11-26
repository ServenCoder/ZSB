package com.serven.zsb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by zhangjiayuan
 * Date: 2018/11/23
 */
@Getter
@AllArgsConstructor
public enum DefaultStatusEnum {
    OK("0", "success"),
    SERVER_ERROR("50000", "server error, please ask admin for help");

    private String code;
    private String name;
}
