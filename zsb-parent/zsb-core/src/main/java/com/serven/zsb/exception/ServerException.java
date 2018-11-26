package com.serven.zsb.exception;

/**
 * Created by zhangjiayuan
 * Date: 2018/11/23
 */
public class ServerException extends ZsbExcption {
    private static final long serialVersionUID = -5540106384159657614L;

    public ServerException() {
    }

    public ServerException(Exception e) {
        super(e);
    }

    public ServerException(String status, String message, Exception e) {
        super(status, message, e);
    }

    public ServerException(String status, String message) {
        super(status, message);
    }
}
