package com.serven.zsb.exception;

/**
 * Created by zhangjiayuan
 * Date: 2018/11/23
 */
public class ClientException extends ZsbExcption {
    private static final long serialVersionUID = 6536735884200973301L;

    public ClientException() {
    }

    public ClientException(Exception e) {
        super(e);
    }

    public ClientException(String status, String message, Exception e) {
        super(status, message, e);
    }

    public ClientException(String status, String message) {
        super(status, message);
    }
}
