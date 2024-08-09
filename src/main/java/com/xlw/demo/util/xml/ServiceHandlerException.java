package com.xlw.demo.util.xml;

public class ServiceHandlerException extends RapidServerException {
    private static final long serialVersionUID = -3249415405394678267L;

    public ServiceHandlerException(String msg) {
        super(msg);
        setEcode("00009999");
    }

    public ServiceHandlerException(Throwable cause) {
        super(cause);
        setEcode("00009999");
    }

    public ServiceHandlerException(String msg, Throwable cause) {
        super(msg, cause);
        setEcode("00009999");
    }
}