package com.serven.zsb.exception;

import com.serven.zsb.enums.DefaultStatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhangjiayuan
 * Date: 2018/11/23
 */
@Getter
@Setter
public class ZsbExcption extends RuntimeException {
    private static final long serialVersionUID = -7116593550462368951L;
    private String status;
    private String message;
    private int httpCode;

    public ZsbExcption() {
        this(DefaultStatusEnum.SERVER_ERROR.getCode(), DefaultStatusEnum.SERVER_ERROR.getName());
    }

    public ZsbExcption(Exception e) {
        this(DefaultStatusEnum.SERVER_ERROR.getCode(), DefaultStatusEnum.SERVER_ERROR.getName(), e);
    }

    public ZsbExcption(String status, String message) {
        super(String.format("[status=%s,message=%s]", status, message));
        this.httpCode = 500;
        this.status = status;
        this.message = message;
    }

    public ZsbExcption(String status, String message, Exception e) {
        super(String.format("[status=%s,message=%s]", status, message), e);
        this.httpCode = 500;
        this.status = status;
        this.message = message;
    }

}
